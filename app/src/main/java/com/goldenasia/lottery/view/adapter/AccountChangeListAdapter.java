package com.goldenasia.lottery.view.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.AccountChangeInfo;
import com.goldenasia.lottery.net.protocal.element.AccountChangeElement;
import com.goldenasia.lottery.view.OrderDetailsHall;
import com.goldenasia.lottery.view.manager.HallMiddleManager;

public class AccountChangeListAdapter extends BaseAdapter {

	private List<AccountChangeInfo> accountChangeList=new ArrayList<AccountChangeInfo>();
	private LayoutInflater inflater;
	private Context context;
	private Typeface fontIco=null;
	// public static final String STATUS_UNFINISH_NAME="(已派发)";
	// public static final String STATUS_FINISH_NAME="(不存在)";
	//
	// public static final String STATUS_SENDED_CODE="1";
	// public static final String STATUS_UNEXIST_CODE="-1";

	public AccountChangeListAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
		this.context=context;
		this.fontIco=Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
	}

	public int getCount() {
		return accountChangeList.size();

	}

	@Override
	public Object getItem(int position) {
		return accountChangeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void refresh(List<AccountChangeInfo> accountChangeList) {
		this.accountChangeList = accountChangeList;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		final AccountChangeInfo accountChangeData=accountChangeList.get(position);
		
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.nb_tab_history_billing_item, null);	
			holder.accountChangeTimes = (TextView) convertView.findViewById(R.id.nb_account_change_billing_times);	//玩法logo
			holder.accountChangeName = (TextView) convertView.findViewById(R.id.nb_account_change_billing_name);
			holder.accountChangeMoney = (TextView) convertView.findViewById(R.id.nb_account_change_billing_money);
			
			holder.detailsbet = (Button) convertView.findViewById(R.id.nb_account_change_billing_details);
			
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.accountChangeTimes.setText(accountChangeData.getTimes());
		holder.accountChangeName.setText(accountChangeData.getTitle());
		holder.accountChangeMoney.setText(accountChangeData.getAvailablebalance());
		
		String targetIco=context.getResources().getString(R.string.fa_chevron_right);
		holder.detailsbet.setText(targetIco);
		holder.detailsbet.setTypeface(fontIco);
		holder.detailsbet.setTextColor(context.getResources().getColor(R.color.gray));
		
		holder.detailsbet.setId(Integer.parseInt(accountChangeData.getOrdertypeid()));
		holder.detailsbet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putInt("detailsid", v.getId());
				//HallMiddleManager.getInstance().changeUI(OrderDetailsHall.class,bundle);
			}
		});

		return convertView;
	}

	static class ViewHolder {
		TextView accountChangeTimes;
		TextView accountChangeName;
		TextView accountChangeMoney;
		Button detailsbet;
	}

}
