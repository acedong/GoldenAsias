package com.goldenasia.lottery.view;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.BankInfoLogo;
import com.goldenasia.lottery.bean.InfoList;
import com.goldenasia.lottery.bean.MyCardInfo;
import com.goldenasia.lottery.bean.PassWord;
import com.goldenasia.lottery.bean.WithdrawalsInfo;
import com.goldenasia.lottery.bean.WithdrawalsUserInfo;
import com.goldenasia.lottery.engine.UserEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.MyCardInfoElements;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.CustomDialog;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.util.WheelSingleDialogShowUtil;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.HallMiddleManager;
import com.goldenasia.lottery.view.manager.TitleManager;
import com.goldenasia.lottery.view.widget.DialogSingleView.onWheelBtnPosClick;

/**
 * 取款页面
 * @author Ace
 *
 */

public class Withdrawals extends BaseUI{

	private LinearLayout bankcardsLayout;
	
	private ImageView bankLogo;
	private TextView bankName;
	private TextView bankTailnumber;
	private TextView currencyCardTip;
	private TextView maximumTipRollout;
	private EditText currencyEdit;
	private Button imageBut;
	private Button currencyButton;
	private String secpass="";
	public Withdrawals(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.nb_page_withdrawals, null);
		
		
		bankcardsLayout=(LinearLayout)findViewById(R.id.nb_bankcards_area);
		
		bankLogo=(ImageView) findViewById(R.id.nb_withdrawals_bank_logo);
		bankName=(TextView) findViewById(R.id.nb_withdrawals_bank_name);
		bankTailnumber=(TextView) findViewById(R.id.nb_withdrawals_bank_tailnumber);
		
