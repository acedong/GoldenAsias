package com.goldenasia.lottery.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.AppendInfo;
import com.goldenasia.lottery.bean.AppendTaskDetail;
import com.goldenasia.lottery.bean.AppendTaskDetailItems;
import com.goldenasia.lottery.bean.AppendUndo;
import com.goldenasia.lottery.engine.BettingEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.AppendTaskDetailElement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.CustomDialog;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.adapter.AppendDetailsAdapter;
import com.goldenasia.lottery.view.adapter.AppendDetailsAdapter.ViewHolder;
import com.goldenasia.lottery.view.controls.IUScrollView;
import com.goldenasia.lottery.view.controls.IUScrollView.OnFirstChildOnTopListener;
import com.goldenasia.lottery.view.controls.IUScrollView.OnGetHeightListener;
import com.goldenasia.lottery.view.custom.MyListView;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.OrderOperation;
import com.goldenasia.lottery.view.manager.TitleManager;

@SuppressLint("UseSparseArrays")
public class AppendOrderDetails extends BaseUI implements OnClickListener{

	private IUScrollView scrollView;
	private ListView listView;
	private AppendDetailsAdapter appendDetailsAdapter;
	private List<AppendTaskDetailItems> mList = new ArrayList<AppendTaskDetailItems>();
	
	private TextView cnname;
	private TextView methodname;
	private TextView beginissue;
	private TextView issuecount;
	private TextView taskprice;
	private TextView finishedcount;
	private TextView wincount;
	private TextView codes;
	private TextView taskno;
	private TextView modes;
	private TextView cancelcount;
	private TextView winprize;
	private TextView finishprice;
	private TextView stoponwin;
	private TextView status;
	private TextView cancelprice;
	private TextView dypointdec;
	private TextView begintime;
	
	private boolean isOP;
	private Button appendUndoBut;
	private Button cancelDetails;
	private Button unselectedDetails;
	private Button selectallDetails;
	private LinearLayout undoOperateArea;
	private LinearLayout undoPromptArea;
	
	public AppendOrderDetails(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.nb_lottery_order_append_details_hall, null);
		
		cnname=(TextView)findViewById(R.id.nb_item_append_details_cnname);
		methodname=(TextView)findViewById(R.id.nb_item_append_details_methodname);
		beginissue=(TextView)findViewById(R.id.nb_item_append_details_beginissue);
		issuecount=(TextView)findViewById(R.id.nb_item_append_details_issuecount);
		taskprice=(TextView)findViewById(R.id.nb_item_append_details_taskprice);
		finishedcount=(TextView)findViewById(R.id.nb_item_append_details_finishedcount);
		wincount=(TextView)findViewById(R.id.nb_item_append_details_wincount);
		codes=(TextView)findViewById(R.id.nb_item_append_details_codes);
		
		taskno=(TextView)findViewById(R.id.nb_item_append_details_taskno);
		modes=(TextView)findViewById(R.id.nb_item_append_details_modes);
		cancelcount=(TextView)findViewById(R.id.nb_item_append_details_cancelcount);
		winprize=(TextView)findViewById(R.id.nb_item_append_details_winprize);
		finishprice=(TextView)findViewById(R.id.nb_item_append_details_finishprice);
		stoponwin=(TextView)findViewById(R.id.nb_item_append_details_stoponwin);
		status=(TextView)findViewById(R.id.nb_item_append_details_status);
		cancelprice=(TextView)findViewById(R.id.nb_item_append_details_cancelprice);
		
		dypointdec=(TextView)findViewById(R.id.nb_item_append_details_dypointdec);
		begintime=(TextView)findViewById(R.id.nb_item_append_details_begintime);
		
		appendUndoBut = (Button) findViewById(R.id.nb_lottery_append_undo_button);
		cancelDetails=(Button) findViewById(R.id.nb_lottery_order_append_details_cancel);
		unselectedDetails=(Button) findViewById(R.id.nb_lottery_order_append_details_unselected);
		selectallDetails=(Button) findViewById(R.id.nb_lottery_order_append_details_selectall);
		undoOperateArea=(LinearLayout) findViewById(R.id.nb_lottery_append_undo_operate_area);
		undoPromptArea=(LinearLayout) findViewById(R.id.nb_lottery_append_undo_prompt_area);
		
		scrollView = (IUScrollView) findViewById(R.id.nb_lottery_order_append_details_scrollview);
		listView = (ListView) findViewById(R.id.nb_lottery_order_append_details_secondview);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
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
		
		cancelDetails.setOnClickListener(this);
		unselectedDetails.setOnClickListener(this);
		selectallDetails.setOnClickListener(this);
		appendUndoBut.setOnClickListener(this);
		
