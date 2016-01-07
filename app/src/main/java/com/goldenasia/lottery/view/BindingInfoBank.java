package com.goldenasia.lottery.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.AnalysisBank;
import com.goldenasia.lottery.bean.BankInfo;
import com.goldenasia.lottery.bean.BankSupportInfo;
import com.goldenasia.lottery.bean.BanksBoundCards;
import com.goldenasia.lottery.bean.BindingData;
import com.goldenasia.lottery.bean.CityInfo;
import com.goldenasia.lottery.bean.InfoList;
import com.goldenasia.lottery.bean.PassWord;
import com.goldenasia.lottery.bean.ProvinceInfo;
import com.goldenasia.lottery.engine.SettingInfoEngine;
import com.goldenasia.lottery.engine.UserEngine;
import com.goldenasia.lottery.net.SafeHttpTask;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.AnalysisBankElement;
import com.goldenasia.lottery.net.protocal.element.BankInfoElement;
import com.goldenasia.lottery.net.protocal.element.BankInfoElements;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.CheckBankNumber;
import com.goldenasia.lottery.util.CustomDialog;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.util.WheelLinkageDialogShowUtil;
import com.goldenasia.lottery.util.WheelSingleDialogShowUtil;
import com.goldenasia.lottery.view.custom.MyTextWatcher;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.HallMiddleManager;
import com.goldenasia.lottery.view.manager.TitleManager;
import com.goldenasia.lottery.view.widget.DialogLinkageView.onWheelLinkageBtnPosClick;
import com.goldenasia.lottery.view.widget.DialogSingleView.onWheelBtnPosClick;

public class BindingInfoBank extends BaseUI {

	private LinearLayout bankinfoArea;
	private LinearLayout alreadyboundArea;

	private LinearLayout mycardLinear;
	private TextView mycardtiptext;

	private EditText holdcard;
	private EditText cardnumber;
	private Button writebankcardBut;

	private EditText subbranch;
	private EditText bankname;
	private EditText bankcardtype;
	private EditText provinces;
	private EditText city;
	private EditText oldrealname;
	private EditText oldcardno;

	private Button nextVerifyBut;
	private Button confirmVerifyBut;

