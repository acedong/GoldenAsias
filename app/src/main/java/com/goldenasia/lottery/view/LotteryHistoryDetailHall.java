package com.goldenasia.lottery.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.HistoryLottery;
import com.goldenasia.lottery.bean.IssueLast;
import com.goldenasia.lottery.engine.LotteryInfoEngine;
import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.HistoryDetailsElements;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.adapter.HistoryDetailsListViewAdapter;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.HallMiddleManager;
import com.goldenasia.lottery.view.manager.TitleManager;

public class LotteryHistoryDetailHall extends BaseUI{
	
	private ListView detailList;// 彩种的入口
	private HistoryDetailsListViewAdapter adapterDetails;
	
	private Button detailBetting;
	
	public LotteryHistoryDetailHall(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		showInMiddle = (LinearLayout) View.inflate(context,R.layout.nb_lottery_history_detail_hall, null);
		
		detailList = (ListView) findViewById(R.id.nb_history_detail_list);
		
		detailBetting = (Button)findViewById(R.id.nb_history_detail_hall_betting);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		adapterDetails = new HistoryDetailsListViewAdapter(context);
		detailList.setAdapter(adapterDetails);
		detailList.setFadingEdgeLength(0);// 删除黑边（上下）
		
		detailBetting.setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		getDataRequest();
		detailBetting.setText(bundle.getString("lotteryname")+context.getResources().getString(R.string.is_shopping_button_betting));
		TitleManager.getInstance().changeCommonTitle(bundle.getString("lotteryname")+"-"+"历史开奖");
		super.onResume();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.nb_history_detail_hall_betting:
				Bundle bundless=new Bundle();
				bundless.putString("issue", bundle.getString("issue"));
				bundless.putString("lotteryname", bundle.getString("lotteryname"));
				bundless.putInt("lotterytype", bundle.getInt("lotterytype"));
				bundless.putInt("lotteryid", bundle.getInt("lotteryid"));
				
				HallMiddleManager.getInstance().changeUI(PlaySSC.class,bundless);
			break;
		}
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
				return lottery.getSingleLotteryInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();

					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						historyChangeNotice(result.getBody().getElements().get(0));
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
					PromptManager.showToast(context, "服务器忙，请稍后重试……");
				}

				super.onPostExecute(result);
			}
		}.executeProxy(bundle.getInt("lotteryid"));

	}

	protected void historyChangeNotice(Element element) {
		HistoryDetailsElements historyElement = (HistoryDetailsElements) element;
		String lotteryid= String.valueOf(bundle.getInt("lotteryid"));
		List<IssueLast> issueLastList = historyElement.getHistoryMap().get(lotteryid);
		List<HistoryLottery> historyCodeList=new ArrayList<HistoryLottery>();
		for(int i=0; i<issueLastList.size(); i++){
			IssueLast issue=issueLastList.get(i);
			HistoryLottery hl=new HistoryLottery();
			hl.setLotteryIssue(issue.getIssue());
			hl.setLotterydigit(issue.getCode());
			hl.setSorts(issue.getSorts());
			historyCodeList.add(hl);
		}
		
		Comparator<HistoryLottery> comparator = new Comparator<HistoryLottery>() {
            public int compare(HistoryLottery s1, HistoryLottery s2) {
                // 先排彩种ID
            	if(!s1.getLotteryIssue().equals(s2.getLotteryIssue()))
            	{
            		return s2.getLotteryIssue().compareTo(s1.getLotteryIssue());
            	}else {
                    return s2.getLotteryIssue().compareTo(s1.getLotteryIssue());
                }
            	
            }
        };
        
        Collections.sort(historyCodeList,comparator);
		adapterDetails.refresh(historyCodeList);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstantValue.VIEW_HISTORY_DETAIL;
	}

}
