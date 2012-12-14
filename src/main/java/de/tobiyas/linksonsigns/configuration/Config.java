/*
 * CastleSiege - by tobiyas
 * http://
 *
 * powered by Kickstarter
 */
 
 package de.tobiyas.linksonsigns.configuration;

 
 import org.bukkit.configuration.file.FileConfiguration;

import de.tobiyas.linksonsigns.LinksOnSigns;

 
 public class Config{
	private LinksOnSigns plugin;

	private String config_line0;
	private String config_line3;
	private String config_replaceID;
	private boolean config_alsoTriggerOnPunch;
	
	private String config_displayTriggerMessage;


	public Config(){
		this.plugin = LinksOnSigns.getPlugin();
		setupConfiguration();
		reloadConfiguration();
	}

	private void setupConfiguration(){
		FileConfiguration config = plugin.getConfig();
		config.options().header("CONFIG: stdLINE0, stdLINE3");

		config.addDefault("stdLINE0", "&9[URL]");
		config.addDefault("stdLINE3", "&cclick me");
		config.addDefault("preReplaceIdentifier", "newurl");
		config.addDefault("displayTriggerMessage", "&5Please click the link above.");
		
		config.addDefault("alsoTriggerOnPunch", true);

		config.options().copyDefaults(true);
		plugin.saveConfig();

	}
	
	
	private void reloadConfiguration(){
		plugin.reloadConfig();
		FileConfiguration config = plugin.getConfig();

		config_line0 = config.getString("stdLINE0" , "&9[URL]");
		config_line3 = config.getString("stdLINE3" , "&cclick me");
		config_replaceID = config.getString("preReplaceIdentifier", "newurl");
		config_alsoTriggerOnPunch = config.getBoolean("alsoTriggerOnPunch", true);
		config_displayTriggerMessage = config.getString("displayTriggerMessage", "&5Please click the link above.");

	}
	
	private String decodeColor(String message){
		return message.replaceAll("(&([a-f0-9]))", "§$2");
	}
	
	
	public String getconfig_line0(){
		return decodeColor(config_line0);
	}
	
	public String getconfig_line3(){
		return decodeColor(config_line3);
	}
	
	public String getconfig_replaceID(){
		return config_replaceID;
	}
	
	public boolean getconfig_alsoTriggerOnPunch(){
		return config_alsoTriggerOnPunch;
	}
	
	public String getconfig_displayTriggerMessage(){
		return decodeColor(config_displayTriggerMessage);
	}

}
