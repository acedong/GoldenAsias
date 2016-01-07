package com.goldenasia.lottery.view.adapter;

import java.util.List;
import java.util.Map;

import android.R.raw;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;

public class WithdrawalsStatusAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> list;
	private LayoutInflater inflater;

	public WithdrawalsStatusAdapter(Context context, List<Map<String, Object>> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.nb_page_withdrawals_status_item, null);
			viewHolder = new ViewHolder();
			viewHolder.stateImage = (ImageView) convertView.findViewById(R.id.nb_withdrawal_state_image);
			viewHolder.viewpath = (View) convertView.findViewById(R.id.nb_withdrawal_view_path);
			viewHolder.title = (TextView) convertView.findViewById(R.id.nb_withdrawal_title);
			viewHolder.detailed = (LinearLayout) convertView.findViewById(R.id.nb_withdrawal_detailed);
			viewHolder.bankinfo = (TextView) convertView.findViewById(R.id.nb_withdrawal_bankinfo);
			viewHolder.money = (TextView) convertView.findViewById(R.id.nb_withdrawal_money);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		String titleStr = list.get(position).get("title").toString();
		String status = list.get(position).get("status").toString();
		
		if(status.equals("0")){
			String bankinfoStr = list.get(position).get("bankinfo").toString();
			String moneyStr = list.get(position).get("money").toString();
			viewHolder.stateImage.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_green_circle_success)); 
			viewHolder.title.setText(titleStr);
			viewHolder.title.setTextColor(context.getResources().getColor(R.color.viridity));
			viewHolder.bankinfo.setText(bankinfoStr);
			viewHolder.money.setText(moneyStr);
			viewHolder.viewpath.setVisibility(View.VISIBLE);
			viewHolder.detailed.setVisibility(View.VISIBLE);
		}else if(status.equals("1")){
			viewHolder.title.setText(titleStr);
			viewHolder.viewpath.setVisibility(View.GONE);
			viewHolder.stateImage.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_green_circle_process)); 
			viewHolder.detailed.setVisibility(View.GONE);
		}

		return convertView;
	}

	static class ViewHolder {
		public ImageView stateImage;
		public TextView title;
		public View viewpath;
		public LinearLayout detailed;
		public TextView bankinfo;
		public TextView money;
	}
}
