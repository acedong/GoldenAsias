package com.goldenasia.lottery.util;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.CityInfo;
import com.goldenasia.lottery.bean.ProvinceInfo;
import com.goldenasia.lottery.view.widget.DialogLinkageView;
import com.goldenasia.lottery.view.widget.WheelListAdapter;
import com.goldenasia.lottery.view.widget.WheelView;
import com.goldenasia.lottery.view.widget.DialogLinkageView.onWheelLinkageBtnNegClick;
import com.goldenasia.lottery.view.widget.DialogLinkageView.onWheelLinkageBtnPosClick;
import com.goldenasia.lottery.view.widget.DialogLinkageView.onWheelLinkageChangingListener;

/**
 * 封装了滚轮操作的类
 * 联动组件
 * @author Ace
 */

public class WheelLinkageDialogShowUtil {

	private Context mContext;

	private String title;
	
	private String[] proInfo=null; 
	private String[] cityInfo=null;
	private List<ProvinceInfo> proList=null;
	private Map<String,List<CityInfo>> cityMap=null;

	private WheelView wheelViewProvince;	//省滚轮
	private WheelView wheelViewCity;		//市滚轮
	private Dialog dialog;
	public DialogLinkageView dialogView;

	private int visibleItems = 5;
	
	private int index = 0;
	private String proIndex="";

	public int getVisibleItems() {
		return visibleItems;
	}

	public void setVisibleItems(int visibleItems) {
		this.visibleItems = visibleItems;
	}

	public WheelView getWheelViewProvince() {
		return wheelViewProvince;
	}
	
	public WheelView getWheelViewCity() {
		return wheelViewCity;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public WheelLinkageDialogShowUtil(Context mContext, Display mDisplay, List<ProvinceInfo> proList,Map<String,List<CityInfo>> cityMap, String title) {

		this.mContext = mContext;
		this.proList = proList;
		this.cityMap =cityMap;
		this.title = title;
		this.proInfo=new String[this.proList.size()];
		
		for(int p=0;p<this.proList.size();p++){
			ProvinceInfo pro=this.proList.get(p);
			this.proInfo[p]=pro.getProvinceName();
			if(p==index)
				proIndex=pro.getProvinceid();
		}
		
		for (Map.Entry<String, List<CityInfo>> entry : this.cityMap.entrySet()) {
			String cityKey=entry.getKey();
			if(cityKey.equals(proIndex)){
				
				List<CityInfo> cityList=(List<CityInfo>)entry.getValue();
				
				this.cityInfo=new String[cityList.size()];
				for(int c=0;c<cityList.size();c++)
				{
					CityInfo ci=cityList.get(c);
					this.cityInfo[c]=ci.getCityName();
				}
			}
		}
	
		dialogView = new DialogLinkageView(mContext);
		dialogView.setWidth(mDisplay.getWidth());
		dialogView.setHeight(mDisplay.getHeight() / 100 * 40);

		// 默认的点击事件
		dialogView.setLinkageBtnNegClick(new onWheelLinkageBtnNegClick() {

			@Override
			public void onClick(String textPro, int positionPro,String textCity, int positionCity) {
				// TODO Auto-generated method stub
				dissmissWheel();
			}
		});

		// 默认的点击事件
		dialogView.setLinkageBtnPosClick(new onWheelLinkageBtnPosClick() {

			@Override
			public void onClick(String textPro, int positionPro,String textCity, int positionCity) {
				// TODO Auto-generated method stub
				dissmissWheel();
			}
		});

		initDialog(dialogView);

	}

	private Dialog initDialog(DialogLinkageView dialogWeelUtil) {
		dialog = dialogWeelUtil.initDialog(title, "内容");
		initWheel(dialogWeelUtil.getWheelViewProvince(),dialogWeelUtil.getWheelViewCity(),proInfo,cityInfo);
		return dialog;
	}

	public void showWheel() {
		if (dialog != null) {
			dialog.show();
		}

	}

	public void dissmissWheel() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}

	}

	public boolean isShowing() {
		if (dialog != null && dialog.isShowing()) {
			return true;
		}
		return false;
	}

	public void setWheelHint(int index) {
		if (wheelViewProvince != null) {
			wheelViewProvince.setCurrentItem(index);
		}

	}

	public void setWindowAlpha(Activity mActivity) {
		WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
		lp.alpha = 0.1f;
		mActivity.getWindow().setAttributes(lp);

	}

	private WheelListAdapter mProvinceAdapter;
	private WheelListAdapter mCityAdapter;
	private String proName="";
	// Scrolling flag
	@SuppressLint("NewApi")
	private void initWheel(WheelView wheelProvince,WheelView wheelCity, final String[] proInfo,final String[] cityInfo) {

		// 为dialog的确定和取消按钮设置数据
		dialogView.setWheel(wheelProvince,wheelCity, proInfo,cityInfo);
		dialogView.setWheelLinkageChanging(new onWheelLinkageChangingListener() {

			@Override
			public void onWheelChanging(WheelView wheel, int oldValueProvince,int newValueProvince, String selectProvince) {
				// TODO Auto-generated method stub
				proName=proInfo[newValueProvince];
				updateCities();
			}
			
		});

		wheelViewProvince = wheelProvince;
		wheelViewProvince.setVisibleItems(visibleItems);
		mProvinceAdapter = new WheelListAdapter(mContext, proInfo, R.layout.wheel_layout, wheelProvince);
		wheelViewProvince.setViewAdapter(mProvinceAdapter);
		if(proInfo.length<3&&proInfo.length>=1)
			wheelViewProvince.setCurrentItem(0);
		else
			wheelViewProvince.setCurrentItem(3);

		wheelViewCity = wheelCity;
		wheelViewCity.setVisibleItems(visibleItems);
		mCityAdapter = new WheelListAdapter(mContext, cityInfo, R.layout.wheel_layout, wheelCity);
		wheelViewCity.setViewAdapter(mCityAdapter);
		if(cityInfo.length<3&&cityInfo.length>=1)
			wheelViewCity.setCurrentItem(0);
		else
			wheelViewCity.setCurrentItem(3);
	}
	
	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities()
	{
		for(int p=0;p<this.proList.size();p++){
			ProvinceInfo pro=this.proList.get(p);
			if(pro.getProvinceName().equals(proName))
				proIndex=pro.getProvinceid();
		}
		List<CityInfo> cities = cityMap.get(proIndex);
				
		this.cityInfo=new String[cities.size()];
		for(int c=0;c<cities.size();c++)
		{
			CityInfo ci=cities.get(c);
			this.cityInfo[c]=ci.getCityName();
		}
		
		WheelView wheelCity=dialogView.getWheelCity();
		dialogView.refreshCityWheel(wheelCity, cityInfo);
		wheelViewCity = dialogView.getWheelCity();
		wheelViewCity.setVisibleItems(5);
		mCityAdapter = new WheelListAdapter(mContext, cityInfo, R.layout.wheel_layout, dialogView.getWheelCity());
		wheelViewCity.setViewAdapter(mCityAdapter);
		if(this.cityInfo.length<3&&this.cityInfo.length>=1)
			wheelViewCity.setCurrentItem(0);
		else
			wheelViewCity.setCurrentItem(3);
	}
	 
	/**
	 * 在选择完以后要执行的事件
	 * 
	 * @param view
	 * @param text
	 */
	public void setTextToView(View view, String text) {

		if (view instanceof TextView) {
			TextView mTextView = (TextView) view;
			mTextView.setText(text);
		}

		else if (view instanceof EditText) {
			EditText mEditText = (EditText) view;
			mEditText.setText(text);
		}
	}

}
