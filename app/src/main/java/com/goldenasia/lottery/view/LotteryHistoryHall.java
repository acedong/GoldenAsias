package com.goldenasia.lottery.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.Category;
import com.goldenasia.lottery.bean.HistoryLottery;
import com.goldenasia.lottery.bean.IssueLast;
import com.goldenasia.lottery.bean.LotteryInfo;
import com.goldenasia.lottery.engine.LotteryInfoEngine;
import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.HistoryListElements;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.adapter.HistoryLotteryListAdapter;
import com.goldenasia.lottery.view.custom.ActionSlideExpandableListView;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.BottomManager;
import com.goldenasia.lottery.view.manager.HallMiddleManager;
import com.goldenasia.lottery.view.manager.TitleManager;

/**
 * 历史开奖大厅
 * @author Ace
 *
 */
public class LotteryHistoryHall extends BaseUI{

	private HistoryLotteryListAdapter historyCategory;
	private List<Category> historyList = new ArrayList<Category>();
	private ActionSlideExpandableListView list;
	
    /*Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_PROGRESS:
                	if (mProgress < 40) {
                        mProgress += 1;
                        // 随机800ms以内刷新一次
                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,new Random().nextInt(800));
                        mLeafLoadingView.setProgress(mProgress);
                    } else {
                        mProgress += 1;
                        // 随机1200ms以内刷新一次
                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,new Random().nextInt(1200));
                        mLeafLoadingView.setProgress(mProgress);
                    }
                    break;

                default:
                    break;
            }
        };
    };*/
//    private RelativeLayout historyLoading;
    private LinearLayout historyRefresh;
//    private static final int REFRESH_PROGRESS = 0x10;
//    private LeafLoadingView mLeafLoadingView;
//    private ImageView mFanView;
//    private int mProgress = 0;
    private TextView textRefresh;
	
	public LotteryHistoryHall(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		showInMiddle = (LinearLayout) View.inflate(context,R.layout.nb_lottery_history_hall, null);
		list = (ActionSlideExpandableListView) findViewById(R.id.list);
		
//		historyLoading=(RelativeLayout)findViewById(R.id.nb_lottery_history_loading);
		historyRefresh=(LinearLayout)findViewById(R.id.nb_lottery_history_refresh);
		
//		mFanView =(ImageView)historyLoading.findViewById(R.id.fan_pic);
//      mLeafLoadingView = (LeafLoadingView)historyLoading.findViewById(R.id.leaf_loading);
        
        textRefresh=(TextView)historyRefresh.findViewById(R.id.nb_page_refresh_text);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		historyList=new ArrayList<Category>();
		historyCategory = new HistoryLotteryListAdapter(context);
		
		list.setAdapter(historyCategory);
		list.setFadingEdgeLength(0);// 删除黑边（上下）
		list.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {

			@Override
			public void onClick(View listView, View buttonview, int position) {

				Category category=historyList.get(position);
				HistoryLottery history=category.getLotteryTitle();
				Bundle bundles=new Bundle();
				bundles.putInt("lotteryid", Integer.parseInt(history.getLotteryId()));
				bundles.putString("lotteryname", history.getLotteryName());
				bundles.putString("issue", history.getLotteryIssue());
				bundles.putInt("lotterytype", Integer.parseInt(history.getLotterytype()));
				
				if(buttonview.getId()==R.id.nb_history_btnopen) {
					HallMiddleManager.getInstance().changeUI(LotteryHistoryDetailHall.class,bundles);
				}else if(buttonview.getId()==R.id.nb_history_btnbutting){
					if(Integer.parseInt(history.getLotterytype()) ==0)
						HallMiddleManager.getInstance().changeUI(PlaySSC.class,bundles);
					else if(Integer.parseInt(history.getLotterytype()) ==2)
						HallMiddleManager.getInstance().changeUI(PlaySYFive.class,bundles);
				}else if(buttonview.getId()==R.id.nb_history_btnrandom){
					HallMiddleManager.getInstance().changeUI(Shopping.class,bundles);
				}
			}

		}, R.id.nb_history_btnopen, R.id.nb_history_btnbutting,R.id.nb_history_btnrandom);
		
