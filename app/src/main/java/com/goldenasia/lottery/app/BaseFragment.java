package com.goldenasia.lottery.app;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.View;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RestRequestManager;

/**
 * Created by Alashi on 2015/12/18.
 */
public class BaseFragment extends Fragment {

    protected ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //即使没有菜单，也要调用setHasOptionsMenu(true)，否则会触发actionbar的bug：
        //ActionBar.NAVIGATION_MODE_LIST时，即使跑了onCreateOptionsMenu，列表和菜单可能没法显示
        setHasOptionsMenu(true);

    }

    @Override
    public void onDestroyView() {
        RestRequestManager.cancelAll(this);
        super.onDestroyView();
    }

 /*   public Toolbar initToolbar(View view) {
        mAppCompatActivity = (AppCompatActivity) mActivity;
        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(toolbarId);
        mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        return toolbar;
    }*/

    public final void setTitle(int resId) {
        getActivity().setTitle(resId);
    }

    public final void setTitle(String string) {
        getActivity().setTitle(string);
    }

    protected boolean isFinishing() {
        if (getActivity() != null) {
            return getActivity().isFinishing();
        }
        return true;
    }

    protected SharedPreferences getSharedPreferences(String name, int mode) {
        if (getActivity() != null) {
            return getActivity().getSharedPreferences(name, mode);
        }
        return null;
    }

    protected final View findViewById(int id) {
        if (id < 0 || null == getView()) {
            return null;
        }

        return getView().findViewById(id);
    }

    protected void showProgress(String msg) {
        if (!isAdded()) {
            return;
        }
        if (null == mProgressDialog) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    protected void hideProgress() {
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    protected void executeRequest(Request request) {
        RestRequestManager.addRequest(request, this);
    }
}
