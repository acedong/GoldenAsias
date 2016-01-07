package com.goldenasia.lottery.view.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.InfoList;
import com.goldenasia.lottery.bean.IssueAll;
import com.goldenasia.lottery.bean.IssueSales;
import com.goldenasia.lottery.bean.LotteryInfo;
import com.goldenasia.lottery.bean.RecordTime;
import com.goldenasia.lottery.engine.CommonInfoEngine;
import com.goldenasia.lottery.net.SafeHttpTask;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.SpeciesListElements;
import com.goldenasia.lottery.net.protocal.element.TaskIssueInfoElement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.PlaySSC;
import com.goldenasia.lottery.view.PlaySYFive;
import com.goldenasia.lottery.view.controls.RushBuyCountDownTimerView;
import com.goldenasia.lottery.view.manager.HallMiddleManager;

/**
 * 彩票种类 列表
 * @author Ace
 */

public class CategoryListViewAdapter extends BaseAdapter {

	private Context context; // 运行上下文
	private SpeciesListElements specieslist=new SpeciesListElements(); // 彩票种类信息信息集合
	private LayoutInflater listContainer; // 视图容器
	private Typeface fontIco=null;
	private OnUpdateDataListener onUpdateData;
	// 依据item的layout
	public class ViewHolder {
		ImageView logo;
		TextView title;
		TextView prompt;
		RushBuyCountDownTimerView salestime;
//		Anticlockwise salestime;
		TextView summary;
		Button bet;
	}

