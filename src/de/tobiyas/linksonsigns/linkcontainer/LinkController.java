package de.tobiyas.linksonsigns.linkcontainer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.tobiyas.linksonsigns.LinksOnSigns;
import de.tobiyas.linksonsigns.spamfilter.SpamController;

public class LinkController {

	private ArrayList<LinkContainer> linkContainer;
	private ArrayList<LinkPlayerReplacer> playerReplacer;
	private YamlConfiguration config;
	private String savePath;
	private SpamController spamController;
	
	private LinksOnSigns plugin;
	
	public LinkController(){
		plugin = LinksOnSigns.getPlugin();
		linkContainer = initLinkContainer();
		playerReplacer = new ArrayList<LinkPlayerReplacer>();
		spamController = new SpamController();
	}
	
	private ArrayList<LinkContainer> initLinkContainer(){
		savePath = plugin.getDataFolder() + File.separator + "links.yml";
		
		initStructure(savePath);
		ArrayList<LinkContainer> container = new ArrayList<LinkContainer>();
		
		config = new YamlConfiguration();
		
		try{
			config.load(savePath);
		}catch(Exception e){
		}
		
		Set<String> linkNames = getYAMLChildren(config, "links");
		
		for(String linkName : linkNames){
			String url = config.getString("links." + linkName);
			container.add(new LinkContainer(linkName, url));
		}
		
		return container;
	}
	
	private void initStructure(String path){
		File dir = new File(plugin.getDataFolder() + File.separator);
		if(!dir.exists())
			dir.mkdir();
			
		File file = new File(path);
		
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				plugin.log("Error on file creation: links.yml");
				return;
			}		
		}
	}
	
	/**
	 * Util for YAMLReader to get all child-keys as Set<String> for a given Node 
	 * 
	 * @param config the YAMLConfiguration to search through
	 * @param yamlNode the Node to get the children from
	 * @return the children as Set<String>
	 */
	private static Set<String> getYAMLChildren(YamlConfiguration config, String yamlNode){
		try{
			ConfigurationSection tempMem = config.getConfigurationSection(yamlNode);
			Set<String> tempSet = tempMem.getKeys(false);
			return tempSet;
			
		}catch(Exception e){
			Set<String> empty = new LinkedHashSet<String>();
			return empty;
		}
	}
	
	public String getURLOfLink(String linkName){
		
		for(LinkContainer container : linkContainer){
			if(container.getLinkName().equals(linkName))
				return container.getURL();
		}
		
		return "";
	}
	
	public void addPlayerSelection(Player player, String linkName, String url){
		for(LinkPlayerReplacer replacer : playerReplacer){
			if(replacer.getPlayer().equals(player)){
				playerReplacer.remove(replacer);
				break;
			}
		}
		
		playerReplacer.add(new LinkPlayerReplacer(player, linkName, url));
	}
	
	public void addPlayerSelection(Player player, String linkName){
		for(LinkPlayerReplacer replacer : playerReplacer){
			if(replacer.getPlayer().equals(player)){
				playerReplacer.remove(replacer);
				break;
			}
		}
		
		playerReplacer.add(new LinkPlayerReplacer(player, linkName));
	}
	
	public void removeSelection(Player player){
		for(LinkPlayerReplacer replacer : playerReplacer){
			if(replacer.getPlayer().equals(player)){
				playerReplacer.remove(replacer);
				break;
			}
		}
	}
	
	public void addLinkContainer(String linkName, String URL){
		config.set("links." + linkName, URL);
		try {
			config.save(savePath);
		} catch (IOException e) {
			plugin.log("saving Links failed.");
		}
		
		linkContainer.add(new LinkContainer(linkName, URL));
	}
	
	public LinkPlayerReplacer getPlayerSelection(Player player){
		for(LinkPlayerReplacer replacer : playerReplacer){
			if(replacer.getPlayer() == player) return replacer;
		}
		return null;
	}
	
	public SpamController getSpamController(){
		return spamController;
	}
}
