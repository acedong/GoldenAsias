package com.goldenasia.lottery.view.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PopupAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	
	private String[] mData;
	
	private int[] mRes;
	
	private int mLayout;
	
	private int mImageId;
	
	private int mTextId;
	
	public PopupAdapter(Context context, String[] data, int[] res, int layout, int imageId, int textId) {
		mInflater = LayoutInflater.from(context);
		mData = data;
		mRes = res;
		mLayout = layout;
		mImageId = imageId;
		mTextId = textId;
	}
	
	@Override
	public int getCount() {
		return mData.length;
	}

	@Override
	public Object getItem(int position) {
		return mData[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		MenuItem item = null;
		if (null == v) {
			v = mInflater.inflate(mLayout, null);
			item = new MenuItem();
			v.setTag(item);
		}
		else
		{
			item = (MenuItem) v.getTag();
		}
		
		ImageView image = (ImageView) v.findViewById(mImageId);
		TextView text = (TextView) v.findViewById(mTextId);
		image.setImageResource(mRes[position]);
		text.setText(mData[position]);
		item.image = image;
		item.text = text;
		
		return v;
	}

}

class MenuItem {
	ImageView image;
	TextView text;
}