	public CategoryListViewAdapter(Context context) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.fontIco=Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
	}

	@Override
	public int getCount() {
		return specieslist.getCatalogueList().size();
	}

	@Override
	public Object getItem(int position) {
		return specieslist.getCatalogueList().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void refresh(SpeciesListElements specieslist) {
		this.specieslist = specieslist;
		notifyDataSetChanged();
	}
	
	public OnUpdateDataListener getOnUpdateData() {
		return onUpdateData;
	}

	public void setOnUpdateData(OnUpdateDataListener onUpdateData) {
		this.onUpdateData = onUpdateData;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LotteryInfo lotteryInfo;
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = listContainer.inflate(R.layout.nb_lottery_home_hall_lottery_species_item, null);
			holder.logo = (ImageView) convertView.findViewById(R.id.nb_hall_lottery_logo); // 玩法logo
			holder.title = (TextView) convertView.findViewById(R.id.nb_hall_lottery_title);
			holder.prompt = (TextView) convertView.findViewById(R.id.nb_hall_lottery_prompt);
			holder.salestime = (RushBuyCountDownTimerView)convertView.findViewById(R.id.nb_lottery_hall_sales_time);
			holder.salestime.setTag(position);
			holder.summary = (TextView) convertView.findViewById(R.id.nb_hall_lottery_summary);
			holder.summary.setTag(position);
			holder.bet = (Button) convertView.findViewById(R.id.nb_hall_lottery_bet);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			holder.salestime.setTag(position);
			holder.summary.setTag(position);
		}
		
		lotteryInfo = specieslist.getCatalogueList().get(position);
		IssueAll issueAllMap= specieslist.getIssueAllMap().get(lotteryInfo.getLotteryId());
		if(issueAllMap!=null){
			IssueSales issueSales=issueAllMap.getIssueSales();
			
			int lotteryid = Integer.parseInt(lotteryInfo.getLotteryId());
			String issue =issueSales.getIssue(); //currIssue.getCurrentissue().getIssue();
			issue = issue.substring(6,issue.length());
			// 第ISSUE期 还有TIME停售
			RecordTime timeRecord = specieslist.getSaleendTime().get(lotteryInfo.getLotteryId());
			if(timeRecord.getMinute()>0||timeRecord.getSecond()>0){
				holder.salestime.setTime(timeRecord.getHour(),timeRecord.getMinute(),timeRecord.getSecond());
				
				String salestext = context.getResources().getString(R.string.is_hall_common_summary);
				salestext = StringUtils.replaceEach(salestext,new String[] { "ISSUE" ,"WAIT" }, new String[] { issue,"销售"});
				
				SpannableStringBuilder salesstyle=null;  
				int fstart=salestext.indexOf(issue);
		        int fend=fstart+issue.length();
		        salesstyle=new SpannableStringBuilder(salestext); 
		        salesstyle.setSpan(new ForegroundColorSpan(Color.RED),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); 
				
				holder.summary.setText(salesstyle);
			}else{
				timeRecord = specieslist.getOpenTime().get(lotteryInfo.getLotteryId());
				holder.salestime.setTime(timeRecord.getHour(), timeRecord.getMinute(),timeRecord.getSecond());
				
				String salestext = context.getResources().getString(R.string.is_hall_common_summary);
				salestext = StringUtils.replaceEach(salestext,new String[] { "ISSUE" ,"WAIT" }, new String[] { issue,"开奖"});
				
				SpannableStringBuilder salesstyle=null;  
				int fstart=salestext.indexOf(issue);
		        int fend=fstart+issue.length();
		        salesstyle=new SpannableStringBuilder(salestext); 
		        salesstyle.setSpan(new ForegroundColorSpan(Color.RED),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); 
				
				holder.summary.setText(salesstyle);
			}
//			holder.salestime.setOnTimeCompleteListener(new Anticlockwise.OnTimeCompleteListener()
//			{
//				@Override
//				public void onTimeComplete(View v) {
//					getCurrentIssueInfo(holder,Integer.parseInt(lotteryInfo.getLotteryId()));
//		          }
//		    });
//			holder.salestime.setTimeFormat("hh:mm:ss");
			
			holder.salestime.setOncountdown(new RushBuyCountDownTimerView.OnUpdateCountdownListener() {
				
				@Override
				public void onUpdateCountdown() {
					// TODO Auto-generated method stub
					getCurrentIssueInfo(holder,Integer.parseInt(lotteryInfo.getLotteryId()));
				}

				@Override
				public void onUpdateRecordTime(boolean hourFlag,
						boolean minuteFlag, boolean secondFlag) {
					// TODO Auto-generated method stub
					RecordTime timeSaleend=specieslist.getSaleendTime().get(lotteryInfo.getLotteryId());
					RecordTime timeOpen=specieslist.getOpenTime().get(lotteryInfo.getLotteryId());
					if(hourFlag){
						timeSaleend.setHour(timeSaleend.getHour()-1);
						timeOpen.setHour(timeOpen.getHour()-1);
					}else if(minuteFlag){
						timeSaleend.setMinute(timeSaleend.getMinute()-1);
						timeOpen.setMinute(timeOpen.getMinute()-1);
					}else if(secondFlag){
						timeSaleend.setSecond(timeSaleend.getSecond()-1);
						timeOpen.setSecond(timeOpen.getSecond()-1);
					}
					specieslist.getSaleendTime().put(lotteryInfo.getLotteryId(), timeSaleend);
					specieslist.getOpenTime().put(lotteryInfo.getLotteryId(), timeOpen);
					onUpdateData.onUpdateData(specieslist);
				}

			});
			holder.salestime.start();
		
			holder.logo.setImageResource(InfoList.gatheringLogo(lotteryInfo.getLotteryName()));
			holder.title.setText(lotteryInfo.getLotteryName());
			holder.prompt.setText(InfoList.gatheringPrompt(lotteryInfo.getLotteryName()));
	
			String targetIco=context.getResources().getString(R.string.fa_chevron_right);
			holder.bet.setId(lotteryid);
			holder.bet.setText(targetIco);
			holder.bet.setTypeface(fontIco);
			holder.bet.setTextSize(22);
			holder.bet.setTextColor(context.getResources().getColor(R.color.slategray));
			
			holder.bet.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					RequestData(lotteryInfo,String.valueOf(v.getId()));
				}
			});
			convertView.setId(lotteryid);
			convertView.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					RequestData(lotteryInfo,String.valueOf(v.getId()));
				}
	
			});
		}else{
			int lotteryid = Integer.parseInt(lotteryInfo.getLotteryId());
			String issue="---";
//			holder.salestime.initTime(0, 0);
			holder.salestime.setTime(0, 0, 0);
			
			String salestext = context.getResources().getString(R.string.is_hall_common_summary);
			salestext = StringUtils.replaceEach(salestext,new String[] { "ISSUE" ,"WAIT" }, new String[] { issue,"销售"});
			
			SpannableStringBuilder salesstyle=null;  
			int fstart=salestext.indexOf(issue);
	        int fend=fstart+issue.length();
	        salesstyle=new SpannableStringBuilder(salestext); 
	        salesstyle.setSpan(new ForegroundColorSpan(Color.RED),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); 
			
	        holder.salestime.stop();
			
			holder.logo.setImageResource(InfoList.gatheringLogo(lotteryInfo.getLotteryName()));
			holder.title.setText(lotteryInfo.getLotteryName());
			holder.prompt.setText(InfoList.gatheringPrompt(lotteryInfo.getLotteryName()));
	        
			holder.summary.setText(salesstyle);
			
			String targetIco=context.getResources().getString(R.string.fa_chevron_right);
			holder.bet.setId(lotteryid);
			holder.bet.setText(targetIco);
			holder.bet.setTypeface(fontIco);
			holder.bet.setTextSize(22);
			holder.bet.setTextColor(context.getResources().getColor(R.color.slategray));
			
			holder.bet.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					PromptManager.showNotTitleDialog(context, "数据读取中，请稍后再试。");
				}
			});
		}
		return convertView;
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

	
	/**
	 * 请求数据
	 */
	private void RequestData(LotteryInfo info,String id) {
		Bundle bundle = new Bundle();
		IssueAll issueAllMap= specieslist.getIssueAllMap().get(id);
		IssueSales issueSales=issueAllMap.getIssueSales();
		if(Integer.parseInt(info.getLotterytype()) ==0){
			bundle.putString("issue", issueSales.getIssue());
			bundle.putString("lotteryname", info.getLotteryName());
			bundle.putInt("lotterytype", Integer.parseInt(info.getLotterytype()));
			bundle.putInt("lotteryid", Integer.parseInt(info.getLotteryId()));
			// if(ShoppingCart.getInstance().getIssuesnumbers()>=1)
			// HallMiddleManager.getInstance().changeUI(Shopping.class,bundle);
			// else
			HallMiddleManager.getInstance().changeUI(PlaySSC.class,bundle);
		}else if(Integer.parseInt(info.getLotterytype())==2)
		{
			bundle.putString("issue", issueSales.getIssue());
			bundle.putString("lotteryname", info.getLotteryName());
			bundle.putInt("lotterytype", Integer.parseInt(info.getLotterytype()));
			bundle.putInt("lotteryid", Integer.parseInt(info.getLotteryId()));
			// if(ShoppingCart.getInstance().getIssuesnumbers()>=1)
			// HallMiddleManager.getInstance().changeUI(Shopping.class,bundle);
			// else
			HallMiddleManager.getInstance().changeUI(PlaySYFive.class,bundle);
		}
	}
	
	private void getCurrentIssueInfo(ViewHolder mHolder,final int lotteryId) {
		final ViewHolder holder = mHolder;
		new SafeHttpTask<Integer>(context) {
			@Override
			protected Message doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				CommonInfoEngine engine = BeanFactory.getImpl(CommonInfoEngine.class);
				return engine.getCurrentIssueInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						TaskIssueInfoElement element = (TaskIssueInfoElement) result.getBody().getElements().get(0);
						Map<String, RecordTime> saleendTimeMap=new HashMap<String, RecordTime>();
						Map<String, RecordTime> openTimeMap=new HashMap<String, RecordTime>();
						String key=""; 
						RecordTime saleendTime=new RecordTime(),openTime=new RecordTime();
						for(Map.Entry<String, IssueAll> entry:element.getIssueAllMap().entrySet()){ 
							IssueAll issueall=entry.getValue();
							IssueSales issueSales=issueall.getIssueSales();
							key=entry.getKey();
							saleendTime=getLasttime(issueSales.getCurrenttime(),issueSales.getSaleend());
							openTime=getLasttime(issueSales.getCurrenttime(),issueSales.getOpentime());
							saleendTimeMap.put(entry.getKey(), getLasttime(issueSales.getCurrenttime(),issueSales.getSaleend()));
							openTimeMap.put(entry.getKey(), getLasttime(issueSales.getCurrenttime(),issueSales.getOpentime()));
						} 
						element.setSaleendTime(saleendTimeMap);
						element.setOpenTime(openTimeMap);
						specieslist.getSaleendTime().put(key, saleendTime);
						specieslist.getOpenTime().put(key, openTime);
						onUpdateData.onUpdateData(specieslist);
						
						IssueAll currentIssueAll=element.getIssueAllMap().get(String.valueOf(lotteryId)); 
						
						IssueSales currentIssueSales = currentIssueAll.getIssueSales();
						String issue =currentIssueSales.getIssue(); //currIssue.getCurrentissue().getIssue();
						issue = issue.substring(6,issue.length());
						// 第ISSUE期 还有TIME停售
						
						RecordTime timeRecord = element.getSaleendTime().get(String.valueOf(lotteryId));
						if(timeRecord.getMinute()>0||timeRecord.getSecond()>0){
							holder.salestime.setTime(timeRecord.getHour(),timeRecord.getMinute(),timeRecord.getSecond());
							
							String salestext = context.getResources().getString(R.string.is_hall_common_summary);
							salestext = StringUtils.replaceEach(salestext,new String[] { "ISSUE" ,"WAIT" }, new String[] { issue,"销售"});
							
							SpannableStringBuilder salesstyle=null;  
							int fstart=salestext.indexOf(issue);
					        int fend=fstart+issue.length();
					        salesstyle=new SpannableStringBuilder(salestext); 
					        salesstyle.setSpan(new ForegroundColorSpan(Color.RED),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); 
							
							holder.summary.setText(salesstyle);
						}else{
							timeRecord = element.getOpenTime().get(String.valueOf(lotteryId));
							holder.salestime.setTime(timeRecord.getHour(),timeRecord.getMinute(),timeRecord.getSecond());
							
							String salestext = context.getResources().getString(R.string.is_hall_common_summary);
							salestext = StringUtils.replaceEach(salestext,new String[] { "ISSUE" ,"WAIT" }, new String[] { issue,"开奖"});
							
							SpannableStringBuilder salesstyle=null;  
							int fstart=salestext.indexOf(issue);
					        int fend=fstart+issue.length();
					        salesstyle=new SpannableStringBuilder(salestext); 
					        salesstyle.setSpan(new ForegroundColorSpan(Color.RED),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); 
							
							holder.summary.setText(salesstyle);
						}
//						holder.salestime.setTimeFormat("hh:mm:ss");
						holder.salestime.start();
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
						}
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					getCurrentIssueInfo(holder,lotteryId);
				}

				super.onPostExecute(result);

			}
		}.executeProxy(lotteryId);
	}
	
	public interface OnUpdateDataListener{
		/**
		 * 刷新数据
		 * @param v
		 * @param position
		 */
		public void onUpdateData(SpeciesListElements specieslist);
	}
	
}
