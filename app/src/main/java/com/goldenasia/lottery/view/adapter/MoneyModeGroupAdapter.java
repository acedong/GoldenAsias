package com.goldenasia.lottery.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldenasia.lottery.R;

/**
 * 金钱模式 玩法适配器
 * @author Ace
 *
 */

public class MoneyModeGroupAdapter extends BaseAdapter {
 
    private Context context;
 
    private List<String> list;
 
    public MoneyModeGroupAdapter(Context context, List<String> list) {
 
        this.context = context;
        this.list = list;
 
    }
 
    @Override
    public int getCount() {
        return list.size();
    }
 
    @Override
    public Object getItem(int position) {
 
        return list.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
 
         
        ViewHolder holder;
        if (convertView==null) {
            convertView=LayoutInflater.from(context).inflate(R.layout.nb_play_moneymode_group_item, null);
            holder=new ViewHolder();
             
            convertView.setTag(holder);
             
            holder.groupItem=(TextView) convertView.findViewById(R.id.groupItem);
             
        }
        else{
            holder=(ViewHolder) convertView.getTag();
        }
        holder.groupItem.setText(list.get(position));
         
        return convertView;
    }
 
    static class ViewHolder {
        TextView groupItem;
    }
 
}
