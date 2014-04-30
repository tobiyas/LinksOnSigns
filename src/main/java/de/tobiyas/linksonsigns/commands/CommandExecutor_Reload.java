package de.tobiyas.linksonsigns.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.tobiyas.linksonsigns.LinksOnSigns;
import de.tobiyas.linksonsigns.permissions.PermissionNode;

public class CommandExecutor_Reload implements CommandExecutor {

	private LinksOnSigns plugin;
	
	public CommandExecutor_Reload() {
		plugin = LinksOnSigns.getPlugin();
		try{
			plugin.getCommand("linksonsignsreload").setExecutor(this);
		}catch(Exception e){
			plugin.log("Could not register command: /linksonsignsreload");
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if(!plugin.getPermissionManager().checkPermissions(sender, PermissionNode.reload.getNode())){
			return true;
		}
		
		plugin.interactConfig().reloadConfiguration();
		plugin.getLinkController().reload();
		
		sender.sendMessage(ChatColor.GREEN + "Reload of " + ChatColor.AQUA + "'LinkOnSigns'" 
				+ ChatColor.GREEN + " done successfully.");
		
		return true;
	}

}
