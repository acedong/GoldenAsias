package com.goldenasia.lottery.view.adapter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.AppendInfo;
import com.goldenasia.lottery.bean.ShoppingCart;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.controls.HVListView;

public class AppendBettingAdapter extends BaseAdapter {
//	private HVListView appendHallListView;
	private LayoutInflater mInflater;
	private Context mContext;
	private Typeface fontIco = null;
	private Map<String, String> editorValue = new HashMap<String, String>();

	public AppendBettingAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		this.fontIco = Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
	}

	public void setData(List<Map<String, AppendInfo>> data) {
		ShoppingCart.getInstance().setAppendList(data);
		init();
	}

	private void init() {
		editorValue.clear();
		HashMap<String, Boolean> isSelected = new HashMap<String, Boolean>();
		if (ShoppingCart.getInstance().getAppendList() != null) {
			for (int i = 0; i < ShoppingCart.getInstance().getAppendList().size(); i++) {
				AppendInfo info = ShoppingCart.getInstance().getAppendList().get(i).get("list_item_inputvalue");
				isSelected.put(info.getIssue(), true);
			}
		}
		ShoppingCart.getInstance().setIsSelected(isSelected);
		onRefreshData.refreshData();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (ShoppingCart.getInstance().getAppendList() != null) {
			return ShoppingCart.getInstance().getAppendList().size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	private Integer index = -1;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		// private AppendInfo appinfo=new AppendInfo();

		final int serial = position + 1;

		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.nb_appendbetting_hall_item, null);

			holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
			holder.appendSerial = (TextView) convertView.findViewById(R.id.nb_append_serial_number);
			holder.appendIssue = (TextView) convertView.findViewById(R.id.nb_append_issue_number);
			holder.appendMultipleMinus = (Button) convertView.findViewById(R.id.nb_append_multiple_minus);
			holder.appendMultiplePlus = (Button) convertView.findViewById(R.id.nb_append_multiple_plus);

			holder.appendGrandtotalMoney = (TextView) convertView.findViewById(R.id.nb_append_grandtotal_money);
			holder.appendGrandtotalMoney.setTag(position);

			holder.numEdit = (EditText) convertView.findViewById(R.id.num_edit);
			holder.numEdit.setTag(position);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			holder.numEdit.setTag(position);
			holder.appendGrandtotalMoney.setTag(position);
		}

		// String taskissue=tasklist.get(position);
		final AppendInfo info = ShoppingCart.getInstance().getAppendList().get(position).get("list_item_inputvalue");
		holder.appendSerial.setText(serial + "");
		holder.appendIssue.setText(info.getIssue());
		String resplusIco = mContext.getResources().getString(R.string.fa_plus);
		String resminusIco = mContext.getResources().getString(R.string.fa_minus);

		holder.appendMultipleMinus.setFocusable(false);
		holder.appendMultipleMinus.setFocusableInTouchMode(false);
		holder.appendMultipleMinus.setText(resminusIco);
		holder.appendMultipleMinus.setTypeface(fontIco);

		holder.appendMultiplePlus.setFocusable(false);
		holder.appendMultiplePlus.setFocusableInTouchMode(false);
		holder.appendMultiplePlus.setText(resplusIco);
		holder.appendMultiplePlus.setTypeface(fontIco);

		holder.numEdit.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_UP) {
					index = (Integer) v.getTag();
				}
				return false;
			}
		});

		holder.numEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
		holder.numEdit.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
		holder.numEdit.setSelection(holder.numEdit.length());

		class MyTextWatcher implements TextWatcher {
			public MyTextWatcher(ViewHolder holder) {
				mHolder = holder;
			}

			private ViewHolder mHolder;

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			private int MIN_MARK = 0;
			private int MAX_MARK = 99;

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				int positionEdit = (Integer) mHolder.numEdit.getTag();
				AppendInfo infoEdit = ShoppingCart.getInstance().getAppendList().get(positionEdit).get("list_item_inputvalue");
				if (start > 1) {
					if (MIN_MARK != -1 && MAX_MARK != -1) {
						int num = Integer.parseInt(s.toString());
						if (num > MAX_MARK) {
							Toast.makeText(mContext, "倍数不能超过99", Toast.LENGTH_SHORT).show();
							s = String.valueOf(MAX_MARK);
							holder.numEdit.setText(s);
						} else if (num < MIN_MARK){
							s = String.valueOf(MIN_MARK);
							ShoppingCart.getInstance().getIsSelected().put(infoEdit.getIssue(), false);
							holder.checkBox.setChecked(ShoppingCart.getInstance().getIsSelected().get(infoEdit.getIssue()));
							Toast.makeText(mContext, "最小倍数为1，0则此注不追号", Toast.LENGTH_SHORT).show();
							holder.numEdit.setText(s);
						}
						return;
					} 
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				AppendInfo appinfo = new AppendInfo();
				int positionEdit = (Integer) mHolder.numEdit.getTag();
				AppendInfo infoEdit = ShoppingCart.getInstance().getAppendList().get(positionEdit).get("list_item_inputvalue");
				if (s != null && !"".equals(s.toString())) {
					
					String str = s.toString();
					int NUM = str.length();   
	                int zeroIndex = -1;  
	                if(NUM >1){
		                for (int i = 0; i < NUM; i++) {    
		                    char c = str.charAt(i);    
		                    if (c== '0'&&i==0) {    
		                        zeroIndex = 1;  
		                        break;  
		                    }
		                }
					}
	                
	                if(zeroIndex != -1){  
	                	str = str.substring(zeroIndex); 
	                	mHolder.numEdit.setText(str);
	                }
	                
	                /* 不足1位补0 */    
	                if (str.length() < 1) {    
	                	str = 0 + str;    
	                } 
					// 当EditText数据发生改变的时候存到data变量中
					appinfo.setMultiple(Integer.valueOf(str));
					appinfo.setIssue(info.getIssue());
					appinfo.setPutin(info.getPutin());
					ShoppingCart.getInstance().getAppendList().get(positionEdit).put("list_item_inputvalue", appinfo);
				} else {
					ShoppingCart.getInstance().getIsSelected().put(infoEdit.getIssue(), false);
					mHolder.checkBox.setChecked(ShoppingCart.getInstance().getIsSelected().get(infoEdit.getIssue()));
					mHolder.numEdit.setText(0 + "");
					Toast.makeText(mContext, "最小倍数为1，0则此注不追号",Toast.LENGTH_SHORT).show();
				}
				mHolder.numEdit.setSelection(mHolder.numEdit.length());
			}
		}
		holder.numEdit.addTextChangedListener(new MyTextWatcher(holder));

		holder.appendMultipleMinus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int positionMinus = (Integer) holder.numEdit.getTag();
				String edittextStr = holder.numEdit.getText().toString();
				int num = Integer.parseInt(edittextStr);
				num--;
				if(num >= 0){
					
					if (num == 0) {
						num=0;
						for(int i=0;i<ShoppingCart.getInstance().getAppendList().size();i++){
							AppendInfo infoUpdata=ShoppingCart.getInstance().getAppendList().get(i).get("list_item_inputvalue");
							double putin=Double.parseDouble(infoUpdata.getPutin());
							if(positionMinus<i){
								double totaldelivery=putin-(ShoppingCart.getInstance().getLotteryvalue()*1);
								infoUpdata.setPutin(totaldelivery+"");
								ShoppingCart.getInstance().getAppendList().get(i).put("list_item_inputvalue", infoUpdata);
							}
						}
						
						AppendInfo infoMinus = ShoppingCart.getInstance().getAppendList().get(positionMinus).get("list_item_inputvalue");
						ShoppingCart.getInstance().getIsSelected().put(infoMinus.getIssue(), false);
						
						holder.checkBox.setChecked(ShoppingCart.getInstance().getIsSelected().get(infoMinus.getIssue()));
						PromptManager.showToast(mContext, "最小倍数为1，0则此注不追号");
					} else if(num>0){
						for(int i=0;i<ShoppingCart.getInstance().getAppendList().size();i++){
							AppendInfo infoUpdata=ShoppingCart.getInstance().getAppendList().get(i).get("list_item_inputvalue");
							double putin=Double.parseDouble(infoUpdata.getPutin());
							if(positionMinus<i){
								double totaldelivery=putin-(ShoppingCart.getInstance().getLotteryvalue()*(num-1));
								infoUpdata.setPutin(totaldelivery+"");
								ShoppingCart.getInstance().getAppendList().get(i).put("list_item_inputvalue", infoUpdata);
							}
						}
					}
					notifyDataSetChanged();
					
					AppendInfo infoMinus=ShoppingCart.getInstance().getAppendList().get(positionMinus).get("list_item_inputvalue");
					double putinMinus=Double.parseDouble(infoMinus.getPutin());
					double totaldeliveryUpdataMinus=0.00;
					if(num!=0)
						totaldeliveryUpdataMinus=putinMinus-ShoppingCart.getInstance().getLotteryvalue()*1;
					
					BigDecimal b=new BigDecimal(totaldeliveryUpdataMinus);  
					double   totaldeliveryUpdata=b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
					
					infoMinus.setMultiple(num);
					infoMinus.setPutin(totaldeliveryUpdata+"");
					holder.numEdit.setText(num + "");
					holder.appendGrandtotalMoney.setText(totaldeliveryUpdata+"");
					
					ShoppingCart.getInstance().getAppendList().get(positionMinus).put("list_item_inputvalue", infoMinus);
					onRefreshData.refreshData();
				}
			}
		});

		holder.appendMultiplePlus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				int positionPlus = (Integer) holder.numEdit.getTag();
				String edittextStr = holder.numEdit.getText().toString();
				int num = Integer.parseInt(edittextStr);
				num++;
				if(num <= 99){
					if (num > 99) {
						PromptManager.showToast(mContext, "最大倍数为99");
					} else {
						
						for(int i=0;i<ShoppingCart.getInstance().getAppendList().size();i++){
							AppendInfo infoUpdata=ShoppingCart.getInstance().getAppendList().get(i).get("list_item_inputvalue");
							if(positionPlus<i){
								double putin=Double.parseDouble(infoUpdata.getPutin());
								double totaldelivery=putin+(ShoppingCart.getInstance().getLotteryvalue()*1);
								infoUpdata.setPutin(totaldelivery+"");
								ShoppingCart.getInstance().getAppendList().get(i).put("list_item_inputvalue", infoUpdata);
							}
						}
					}
					holder.numEdit.setText(num + "");
					notifyDataSetChanged();
				
					AppendInfo infoPlus=ShoppingCart.getInstance().getAppendList().get(positionPlus).get("list_item_inputvalue");
					
					double putinPlus=Double.parseDouble(infoPlus.getPutin());
					double totaldeliveryPlus=0.00;
					int pos=positionPlus+1;
					if(positionPlus!=0&&num<=1){
						totaldeliveryPlus=putinPlus+(ShoppingCart.getInstance().getLotteryvalue()*pos*1);
					}else
						totaldeliveryPlus=putinPlus+(ShoppingCart.getInstance().getLotteryvalue()*1);
					
					BigDecimal b=new BigDecimal(totaldeliveryPlus);  
					double   totaldeliveryUpdata=b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
					
					holder.appendGrandtotalMoney.setText(totaldeliveryUpdata+"");
					info.setMultiple(num);
					info.setPutin(totaldeliveryUpdata+"");
					ShoppingCart.getInstance().getAppendList().get(positionPlus).put("list_item_inputvalue", info);
					
					onRefreshData.refreshData();
				}
			}
		});

		holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				int positionCheck = (Integer) holder.numEdit.getTag();
				AppendInfo infoCheck = ShoppingCart.getInstance().getAppendList().get(positionCheck).get("list_item_inputvalue");
				ShoppingCart.getInstance().getIsSelected().put(infoCheck.getIssue(), isChecked);
			}
		});

		holder.checkBox.setChecked(ShoppingCart.getInstance().getIsSelected().get(info.getIssue()));
		BigDecimal bPutin=new BigDecimal(info.getPutin());  
		double   totaldelivery=bPutin.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		holder.appendGrandtotalMoney.setText(totaldelivery+"");

		if (info.getMultiple() != null && !"".equals(info.getMultiple())) {
			holder.numEdit.setText(info.getMultiple()+"");
		} else {
			holder.numEdit.setText("1");
		}
		holder.numEdit.clearFocus();
		if (index != -1 && index == position) {
			holder.numEdit.requestFocus();
		}

		// 校正（处理同时上下和左右滚动出现错位情况）
