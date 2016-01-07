package com.goldenasia.lottery.view;

import java.math.BigDecimal;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.GlobalParams;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.AddUserInfo;
import com.goldenasia.lottery.bean.AddUserQuota;
import com.goldenasia.lottery.engine.SettingInfoEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.AddUserQuotaElement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.CustomDialog;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.controls.SegmentedControlView;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.TitleManager;

public class AddUser extends BaseUI implements SeekBar.OnSeekBarChangeListener {

	private TextView mRebate;

	// private SegmentedRadioGroup segmentText;
	private SegmentedControlView ascv;
	private EditText username;
	private EditText password;
	private SeekBar seekBar;
	private TextView adduserNorm;
	private TextView maxPoint; 
	private Button addaccountBut;
	
	private String userType=null;
	
	private AddUserQuota addUserQuota=new AddUserQuota();
	
	public AddUser(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		showInMiddle = (ViewGroup) View.inflate(context, R.layout.nb_page_add_user, null);

		LinearLayout mainLayout = (LinearLayout) findViewById(R.id.nb_add_user_keyboard);
		mainLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (in.isActive()) {
					in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
				}
			}
		});
		LinearLayout holder = (LinearLayout) findViewById(R.id.ascv_sample_holder);
		try {
			ascv = new SegmentedControlView(context);
			ascv.setColors(Color.parseColor("#FFC125"),Color.parseColor("#FFFFFF"),Color.TRANSPARENT,Color.parseColor("#FFC125"));
			ascv.setItems(new String[] { "会员用户", "代理用户" }, null);
			ascv.setDefaultSelection(0);//ascv_stretch
			ascv.setStretch(true);
			holder.addView(ascv);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		username=(EditText)findViewById(R.id.nb_adduser_username);
		password=(EditText)findViewById(R.id.nb_adduser_password);

		// 与“系统默认SeekBar”对应的TextView
		mRebate = (TextView) findViewById(R.id.nb_seekbar_title_def);
		seekBar = (SeekBar) findViewById(R.id.timeline);
		
		adduserNorm=(TextView)findViewById(R.id.nb_adduser_norm_label);
		maxPoint=(TextView)findViewById(R.id.nb_adduser_maxpoint);
		addaccountBut=(Button)findViewById(R.id.nb_pageuser_addaccount_but);
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		// segmentText.setOnCheckedChangeListener(this);
		ascv.setOnCheckedChangeListener(new SegmentedControlView.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (ascv.getChecked()) {
					case "会员用户":
						userType="0";
						break;
					case "代理用户":
						userType="1";
						break;
					default:
						break;
				}
			}
		});
		seekBar.setMax(0);
		seekBar.setOnSeekBarChangeListener(this);
		addaccountBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getRequestData();
			}
		});
	}

	@Override
	public void onResume() {
		TitleManager.getInstance().changeSettingTitle("增加用户");
		getInitTargetInfo();
		username.setText("");
		password.setText("");
		userType="0";
		super.onResume();
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstantValue.VIEW_ADD_USER;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		float value = (float) (progress / 10.0);
		mRebate.setText(value + "");
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 初始化
	 */
	private void getInitTargetInfo() {
		
		new MyHttpTask<String>() {

			@Override
			protected Message doInBackground(String... params) {
				SettingInfoEngine engine = BeanFactory.getImpl(SettingInfoEngine.class);
				return engine.addUserQuota();
			}

			@Override
			protected void onPostExecute(Message result) {
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						AddUserQuotaElement userQuotaEle=(AddUserQuotaElement)result.getBody().getElements().get(0);
						addUserQuota=userQuotaEle.getAddUserQuota();
						adduserNorm.setText(userQuotaEle.getAddUserQuota().getUserquota());
						maxPoint.setText(userQuotaEle.getAddUserQuota().getMaxpoint());
						float quota=Float.parseFloat(userQuotaEle.getAddUserQuota().getUsermaxpoint());
						quota=quota*10;
						seekBar.setMax((int)quota);
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
						}
					}
				}else{
					seekBar.setMax((int)GlobalParams.POINT);
					getInitTargetInfo();
				}
				
				super.onPostExecute(result);
			}
		}.executeProxy("1");
	}
	
	/**
	 * 提交修改数据
	 */
	private void getRequestData()
	{
		if (TextUtils.isEmpty(username.getText().toString())) {
			Toast.makeText(context, "用户名不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		
		if (TextUtils.isEmpty(password.getText().toString())) {
			Toast.makeText(context, "密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		BigDecimal maxPointBd = new BigDecimal(maxPoint.getText().toString());
		maxPointBd = maxPointBd.setScale(1, BigDecimal.ROUND_HALF_UP);
		double maxPoint= maxPointBd.doubleValue();
		
		BigDecimal mRebateBd = new BigDecimal(mRebate.getText().toString());
		mRebateBd = mRebateBd.setScale(1, BigDecimal.ROUND_HALF_UP);
		double rebate= mRebateBd.doubleValue();
		double userrebate=maxPoint-rebate;
		
		final AddUserInfo adduser=new AddUserInfo();
		adduser.setUsername(username.getText().toString());
		adduser.setUserpass(password.getText().toString());
		adduser.setNickname(username.getText().toString());
		adduser.setCellphone("1");
		adduser.setKeeppoint(userrebate+"");
		adduser.setUsertype(userType);
		adduser.setPgtype("");
		
		String promptInfo="";
		promptInfo+="用户名："+username.getText().toString()+"\n";
		promptInfo+="密\t码："+password.getText().toString()+"\n";
		promptInfo+="昵\t称："+username.getText().toString()+"\n";
		promptInfo+="返\t点："+userrebate+"\n";
		
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(promptInfo);
		builder.setTitle("开户-提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//设置你的操作事项
				getSubmitData(adduser);
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
		
	}
	
	private void getSubmitData(AddUserInfo adduser)
	{
		new MyHttpTask<AddUserInfo>() {
			
			protected void onPreExecute() {
				// 显示滚动条
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}

			@Override
			protected Message doInBackground(AddUserInfo... params) {
				SettingInfoEngine engine = BeanFactory.getImpl(SettingInfoEngine.class);
				return engine.addUserData(params[0]);
			}
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				if(result!=null)
				{
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						PromptManager.showToast(context, oelement.getErrormsg());
						username.setText("");
						password.setText("");
						mRebate.setText("0.0");
						seekBar.setProgress(0);
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
						}
					}
				}else {
					PromptManager.showToast(context, "服务器忙，请稍后重试……");
				}
				super.onPostExecute(result);
			}
		}.executeProxy(adduser);
	}
	
	// @Override
	// public void onCheckedChanged(RadioGroup group, int checkedId) {
	// // TODO Auto-generated method stub
	// Toast.makeText(context, checkedId, Toast.LENGTH_SHORT);
	// if (checkedId == R.id.button_one) {
	// mToast.setText("One");
	// mToast.show();
	// } else if (checkedId == R.id.button_two) {
	// mToast.setText("Two");
	// mToast.show();
	// }
	// }

}
