/*
 * LinksOnSigns - by tobiyas
 * http://
 *
 * powered by Kickstarter
 */

package de.tobiyas.linksonsigns;


import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.linksonsigns.commands.CommandExecutor_LinkSign;
import de.tobiyas.linksonsigns.configuration.Config;
import de.tobiyas.linksonsigns.linkcontainer.LinkController;
import de.tobiyas.linksonsigns.listeners.Listener_Block;
import de.tobiyas.linksonsigns.listeners.Listener_Player;
import de.tobiyas.util.debug.logger.DebugLogger;
import de.tobiyas.util.metrics.SendMetrics;
import de.tobiyas.util.permissions.PermissionManager;


public class LinksOnSigns extends JavaPlugin{
	private DebugLogger debugLogger;
	
	private PluginDescriptionFile description;
	private static LinksOnSigns plugin;
	
	private LinkController linkController;
	private PermissionManager permissionManager;

	private String prefix;
	
	private Config config;

	
	@Override
	public void onEnable(){
		debugLogger = new DebugLogger(this);
		debugLogger.setAlsoToPlugin(true);
		
		description = getDescription();
		prefix = "["+description.getName()+"] ";
		
		plugin = this;
		log("loading "+description.getFullName());
	
		linkController = new LinkController();
		permissionManager = new PermissionManager(this);
		
		config = new Config();

		boolean enableUploads = config.isconfig_uploadErrorStackTraces();
		debugLogger.enableUploads(enableUploads);
		
		registerEvents();
		registerCommands();
		
		initMetrics();
	}
	
	@Override
	public void onDisable(){
		log("disabled "+description.getFullName());

	}
	
	public void log(String message){
		debugLogger.log(prefix+message);
	}
	
	public void logStacktrace(Exception exp, String message){
		debugLogger.logError(message);
		debugLogger.logStackTrace(exp);
	}
	
	private void initMetrics(){
		SendMetrics.sendMetrics(this);
	}


	private void registerEvents(){
		new Listener_Block(this);
		new Listener_Player(this);
	}
	
	private void registerCommands(){
		new CommandExecutor_LinkSign();
	}


	public static LinksOnSigns getPlugin(){
		return plugin;
	}
	
	public LinkController getLinkController(){
		return linkController;
	}
	
	public PermissionManager getPermissionManager(){
		return permissionManager;
	}
	
	public Config interactConfig(){
		return config;
	}

}
