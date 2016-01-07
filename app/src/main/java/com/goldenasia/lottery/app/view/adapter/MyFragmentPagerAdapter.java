package com.goldenasia.lottery.app.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.goldenasia.lottery.app.view.ContainerFragment;
import com.goldenasia.lottery.app.view.FragmentCategory;
import com.goldenasia.lottery.app.view.FragmentHistory;
import com.goldenasia.lottery.app.view.FragmentHome;
import com.goldenasia.lottery.app.view.FragmentUser;

/**
 * Created on 2016/01/06.
 * @author ACE
 * @功能描述: UI切换控制器
 *
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 4;
    private FragmentHome myFragment1 = null;
    private FragmentCategory myFragment2 = null;
    private FragmentHistory myFragment3 = null;
    private FragmentUser myFragment4 = null;


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        myFragment1 = new FragmentHome();
        myFragment2 = new FragmentCategory();
        myFragment3 = new FragmentHistory();
        myFragment4 = new FragmentUser();
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case ContainerFragment.PAGE_ONE:
                fragment = myFragment1;
                break;
            case ContainerFragment.PAGE_TWO:
                fragment = myFragment2;
                break;
            case ContainerFragment.PAGE_THREE:
                fragment = myFragment3;
                break;
            case ContainerFragment.PAGE_FOUR:
                fragment = myFragment4;
                break;
        }
        return fragment;
    }


}

