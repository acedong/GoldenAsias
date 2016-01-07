package com.goldenasia.lottery.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.R.raw;
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
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.PassWord;
import com.goldenasia.lottery.engine.UserEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.TitleManager;

/**
 * 帐号密码
 * @author Ace
 *
 */

public class AccountPassword extends BaseUI{
	
	private EditText oldAccountPass;
	private EditText newAccountPass;
	private EditText confirmAccountPass;
	
	private Button confirmSubmit;

	public AccountPassword(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.nb_page_account_password, null);
		oldAccountPass =(EditText) findViewById(R.id.nb_old_accountpass_edit_text);
		newAccountPass = (EditText) findViewById(R.id.nb_new_account_pass_edit_text);
		confirmAccountPass =(EditText) findViewById(R.id.nb_confirm_account_pass_edit_text);
		confirmSubmit=(Button)findViewById(R.id.nb_confirm_accountpass_button);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		newAccountPass.addTextChangedListener(new EditChangedListener());  
		confirmSubmit.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.nb_confirm_accountpass_button:
				InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (in.isActive()) {
					in.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
				}
				getRequestData();
			break;
		}
	}
	
	@Override
	public void onResume() {
		TitleManager.getInstance().changeSettingTitle("修改帐号密码");
		oldAccountPass.setText("");
		newAccountPass.setText("");
		confirmAccountPass.setText("");
		super.onResume();
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstantValue.VIEW_ACCOUNT_PASSWORD;
	}
	/**
	 * 提交修改数据
	 */
	private void getRequestData()
	{
		Pattern p=Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");   
		
		if (TextUtils.isEmpty(oldAccountPass.getText().toString())) {
			Toast.makeText(context, "当前密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		
		if (TextUtils.isEmpty(newAccountPass.getText().toString())) {
			Toast.makeText(context, "新密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		Matcher newAccountM=p.matcher(newAccountPass.getText().toString()); 
		if(!newAccountM.find()){
			Toast.makeText(context, "新密码格式不正确", Toast.LENGTH_LONG).show();
			return;
		}
		
		if (TextUtils.isEmpty(confirmAccountPass.getText().toString())) {
			Toast.makeText(context, "确认密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		
		Matcher confirmAccountPassM=p.matcher(confirmAccountPass.getText().toString()); 
		if(!confirmAccountPassM.find()){
			Toast.makeText(context, "确认格式密码格式不正确", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(!newAccountPass.getText().toString().equals(confirmAccountPass.getText().toString())){
			Toast.makeText(context, "确认格式密码与新密码不相同，请检查", Toast.LENGTH_LONG).show();
			return;
		}
		
		PassWord password=new PassWord();
		password.setCellphone("1");
		password.setType("1");
		password.setNewpass(newAccountPass.getText().toString());
		password.setOldpass(oldAccountPass.getText().toString());
		
		
		new MyHttpTask<PassWord>() {
			
			protected void onPreExecute() {
				// 显示滚动条
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}

			@Override
			protected Message doInBackground(PassWord... params) {
				UserEngine engine = BeanFactory.getImpl(UserEngine.class);
				return engine.loginPassword(params[0]);
			}
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				if(result!=null)
				{
					PromptManager.closeProgressDialog();
					Oelement oelement = result.getBody().getOelement();
					
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						oldAccountPass.setText("");
						newAccountPass.setText("");
						confirmAccountPass.setText("");
						PromptManager.showToast(context, oelement.getErrormsg());
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
						}
					}
				}else {
					PromptManager.showToast(context, "网络状态差，稍后重试。");
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
	           editStart = newAccountPass.getSelectionStart();  
	           editEnd = newAccountPass.getSelectionEnd(); 
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
	               newAccountPass.setText(s);  
	               newAccountPass.setSelection(tempSelection);  
	           }  
	  
	       }  
	   };  
}
