package com.goldenasia.lottery.view;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.GlobalParams;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.AppendTaskInfo;
import com.goldenasia.lottery.engine.BettingEngine;
import com.goldenasia.lottery.net.NetUtil;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.AppendOrderElements;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.adapter.AppendOrderListAdapter;
import com.goldenasia.lottery.view.controls.RefreshLoadListView;
import com.goldenasia.lottery.view.controls.RefreshLoadListView.IXListViewListener;

/**
 * 追号任务
 * @author Ace
 *
 */
public class Tab03Activity extends Activity implements IXListViewListener{
	
	private Context context =null;
	
	private RefreshLoadListView appendOrderList;
	
	private LinearLayout footerLayout;
	private TextView appendorderText;
	
	private AppendOrderListAdapter appendAdapter;
	
	private List<AppendTaskInfo> appendCurrentList=new ArrayList<AppendTaskInfo>();
	
	private Handler mHandler;
	private int currentPage=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context=Tab03Activity.this;
		
		setContentView(R.layout.nb_tab_history_append);
		
		appendOrderList=(RefreshLoadListView)findViewById(R.id.nb_history_append_order_list);
		appendOrderList.setPullRefreshEnable(true);
		appendOrderList.setRefreshLoadListViewListener(this);
		appendOrderList.setFadingEdgeLength(0);// 删除黑边（上下）
		
		View emptyview = View.inflate(this,R.layout.nb_tab_history_appendorder_emptyview, null);
		appendorderText = (TextView) emptyview.findViewById(R.id.nb_appendorders_header_info);
		footerLayout = (LinearLayout) emptyview.findViewById(R.id.nb_appendorders_header);
		appendOrderList.addFooterView(emptyview);
		
		appendAdapter = new AppendOrderListAdapter(context);
		appendOrderList.setAdapter(appendAdapter);
		
		mHandler = new Handler();
	}
	
	@Override
	public void onResume() {

		if(!GlobalParams.PARENTID.equals("0")){
			appendCurrentList.clear();
			currentPage = 1;
			getDataRequestLoding(currentPage);
			appendAdapter.refresh(appendCurrentList);
		}
		super.onResume();
	}
	
	private void getDataRequestLoding(int page) {

		new ProgressBarAsyncTask<Integer>() {

			@Override
			protected Message doInBackground(Integer... params) {
				// 获取数据——业务的调用
				BettingEngine engine = BeanFactory.getImpl(BettingEngine.class);
				return engine.appendOrderListInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				List<AppendTaskInfo> appendList=new ArrayList<AppendTaskInfo>();
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						AppendOrderElements appendListElement = (AppendOrderElements) result.getBody().getElements().get(0);
						if (appendListElement.getAppendList().size() <= 0) {
							appendorderText.setText("暂无追号");
							footerLayout.setVisibility(View.VISIBLE);
							appendOrderList.setPullLoadEnable(false);
						}else{
							footerLayout.setVisibility(View.GONE);
							appendOrderList.setPullLoadEnable(true);
						}
						
						appendList=appendListElement.getAppendList();
						if(appendList.size()>0){
							if(appendCurrentList.size()>0)
								appendCurrentList.addAll(appendList);
							else if(appendCurrentList.size()==0){
								appendCurrentList.clear();
								appendCurrentList.addAll(appendList);
							}else{
								appendCurrentList=new ArrayList<AppendTaskInfo>();
								appendCurrentList.addAll(appendList);
							}
						}else{
							appendorderText.setText("没有更多数据");
						}
						
					} else {
						appendList=new ArrayList<AppendTaskInfo>();
						appendorderText.setText("暂无追号");
						footerLayout.setVisibility(View.VISIBLE);
						appendOrderList.setPullLoadEnable(false);
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					appendList=new ArrayList<AppendTaskInfo>();
					appendorderText.setText("网络状态差，刷新试试");
					footerLayout.setVisibility(View.VISIBLE);
					appendOrderList.setPullLoadEnable(false);
					PromptManager.showToast(context, "网络状态差，刷新试试");
				}
				super.onPostExecute(result);
			}
		}.executeProxy(page);
	}
	
	private void getDataRequest(int page) {

		new ProgressBarAsyncTask<Integer>() {

			@Override
			protected Message doInBackground(Integer... params) {
				// 获取数据——业务的调用
				BettingEngine engine = BeanFactory.getImpl(BettingEngine.class);
				return engine.appendOrderListInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				List<AppendTaskInfo> appendList=new ArrayList<AppendTaskInfo>();
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						AppendOrderElements appendListElement = (AppendOrderElements) result.getBody().getElements().get(0);
						if (appendListElement.getAppendList().size() <= 0) {
							appendorderText.setText("暂无追号");
							footerLayout.setVisibility(View.VISIBLE);
							appendOrderList.setPullLoadEnable(false);
						}else{
							footerLayout.setVisibility(View.GONE);
							appendOrderList.setPullLoadEnable(true);
						}
						
						appendList=appendListElement.getAppendList();
						if(appendList.size()>0){
							if(appendCurrentList.size()>0)
								appendCurrentList.addAll(appendList);
							else if(appendCurrentList.size()==0){
								appendCurrentList.clear();
								appendCurrentList.addAll(appendList);
							}else{
								appendCurrentList=new ArrayList<AppendTaskInfo>();
								appendCurrentList.addAll(appendList);
							}
						}else{
							appendorderText.setText("没有更多数据");
						}
						
					} else {
						appendList=new ArrayList<AppendTaskInfo>();
						appendorderText.setText("暂无追号");
						footerLayout.setVisibility(View.VISIBLE);
						appendOrderList.setPullLoadEnable(false);
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					appendList=new ArrayList<AppendTaskInfo>();
					appendorderText.setText("网络状态差，刷新试试");
					footerLayout.setVisibility(View.VISIBLE);
					appendOrderList.setPullLoadEnable(false);
					PromptManager.showToast(context, "网络状态差，刷新试试");
				}
				super.onPostExecute(result);
			}
		}.executeProxy(page);
	}
	
	/**
	 * 访问网络的工具
	 * @author Administrator
	 * @param <Params>
	 */
	private abstract class ProgressBarAsyncTask<Params> extends AsyncTask<Params, Void, Message> {
		/**
		 * 类似与Thread.start方法 由于final修饰，无法Override，方法重命名 省略掉网络判断
		 * @param params
		 * @return
		 */
		public final AsyncTask<Params, Void, Message> executeProxy(Params... params) {
			if (NetUtil.checkNet(context)) {
				return super.execute(params);
			} else {
				PromptManager.showNoNetWork(context);
			}
			return null;
		}

	}
	
	private void onLoad() {
		appendOrderList.stopRefresh();
		appendOrderList.stopLoadMore();
		appendOrderList.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				currentPage=1;
				appendCurrentList.clear();
				getDataRequest(currentPage);
				appendAdapter.refresh(appendCurrentList);
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		currentPage=currentPage+1;
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				getDataRequest(currentPage);
				appendAdapter.refresh(appendCurrentList);
				onLoad();
			}
		}, 2000);
	}

}
