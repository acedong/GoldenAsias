package com.goldenasia.lottery.view.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.HistoryLottery;

/**
 * 历史开奖 列表
 * @author Ace
 *
 */

public class HistoryDetailsListViewAdapter extends BaseAdapter{

	private Context context;								//运行上下文   
    private List<HistoryLottery> historyDetailsList=new ArrayList<HistoryLottery>();    	//彩票开奖号码信息集合 
    private LayoutInflater listContainer;           		//视图容器
	// 依据item的layout
    public final class ViewHolder {
		TextView issue;
		GridView code;
	}
    
	public HistoryDetailsListViewAdapter(Context context) {
        this.context = context;
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文   
    }
	
	@Override
	public int getCount() {
		return historyDetailsList.size();
	}

	@Override
	public Object getItem(int position) {
		return historyDetailsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void refresh(List<HistoryLottery> detailsList) {
		this.historyDetailsList = detailsList;
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		final HistoryLottery hislot=historyDetailsList.get(position);
		
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = listContainer.inflate(R.layout.nb_lottery_history_detail_hall_list_item, null);
			holder.issue = (TextView) convertView.findViewById(R.id.nb_history_details_issue);
			holder.code = (GridView) convertView.findViewById(R.id.nb_history_details_code);
			
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		String textissue = context.getResources().getString(R.string.is_history_details_issue_label);
		textissue = StringUtils.replaceEach(textissue, new String[] { "ISSUE" }, new String[] { hislot.getLotteryIssue()});
		
		String[] item=null;
		String digit=hislot.getLotterydigit();
		if(digit.contains(" ")){
			item = hislot.getLotterydigit().split(" "); 
		}else{
			item = hislot.getLotterydigit().split(""); 
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
		LotteryCodeAdapter adapter = new LotteryCodeAdapter(context, item); 
		
		holder.issue.setText(textissue);
		
		holder.code.setSelector(new ColorDrawable(Color.TRANSPARENT));
		holder.code.setAdapter(adapter);  
		
		convertView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
			
		});
		return convertView;
	}
	
}
