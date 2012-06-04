/*
 * LinksOnSigns - by tobiyas
 * http://
 *
 * powered by Kickstarter
 */

package de.tobiyas.linksonsigns;


import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;
import org.bukkit.plugin.PluginDescriptionFile;

import de.tobiyas.linksonsigns.commands.CommandExecutor_LinkSign;
import de.tobiyas.linksonsigns.configuration.Config;
import de.tobiyas.linksonsigns.linkcontainer.LinkController;
import de.tobiyas.linksonsigns.listeners.Listener_Block;
import de.tobiyas.linksonsigns.listeners.Listener_Player;
import de.tobiyas.util.permissions.PermissionManager;


public class LinksOnSigns extends JavaPlugin{
	private Logger log;
	private PluginDescriptionFile description;
	private static LinksOnSigns plugin;
	
	private LinkController linkController;
	private PermissionManager permissionManager;

	private String prefix;
	
	private Config config;

	
	@Override
	public void onEnable(){
		log = Logger.getLogger("Minecraft");
		description = getDescription();
		prefix = "["+description.getName()+"] ";
		
		plugin = this;
		log("loading "+description.getFullName());
	
		linkController = new LinkController();
		permissionManager = new PermissionManager(this);
		
		config = new Config();

		registerEvents();
		registerCommands();
	}
	
	@Override
	public void onDisable(){
		log("disabled "+description.getFullName());

	}
	public void log(String message){
		log.info(prefix+message);
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
