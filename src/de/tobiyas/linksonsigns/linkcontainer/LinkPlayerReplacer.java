package de.tobiyas.linksonsigns.linkcontainer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import de.tobiyas.linksonsigns.LinksOnSigns;

public class LinkPlayerReplacer {

	private String linkName;
	private String url;
	private Player player;
	
	private LinksOnSigns plugin;
	
	
	public LinkPlayerReplacer(Player player, String linkName){
		this.linkName = linkName;
		this.url = getUrlFromFile(linkName);
		this.player = player;
		plugin = LinksOnSigns.getPlugin();
	}
	
	public LinkPlayerReplacer(Player player, String linkName, String url){
		this.linkName = linkName;
		this.url = url;
		this.player = player;
		plugin = LinksOnSigns.getPlugin();
	}
	
	private String getUrlFromFile(String linkName){
		return plugin.getLinkController().getURLOfLink(linkName);
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public String getLinkName(){
		return linkName;
	}
	
	public void placeLinkOnSign(Block block){
		World world = player.getWorld();
		Location location = new Location(world, block.getX(), block.getY(), block.getZ());
		
		Sign sign = (Sign) location.getBlock().getState();
		
		if(plugin.getLinkController().getURLOfLink(linkName) == ""){
			plugin.getLinkController().addLinkContainer(linkName, url);
		}
		
		sign.setLine(0, ChatColor.BLUE + "[URL]");
		sign.setLine(3, ChatColor.RED + "klick me");
		
		int stringLength = linkName.length();
		
		if(stringLength > 15){
			sign.setLine(1, linkName.substring(0, 15));
			sign.setLine(2, linkName.substring(15, stringLength));
		}else{
			sign.setLine(1, linkName);
			sign.setLine(2, "");
		}
		sign.update();
	}
}
