package com.goldenasia.lottery.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.GlobalParams;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.InfoList;
import com.goldenasia.lottery.bean.PassWord;
import com.goldenasia.lottery.bean.SettingItemClump;
import com.goldenasia.lottery.bean.SettingItemEntity;
import com.goldenasia.lottery.bean.User;
import com.goldenasia.lottery.engine.UserEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.BalanceElement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.CustomDialog;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.adapter.SetTitleAdapter;
import com.goldenasia.lottery.view.controls.IUScrollView;
import com.goldenasia.lottery.view.controls.IUScrollView.OnFirstChildOnTopListener;
import com.goldenasia.lottery.view.controls.IUScrollView.OnGetHeightListener;
import com.goldenasia.lottery.view.custom.MyListView;
import com.goldenasia.lottery.view.custom.RoundImageView;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.BottomManager;
import com.goldenasia.lottery.view.manager.HallMiddleManager;

/**
 * 我的彩票大厅
 * 
 * @author Ace
 *
 */
public class MySelfHall extends BaseUI {
	
	//private View mHeaderView;
	
	private RoundImageView avatarImg;
	private TextView usernameText;
	private Button addMember;
	private TextView balance;
	private TextView fpoint;
	private TextView withdrawalsBut;

	private IUScrollView scrollView;
	private MyListView listView;
	
	private Button quitSystem;

	public MySelfHall(Context context) {
		super(context);
	}

	public void init() {
		showInMiddle = (ViewGroup) View.inflate(context, R.layout.nb_lottery_myself_hall, null);
		
		//mHeaderView = LayoutInflater.from(context).inflate(R.layout.nb_lottery_myself_hall_header_userinfo, null);
		
		avatarImg = (RoundImageView) findViewById(R.id.nb_user_avatarimg_info);
		usernameText = (TextView) findViewById(R.id.nb_username_info);
		balance = (TextView) findViewById(R.id.nb_balance_info);
		fpoint = (TextView) findViewById(R.id.nb_fpoint_info);
		
		addMember=(Button) findViewById(R.id.nb_add_member);
		
		withdrawalsBut = (TextView) findViewById(R.id.nb_withdrawals_but);

		scrollView = (IUScrollView) findViewById(R.id.scrollView);
		listView = (MyListView) findViewById(R.id.lv_secondView);

		
		List<SettingItemClump> data = createTestData();
		SetTitleAdapter customAdapter = new SetTitleAdapter(context, data);
		listView.setAdapter(customAdapter);

		quitSystem = (Button) findViewById(R.id.nb_myself_quit_system);
		
	}

