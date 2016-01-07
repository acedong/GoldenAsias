package com.goldenasia.lottery.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.HistoryLottery;

/**
 * 历史开奖适配器
 * @author Ace
 *
 */

public class HistoryLotteryOtherListAdapter extends BaseAdapter{

	private Context context;								//运行上下文   
    private String[] historyList=null;    			//开奖信息集合 
    private LayoutInflater container;           		//视图容器
	
    public HistoryLotteryOtherListAdapter(Context contexts,String[] historyList) {
        this.context = contexts;
        this.historyList=historyList;
        container = LayoutInflater.from(context);   //创建视图容器并设置上下文   
    }
    
    public final class ViewHolder {
	    TextView otherIssue;
		TextView otherCode;
    }
	
	@Override
	public int getCount() {
		return this.historyList.length;
	}

	@Override
	public Object getItem(int position) {
		return  this.historyList[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		
		String historyinfo=historyList[position];
		
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = container.inflate(R.layout.nb_lottery_history_hall_otherlist_item, null);
			
			holder.otherIssue = (TextView) convertView.findViewById(R.id.nb_hall_lottery_historyother_issue);
			holder.otherCode = (TextView) convertView.findViewById(R.id.nb_hall_lottery_historyother_code);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String[] issue=historyinfo.split("-");
		holder.otherIssue.setText(issue[0]);
		holder.otherCode.setText(issue[1]);
		
		return convertView;
	}

}
