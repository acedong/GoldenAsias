/*
 * Copyright (c) 2014, 青岛司通科技有限公司 All rights reserved.
 * File Name：RushBuyCountDownTimerView.java
 * Version：V1.0
 * Author：zhaokaiqiang
 * Date：2014-9-26
 */
package com.goldenasia.lottery.view.controls;

import java.util.Timer;
import java.util.TimerTask;

import com.goldenasia.lottery.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("HandlerLeak")
public class RushBuyCountDownTimerView extends LinearLayout {

	// 小时，十位
	private TextView tv_hour_decade;
	// 小时，个位
	private TextView tv_hour_unit;
	// 分钟，十位
	private TextView tv_min_decade;
	// 分钟，个位
	private TextView tv_min_unit;
	// 秒，十位
	private TextView tv_sec_decade;
	// 秒，个位
	private TextView tv_sec_unit;

	private Context context;

	private int hour_decade=0;
	private int hour_unit=0;
	private int min_decade=0;
	private int min_unit=0;
	private int sec_decade=0;
	private int sec_unit=0;
	
	private OnUpdateCountdownListener oncountdown;
	// 计时器
	private Timer timer;

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			countDown();
		};
	};

	public OnUpdateCountdownListener getOncountdown() {
		return oncountdown;
	}

	public void setOncountdown(OnUpdateCountdownListener oncountdown) {
		this.oncountdown = oncountdown;
	}

	public RushBuyCountDownTimerView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.view_countdowntimer, this);

		tv_hour_decade = (TextView) view.findViewById(R.id.tv_hour_decade);
		tv_hour_unit = (TextView) view.findViewById(R.id.tv_hour_unit);
		tv_min_decade = (TextView) view.findViewById(R.id.tv_min_decade);
		tv_min_unit = (TextView) view.findViewById(R.id.tv_min_unit);
		tv_sec_decade = (TextView) view.findViewById(R.id.tv_sec_decade);
		tv_sec_unit = (TextView) view.findViewById(R.id.tv_sec_unit);

	}

	/**
	 * 
	 * @Description: 开始计时
	 * @param
	 * @return void
	 * @throws
	 */
	public void start() {

		if (timer == null) {
			timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					handler.sendEmptyMessage(0);
				}
			}, 0, 1000);
		}
	}

	/**
	 * 
	 * @Description: 停止计时
	 * @param
	 * @return void
	 * @throws
	 */
	public void stop() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	/**
	 * @throws Exception
	 * 
	 * @Description: 设置倒计时的时长
	 * @param
	 * @return void
	 * @throws
	 */
	public void setTime(int hour, int min, int sec) {

		if (hour >= 60 || min >= 60 || sec >= 60 || hour < 0 || min < 0 || sec < 0) {
			Log.v("RushBuyCountDownTimerView", "Time format is error,please check out your code");
			//throw new RuntimeException("Time format is error,please check out your code");
		}

		hour_decade = hour / 10;
		hour_unit = hour - hour_decade * 10;

		min_decade = min / 10;
		min_unit = min - min_decade * 10;

		sec_decade = sec / 10;
		sec_unit = sec - sec_decade * 10;

		tv_hour_decade.setText(hour_decade + "");
		tv_hour_unit.setText(hour_unit + "");
		tv_min_decade.setText(min_decade + "");
		tv_min_unit.setText(min_unit + "");
		tv_sec_decade.setText(sec_decade + "");
		tv_sec_unit.setText(sec_unit + "");

	}

	/**
	 * 
	 * @Description: 倒计时
	 * @param
	 * @return boolean
	 * @throws
	 */
	private void countDown() {
		int sec_decade = Integer.valueOf(tv_sec_decade.getText().toString());
		int sec_unit = Integer.valueOf(tv_sec_unit.getText().toString());
		
		int min_decade = Integer.valueOf(tv_min_decade.getText().toString());
		int min_unit = Integer.valueOf(tv_min_unit.getText().toString());
		
		int hour_decade = Integer.valueOf(tv_hour_decade.getText().toString());
		int hour_unit = Integer.valueOf(tv_hour_unit.getText().toString());
		if(hour_decade!=0||hour_unit!=0||min_decade!=0||min_unit!=0||sec_decade!=0||sec_unit!=0){
			if (isCarry4Unit(tv_sec_decade,tv_sec_unit)) {
				if (isCarry4Decade(tv_sec_decade,tv_sec_unit)) {
					if (isCarry4Unit(tv_min_decade,tv_min_unit)) {
						if (isCarry4Decade(tv_min_decade,tv_min_unit)) {
							if (isCarry4Unit(tv_hour_decade,tv_hour_unit)) {
								if (isCarry4Decade(tv_hour_decade,tv_hour_unit)) {
									//Toast.makeText(context, "时间到了",Toast.LENGTH_SHORT).show();
									stop();
									oncountdown.onUpdateCountdown();
								}else{
									oncountdown.onUpdateRecordTime(true, false, false);
								}
							}else{
								oncountdown.onUpdateRecordTime(true, false, false);
							}
						}else{
							oncountdown.onUpdateRecordTime(false, true, false);
						}
					}else{
						oncountdown.onUpdateRecordTime(false, true, false);
					}
				}else{
					oncountdown.onUpdateRecordTime(false, false, true);
				}
			}else{
				oncountdown.onUpdateRecordTime(false, false, true);
			}
		}else{
			stop();
			oncountdown.onUpdateCountdown();
		}
	}

	/**
	 * @Description: 变化十位，并判断是否需要进位
	 * @param
	 * @return boolean
	 * @throws
	 */
	private boolean isCarry4Decade(TextView tvshi,TextView tvge) {

		int timeshi = Integer.valueOf(tvshi.getText().toString());
		timeshi = timeshi - 1;
		if (timeshi < 0) {
			timeshi = 5;
			tvshi.setText(timeshi + "");
			return true;
		} else {
			tvshi.setText(timeshi + "");
			return false;
		}

	}

	/**
	 * @Description: 变化个位，并判断是否需要进位
	 * @param
	 * @return boolean
	 * @throws
	 */
	private boolean isCarry4Unit(TextView tvshi,TextView tvge) {

		int timege = Integer.valueOf(tvge.getText().toString());
		timege = timege - 1;
		if (timege < 0) {
			timege = 9;
			tvge.setText(timege + "");
			return true;
		} else {
			tvge.setText(timege + "");
			return false;
		}
	}
	
	public interface OnUpdateRecordTimeListener{
		public void onUpdateRecordTime();
	}
	
	public interface OnUpdateCountdownListener{
		/**
		 * 刷新数据
		 * @param v
		 * @param position
		 */
		public void onUpdateCountdown();
		/**
		 * 
		 */
		public void onUpdateRecordTime(boolean hourFlag,boolean minuteFlag,boolean secondFlag);
	}
}
