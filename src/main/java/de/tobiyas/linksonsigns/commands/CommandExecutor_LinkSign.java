package de.tobiyas.linksonsigns.commands;

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tobiyas.linksonsigns.LinksOnSigns;
import de.tobiyas.linksonsigns.linkcontainer.LinkContainer;
import de.tobiyas.linksonsigns.permissions.PermissionNode;
import de.tobiyas.linksonsigns.shortener.TinyUrlShortener;

public class CommandExecutor_LinkSign implements CommandExecutor {

	private LinksOnSigns plugin;
	
	public CommandExecutor_LinkSign(){
		plugin = LinksOnSigns.getPlugin();
		plugin.getCommand("linksign").setExecutor(this);
	}
	
	@SuppressWarnings("deprecation")
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
			player.sendMessage(ChatColor.RED + "Or: /linksign <'key words'> [URL]");
			return true;
		}
		
		
		String key = "";
		String url = "";
		
		if(args[0].startsWith("'")){
			String completeArgs = "";
			Iterator<String> it = Arrays.asList(args).iterator();
			while(it.hasNext()){
				completeArgs += it.next() + (it.hasNext() ? " " : "");
			}
			
			Pattern pattern = Pattern.compile("\\'(.*?)\\'");
			Matcher matcher = pattern.matcher(completeArgs);
			matcher.find();
			
			if(matcher.groupCount() > 0){
				key = matcher.group(1);
				key = key.replace(" ", " ");
			}
			
			try{
				url = completeArgs.split(key.replace(" ", " "))[1].substring(1);
			}catch(IndexOutOfBoundsException exp){}
		}
		
		if(args.length == 1){
			key = args[0];
			url = "";
		}
				
		
		if(!"".equals(key) && "".equals(url)){
			LinkContainer container = plugin.getLinkController().getURLOfLink(key);
			if(container == null){
				player.sendMessage(ChatColor.RED + key + " not found.");
				return true;
			}
			
			plugin.getLinkController().addPlayerSelection(player, key, url);
			String replaceString = plugin.interactConfig().getconfig_replaceID();
			player.sendMessage(ChatColor.GREEN + "Punch on a free Link-Sign (sign with '" + replaceString + 
					"' in first line) to save the link to this sign.");
			
			return true;
		}
	
		//here key == "" is always true.
		//we always need key + url here.
		if(args.length < 2){
			player.sendMessage(ChatColor.RED + "Wrong usage: /linksign <keyword> [URL]");
			player.sendMessage(ChatColor.RED + "Or: /linksign <'key words'> [URL]");
			return true;
		}
		
		url = args[args.length -1];
		
		if("".equals(key)){
			key = args[0];
		}
		
		boolean isShortened = plugin.interactConfig().isconfig_useTinyUrlShortener();
		if("lsigns".equals(label)){
			isShortened = true;
		}
		if("lsignns".equals(label)){
			isShortened = false;
		}
		
		
		if(args.length >= 2){
			key = "";
			for(int i = 0; i < args.length - 1; i++){
				key += args[i] + " ";
			}
			key = key.substring(0, key.length() - 1);
			
			if(key == ""){
				player.sendMessage(ChatColor.RED + key + " No valid recognization String.");
				return true;
			}
			
			if(isShortened){
				sender.sendMessage(ChatColor.GREEN + "Shortening... This can take a while.");
				SetUrlAsync asyncTask = new SetUrlAsync(player, url, key);
				Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, asyncTask, 1);
			}else{
				post(player, url, key);
			}
		}
			
		return true;
	}
	
	private void post(Player player, String url, String recogString){			
		plugin.getLinkController().addPlayerSelection(player, recogString, url);
		player.sendMessage(ChatColor.GREEN + "Punch on a free Link-Sign (sign with 'newurl' in first line) to save the link to this sign.");
	}
	
	private class SetUrlAsync implements Runnable{

		private final Player player;
		private String url;
		private String recogString;
		
		public SetUrlAsync(Player player, String url, String recogString) {
			this.player = player;
			this.url = url;
			this.recogString = recogString;
		}
		
		@Override
		public void run() {
			try{
				url = TinyUrlShortener.shortenURL(url);
			}catch(Exception exp){
				player.sendMessage(ChatColor.RED + "Error while connecting to tiny URL. Using normal URL");
				plugin.getDebugLogger().logError("Error on link shortening");
				plugin.getDebugLogger().logStackTrace(exp);
			}
			
			post(player, url, recogString);
		}
		
	}

}
