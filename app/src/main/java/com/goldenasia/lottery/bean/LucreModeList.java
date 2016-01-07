package com.goldenasia.lottery.bean;

import java.util.ArrayList;
import java.util.List;

public class LucreModeList {
	private static List<LucreMode> lucremode=new ArrayList<LucreMode>();
	
	static{
		lucremode.add(new LucreMode("元", 1, 1.00));
		lucremode.add(new LucreMode("角", 2, 0.10));
		lucremode.add(new LucreMode("分", 3, 0.01));
	}

	public static List<LucreMode> getLucremode() {
		return lucremode;
	}

	public static void setLucremode(List<LucreMode> lucremode) {
		LucreModeList.lucremode = lucremode;
	}
	
}
