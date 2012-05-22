/*
 * CastleSiege - by tobiyas
 * http://
 *
 * powered by Kickstarter
 */
 
 package de.tobiyas.linksonsigns.configuration;

 
 import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import de.tobiyas.linksonsigns.LinksOnSigns;

 
 public class Config{
	private LinksOnSigns plugin;

	private String config_line0;
	private String config_line3;
	private String config_replaceID;


	public Config(){
		this.plugin = LinksOnSigns.getPlugin();
		setupConfiguration();
		reloadConfiguration();
	}

	private void setupConfiguration(){
		FileConfiguration config = plugin.getConfig();
		config.options().header("CONFIG: stdLINE0, stdLINE3");

		config.addDefault("stdLINE0", ChatColor.BLUE + "[URL]");
		config.addDefault("stdLINE3", ChatColor.RED + "click me");
		config.addDefault("preReplaceIdentifier", "newurl");

		config.options().copyDefaults(true);
		plugin.saveConfig();

	}
	
	
	private void reloadConfiguration(){
		plugin.reloadConfig();
		FileConfiguration config = plugin.getConfig();

		config_line0 = decodeColor(config.getString("stdLINE0" ,ChatColor.BLUE + "[URL]"));
		config_line3 = decodeColor(config.getString("stdLINE3" ,ChatColor.RED + "click me"));
		config_replaceID = config.getString("preReplaceIdentifier", "newurl");

	}
	
	private String decodeColor(String message){
		return message.replaceAll("(§([a-f0-9]))", "&$2");
	}
	
	
	public String getconfig_line0(){
		return config_line0;
	}
	
	public String getconfig_line3(){
		return config_line3;
	}
	
	public String getconfig_replaceID(){
		return config_replaceID;
	}

}
