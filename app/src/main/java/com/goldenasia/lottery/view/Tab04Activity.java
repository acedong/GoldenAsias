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
import com.goldenasia.lottery.bean.AccountChangeInfo;
import com.goldenasia.lottery.engine.BettingEngine;
import com.goldenasia.lottery.net.NetUtil;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.AccountChangeElement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.adapter.AccountChangeListAdapter;
import com.goldenasia.lottery.view.controls.RefreshLoadListView;
import com.goldenasia.lottery.view.controls.RefreshLoadListView.IXListViewListener;

/**
 * 帐户流水
 * 
 * @author Ace
 */
public class Tab04Activity extends Activity implements IXListViewListener {

	private Context context = null;

	private RefreshLoadListView accountChangeList;
	private LinearLayout footerLayout;
	private TextView accountChangeText;
	private AccountChangeListAdapter accountChangeAdapter;

	private List<AccountChangeInfo> accountCurrentList = new ArrayList<AccountChangeInfo>();

	private Handler mHandler;
	private int currentPage = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = Tab04Activity.this;

		setContentView(R.layout.nb_tab_history_billing);

		accountChangeList = (RefreshLoadListView) findViewById(R.id.nb_history_billing_order_list);
		accountChangeList.setPullRefreshEnable(true);
		accountChangeList.setRefreshLoadListViewListener(this);
		accountChangeList.setFadingEdgeLength(0);// 删除黑边（上下）

		View emptyview = View.inflate(this, R.layout.nb_tab_history_billing_emptyview, null);

		accountChangeText = (TextView) emptyview
				.findViewById(R.id.nb_history_billing_header_info);
		footerLayout = (LinearLayout) emptyview
				.findViewById(R.id.nb_history_billing_header);

		accountChangeAdapter = new AccountChangeListAdapter(context);
		accountChangeList.setAdapter(accountChangeAdapter);
		accountChangeList.addFooterView(emptyview);

		mHandler = new Handler();
	}

	@Override
	public void onResume() {
		
		if(GlobalParams.PARENTID.equals("0")){
			onRefresh();
		}else{
			accountCurrentList.clear();
			currentPage = 1;
			getDataRequestLoding(currentPage);
			accountChangeAdapter.refresh(accountCurrentList);
		}
		super.onResume();
	}
	
	private void getDataRequestLoding(int page) {

		new ProgressBarAsyncTask<Integer>() {

			@Override
			protected Message doInBackground(Integer... params) {
				// 获取数据——业务的调用
				BettingEngine engine = BeanFactory.getImpl(BettingEngine.class);
				return engine.getBillingListInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				List<AccountChangeInfo> accountList = new ArrayList<AccountChangeInfo>();
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {

						AccountChangeElement accountListElement = (AccountChangeElement) result.getBody().getElements().get(0);
						if (accountListElement.getAccountChangeList().size() <= 0) {
							accountChangeText.setText("暂无账变");
							footerLayout.setVisibility(View.VISIBLE);
							accountChangeList.setPullLoadEnable(false);
						}else{
							footerLayout.setVisibility(View.GONE);
							accountChangeList.setPullLoadEnable(true);
						}
						
						accountList=accountListElement.getAccountChangeList();
						if(accountList.size()>0){
							if(accountCurrentList.size()>0)
								accountCurrentList.addAll(accountList);
							else if(accountCurrentList.size()==0){
								accountCurrentList.clear();
								accountCurrentList.addAll(accountList);
							}else{
								accountCurrentList=new ArrayList<AccountChangeInfo>();
								accountCurrentList.addAll(accountList);
							}
						}else{
							accountChangeText.setText("没有更多数据");
						}
						
					} else {
						accountList=new ArrayList<AccountChangeInfo>();
						accountChangeText.setText("暂无账变");
						footerLayout.setVisibility(View.VISIBLE);
						accountChangeList.setPullLoadEnable(false);
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}
					}
					
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					accountList=new ArrayList<AccountChangeInfo>();
					accountChangeText.setText("网络状态差，刷新试试");
					footerLayout.setVisibility(View.VISIBLE);
					accountChangeList.setPullLoadEnable(false);
					PromptManager.showToast(context, "网络状态差，刷新试试");
				}

				super.onPostExecute(result);
			}
		}.executeProxy(page);
	}

	private void getDataRequest(int page) {

		new ProgressBarAsyncTask<Integer>() {
			
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}
			
			@Override
			protected Message doInBackground(Integer... params) {
				// 获取数据——业务的调用
				BettingEngine engine = BeanFactory.getImpl(BettingEngine.class);
				return engine.getBillingListInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				List<AccountChangeInfo> accountList = new ArrayList<AccountChangeInfo>();
				// 更新界面
				PromptManager.closeProgressDialog();
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {

						AccountChangeElement accountListElement = (AccountChangeElement) result.getBody().getElements().get(0);
						if (accountListElement.getAccountChangeList().size() <= 0) {
							accountChangeText.setText("暂无账变");
							footerLayout.setVisibility(View.VISIBLE);
							accountChangeList.setPullLoadEnable(false);
						}else{
							footerLayout.setVisibility(View.GONE);
							accountChangeList.setPullLoadEnable(true);
						}
						
						accountList=accountListElement.getAccountChangeList();
						if(accountList.size()>0){
							if(accountCurrentList.size()>0)
								accountCurrentList.addAll(accountList);
							else if(accountCurrentList.size()==0){
								accountCurrentList.clear();
								accountCurrentList.addAll(accountList);
							}else{
								accountCurrentList=new ArrayList<AccountChangeInfo>();
								accountCurrentList.addAll(accountList);
							}
						}else{
							accountChangeText.setText("没有更多数据");
						}
						
					} else {
						accountList=new ArrayList<AccountChangeInfo>();
						accountChangeText.setText("暂无账变");
						footerLayout.setVisibility(View.VISIBLE);
						accountChangeList.setPullLoadEnable(false);
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}
					}
					
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					accountList=new ArrayList<AccountChangeInfo>();
					accountChangeText.setText("网络状态差，刷新试试");
					footerLayout.setVisibility(View.VISIBLE);
					accountChangeList.setPullLoadEnable(false);
					PromptManager.showToast(context, "网络状态差，刷新试试");
				}

				super.onPostExecute(result);
			}
		}.executeProxy(page);
	}

	/**
	 * 访问网络的工具
	 * 
	 * @author Administrator
	 * @param <Params>
	 */
	private abstract class ProgressBarAsyncTask<Params> extends
			AsyncTask<Params, Void, Message> {
		/**
		 * 类似与Thread.start方法 由于final修饰，无法Override，方法重命名 省略掉网络判断
		 * 
		 * @param params
		 * @return
		 */
		public final AsyncTask<Params, Void, Message> executeProxy(
				Params... params) {
			if (NetUtil.checkNet(context)) {
				return super.execute(params);
			} else {
				PromptManager.showNoNetWork(context);
			}
			return null;
		}

	}

	private void onLoad() {
		accountChangeList.stopRefresh();
		accountChangeList.stopLoadMore();
		accountChangeList.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				currentPage = 1;
				accountCurrentList.clear();
				getDataRequest(currentPage);
				accountChangeAdapter.refresh(accountCurrentList);
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		currentPage = currentPage + 1;
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				getDataRequest(currentPage);
				accountChangeAdapter.refresh(accountCurrentList);
				onLoad();
			}
		}, 2000);
	}

}