		currencyCardTip=(TextView) findViewById(R.id.nb_currency_card_tip);
		maximumTipRollout=(TextView) findViewById(R.id.nb_tip_maximum_rollout);
		currencyEdit = (EditText) findViewById(R.id.nb_withdrawals_currency_edit_text);    
		
		
		imageBut=(Button) findViewById(R.id.nb_choose_cards_but);
		currencyButton=(Button)findViewById(R.id.nb_withdrawals_currency_button) ;
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
		currencyEdit.addTextChangedListener(new TextWatcher() {    
            private boolean isChanged = false;    
    
            @Override    
            public void onTextChanged(CharSequence s, int start, int before,    
                    int count) {    
                // TODO Auto-generated method stub    
            }    
    
            @Override    
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {    
                // TODO Auto-generated method stub    
            }    
    
            @Override    
            public void afterTextChanged(Editable s) {    
                // TODO Auto-generated method stub    
                if (isChanged) {// ----->如果字符未改变则返回 
                	currencyButton.setEnabled(false);
            		currencyButton.setTextColor(Color.GRAY);
            		currencyButton.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.nb_fillet_gray_backdrop_button));
                    return;    
                }    
                String str = s.toString();    
    
                isChanged = true;    
                String cuttedStr = str;    
                /* 删除字符串中的dot */    
                for (int i = str.length() - 1; i >= 0; i--) {    
                    char c = str.charAt(i);    
                    if ('.' == c) {    
                        cuttedStr = str.substring(0, i) + str.substring(i + 1);    
                        break;    
                    }    
                }    
                /* 删除前面多余的0 */    
                int NUM = cuttedStr.length();   
                int zeroIndex = -1;  
                for (int i = 0; i < NUM - 2; i++) {    
                    char c = cuttedStr.charAt(i);    
                    if (c != '0') {    
                        zeroIndex = i;  
                        break;  
                    }else if(i == NUM - 3){  
                        zeroIndex = i;  
                        break;  
                    }  
                }    
                if(zeroIndex != -1){  
                    cuttedStr = cuttedStr.substring(zeroIndex);  
                }  
                /* 不足3位补0 */    
                if (cuttedStr.length() < 3) {    
                    cuttedStr = "0" + cuttedStr;    
                }    
                /* 加上dot，以显示小数点后两位 */    
                cuttedStr = cuttedStr.substring(0, cuttedStr.length() - 2)+ "." + cuttedStr.substring(cuttedStr.length() - 2);    
                
                currencyEdit.setText(cuttedStr);    
    
                currencyEdit.setSelection(currencyEdit.length());    
                isChanged = false;  
                
                currencyButton.setEnabled(true);
        		currencyButton.setTextColor(Color.WHITE);
        		currencyButton.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.nb_fillet_red_backdrop_button));
            }    
        });    

		String targetIco=context.getResources().getString(R.string.fa_chevron_right);
		imageBut.setText(targetIco);
		imageBut.setTypeface(font);
		imageBut.setTextColor(Color.GRAY);
		imageBut.setTextSize(18);
		imageBut.setOnClickListener(this);
		bankcardsLayout.setOnClickListener(this);
		currencyButton.setOnClickListener(this);
		currencyButton.setEnabled(false);
		currencyButton.setTextColor(Color.GRAY);
		currencyButton.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.nb_fillet_gray_backdrop_button));
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nb_choose_cards_but:
			btnOnclik(wheelBankUtil, imageBut);
			break;
		case R.id.nb_withdrawals_currency_button:
			InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (in.isActive()) {
				in.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
			}
			
			//输入密码
			LayoutInflater factory = LayoutInflater.from(context);//提示框  
            final View fundsPassView = factory.inflate(R.layout.nb_alert_funds_password, null);//这里必须是final的 
            final TextView fundsPasswordERR=(TextView)fundsPassView.findViewById(R.id.nb_funds_password_label_err);//获得输入框对象
            final EditText fundsPasswordEdit=(EditText)fundsPassView.findViewById(R.id.nb_funds_password_edit);//获得输入框对象  
            
            CustomDialog.Builder builder = new CustomDialog.Builder(context);
    		builder.setContentView(fundsPassView);
    		builder.setTitle("提示-请输入资金密码");
    		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
    			public void onClick(final DialogInterface dialog, int which) {
    				//设置你的操作事项
    				String fundspass=fundsPasswordEdit.getText().toString();
                	if (TextUtils.isEmpty(fundspass)) {
            			fundsPasswordERR.setText("资金密码不能为空");
            			return;
            		}
                	
                	PassWord password=new PassWord();
                	password.setCellphone("1");
                	password.setType("2");
                	password.setPassword(fundsPasswordEdit.getText().toString());
                	
                	if(mybankinfo!=null){
	                	final WithdrawalsInfo withdrawsls = new WithdrawalsInfo();
						withdrawsls.setBankinfo(mybankinfo.getEntry());
						withdrawsls.setMoney(Double.valueOf(currencyEdit.getText().toString()).doubleValue());
						withdrawsls.setSecpass(fundsPasswordEdit.getText().toString());
						getSettingPassword(dialog,password,withdrawsls,fundsPasswordERR,fundsPasswordEdit);
                	}else{
                		CustomDialog.Builder builderTip = new CustomDialog.Builder(context);
                		builderTip.setMessage("暂无绑卡信息，是否进行绑定！");
                		builderTip.setTitle("温馨提示");
                		builderTip.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialogTip, int which) {
								dialog.dismiss();
								dialogTip.dismiss();
								
								HallMiddleManager.getInstance().changeUI(BindingInfoBank.class);
							}
						});
                		builderTip.create().show();
                	}
					
    			}
    		});

    		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				dialog.dismiss();
    			}
    		});

    		builder.create().show();
    		
			break;
		case R.id.nb_bankcards_area:
			if(InfoList.getInstance().getMycardInfoList().size()>0){
				btnOnclik(wheelBankUtil, imageBut);
			}else{
				CustomDialog.Builder builderDialog = new CustomDialog.Builder(context);
				builderDialog.setMessage("暂无绑卡信息，是否进行绑定");
				builderDialog.setTitle("提示");
				builderDialog.setPositiveButton("绑卡信息", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						//设置你的操作事项
						HallMiddleManager.getInstance().changeUI(BindingInfoBank.class);
					}
				});

				builderDialog.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builderDialog.create().show();
			}
			break;
		}
	}
	
	private WithdrawalsUserInfo userinfo=null;
	private MyCardInfo mybankinfo=null;
	private final double balanceLessThan=100;
	@Override
	public void onResume() {
		TitleManager.getInstance().changeSettingTitle("提现");
		getInitDataRequest();
		
		bankName.setText("无绑定银行卡");
		bankName.setTextSize(16);
		bankTailnumber.setText("");
		bankTailnumber.setVisibility(View.GONE);
		
		
		String moneyEditHintText="";
		moneyEditHintText= context.getResources().getString(R.string.is_userinfo_bank_lessthan_balance);
		moneyEditHintText = StringUtils.replaceEach(moneyEditHintText, new String[] {"NUMBER"}, new String[] { String.valueOf(100)});
		currencyEdit.setHint(moneyEditHintText);
		
		String urrencyCardTip = context.getResources().getString(R.string.is_currency_card_tip);
		urrencyCardTip = StringUtils.replaceEach(urrencyCardTip, new String[] {"MINMONEY","MAXMONEY","TIME"}, new String[] { String.valueOf(100), String.valueOf(50000) , "10:00-5:00"});
		currencyCardTip.setText(urrencyCardTip);
		
		String maximunTiprolloutText= context.getResources().getString(R.string.is_maximum_rollout);
		maximunTiprolloutText=StringUtils.replaceEach(maximunTiprolloutText, new String[] {"BOUT","BALANCE"}, new String[] { String.valueOf(1) , String.valueOf(0) });
		maximumTipRollout.setText(maximunTiprolloutText);
		
		super.onResume();
	}
	private int remain=0;
	private void InitTip(){
		
		List<MyCardInfo> userbankInfoList=InfoList.getInstance().getMycardInfoList();
		if(userbankInfoList.size()>0)
		{
			mybankinfo=userbankInfoList.get(0);
			String tailNumberText= context.getResources().getString(R.string.is_userinfo_bank_tail_number);
			tailNumberText = StringUtils.replaceEach(tailNumberText, new String[] {"NUMBER"}, new String[] { mybankinfo.getCardno() });
			
			bankName.setText(mybankinfo.getBankname());
			bankTailnumber.setText(tailNumberText);
			bankTailnumber.setVisibility(View.VISIBLE);
			InitWheelMyBank();
		}else{
			bankName.setText("无绑定银行卡");
			bankName.setTextSize(16);
			bankTailnumber.setText("");
			bankTailnumber.setVisibility(View.GONE);
		}
		
		userinfo=InfoList.getInstance().getUserCashDesk();
		if(userinfo!=null){
			String moneyEditHintText="";
			if(balanceLessThan>=Double.valueOf(userinfo.getUseravailablebalance()).doubleValue())
			{
				moneyEditHintText= context.getResources().getString(R.string.is_userinfo_bank_lessthan_balance);
				moneyEditHintText = StringUtils.replaceEach(moneyEditHintText, new String[] {"NUMBER"}, new String[] { String.valueOf(userinfo.getWithdrawmin())});
			}else
			{
				moneyEditHintText= context.getResources().getString(R.string.is_userinfo_bank_morethan_balance);
				moneyEditHintText = StringUtils.replaceEach(moneyEditHintText, new String[] {"NUMBER"}, new String[] { String.valueOf(userinfo.getUseravailablebalance())});
			}
			currencyEdit.setHint(moneyEditHintText);
			
			String urrencyCardTip = context.getResources().getString(R.string.is_currency_card_tip);
			urrencyCardTip = StringUtils.replaceEach(urrencyCardTip, new String[] {"MINMONEY","MAXMONEY","TIME"}, new String[] { String.valueOf(userinfo.getWithdrawmin()), String.valueOf(userinfo.getWithdrawmax()) , userinfo.getWithdrawtime()});
			currencyCardTip.setText(urrencyCardTip);
			
			String maximunTiprolloutText= context.getResources().getString(R.string.is_maximum_rollout);
			remain=Integer.parseInt(userinfo.getiMaxRequestOfAday())-userinfo.getiMyRequestOfToday();
			maximunTiprolloutText=StringUtils.replaceEach(maximunTiprolloutText, new String[] {"BOUT","BALANCE"}, new String[] { String.valueOf(remain) , String.valueOf(userinfo.getUseravailablebalance()) });
			maximumTipRollout.setText(maximunTiprolloutText);
		}else{
			String moneyEditHintText="";
			if(balanceLessThan>=Double.valueOf(userinfo.getUseravailablebalance()).doubleValue())
			{
				moneyEditHintText= context.getResources().getString(R.string.is_userinfo_bank_lessthan_balance);
				moneyEditHintText = StringUtils.replaceEach(moneyEditHintText, new String[] {"NUMBER"}, new String[] { String.valueOf(userinfo.getWithdrawmin())});
			}else
			{
				moneyEditHintText= context.getResources().getString(R.string.is_userinfo_bank_morethan_balance);
				moneyEditHintText = StringUtils.replaceEach(moneyEditHintText, new String[] {"NUMBER"}, new String[] { String.valueOf(userinfo.getUseravailablebalance())});
			}
			currencyEdit.setHint(moneyEditHintText);
			
			String urrencyCardTip = context.getResources().getString(R.string.is_currency_card_tip);
			urrencyCardTip = StringUtils.replaceEach(urrencyCardTip, new String[] {"MINMONEY","MAXMONEY","TIME"}, new String[] { String.valueOf(userinfo.getWithdrawmin()), String.valueOf(userinfo.getWithdrawmax()) , userinfo.getWithdrawtime()});
			currencyCardTip.setText(urrencyCardTip);
			
			String maximunTiprolloutText= context.getResources().getString(R.string.is_maximum_rollout);
			remain=Integer.parseInt(userinfo.getiMaxRequestOfAday())-userinfo.getiMyRequestOfToday();
			maximunTiprolloutText=StringUtils.replaceEach(maximunTiprolloutText, new String[] {"BOUT","BALANCE"}, new String[] { String.valueOf(remain) , String.valueOf(userinfo.getUseravailablebalance()) });
			maximumTipRollout.setText(maximunTiprolloutText);
		}
			
	}
	
	@SuppressWarnings("unused")
	private void btnOnclik(WheelSingleDialogShowUtil dialogShowUtilBank, View view) {
		dialogShowUtilBank.showWheel();
		WheelSingleDialogShowUtil currentClickWheelBank = dialogShowUtilBank;
		View currentViewBank = view;
	}

	private WheelSingleDialogShowUtil wheelBankUtil;
	private WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	private void InitWheelMyBank() {
		// DisplayMetrics dm = new DisplayMetrics();
		wheelBankUtil = new WheelSingleDialogShowUtil(context, wm.getDefaultDisplay(), InfoList.getInstance().getMycardInfoData(), "请选择已绑定卡信息");

		if (InfoList.getInstance().getMycardInfoData().length > 0) {
			// 默认显示第一条
			wheelBankUtil.setWheelHint(0);
		}
		wheelBankUtil.dialogView.setBtnPosClick(new onWheelBtnPosClick() {
			@Override
			public void onClick(String text, int position) {
				// TODO Auto-generated method stub
				wheelBankUtil.dissmissWheel();
				String tailNO=text.substring(text.length()-4,text.length());
				mybankinfo=InfoList.getInstance().getChooseMycardInfoData(tailNO);
				
				BankInfoLogo logo=InfoList.getInstance().getBankinfoLogo(text.substring(0,text.length()-(text.length()-6)));
				bankLogo.setImageDrawable(context.getResources().getDrawable(logo.getLogoId()));
				bankName.setText(mybankinfo.getBankname());
				
				
				
				String tailNumberText= context.getResources().getString(R.string.is_userinfo_bank_tail_number);
				tailNumberText = StringUtils.replaceEach(tailNumberText, new String[] {"NUMBER"}, new String[] { mybankinfo.getCardno() });
				bankTailnumber.setText(tailNumberText);
			}
		});
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstantValue.VIEW_INFO_WITHDRAWALS;
	}
	private int r=0;
	private void getSettingPassword(final DialogInterface dialog,final PassWord password,final WithdrawalsInfo withdrawsls,final TextView fundsPasswordERR,final EditText fundsPasswordEdit){
		
		new MyHttpTask<PassWord>() {
	
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}
			
			@Override
			protected Message doInBackground(PassWord... params) {
				// 获取数据——业务的调用
				UserEngine engine = BeanFactory.getImpl(UserEngine.class);
				Message passEngine=engine.fundsPassword(params[0]);
	
				if (passEngine != null) {
					Oelement oelement = passEngine.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						Message withEngine = engine.getWithdrawals(withdrawsls);
						return withEngine;
					} else {
						return passEngine;
					}
				}
				return null;
			}
	
			@Override
			protected void onPostExecute(Message result) {
				// 更新界面
				PromptManager.closeProgressDialog();
				if (result != null) {
					r=0;
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						bundle=new Bundle();
						bundle.putSerializable("withdrawsls",withdrawsls);  
						
						HallMiddleManager.getInstance().changeUI(WithdrawalsStatus.class);
						PromptManager.showToast(context, oelement.getErrormsg());
						dialog.dismiss();
					}else{
						if (oelement.getErrorcode().equals("113")) {
							fundsPasswordEdit.setText("");
							fundsPasswordERR.setText(oelement.getErrormsg());
							PromptManager.showToast(context,oelement.getErrormsg());
						} else if (oelement.getErrorcode().equals("108")) {
							PromptManager.showToast(context,oelement.getErrormsg());
						} else if (oelement.getErrorcode().equals("112")) {
							PromptManager.showToast(context,"操作失败, 银行账号已被您在之前成功绑定");
						} else if(oelement.getErrorcode().equals("5102")) {
							PromptManager.showToast(context,"操作失败,暂时不能处理提现，请联系在线客服");
							dialog.dismiss();
						}else if (oelement.getErrorcode().equals("255")) {
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						} else {
							PromptManager.showToast(context,oelement.getErrormsg());
						}
					}
					
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					r=r+1;
					if(r<3){
						fundsPasswordERR.setText("密码错误!请重新输入");
						getSettingPassword(dialog,password,withdrawsls,fundsPasswordERR,fundsPasswordEdit);
					}else{
						PromptManager.showToast(context, "网络状态差，请重试！");
					}
				}
	
				super.onPostExecute(result);
			}
		}.executeProxy(password);
	}
	
	/**
	 * 初使化信息
	 */
	private void getInitDataRequest() {
		WithdrawalsInfo withdrawsls=null;
		new MyHttpTask<WithdrawalsInfo>() {

			@Override
			protected Message doInBackground(WithdrawalsInfo... params) {
				// 获取数据——业务的调用
				UserEngine engine = BeanFactory.getImpl(UserEngine.class);
				return engine.getWithdrawals(null);
			}

			@Override
			protected void onPostExecute(Message result) {
		
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						MyCardInfoElements userInfoEle=(MyCardInfoElements)result.getBody().getElements().get(0);
						if(userInfoEle!=null){
							InfoList.getInstance().setMycardInfoList(userInfoEle.getMycardList());
							InfoList.getInstance().setCardLimitMap(userInfoEle.getCardLimitMap());
							InfoList.getInstance().setUserCashDesk(userInfoEle.getUserInfoWithdrawals());
							InitTip();
						}
					} else {
						PromptManager.showToast(context, oelement.getErrormsg());
					}
				}else{
					PromptManager.showToast(context, "服务器忙，请稍后重试……");
				}
				super.onPostExecute(result);
			}
		}.executeProxy(withdrawsls);
	}

}
