package com.goldenasia.lottery.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.view.GoldenLogin;
import com.goldenasia.lottery.base.net.NetStateHelper;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequestBase;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.base.thread.Future;
import com.goldenasia.lottery.base.thread.FutureListener;
import com.goldenasia.lottery.base.thread.ThreadPool;
import com.goldenasia.lottery.data.GetCommand;
import com.goldenasia.lottery.data.TestCommand;

/**
 * Created by Alashi on 2015/12/22.
 */
public class DebugActivity extends AppCompatActivity {
    private static final String TAG = DebugActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setVisible(false);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

//        findViewById(R.id.btn1).setOnClickListener(onClickListener);
//        findViewById(R.id.btn2).setOnClickListener(onClickListener);
//        findViewById(R.id.btn3).setOnClickListener(onClickListener);
//        findViewById(R.id.btn4).setOnClickListener(onClickListener);

        GoldenAsiaApp.getNetStateHelper().addWeakListener(new NetStateHelper.NetStateListener() {
            @Override
            public void onStateChange(boolean isConnected) {
                String connect = GoldenAsiaApp.getNetStateHelper().isConnected() ? "网络连接：可以" : "网络连接：无网络";
                ((TextView) findViewById(R.id.text)).setText(connect);
            }
        });
        String connect = GoldenAsiaApp.getNetStateHelper().isConnected()? "网络连接：可以": "网络连接：无网络";
        ((TextView)findViewById(R.id.text)).setText(connect);

        FragmentLauncher.launch(DebugActivity.this, GoldenLogin.class);

        this.finish();
    }

    /*private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn1:
                    testhttp();
                    break;
                case R.id.btn2:
                    testGetHttp();
                    break;
                case R.id.btn3:
                    testThreadPool();
                    break;
                case R.id.btn4:
                    FragmentLauncher.launch(DebugActivity.this, TestFragment.class);
                    break;
            }
        }
    };*/

    private void testThreadPool() {
        GoldenAsiaApp.getThreadPool().submit(new ThreadPool.Job<Void>() {
            @Override
            public Void run(ThreadPool.JobContext jc) {
                Log.i(TAG, "run: ThreadPool Job running!");
                return null;
            }
        }, new FutureListener<Void>() {
            @Override
            public void onFutureDone(Future<Void> future) {
                Toast.makeText(DebugActivity.this, "线程池，回调到UI线程", Toast.LENGTH_SHORT).show();
            }
        }, true);
    }

    private void testGetHttp() {
        GetCommand command = new GetCommand();
        command.setV("5.9.1");
        command.setSdk(21);
        command.setUdid("a167f807eaa844bb8bdee2527b7d7eba936c9dce");
        command.setChannel("wandoujia_pc_wandoujia2_homepage");
        command.setRippleSupported(false);
        command.setVc(8951);
        command.setCapacity(3);
        command.setStart(15);
        command.setMax(15);
        RestRequestManager.executeCommand(DebugActivity.this, command,
                restCallback, 0, DebugActivity.this);
    }

    private void testhttp() {
        TestCommand command = new TestCommand();
        command.setId(1234);
        command.setToken("mytokenstring");
        RestRequestManager.executeCommand(DebugActivity.this, command,
                restCallback, 0, DebugActivity.this);
    }

    private RestCallback restCallback = new RestCallback<String>() {
        @Override
        public boolean onRestComplete(RestRequestBase request, RestResponse<String> response) {
            //网络请求调用成功，处理结果
            Log.i(TAG, "onRestComplete: " + response.getResponse());
            return false;
        }
        @Override
        public boolean onRestError(RestRequestBase request, int errCode, String errDesc) {
            //请求失败，异常情况处理
            Log.w(TAG, "onRestError() called with: errCode = [" + errCode + "], errDesc = [" + errDesc + "]");
            return false;
        }
        @Override
        public void onRestStateChanged(RestRequestBase request, RestRequestBase.RestState state) {
            //状态响应，如开始时弹等待对话框，结束时隐藏对话框
            Log.d(TAG, "onRestStateChanged: " + state);
        }
    };
}