//		View child = ((ViewGroup) convertView).getChildAt(1);
//		int head = appendHallListView.getHeadScrollX();
//		if (child.getScrollX() != head) {
//			child.scrollTo(appendHallListView.getHeadScrollX(), 0);
//		}
		return convertView;
	}

	public HashMap<String, Boolean> getIsSelected() {
		return ShoppingCart.getInstance().getIsSelected();
	}

	public void setIsSelected(HashMap<String, Boolean> isSelected) {
		ShoppingCart.getInstance().setIsSelected(isSelected);
	}

	public class ViewHolder {
		TextView appendSerial; // 序列号
		TextView appendIssue; // 期号
		CheckBox checkBox;
		Button appendMultipleMinus;
		EditText numEdit;
		Button appendMultiplePlus;

		TextView appendGrandtotalMoney;
	}
	
	private OnRefreshDataChangeListener onRefreshData;
	
	public OnRefreshDataChangeListener getOnRefreshData() {
		return onRefreshData;
	}

	public void setOnRefreshData(OnRefreshDataChangeListener onRefreshData) {
		this.onRefreshData = onRefreshData;
	}

	public interface OnRefreshDataChangeListener{
		/**
		 * 刷新数据
		 * @param v
		 * @param position
		 */
		public void refreshData();
	}
}
