package com.goldenasia.lottery.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.GlobalParams;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.OrderDetails;
import com.goldenasia.lottery.engine.BettingEngine;
import com.goldenasia.lottery.engine.UserEngine;
import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.BalanceElement;
import com.goldenasia.lottery.net.protocal.element.OrderDetailsElement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.CustomDialog;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.adapter.OrderCodeAdapter;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.OrderOperation;
import com.goldenasia.lottery.view.manager.TitleManager;

public class OrderDetailsHall extends BaseUI implements OrderOperation{
	
	private TextView usernameDetail;
	private TextView cnnameDetail;
	private TextView issueDetail;
	private TextView methodnameDetail;
	private TextView modesDetail;
	private TextView totalpriceDetail;
	private TextView dypointdecDetail;
	private TextView bonusDetail;
	private TextView statusdescDetail;
	private TextView writetimeDetail;
	private GridView nocodeDetail;
	private TextView codeDetail;
	private TextView projectnoDetail;

	public OrderDetailsHall(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.nb_lottery_order_details_hall, null);
		
		usernameDetail = (TextView) findViewById(R.id.nb_detail_username);
		cnnameDetail = (TextView)findViewById(R.id.nb_detail_cnname);
		issueDetail = (TextView)findViewById(R.id.nb_detail_issue);
		methodnameDetail = (TextView)findViewById(R.id.nb_detail_methodname);
		modesDetail = (TextView)findViewById(R.id.nb_detail_mode);
		totalpriceDetail = (TextView)findViewById(R.id.nb_detail_totalprice);
		dypointdecDetail = (TextView)findViewById(R.id.nb_detail_dypointdec);
		bonusDetail = (TextView)findViewById(R.id.nb_detail_bonus);
		statusdescDetail = (TextView) findViewById(R.id.nb_detail_status);
		writetimeDetail = (TextView)findViewById(R.id.nb_detail_writetime);
		nocodeDetail = (GridView)findViewById(R.id.nb_detail_nocode);
		codeDetail = (TextView)findViewById(R.id.nb_detail_code);
		projectnoDetail = (TextView)findViewById(R.id.nb_detail_projectno);
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onResume() {
		getDataRequest();
		super.onResume();
	}

	/**
	 * 获取请求信息
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

				BettingEngine engine = BeanFactory.getImpl(BettingEngine.class);
				return engine.getOrderDetailsInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();

					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						OrderDetailsChangeNotice(result.getBody().getElements().get(0));
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
						}
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					PromptManager.showToast(context, "服务器忙，请稍后重试……");
				}

				super.onPostExecute(result);
			}
		}.executeProxy(bundle.getInt("detailsid"));

	}
	
	private OrderDetails orderDetails; 
	/**
	 * 修改界面提示信息
	 * 
	 * @param element
	 */
	@SuppressWarnings("unused")
	protected void OrderDetailsChangeNotice(Element element) {
		OrderDetailsElement detailsElement = (OrderDetailsElement) element;

		orderDetails = detailsElement.getOrderDetails();
		
		usernameDetail.setText(orderDetails.getUsername());
		cnnameDetail.setText(orderDetails.getCnname());
		issueDetail.setText(orderDetails.getIssue());
		methodnameDetail.setText(orderDetails.getMethodname());
		modesDetail.setText(orderDetails.getModes());
		totalpriceDetail.setText(orderDetails.getTotalprice());
		dypointdecDetail.setText(orderDetails.getDypointdec());
		bonusDetail.setText(orderDetails.getBonus());
		statusdescDetail.setText(orderDetails.getStatusdesc());
		writetimeDetail.setText(orderDetails.getWritetime());
		
		String[] item=null;
		String digit=orderDetails.getNocode();
		if(!digit.equals("")||null!=digit){
			if(digit.contains(" ")){
				item = digit.split(" "); 
			}else{
				item = digit.split(""); 
				StringBuffer sb = new StringBuffer();
		        for(int i=0; i<item.length; i++) {
		            if("".equals(item[i])) {
		                continue;
		            }
		            sb.append(item[i]);
		            if(i != item.length - 1) {
		                sb.append(";");
		            }
		        }
		        //用String的split方法分割，得到数组
		        item = sb.toString().split(";");
			}
		}
		if(digit.equals("")||null==digit) {
			item=new String[5];
			for(int i=0; i<5; i++){
				item[i]="-";
			}
		}
		OrderCodeAdapter adapter = new OrderCodeAdapter(context, item); 
		nocodeDetail.setSelector(new ColorDrawable(Color.TRANSPARENT));
		nocodeDetail.setAdapter(adapter);  
		
		codeDetail.setText(orderDetails.getCode());
		projectnoDetail.setText(orderDetails.getProjectno());
		
		if(orderDetails.getStatusdesc().equals("未开奖")){
			TitleManager.getInstance().changeOrderCancelTitleInhibit();
		}else if (orderDetails.getStatusdesc().equals("未中奖")) {
			TitleManager.getInstance().changeOrderCancelTitleEnable();
		}
//		TitleManager.getInstance().changeOrderCancelTitle(title);
	}

	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstantValue.VIEW_ORDERDETAILS;
	}

	@Override
	public void rescind() {
		// TODO Auto-generated method stub
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage("您确定撤单吗？");
		builder.setTitle("温馨提示");
		builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				getCancelRequest(dialog);
			}
		});

		builder.setNegativeButton("否", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
		
	}

	
	public void getCancelRequest(final DialogInterface dialog){
		new MyHttpTask<Integer>() {
			
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}

			@Override
			protected Message doInBackground(Integer... params) {

				BettingEngine engineBetting = BeanFactory.getImpl(BettingEngine.class);
				Message cancelOrder = engineBetting.getOrderCancel(params[0]);

				if (cancelOrder != null) {
					Oelement oelement = cancelOrder.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						// 成功了获取余额
						UserEngine engine = BeanFactory.getImpl(UserEngine.class);
						Message balance = engine.getBalance();
						if (balance != null) {
							oelement = balance.getBody().getOelement();
							if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
								BalanceElement element = (BalanceElement) balance.getBody().getElements().get(0);
								String money = element.getInvestvalues();
								GlobalParams.MONEY = Double.parseDouble(money);
								
								return balance;
							}
						}
					} else {
						return cancelOrder;
					}
				}

				return null;
			}

			@Override
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				if (result != null) {
					// 界面跳转
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						dialog.dismiss();
						TitleManager.getInstance().changeOrderCancelTitleEnable();
						PromptManager.showToast(context,"订单撤消成功");
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
						}
					}
					// HallMiddleManager.getInstance().goBack();
				} else {
					PromptManager.showToast(context, "网络状态差，请重试！");
				}
				super.onPostExecute(result);
			}

		}.executeProxy(Integer.parseInt(orderDetails.getProjectid()));
	}
}
