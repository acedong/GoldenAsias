package com.goldenasia.lottery.view.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.AppendTaskInfo;
import com.goldenasia.lottery.net.protocal.element.AppendOrderElements;
import com.goldenasia.lottery.view.AppendOrderDetails;
import com.goldenasia.lottery.view.OrderDetailsHall;
import com.goldenasia.lottery.view.manager.HallMiddleManager;

public class AppendOrderListAdapter extends BaseAdapter {

	private List<AppendTaskInfo> appendOrderList=new ArrayList<AppendTaskInfo>();
	private LayoutInflater inflater;
	private Context context;
	private Typeface fontIco=null;
	// public static final String STATUS_UNFINISH_NAME="(已派发)";
	// public static final String STATUS_FINISH_NAME="(不存在)";
	//
	// public static final String STATUS_SENDED_CODE="1";
	// public static final String STATUS_UNEXIST_CODE="-1";

	public AppendOrderListAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
		this.context=context;
		this.fontIco=Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
	}

	public int getCount() {
		return appendOrderList.size();
	}


	@Override
	public Object getItem(int position) {
		return appendOrderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void refresh(List<AppendTaskInfo> appendOrderList) {
		this.appendOrderList = appendOrderList;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		final AppendTaskInfo orderData=appendOrderList.get(position);
		
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.nb_tab_history_appendorder_item, null);	
			holder.lotterytitle = (TextView) convertView.findViewById(R.id.nb_appendorders_lottery_title);	//玩法logo
			holder.playgamename = (TextView) convertView.findViewById(R.id.nb_appendorders_playgame_name);
			holder.append_time = (TextView) convertView.findViewById(R.id.nb_appendorders_append_time);
			holder.append_money = (TextView) convertView.findViewById(R.id.nb_appendorders_append_money);
			
			holder.ordersstatus = (TextView) convertView.findViewById(R.id.nb_appendorders_append_status);
			holder.detailsbet = (Button) convertView.findViewById(R.id.nb_appendorders_append_details);
			
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		String money = context.getResources().getString(R.string.is_detail_money_unit_label);
		money = StringUtils.replaceEach(money, new String[] {"UNIT"}, new String[] { orderData.getTaskprice()});
		
		holder.lotterytitle.setText(orderData.getLotteryname());
		holder.playgamename.setText(orderData.getTitle());
		holder.append_time.setText(orderData.getBegintime());
		holder.append_money.setText(money);
		holder.ordersstatus.setText(orderData.getStatus());
		
		String targetIco=context.getResources().getString(R.string.fa_chevron_right);
		holder.detailsbet.setText(targetIco);
		holder.detailsbet.setTypeface(fontIco);
		holder.detailsbet.setTextColor(context.getResources().getColor(R.color.white));
		
		holder.detailsbet.setId(Integer.parseInt(orderData.getTaskid()));
		holder.detailsbet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putString("detailsid", String.valueOf(v.getId()));
				HallMiddleManager.getInstance().changeUI(AppendOrderDetails.class,bundle);
			}
		});
		
		convertView.setId(Integer.parseInt(orderData.getTaskid()));
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putString("detailsid", String.valueOf(v.getId()));
				HallMiddleManager.getInstance().changeUI(AppendOrderDetails.class,bundle);
			}
		});

		return convertView;
	}

	static class ViewHolder {
		TextView lotterytitle;
		TextView playgamename;
		TextView append_time;
		TextView append_money;
		TextView ordersstatus;
		Button detailsbet;
	}

}
