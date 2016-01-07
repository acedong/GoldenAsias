package com.goldenasia.lottery.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.FragmentLauncher;
import com.goldenasia.lottery.app.view.SoftKeyBoardSatusView.SoftkeyBoardListener;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequestBase;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.data.TestCommand;

/**
 * Created on 2015/12/31.
 * @author ACE
 * @功能描述: 登录页面
 *
 */

public class GoldenLogin extends BaseFragment implements  SoftkeyBoardListener{
    private static final String TAG = GoldenLogin.class.getSimpleName();

    private SoftKeyBoardSatusView satusView;
    private LinearLayout login_layout;
    private int scroll_dx;
    private int screenHeight;

    private EditText userName;
    private EditText password;
    private ImageView userNameClear,passwordClear;// 清空用户名

    private boolean mbDisplayFlg = false;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor ed;

    private TextView versionText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.golden_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar activitybar=((AppCompatActivity)getActivity()).getSupportActionBar();


        //处理遮挡登录界面
        satusView = (SoftKeyBoardSatusView) findViewById(R.id.login_soft_status_view);
        login_layout = (LinearLayout) findViewById(R.id.login_layout);
        screenHeight =getActivity().getWindowManager().getDefaultDisplay().getHeight();
        satusView.setSoftKeyBoardListener(this);

        userName = (EditText) findViewById(R.id.login_edit_account);
        userNameClear = (ImageView) findViewById(R.id.login_account_edit_clear);
        password = (EditText) findViewById(R.id.login_edit_password);
        passwordClear = (ImageView) findViewById(R.id.login_password_edit_clear);

        userName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (userName.getText().toString().length() > 0) {
                    userNameClear.setVisibility(View.VISIBLE);
                }

            }
        });

        userName.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub
                return false;
            }
        });

        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (password.getText().toString().length() > 0) {
                    passwordClear.setVisibility(View.VISIBLE);
                }

            }
        });

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub
                return false;
            }
        });
        findViewById(R.id.login_login_btn).setOnClickListener(onClickListener);

        //处理自动登录 1、第一次登录成功记录数据。默认自动登录 2、自动登录

    }

    /**
     * 处理初使化数据
     */
    @Override
    public void onResume() {

        super.onResume();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_account_edit_clear: //帐号清空 点帐号清空时默认清空密码
                    testhttp();
                    break;
                case R.id.login_password_edit_clear://密码清空

                    break;
                case R.id.login_arrow:      //多帐号切换选择

                    break;
                case R.id.login_login_btn:      //帐号登录BUT
                    if (TextUtils.isEmpty(userName.getText().toString())) {
                        Toast.makeText(getContext(), "请输入用户名", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (TextUtils.isEmpty(password.getText().toString())) {
                        Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (checkUserInfo()) {

                        testhttp();
                        FragmentLauncher.launch(getActivity(), ContainerFragment.class);
                    }

                    break;

            }
        }
    };

    /**
     * 用户信息验证
     * @return
     */
    private boolean checkUserInfo() {
        Boolean flag = false;
        try {
            //验证用户名密码的有效格式服务
            flag = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block

        }
        return flag;
    }

    /**
     * 处理登录数据的请求
     */
    private void requestLogin(){

    }

    /**
     * 获取数据信息
     */
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

    /***
     * 菜单数据作
     * @param menu
     * @param inflater
     */
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

    @Override
    public void keyBoardStatus(int w, int h, int oldw, int oldh) {

    }

    @Override
    public void keyBoardVisable(int move) {
        int[] location = new int[2];
        login_layout.getLocationOnScreen(location);
        int btnToBottom = screenHeight - location[1] - login_layout.getHeight();
        scroll_dx = btnToBottom > move ? 0 : move - btnToBottom;
        login_layout.scrollBy(0, scroll_dx);
    }

    @Override
    public void keyBoardInvisable(int move) {
        login_layout.scrollBy(0, -scroll_dx);
    }
}
