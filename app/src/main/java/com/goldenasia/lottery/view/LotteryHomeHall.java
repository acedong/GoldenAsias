package com.goldenasia.lottery.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.IssueAll;
import com.goldenasia.lottery.bean.IssueSales;
import com.goldenasia.lottery.bean.RecordTime;
import com.goldenasia.lottery.engine.CommonInfoEngine;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.SpeciesListElements;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.adapter.CategoryListViewAdapter;
import com.goldenasia.lottery.view.controls.MyListener;
import com.goldenasia.lottery.view.controls.PullToRefreshLayout;
import com.goldenasia.lottery.view.controls.PullableListView;
import com.goldenasia.lottery.view.controls.RushBuyCountDownTimerView;
import com.goldenasia.lottery.view.controls.PullToRefreshLayout.OnRefreshListener;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.BottomManager;
import com.goldenasia.lottery.view.manager.TitleManager;

/**
 * 购彩大厅
 * @author Ace
 */
public class LotteryHomeHall extends BaseUI implements OnRefreshListener{
	// 第一步：加载layout（布局参数设置）
	// 第二步：初始化layout中控件
	// 第三步：设置监听
	private PullToRefreshLayout refreshView;
	private PullableListView categoryList;// 彩种的入口
	private CategoryListViewAdapter adapterCategory;
	private LinearLayout historyRefresh;
	private TextView textRefresh;
	private SpeciesListElements specieslist;
	
	public LotteryHomeHall(Context context) {
		super(context);
	}

	public void init() {
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.nb_lottery_home_hall, null);
		
