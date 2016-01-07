package com.goldenasia.lottery.view.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.SettingItemEntity;

public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    private List<SettingItemEntity> mData;
    private LayoutInflater mLayoutInflater;
    
    private Typeface fonts = null;
    public CustomAdapter(Context pContext, List<SettingItemEntity> pData) {
    	this.fonts=Typeface.createFromAsset(pContext.getAssets(),"fontawesome-webfont.ttf");
    	this.mContext = pContext;
    	this.mData = pData;
    	this.mLayoutInflater = LayoutInflater.from(mContext);
    }
    

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 常见的优化ViewHolder
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.nb_lottery_myself_hall_packetview_item, null);
            
            viewHolder = new ViewHolder();
            viewHolder.content = (TextView) convertView.findViewById(R.id.nb_setting_content_title);
            viewHolder.contentIcon = (Button) convertView.findViewById(R.id.nb_setting_content_icon);
            viewHolder.arrowIco =(Button) convertView.findViewById(R.id.nb_myself_hall_arrow_ico);
            
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取数据
        SettingItemEntity itemEntity = mData.get(position);
        viewHolder.content.setText(itemEntity.getContent());
        
		String homeIco=mContext.getResources().getString(itemEntity.getIcoImage());
		viewHolder.contentIcon.setText(homeIco);
		viewHolder.contentIcon.setTextColor(mContext.getResources().getColor(R.color.gainsboro));  
		viewHolder.contentIcon.setTypeface(fonts);
		
		String arrowIco=mContext.getResources().getString(R.string.fa_chevron_right);
		viewHolder.arrowIco.setText(arrowIco);
		viewHolder.arrowIco.setTextColor(mContext.getResources().getColor(R.color.gray));  
		viewHolder.arrowIco.setTypeface(fonts);

        return convertView;
    }
    
    @Override
    public int getCount() {
        if (null != mData) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != mData && position < getCount()) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    private class ViewHolder {
        TextView content;
        Button contentIcon;
        Button arrowIco;
    }
    
}