		String targetIco=context.getResources().getString(R.string.fa_signal);
		textRefresh.setText(targetIco);
		textRefresh.setTypeface(font);
		textRefresh.setTextColor(context.getResources().getColor(R.color.slategray));
		textRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDataRequestRefresh();
			}
		});
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstantValue.VIEW_HISTORY;
	}
	
	@Override
	public void onResume() {
		TitleManager.getInstance().changeHomeTitle("最新开奖");
		BottomManager.getInstrance().selectSwitch(2);
		if(historyList.size()>0)
			getDataRequestRefresh();
		else
			getDataRequest();
			
		super.onResume();
	}
	
	/**
	 * 获取请求列表信息
	 */
	private void getDataRequestRefresh() {
		new MyHttpTask<Integer>() {
			
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}
			
			@Override
			protected Message doInBackground(Integer... params) {
				// 获取数据——业务的调用
				LotteryInfoEngine lottery = BeanFactory.getImpl(LotteryInfoEngine.class);
				return lottery.getRecentLotteryInfo();
			}

			@Override
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						historyChangeNotice(result.getBody().getElements().get(0));
					} else {
						PromptManager.showToast(context, oelement.getErrormsg());
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					historyRefresh.setVisibility(View.VISIBLE);
				}
				super.onPostExecute(result);
			}
		}.executeProxy(0);
	}
	
	/**
	 * 获取请求列表信息
	 */
	private void getDataRequest() {
		new MyHttpTask<Integer>() {
			
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}
			
			@Override
			protected Message doInBackground(Integer... params) {
				// 获取数据——业务的调用
				LotteryInfoEngine lottery = BeanFactory.getImpl(LotteryInfoEngine.class);
				return lottery.getRecentLotteryInfo();
			}

			@Override
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						historyChangeNotice(result.getBody().getElements().get(0));
					} else {
						PromptManager.showToast(context, oelement.getErrormsg());
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					historyRefresh.setVisibility(View.VISIBLE);
				}
//				mProgress=0;
//				mLeafLoadingView.setProgress(mProgress);
				super.onPostExecute(result);
			}
		}.executeProxy(0);
	}

	protected void historyChangeNotice(Element element) {
		HistoryListElements historyElesList = (HistoryListElements) element;
		List<LotteryInfo> lotteryInfoList=historyElesList.getLotteryList();
		Map<String, List<IssueLast>> historyMap=historyElesList.getHistoryMap();
		
		List<Category> listData = new ArrayList<Category>();
		
		//循环组合分组
		for(LotteryInfo l:lotteryInfoList){
			HistoryLottery hisLottery=new HistoryLottery();
			Category category = new Category();
			hisLottery.setLotteryId(l.getLotteryId());
			hisLottery.setLotteryName(l.getLotteryName());
			hisLottery.setSorts(l.getSorts());
			hisLottery.setLotterytype(l.getLotterytype());
			
			List<IssueLast> issueLastList = historyMap.get(l.getLotteryId());
			List<HistoryLottery> historyList=new ArrayList<HistoryLottery>();
			for(int i=0; i<issueLastList.size(); i++){
				IssueLast issue=issueLastList.get(i);
				if(i==0){
					hisLottery.setLotteryIssue(issue.getIssue());
					hisLottery.setLotterydigit(issue.getCode());
					
				}else{
					HistoryLottery hl=new HistoryLottery();
					hl.setLotteryIssue(issue.getIssue());
					hl.setLotterydigit(issue.getCode());
					hl.setSorts(issue.getSorts());
					historyList.add(hl);
					category.setmCategoryItem(historyList);	//添加子类
				}
			}
			category.setLotteryTitle(hisLottery);
			listData.add(category);
		}
		historyList.clear();
        historyList=listData;
		historyCategory.refresh(listData);
	}

}