		historyRefresh=(LinearLayout)findViewById(R.id.nb_lottery_home_refresh);
		//初使化页面
		refreshView=(PullToRefreshLayout) findViewById(R.id.refresh_view);
		categoryList = (PullableListView)findViewById(R.id.nb_homwespecies_list);
		textRefresh=(TextView)historyRefresh.findViewById(R.id.nb_page_refresh_text);
	}
	
	private boolean flag=true;
	
	@Override
	public void onResume() {
		TitleManager.getInstance().changeHomeTitle("购彩大厅");
		BottomManager.getInstrance().selectSwitch(1);
		BottomManager.getInstrance().UserType();
		if(flag){
			specieslist=(SpeciesListElements)bundle.getSerializable("species");
			if(specieslist!=null){
				flag=false;
				Map<String, RecordTime> saleendTimeMap=new HashMap<String, RecordTime>();
				Map<String, RecordTime> openTimeMap=new HashMap<String, RecordTime>();
				for(Map.Entry<String, IssueAll> entry:specieslist.getIssueAllMap().entrySet()){ 
					IssueAll issueall=entry.getValue();
					IssueSales issueSales=issueall.getIssueSales();
					saleendTimeMap.put(entry.getKey(), getLasttime(issueSales.getCurrenttime(),issueSales.getSaleend()));
					openTimeMap.put(entry.getKey(), getLasttime(issueSales.getCurrenttime(),issueSales.getOpentime()));
				} 
				specieslist.setSaleendTime(saleendTimeMap);
				specieslist.setOpenTime(openTimeMap);
				adapterCategory.refresh(specieslist);
			}
		}else
			getDataRequest();
		super.onResume();
	}

	@Override
	public int getID() {
		return ConstantValue.VIEW_HOME_HALL;
	}
	
	@Override
	public void setListener() {
		
		refreshView.setOnRefreshListener(this);
		adapterCategory = new CategoryListViewAdapter(context);
		adapterCategory.setOnUpdateData(new CategoryListViewAdapter.OnUpdateDataListener() {

			@Override
			public void onUpdateData(SpeciesListElements species) {
				// TODO Auto-generated method stub
				specieslist=species;
				bundle.putSerializable("species",specieslist);  
			}
			
//			@Override
//			public void onUpdateData(String key, RecordTime saleendTime,RecordTime openTime) {
//				// TODO Auto-generated method stub
//				Map<String, RecordTime> saleendTimeMap=specieslist.getSaleendTime();
//				Map<String, RecordTime> openTimeMap=specieslist.getOpenTime();
//				for(Map.Entry<String, RecordTime> entry:specieslist.getSaleendTime().entrySet()){ 
//					if(entry.getKey().equals(key)){
//						saleendTimeMap.put(entry.getKey(), saleendTime);
//						openTimeMap.put(entry.getKey(), openTime);
//					}	
//				} 
//				specieslist.setSaleendTime(saleendTimeMap);
//				specieslist.setOpenTime(openTimeMap);
//			}
		});
		
		categoryList.setAdapter(adapterCategory);
		categoryList.setFadingEdgeLength(0);// 删除黑边（上下）
		
		String targetIco=context.getResources().getString(R.string.fa_signal);
		textRefresh.setText(targetIco);
		textRefresh.setTypeface(font);
		textRefresh.setTextColor(context.getResources().getColor(R.color.slategray));
		textRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onRefresh(refreshView);
			}
		});
	}
	
	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
	{
		// 下拉刷新操作
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				getDataRequest(pullToRefreshLayout);
			}
		}.sendEmptyMessageDelayed(0, 5000);
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
	{
		// 加载操作
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// 千万别忘了告诉控件加载完毕了哦！
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 5000);
	}
	
	/**
	 * 获取请求列表信息 
	 */
	private void getDataRequest(final PullToRefreshLayout pullToRefreshLayout) {
		textRefresh.setVisibility(View.GONE);
		new MyHttpTask<Integer>() {
			
			@Override
			protected com.goldenasia.lottery.net.protocal.Message doInBackground(Integer... params) {
				// 获取数据——业务的调用
				CommonInfoEngine engine = BeanFactory.getImpl(CommonInfoEngine.class);
				return engine.getSpeciesListInfo(params[0]);
			}

			@Override
			protected void onPostExecute(com.goldenasia.lottery.net.protocal.Message result) {
				// 更新界面
//				PromptManager.closeProgressDialog();
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();

					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						specieslist= (SpeciesListElements) result.getBody().getElements().get(0);
						Map<String, RecordTime> saleendTime=new HashMap<String, RecordTime>();
						Map<String, RecordTime> openTime=new HashMap<String, RecordTime>();
						for(Map.Entry<String, IssueAll> entry:specieslist.getIssueAllMap().entrySet()){ 
							IssueAll issueall=entry.getValue();
							IssueSales issueSales=issueall.getIssueSales();
							saleendTime.put(entry.getKey(), getLasttime(issueSales.getCurrenttime(),issueSales.getSaleend()));
							openTime.put(entry.getKey(), getLasttime(issueSales.getCurrenttime(),issueSales.getOpentime()));
						} 
						specieslist.setSaleendTime(saleendTime);
						specieslist.setOpenTime(openTime);
						adapterCategory.refresh(specieslist);
						bundle.putSerializable("species",specieslist);  
						issueAllMap=specieslist.getIssueAllMap();
						menuMap=specieslist.getMenuMap();
						initMenuArrayData();
						appendIssueInfo();
						pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
						}
						pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					PromptManager.showToast(context, "网络状态差，请重试！");
					textRefresh.setVisibility(View.VISIBLE);
					pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
				}
				
				super.onPostExecute(result);
			}
		}.executeProxy(0);
		
	}
	
	/**
	 * 获取请求列表信息 
	 */
	private void getDataRequest() {
		textRefresh.setVisibility(View.GONE);
		new MyHttpTask<Integer>() {
			
			@Override
			protected com.goldenasia.lottery.net.protocal.Message doInBackground(Integer... params) {
				// 获取数据——业务的调用
				CommonInfoEngine engine = BeanFactory.getImpl(CommonInfoEngine.class);
				return engine.getSpeciesListInfo(params[0]);
			}

			@Override
			protected void onPostExecute(com.goldenasia.lottery.net.protocal.Message result) {
				// 更新界面
//				PromptManager.closeProgressDialog();
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();

					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						specieslist = (SpeciesListElements) result.getBody().getElements().get(0);
						Map<String, RecordTime> saleendTime=new HashMap<String, RecordTime>();
						Map<String, RecordTime> openTime=new HashMap<String, RecordTime>();
						for(Map.Entry<String, IssueAll> entry:specieslist.getIssueAllMap().entrySet()){ 
							IssueAll issueall=entry.getValue();
							IssueSales issueSales=issueall.getIssueSales();
							saleendTime.put(entry.getKey(), getLasttime(issueSales.getCurrenttime(),issueSales.getSaleend()));
							openTime.put(entry.getKey(), getLasttime(issueSales.getCurrenttime(),issueSales.getOpentime()));
						} 
						specieslist.setSaleendTime(saleendTime);
						specieslist.setOpenTime(openTime);
						bundle.putSerializable("species",specieslist);  
						adapterCategory.refresh(specieslist);
						issueAllMap=specieslist.getIssueAllMap();
						menuMap=specieslist.getMenuMap();
						initMenuArrayData();
						appendIssueInfo();
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
							textRefresh.setVisibility(View.VISIBLE);
						}
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					PromptManager.showToast(context, "网络状态差，请重试！");
					textRefresh.setVisibility(View.VISIBLE);
				}
				
				super.onPostExecute(result);
			}
		}.executeProxy(0);
		
	}
	
	@SuppressLint("SimpleDateFormat")
	private RecordTime getLasttime(String currenttime,String end) {
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		 RecordTime recTime=new RecordTime();
		 try {
			//Date currentTime = new Date();
			//String dateString = df.format(currentTime);
			 if((currenttime!=null||currenttime.equals(""))&&(end!=null||end.equals(""))){
				Date d1 = df.parse(currenttime);
				Date d2 = df.parse(end);
				// Date d2 = new Date(System.currentTimeMillis());//你也可以获取当前时间
				long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别
				long days = diff / (1000 * 60 * 60 * 24);
				long hours = (diff - days * (1000 * 60 * 60 * 24))
						/ (1000 * 60 * 60);
				long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours* (1000 * 60 * 60))/ (1000 * 60);
				long seconds = (diff/1000-days*24*60*60-hours*60*60-minutes*60);  
				int day= new Long(days).intValue();  int hour= new Long(hours).intValue(); int minute= new Long(minutes).intValue(); int second= new Long(seconds).intValue();
				recTime.setDay(day);recTime.setHour(hour); recTime.setMinute(minute);recTime.setSecond(second);
				return recTime;
			 }else{
				 recTime.setDay(0);recTime.setHour(0); recTime.setMinute(0);recTime.setSecond(0);
				 return recTime;
			 }
		 } catch (Exception e) {
		 }
		 return recTime;
	}
	
	
}
