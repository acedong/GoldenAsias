package com.goldenasia.lottery.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Alashi on 2015/12/18.
 */
public class FragmentLauncher extends AppCompatActivity {
    private static final String TAG = FragmentLauncher.class.getSimpleName();

    public static final String KEY_FRAGMENT_NAME = "fragment-name";

    public static void launch(Context context, Class<? extends Fragment> fragment) {
        launch(context, fragment.getName());
    }

    public static void launch(Context context, String fragmentName) {
        Intent intent = new Intent(context, FragmentLauncher.class);
        intent.putExtra(KEY_FRAGMENT_NAME, fragmentName);
        context.startActivity(intent);
    }

    public static void launch(Context context, String fragmentName, Bundle bundle) {
        Intent intent = new Intent(context, FragmentLauncher.class);
        intent.putExtra(KEY_FRAGMENT_NAME, fragmentName);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public static void launchForResult(Fragment fagment, String fragmentName, Bundle bundle, int requestCode) {
        Intent intent = new Intent(fagment.getActivity(), FragmentLauncher.class);
        intent.putExtra(KEY_FRAGMENT_NAME, fragmentName);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        fagment.startActivityForResult(intent, requestCode);
    }

    public static void launchForResult(Activity activity, String fragmentName, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, FragmentLauncher.class);
        intent.putExtra(KEY_FRAGMENT_NAME, fragmentName);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fragmentName = getIntent().getStringExtra(KEY_FRAGMENT_NAME);
        Log.i(TAG, "onCreate " + fragmentName);
        Fragment fragment = Fragment.instantiate(this, fragmentName);
        fragment.setArguments(getIntent().getExtras());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, fragment);
        //ft.commit();
        ft.commitAllowingStateLoss();
    }
}
