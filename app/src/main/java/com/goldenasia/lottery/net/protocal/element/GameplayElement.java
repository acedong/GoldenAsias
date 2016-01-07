package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.PlayMenu;
import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Leaf;

public class GameplayElement extends Element{
	
	// <iscellphone>1</iscellphone>
	private Leaf iscellphones = new Leaf("iscellphone", "1");
	// <jscode></jscode>
	private Leaf jscodes = new Leaf("jscode");				//玩法编码
	// <methodname></methodname>
	private Leaf methodnames = new Leaf("methodname"); 		//玩法名称
	// <methodid></methodid>
	private Leaf methodIds = new Leaf("methodid");			//玩法ID
	
	
	/******************服务器回复**********************/
	private PlayMenu playmenu=null;

	public PlayMenu getPlaymenu() {
		return playmenu;
	}

	public void setPlaymenu(PlayMenu playmenu) {
		this.playmenu = playmenu;
	}

	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "menu");
			iscellphones.serializerLeaf(serializer);
			jscodes.serializerLeaf(serializer);
			methodnames.serializerLeaf(serializer);
			methodIds.serializerLeaf(serializer);
			serializer.endTag(null, "menu");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return "ZUS";
	}

	public Leaf getIscellphones() {
		return iscellphones;
	}

	public Leaf getJscodes() {
		return jscodes;
	}

	public Leaf getMethodnames() {
		return methodnames;
	}

	public Leaf getMethodIds() {
		return methodIds;
	}

}
