package com.goldenasia.lottery.view.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.AppendTaskDetailItems;

public class AppendDetailsAdapter extends BaseAdapter {

	private List<AppendTaskDetailItems> appendTaskList=new ArrayList<AppendTaskDetailItems>();
	private static HashMap<Integer, Boolean> isSelected;
	private LayoutInflater inflater;
	private Context context;
	private Typeface fontIco=null;
	private boolean isOP;
	// public static final String STATUS_UNFINISH_NAME="(已派发)";
	// public static final String STATUS_FINISH_NAME="(不存在)";
	//
	// public static final String STATUS_SENDED_CODE="1";
	// public static final String STATUS_UNEXIST_CODE="-1";

	public AppendDetailsAdapter(Context context) {
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context=context;
		this.fontIco=Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
		// 初始化数据
		initDate();
	}
	
	// 初始化isSelected的数据
	public void initDate() {
		isSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < appendTaskList.size(); i++) {
			getIsSelected().put(i, false);
		}
	}
	
	public void setOP(boolean isOP) {
		this.isOP = isOP;
	}
	
	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		AppendDetailsAdapter.isSelected = isSelected;
	}
	
	public List<AppendTaskDetailItems> getAppendTaskList() {
		return appendTaskList;
	}

	public void setAppendTaskList(List<AppendTaskDetailItems> appendTaskList) {
		this.appendTaskList = appendTaskList;
	}

	@Override
	public int getCount() {
		return appendTaskList == null ? 0 : appendTaskList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return appendTaskList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void refresh(List<AppendTaskDetailItems> appendTaskList) {
		this.appendTaskList = appendTaskList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.nb_lottery_order_append_details_hall_item, null);
			
			holder = new ViewHolder();
			holder.cb_op = (CheckBox) convertView.findViewById(R.id.cb_op);
			holder.cb_op.setTag(position);
			holder.rl_content = (RelativeLayout) convertView.findViewById(R.id.rl_content);
			holder.issue = (TextView) convertView.findViewById(R.id.nb_item_append_details_issue);	
			holder.multiple = (TextView) convertView.findViewById(R.id.nb_item_append_details_multiple);
			holder.status = (TextView) convertView.findViewById(R.id.nb_item_append_details_status);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			holder.cb_op.setTag(position);
		}
		
		final AppendTaskDetailItems appendTaskItems=appendTaskList.get(position);
		if(!appendTaskItems.getStatusdes().equals("已完成")){
			if (isOP) {
				holder.cb_op.setVisibility(View.VISIBLE);
				RelativeLayout.LayoutParams lps = (LayoutParams) holder.rl_content.getLayoutParams();
				lps.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.app_item_content_margin);
	
			} else {
				holder.cb_op.setVisibility(View.GONE);
				RelativeLayout.LayoutParams lps = (LayoutParams) holder.rl_content.getLayoutParams();
				lps.leftMargin = 0;
			}
		}
		
		holder.cb_op.setChecked(getIsSelected().get(position));
		holder.issue.setText(appendTaskItems.getIssue());
		holder.multiple.setText(appendTaskItems.getMultiple());
		holder.status.setText(appendTaskItems.getStatusdes());
		
//		convertView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				int cb_op = (Integer) holder.cb_op.getTag();
//				boolean isCheck = holder.cb_op.isChecked();
//				if (isCheck) {
//					holder.cb_op.setVisibility(View.GONE);
//					RelativeLayout.LayoutParams lps = (LayoutParams) holder.rl_content.getLayoutParams();
//					lps.leftMargin = 0;
//				} else {
//					holder.cb_op.setVisibility(View.VISIBLE);
//					RelativeLayout.LayoutParams lps = (LayoutParams) holder.rl_content.getLayoutParams();
//					lps.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.app_item_content_margin);
//				}
//				getIsSelected().put(cb_op, isCheck);
//				holder.cb_op.setChecked(getIsSelected().get(cb_op));
//			}
//		});
		
		return convertView;
	}

	public static class ViewHolder {
		public CheckBox cb_op;
		public RelativeLayout rl_content;
		
		public TextView issue;
		public TextView multiple;
		public TextView status;
	}
}
