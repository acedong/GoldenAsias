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
import com.goldenasia.lottery.view.adapter.TwoPoolAdapter;
import com.goldenasia.lottery.view.custom.MyTwoGridView;
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
public class SyxwPickFragment extends BaseFragment {
    private static final String TAG = SscPickFragment.class.getSimpleName();

    private TextView randomRed;
    private LinearLayout oneFaceContainer;
    private LinearLayout twoFaceContainer;
    private LinearLayout threeFaceContainer;
    private LinearLayout assembleFaceContainer;

    // 选号容器
    private MyTwoGridView oneContainer;
    private MyTwoGridView twoContainer;
    private MyTwoGridView threeContainer;
    private MyTwoGridView assembleContainer;

    private TwoPoolAdapter oneAdapter;
    private TwoPoolAdapter twoAdapter;
    private TwoPoolAdapter threeAdapter;
    private TwoPoolAdapter assembleAdapter;

    private List<Integer> oneNums;
    private List<Integer> twoNums;
    private List<Integer> threeNums;
    private List<Integer> assembleNums;

    private SensorManager manager;
    private ShakeListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_syxw_pick, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("十一选五");

        oneFaceContainer = (LinearLayout) findViewById(R.id.syxw_pick_one_area);
        twoFaceContainer = (LinearLayout) findViewById(R.id.syxw_pick_two_area);
        threeFaceContainer = (LinearLayout) findViewById(R.id.syxw_pick_three_area);
        assembleFaceContainer = (LinearLayout) findViewById(R.id.syxw_pick_assemble_area);

        oneContainer = (MyTwoGridView) findViewById(R.id.syxw_pick_one_number_container);
        twoContainer = (MyTwoGridView) findViewById(R.id.syxw_pick_two_number_container);
        threeContainer = (MyTwoGridView) findViewById(R.id.syxw_pick_three_number_container);
        assembleContainer = (MyTwoGridView) findViewById(R.id.syxw_pick_assemble_number_container);

        randomRed = (TextView) findViewById(R.id.syxw_pick_random_red);

        oneNums = new ArrayList<Integer>();
        twoNums = new ArrayList<Integer>();
        threeNums = new ArrayList<Integer>();
        assembleNums = new ArrayList<Integer>();

        oneAdapter = new TwoPoolAdapter(getContext(), 11, oneNums, R.drawable.id_redball);
        twoAdapter = new TwoPoolAdapter(getContext(), 11, twoNums, R.drawable.id_redball);
        threeAdapter = new TwoPoolAdapter(getContext(), 11, threeNums, R.drawable.id_redball);
        assembleAdapter = new TwoPoolAdapter(getContext(), 11, assembleNums, R.drawable.id_redball);

        oneContainer.setAdapter(oneAdapter);
        twoContainer.setAdapter(twoAdapter);
        threeContainer.setAdapter(threeAdapter);
        assembleContainer.setAdapter(assembleAdapter);

        manager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);

        setListener();
    }

    public void setListener() {
        oneContainer.setOnActionUpListener(new MyTwoGridView.OnActionUpListener() {

            @Override
            public void onActionUp(View view, int position) {
                TextView textview=(TextView)view;
                // 判断当前点击的item是否被选中了
                if (!oneNums.contains(position + 1)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    textview.setBackgroundResource(R.drawable.id_redball);
                    textview.setTextColor(getContext().getResources().getColor(R.color.white));
                    oneNums.add(position + 1);
                } else {
                    // 还原背景图片
                    textview.setBackgroundResource(R.drawable.id_defalut_ball);
                    textview.setTextColor(getContext().getResources().getColor(R.color.darkgray));
                    oneNums.remove((Object) (position + 1));
                }


            }
        });

        twoContainer.setOnActionUpListener(new MyTwoGridView.OnActionUpListener() {

            @Override
            public void onActionUp(View view, int position) {
                TextView textview=(TextView)view;
                // 判断当前点击的item是否被选中了
                if (!twoNums.contains(position + 1)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    textview.setBackgroundResource(R.drawable.id_redball);
                    textview.setTextColor(getContext().getResources().getColor(R.color.white));
                    twoNums.add(position + 1);
                } else {
                    // 被选中
                    // 还原背景图片
                    textview.setBackgroundResource(R.drawable.id_defalut_ball);
                    textview.setTextColor(getContext().getResources().getColor(R.color.darkgray));
                    twoNums.remove((Object) (position + 1));
                }


            }
        });

        threeContainer.setOnActionUpListener(new MyTwoGridView.OnActionUpListener() {

            @Override
            public void onActionUp(View view, int position) {
                TextView textview=(TextView)view;
                // 判断当前点击的item是否被选中了
                if (!threeNums.contains(position + 1)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    textview.setBackgroundResource(R.drawable.id_redball);
                    textview.setTextColor(getContext().getResources().getColor(R.color.white));
                    threeNums.add(position + 1);
                } else {
                    // 被选中
                    // 还原背景图片
                    textview.setBackgroundResource(R.drawable.id_defalut_ball);
                    textview.setTextColor(getContext().getResources().getColor(R.color.darkgray));
                    threeNums.remove((Object) (position + 1));
                }


            }
        });


        assembleContainer.setOnActionUpListener(new MyTwoGridView.OnActionUpListener() {

            @Override
            public void onActionUp(View view, int position) {
                TextView textview=(TextView)view;
                // 判断当前点击的item是否被选中了
                if (!assembleNums.contains(position + 1)) {
                    // 如果没有被选中
                    // 背景图片切换到红色
                    textview.setBackgroundResource(R.drawable.id_redball);
                    textview.setTextColor(getContext().getResources().getColor(R.color.white));
                    assembleNums.add(position + 1);
                } else {
                    // 被选中
                    // 还原背景图片
                    textview.setBackgroundResource(R.drawable.id_defalut_ball);
                    textview.setTextColor(getContext().getResources().getColor(R.color.darkgray));
                    assembleNums.remove((Object) (position + 1));
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
