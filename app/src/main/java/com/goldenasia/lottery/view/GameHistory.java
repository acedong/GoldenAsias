package com.goldenasia.lottery.view;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.GlobalParams;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.BottomManager;
import com.goldenasia.lottery.view.manager.HallMiddleManager;
import com.goldenasia.lottery.view.manager.TitleManager;

public class GameHistory extends BaseUI implements OnCheckedChangeListener{
	
	private RadioGroup mRadioGroup;
	private RadioButton mRadioButton1;
	private RadioButton mRadioButton2;
	private RadioButton mRadioButton3;
	private RadioButton mRadioButton4;
	private ImageView mImageView;
	private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
	private HorizontalScrollView mHorizontalScrollView;//上面的水平滚动控件
	private ViewPager mViewPager;	//下方的可横向拖动的控件
	private ArrayList<View> mViews;//用来存放下方滚动的layout(layout_1,layout_2,layout_3)
	
	LocalActivityManager manager = null;

	public GameHistory(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init() {
		// TODO Auto-generated method stub
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.nb_lottery_history_game_hall, null);
		
		manager = new LocalActivityManager((Activity)context, true);
		manager.dispatchCreate(bundle);
		manager.dispatchPause(true);
		manager.dispatchResume();
        
        iniController();
        iniListener();
        iniVariable();
        
        mRadioButton1.setChecked(true);
        mViewPager.setCurrentItem(1);
        mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onResume() {
		TitleManager.getInstance().changeHomeTitle("游戏记录");
		BottomManager.getInstrance().selectSwitch(3);
		BottomManager.getInstrance().UserType();
		if(GlobalParams.PARENTID.equals("0")){
			mViewPager.setCurrentItem(3);
			mRadioButton1.setClickable(false);
			mRadioButton2.setClickable(false);
			mRadioButton3.setChecked(true);
			mRadioButton3.setClickable(true);
			mRadioButton4.setClickable(true);
		}else{
			mViewPager.setCurrentItem(1);
			mRadioButton1.setClickable(true);
			mRadioButton2.setClickable(true);
			mRadioButton3.setClickable(true);
			mRadioButton4.setClickable(true);
		}
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		
	}
	
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

    
    private void iniVariable() {
    	Intent intent = new Intent(context,Tab01Activity.class);
    	Intent intent1 = new Intent(context,Tab02Activity.class);
    	Intent intent2= new Intent(context,Tab03Activity.class);
    	Intent intent3= new Intent(context,Tab04Activity.class);
    	Intent intent4= new Intent(context,Tab05Activity.class);
    	
    	mViews = new ArrayList<View>();
    	
    	mViews.add(getView("A", intent));
    	mViews.add(getView("B", intent1));
    	mViews.add(getView("C", intent2));
    	mViews.add(getView("D", intent3));
    	mViews.add(getView("E", intent4));
    	
    	mViewPager.setAdapter(new MyPagerAdapter());//设置ViewPager的适配器
	}
    
    /**
	 * RadioGroup点击CheckedChanged监听
	 */
	@SuppressLint("NewApi")
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		AnimationSet _AnimationSet = new AnimationSet(true);
		TranslateAnimation _TranslateAnimation;
		
		Log.i("zj", "checkedid="+checkedId);
		if (checkedId == R.id.btn1) {
			_TranslateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, context.getResources().getDimension(R.dimen.rdo1), 0f, 0f);
			_AnimationSet.addAnimation(_TranslateAnimation);
			_AnimationSet.setFillBefore(false);
			_AnimationSet.setFillAfter(true);
			_AnimationSet.setDuration(100);
			
			mImageView.startAnimation(_AnimationSet);//开始上面蓝色横条图片的动画切换
			mViewPager.setCurrentItem(1);//让下方ViewPager跟随上面的HorizontalScrollView切换
		}else if (checkedId == R.id.btn2) {
			_TranslateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, context.getResources().getDimension(R.dimen.rdo2), 0f, 0f);

			_AnimationSet.addAnimation(_TranslateAnimation);
			_AnimationSet.setFillBefore(false);
			_AnimationSet.setFillAfter(true);
			_AnimationSet.setDuration(100);

			//mImageView.bringToFront();
			mImageView.startAnimation(_AnimationSet);
			
