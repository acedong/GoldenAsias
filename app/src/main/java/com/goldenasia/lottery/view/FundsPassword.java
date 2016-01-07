package com.goldenasia.lottery.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.GlobalParams;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.PassWord;
import com.goldenasia.lottery.engine.UserEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.AccountPassword.EditChangedListener;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.TitleManager;

/**
 * 资金密码
 * @author Ace
 *
 */

public class FundsPassword extends BaseUI{
	private LinearLayout settingFundsArea;
	private LinearLayout modifyFundsArea;
	
	private EditText settingFundsPass;
	
	private EditText oldFundsPass;
	private EditText newFundsPass;
	private EditText confirmFundsPass;
	
	private Button confirmSubmit;

	public FundsPassword(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.nb_page_funds_password, null);
		
		settingFundsArea=(LinearLayout)findViewById(R.id.nb_setting_fundspass_area);
		modifyFundsArea=(LinearLayout)findViewById(R.id.nb_modify_fundspass_area);
		
		settingFundsPass =(EditText) findViewById(R.id.nb_setting_fundspass_edit_text);
		
		oldFundsPass =(EditText) findViewById(R.id.nb_old_fundspass_edit_text);
		newFundsPass = (EditText) findViewById(R.id.nb_new_funds_pass_edit_text);
		confirmFundsPass =(EditText) findViewById(R.id.nb_confirm_funds_pass_edit_text);
		
		confirmSubmit=(Button)findViewById(R.id.nb_confirm_fundspass_button);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		settingFundsPass.addTextChangedListener(new EditChangedListener());  
		newFundsPass.addTextChangedListener(new EditChangedListener());  
		confirmSubmit.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.nb_confirm_fundspass_button:
				InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (in.isActive()) {
					in.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
				}
				
				if(GlobalParams.ISSECURITYPWD)
					getRequestData();
				else
					getSettingRequestData();
			break;
		}
	}
	
	@Override
	public void onResume() {
		settingFundsPass.setText("");
		oldFundsPass.setText("");
		newFundsPass.setText("");
		confirmFundsPass.setText("");
		
		hideSwitchFundsArea();
		super.onResume();
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstantValue.VIEW_FUNDS_PASSWORD;
	}
	
	private void hideSwitchFundsArea()
	{
		if(GlobalParams.ISSECURITYPWD){
			TitleManager.getInstance().changeSettingTitle("修改资金密码");
			settingFundsArea.setVisibility(View.GONE);
			modifyFundsArea.setVisibility(View.VISIBLE);
		}else{
			TitleManager.getInstance().changeSettingTitle("设置资金密码");
			settingFundsArea.setVisibility(View.VISIBLE);
			modifyFundsArea.setVisibility(View.GONE);
		}
	}

	/**
	 * 提交设置资金密码
	 */
	private void getSettingRequestData()
	{
		Pattern p=Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");   
		
		if (TextUtils.isEmpty(settingFundsPass.getText().toString())) {
			Toast.makeText(context, "资金密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		
		Matcher newAccountM=p.matcher(settingFundsPass.getText().toString()); 
		if(!newAccountM.find()){
			Toast.makeText(context, "资金密码格式不正确", Toast.LENGTH_LONG).show();
			return;
		}
		
		PassWord password=new PassWord();
		password.setCellphone("1");
		password.setType("2");
		password.setPassword(settingFundsPass.getText().toString());
		
		new MyHttpTask<PassWord>() {
			
			protected void onPreExecute() {
				// 显示滚动条
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}

			@Override
			protected Message doInBackground(PassWord... params) {
				UserEngine engine = BeanFactory.getImpl(UserEngine.class);
				return engine.fundsPassword(params[0]);
			}
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				if(result!=null)
				{
					PromptManager.closeProgressDialog();
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						GlobalParams.ISSECURITYPWD=true;
						settingFundsPass.setText("");
						
						PromptManager.showToast(context, oelement.getErrormsg());
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
		}.executeProxy(password);
	}
	
	/**
	 * 提交修改数据
	 */
	private void getRequestData()
	{
		Pattern p=Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");   
		
		if (TextUtils.isEmpty(oldFundsPass.getText().toString())) {
			Toast.makeText(context, "当前资金密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		
		if (TextUtils.isEmpty(newFundsPass.getText().toString())) {
			Toast.makeText(context, "新资金密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		Matcher newFundsM=p.matcher(newFundsPass.getText().toString()); 
		if(!newFundsM.find()){
			Toast.makeText(context, "新资金密码格式不正确", Toast.LENGTH_LONG).show();
			return;
		}
		
		if (TextUtils.isEmpty(confirmFundsPass.getText().toString())) {
			Toast.makeText(context, "确认资金密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		
		Matcher confirmFundsPassM=p.matcher(confirmFundsPass.getText().toString()); 
		if(!confirmFundsPassM.find()){
			Toast.makeText(context, "确认资金格式密码格式不正确", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(!newFundsPass.getText().toString().equals(confirmFundsPass.getText().toString())){
			Toast.makeText(context, "确认资金格式密码与新资金密码不相同，请检查", Toast.LENGTH_LONG).show();
			return;
		}
		
		PassWord password=new PassWord();
		password.setCellphone("1");
		password.setType("3");
		password.setNewpass(newFundsPass.getText().toString());
		password.setOldpass(oldFundsPass.getText().toString());
		
		
		new MyHttpTask<PassWord>() {
			
			protected void onPreExecute() {
				// 显示滚动条
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}

			@Override
			protected Message doInBackground(PassWord... params) {
				UserEngine engine = BeanFactory.getImpl(UserEngine.class);
				return engine.fundsPassword(params[0]);
			}
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				if(result!=null)
				{
					PromptManager.closeProgressDialog();
					Oelement oelement = result.getBody().getOelement();
					
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						oldFundsPass.setText("");
						newFundsPass.setText("");
						confirmFundsPass.setText("");
						PromptManager.showToast(context, oelement.getErrormsg());
						
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
		}.executeProxy(password);
	}
	
	class EditChangedListener implements TextWatcher {  
	       private CharSequence temp;//监听前的文本  
	       private int editStart;//光标开始位置  
	       private int editEnd;//光标结束位置  
	       private final int charMaxNum = 20;  
	  
	       @Override  
	       public void beforeTextChanged(CharSequence s, int start, int count, int after) {  
	           temp = s;  
	       }  
	  
	       @Override  
	       public void onTextChanged(CharSequence s, int start, int before, int count) {  
	  
	       }  
	  
	       @Override  
	       public void afterTextChanged(Editable s) {  
	           /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */  
	           editStart = newFundsPass.getSelectionStart();  
	           editEnd = newFundsPass.getSelectionEnd(); 
	           if(temp.length()>0){
	        	   confirmSubmit.setEnabled(true);
	        	   confirmSubmit.setTextColor(context.getResources().getColor(R.color.white));
	        	   confirmSubmit.setBackgroundResource(R.drawable.nb_fillet_red_backdrop_button);
	           }else{
	        	   confirmSubmit.setEnabled(false);
	        	   confirmSubmit.setTextColor(context.getResources().getColor(R.color.black));
	        	   confirmSubmit.setBackgroundResource(R.drawable.nb_fillet_gray_backdrop_button);
	           }
	        	  
	           if (temp.length() > charMaxNum) {  
	               Toast.makeText(context, "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();  
	               s.delete(editStart - 1, editEnd);  
	               int tempSelection = editStart;  
	               newFundsPass.setText(s);  
	               newFundsPass.setSelection(tempSelection);  
	           }  
	  
	       }  
	   };  
}
