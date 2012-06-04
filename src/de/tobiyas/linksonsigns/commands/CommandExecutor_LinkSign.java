package de.tobiyas.linksonsigns.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tobiyas.linksonsigns.LinksOnSigns;
import de.tobiyas.linksonsigns.permissions.PermissionNode;

public class CommandExecutor_LinkSign implements CommandExecutor {

	private LinksOnSigns plugin;
	
	public CommandExecutor_LinkSign(){
		plugin = LinksOnSigns.getPlugin();
		plugin.getCommand("linksign").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You have to be a player to use this command.");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(!plugin.getPermissionManager().checkPermissions(player, PermissionNode.create.getNode())){
			player.sendMessage(ChatColor.RED + "You dont have Permission.");
			return true;
		}
		
		if(args.length == 0){
			player.sendMessage(ChatColor.RED + "Wrong usage: /linksign <keyword> [URL]");
			return true;
		}
		
		if(args.length == 1){
			String url = plugin.getLinkController().getURLOfLink(args[0]);
			if(url == ""){
				player.sendMessage(ChatColor.RED + args[0] + " not found.");
				return true;
			}
			plugin.getLinkController().addPlayerSelection(player, args[0], url);
			player.sendMessage(ChatColor.GREEN + "Punch on a free Link-Sign (sign with 'newurl' in first line) to save the link to this sign.");
			return true;
		}
	
		String url = args[args.length -1];
		String recogString = args[0];
		
		if(args.length >= 2){
			recogString = "";
			for(int i = 0; i < args.length - 1; i++){
				recogString += args[i] + " ";
			}
			recogString = recogString.substring(0, recogString.length()-1);
			
			if(recogString == ""){
				player.sendMessage(ChatColor.RED + args[0] + " No valid recognization String.");
				return true;
			}
			
			plugin.getLinkController().addPlayerSelection(player, recogString, url);
			player.sendMessage(ChatColor.GREEN + "Punch on a free Link-Sign (sign with 'newurl' in first line) to save the link to this sign.");
			return true;
		}
		
		return false;
	}

}
