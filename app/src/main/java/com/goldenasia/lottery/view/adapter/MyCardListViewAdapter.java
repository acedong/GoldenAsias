package com.goldenasia.lottery.view.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.BankSupportInfo;

/**
 * 彩票种类 列表
 * @author Ace
 */

public class MyCardListViewAdapter extends BaseAdapter {

	private Context context; // 运行上下文
	private List<BankSupportInfo> userBankInfoList=new ArrayList<BankSupportInfo>();
	private LayoutInflater listContainer; // 视图容器
	private Typeface fontIco=null;
	// 依据item的layout
	public class ViewHolder {
		ImageView banklogo;
		TextView oldrealname;
		TextView oldcardno;
	}

	public MyCardListViewAdapter(Context context) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.fontIco=Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
	}

	@Override
	public int getCount() {
		return userBankInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return userBankInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void refresh(List<BankSupportInfo> userBankInfoList) {
		this.userBankInfoList = userBankInfoList;
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = listContainer.inflate(R.layout.nb_page_binding_bank_usercard_item, null);
			holder.oldrealname = (TextView) convertView.findViewById(R.id.nb_oldrealname_edit_text);
			holder.oldcardno = (TextView) convertView.findViewById(R.id.nb_oldcardno_edit_text);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}
	
}
