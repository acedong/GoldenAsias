package com.goldenasia.lottery.view.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.MessageInfo;
import com.goldenasia.lottery.view.OneselfMessageDetail;
import com.goldenasia.lottery.view.controls.AutoAjustIntentTextView;
import com.goldenasia.lottery.view.manager.HallMiddleManager;

public class OneselfMessageAdapter extends BaseAdapter {

	private List<MessageInfo> messageInfoList=new ArrayList<MessageInfo>();
	private LayoutInflater inflater;
	private Context context;
	private Typeface fontIco=null;
	// public static final String STATUS_UNFINISH_NAME="(已派发)";
	// public static final String STATUS_FINISH_NAME="(不存在)";
	//
	// public static final String STATUS_SENDED_CODE="1";
	// public static final String STATUS_UNEXIST_CODE="-1";

	public OneselfMessageAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
		this.context=context;
		this.fontIco=Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
	}

	public int getCount() {
		return messageInfoList.size();
	}


	@Override
	public Object getItem(int position) {
		return messageInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void refresh(List<MessageInfo> messageList) {
		this.messageInfoList = messageList;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		final MessageInfo messageData;
		
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.nb_lottery_message_hall_oneself_item, null);	
			holder.messagetitle = (TextView) convertView.findViewById(R.id.nb_item_message_title);	//玩法logo
			holder.messagecontent = (TextView) convertView.findViewById(R.id.nb_item_message_content);
			holder.detailsbet = (Button) convertView.findViewById(R.id.nb_item_message_bet);
			
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		messageData=messageInfoList.get(position);
		
		holder.messagetitle.setText(messageData.getTitle());
		holder.messagecontent.setText(Html.fromHtml(messageData.getSubject()));
		
		String targetIco=context.getResources().getString(R.string.fa_chevron_right);
		holder.detailsbet.setText(targetIco);
		holder.detailsbet.setTypeface(fontIco);
		holder.detailsbet.setTextColor(context.getResources().getColor(R.color.gray));
		
		holder.detailsbet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putString("msgid", messageData.getId());
				HallMiddleManager.getInstance().changeUI(OneselfMessageDetail.class,bundle);
			}
		});
		
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle=new Bundle();
				bundle.putString("msgid", messageData.getId());
				HallMiddleManager.getInstance().changeUI(OneselfMessageDetail.class,bundle);
			}

		});

		return convertView;
	}

	static class ViewHolder {
		TextView messagetitle;
		TextView messagecontent;
		Button detailsbet;
	}

}
