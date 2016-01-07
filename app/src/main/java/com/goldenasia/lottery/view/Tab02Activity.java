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
import com.goldenasia.lottery.engine.BettingEngine;
import com.goldenasia.lottery.net.NetUtil;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.OrderElement;
import com.goldenasia.lottery.net.protocal.element.OrderListElements;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.adapter.BettingOrderListAdapter;
import com.goldenasia.lottery.view.controls.RefreshLoadListView;
import com.goldenasia.lottery.view.controls.RefreshLoadListView.IXListViewListener;

/**
 * 投注历史
 * 
 * @author Ace
 */
public class Tab02Activity extends Activity implements IXListViewListener {
	private RefreshLoadListView bettingOrderView;
	private BettingOrderListAdapter bettingOrderAdapter;
	private LinearLayout footerLayout;
	private TextView bettingorderText;

	private Context context = null;
	private int currentPage = 1;
	private Handler mHandler;

	private List<OrderElement> orderCurrentList = new ArrayList<OrderElement>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		context = Tab02Activity.this;

		setContentView(R.layout.nb_tab_history_betting);
		bettingOrderView = (RefreshLoadListView) findViewById(R.id.nb_history_betting_order_list);
		bettingOrderView.setPullRefreshEnable(true);
		bettingOrderView.setRefreshLoadListViewListener(this);
		bettingOrderView.setFadingEdgeLength(0);// 删除黑边（上下）

		View emptyview = View.inflate(this,
				R.layout.nb_tab_history_bettingorder_emptyview, null);
		bettingorderText = (TextView) emptyview
				.findViewById(R.id.nb_bettingorder_header_info);
		footerLayout = (LinearLayout) emptyview
				.findViewById(R.id.nb_bettingorder_header);
		bettingOrderView.addFooterView(emptyview);

		bettingOrderAdapter = new BettingOrderListAdapter(context);
		bettingOrderView.setAdapter(bettingOrderAdapter);

		mHandler = new Handler();
	}

	@Override
	public void onResume() {
		if (!GlobalParams.PARENTID.equals("0")) {
			onRefresh();
		}
		super.onResume();
	}

	private void getDataRequest(int page) {

		new ProgressBarAsyncTask<Integer>() {

			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context, "");
				super.onPreExecute();
			}

			@Override
			protected Message doInBackground(Integer... params) {
				// 获取数据——业务的调用
				BettingEngine engine = BeanFactory.getImpl(BettingEngine.class);
				return engine.bettingOrderListInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				List<OrderElement> orderList = new ArrayList<OrderElement>();
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();

					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {

						OrderListElements orderListElement = (OrderListElements) result
								.getBody().getElements().get(0);
						if (orderListElement.getOrderElements()
								.getOrderElementList().size() <= 0) {
							bettingorderText.setText("暂无订单");
							footerLayout.setVisibility(View.VISIBLE);
							bettingOrderView.setPullLoadEnable(false);
						} else {
							footerLayout.setVisibility(View.GONE);
							bettingOrderView.setPullLoadEnable(true);
						}
						orderList = orderListElement.getOrderElements()
								.getOrderElementList();
						if (orderList.size() > 0) {
							if (orderCurrentList.size() > 0)
								orderCurrentList.addAll(orderList);
							else if (orderCurrentList.size() == 0) {
								orderCurrentList.clear();
								orderCurrentList.addAll(orderList);
							} else {
								orderCurrentList = new ArrayList<OrderElement>();
								orderCurrentList.addAll(orderList);
							}
						} else {
							bettingorderText.setText("没有更多数据");
						}

					} else {
						orderList = new ArrayList<OrderElement>();
						bettingorderText.setText("暂无订单");
						footerLayout.setVisibility(View.VISIBLE);
						bettingOrderView.setPullLoadEnable(false);
						if (oelement.getErrorcode().equals("255")) {
							PromptManager.showRelogin(context,
									oelement.getErrormsg(),
									oelement.getErrorcode());
						}
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					orderList = new ArrayList<OrderElement>();
					bettingorderText.setText("网络状态差，刷新试试");
					footerLayout.setVisibility(View.VISIBLE);
					bettingOrderView.setPullLoadEnable(false);
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
		bettingOrderView.stopRefresh();
		bettingOrderView.stopLoadMore();
		bettingOrderView.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				currentPage = 1;
				orderCurrentList.clear();
				getDataRequest(currentPage);
				bettingOrderAdapter.refresh(orderCurrentList);
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
				bettingOrderAdapter.refresh(orderCurrentList);
				onLoad();
			}
		}, 2000);
	}

}
