package com.goldenasia.lottery.view.manager;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.goldenasia.lottery.bean.ConstantInformation;
import com.goldenasia.lottery.bean.IssueAll;
import com.goldenasia.lottery.bean.PlayMenu;
import com.goldenasia.lottery.bean.SerialNumber;
import com.goldenasia.lottery.net.NetUtil;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.util.PromptManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 所有界面的基类
 * 
 * @author Administrator
 * 
 */
public abstract class BaseUI implements View.OnClickListener {
	protected Context context;
	protected Bundle bundle;
	protected Typeface font;
	// 显示到中间容器
	protected ViewGroup showInMiddle;
	protected Map<String, List<PlayMenu>> menuMap=new HashMap<String, List<PlayMenu>>();
	protected Map<String, IssueAll> issueAllMap=new HashMap<String, IssueAll>();
	public BaseUI(Context context) {
		this.context = context;
		this.font = Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
		init();
		setListener();
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	/**
	 * 界面的初始化
	 * 
	 * @return
	 */
	public abstract void init();

	/**
	 * 设置监听
	 * 
	 * @return
	 */
	public abstract void setListener();

	/**
	 * 获取需要在中间容器加载的内容
	 * 
	 * @return
	 */
	public View getChild() {
		// 设置layout参数

		// root=null
		// showInMiddle.getLayoutParams()=null
		// root!=null
		// return root
		// 当LayoutParams类型转换异常，向父容器看齐
		if (showInMiddle.getLayoutParams() == null) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
			showInMiddle.setLayoutParams(params);
		}
		return showInMiddle;
	}

	/**
	 * 获取每个界面的标示——容器联动时的比对依据
	 * @return
	 */
	public abstract int getID();

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	public View findViewById(int id) {
		return showInMiddle.findViewById(id);
	}
	
	/**
	 * 初始化菜单信息
	 */
	public void initMenuArrayData(){
		
		Map<String,String[]> playMenuArrayMap=new HashMap<String, String[]>();
		for(Map.Entry<String, List<PlayMenu>> entry:menuMap.entrySet()){ 
			List<PlayMenu> playMenuList=entry.getValue();
			String[] playMenuArray = new String[playMenuList.size()];
			for(int p=0;p<playMenuList.size();p++){
				PlayMenu playmenu=playMenuList.get(p);
				if(playmenu.getJscode().indexOf("DWD")!=-1)
				{
					playMenuArray[p]="定位胆";
				}else
					playMenuArray[p]=playmenu.getMethodname();
			}
			playMenuArrayMap.put(entry.getKey(), playMenuArray); 
		} 
		
		Map<String,String[]> newplayMenuArrayMap=new HashMap<String, String[]>();
		for (String key : playMenuArrayMap.keySet()) {  
			String[] array=playMenuArrayMap.get(key);
			List<String> arrayStrList=Arrays.asList(array);
			List<String> tempList= new ArrayList<String>();  
			for(int i=0; i< arrayStrList.size(); i++){  
				String ar=arrayStrList.get(i);
		        if(!tempList.contains(ar)){  
		        	tempList.add(ar);  
		        }  
		    } 
			String[] tempdata=new String[tempList.size()];
			for(int i=0;i<tempList.size();i++){  
				tempdata[i] =tempList.get(i);  
		    }  
			newplayMenuArrayMap.put(key, tempdata);
        }  
		
		ConstantInformation.playmuneMap=newplayMenuArrayMap;
		
		Map<String, List<PlayMenu>> menuArrayMap=new HashMap<String, List<PlayMenu>>();
		for(Map.Entry<String, List<PlayMenu>> entry:menuMap.entrySet()){ 
			List<PlayMenu> playMenuList=entry.getValue();
			boolean flag=true;
			
			List<PlayMenu> menuList=new ArrayList<PlayMenu>();
			for(int p=0;p<playMenuList.size();p++){
				PlayMenu playmenu=playMenuList.get(p);
				if(playmenu.getJscode().indexOf("DWD")!=-1&&flag){
					flag=false;
					playmenu.setMethodname("定位胆");
					menuList.add(playmenu);
				}else
					menuList.add(playmenu);
			}
			menuArrayMap.put(entry.getKey(), menuList); 
		} 
		ConstantInformation.playMap=menuArrayMap;
	}
	/**
	 * 初始化任务奖期
	 */
	public void appendIssueInfo(){
		Map<String, String[]> taskIssueMap=new HashMap<String, String[]>();
		for(Map.Entry<String, IssueAll> entry:issueAllMap.entrySet()){ 
			IssueAll issueall=entry.getValue();
			String taskIssue[] =SerialNumber.newInstance(issueall);
			taskIssueMap.put(entry.getKey(), taskIssue);
		} 
		ConstantInformation.taskIssueMap=taskIssueMap;
	}
	
	/**
	 * 访问网络的工具
	 * @author Administrator
	 * @param <Params>
	 */
	protected abstract class MyHttpTask<Params> extends AsyncTask<Params, Void, Message> {
		/**
		 * 类似与Thread.start方法 由于final修饰，无法Override，方法重命名 省略掉网络判断
		 * @param params
		 * @return
		 */
		public final AsyncTask<Params, Void, Message> executeProxy(Params... params) {
			if (NetUtil.checkNet(context)) {
				return super.execute(params);
			} else {
				PromptManager.showNoNetWork(context);
			}
			return null;
		}

	}

	/**
	 * 要出去的时候调用
	 */
	public void onPause() {
		
	}

	/**
	 * 进入到界面之后
	 */
	public void onResume() {
		
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}
}