	public BindingInfoBank(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		showInMiddle = (RelativeLayout) View.inflate(context,
				R.layout.nb_page_binding_bank, null);

		LinearLayout mainLayout = (LinearLayout) findViewById(R.id.nb_binding_bank_view);
		mainLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				InputMethodManager in = (InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (in.isActive()) {
					in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
				}
			}
		});

		mycardLinear = (LinearLayout) findViewById(R.id.bind_mycard_linear);
		mycardtiptext = (TextView) findViewById(R.id.bind_mycard_tiptext);

		bankinfoArea = (LinearLayout) findViewById(R.id.nb_bankinfo_area);
		alreadyboundArea = (LinearLayout) findViewById(R.id.nb_alreadybound_area);

		holdcard = (EditText) findViewById(R.id.nb_holdcard_edit_text);
		cardnumber = (EditText) findViewById(R.id.nb_cardnumber_edit_text);
		writebankcardBut = (Button) findViewById(R.id.nb_write_bankcard);

		subbranch = (EditText) findViewById(R.id.nb_subbranch_edit_text);
		bankname = (EditText) findViewById(R.id.nb_bankname_edit_text);
		bankcardtype = (EditText) findViewById(R.id.nb_bankcardtype_edit_text);
		provinces = (EditText) findViewById(R.id.nb_provinces_edit_text);
		city = (EditText) findViewById(R.id.nb_city_edit_text);

		oldrealname = (EditText) findViewById(R.id.nb_oldrealname_edit_text);
		oldcardno = (EditText) findViewById(R.id.nb_oldcardno_edit_text);

		nextVerifyBut = (Button) findViewById(R.id.nb_next_verify_button);
		confirmVerifyBut = (Button) findViewById(R.id.nb_confirm_verify_button);

	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		String resplusIco = context.getResources().getString(R.string.fa_bookmark);
		writebankcardBut.setText(resplusIco);
		writebankcardBut.setTypeface(font);
		writebankcardBut.setOnClickListener(this);
		cardnumber.setInputType(InputType.TYPE_CLASS_NUMBER); // 调用数字键盘
		cardnumber.setFilters(new InputFilter[] { new InputFilter.LengthFilter(25) });
		MyTextWatcher cardnumberWatcher = new MyTextWatcher(cardnumber);
		cardnumberWatcher.setTextWatcherChanging(new MyTextWatcher.onTextWatcherChangingListener() {
			@Override
			public void onTextWatcherChanging(boolean textbool) {
				// TODO Auto-generated method stub
				if (textbool) {
					writebankcardBut.setVisibility(View.VISIBLE);
					nextVerifyBut.setEnabled(true);
					nextVerifyBut.setTextColor(context.getResources().getColor(R.color.white));
					nextVerifyBut.setBackgroundResource(R.drawable.nb_fillet_red_backdrop_button);
				}
			}
		}
		);
		cardnumber.addTextChangedListener(cardnumberWatcher);
		oldcardno.setInputType(InputType.TYPE_CLASS_NUMBER); // 调用数字键盘
		oldcardno.setFilters(new InputFilter[] { new InputFilter.LengthFilter(25) });
		
		MyTextWatcher oldcardnoWatcher = new MyTextWatcher(oldcardno);
		oldcardno.addTextChangedListener(oldcardnoWatcher);
		oldcardnoWatcher.setTextWatcherChanging(new MyTextWatcher.onTextWatcherChangingListener() {
			@Override
			public void onTextWatcherChanging(boolean textbool) {
				// TODO Auto-generated method stub
				if (textbool) {
					writebankcardBut.setVisibility(View.VISIBLE);
					nextVerifyBut.setEnabled(true);
					nextVerifyBut.setTextColor(context.getResources().getColor(R.color.white));
					nextVerifyBut.setBackgroundResource(R.drawable.nb_fillet_red_backdrop_button);
				}
			}
		}
		);
		/*
		 * cardnumber.setOnFocusChangeListener(new
		 * android.view.View.OnFocusChangeListener() {
		 * 
		 * @Override public void onFocusChange(View v, boolean hasFocus) { if
		 * (hasFocus) { // 此处为得到焦点时的处理内容 flag=false; } else { // 此处为失去焦点时的处理内容 }
		 * } });
		 */

		bankname.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				InputMethodManager in = (InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (in.isActive()) {
					in.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
				}
				if (InfoList.getInstance().getBankInfoList().size() > 0)
					btnOnclik(wheelBankUtil, bankname);
			}
		});

		provinces.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnOnclik(wheelAddressUtil, provinces);
			}
		});

		city.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnOnclik(wheelAddressUtil, city);
			}
		});

		nextVerifyBut.setOnClickListener(this);
		confirmVerifyBut.setOnClickListener(this);

	}

	@Override
	public void onResume() {
		TitleManager.getInstance().changeSettingTitle("绑定银行卡");
		analysis = false;
		holdcard.setText("");
		cardnumber.setText("");
		writebankcardBut.setText("");
		subbranch.setText("");
		bankname.setText("");
		bankcardtype.setText("");
		provinces.setText("");
		city.setText("");

		oldrealname.setText("");
		oldcardno.setText("");
		InfoList.getInstance().InitBankData();
		InfoList.getInstance().InitAddress();
		tipmycardhide("");
		hideCommonBankInfo();
		hideConfirmChangeButton();
		getDataRequest();
		super.onResume();
	}

	@SuppressLint("InflateParams")
	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.nb_write_bankcard:
			cardnumber.setText("");
			writebankcardBut.setVisibility(View.INVISIBLE);
			break;
		case R.id.nb_next_verify_button:
			InputMethodManager in = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (in.isActive()) {
				in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
			}

			final String cardNo = cardnumber.getText().toString()
					.replace(" ", "");

			if (TextUtils.isEmpty(holdcard.getText().toString())) {
				Toast.makeText(context, "持卡人姓名不能为空", Toast.LENGTH_LONG).show();
				return;
			}
			if (TextUtils.isEmpty(cardnumber.getText().toString())) {
				Toast.makeText(context, "银行卡号不能为空", Toast.LENGTH_LONG).show();
				return;
			}

			String isBankCard = CheckBankNumber.luhmCheck(cardNo);

			if (!isBankCard.equals("true")) {
				Toast.makeText(context, isBankCard, Toast.LENGTH_LONG).show();
				return;
			}

			analysisData(cardNo);

			break;
		case R.id.nb_confirm_verify_button:

			if (TextUtils.isEmpty(holdcard.getText().toString())) {
				PromptManager.showToast(context, "持卡人姓名不能为空");
				return;
			}
			if (TextUtils.isEmpty(cardnumber.getText().toString())) {
				PromptManager.showToast(context, "银行卡号不能为空");
				return;
			}
			final String cardNoVerify = cardnumber.getText().toString()
					.replace(" ", "");
			;
			String isBankCardVerify = CheckBankNumber.luhmCheck(cardNoVerify);

			if (!isBankCardVerify.equals("true")) {
				PromptManager.showToast(context, isBankCardVerify);
				return;
			}

			if (TextUtils.isEmpty(subbranch.getText().toString())) {
				PromptManager.showToast(context, "开户支行不能为空");
				return;
			}

			if (TextUtils.isEmpty(bankname.getText().toString())) {
				PromptManager.showToast(context, "银行名称不能为空");
				return;
			}

			if (TextUtils.isEmpty(provinces.getText().toString())) {
				PromptManager.showToast(context, "银行省市不能为空");
				return;
			}

			if (TextUtils.isEmpty(city.getText().toString())) {
				PromptManager.showToast(context, "银行省市不能为空");
				return;
			}
			if (oldflag) {
				if (TextUtils.isEmpty(oldrealname.getText().toString())) {
					PromptManager.showToast(context, "旧卡持卡人姓名不能为空");
					return;
				}

				final String cardNoOldVerify = oldcardno.getText().toString()
						.replace(" ", "");
				String isBankCardOldVerify = CheckBankNumber
						.luhmCheck(cardNoOldVerify);

				if (!isBankCardOldVerify.equals("true")) {
					PromptManager.showToast(context, isBankCardOldVerify);
					return;
				}

				if (TextUtils.isEmpty(oldcardno.getText().toString())) {
					PromptManager.showToast(context, "旧卡银行卡号不能为空");
					return;
				}
			}

			LayoutInflater factory = LayoutInflater.from(context);// 提示框
			final View fundsPassView = factory.inflate(
					R.layout.nb_alert_funds_password, null);// 这里必须是final的
			final TextView fundsPasswordERR = (TextView) fundsPassView
					.findViewById(R.id.nb_funds_password_label_err);// 获得输入框对象
			final EditText fundsPasswordEdit = (EditText) fundsPassView
					.findViewById(R.id.nb_funds_password_edit);// 获得输入框对象

			CustomDialog.Builder builder = new CustomDialog.Builder(context);
			builder.setContentView(fundsPassView);
			builder.setTitle("提示-请输入资金密码");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(final DialogInterface dialog,
								int which) {

							// 设置你的操作事项
							String fundspass = fundsPasswordEdit.getText()
									.toString();
							if (TextUtils.isEmpty(fundspass)) {
								fundsPasswordERR.setText("资金密码不能为空");
								return;
							}

							PassWord password = new PassWord();
							password.setCellphone("1");
							password.setType("4");
							password.setPassword(fundsPasswordEdit.getText()
									.toString());

							new MyHttpTask<PassWord>() {
								
								@Override
								protected void onPreExecute() {
									PromptManager.showProgressDialog(context,"");
									super.onPreExecute();
								}
								
								@Override
								protected Message doInBackground( PassWord... params) {
									UserEngine engine = BeanFactory.getImpl(UserEngine.class);
									Message passEngine = engine
											.fundsPassword(params[0]);

									if (passEngine != null) {
										Oelement oelement = passEngine.getBody().getOelement();
										if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
											dialog.dismiss();
											BanksBoundCards boundcards = new BanksBoundCards();
											boundcards.setCellphone("1");
											boundcards.setFlag("final");

											BankInfo bankinfo = InfoList.getInstance().isSelectBank(bankname.getText().toString());
											boundcards.setBankid(bankinfo.getBankid());
											boundcards.setBranch(subbranch.getText().toString());
											boundcards.setRealname(holdcard.getText().toString());
											boundcards.setCardno(cardNoVerify);
											ProvinceInfo provinceInfo = InfoList.getInstance().isSelectProvince(provinces.getText().toString());
											CityInfo cityInfo = InfoList.getInstance().isSelectCity(provinceInfo,city.getText().toString());
											boundcards.setProvinceid(provinceInfo.getProvinceid());
											boundcards.setCityid(cityInfo.getCityId());
											boundcards.setSecpass(fundsPasswordEdit.getText().toString());// 密码验证
											if (oldflag) {
												boundcards.setOldrealname(oldrealname.getText().toString());
												boundcards.setOldcardno(oldcardno.getText().toString().replace(" ", ""));
											} else {
												boundcards.setOldrealname("");
												boundcards.setOldcardno("");
											}

											Message addcard = engine.bankInfoAddCard(boundcards);
											return addcard;
										} else {
											return passEngine;
										}
									}
									return null;
								}

								@Override
								protected void onPostExecute(Message result) {
									PromptManager.closeProgressDialog();
									if (result != null) {
										Oelement oelement = result.getBody().getOelement();
										if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
											tipmycardhide("");
											PromptManager.showToast(context,"银行卡绑定成功");
										} else {
											if (oelement.getErrorcode().equals("113")) {
												fundsPasswordEdit.setText("");
												fundsPasswordERR.setText(oelement.getErrormsg());
												PromptManager.showToast(context,oelement.getErrormsg());
											} else if (oelement.getErrorcode().equals("108")) {
												tipmycardshow(oelement.getErrormsg());
												PromptManager.showToast(context,oelement.getErrormsg());
											} else if (oelement.getErrorcode().equals("112")) {
												hideCommonBankInfo();
												hideConfirmChangeButton();
												tipmycardshow("操作失败, 银行账号已被您在之前成功绑定");
												PromptManager.showToast(context,"操作失败, 银行账号已被您在之前成功绑定");
											} else if (oelement.getErrorcode().equals("255")) {
												PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
											} else {
												PromptManager.showToast(context,oelement.getErrormsg());
											}
										}
									} else {
										// fundsPasswordERR.setText("资金密码错误!请重新输入");
									}
									super.onPostExecute(result);
								}
							}.executeProxy(password);

						}
					});

			builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			builder.create().show();
			break;
		}

	}

	// 显示
	private void tipmycardshow(String text) {
		mycardLinear.setVisibility(View.VISIBLE);
		mycardtiptext.setText(text);
	}

	private void tipmycardhide(String text) {
		mycardLinear.setVisibility(View.GONE);
		mycardtiptext.setText(text);
	}

	private WindowManager wm = (WindowManager) context
			.getSystemService(Context.WINDOW_SERVICE);

	@SuppressWarnings("unused")
	private void btnOnclik(WheelSingleDialogShowUtil dialogShowUtilBank,
			View view) {
		dialogShowUtilBank.showWheel();
		WheelSingleDialogShowUtil currentClickWheelBank = dialogShowUtilBank;
		View currentViewBank = view;
	}

	private WheelSingleDialogShowUtil wheelBankUtil;

	private void InitWheelBank() {
		// DisplayMetrics dm = new DisplayMetrics();
		wheelBankUtil = new WheelSingleDialogShowUtil(context,
				wm.getDefaultDisplay(), InfoList.getInstance()
						.getBankInfoArray(), "银行信息");

		wheelBankUtil.dialogView.setBtnPosClick(new onWheelBtnPosClick() {
			@Override
			public void onClick(String text, int position) {
				// TODO Auto-generated method stub
				wheelBankUtil.dissmissWheel();
				wheelBankUtil.setTextToView(bankname, text);
			}
		});
	}

	@SuppressWarnings("unused")
	private void btnOnclik(WheelLinkageDialogShowUtil dialogShowUtilAddress,
			View view) {
		dialogShowUtilAddress.showWheel();
		WheelLinkageDialogShowUtil currentClickWheelAddress = dialogShowUtilAddress;
		View currentViewAddress = view;
	}

	private WheelLinkageDialogShowUtil wheelAddressUtil;

	private void InitWheelAddress() {
		// DisplayMetrics dm = new DisplayMetrics();
		wheelAddressUtil = new WheelLinkageDialogShowUtil(context,
				wm.getDefaultDisplay(), InfoList.getInstance()
						.getProvinceInfoList(), InfoList.getInstance()
						.getCityInfoMap(), "请选择银行所在地址");

		if (InfoList.getInstance().getProvinceInfoList().size() > 0) {
			// 默认显示第一条
			wheelAddressUtil.setWheelHint(3);
		}
		wheelAddressUtil.dialogView
				.setLinkageBtnPosClick(new onWheelLinkageBtnPosClick() {

					@Override
					public void onClick(String textPro, int positionPro,
							String textCity, int positionCity) {
						// TODO Auto-generated method stub
						wheelAddressUtil.dissmissWheel();
						wheelAddressUtil.setTextToView(provinces, textPro);

						wheelAddressUtil.dissmissWheel();
						wheelAddressUtil.setTextToView(city, textCity);
					}

				});
	}

	/**
	 * 转换到详细信息
	 */
	private void showCommonBankInfo() {
		if (bankinfoArea.getVisibility() == View.GONE || bankinfoArea.getVisibility() == View.INVISIBLE)
			bankinfoArea.setVisibility(View.VISIBLE);
	}
	
	private void hideCommonBankInfo() {
		if (bankinfoArea.getVisibility() == View.VISIBLE)
			bankinfoArea.setVisibility(View.VISIBLE);
	}

	/**
	 * 转换到绑
	 */
	private void hideNextChangeButton() {
		nextVerifyBut.setVisibility(View.GONE);
		confirmVerifyBut.setVisibility(View.VISIBLE);
	}

	private void hideConfirmChangeButton() {
		nextVerifyBut.setVisibility(View.VISIBLE);
		confirmVerifyBut.setVisibility(View.GONE);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstantValue.VIEW_INFO_BACK;
	}

	/**
	 * 初使化信息
	 */
	private boolean oldflag = false;

	private void getDataRequest() {

		BanksBoundCards boundcards = null;
		new MyHttpTask<BanksBoundCards>() {

			@Override
			protected Message doInBackground(BanksBoundCards... params) {
				UserEngine engine = BeanFactory.getImpl(UserEngine.class);
				return engine.bankInfoAddCard(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				// 更新界面
				List<BankInfo> bankInfoList = new ArrayList<BankInfo>();
				BindingData bindingData = null;
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						BankInfoElements bankinfoEle = (BankInfoElements) result
								.getBody().getElements().get(0);
						if (bankinfoEle != null) {

							for (int bi = 0; bi < bankinfoEle
									.getBankinfoEleList().size(); bi++) {
								BankInfoElement bankinfo = bankinfoEle
										.getBankinfoEleList().get(bi);
								bankInfoList.add(bankinfo.getBankInfo());
							}

							InfoList.getInstance()
									.setBankInfoList(bankInfoList);
							bindingData = bankinfoEle.getBindingData();

							if (bankinfoEle.getUserBankInfoList().size() > 0) {
								oldflag = true;
								BankSupportInfo bankinfo = bankinfoEle
										.getUserBankInfoList().get(0);
								oldcardno.setHint(bankinfo.getCardno());
								alreadyboundArea.setVisibility(View.VISIBLE);
							} else {
								oldflag = false;
							}
						}

					} else {
						if (oelement.getErrorcode().equals("255")) {
							PromptManager.showRelogin(context,
									oelement.getErrormsg(),
									oelement.getErrorcode());
						} else {
							PromptManager.showToast(context,
									oelement.getErrormsg());
						}
					}
				} else {
					getDataRequest();
					return;
				}

				if (bankInfoList.size() > 0) {
					InfoList.getInstance().InitBankData();
					InitWheelBank();
				} else {
					if (Integer.parseInt(bindingData.getiMyBindCount()) >= Integer
							.parseInt(bindingData.getiCardmaxbind())) {
						bankname.setEnabled(false);
						provinces.setEnabled(false);
						city.setEnabled(false);

						CustomDialog.Builder builder = new CustomDialog.Builder(
								context);
						builder.setMessage("您绑定卡已超过限额！");
						builder.setTitle("提示");
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										HallMiddleManager.getInstance()
												.goBack();
									}
								});

						builder.setNegativeButton(
								"取消",
								new android.content.DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								});

					}
				}

				InitWheelAddress();
				super.onPostExecute(result);
			}
		}.executeProxy(boundcards);
	}

	private boolean analysis = false;

	private void analysisData(final String cardNo) {

		new MyHttpTask<String>() {

			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context, "");
				super.onPreExecute();
			}

			@Override
			protected Message doInBackground(String... params) {
				SettingInfoEngine engine = BeanFactory
						.getImpl(SettingInfoEngine.class);
				return engine.analysisData(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				// 更新界面
				PromptManager.closeProgressDialog();
				analysis = true;
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						analysis = false;
						AnalysisBankElement bankinfoEle = (AnalysisBankElement) result
								.getBody().getElements().get(0);
						AnalysisBank analysisBankInfo = bankinfoEle
								.getAnalysisbank();
						provinces.setText(analysisBankInfo.getProvince());
						city.setText(analysisBankInfo.getCity());
						bankname.setText("中国" + analysisBankInfo.getBank());
						bankcardtype.setText(analysisBankInfo.getType());
						confirmVerifyBut.setEnabled(true);
						confirmVerifyBut.setTextColor(context.getResources()
								.getColor(R.color.white));
						confirmVerifyBut
								.setBackgroundResource(R.drawable.nb_fillet_red_backdrop_button);
						BindingData binding = InfoList.getInstance()
								.getBindingData();
						if (binding != null) {
							int mybindCount = Integer.parseInt(binding
									.getiMyBindCount());
							int maxbindCount = Integer.parseInt(binding
									.getiCardmaxbind());

							if (mybindCount < maxbindCount) {
								if (mybindCount > 0)
									alreadyboundArea
											.setVisibility(View.VISIBLE);
							} else if (mybindCount == 0) {
								alreadyboundArea.setVisibility(View.GONE);
							}
						}
						showCommonBankInfo();
						hideNextChangeButton();
					} else {
						if (oelement.getErrorcode().equals("255")) {
							PromptManager.showRelogin(context,
									oelement.getErrormsg(),
									oelement.getErrorcode());
						} else {
							PromptManager.showToast(context,
									oelement.getErrormsg());
						}
					}
				} else {
					if (analysis) {
						analysisData(cardNo);
					} else {
						provinces.setText("");
						city.setText("");
						bankname.setText("");
						bankcardtype.setText("");
						confirmVerifyBut.setEnabled(true);
						confirmVerifyBut.setTextColor(context.getResources()
								.getColor(R.color.white));
						confirmVerifyBut
								.setBackgroundResource(R.drawable.nb_fillet_red_backdrop_button);
						BindingData binding = InfoList.getInstance()
								.getBindingData();
						if (binding != null) {
							int mybindCount = Integer.parseInt(binding
									.getiMyBindCount());
							int maxbindCount = Integer.parseInt(binding
									.getiCardmaxbind());

							if (mybindCount < maxbindCount) {
								if (mybindCount > 0)
									alreadyboundArea
											.setVisibility(View.VISIBLE);
							} else if (mybindCount == 0) {
								alreadyboundArea.setVisibility(View.GONE);
							}
						}
						showCommonBankInfo();
						hideNextChangeButton();
					}
				}

				super.onPostExecute(result);
			}
		}.executeProxy(cardNo);
	}
	/*
	 * private class CardTextWatcher implements TextWatcher{
	 * 
	 * int beforeTextLength = 0; int onTextLength = 0; boolean isChanged =
	 * false;
	 * 
	 * int location = 0;// 记录光标的位置 private char[] tempChar; private StringBuffer
	 * buffer = new StringBuffer(); int konggeNumberB = 0;
	 * 
	 * @Override public void onTextChanged(CharSequence s, int start, int
	 * before, int count) { // TODO Auto-generated method stub onTextLength =
	 * s.length(); buffer.append(s.toString()); if (onTextLength ==
	 * beforeTextLength || onTextLength <= 3 || isChanged) { isChanged = false;
	 * return; } isChanged = true; }
	 * 
	 * @Override public void beforeTextChanged(CharSequence s, int start, int
	 * count, int after) { // TODO Auto-generated method stub beforeTextLength =
	 * s.length(); if (buffer.length() > 0) { buffer.delete(0, buffer.length());
	 * } konggeNumberB = 0; for (int i = 0; i < s.length(); i++) { if
	 * (s.charAt(i) == ' ') { konggeNumberB++; } } }
	 * 
	 * @Override public void afterTextChanged(Editable s) { // TODO
	 * Auto-generated method stub if (isChanged) { location =
	 * cardnumber.getSelectionEnd(); int index = 0; while (index <
	 * buffer.length()) { if (buffer.charAt(index) == ' ') {
	 * buffer.deleteCharAt(index); } else { index++; } }
	 * 
	 * index = 0; int konggeNumberC = 0; while (index < buffer.length()) { if
	 * ((index == 4 || index == 9 || index == 14 || index == 19)) {
	 * buffer.insert(index, ' '); konggeNumberC++; } index++; }
	 * 
	 * if (konggeNumberC > konggeNumberB) { location += (konggeNumberC -
	 * konggeNumberB); }
	 * 
	 * tempChar = new char[buffer.length()]; buffer.getChars(0, buffer.length(),
	 * tempChar, 0); String str = buffer.toString(); if (location >
	 * str.length()) { location = str.length(); } else if (location < 0) {
	 * location = 0; }
	 * 
	 * cardnumber.setText(str); Editable etable = cardnumber.getText();
	 * Selection.setSelection(etable, location); isChanged = false; }
	 * 
	 * } }
	 * 
	 * private class OldCardTextWatcher implements TextWatcher{
	 * 
	 * int beforeTextLength = 0; int onTextLength = 0; boolean isChanged =
	 * false;
	 * 
	 * int location = 0;// 记录光标的位置 private char[] tempChar; private StringBuffer
	 * buffer = new StringBuffer(); int konggeNumberB = 0;
	 * 
	 * @Override public void onTextChanged(CharSequence s, int start, int
	 * before, int count) { // TODO Auto-generated method stub onTextLength =
	 * s.length(); buffer.append(s.toString()); if (onTextLength ==
	 * beforeTextLength || onTextLength <= 3 || isChanged) { isChanged = false;
	 * return; } isChanged = true;
	 * 
	 * }
	 * 
	 * @Override public void beforeTextChanged(CharSequence s, int start, int
	 * count, int after) { // TODO Auto-generated method stub beforeTextLength =
	 * s.length(); if (buffer.length() > 0) { buffer.delete(0, buffer.length());
	 * } konggeNumberB = 0; for (int i = 0; i < s.length(); i++) { if
	 * (s.charAt(i) == ' ') { konggeNumberB++; } } }
	 * 
	 * @Override public void afterTextChanged(Editable s) { // TODO
	 * Auto-generated method stub if (isChanged) { location =
	 * oldcardno.getSelectionEnd(); int index = 0; while (index <
	 * buffer.length()) { if (buffer.charAt(index) == ' ') {
	 * buffer.deleteCharAt(index); } else { index++; } }
	 * 
	 * index = 0; int konggeNumberC = 0; while (index < buffer.length()) { if
	 * ((index == 4 || index == 9 || index == 14 || index == 19)) {
	 * buffer.insert(index, ' '); konggeNumberC++; } index++; }
	 * 
	 * if (konggeNumberC > konggeNumberB) { location += (konggeNumberC -
	 * konggeNumberB); }
	 * 
	 * tempChar = new char[buffer.length()]; buffer.getChars(0, buffer.length(),
	 * tempChar, 0); String str = buffer.toString(); if (location >
	 * str.length()) { location = str.length(); } else if (location < 0) {
	 * location = 0; }
	 * 
	 * oldcardno.setText(str); Editable etable = oldcardno.getText();
	 * Selection.setSelection(etable, location); isChanged = false; } }
	 * 
	 * }
	 */

	/*
	 * try { HttpClient httpclient = new DefaultHttpClient(); String strResult =
	 * "doPostError"; String url =
	 * context.getResources().getString(R.string.is_binding_info_bank_url);
	 * HttpPost httppost = new HttpPost(url); List<NameValuePair> nameValuePairs
	 * = new ArrayList<NameValuePair>(2); nameValuePairs.add(new
	 * BasicNameValuePair("key", ConstantValue.URL_KEY)); nameValuePairs.add(new
	 * BasicNameValuePair("card", cardNo));
	 * 
	 * httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	 * HttpResponse response = httpclient.execute(httppost); if
	 * (response.getStatusLine().getStatusCode() == 200) { strResult =
	 * EntityUtils.toString(response.getEntity());
	 * 
	 * JSONTokener jsonParser = new JSONTokener(strResult); JSONObject
	 * jsonObject = (JSONObject) jsonParser.nextValue(); int errorCode =
	 * jsonObject.getInt("error_code"); if (errorCode == 0) { JSONObject
	 * jsoninfo = jsonObject.getJSONObject("result");
	 * provinces.setText(jsoninfo.getString("province"));
	 * city.setText(jsoninfo.getString("city"));
	 * bankname.setText("中国"+jsoninfo.getString("bank"));
	 * bankcardtype.setText(jsoninfo.getString("cardname")); } }
	 * httppost.abort(); } catch (ClientProtocolException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (JSONException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 */
}
