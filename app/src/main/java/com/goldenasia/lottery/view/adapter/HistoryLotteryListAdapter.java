package com.goldenasia.lottery.view.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.Category;
import com.goldenasia.lottery.bean.HistoryLottery;
import com.goldenasia.lottery.view.LotteryHistoryDetailHall;
import com.goldenasia.lottery.view.custom.MyListView;
import com.goldenasia.lottery.view.manager.HallMiddleManager;

public class HistoryLotteryListAdapter extends BaseAdapter{

	private Context context;								//运行上下文   
    private List<Category> categorylist=new ArrayList<Category>();    			//彩票种类最近开奖信息集合 
    private LayoutInflater listContainer;           		//视图容器
    private Typeface fontIco=null;
	// 依据item的layout
    public final class ViewHolder {
		TextView title;
		TextView issue;
		GridView codegridview;
		RelativeLayout latestLotteryItem;
		LinearLayout lotteryItem;
		Button historyBet;
		MyListView otherList;
		Button btnopen;
		Button btnbutting;
		Button btnrandom;
	}
    
	public HistoryLotteryListAdapter(Context context) {
        this.context = context;
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文  
        this.fontIco=Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
    }
	
	@Override
	public int getCount() {
		if (null != categorylist&&categorylist.size()>0) {
            return this.categorylist.size();
        }
        return 0;
	}

	@Override
	public Object getItem(int position) {
        if (null !=  categorylist && categorylist.size()>0) {
            return  categorylist.get(position);
        }
        return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void refresh(List<Category> historylist) {
		this.categorylist = historylist;
		notifyDataSetChanged();
	}

	private String name="";
	private String issue="";
	private String[] issuedigits;
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		if(getCount() == 0){  
            return null;  
        }  
		ViewHolder holder = null;
		Category categoryLottery=categorylist.get(position);
		HistoryLottery historyLottery=categoryLottery.getLotteryTitle();
		
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = listContainer.inflate(R.layout.nb_lottery_history_hall_list_item, null);
			
			holder.title = (TextView) convertView.findViewById(R.id.nb_hall_lottery_history_title);
			holder.issue = (TextView) convertView.findViewById(R.id.nb_hall_lottery_history_issue);
			holder.codegridview = (GridView) convertView.findViewById(R.id.nb_hall_lottery_history_code);
			holder.latestLotteryItem = (RelativeLayout) convertView.findViewById(R.id.nb_hall_latest_lottery_history_item);
			holder.lotteryItem = (LinearLayout) convertView.findViewById(R.id.nb_hall_lottery_history_item);
			holder.otherList=(MyListView) convertView.findViewById(R.id.nb_lottery_other_list);
			holder.historyBet=(Button) convertView.findViewById(R.id.nb_hall_lottery_history_bet);
			
			holder.btnopen=(Button)convertView.findViewById(R.id.nb_history_btnopen);
			holder.btnbutting=(Button)convertView.findViewById(R.id.nb_history_btnbutting);
			holder.btnrandom=(Button)convertView.findViewById(R.id.nb_history_btnrandom);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		name=historyLottery.getLotteryName();
		issue=historyLottery.getLotteryIssue();
		
		String text = context.getResources().getString(R.string.is_hall_common_issue);
		text = StringUtils.replaceEach(text, new String[] {"ISSUE"}, new String[] { issue });
		LotteryCodeAdapter adapter;
		String digit=historyLottery.getLotterydigit();
		if(digit.indexOf(" ") != -1){
			String[] item = digit.split(" ");
			adapter = new LotteryCodeAdapter(context, item); 
		}else{
			String[] item = digit.split(""); 
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
	        adapter = new LotteryCodeAdapter(context, item);
		}
		
		holder.title.setText(historyLottery.getLotteryName());
		holder.issue.setText(text);
		
		holder.codegridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		holder.codegridview.setAdapter(adapter);  
		issuedigits =new String[3];
		for(int i=0;i<categoryLottery.getmCategoryItem().size();i++)
		{
			HistoryLottery history=categoryLottery.getmCategoryItem().get(i);
			if(i<3)
				issuedigits[i]=history.getLotteryIssue()+"-"+history.getLotterydigit();
		}
		
		HistoryLotteryOtherListAdapter otherHistory=new HistoryLotteryOtherListAdapter(context,issuedigits);
		holder.otherList.setAdapter(otherHistory);
		
		String btnopenIco=context.getResources().getString(R.string.fa_ellipsis_h);
		holder.btnopen.setText(btnopenIco+" 详情");
		holder.btnopen.setTypeface(fontIco);
		holder.btnopen.setTextSize(12);
		holder.btnopen.setTextColor(context.getResources().getColor(R.color.gray));
		
		String btnbuttingIco=context.getResources().getString(R.string.fa_tasks);
		holder.btnbutting.setText(btnbuttingIco+" 投注");
		holder.btnbutting.setTypeface(fontIco);
		holder.btnbutting.setTextSize(12);
		holder.btnbutting.setTextColor(context.getResources().getColor(R.color.gray));
		
		String btnrandomIco=context.getResources().getString(R.string.fa_random);
		holder.btnrandom.setText(btnrandomIco+" 随机");
		holder.btnrandom.setTypeface(fontIco);
		holder.btnrandom.setTextSize(12);
		holder.btnrandom.setTextColor(context.getResources().getColor(R.color.gray));
		
		String targetIco=context.getResources().getString(R.string.fa_chevron_down);
		holder.historyBet.setText(targetIco);
		holder.historyBet.setTypeface(fontIco);
		holder.historyBet.setTextSize(18);
		holder.historyBet.setTextColor(context.getResources().getColor(R.color.gray));
		
		holder.lotteryItem.setId(Integer.parseInt(historyLottery.getLotteryId()));
		holder.lotteryItem.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle=new Bundle();
				bundle.putInt("lotteryid", v.getId());
				bundle.putString("lotteryname", name);
				bundle.putString("issue", issue);
				HallMiddleManager.getInstance().changeUI(LotteryHistoryDetailHall.class,bundle);
			}
		});
		
		holder.latestLotteryItem.setId(Integer.parseInt(historyLottery.getLotteryId()));
		holder.latestLotteryItem.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle=new Bundle();
				bundle.putInt("lotteryid", v.getId());
				bundle.putString("lotteryname", name);
				bundle.putString("issue", issue);
				HallMiddleManager.getInstance().changeUI(LotteryHistoryDetailHall.class,bundle);
			}
			
		});
		return convertView;
	}
	
	
//	private String currenthisIssue="";
	/**
     * 判断是否需要显示标题
     * 
     * @param position
     * @return
     
    private boolean needTitle(int position) {
    	
        // 第一个肯定是分类
        if (position == 0) {
        	HistoryLottery currentfirst=(HistoryLottery)getItem(position);
        	currenthisIssue=currentfirst.getLotteryIssue();
            return true;
        }
        
        // 边界处理
        if (position < 0) {
            return false;
        }
         
        // 当前  // 上一个 
        HistoryLottery currentEntity =(HistoryLottery)getItem(position);
        HistoryLottery previousEntity = (HistoryLottery)getItem(position - 1);
        
        if (null == currentEntity || null == previousEntity) {
            return false;
        }
        
        String currentid = currentEntity.getLotteryId();
        String previousid = previousEntity.getLotteryId();
        if (null == currentid || null == previousid) {
            return false;
        }
        
        // 当前item分类名和上一个item分类名不同，则表示两item属于不同分类
        if (currentid.equals(previousid)) {
            return false;
        }
        currenthisIssue=currentEntity.getLotteryIssue();
        return true;
    }
    */

}
