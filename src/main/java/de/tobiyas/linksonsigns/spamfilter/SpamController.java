package de.tobiyas.linksonsigns.spamfilter;

import java.util.LinkedList;

import org.bukkit.entity.Player;

public class SpamController {

	private LinkedList<Player> spamList;
	
	public SpamController(){
		spamList = new LinkedList<Player>();
	}
	
	public void addToSpamList(Player player){
		spamList.add(player);
		new SpamRemover(player, this, 3);
	}
	
	public boolean isOnSpamList(Player player){
		return spamList.contains(player);
	}
	
	public void removeFromSpamList(Player player){
		spamList.remove(player);
	}
}
