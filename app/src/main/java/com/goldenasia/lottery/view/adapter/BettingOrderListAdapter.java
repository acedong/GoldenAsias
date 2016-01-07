package com.goldenasia.lottery.view.adapter;

import java.util.ArrayList;
import java.util.List;

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
import com.goldenasia.lottery.bean.OrderCatalog;
import com.goldenasia.lottery.net.protocal.element.OrderElement;
import com.goldenasia.lottery.net.protocal.element.OrderElements;
import com.goldenasia.lottery.net.protocal.element.OrderListElements;
import com.goldenasia.lottery.view.OrderDetailsHall;
import com.goldenasia.lottery.view.manager.HallMiddleManager;

public class BettingOrderListAdapter extends BaseAdapter {

	private List<OrderElement> orderlist=new ArrayList<OrderElement>();
	private int resource;
	private LayoutInflater inflater;
	private Typeface fontIco=null;
	private Context context;
	// public static final String STATUS_UNFINISH_NAME="(已派发)";
	// public static final String STATUS_FINISH_NAME="(不存在)";
	//
	// public static final String STATUS_SENDED_CODE="1";
	// public static final String STATUS_UNEXIST_CODE="-1";

	public BettingOrderListAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
		this.context=context;
		this.fontIco=Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
	}

	public int getCount() {
		return orderlist.size();

	}

	@Override
	public Object getItem(int position) {
		return orderlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void refresh(List<OrderElement> orderlist) {
		this.orderlist = orderlist;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		final OrderElement orderData=orderlist.get(position);
		
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.nb_tab_history_bettingorder_item, null);	
			holder.lotterytitle = (TextView) convertView.findViewById(R.id.nb_orders_lottery_title);	//玩法logo
			holder.playgamename = (TextView) convertView.findViewById(R.id.nb_orders_playgame_name);
			holder.betting_money = (TextView) convertView.findViewById(R.id.nb_orders_betting_money);
			holder.betting_issue = (TextView) convertView.findViewById(R.id.nb_orders_betting_issue);
			holder.ordersstatus = (TextView) convertView.findViewById(R.id.nb_orders_status);
			holder.detailsbet = (Button) convertView.findViewById(R.id.nb_orders_details);
			
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		OrderCatalog orderCatalog=orderData.getOrderCatalog();
		
		holder.lotterytitle.setText(orderCatalog.getLotteryname());
		holder.playgamename.setText("("+orderCatalog.getMethodname()+")");
		holder.betting_money.setText(orderCatalog.getTotalprice()+" 元");
		holder.betting_issue.setText("第 "+orderCatalog.getIssue()+" 期");
		holder.ordersstatus.setText(orderCatalog.getStatus());
		
		String targetIco=context.getResources().getString(R.string.fa_chevron_right);
		holder.detailsbet.setText(targetIco);
		holder.detailsbet.setTypeface(fontIco);
		
		holder.detailsbet.setId(Integer.parseInt(orderCatalog.getProjectid()));
		holder.detailsbet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putInt("detailsid", v.getId());
				HallMiddleManager.getInstance().changeUI(OrderDetailsHall.class,bundle);
			}
		});
		
		convertView.setId(Integer.parseInt(orderCatalog.getProjectid()));
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putInt("detailsid", v.getId());
				HallMiddleManager.getInstance().changeUI(OrderDetailsHall.class,bundle);
			}
		});

		return convertView;
	}

	static class ViewHolder {
		TextView lotterytitle;
		TextView playgamename;
		TextView betting_money;
		TextView betting_issue;
		TextView ordersstatus;
		Button detailsbet;
	}

}
