package com.goldenasia.lottery.view.adapter;

import java.text.DecimalFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldenasia.lottery.R;

public class LotteryCodeAdapter extends BaseAdapter{

	private Context context;								//运行上下文   
    private String[] codes=null;    			//彩票种类最近开奖信息集合 
    private LayoutInflater listContainer;           		//视图容器
    
	// 依据item的layout
    public final class ViewHolder {
		TextView code;
	}
    
	public LotteryCodeAdapter(Context context,String[] code) {
        this.context = context;
        this.codes=code;
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文   
    }
	
	@Override
	public int getCount() {
		return this.codes.length;
	}

	@Override
	public Object getItem(int position) {
		return codes[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = listContainer.inflate(R.layout.nb_lottery_history_hall_gridview_item, null);
			holder.code = (TextView) convertView.findViewById(R.id.lottery_item_code);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.code.setText(codes[position]);
		holder.code.setTextColor(context.getResources().getColor(R.color.gray));
		return convertView;
	}
}