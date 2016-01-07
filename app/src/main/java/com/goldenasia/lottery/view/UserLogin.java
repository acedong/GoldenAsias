package com.goldenasia.lottery.view;

import java.io.InputStream;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.GlobalParams;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.User;
import com.goldenasia.lottery.engine.CommonInfoEngine;
import com.goldenasia.lottery.engine.UserEngine;
import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.BalanceElement;
import com.goldenasia.lottery.net.protocal.element.SpeciesListElements;
import com.goldenasia.lottery.net.protocal.element.VersionElement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.ParseXmlService;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.util.UpdateManager;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.HallMiddleManager;

/**
 * 用户登陆+余额获取 两个登录入口：主动登录（购彩大厅）；被动登录（购物车）
 */
public class UserLogin extends BaseUI {

	private EditText username;
	private ImageView clear;// 清空用户名
	private EditText password;
	private ImageView mCheckSwithcButton;
	private Button nbLoginButton;
	private CheckBox nbRemPassword, nbAutoLogin;
	private boolean mbDisplayFlg = false;
	private SharedPreferences sharedPreferences;
	private Editor ed;

	private TextView usernameTitle;
	private TextView pswTitle;
	private TextView versionText;
	public UserLogin(Context context) {
		super(context);
	}

	@Override
	public void init() {
		showInMiddle = (ViewGroup) View.inflate(context, R.layout.nb_userlogin, null);

		LinearLayout mainLayout = (LinearLayout) findViewById(R.id.view_2);
		mainLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (in.isActive()) {
					in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
				}
			}
		});

		usernameTitle=(TextView)findViewById(R.id.et_username_title);
		pswTitle=(TextView)findViewById(R.id.et_psw_title);
		
		
		username = (EditText) findViewById(R.id.nb_user_login_username);
		clear = (ImageView) findViewById(R.id.nb_clear);
		password = (EditText) findViewById(R.id.nb_user_login_password);

		mCheckSwithcButton = (ImageView) findViewById(R.id.btnPassword);

		nbRemPassword = (CheckBox) findViewById(R.id.nb_rememberpassword);
		nbAutoLogin = (CheckBox) findViewById(R.id.nb_automaticdoors);

		nbLoginButton = (Button) findViewById(R.id.nb_loginbutton);
		
		versionText =(TextView)findViewById(R.id.version_no);
		
		sharedPreferences = context.getSharedPreferences("nbuserinfo",Context.MODE_WORLD_READABLE);  
		ed = sharedPreferences.edit();
	}

	@Override
	public void setListener() {
		
		String usernameIco=context.getResources().getString(R.string.fa_user);
		usernameTitle.setText(usernameIco);
		usernameTitle.setTypeface(font);
		usernameTitle.setTextSize(18);
		usernameTitle.setTextColor(context.getResources().getColor(R.color.gray));
		
		username.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (username.getText().toString().length() > 0) {
					clear.setVisibility(View.VISIBLE);
				}

			}
		});

		username.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		String passwordIco=context.getResources().getString(R.string.fa_lock);
		pswTitle.setText(passwordIco);
		pswTitle.setTypeface(font);
		pswTitle.setTextSize(18);
		pswTitle.setTextColor(context.getResources().getColor(R.color.gray));

		password.setOnEditorActionListener(new OnEditorActionListener() {

			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// submit status???
				if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					loginVerifyInfo();
					InputMethodManager in = (InputMethodManager) context
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					if (in.isActive()) {
						in.hideSoftInputFromWindow(
								v.getApplicationWindowToken(), 0);
					}
				}
				return false;
			}
		});
		
		
		nbRemPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			// TODO Auto-generated method stub
				Boolean isChecked1 = nbRemPassword.isChecked();
				ed.putBoolean("ISCHECK", isChecked1);
				ed.commit();
			}
		});
		
		// 设置自动登录默认为不点击
		Boolean value1 = sharedPreferences.getBoolean("AUTO_ISCHECK", false);
		nbAutoLogin.setChecked(value1);
		nbAutoLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			// TODO Auto-generated method stub
				Boolean isChecked2 = nbAutoLogin.isChecked();
				ed.putBoolean("AUTO_ISCHECK", isChecked2);
				ed.commit();
			}
		});

		clear.setOnClickListener(this);
		mCheckSwithcButton.setOnClickListener(this);
		nbLoginButton.setOnClickListener(this);
		
		username.setText(sharedPreferences.getString("nb_name", ""));
		// 从SharedPreferences里边取出 记住密码的状态
		if (sharedPreferences.getBoolean("ISCHECK", false)) {
			// 将记住密码设置为被点击状态
			nbRemPassword.setChecked(true);
			// 然后将值赋值给EditText
			
			password.setText(sharedPreferences.getString("nb_pass", ""));
			// 获取自动登录按钮的状态
			if (sharedPreferences.getBoolean("AUTO_ISCHECK", false)) {
			// 设置自动登录被点击 然后实现跳转
				nbAutoLogin.setChecked(true);
			}
		}
		
		if (nbRemPassword.isChecked() && nbAutoLogin.isChecked()) {
			loginVerifyInfo();
		}
		
		HashMap<String, String> mHashMap= new HashMap<String, String>();
		InputStream inStream = ParseXmlService.class.getClassLoader().getResourceAsStream("version.xml");
		// 解析XML文件。 由于XML文件比较小，因此使用DOM方式进行解析
		ParseXmlService service = new ParseXmlService();
		try
		{
			mHashMap = service.parseXml(inStream);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (null != mHashMap)
		{
			String versionnoInfo = context.getResources().getString(R.string.is_client_version_no);
			versionnoInfo = StringUtils.replaceEach(versionnoInfo, new String[] {"VERSION"}, new String[] { mHashMap.get("version")});
			versionText.setText(versionnoInfo);
		}
		
		
	}

	OnKeyListener onKey = new OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {

			// TODO Auto-generated method stub
			if (keyCode == KeyEvent.KEYCODE_ENTER) {

				InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

				if (imm.isActive()) {
					imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),
							0);
				}
				return true;
			}
			return false;
		}

	};
	
	@Override
	public void onResume() {
		getDataRequest();
		GlobalParams.isLogin = false;
		GlobalParams.USERNAME = "";
		GlobalParams.NICKNAME = "";
		GlobalParams.PARENTID = "";
		GlobalParams.USERTYPE = "";
		GlobalParams.ISFROZEN = "";
		GlobalParams.ISSECURITYPWD = false;
		GlobalParams.isRemPassword=false;
		GlobalParams.PASSWORD = "";
		GlobalParams.POINT = 0;
		super.onResume();
	}

	@Override
	public int getID() {
		return ConstantValue.VIEW_LOGIN;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nb_clear:
			// 清除用户名
			username.setText("");
			clear.setVisibility(View.INVISIBLE);
			break;
		case R.id.btnPassword:

			if (!mbDisplayFlg) {// 显示密码
				password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				mCheckSwithcButton.setImageResource(R.drawable.id_eye_show);
			} else {// 隐藏密码
				password.setTransformationMethod(PasswordTransformationMethod.getInstance());
				mCheckSwithcButton.setImageResource(R.drawable.id_eye_hide);
			}

			mbDisplayFlg = !mbDisplayFlg;
			password.postInvalidate();
			break;
		case R.id.nb_loginbutton:
			// 登录分为两种 1、自动登录。 2手动登录
			// 用户输入信息
			InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (in.isActive()) {
				in.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
			}
			loginVerifyInfo();

			break;
		}
	}

	/**
	 * 用户信息判断
	 * 
	 * @return
	 */
	private boolean checkUserInfo() {
		Boolean flag = false;  
        try {  
            //此处为调用web服务，验证用户名密码的服务，特此省略  
            flag = true;  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
        }  
  
        return flag;  
	}

	private void loginVerifyInfo() {
		// 将信息存入到users里面
		
		if (TextUtils.isEmpty(username.getText().toString())) {
			Toast.makeText(context, "请输入用户名", Toast.LENGTH_LONG).show();
			return;
		}
		if (TextUtils.isEmpty(password.getText().toString())) {
			Toast.makeText(context, "请输入密码", Toast.LENGTH_LONG).show();
			return;
		}
		if (checkUserInfo()) {
			// 登录
			User user = new User();
			user.setUsername(username.getText().toString());
			user.setPassword(password.getText().toString());
			user.setCellphone("1");
			user.setRempassword(nbRemPassword.isChecked());
			user.setAutoLogin(nbAutoLogin.isChecked());
			
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
							
							ed.putString("nb_name", params[0].getUsername());
                    		ed.putString("nb_pass", params[0].getPassword());
                    		ed.commit();
                            
							// 成功了获取余额
							Message balance = engine.getBalance();
							if (balance != null) {
								oelement = balance.getBody().getOelement();
								if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
									BalanceElement element = (BalanceElement) balance.getBody().getElements().get(0);
									String money = element.getInvestvalues();
									GlobalParams.MONEY = Double.parseDouble(money);
									
									return balance;
								}
							}
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
							if(GlobalParams.ISFROZEN.equals("0")){
								if(GlobalParams.PARENTID.equals("0")){
									PromptManager.closeProgressDialog();
									HallMiddleManager.getInstance().changeUI(GameHistory.class);
								}else{
									getTicketDataRequest();
								}
							}
						} else {
							PromptManager.closeProgressDialog();
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
	}
	
	/**
	 * 获取请求列表信息 
	 */
	private void getTicketDataRequest() {
		new MyHttpTask<Integer>() {
			
			@Override
			protected com.goldenasia.lottery.net.protocal.Message doInBackground(Integer... params) {
				// 获取数据——业务的调用
				CommonInfoEngine engine = BeanFactory.getImpl(CommonInfoEngine.class);
				return engine.getSpeciesListInfo(params[0]);
			}

			@Override
			protected void onPostExecute(com.goldenasia.lottery.net.protocal.Message result) {
				PromptManager.closeProgressDialog();
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();

					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						speciesChangeNotice(result.getBody().getElements().get(0));
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
						}
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					PromptManager.showToast(context, "网络状态差，请重试！");
				}
				super.onPostExecute(result);
			}
		}.executeProxy(0);
		
	}
	/**
	 * 修改界面提示信息
	 * @param element
	 */
	protected void speciesChangeNotice(Element  element) {
		SpeciesListElements speciesElement = (SpeciesListElements) element;
		bundle=new Bundle();
		bundle.putBoolean("flag", true);
		bundle.putSerializable("species",speciesElement);  
		issueAllMap=speciesElement.getIssueAllMap();
		menuMap=speciesElement.getMenuMap();
		initMenuArrayData();
		appendIssueInfo();
		HallMiddleManager.getInstance().changeUI(LotteryHomeHall.class,bundle);
	}
	
	/**
	 * 取版本号
	 */
	private int r=0;
	private void getDataRequest() {
		new MyHttpTask<Integer>() {
			@Override
			protected Message doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				UserEngine engine = BeanFactory.getImpl(UserEngine.class);
				return engine.Version();
			}

			@Override
			protected void onPostExecute(Message result) {
				// 更新界面
				if (result != null) {
					r=0;
					Oelement oelement = result.getBody().getOelement();
		
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						// LotteryMenuElement element = (LotteryMenuElement)
						VersionElement element = (VersionElement) result.getBody().getElements().get(0);
						String versionno = element.getVersionno();
						UpdateManager manager = new UpdateManager(context);
						// 检查软件更新
						manager.checkUpdate(versionno);
						nbLoginButton.setEnabled(true);
						nbLoginButton.setTextColor(context.getResources().getColor(R.color.white));
						nbLoginButton.setBackgroundResource(R.drawable.nb_fillet_red_backdrop_button);
						nbLoginButton.setText(context.getResources().getString(R.string.nb_login_button_label));
						/*if(!ConstantValue.VERSION.equals(versionno))
						{
							//更新版本操作
							PromptManager.showToast(context, "版本太低新更新");
						}*/
					} else {
						PromptManager.showToast(context, oelement.getErrormsg());
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					r=r+1;
					if(r<3){
						getDataRequest();
					}else{
						nbLoginButton.setEnabled(true);
						nbLoginButton.setTextColor(context.getResources().getColor(R.color.white));
						nbLoginButton.setBackgroundResource(R.drawable.nb_fillet_red_backdrop_button);
						nbLoginButton.setText(context.getResources().getString(R.string.nb_login_button_label));
					}
				}
				super.onPostExecute(result);

			}
		}.executeProxy(0);
	}
}
