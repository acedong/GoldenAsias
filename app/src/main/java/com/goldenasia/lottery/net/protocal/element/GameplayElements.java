package com.goldenasia.lottery.net.protocal.element;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.net.protocal.Element;

public class GameplayElements extends Element{
	
	private GameplayElement gameplay=new GameplayElement();
	
	private List<GameplayElement> gameplayList=new ArrayList<GameplayElement>();

	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "menus");
			gameplay.serializerElement(serializer);
			serializer.endTag(null, "menus");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return "menus";
	}

	public GameplayElement getGameplay() {
		return gameplay;
	}

	public void setGameplay(GameplayElement gameplay) {
		this.gameplay = gameplay;
	}

	public List<GameplayElement> getGameplayList() {
		return gameplayList;
	}

	public void setGameplayList(List<GameplayElement> gameplayList) {
		this.gameplayList = gameplayList;
	}
	
	public void setList(List<GameplayElement> gameplayList){
		this.gameplayList = gameplayList;
	}
	
}
