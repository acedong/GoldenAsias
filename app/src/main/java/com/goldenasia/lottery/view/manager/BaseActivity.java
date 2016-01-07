package com.goldenasia.lottery.view.manager;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.goldenasia.lottery.net.NetUtil;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.util.PromptManager;

public class BaseActivity extends Activity{
	
	protected Context context =BaseActivity.this;
	/**
	 * 访问网络的工具
	 * @author Administrator
	 * @param <Params>
	 */
	protected abstract class ProgressBarAsyncTask<Params> extends AsyncTask<Params, Void, Message> {
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

}
