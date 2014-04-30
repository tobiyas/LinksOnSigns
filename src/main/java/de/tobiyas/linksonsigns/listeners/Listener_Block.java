/*
 * LinksOnSigns - by tobiyas
 * http://
 *
 * powered by Kickstarter
 */

package de.tobiyas.linksonsigns.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import de.tobiyas.linksonsigns.LinksOnSigns;
import de.tobiyas.linksonsigns.permissions.PermissionNode;


public class Listener_Block implements Listener {
	private LinksOnSigns plugin;

	public Listener_Block(LinksOnSigns plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onSignChange(SignChangeEvent event){
		if(!checkIfLinkSign(event.getLine(0))) return;
		Player player = event.getPlayer();
		if(!plugin.getPermissionManager().checkPermissions(player, PermissionNode.create.getNode())){
			event.setCancelled(true);
			return;
		}
	}
	
	private boolean checkIfLinkSign(String line){
		return line.equals(plugin.interactConfig().getconfig_line0());
	}


}
