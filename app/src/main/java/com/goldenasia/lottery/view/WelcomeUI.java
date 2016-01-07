package com.goldenasia.lottery.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.engine.UserEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.HallMiddleManager;

/**
 * 第二个简答的界面
 * 
 * @author Administrator
 * 
 */
public class WelcomeUI extends BaseUI implements AnimationListener{
	private ImageView  imageView = null;   
	private ImageView  imageView2 = null;   
    private Animation alphaAnimation = null;  
    private Animation alphaAnimation2 = null;  
    private boolean flag=false;
	public WelcomeUI(Context context) {
		super(context);
	}

	/**
	 * 初始化：调用一次
	 */
	public void init() {
		// 简单界面：
		showInMiddle = (ViewGroup) View.inflate(context, R.layout.nb_welcome, null);
		imageView = (ImageView)findViewById(R.id.welcome_image_view);
		
		imageView2 = (ImageView)findViewById(R.id.welcome_image2_view);
	}

	@Override
	public int getID() {
		return ConstantValue.VIEW_SECOND;
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		alphaAnimation = AnimationUtils.loadAnimation(context, R.anim.welcome_alpha);   
        alphaAnimation.setFillEnabled(true); //启动Fill保持   
        alphaAnimation.setFillAfter(true);  //设置动画的最后一帧是保持在View上面   
        alphaAnimation.setAnimationListener(this);  //为动画设置监听   
		imageView.setAnimation(alphaAnimation); 
		
		alphaAnimation2 = AnimationUtils.loadAnimation(context, R.anim.welcome_alpha);   
        alphaAnimation2.setFillEnabled(true); //启动Fill保持   
        alphaAnimation2.setFillAfter(true);  //设置动画的最后一帧是保持在View上面   
        alphaAnimation2.setAnimationListener(this);  //为动画设置监听   
		imageView2.setAnimation(alphaAnimation2); 
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		getDataRequest();
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		HallMiddleManager.getInstance().changeUI(UserLogin.class);
		HallMiddleManager.getInstance().clear();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 获取api路径
	 */
	
	private void getDataRequest() {
		new MyHttpTask<Integer>() {
			
			@Override
			protected Message doInBackground(Integer... params) {
				// 获取数据——业务的调用
				UserEngine engine = BeanFactory.getImpl(UserEngine.class);
				return engine.takeApiPathTxT();
			}

			@Override
			protected void onPostExecute(Message result) {
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						flag=true;
					}else{
						flag=true;
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					flag=true;
				}
				super.onPostExecute(result);
			}
		}.executeProxy(0);
	}
}
