package com.goldenasia.lottery.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.WithdrawalsInfo;
import com.goldenasia.lottery.view.adapter.WithdrawalsStatusAdapter;
import com.goldenasia.lottery.view.manager.BaseUI;

public class WithdrawalsStatus extends BaseUI {

	private ListView listView;
	private List<String> data ;
	private WithdrawalsStatusAdapter statusAdapter;
	private WithdrawalsInfo withdrawsls;

	public WithdrawalsStatus(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		showInMiddle = (LinearLayout) View.inflate(context,R.layout.nb_page_withdrawals_status, null);
		listView = (ListView) findViewById(R.id.nb_withdrawals_status_list_view);
		listView.setDividerHeight(0);
		statusAdapter = new WithdrawalsStatusAdapter(context,getData());
		listView.setAdapter(statusAdapter);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onResume() {
		withdrawsls=(WithdrawalsInfo)bundle.getSerializable("withdrawsls");
		super.onResume();
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstantValue.VIEW_INFO_WITHDRAWALS_STATUS;
	}
	
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(withdrawsls!=null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", "提取申请成功，已提交银行处理");
			map.put("status", "0");
			
			map.put("bankinfo", withdrawsls.getBankinfo());
			map.put("money",  withdrawsls.getMoney());
			list.add(map);
	
			map = new HashMap<String, Object>();
			Date now = new Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
			String hehe = dateFormat.format( now );
			map.put("title", "预计"+hehe+" 23:59前到账");
			map.put("status", 1);
			list.add(map);
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", "提款申请失败！");
			map.put("status", "1");
			
			list.add(map);
		}
		return list;
	}

}
