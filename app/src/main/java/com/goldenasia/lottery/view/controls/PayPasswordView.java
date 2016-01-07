package com.goldenasia.lottery.view.controls;

import java.util.ArrayList;

import android.R.bool;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.ShoppingCart;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.controls.PayPasswordView.KeyboardEnum.ActionEnum;

/**
 * Dialog 修改倍数键盘
 * 
 * @author LanYan
 *
 */
@SuppressLint("InflateParams")
public class PayPasswordView implements OnClickListener {

	private ImageButton delBut;
	private Button sureBut;
	private Button tab;
	private Button keyboardBut;
	private Typeface font;
	private LinearLayout zero;
	private LinearLayout one;
	private LinearLayout two;
	private LinearLayout three;
	private LinearLayout four;
	private LinearLayout five;
	private LinearLayout sex;
	private LinearLayout seven;
	private LinearLayout eight;
	private LinearLayout nine;
	
	private EditText box1;
	private EditText box2;
	private Button doubleMinusBut; // 倍数减
	private Button doublePlusBut; // 倍数加
	private Button appendMinusBut; // 追号期数 减
	private Button appendPlusBut; // 追号期数 加
	
	private ArrayList<String> mList = new ArrayList<String>();
	private View mView;
	private OnPayListener listener;
	@SuppressWarnings("unused")
	private Context mContext;

	public PayPasswordView(Context mContext, OnPayListener listener) {
		getDecorView(mContext, listener);
	}

	public static PayPasswordView getInstance(Context mContext, OnPayListener listener) {
		return new PayPasswordView(mContext, listener);
	}

	public void getDecorView(Context mContext, OnPayListener listener) {
		this.listener = listener;
		this.mContext = mContext;
		this.font = Typeface.createFromAsset(mContext.getAssets(),"fontawesome-webfont.ttf");
		mView = LayoutInflater.from(mContext).inflate(R.layout.item_paypassword, null);
		findViewByid();
		setLintenter();
	}

	private void findViewByid() {

		delBut = (ImageButton) mView.findViewById(R.id.pay_keyboard_del);// 删除键
		sureBut = (Button) mView.findViewById(R.id.pay_keyboard_sure);// 确认键
		
		String keyboardIco = mContext.getResources().getString(R.string.fa_keyboard_o);
		keyboardBut = (Button) mView.findViewById(R.id.pay_keyboard_ico);
		keyboardBut.setText(keyboardIco);
		keyboardBut.setTypeface(font);
		
		tab = (Button) mView.findViewById(R.id.pay_keyboard_tab);

		// 键盘1-9
		zero = (LinearLayout) mView.findViewById(R.id.pay_keyboard_zero);
		one = (LinearLayout) mView.findViewById(R.id.pay_keyboard_one);
		two = (LinearLayout) mView.findViewById(R.id.pay_keyboard_two);
		three = (LinearLayout) mView.findViewById(R.id.pay_keyboard_three);
		four = (LinearLayout) mView.findViewById(R.id.pay_keyboard_four);
		five = (LinearLayout) mView.findViewById(R.id.pay_keyboard_five);
		sex = (LinearLayout) mView.findViewById(R.id.pay_keyboard_sex);
		seven = (LinearLayout) mView.findViewById(R.id.pay_keyboard_seven);
		eight = (LinearLayout) mView.findViewById(R.id.pay_keyboard_eight);
		nine = (LinearLayout) mView.findViewById(R.id.pay_keyboard_nine);

		// 输入框 TextView
		box1 = (EditText) mView.findViewById(R.id.pay_box1);
		box1.setInputType(InputType.TYPE_NULL);
		box2 = (EditText) mView.findViewById(R.id.pay_box2);
		box2.setInputType(InputType.TYPE_NULL);
		
		appendMinusBut = (Button) mView.findViewById(R.id.nb_shopping_drift_append_minus);
		appendPlusBut = (Button) mView.findViewById(R.id.nb_shopping_drift_append_plus);
		doubleMinusBut = (Button) mView.findViewById(R.id.nb_shopping_drift_double_minus);
		doublePlusBut = (Button) mView.findViewById(R.id.nb_shopping_drift_double_plus);
	}

