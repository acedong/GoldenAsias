package com.goldenasia.lottery.net;

import android.content.Context;
import android.os.AsyncTask;

import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.util.PromptManager;

public class SafeHttpTask<Params> extends AsyncTask<Params, Void, Message> {
	private Context context;
	public SafeHttpTask(Context contexts){
		this.context=contexts;
	}
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

	@Override
	protected Message doInBackground(Params... params) {
		// TODO Auto-generated method stub
		return null;
	}

}