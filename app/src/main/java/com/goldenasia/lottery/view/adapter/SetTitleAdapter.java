package com.goldenasia.lottery.view.adapter;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.GlobalParams;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.PassWord;
import com.goldenasia.lottery.bean.SettingItemClump;
import com.goldenasia.lottery.bean.SettingItemEntity;
import com.goldenasia.lottery.bean.User;
import com.goldenasia.lottery.engine.UserEngine;
import com.goldenasia.lottery.net.SafeHttpTask;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.VersionElement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.CustomDialog;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.util.UpdateManager;
import com.goldenasia.lottery.view.AccountPassword;
import com.goldenasia.lottery.view.BindingInfoBank;
import com.goldenasia.lottery.view.FundsPassword;
import com.goldenasia.lottery.view.LotteryCampaignHall;
import com.goldenasia.lottery.view.MessageHall;
import com.goldenasia.lottery.view.custom.MyListView;
import com.goldenasia.lottery.view.manager.HallMiddleManager;

@SuppressLint("InflateParams")
public class SetTitleAdapter extends BaseAdapter {

    private Context mContext;
    private List<SettingItemClump> mData;
    private LayoutInflater mLayoutInflater;
    

    public SetTitleAdapter(Context pContext, List<SettingItemClump> pData) {
        this.mContext = pContext;
        this.mData = pData;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }
    

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 常见的优化ViewHolder
        final ViewHolder viewHolder;
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.nb_lottery_myself_hall_titlelist_item, null);
            
            viewHolder = new ViewHolder();
            viewHolder.titlec = (TextView) convertView.findViewById(R.id.nb_setting_list_title); 
            viewHolder.menuList = (MyListView) convertView.findViewById(R.id.nb_myself_menu_list);
            viewHolder.menuList.setTag(position);
            
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.menuList.setTag(position);
        }
        
        SettingItemClump settingItem=mData.get(position);
        
    	viewHolder.titlec.setText(settingItem.getTitleStr());
        List<SettingItemEntity> setItemList=settingItem.getItemEntity();

        CustomAdapter otherHistory=new CustomAdapter(mContext,setItemList);
        viewHolder.menuList.setAdapter(otherHistory);
        
        viewHolder.menuList.setOnItemClickListener(new OnItemClickListener(){
			 
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                // TODO Auto-generated method stub
            	int ilang=(int) viewHolder.menuList.getTag();
            	
            	switch (ilang) {
				case 0:
					switch (pos) {
						case 0:
							bindingBankCard();	//绑定银行卡
							break;
						case 1:
							accountPassword();	//帐号密码修改
							break;
						case 2:
							fundsPassword();	//修改资金密码
							break;
					}
					break;
				case 1:
					switch (pos) {
						case 0:
							oneselfNews();		//个人消息
							break;
						case 1:
							systemMessage();	//系统消息
							break;
						case 2:
							activityMessage();	//活动消息
							break;
					}
					break;
				case 2:
					switch (pos) {
						case 0:
							break;
						case 1:
							detectUpdates();	//检查更新
							break;
						case 2:
							break;
					}
					break;
				default:
					break;
				}
            }
             
        });
        
        return convertView;
    }
    
    @Override
    public int getCount() {
        if (null != mData) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != mData && position < getCount()) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    private class ViewHolder {
        TextView titlec;
        MyListView menuList;
    }
    /**
	 * 检测更新
	 */
	private void detectUpdates(){
		getDataRequest();
	}
	/**
	 * 活动消息
	 */
	private void activityMessage(){
		HallMiddleManager.getInstance().changeUI(LotteryCampaignHall.class);
	}
	/**
	 * 系统消息
	 */
	private void systemMessage(){
		Bundle bundleMessage=new Bundle();
		bundleMessage.putInt("typeinfo", 1);
		HallMiddleManager.getInstance().changeUI(MessageHall.class,bundleMessage);
	}
	/**
	 * 个人信息
	 */
	private void oneselfNews(){
		Bundle bundleMessage=new Bundle();
		bundleMessage.putInt("typeinfo", 0);
		HallMiddleManager.getInstance().changeUI(MessageHall.class,bundleMessage);
	}
	/**
	 * 修改资金密码
	 */
	private void fundsPassword(){
		HallMiddleManager.getInstance().changeUI(FundsPassword.class);
	}
	
	/**
	 * 修改帐号密码
	 */
	private void accountPassword(){
		HallMiddleManager.getInstance().changeUI(AccountPassword.class);
	}
	
	/**
	 * 绑定银行卡
	 */
	private void bindingBankCard(){
		if (GlobalParams.ISSECURITYPWD)
			HallMiddleManager.getInstance().changeUI(BindingInfoBank.class);
		else {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			final View textEntryView = inflater.inflate(R.layout.nb_alert_funds_password, null);
			final TextView secpasserrText = (TextView) textEntryView.findViewById(R.id.nb_funds_password_label_err);
			final EditText secpassInput = (EditText) textEntryView.findViewById(R.id.nb_funds_password_edit);
			secpassInput.setHint("请设置资金密码");

			CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
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
					
					new SafeHttpTask<Integer>(mContext) {
						
						@Override
						protected void onPreExecute() {
							PromptManager.showProgressDialog(mContext,"");
							super.onPreExecute();
						}
						
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
							PromptManager.closeProgressDialog();
							if (result != null) {
								// 界面跳转
								Oelement oelement = result.getBody().getOelement();
								if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
			                        // TODO Auto-generated method stub  
									GlobalParams.ISSECURITYPWD=Integer.parseInt(result.getHeader().getSecuritypwd().getTagValue())==0?false:true;
									dialog.dismiss();
									HallMiddleManager.getInstance().changeUI(BindingInfoBank.class);
									
								} else {
									if (oelement.getErrorcode().equals("113")) {
										secpasserrText.setText("");
										secpasserrText.setText(oelement.getErrormsg());
										PromptManager.showToast(mContext,oelement.getErrormsg());
									}else if(oelement.getErrorcode().equals("255")){
										PromptManager.showRelogin(mContext,oelement.getErrormsg(),oelement.getErrorcode());
									}else{
										PromptManager.showToast(mContext, oelement.getErrormsg());
									}
								}
							} else {
								PromptManager.showToast(mContext, "网络状态差，请重试！");
							}
							super.onPostExecute(result);
						}
					}.executeProxy();

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
	}
	
	/**
	 * 取版本号
	 */
	private void getDataRequest() {
		new SafeHttpTask<Integer>(mContext)  {
			
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(mContext,"");
				super.onPreExecute();
			}
			
			@Override
			protected Message doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				UserEngine engine = BeanFactory.getImpl(UserEngine.class);
				return engine.Version();
			}

			@Override
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
		
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						// LotteryMenuElement element = (LotteryMenuElement)
						VersionElement element = (VersionElement) result.getBody().getElements().get(0);
						String versionno = element.getVersionno();
						UpdateManager manager = new UpdateManager(mContext);
						// 检查软件更新
						boolean versionflag=manager.checkUpdate(versionno);
						if(versionflag){
							CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
							builder.setMessage("版本已经是最新");
							builder.setTitle("温馨提示-版本检测");
							builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
								public void onClick(final DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							});

							builder.create().show();
						}
						/*if(!ConstantValue.VERSION.equals(versionno))
						{
							//更新版本操作
							PromptManager.showToast(context, "版本太低新更新");
						}*/
					} else {
						PromptManager.showToast(mContext, oelement.getErrormsg());
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					PromptManager.showToast(mContext, "网络状态差...");
				}
				super.onPostExecute(result);

			}
		}.executeProxy(0);
	}
}