		appendDetailsAdapter=new AppendDetailsAdapter(context);
		listView.setAdapter(appendDetailsAdapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			 
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				// 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
            	AppendTaskDetailItems appendTaskItems=mList.get(pos);
				if(!appendTaskItems.getStatusdes().equals("已完成")){
					ViewHolder holder = (ViewHolder) arg1.getTag();
					// 改变CheckBox的状态
					holder.cb_op.toggle();
				
					if (holder.cb_op.isChecked()) {
						holder.cb_op.setVisibility(View.VISIBLE);
						RelativeLayout.LayoutParams lps = (android.widget.RelativeLayout.LayoutParams) holder.rl_content.getLayoutParams();
						lps.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.app_item_content_margin);
					} else {
						holder.cb_op.setVisibility(View.GONE);
						RelativeLayout.LayoutParams lps = (android.widget.RelativeLayout.LayoutParams) holder.rl_content.getLayoutParams();
						lps.leftMargin = 0;
					}
					// 将CheckBox的选中状况记录下来
					AppendDetailsAdapter.getIsSelected().put(pos, holder.cb_op.isChecked());
				}
			}
		});
		
	}
	
	@Override
	public void onResume() {
		TitleManager.getInstance().changeSettingTitle("追号详情");
		isOP=false;
		appendDetailsAdapter.setOP(isOP);
		getDataRequest();
		super.onResume();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.nb_lottery_append_undo_button:
	
				isOP = !isOP;
				removeCheckItem();
				if (isOP) {
					String appendUndoSuccessTitle = context.getResources().getString(R.string.is_lottery_append_undo_button_success);
					appendUndoBut.setText(appendUndoSuccessTitle);
					undoOperateArea.setVisibility(View.VISIBLE);
					undoPromptArea.setVisibility(View.GONE);
				} else {
					String appendUndoEditorTitle = context.getResources().getString(R.string.is_lottery_append_undo_button_editor);
					appendUndoBut.setText(appendUndoEditorTitle);
					undoOperateArea.setVisibility(View.GONE);
					undoPromptArea.setVisibility(View.VISIBLE);
				}
				appendDetailsAdapter.setOP(isOP);
				appendDetailsAdapter.notifyDataSetChanged();
	
				break;
			case R.id.nb_lottery_order_append_details_cancel:
				HashMap<Integer, Boolean> isSelectedCancel = new HashMap<Integer, Boolean>();
				for (int i = 0; i < mList.size(); i++) {
					isSelectedCancel.put(i, false);
				}
				//appendDetailsAdapter.refresh(appendTaskCancel);
				AppendDetailsAdapter.setIsSelected(isSelectedCancel);
				appendDetailsAdapter.notifyDataSetChanged();
				break;
			case R.id.nb_lottery_order_append_details_unselected:
				HashMap<Integer, Boolean> isSelectedUnselected = AppendDetailsAdapter.getIsSelected();
				for (int i = 0; i < mList.size(); i++) {
					AppendTaskDetailItems items=mList.get(i);
					if (isSelectedUnselected.get(i)) {
						isSelectedUnselected.put(i, false);
					} else {
						if(!items.getStatusdes().equals("已完成")){
							isSelectedUnselected.put(i, true);
						}else
							isSelectedUnselected.put(i, false);
					}
				}
				//appendDetailsAdapter.refresh(appendTaskUnselected);
				AppendDetailsAdapter.setIsSelected(isSelectedUnselected);
				appendDetailsAdapter.notifyDataSetChanged();
				break;
			case R.id.nb_lottery_order_append_details_selectall:
				HashMap<Integer, Boolean> isSelectedSelectall = new HashMap<Integer, Boolean>();
				for (int i = 0; i < mList.size(); i++) {
					AppendTaskDetailItems items=mList.get(i);
					if(!items.getStatusdes().equals("已完成"))
						isSelectedSelectall.put(i, true);
					else
						isSelectedSelectall.put(i, false);
				}
				// 数量设为list的长度
				//checkNum = list.size();
				// 刷新listview和TextView的显示
				//appendDetailsAdapter.refresh(appendTaskSelectall);
				AppendDetailsAdapter.setIsSelected(isSelectedSelectall);
				appendDetailsAdapter.notifyDataSetChanged();
				break;
			default:
				break;
		}
	}
	private String projectid="";
	private void removeCheckItem() {

		String appendUndoSuccessTitle = context.getResources().getString(R.string.is_lottery_append_undo_button_success);
		if (appendUndoSuccessTitle.equals(appendUndoBut.getText())) {
			
			HashMap<Integer, Boolean> isSelectedUnselected = AppendDetailsAdapter.getIsSelected();
			List<String> undoList=new ArrayList<String>();
			
			for (int m=0; m<appendDetailsAdapter.getAppendTaskList().size();m++) {
				AppendTaskDetailItems unAppendstr=appendDetailsAdapter.getAppendTaskList().get(m);
				String unapp=unAppendstr.getEntry();
				if(isSelectedUnselected.get(m)){
					undoList.add(unapp);
				}
			}
			
			if(undoList.size()>0){
				String[] unString=new String[undoList.size()];
				for(int i=0;i<undoList.size();i++){
					unString[i]=undoList.get(i);
				}
				
				final AppendUndo appendUndo=new AppendUndo();
				appendUndo.setCellphone("1");
				appendUndo.setId(projectid);
				appendUndo.setIdsList(unString);
				
				CustomDialog.Builder builder = new CustomDialog.Builder(context);
				builder.setMessage("你确定撤销追号吗？");
				builder.setTitle("温馨提示");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						//设置你的操作事项
						getDataUndoRequest(appendUndo);
					}
				});

				builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

				builder.create().show();
				
			}
			
