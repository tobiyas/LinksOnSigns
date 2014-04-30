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
	
	private boolean config_useTinyUrlShortener;
	
	private String config_displayTriggerMessage;

	private boolean config_uploadErrorStackTraces;
	
	private String config_linkFormat;
	
	private boolean config_useJSONRawSend;
	
	private boolean config_enableMetrics;
	

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
		config.addDefault("useTinyUrlShortener", false);
		
		config.addDefault("alsoTriggerOnPunch", true);
		config.addDefault("linkFormat", "&3URL: %LINK%");

		config.addDefault("useJSONRawSend", false);

		config.addDefault("uploadErrorStackTraces", false);
		config.addDefault("enableMetrics", true);
		
		config.options().copyDefaults(true);
		plugin.saveConfig();

	}
	
	
	public void reloadConfiguration(){
		plugin.reloadConfig();
		FileConfiguration config = plugin.getConfig();

		config_line0 = config.getString("stdLINE0" , "&9[URL]");
		config_line3 = config.getString("stdLINE3" , "&cclick me");
		config_replaceID = config.getString("preReplaceIdentifier", "newurl");
		config_alsoTriggerOnPunch = config.getBoolean("alsoTriggerOnPunch", true);
		config_useTinyUrlShortener = config.getBoolean("useTinyUrlShortener", false);
		config_displayTriggerMessage = config.getString("displayTriggerMessage", "&5Please click the link above.");
		config_uploadErrorStackTraces = config.getBoolean("uploadErrorStackTraces", false);
		config_linkFormat = config.getString("linkFormat", "&3URL: %LINK%");
		config_useJSONRawSend = config.getBoolean("useJSONRawSend", false);
		config_enableMetrics = config.getBoolean("enableMetrics", true);
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

	public boolean isconfig_useTinyUrlShortener() {
		return config_useTinyUrlShortener;
	}

	public boolean isconfig_uploadErrorStackTraces(){
		return config_uploadErrorStackTraces;
	}

	public String getConfig_linkFormat() {
		return config_linkFormat;
	}

	public boolean isConfig_useJSONRawSend() {
		return config_useJSONRawSend;
	}

	public boolean isConfig_enableMetrics() {
		return config_enableMetrics;
	}
	
	
}