	private boolean flagAppendEdit = false, flagMultipleEdit = false;
	private void setLintenter() {
		box1.setText(ShoppingCart.getInstance().getIssuesnumbers().toString());
		box1.setSelection(box1.getText().length());
		box1.setSelectAllOnFocus(true);
		box1.setCursorVisible(true);
		box1.clearFocus();
		box1.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					// 此处为得到焦点时的处理内容
					flagAppendEdit = true;
					box1.setSelection(box1.getText().length());
					ShoppingCart.getInstance().setFlagfocus(true);
					
				} else {
					// 此处为失去焦点时的处理内容
					flagAppendEdit = false;
				}
			}
		});
		box2.setText(ShoppingCart.getInstance().getAppnumbers().toString());
		box2.setSelection(box2.getText().length());
		box2.setSelectAllOnFocus(true);
		box2.setCursorVisible(true);
		box2.clearFocus();
		box2.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					// 此处为得到焦点时的处理内容
					flagMultipleEdit = true;
					box2.setSelection(box2.getText().length());
					ShoppingCart.getInstance().setFlagfocus(false);
				} else {
					// 此处为失去焦点时的处理内容
					flagMultipleEdit = false;
				}
			}
		});
		
		if(ShoppingCart.getInstance().isFlagfocus()){
			box1.setFocusable(true);
		}else{
			box2.setFocusable(true);
		}
		
		sureBut.setOnClickListener(this);
		delBut.setOnClickListener(this);
		zero.setOnClickListener(this);
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		five.setOnClickListener(this);
		sex.setOnClickListener(this);
		seven.setOnClickListener(this);
		eight.setOnClickListener(this);
		nine.setOnClickListener(this);
		keyboardBut.setOnClickListener(this);
		tab.setOnClickListener(this);
		
		String resplusIco = mContext.getResources().getString(R.string.fa_plus);
		String resminusIco = mContext.getResources().getString(R.string.fa_minus);
		doubleMinusBut.setText(resminusIco);
		doubleMinusBut.setTypeface(font);
		doubleMinusBut.setOnClickListener(this);

		doublePlusBut.setText(resplusIco);
		doublePlusBut.setTypeface(font);
		doublePlusBut.setOnClickListener(this);

		appendMinusBut.setText(resminusIco);
		appendMinusBut.setTypeface(font);
		appendMinusBut.setOnClickListener(this);

		appendPlusBut.setText(resplusIco);
		appendPlusBut.setTypeface(font);
		appendPlusBut.setOnClickListener(this);
		delBut.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				parseActionType(KeyboardEnum.longdel);
				return false;
			}
		});
	}

	private void parseActionType(KeyboardEnum type) {
		// TODO Auto-generated method stub
		if (type.getType() == ActionEnum.add) {
			if (flagAppendEdit) {
				updateTextWatcher(box1, type.getValue());
				updateUi();
			}
			if (flagMultipleEdit) {
				updateTextWatcher(box2, type.getValue());
				updateUi();
				// updateTextWatcher(box1);
			}

		} else if (type.getType() == ActionEnum.delete) {
			if (flagAppendEdit) {
				updateUiEditableDelete(box1);
			}
			if (flagMultipleEdit) {
				updateUiEditableDelete(box2);
			}
		}else if(type.getType() == ActionEnum.sure){
			String appendStr=box1.getText().toString();
			String append="",multiple="";
			if(appendStr.length()>0){
				Integer appendInt=Integer.parseInt(appendStr);
				if(appendInt>0){
					append=appendStr;
				}
			}else{
				append="1";
				box1.setText(multiple);
			}
			
			String multipleStr=box2.getText().toString();
			if(multipleStr.length()>0){
				Integer multipleInt=Integer.parseInt(multipleStr);
				if(multipleInt>0){
					multiple=multipleStr;
				}
			}else{
				multiple="1";
				box2.setText(multiple);
			}
				
			listener.onSurePay(append,multiple);
		}else if (type.getType() == ActionEnum.longClick) {
			mList.clear();
			updateUi();
		}else if(type.getType() == ActionEnum.tab) {
			//两种 第一个输入框 跳转第二输入框
			//第二输焦点 
			if(ShoppingCart.getInstance().isFlagfocus()){
				ShoppingCart.getInstance().setFlagfocus(false);
				box2.setFocusable(true);
				box2.requestFocus();
			}else{
				if(ShoppingCart.getInstance().isFlagfocus()==false){
					ShoppingCart.getInstance().setFlagfocus(true);
					listener.onCancelPay();
				}
			} 
			
		}else if(type.getType() == ActionEnum.cancel) {
			listener.onCancelPay();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == zero) {
			parseActionType(KeyboardEnum.zero);
		} else if (v == one) {
			parseActionType(KeyboardEnum.one);
		} else if (v == two) {
			parseActionType(KeyboardEnum.two);
		} else if (v == three) {
			parseActionType(KeyboardEnum.three);
		} else if (v == four) {
			parseActionType(KeyboardEnum.four);
		} else if (v == five) {
			parseActionType(KeyboardEnum.five);
		} else if (v == sex) {
			parseActionType(KeyboardEnum.sex);
		} else if (v == seven) {
			parseActionType(KeyboardEnum.seven);
		} else if (v == eight) {
			parseActionType(KeyboardEnum.eight);
		} else if (v == nine) {
			parseActionType(KeyboardEnum.nine);
		} else if(v == sureBut){
			parseActionType(KeyboardEnum.sure);
		} else if (v == delBut) {
			parseActionType(KeyboardEnum.del);
		} else if (v == keyboardBut){
			parseActionType(KeyboardEnum.cancel);
		} else if(v == tab){
			parseActionType(KeyboardEnum.tab);
		} else if(v == appendMinusBut){
			int appendminusNo = ShoppingCart.getInstance().getIssuesnumbers();
			if (appendminusNo <= 1) {
				PromptManager.showToast(mContext, "追号期数不能小于1");
			}
			ShoppingCart.getInstance().addIssuesnumbers(false);
			box1.setKeyListener(null);
			box1.setSelection(box1.getText().length());
			box1.setText(ShoppingCart.getInstance().getIssuesnumbers().toString());
		} else if(v == appendPlusBut){
			ShoppingCart.getInstance().getIssuesnumbers();
			ShoppingCart.getInstance().addIssuesnumbers(true);
			box1.setKeyListener(null);
			box1.setSelection(box1.getText().length());
			box1.setText(ShoppingCart.getInstance().getIssuesnumbers().toString());
		} else if(v == doublePlusBut){
			ShoppingCart.getInstance().getAppnumbers();
			ShoppingCart.getInstance().addAppnumbers(true);
			box2.setKeyListener(null);
			box2.setSelection(box2.getText().length());
			box2.setText(ShoppingCart.getInstance().getAppnumbers().toString());
		} else if(v == doubleMinusBut){
			int doubleMinusNo = ShoppingCart.getInstance().getAppnumbers();
			if (doubleMinusNo <= 1) {
				PromptManager.showToast(mContext, "投注倍数不能小于1");
			}
			ShoppingCart.getInstance().addAppnumbers(false);
			box2.setKeyListener(null);
			box2.setSelection(box2.getText().length());
			box2.setText(ShoppingCart.getInstance().getAppnumbers().toString());
		}
		
	}

	/**
	 * 更新UI 在光标处插入字符
	 * 
	 * @param editText
	 */
	private void updateUiEditableInsert(EditText editText, String val) {
		int index = editText.getSelectionStart();
		Editable editable = editText.getText();
		editable.insert(index, val);
	}

	/**
	 * 更新UI 在光标处插入字符
	 * 
	 * @param editText
	 */
	private void updateUiEditableDelete(EditText editText) {
		int index = editText.getSelectionStart();
		if (index > 0) {
			Editable editable = editText.getText();
			editable.delete(index - 1, index);
		}
	}

	/**
	 * 刷新UI 光标位置尾部
	 */
	private void updateUi() {

		if (flagAppendEdit) {
			Editable etext = box1.getText();
			Selection.setSelection(etext, etext.length());
			
		}
		if (flagMultipleEdit) {
			Editable etext = box2.getText();
			Selection.setSelection(etext, etext.length());
			
		}
	}

	public interface OnPayListener {
		void onCancelPay();
		void onSurePay(String append, String multiple); //倍数与追号期数
	}

	public View getView() {
		return mView;
	}

	private void updateTextWatcher(final EditText textView, final String val) {
		/**
		 * TextWatcher：接口，继承它要实现其三个方法，分别为Text改变之前、改变的过程中、改变之后各自发生的动作
		 */
		int index = textView.getSelectionStart();
		Editable editable = textView.getText();
		editable.insert(index, val);
		textView.addTextChangedListener(new TextWatcher() {

			private boolean isChanged = false;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (isChanged) {// ----->如果字符未改变则返回
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
				int zeroIndex = -1;
				boolean flag = false;
				if (cuttedStr.length() > 0) {
					char one = str.charAt(0);
					if (one == '0') {
						if (cuttedStr.length() <= 2) {
							int n = 0;
							while (n < 2) {
								if (flag)
									break;
								if (cuttedStr.length() != 0
										&& cuttedStr.length() <= 2) {
									if (n < cuttedStr.length()) {
										char c = cuttedStr.charAt(n);
										if (c != '0') {
											zeroIndex = n;
											flag = true;
											break;
										}
									}
									n++;
								} else {
									flag = true;
									n++;
								}
							}

							if (zeroIndex != -1) {
								cuttedStr = cuttedStr.substring(zeroIndex,
										cuttedStr.length());
								textView.setText(cuttedStr);
							} else if (!flag) {
								int index = textView.getSelectionStart();
								if (index >= 2) {
									Editable editable = textView.getText();
									editable.delete(index - 1, index);
								}
							}
							/* 不足1位补0 */
							if (cuttedStr.length() < 1) {
								cuttedStr = "0";
							}
							/* 加上dot，以显示小数点后两位 */
						}
					}
				}
				isChanged = false;
			}
		});
		if (flagAppendEdit) {
			ShoppingCart.getInstance().setIssuesnumbers(Integer.parseInt(box1.getText().toString()));
		}
		if (flagMultipleEdit) {
			ShoppingCart.getInstance().setAppnumbers(Integer.parseInt(box2.getText().toString()));
		}
		
	}

	public enum KeyboardEnum {
		one(ActionEnum.add, "1"), two(ActionEnum.add, "2"), three(
				ActionEnum.add, "3"), four(ActionEnum.add, "4"), five(
				ActionEnum.add, "5"), sex(ActionEnum.add, "6"), seven(
				ActionEnum.add, "7"), eight(ActionEnum.add, "8"), nine(
				ActionEnum.add, "9"), zero(ActionEnum.add, "0"), del(
				ActionEnum.delete, "del"), longdel(ActionEnum.longClick,
				"longclick"), cancel(ActionEnum.cancel, "cancel"),tab(ActionEnum.tab, "del"),
				sure(ActionEnum.sure, "sure");// , point(ActionEnum.add, ".")
		public enum ActionEnum {
			add, delete, longClick, cancel, sure,tab
		}

		private ActionEnum type;
		private String value;

		private KeyboardEnum(ActionEnum type, String value) {
			this.type = type;
			this.value = value;
		}

		public ActionEnum getType() {
			return type;
		}

		public void setType(ActionEnum type) {
			this.type = type;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

}
