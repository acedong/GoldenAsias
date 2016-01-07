package com.goldenasia.lottery.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.HallMiddleManager;

/**
 * 用户登录界面
 * @author Ace
 *
 */

public class Login extends BaseUI {
	
	private LinearLayout user_item1;
	private LinearLayout user_item2;
	private LinearLayout user_item3;
	private int screenHeight;
	private int screenWidth;
	//登录信息
	private EditText nbUserName, nbPassword;
	private CheckBox nbRemPassword, nbAutoLogin;
	private Button nbLoginButton;

    private String userNameValue,passwordValue;
	private SharedPreferences sp;
	
	public Login(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.nb_userlogin, null);
		
//		nbUserName=(EditText) findViewById(R.id.nb_username);
//		nbPassword=(EditText) findViewById(R.id.nb_password);
//		nbLoginButton=(Button) findViewById(R.id.nb_loginbutton);
//		
//		nbRemPassword = (CheckBox) findViewById(R.id.nb_rememberpassword);
//		nbAutoLogin = (CheckBox) findViewById(R.id.nb_automaticdoors);
//		
//		
//		//辅助
//		user_item1 = (LinearLayout) findViewById(R.id.user_enrollitem);
//		user_item2 = (LinearLayout) findViewById(R.id.user_forgotpassworditem);
//		user_item3 = (LinearLayout) findViewById(R.id.user_onlineserviceitem);
		

	}


	@Override
	public void setListener() {
		// TODO Auto-generated method stub
//		initLogin();
//		initEvent();
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstantValue.VIEW_LOGIN;
	}
	
	
	/**
	 * 登录页面，登录操作
	 */
	
	private void initLogin()
	{
		//判断记住密码多选框的状态
	      if(sp.getBoolean("ISCHECK", false))
	        {
	    	  //设置默认是记录密码状态
	          nbRemPassword.setChecked(true);
	       	  nbUserName.setText(sp.getString("USER_NAME", ""));
	       	  nbPassword.setText(sp.getString("PASSWORD", ""));
	       	  //判断自动登陆多选框状态
	       	  if(sp.getBoolean("AUTO_ISCHECK", false))
	       	  {
	       		     //设置默认是自动登录状态
	       		     nbAutoLogin.setChecked(true);
	       		    //跳转界面
					HallMiddleManager.getInstance().changeUI(LotteryHomeHall.class);
	       	  }
	        }
			
		    // 登录监听事件  现在默认为用户名为：liu 密码：123
			nbLoginButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					userNameValue = nbUserName.getText().toString();
				    passwordValue = nbPassword.getText().toString();
				    
					if(userNameValue.equals("liu")&&passwordValue.equals("123"))
					{
						PromptManager.showToast(context, "登录成功");
						//登录成功和记住密码框为选中状态才保存用户信息
						if(nbRemPassword.isChecked())
						{
						 //记住用户名、密码、
						  Editor editor = sp.edit();
						  editor.putString("USER_NAME", userNameValue);
						  editor.putString("PASSWORD",passwordValue);
						  editor.commit();
						}
						//跳转界面
						HallMiddleManager.getInstance().changeUI(LotteryHomeHall.class);
						
					}else{
						PromptManager.showToast(context, "用户名或密码错误，请重新登录");
					}
					
				}
			});

		    //监听记住密码多选框按钮事件
			nbRemPassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
					if (nbRemPassword.isChecked()) {
	                    
						System.out.println("记住密码已选中");
						sp.edit().putBoolean("ISCHECK", true).commit();
						
					}else {
						
						System.out.println("记住密码没有选中");
						sp.edit().putBoolean("ISCHECK", false).commit();
						
					}

				}
			});
			
			//监听自动登录多选框事件
			nbAutoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
					if (nbAutoLogin.isChecked()) {
						System.out.println("自动登录已选中");
						sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

					} else {
						System.out.println("自动登录没有选中");
						sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
					}
				}
			});
	}

	/**
	 * 登录页面，辅助功能操作
	 */
	
	private void initEvent() {
//		// TODO Auto-generated method stub
//		user_item1.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Bitmap backpic = BitmapFactory.decodeResource(DebugActivity.this.getResources(),R.drawable.dialog_queding_bot);
//				Bitmap newbackpic = BitmapUtil.GetNewBitmap(backpic, screenWidth, screenHeight, screenWidth - 10, 40);
//				BitmapDrawable drawpic = new BitmapDrawable(DebugActivity.this.getResources(), newbackpic);// 将Bitmap转换为Drawable
//				v.setBackgroundDrawable(drawpic);
//			}
//		});
//		user_item2.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				v.setBackgroundColor(Color.parseColor("#FFEFC6"));
//			}
//		});
//		user_item3.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Bitmap backpic = BitmapFactory.decodeResource(DebugActivity.this.getResources(), R.drawable.dialog_queding_1);
//				Bitmap newbackpic = BitmapUtil.GetNewBitmap(backpic, screenWidth, screenHeight, screenWidth - 10, 40);
//				BitmapDrawable drawpic = new BitmapDrawable(DebugActivity.this.getResources(), newbackpic);// 将Bitmap转换为Drawable
//				v.setBackgroundDrawable(drawpic);
//			}
//		});
	}
}