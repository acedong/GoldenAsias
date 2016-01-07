package com.goldenasia.lottery.view;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.ReportData;
import com.goldenasia.lottery.bean.SearchParam;
import com.goldenasia.lottery.engine.BettingEngine;
import com.goldenasia.lottery.net.NetUtil;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.ReportDataElement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.custom.SelectBirthday;

/**
 * 盈亏列表
 * @author Ace
 *
 */
public class Tab05Activity extends Activity{
	
	private Context context =null;
	
	private EditText begintime;
	private SelectBirthday birth;
	private EditText endtime;
	private Button button;
	
	private TextView teamRealpayment;
	private TextView teamWithdraw;
	private TextView teamBets;
	private TextView teamPoints;
	private TextView teamBonus;
	private TextView teamProfit;
	
	private TextView bets;
	private TextView points;
	private TextView bonus;
	private TextView realpayment;
	private TextView withdraw;
	private TextView profit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context =Tab05Activity.this;
		setContentView(R.layout.nb_tab_history_profit_statistics);
		
		begintime=(EditText)findViewById(R.id.nb_find_profit_statistics_begintime);
		Date as = new Date(new Date().getTime()-24*60*60*1000);
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd");
		String begintimestr = matter.format(as);
		begintime.setText(begintimestr);
		begintime.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				birth = new SelectBirthday(Tab05Activity.this);
				birth.showAtLocation(Tab05Activity.this.findViewById(R.id.root), Gravity.BOTTOM, 0, 0);
				birth.setOnBtnClick(new SelectBirthday.onBtnListenerClick() {
					
					@Override
					public void onClick(String textdate) {
						// TODO Auto-generated method stub
						begintime.setText(textdate);
					}
				});
			}
		});
		
		endtime=(EditText)findViewById(R.id.nb_find_profit_statistics_endtime);
		String endtimestr = matter.format(new Date());
		endtime.setText(endtimestr);
		endtime.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				birth = new SelectBirthday(Tab05Activity.this);
				birth.showAtLocation(Tab05Activity.this.findViewById(R.id.root), Gravity.BOTTOM, 0, 0);
				birth.setOnBtnClick(new SelectBirthday.onBtnListenerClick() {
					
					@Override
					public void onClick(String textdate) {
						// TODO Auto-generated method stub
						endtime.setText(textdate);
					}
				});
			}
		});
		button=(Button)findViewById(R.id.nb_find_statistics_button);
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDataRequest();
			}
		});
		teamRealpayment=(TextView)findViewById(R.id.nb_statistics_team_realpayment);
		teamWithdraw=(TextView)findViewById(R.id.nb_statistics_team_withdraw);
		teamBets=(TextView)findViewById(R.id.nb_statistics_team_bets);
		teamPoints=(TextView)findViewById(R.id.nb_statistics_team_points);
		teamBonus=(TextView)findViewById(R.id.nb_statistics_team_bonus);
		teamProfit=(TextView)findViewById(R.id.nb_statistics_team_profit);
		
		bets=(TextView)findViewById(R.id.nb_statistics_bets);
		points=(TextView)findViewById(R.id.nb_statistics_points);
		bonus=(TextView)findViewById(R.id.nb_statistics_bonus);
		realpayment=(TextView)findViewById(R.id.nb_statistics_realpayment);
		withdraw=(TextView)findViewById(R.id.nb_statistics_withdraw);
		profit=(TextView)findViewById(R.id.nb_statistics_profit);
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

	private void getDataRequest() {
		
		if (TextUtils.isEmpty(begintime.getText().toString())) {
			Toast.makeText(context, "开始时间不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		if (TextUtils.isEmpty(endtime.getText().toString())) {
			Toast.makeText(context, "结束时间不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		SearchParam param=new SearchParam();
		param.setCellphone("1");
		param.setStartdate(begintime.getText().toString());
		param.setEnddate(endtime.getText().toString());
		
		new ProgressBarAsyncTask<SearchParam>() {
			
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}

			@Override
			protected Message doInBackground(SearchParam... params) {
				// 获取数据——业务的调用
				BettingEngine engine = BeanFactory.getImpl(BettingEngine.class);
				return engine.getProfitStatisticsInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				// 更新界面
				PromptManager.closeProgressDialog();
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();

					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						ReportDataElement reportEle=(ReportDataElement)result.getBody().getElements().get(0);
						ReportData report=reportEle.getReport();
						
						BigDecimal paymentDd=null;
						if(report.getTeamrealpayment()!=null){
							paymentDd=new BigDecimal(report.getTeamrealpayment()); 
							paymentDd=paymentDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						}else{
							paymentDd=new BigDecimal("0.0000"); 
							paymentDd=paymentDd.setScale(4, BigDecimal.ROUND_HALF_UP);
						}
						teamRealpayment.setText(paymentDd+"");
						
						BigDecimal withdrawDd=null;
						if(report.getTeamrealwithdraw()!=null){
							withdrawDd=new BigDecimal(report.getTeamrealwithdraw()); 
							withdrawDd=withdrawDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						}else{
							withdrawDd=new BigDecimal("0.0000"); 
							withdrawDd=withdrawDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						}
						teamWithdraw.setText(withdrawDd+"");
						
						BigDecimal teamBetsDd=null;
						if(report.getTeambets()!=null){
							teamBetsDd=new BigDecimal(report.getTeambets()); 
							teamBetsDd=teamBetsDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						}else{
							teamBetsDd=new BigDecimal("0.0000"); 
							teamBetsDd=teamBetsDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						}
						teamBets.setText(teamBetsDd+"");
						
						BigDecimal teamPointsDd=null;
						if(report.getTeampoints()!=null){
							teamPointsDd=new BigDecimal(report.getTeampoints()); 
							teamPointsDd=teamPointsDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						}else{
							teamPointsDd=new BigDecimal("0.0000"); 
							teamPointsDd=teamPointsDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						}
						teamPoints.setText(teamPointsDd+"");
						
						BigDecimal teamBonusDd=null;
						if(report.getTeambonus()!=null){
							teamBonusDd=new BigDecimal(report.getTeambonus()); 
							teamBonusDd=teamBonusDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						}else{
							teamBonusDd=new BigDecimal("0.0000"); 
							teamBonusDd=teamBonusDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						}
						teamBonus.setText(teamBonusDd+"");
						
						BigDecimal teamProfitDd=null;
						if(report.getTeamprofit()!=null){
							teamProfitDd=new BigDecimal(report.getTeamprofit()); 
							teamProfitDd=teamProfitDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						}else{
							teamProfitDd=new BigDecimal("0.0000"); 
							teamProfitDd=teamProfitDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						}
						teamProfit.setText(teamProfitDd+"");
						
						
						BigDecimal betsDd=new BigDecimal(report.getBets()); 
						betsDd=betsDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
					    bets.setText(betsDd+"");
					    BigDecimal pointsDd=new BigDecimal(report.getPoints()); 
					    pointsDd=pointsDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						points.setText(pointsDd+"");
						BigDecimal bonusDd=new BigDecimal(report.getBonus()); 
						bonusDd=bonusDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						bonus.setText(bonusDd+"");
						BigDecimal realpaymentDd=new BigDecimal(report.getRealpayment()); 
						realpaymentDd=realpaymentDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						realpayment.setText(realpaymentDd+"");
						BigDecimal realwithdrawDd=new BigDecimal(report.getRealwithdraw()); 
						realwithdrawDd=realwithdrawDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						withdraw.setText(realwithdrawDd+"");
						BigDecimal profitDd=new BigDecimal(report.getProfit()); 
						profitDd=profitDd.setScale(4, BigDecimal.ROUND_HALF_UP); 
						profit.setText(profitDd+"");
						
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
						}
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					// 如何提示用户
				}

				super.onPostExecute(result);
			}
		}.executeProxy(param);
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

}