			mViewPager.setCurrentItem(2);
		}else if (checkedId == R.id.btn3) {
			_TranslateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, context.getResources().getDimension(R.dimen.rdo3), 0f, 0f);
			
			_AnimationSet.addAnimation(_TranslateAnimation);
			_AnimationSet.setFillBefore(false);
			_AnimationSet.setFillAfter(true);
			_AnimationSet.setDuration(100);
			
			//mImageView.bringToFront();
			mImageView.startAnimation(_AnimationSet);
			
			mViewPager.setCurrentItem(3);
		}else if (checkedId == R.id.btn4) {
			_TranslateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, context.getResources().getDimension(R.dimen.rdo4), 0f, 0f);
			
			_AnimationSet.addAnimation(_TranslateAnimation);
			_AnimationSet.setFillBefore(false);
			_AnimationSet.setFillAfter(true);
			_AnimationSet.setDuration(100);
			
			//mImageView.bringToFront();
			mImageView.startAnimation(_AnimationSet);
			mViewPager.setCurrentItem(4);
		}
		
		mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();//更新当前蓝色横条距离左边的距离
		
		Log.i("zj", "getCurrentCheckedRadioLeft="+getCurrentCheckedRadioLeft());
		Log.i("zj", "getDimension="+context.getResources().getDimension(R.dimen.rdo2));
		
		mHorizontalScrollView.smoothScrollTo((int)mCurrentCheckedRadioLeft-(int)context.getResources().getDimension(R.dimen.rdo2), 0);
	}
    
	/**
     * 获得当前被选中的RadioButton距离左侧的距离
     */
	private float getCurrentCheckedRadioLeft() {
		// TODO Auto-generated method stub
		if (mRadioButton1.isChecked()) {
			return context.getResources().getDimension(R.dimen.rdo1);
		}else if (mRadioButton2.isChecked()) {
			return context.getResources().getDimension(R.dimen.rdo2);
		}else if (mRadioButton3.isChecked()) {
			return context.getResources().getDimension(R.dimen.rdo3);
		}else if (mRadioButton4.isChecked()) {
			return context.getResources().getDimension(R.dimen.rdo4);
		}
		return 0f;
	}

	private void iniListener() {
		// TODO Auto-generated method stub
		mRadioGroup.setOnCheckedChangeListener(this);
		mViewPager.setOnPageChangeListener(new MyPagerOnPageChangeListener());
	}

	private void iniController() {
		// TODO Auto-generated method stub
		mRadioGroup = (RadioGroup)findViewById(R.id.radioGroup);
		mRadioButton1 = (RadioButton)findViewById(R.id.btn1);
		mRadioButton2 = (RadioButton)findViewById(R.id.btn2);
		mRadioButton3 = (RadioButton)findViewById(R.id.btn3);
		mRadioButton4 = (RadioButton)findViewById(R.id.btn4);
		
		mImageView = (ImageView)findViewById(R.id.img1);
		
		mHorizontalScrollView = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);
		
		mViewPager = (ViewPager)findViewById(R.id.pager);
	}

	/**
	 * ViewPager的适配器
	 */
	private class MyPagerAdapter extends PagerAdapter{

		@Override
		public void destroyItem(View v, int position, Object obj) {
			// TODO Auto-generated method stub
			((ViewPager)v).removeView(mViews.get(position));
		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mViews.size();
		}

		@Override
		public Object instantiateItem(View v, int position) {
			((ViewPager)v).addView(mViews.get(position));
			return mViews.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub
		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub
		}
		
	}
	/**
	 * ViewPager的PageChangeListener(页面改变的监听器)
	 */
	private class MyPagerOnPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub			
		}
		/**
		 * 滑动ViewPager的时候,让上方的HorizontalScrollView自动切换
		 */
		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			//Log.i("zj", "position="+position);
			if(GlobalParams.PARENTID.equals("0")){
				if (position == 0) {
					mViewPager.setCurrentItem(3);
				}else if (position == 1) {
					mViewPager.setCurrentItem(3);
				}else if (position == 2) {
					mViewPager.setCurrentItem(3);
				}else if (position == 3) {
					mViewPager.setCurrentItem(3);
				}else if (position == 4) {
					mRadioButton4.performClick();
				}else if (position == 5) {
					mViewPager.setCurrentItem(4);
				}
			}else{
				if (position == 0) {
					mViewPager.setCurrentItem(1);
				}else if (position == 1) {
					mRadioButton1.performClick();
				}else if (position == 2) {
					mRadioButton2.performClick();
				}else if (position == 3) {
					mRadioButton3.performClick();
				}else if (position == 4) {
					mRadioButton4.performClick();
				}else if (position == 5) {
					mViewPager.setCurrentItem(4);
				}
			}
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstantValue.VIEW_GAMEHISTORY;
	}
}
