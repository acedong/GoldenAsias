package com.goldenasia.lottery.view.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldenasia.lottery.R;

/**
 * 选号容器用Adapter（时时彩）
 * @author Ace
 *
 */
public class OnePoolAdapter extends BaseAdapter {

	private Context context;
	private int endNum;
	private List<Integer> slectedNums;
	private int slectedBgResId;// 选中的背景图片的资源id
	private LayoutInflater listContainer; // 视图容器

	public OnePoolAdapter(Context context, int endNum, List<Integer> slectedNums, int slectedBgResId) {
		super();
		this.context = context;
		this.endNum = endNum;
		this.slectedNums = slectedNums;
		this.slectedBgResId = slectedBgResId;
		listContainer = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return endNum;
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
		ViewHolder holder=null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = listContainer.inflate(R.layout.nb_gridview_one_item_ball, null);
			holder.ball = (TextView) convertView.findViewById(R.id.nb_one_balltext); 
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.ball.setTag(position);
		DecimalFormat decimalFormat = new DecimalFormat("0");
		holder.ball.setText(decimalFormat.format(position));
		holder.ball.setTextColor(context.getResources().getColor(R.color.darkgray));
		holder.ball.setTextSize(16);
		TextPaint paint = holder.ball.getPaint();
		paint.setFakeBoldText(true); 
		// 居中
		holder.ball.setGravity(Gravity.CENTER);
		
		// 获取到用户已选号码的集合，判读集合中有，背景图片修改为红色
		if (slectedNums.contains(position)) {
			holder.ball.setBackgroundResource(slectedBgResId);
		} else {
			holder.ball.setBackgroundResource(R.drawable.id_defalut_ball);
		}
		return convertView;
	}

	// 依据item的layout
	public final class ViewHolder {
		TextView ball;
	}
}
