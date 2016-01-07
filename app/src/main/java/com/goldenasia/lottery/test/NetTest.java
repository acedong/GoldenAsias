package com.goldenasia.lottery.test;

import android.test.AndroidTestCase;

import com.goldenasia.lottery.net.NetUtil;

public class NetTest extends AndroidTestCase {
	public void testNetType(){
		NetUtil.checkNet(getContext());
	}
}
