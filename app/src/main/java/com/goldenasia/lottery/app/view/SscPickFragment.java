package com.goldenasia.lottery.app.view;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequestBase;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.data.TestCommand;
import com.goldenasia.lottery.view.adapter.OnePoolAdapter;
import com.goldenasia.lottery.view.custom.MyOneGridView;
import com.goldenasia.lottery.view.custom.MyOneGridView.OnActionUpListener;
import com.goldenasia.lottery.view.sensor.ShakeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016/01/04.
 * @author ACE
 * @功能描述: 选号界面
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SscPickFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class SscPickFragment extends BaseFragment{
    private static final String TAG = SscPickFragment.class.getSimpleName();

    private TextView randomRed;

    private LinearLayout wanFaceContainer;
    private LinearLayout qianFaceContainer;
    private LinearLayout baiFaceContainer;
    private LinearLayout shiFaceContainer;
    private LinearLayout geFaceContainer;
    private LinearLayout assembleFaceContainer;
    // 选号容器
    private MyOneGridView wanContainer;
    private MyOneGridView qianContainer;
    private MyOneGridView baiContainer;
    private MyOneGridView shiContainer;
    private MyOneGridView geContainer;
    private MyOneGridView assembleContainer;

    private OnePoolAdapter wanAdapter;
    private OnePoolAdapter qianAdapter;
    private OnePoolAdapter baiAdapter;
    private OnePoolAdapter shiAdapter;
    private OnePoolAdapter geAdapter;
    private OnePoolAdapter assembleAdapter;

    private List<Integer> wanNums;
    private List<Integer> qianNums;
    private List<Integer> baiNums;
    private List<Integer> shiNums;
    private List<Integer> geNums;
    private List<Integer> assembleNums;

    private SensorManager manager;
    private ShakeListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ssc_pick, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("修改的标题");

        wanFaceContainer = (LinearLayout) findViewById(R.id.ssc_pick_wan_area);
        qianFaceContainer = (LinearLayout) findViewById(R.id.ssc_pick_qian_area);
        baiFaceContainer = (LinearLayout) findViewById(R.id.ssc_pick_bai_area);
        shiFaceContainer = (LinearLayout) findViewById(R.id.ssc_pick_shi_area);
        geFaceContainer = (LinearLayout) findViewById(R.id.ssc_pick_ge_area);

        assembleFaceContainer = (LinearLayout) findViewById(R.id.ssc_pick_assemble_area);

        wanContainer = (MyOneGridView) findViewById(R.id.ssc_pick_wan_number_container);
        qianContainer = (MyOneGridView) findViewById(R.id.ssc_pick_qian_number_container);
        baiContainer = (MyOneGridView) findViewById(R.id.ssc_pick_bai_number_container);
        shiContainer = (MyOneGridView) findViewById(R.id.ssc_pick_shi_number_container);
        geContainer = (MyOneGridView) findViewById(R.id.ssc_pick_ge_number_container);

        assembleContainer = (MyOneGridView) findViewById(R.id.ssc_pick_assemble_number_container);

        randomRed = (TextView) findViewById(R.id.ssc_pick_random_red);

        wanNums = new ArrayList<Integer>();
        qianNums = new ArrayList<Integer>();
        baiNums = new ArrayList<Integer>();
        shiNums = new ArrayList<Integer>();
        geNums = new ArrayList<Integer>();

        assembleNums = new ArrayList<Integer>();

        wanAdapter = new OnePoolAdapter(getContext(), 10, wanNums, R.drawable.id_redball);
        qianAdapter = new OnePoolAdapter(getContext(), 10, qianNums, R.drawable.id_redball);
        baiAdapter = new OnePoolAdapter(getContext(), 10, baiNums, R.drawable.id_redball);
        shiAdapter = new OnePoolAdapter(getContext(), 10, shiNums, R.drawable.id_redball);
        geAdapter = new OnePoolAdapter(getContext(), 10, geNums, R.drawable.id_redball);

        assembleAdapter = new OnePoolAdapter(getContext(), 10, assembleNums, R.drawable.id_redball);

        wanContainer.setAdapter(wanAdapter);
        qianContainer.setAdapter(qianAdapter);
        baiContainer.setAdapter(baiAdapter);
        shiContainer.setAdapter(shiAdapter);
        geContainer.setAdapter(geAdapter);

        assembleContainer.setAdapter(assembleAdapter);

        manager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);

        setListener();
    }

    public void setListener() {
        wanContainer.setOnActionUpListener(new OnActionUpListener() {

            @Override
            public void onActionUp(View view, int position) {
                // 判断当前点击的item是否被选中了
                if (!wanNums.contains(position)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    view.setBackgroundResource(R.drawable.id_redball);
                    wanNums.add(position);
                } else {
                    // 还原背景图片
                    view.setBackgroundResource(R.drawable.id_defalut_ball);
                    wanNums.remove((Object) (position));
                }


            }
        });

        qianContainer.setOnActionUpListener(new OnActionUpListener() {

            @Override
            public void onActionUp(View view, int position) {
                // 判断当前点击的item是否被选中了
                if (!qianNums.contains(position)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    view.setBackgroundResource(R.drawable.id_redball);
                    qianNums.add(position);
                } else {
                    // 被选中
                    // 还原背景图片
                    view.setBackgroundResource(R.drawable.id_defalut_ball);
                    qianNums.remove((Object) (position));
                }


            }
        });

        baiContainer.setOnActionUpListener(new OnActionUpListener() {

            @Override
            public void onActionUp(View view, int position) {
                // 判断当前点击的item是否被选中了
                if (!baiNums.contains(position)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    view.setBackgroundResource(R.drawable.id_redball);
                    baiNums.add(position);
                } else {
                    // 被选中
                    // 还原背景图片
                    view.setBackgroundResource(R.drawable.id_defalut_ball);
                    baiNums.remove((Object) (position));
                }


            }
        });

        shiContainer.setOnActionUpListener(new OnActionUpListener() {

            @Override
            public void onActionUp(View view, int position) {
                // 判断当前点击的item是否被选中了
                if (!shiNums.contains(position)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    view.setBackgroundResource(R.drawable.id_redball);
                    shiNums.add(position);
                } else {
                    // 被选中
                    // 还原背景图片
                    view.setBackgroundResource(R.drawable.id_defalut_ball);
                    shiNums.remove((Object) (position));
                }

            }
        });

        geContainer.setOnActionUpListener(new OnActionUpListener() {

            @Override
            public void onActionUp(View view, int position) {
                // 判断当前点击的item是否被选中了
                if (!geNums.contains(position)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    view.setBackgroundResource(R.drawable.id_redball);
                    geNums.add(position);
                } else {
                    // 被选中
                    // 还原背景图片
                    view.setBackgroundResource(R.drawable.id_defalut_ball);
                    geNums.remove((Object) (position));
                }

            }
        });

        assembleContainer.setOnActionUpListener(new OnActionUpListener() {

            @Override
            public void onActionUp(View view, int position) {
                // 判断当前点击的item是否被选中了
                if (!assembleNums.contains(position)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    view.setBackgroundResource(R.drawable.id_redball);
                    // 摇晃的动画
                    //view.startAnimation(AnimationUtils.loadAnimation(context,R.anim.ia_ball_shake));
                    assembleNums.add(position);
                } else {
                    // 被选中
                    // 还原背景图片
                    view.setBackgroundResource(R.drawable.id_defalut_ball);
                    assembleNums.remove((Object) (position));
                }
            }
        });
    }

    @Override
    public void onResume() {
        // 注册
        listener = new ShakeListener(getContext()) {

            @Override
            public void randomCure() {


            }

        };
        manager.registerListener(listener,manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_FASTEST);

        super.onResume();

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

