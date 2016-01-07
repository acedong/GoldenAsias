package com.goldenasia.lottery.view.adapter;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.PlayMenu;
import com.goldenasia.lottery.bean.ShoppingCart;
import com.goldenasia.lottery.bean.ShoppingDetail;
import com.goldenasia.lottery.bean.Ticket;
import com.goldenasia.lottery.view.custom.SwipeListView;

public class ShoppingAdapter extends BaseAdapter {
	private Context contexts;
	private int mRightWidth = 0;
	private Typeface fonts = null;
	
	private SwipeListView mSwipeListView;
	private LayoutInflater inflater;
	public ShoppingAdapter(Context context,SwipeListView mSwipeListView) {
		this.contexts=context;
		this.mSwipeListView=mSwipeListView;
		this.inflater=LayoutInflater.from(context);
		fonts=Typeface.createFromAsset(contexts.getAssets(),"fontawesome-webfont.ttf");
	}

	@Override
	public int getCount() {
		boolean flag=ShoppingCart.getInstance().getTicketsMap().isEmpty();
		if (!flag) {
			return ShoppingCart.getInstance().getTicketsMap().get(ShoppingCart.getInstance().getLotteryid()).size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return ShoppingCart.getInstance().getTicketsMap().get(ShoppingCart.getInstance().getLotteryid()).get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.nb_shopping_row,null);

			holder.pickNum = (TextView) convertView.findViewById(R.id.nb_shopping_item_picks);
			holder.playname = (TextView) convertView.findViewById(R.id.nb_shopping_item_playname);
			holder.money = (TextView) convertView.findViewById(R.id.nb_shopping_item_money);
			holder.modemoney = (TextView) convertView.findViewById(R.id.nb_shopping_item_modemoney);
			holder.notenum = (TextView) convertView.findViewById(R.id.nb_shopping_item_notenum);
			
			holder.item_right_button = (Button)convertView.findViewById(R.id.nb_shopping_item_delete);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Ticket ticket = ShoppingCart.getInstance().getTicketsMap().get(ShoppingCart.getInstance().getLotteryid()).get(position);
		Integer lotterytype=ShoppingCart.getInstance().getLotterytype();
		
		ShoppingDetail detail=analyzeDetail(ticket,lotterytype);
		
		String moneyText = contexts.getResources().getString( R.string.is_shopping_list_money_title);
		moneyText = StringUtils.replaceEach(moneyText, new String[] { "MONEY" }, new String[] { ticket.getNoteMoney()+"" });
		holder.money.setText(moneyText);
		
		String modemoneyText = contexts.getResources().getString( R.string.is_shopping_list_mode_title);
		modemoneyText = StringUtils.replaceEach(modemoneyText, new String[] { "MODE" }, new String[] { ticket.getMoneyMode().getLucreUnit() });
		holder.modemoney.setText(modemoneyText);
		
		String noteNumText = contexts.getResources().getString( R.string.is_shopping_list_notenum_title);
        noteNumText = StringUtils.replaceEach(noteNumText, new String[] { "NOTE" }, new String[] { ticket.getNum()+"" });
		holder.notenum.setText(noteNumText);
		
		holder.pickNum.setText(detail.getPickstr());
		holder.playname.setText(detail.getPlaymenuName());
		
		String resTrashIco=contexts.getResources().getString(R.string.fa_trash_o);
		holder.item_right_button.setText(resTrashIco+"删除");
		holder.item_right_button.setTypeface(fonts);
		
		holder.item_right_button.setLayoutParams(new RelativeLayout.LayoutParams(contexts.getResources().getDisplayMetrics().widthPixels/3, RelativeLayout.LayoutParams.MATCH_PARENT));
		holder.item_right_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mSwipeListView.closeAnimate(position);
				mSwipeListView.dismiss(position);
			}
		});
		
		return convertView;
	}

	class ViewHolder {
		TextView pickNum;
		TextView playname;
		TextView money;
		TextView modemoney;
		TextView notenum;
		
		Button item_right_button;
	}
	
    private ShoppingDetail analyzeDetail(Ticket ticket,Integer lotterytype){
		ShoppingDetail detail=new ShoppingDetail();
		PlayMenu playmenu=ticket.getSelectPlay();
		switch (lotterytype) {
			case 0:
				if(playmenu.getJscode().equalsIgnoreCase("ZX3")){	//前三 后三直选
					if(playmenu.getPlaymode().equals("AGO"))
					{
						String wan="",qian="",bai="";
						if(ticket.getWanNum().length()>0){
							wan=ticket.getWanNum();
						}else{
							wan="-";
						}
						if(ticket.getQianNum().length()>0){
							qian=ticket.getQianNum();
						}else{
							qian="-";
						}
						if(ticket.getBaiNum().length()>0){
							bai=ticket.getBaiNum();
						}else{
							bai="-";
						}
						detail.setPickstr(wan+" , "+qian+" , "+bai);
						detail.setPlaymenuName(playmenu.getMethodname());
					}else if(playmenu.getPlaymode().equals("AFTER"))
					{
						String bai="",shi="",ge="";
						if(ticket.getBaiNum().length()>0){
							bai=ticket.getBaiNum();
						}else{
							bai="-";
						}
						if(ticket.getShiNum().length()>0){
							shi=ticket.getShiNum();
						}else{
							shi="-";
						}
						if(ticket.getGeNum().length()>0){
							ge=ticket.getGeNum();
						}else{
							ge="-";
						}
						detail.setPickstr(bai+" , "+shi+" , "+ge);
						detail.setPlaymenuName(playmenu.getMethodname());
					}
				}		
				else if(playmenu.getJscode().equalsIgnoreCase("ZUS")){	//前三 后三组选 组三 C(n,2)*2
					String assemble="";
					if(ticket.getAssembleSscNum().length()>0){
						assemble=ticket.getAssembleSscNum();
					}else{
						assemble="-";
					}
					detail.setPickstr(assemble);
					detail.setPlaymenuName(playmenu.getMethodname());
				}		
				else if(playmenu.getJscode().equalsIgnoreCase("ZUL")){	//前三 后三组选 组六 C(n,3)
					String assemble="";
					if(ticket.getAssembleSscNum().length()>0){
						assemble=ticket.getAssembleSscNum();
					}else{
						assemble="-";
					}
					detail.setPickstr(assemble);
					detail.setPlaymenuName(playmenu.getMethodname());
				}		
				else if(playmenu.getJscode().equalsIgnoreCase("ZX2")){	//前二 后二直选 n1*n2
					
					if(playmenu.getPlaymode().equals("AGO"))
					{
						String wan="",qian="";
						if(ticket.getWanNum().length()>0){
							wan=ticket.getWanNum();
						}else{
							wan="-";
						}
						if(ticket.getQianNum().length()>0){
							qian=ticket.getQianNum();
						}else{
							qian="-";
						}
						detail.setPickstr(wan+" , "+qian);
						detail.setPlaymenuName(playmenu.getMethodname());
					}else if(playmenu.getPlaymode().equals("AFTER"))
					{
						String shi="",ge="";
						if(ticket.getShiNum().length()>0){
							shi=ticket.getShiNum();
						}else{
							shi="-";
						}
						if(ticket.getGeNum().length()>0){
							ge=ticket.getGeNum();
						}else{
							ge="-";
						}
						detail.setPickstr(shi+" , "+ge);
						detail.setPlaymenuName(playmenu.getMethodname());
					}
				}		
				else if(playmenu.getJscode().equalsIgnoreCase("ZU2")){	//前二 后二组选 C(n,2)
					String assemble="";
					if(ticket.getAssembleSscNum().length()>0){
						assemble=ticket.getAssembleSscNum();
					}else{
						assemble="-";
					}
					detail.setPickstr(assemble);
					detail.setPlaymenuName(playmenu.getMethodname());
				}		
				else if(playmenu.getJscode().equalsIgnoreCase("DWD")){	//个、十、百、千、万 C(n,1)
					String wan="",qian="",bai="",shi="",ge="";
					if(ticket.getWanNum().length()>0){
						wan=ticket.getWanNum();
					}else{
						wan="-";
					}
					if(ticket.getQianNum().length()>0){
						qian=ticket.getQianNum();
					}else{
						qian="-";
					}
					if(ticket.getBaiNum().length()>0){
						bai=ticket.getBaiNum();
					}else{
						bai="-";
					}
					if(ticket.getShiNum().length()>0){
						shi=ticket.getShiNum();
					}else{
						shi="-";
					}
					if(ticket.getGeNum().length()>0){
						ge=ticket.getGeNum();
					}else{
						ge="-";
					}
					detail.setPickstr(wan+" , "+qian+" , "+bai+" , "+shi+" , "+ge);
					detail.setPlaymenuName(playmenu.getMethodname());
				}		
				else if(playmenu.getJscode().equalsIgnoreCase("BDW1")){	//后三一码不定位 前三一码不定位 C(n,1)
					String assemble="";
					if(ticket.getAssembleSscNum().length()>0){
						assemble=ticket.getAssembleSscNum();
					}else{
						assemble="-";
					}
					detail.setPickstr(assemble);
					detail.setPlaymenuName(playmenu.getMethodname());
				}else if(playmenu.getJscode().equalsIgnoreCase("BDW2")){	//后三二码不定位 前三二码不定位 C(n,1)
					String assemble="";
					if(ticket.getAssembleSscNum().length()>0){
						assemble=ticket.getAssembleSscNum();
					}else{
						assemble="-";
					}
					detail.setPickstr(assemble);
					detail.setPlaymenuName(playmenu.getMethodname());
				}
				break;
			case 1:
				
				break;
			case 2:
				if (playmenu.getJscode().equals("LTZX3")) // 前三直选
				{
					String one="",two="",three="";
					if(ticket.getOneNum().length()>0){
						one=ticket.getOneNum();
					}else{
						one="-";
					}
					if(ticket.getTwoNum().length()>0){
						two	= ticket.getTwoNum();			
					}else{
						two="-";
					}
					if(ticket.getThreeNum().length()>0){
						three=ticket.getThreeNum();
					}else{
						three="-";
					}
					detail.setPickstr(one+" , "+two+" , "+three);
					detail.setPlaymenuName(playmenu.getMethodname());
				} else if (playmenu.getJscode().equals("LTZU3")) // 前三组选
				{
					String assemble="";
					if(ticket.getAssembleSyFiveNum().length()>0){
						assemble=ticket.getAssembleSyFiveNum();
					}else{
						assemble="-";
					}
					detail.setPickstr(assemble);
					detail.setPlaymenuName(playmenu.getMethodname());
				} else if (playmenu.getJscode().equals("LTZX2")) // 前二直选
				{
					String one="",two="";
					if(ticket.getOneNum().length()>0){
						one=ticket.getOneNum();
					}else{
						one="-";
					}
					if(ticket.getTwoNum().length()>0){
						two	= ticket.getTwoNum();			
					}else{
						two="-";
					}
					detail.setPickstr(one+" , "+two);
					detail.setPlaymenuName(playmenu.getMethodname());
				} else if (playmenu.getJscode().equals("LTZU2")) // 前二组选
				{
					String assemble="";
					if(ticket.getAssembleSyFiveNum().length()>0){
						assemble=ticket.getAssembleSyFiveNum();
					}else{
						assemble="-";
					}
					detail.setPickstr(assemble);
					detail.setPlaymenuName(playmenu.getMethodname());
				} else if (playmenu.getJscode().equals("LTDWD")){ // 第一位 二位 三位
					detail.setPickstr((ticket.getOneNum()!=null&&!ticket.getOneNum().equals("")?ticket.getOneNum():"-")+" , "+(ticket.getTwoNum()!=null&&!ticket.getTwoNum().equals("")?ticket.getTwoNum():"-")+" , "+(ticket.getThreeNum()!=null&&!ticket.getThreeNum().equals("")?ticket.getThreeNum():"-"));
					detail.setPlaymenuName(playmenu.getMethodname());
				} else if (playmenu.getJscode().equals("LTRX1")){ 	// 任选一中一
					String assemble="";
					if(ticket.getAssembleSyFiveNum().length()>0){
						assemble=ticket.getAssembleSyFiveNum();
					}else{
						assemble="-";
					}
					detail.setPickstr(assemble);
					detail.setPlaymenuName(playmenu.getMethodname());
				} else if (playmenu.getJscode().equals("LTRX2")){ 	// 任选二中二
					String assemble="";
					if(ticket.getAssembleSyFiveNum().length()>0){
						assemble=ticket.getAssembleSyFiveNum();
					}else{
						assemble="-";
					}
					detail.setPickstr(assemble);
					detail.setPlaymenuName(playmenu.getMethodname());
				} else if (playmenu.getJscode().equals("LTRX3")){ 	// 任选三中三
					String assemble="";
					if(ticket.getAssembleSyFiveNum().length()>0){
						assemble=ticket.getAssembleSyFiveNum();
					}else{
						assemble="-";
					}
					detail.setPickstr(assemble);
					detail.setPlaymenuName(playmenu.getMethodname());
				} else if (playmenu.getJscode().equals("LTRX4")){	//任选四中四
					String assemble="";
					if(ticket.getAssembleSyFiveNum().length()>0){
						assemble=ticket.getAssembleSyFiveNum();
					}else{
						assemble="-";
					}
					detail.setPickstr(assemble);
					detail.setPlaymenuName(playmenu.getMethodname());
				}else if (playmenu.getJscode().equals("LTRX5")){	//任选五中五
					String assemble="";
					if(ticket.getAssembleSyFiveNum().length()>0){
						assemble=ticket.getAssembleSyFiveNum();
					}else{
						assemble="-";
					}
					detail.setPickstr(assemble);
					detail.setPlaymenuName(playmenu.getMethodname());
				}else if (playmenu.getJscode().equals("LTRX6")){	//任选六中五
					String assemble="";
					if(ticket.getAssembleSyFiveNum().length()>0){
						assemble=ticket.getAssembleSyFiveNum();
					}else{
						assemble="-";
					}
					detail.setPickstr(assemble);
					detail.setPlaymenuName(playmenu.getMethodname());
				}else if (playmenu.getJscode().equals("LTRX7")){	//任选七中五
					String assemble="";
					if(ticket.getAssembleSyFiveNum().length()>0){
						assemble=ticket.getAssembleSyFiveNum();
					}else{
						assemble="-";
					}
					detail.setPickstr(assemble);
					detail.setPlaymenuName(playmenu.getMethodname());
				}else if (playmenu.getJscode().equals("LTRX8")){	//任选八中五
					String assemble="";
					if(ticket.getAssembleSyFiveNum().length()>0){
						assemble=ticket.getAssembleSyFiveNum();
					}else{
						assemble="-";
					}
					detail.setPickstr(assemble);
					detail.setPlaymenuName(playmenu.getMethodname());
				}
			break;
		}
		return detail;
	}
    
}