	@Override
	public void setListener() {
		scrollView.setOnGetHeightListener(new OnGetHeightListener() {

			@Override
			public void onGetHeight(int height) {
				// TODO Auto-generated method stub
				LinearLayout.LayoutParams params = (LayoutParams) listView.getLayoutParams();
				params.height = height;
				listView.setLayoutParams(params);
			}
		});

		scrollView.setOnFirstChildOnTopListener(new OnFirstChildOnTopListener() {
			@Override
			public boolean onFirstChildOnTop() {
				// TODO Auto-generated method stub
				return listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0;
			}
		});
		
		usernameText.setText("");
		balance.setText("");
		fpoint.setText("");

		withdrawalsBut.setOnClickListener(this);
		
		String plusIco=context.getResources().getString(R.string.fa_plus);
		addMember.setText(plusIco+" 增加会员");
		addMember.setTypeface(font);
		addMember.setOnClickListener(this);
		if(GlobalParams.USERTYPE.equals("1")){
			addMember.setEnabled(true);
			addMember.setVisibility(View.VISIBLE);
		}else{
			addMember.setEnabled(false);
			addMember.setVisibility(View.GONE);
		}
		
		List<SettingItemClump> mData=createTestData();
		SetTitleAdapter setAdapter=new SetTitleAdapter(context, mData);
		listView.setAdapter(setAdapter);

		quitSystem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getLogOut();
			}
			
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.nb_add_member:
				// 添加会员
				if(GlobalParams.USERTYPE.equals("1"))
					HallMiddleManager.getInstance().changeUI(AddUser.class);
				break;
			case R.id.nb_withdrawals_but:
				// 提现
				if (GlobalParams.ISSECURITYPWD)
					HallMiddleManager.getInstance().changeUI(Withdrawals.class);
				else {
					LayoutInflater inflater = LayoutInflater.from(context);
					final View textEntryView = inflater.inflate(R.layout.nb_alert_funds_password, null);
					final TextView secpasserrText = (TextView) textEntryView.findViewById(R.id.nb_funds_password_label_err);
					final EditText secpassInput = (EditText) textEntryView.findViewById(R.id.nb_funds_password_edit);
					secpassInput.setHint("请设置资金密码");

					CustomDialog.Builder builder = new CustomDialog.Builder(context);
					builder.setContentView(textEntryView);
					builder.setTitle("提示-设置资金密码");
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(final DialogInterface dialog, int which) {
							
							//设置你的操作事项
							if (TextUtils.isEmpty(secpassInput.getText().toString())) {
								secpasserrText.setText("请设置资金密码");
								return;
							}

							if (!secpassInput.getText().toString().matches("^[a-z0-9_-]{6,16}$")) {
								secpasserrText.setText("长度 6-16,必须包含数字和字母");
								return;
							}
							
							final PassWord pass = new PassWord("1","2", secpassInput.getText().toString());
							getSettingPassword(pass,dialog,secpasserrText,secpassInput);
							

						}
					});

					builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							secpasserrText.setText("");
							secpassInput.setText("");
						}
					});

					builder.create().show();
				}
				break;
		}
	}
	private int r=0;
	private void getSettingPassword(final PassWord pass,final DialogInterface dialog,final TextView secpasserrText,final EditText secpassInput){
		new MyHttpTask<Integer>() {
			@Override
			protected Message doInBackground(Integer... params) {
				
				UserEngine engine = BeanFactory.getImpl(UserEngine.class);
				Message passmessage = engine.fundsPassword(pass);
				if (passmessage != null) {
					Oelement oelement = passmessage.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						User user = new User();
						user.setUsername(GlobalParams.USERNAME);
						user.setPassword(GlobalParams.PASSWORD);
						user.setCellphone("1");
						Message login = engine.login(user);
						return login;
					}else{
						return passmessage;
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(Message result) {
				if (result != null) {
					r=0;
					// 界面跳转
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
                        // TODO Auto-generated method stub  
						GlobalParams.ISSECURITYPWD=Integer.parseInt(result.getHeader().getSecuritypwd().getTagValue())==0?false:true;
						dialog.dismiss();
						HallMiddleManager.getInstance().changeUI(Withdrawals.class);
						
					} else {
						if (oelement.getErrorcode().equals("113")) {
							secpassInput.setText("");
							secpasserrText.setText(oelement.getErrormsg());
							PromptManager.showToast(context,oelement.getErrormsg());
						} else if (oelement.getErrorcode().equals("108")) {
							dialog.dismiss();
							PromptManager.showToast(context,oelement.getErrormsg());
						} else if(oelement.getErrorcode().equals("208")){
							getLogin();
						}else if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
						}
					}
				} else {
					r=r+1;
					if(r<3){
						getSettingPassword(pass,dialog,secpasserrText,secpassInput);
					}else{
						PromptManager.showToast(context, "网络状态差，请重试！");
					}
					
				}
				super.onPostExecute(result);
			}
		}.executeProxy();
	}
	
	private void getLogin(){
		
		User user = new User();
		user.setUsername(GlobalParams.USERNAME);
		user.setPassword(GlobalParams.PASSWORD);
		user.setCellphone("1");
		new MyHttpTask<User>() {
			
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}
			
			@Override
			protected Message doInBackground(User... params) {

				UserEngine engine = BeanFactory.getImpl(UserEngine.class);
				Message login = engine.login(params[0]);

				if (login != null) {
					Oelement oelement = login.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						// 登录成功了
						GlobalParams.isLogin = true;
						GlobalParams.USERNAME = params[0].getUsername();
						GlobalParams.NICKNAME = login.getHeader().getNickname().getTagValue();
						GlobalParams.PARENTID = login.getHeader().getParentid().getTagValue();
						GlobalParams.USERTYPE = login.getHeader().getUsertype().getTagValue();
						GlobalParams.ISFROZEN = login.getHeader().getIsfrozen().getTagValue();
						GlobalParams.ISSECURITYPWD = Integer.parseInt(login.getHeader().getSecuritypwd().getTagValue())!=0?true:false;
						GlobalParams.isRemPassword=params[0].isRempassword();
						GlobalParams.PASSWORD = params[0].getPassword();
						GlobalParams.POINT = Float.parseFloat(login.getHeader().getFpoint().getTagValue());
						return login;
					} else {
						return login;
					}
				}

				return null;
			}

			@Override
			protected void onPostExecute(Message result) {
				
				if (result != null) {
					// 界面跳转
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						
					} else {
						PromptManager.showToast(context,oelement.getErrormsg());
					}
					// HallMiddleManager.getInstance().goBack();
				} else {
					PromptManager.closeProgressDialog();
					PromptManager.showToast(context, "网络状态差，请重试！");
				}
				super.onPostExecute(result);
			}
		}.executeProxy(user);
	}
	/**
	 *  退出系统
	 */
	private void getLogOut() {
		
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage("您确定要退出系统？");
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, int which) {
				//设置你的操作事项
				GlobalParams.clear();
            	GlobalParams.isLogin=false;
            	InfoList.getInstance().clear();
            	HallMiddleManager.getInstance().changeUI(UserLogin.class);
				HallMiddleManager.getInstance().clear();
				dialog.dismiss();
				new MyHttpTask<Integer>() {
					@Override
					protected Message doInBackground(Integer... params) {
						UserEngine userengine = BeanFactory.getImpl(UserEngine.class);
						return userengine.Logout();
					}
		
					@Override
					protected void onPostExecute(Message result) {
						if (result != null) {
							// 界面跳转
							Oelement oelement = result.getBody().getOelement();
							if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
		                        // TODO Auto-generated method stub  
							} else {
								PromptManager.showToast(context, oelement.getErrormsg());
							}
							// HallMiddleManager.getInstance().goBack();
						} else {
							PromptManager.showToast(context, "网络状态差，请重试！");
						}
						super.onPostExecute(result);
					}
		
				}.executeProxy();
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		builder.create().show();
	}

	@Override
	public void onResume() {
		BottomManager.getInstrance().selectSwitch(4);
		usernameText.setText(GlobalParams.NICKNAME);
		fpoint.setText(GlobalParams.POINT + "");
		balance.setText(GlobalParams.MONEY + "");
		getBalance();
		super.onResume();
	}

	@Override
	public int getID() {
		return ConstantValue.VIEW_MYSELF_HALL;
	}
	private void getBalance(){
		new MyHttpTask<String>() {
			
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}
			
			@Override
			protected Message doInBackground(String... params) {
				UserEngine engine = BeanFactory.getImpl(UserEngine.class);
				return engine.getBalance();
			}
	
			@Override
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				if (result != null) {
					// 界面跳转
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						BalanceElement element = (BalanceElement) result.getBody().getElements().get(0);
						String money = element.getInvestvalues();
						GlobalParams.MONEY = Double.parseDouble(money);
						balance.setText(Double.parseDouble(money) + "");
					} else {
						PromptManager.showToast(context,oelement.getErrormsg());
					}
					// HallMiddleManager.getInstance().goBack();
				}
				super.onPostExecute(result);
			}
		}.executeProxy("0");
	}

	private List<SettingItemClump> createTestData() {

		List<SettingItemClump> dataSetItemList = new ArrayList<SettingItemClump>();
		
		List<SettingItemEntity> setItemMap1=new ArrayList<SettingItemEntity>();
		SettingItemClump setItem1=new SettingItemClump();
		
		SettingItemEntity itemEntity1 = new SettingItemEntity("银行卡绑定", R.string.fa_credit_card);
		SettingItemEntity itemEntity2 = new SettingItemEntity("修改登录密码", R.string.fa_key);
		SettingItemEntity itemEntity3 = new SettingItemEntity("修改资金密码", R.string.fa_unlock_alt);
		setItemMap1.add(itemEntity1);
		setItemMap1.add(itemEntity2);
		setItemMap1.add(itemEntity3);
		setItem1.setItemEntity(setItemMap1);
		setItem1.setTitleStr("我的设置");
		dataSetItemList.add(setItem1);
		
		List<SettingItemEntity> setItemMap2=new ArrayList<SettingItemEntity>();
		SettingItemClump setItem2=new SettingItemClump();
		SettingItemEntity itemEntity4 = new SettingItemEntity("个人消息", R.string.fa_envelope);
		SettingItemEntity itemEntity5 = new SettingItemEntity("系统消息", R.string.fa_clipboard);
		SettingItemEntity itemEntity6 = new SettingItemEntity("活动", R.string.fa_gift);
		setItemMap2.add(itemEntity4);
		setItemMap2.add(itemEntity5);
		setItemMap2.add(itemEntity6);
		setItem2.setItemEntity(setItemMap2);
		setItem2.setTitleStr("消息与公告");
		dataSetItemList.add(setItem2);
		
		List<SettingItemEntity> setItemMap3=new ArrayList<SettingItemEntity>();
		SettingItemClump setItem3=new SettingItemClump();
		SettingItemEntity itemEntity7 = new SettingItemEntity("意见反馈", R.string.fa_comments);
		SettingItemEntity itemEntity8 = new SettingItemEntity("检测更新", R.string.fa_retweet);
		SettingItemEntity itemEntity9 = new SettingItemEntity("关于", R.string.fa_question);
		setItemMap3.add(itemEntity7);
		setItemMap3.add(itemEntity8);
		setItemMap3.add(itemEntity9);
		setItem3.setItemEntity(setItemMap3);
		setItem3.setTitleStr("客户端");
		dataSetItemList.add(setItem3);

		return dataSetItemList;
	}

}
