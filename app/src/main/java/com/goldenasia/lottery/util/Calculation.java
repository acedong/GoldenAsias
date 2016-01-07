package com.goldenasia.lottery.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.goldenasia.lottery.bean.PlayMenu;

public class Calculation {

	private static Calculation instance = new Calculation();

	public Calculation() {

	}

	public static Calculation getInstance() {
		return instance;
	}

	private List<Integer> assembleNums = new ArrayList<Integer>();

	private int ZUS(String s, List<Integer> iL, int m) {
		int total = 0;
		if (m == 0) {
			System.out.println(s);
			total++;
		}
		List<Integer> iL2;
		for (int i = 0; i < assembleNums.size(); i++) {
			iL2 = new ArrayList<Integer>();
			iL2.addAll(iL);
			if (!iL.contains(i)) {
				String str = s + assembleNums.get(i);
				iL2.add(i);
				ZUS(str, iL2, m - 1);
			}
		}
		return total;
	}

	/**
	 * 计算注数 十一选五
	 * 
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public int calculationNote(List<Integer> oneNums, List<Integer> twoNums,
			List<Integer> threeNums, List<Integer> assembleNums,
			PlayMenu playkey) {
		int redC = 0;
		switch (playkey.getJscode()) {
		case "LTZX3":
			List<Integer> aIntersectsB = intersects(oneNums, twoNums);
			List<Integer> aIntersectsC = intersects(oneNums, threeNums);
			List<Integer> bIntersectsC = intersects(twoNums, threeNums);
			List<Integer> abc = intersects(oneNums, bIntersectsC);
			
			redC = oneNums.size() * twoNums.size() * threeNums.size()
					- oneNums.size() * bIntersectsC.size()
					- twoNums.size() * aIntersectsC.size()
					- threeNums.size() * aIntersectsB.size()
					+ 2 * abc.size();
			break;
		case "LTZU3":
			redC = C(assembleNums.size(), 3);
			break;
		case "LTZX2": // A*B-A∩B
			redC = oneNums.size() * twoNums.size()
					- methodJiaoji(oneNums, twoNums);
			break;
		case "LTZU2":
			redC = C(assembleNums.size(), 2);
			break;
		case "LTBDW":
			break;
		case "LTDWD":
			redC = oneNums.size() + twoNums.size() + threeNums.size();
			break;
		case "LTRX1":
			redC = C(assembleNums.size(), 1);
			break;
		case "LTRX2":
			redC = C(assembleNums.size(), 2);
			break;
		case "LTRX3":
			redC = C(assembleNums.size(), 3);
			break;
		case "LTRX4":
			redC = C(assembleNums.size(), 4);
			break;
		case "LTRX5":
			redC = C(assembleNums.size(), 5);
			break;
		case "LTRX6":
			redC = C(assembleNums.size(), 6);
			break;
		case "LTRX7":
			redC = C(assembleNums.size(), 7);
			break;
		case "LTRX8":
			redC = C(assembleNums.size(), 8);
			break;
		}
		return redC;
	}

	/**
	 * 计算注数 时时彩
	 * 
	 * @return
	 */
	public int calculationNote(List<Integer> wanNums, List<Integer> qianNums,
			List<Integer> baiNums, List<Integer> shiNums, List<Integer> geNums,
			List<Integer> assembleNums, PlayMenu playkey) {
		int redC = 0;
		switch (playkey.getJscode()) {
		case "ZX3": // 前三 后三直选 n1*n2*n3
			if (playkey.getPlaymode().equals("AGO"))
				redC = wanNums.size() * qianNums.size() * baiNums.size();
			else if (playkey.getPlaymode().equals("AFTER"))
				redC = baiNums.size() * shiNums.size() * geNums.size();
			break;
		case "ZUS": // 前三 后三组选 组三 C(n,2)*2
			redC = C(assembleNums.size(), 2) * 2;
			break;
		case "ZUL": // 前三 后三组选 组六 C(n,3)
			redC = C(assembleNums.size(), 3);
			break;
		case "ZX2": // 前二 后二直选 n1*n2
			if (playkey.getPlaymode().equals("AGO"))
				redC = wanNums.size() * qianNums.size();
			else if (playkey.getPlaymode().equals("AFTER"))
				redC = shiNums.size() * geNums.size();
			break;
		case "ZU2": // 前二 后二组选 C(n,2)
			redC = C(assembleNums.size(), 2);
			break;
		case "DWD": // 个、十、百、千、万 C(n,1)
			if ((wanNums.size() > 0) || (qianNums.size() > 0)
					|| (baiNums.size() > 0) || (shiNums.size() > 0)
					|| (geNums.size() > 0))
				redC = wanNums.size() + qianNums.size() + baiNums.size() + shiNums.size() + geNums.size();
			break;
		case "BDW1": // 后三一码不定位 前三一码不定位 C(n,1)
			redC = C(assembleNums.size(), 1);
			break;
		case "BDW2": // 后三二码不定位 前三二码不定位 C(n,2)
			redC = C(assembleNums.size(), 2);
			break;
		}

		return redC;
	}

	/**
	 * 计算一个数的阶乘
	 * 
	 * @param num
	 * @return
	 */
	private long factorial(int num) {
		// num=7 7*6*5...*1

		if (num > 1) {
			return num * factorial(num - 1);
		} else if (num == 1 || num == 0) {
			return 1;
		} else {
			throw new IllegalArgumentException("num >= 0");
		}
	}

	/**
	 * 计算金额
	 * 
	 * @return
	 */
	public float calculationMoney() {
		return 0;
	}

	// 阶乘
	private int P(int n) {
		if (n < 0) {
			return 0;
		}

		if (n == 0) {
			return 1;
		}

		int rlt = 1;
		int i = 1;
		while (i < n) {
			rlt *= (i + 1);
			i++;
		}
		return rlt;
	}

	// 排列
	private int A(int n, int m) {
		if (n < m) {
			return 0;
		}
		return P(n) / P(n - m);
	}

	// 组合
	private int C(int n, int m) {
		if (n < m) {
			return 0;
		}
		return A(n, m) / P(m);
	}

	// 交集
	private List<Integer> intersects(List<Integer> arrayFirst, List<Integer> arraySecond) {
		List<Integer> intersectSet = new ArrayList<Integer>(arrayFirst);
		intersectSet.retainAll(arraySecond);
		return intersectSet;
	}
	
	private int methodJiaoji(List<Integer> arrayFirst, List<Integer> arraySecond) {
		List<Integer> newArray = new ArrayList<Integer>();

		for (int i = 0; i < arrayFirst.size(); i++) {
			for (int j = 0; j < arraySecond.size(); j++) {
				if (arraySecond.get(j).equals(arrayFirst.get(i))) {
					newArray.add(arraySecond.get(j));
				}
			}
		}
		return newArray.size();
	}
	
}