//			for (AppendTaskDetailItems ai : mList) {
//				if (ai.isChecked()) {
//					checkedList.add(ai);
//				} else {
//					unCheckedList.add(ai);
//				}
//			}

//			mList.clear();
//			mList.addAll(unCheckedList);

			//同步数据库,物理删除
//			syncFavoriteTable(checkedList);
		}
	}
	
	/**
	 * 获取请求信息
	 */
	private void getDataRequest() {

		new MyHttpTask<String>() {
			
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}

			@Override
			protected Message doInBackground(String... params) {
				// 获取数据——业务的调用
				BettingEngine engine = BeanFactory.getImpl(BettingEngine.class);
				return engine.appendOrderDetailsInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();

					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						AppendTaskDetailElement taskDetailElement=(AppendTaskDetailElement)result.getBody().getElements().get(0);
						AppendTaskDetail appendTask=taskDetailElement.getTaskDetail();
						TitleManager.getInstance().changeSettingTitle(appendTask.getTitle());
						
						projectid=appendTask.getTaskid();
						cnname.setText(appendTask.getCnname());
						methodname.setText(appendTask.getMethodname());
						beginissue.setText(appendTask.getBeginissue());
						issuecount.setText(appendTask.getIssuecount());
						taskprice.setText(appendTask.getTaskprice());
						finishedcount.setText(appendTask.getFinishedcount());
						wincount.setText(appendTask.getWincount());
						codes.setText(appendTask.getCodes());
						taskno.setText(appendTask.getTaskno());
						modes.setText(appendTask.getModes());
						if(appendTask.getCancelcount().equals("")||null==appendTask.getCancelcount()){
							cancelcount.setText("----");
						}else{
							cancelcount.setText(appendTask.getCancelcount());
						}
						winprize.setText(appendTask.getWinprize());
						finishprice.setText(appendTask.getFinishprice());
						if(appendTask.getStoponwin().equals("0")){
							stoponwin.setText("是");
						}else
							stoponwin.setText("否");
						status.setText(appendTask.getStatus());
						if(appendTask.getStatus().equals("已完成")){
							appendUndoBut.setEnabled(false);
						}else{
							appendUndoBut.setEnabled(true);
						}
						cancelprice.setText(appendTask.getCancelprice());
						dypointdec.setText(appendTask.getDypointdec());
						begintime.setText(appendTask.getBegintime());
						
						mList=taskDetailElement.getTaskDetailList();
						appendDetailsAdapter.refresh(taskDetailElement.getTaskDetailList());
						appendDetailsAdapter.initDate();
						appendDetailsAdapter.notifyDataSetChanged();
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
						}
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					// 如何提示用户
					PromptManager.showToast(context, "网络状态差，刷新试试");
				}

				super.onPostExecute(result);
			}
		}.executeProxy(bundle.getString("detailsid"));
	}
	
	/**
	 * 获取请求信息
	 */
	private void getDataUndoRequest(AppendUndo appendUndo) {

		new MyHttpTask<AppendUndo>() {

			@Override
			protected Message doInBackground(AppendUndo... params) {
				// 获取数据——业务的调用
				BettingEngine engine = BeanFactory.getImpl(BettingEngine.class);
				return engine.appendOrderDetailsUndoInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();

					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						getDataRequest();
						PromptManager.showToast(context, "成功撤销追号");
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
						}
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					PromptManager.showToast(context, "网络状态差，刷新试试");
				}
				super.onPostExecute(result);
			}
		}.executeProxy(appendUndo);

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstantValue.VIEW_APPENDORDERDETAILS;
	}
	
}
