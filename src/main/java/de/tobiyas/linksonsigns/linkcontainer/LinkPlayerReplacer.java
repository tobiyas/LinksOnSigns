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
	private LinkContainer url;
	private Player player;
	
	private LinksOnSigns plugin;
	
	
	public LinkPlayerReplacer(Player player, String linkName){
		this.linkName = linkName;
		this.url = getUrlFromFile(linkName);
		this.player = player;
		plugin = LinksOnSigns.getPlugin();
	}
	
	public LinkPlayerReplacer(Player player, String linkName, LinkContainer url){
		this.linkName = linkName;
		this.url = url;
		this.player = player;
		plugin = LinksOnSigns.getPlugin();
	}
	
	private LinkContainer getUrlFromFile(String linkName){
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
		
		if(plugin.getLinkController().getURLOfLink(linkName) == null){
			plugin.getLinkController().addLinkContainer(url);
		}
		
		String line0 = plugin.interactConfig().getconfig_line0();
		String line3 = plugin.interactConfig().getconfig_line3();
		
		line0 = ChatColor.translateAlternateColorCodes('&', line0);
		line3 = ChatColor.translateAlternateColorCodes('&', line3);
		
		sign.setLine(0, line0);
		sign.setLine(3, line3);
		
		String copiedLinkName = linkName;
		copiedLinkName = ChatColor.translateAlternateColorCodes('&', copiedLinkName);
		
		int stringLength = copiedLinkName.length();
		
		if(stringLength > 15){
			sign.setLine(1, copiedLinkName.substring(0, 15));
			sign.setLine(2, copiedLinkName.substring(15, stringLength));
		}else{
			sign.setLine(1, copiedLinkName);
			sign.setLine(2, "");
		}
		
		if(player.isOnline()){
			boolean isShortened = plugin.interactConfig().isconfig_useTinyUrlShortener();
			String message = ChatColor.GREEN + "The Sign has been linked ";
			if(isShortened){
				message += "and shortened ";
			}
			
			message += "successfully.";
			player.sendMessage(message);
			
		}
		
		sign.update();
	}
}
