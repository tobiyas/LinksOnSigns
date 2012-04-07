package de.tobiyas.linksonsigns.spamfilter;

import org.bukkit.entity.Player;

import de.tobiyas.linksonsigns.LinksOnSigns;

public class SpamRemover implements Runnable{
	
	private Player player;
	private SpamController controller;
	private boolean finished;
	private LinksOnSigns plugin;
	
	private int runnID;
	
	public SpamRemover(Player player, SpamController controller, int removeTime){
		this.finished = false;
		this.player = player;
		this.controller = controller;
		plugin = LinksOnSigns.getPlugin();
		runnID = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, removeTime * 20);
	}

	@Override
	public void run() {
		if(!finished){
			controller.removeFromSpamList(player);
			finished = true;
			plugin.getServer().getScheduler().cancelTask(runnID);
		}
	}
}
