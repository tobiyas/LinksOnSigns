/*
 * LinksOnSigns - by tobiyas
 * http://
 *
 * powered by Kickstarter
 */

package de.tobiyas.linksonsigns.listeners;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener; 
import org.bukkit.event.EventHandler; 

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.tobiyas.linksonsigns.LinksOnSigns;
import de.tobiyas.linksonsigns.linkcontainer.LinkPlayerReplacer;
import de.tobiyas.linksonsigns.permissions.PermissionNode;


public class Listener_Player implements Listener {
	private LinksOnSigns plugin;

	public Listener_Player(LinksOnSigns plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Block block = event.getClickedBlock();
		if(event.getClickedBlock() == null) return;
		if(event.getClickedBlock().getType() == null) return;
		
		if(!((block.getType().equals(Material.SIGN)) || block.getType().equals(Material.WALL_SIGN) || block.getType().equals(Material.SIGN_POST))) return;
		
		Sign sign = (Sign) block.getState();
		Player player = (Player) event.getPlayer();
			
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(!event.getAction().equals(Action.LEFT_CLICK_BLOCK))
				return;
			else
				if(checkLinkSignCreation(player, block)){
					event.setCancelled(true);
				}
			return;
		}
		
		if(!(isLinkSign(sign))) return;
		
		event.setCancelled(true);
		if(!plugin.getPermissionManager().checkPermissions(player, PermissionNode.read.getNode())){
			player.sendMessage(ChatColor.RED + "You dont have Permission.");
			return;
		}
		
		if(plugin.getLinkController().getSpamController().isOnSpamList(player)) return;
		String url = getUrlFromSign(sign);
		if(url.equals("")) return;
		
		event.getPlayer().sendMessage(ChatColor.BLUE + "" + ChatColor.UNDERLINE + url);
		event.getPlayer().sendMessage(ChatColor.DARK_GREEN + "Please open your Chat and click on the Link.");
		
		plugin.getLinkController().getSpamController().addToSpamList(player);
	}
	
	private boolean isLinkSign(Sign sign){
		return sign.getLine(0).equals(ChatColor.BLUE + "[URL]");
	}
	
	private String getUrlFromSign(Sign sign){
		String temp = plugin.getLinkController().getURLOfLink(sign.getLine(1) + sign.getLine(2));
		return temp;
	}
	
	private boolean checkLinkSignCreation(Player player, Block block){
		if(!isFreeLinkSign((Sign) block.getState())) return false;
		
		LinkPlayerReplacer replacer = plugin.getLinkController().getPlayerSelection(player);
		if(replacer == null) return false;
		
		if(!plugin.getPermissionManager().checkPermissions(player, PermissionNode.create.getNode())){
			player.sendMessage(ChatColor.RED + "You dont have Permission.");
			return true;
		}
		
		replacer.placeLinkOnSign(block);
		plugin.getLinkController().removeSelection(player);
		player.sendMessage(ChatColor.GREEN + "Sign linked.");
		return true;
	}
	
	private boolean isFreeLinkSign(Sign sign){
		return sign.getLine(0).toLowerCase().contains(plugin.interactConfig().getconfig_replaceID());
	}


}
