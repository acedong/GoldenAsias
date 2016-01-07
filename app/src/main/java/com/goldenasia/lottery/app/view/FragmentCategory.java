package com.goldenasia.lottery.app.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequestBase;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.data.TestCommand;

/**
 * Created on 2016/01/04.
 * @author ACE
 * @功能描述: 购彩大厅
 */
public class FragmentCategory extends BaseFragment {
	private static final String TAG = FragmentHistory.class.getSimpleName();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_history, null);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);


	}

	private void testhttp() {
		TestCommand command = new TestCommand();
		command.setId(1234);
		command.setToken("mytokenstring");
		RestRequestManager.executeCommand(getActivity(), command, restCallback, 0, this);
	}

	private RestCallback restCallback = new RestCallback() {
		@Override
		public boolean onRestComplete(RestRequestBase request, RestResponse response) {
			return false;
		}

		@Override
		public boolean onRestError(RestRequestBase request, int errCode, String errDesc) {
			return false;
		}

		@Override
		public void onRestStateChanged(RestRequestBase request, RestRequestBase.RestState state) {

		}
	};

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		Log.d(TAG, "onCreateOptionsMenu: ");
		//inflater.inflate(R.menu.menu_test_fragment, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected: ");
        /*if (item.getItemId() == R.id.action_test) {
            Toast.makeText(getActivity(), "点击了测试菜单", Toast.LENGTH_SHORT).show();
            return true;
        }*/
		return super.onOptionsItemSelected(item);
	}
}