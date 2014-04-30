package de.tobiyas.linksonsigns.linkcontainer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.tobiyas.linksonsigns.LinksOnSigns;
import de.tobiyas.linksonsigns.spamfilter.SpamController;
import de.tobiyas.util.config.YAMLConfigExtended;

public class LinkController {

	private List<LinkContainer> linkContainer;
	private List<LinkPlayerReplacer> playerReplacer;
	private YAMLConfigExtended config;
	private String savePath;
	private SpamController spamController;
	
	private LinksOnSigns plugin;
	
	public LinkController(){
		plugin = LinksOnSigns.getPlugin();
		linkContainer = new LinkedList<LinkContainer>();
		playerReplacer = new ArrayList<LinkPlayerReplacer>();
		spamController = new SpamController();
	}
	
	public void reload(){
		linkContainer = initLinkContainer();
	}
	
	private List<LinkContainer> initLinkContainer(){
		savePath = plugin.getDataFolder() + File.separator + "links.yml";
		
		initStructure(savePath);
		List<LinkContainer> container = new ArrayList<LinkContainer>();
		
		config = new YAMLConfigExtended(savePath).load();

		if(!config.getValidLoad()){
			plugin.log("Error on reading links.yml");
			return container;
		}
		
		Set<String> linkNames = getYAMLChildren(config, "links");
		
		for(String linkName : linkNames){
			String path = "links." + linkName;
			
			if(!config.isString(path)){
				continue;
			}
			
			
			String url = config.getString(path);
			linkName.replace("'", "");
			
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
		String unconvertedLinke = linkName;
		linkName = linkName.replaceAll("§", "&");
		
		for(LinkContainer container : linkContainer){
			if(container.getLinkName().equals(linkName)){
				return container.getURL();
			}
			if(container.getLinkName().equals(unconvertedLinke)){
				return container.getURL();
			}
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
		config.load();
		
		config.set("links." + linkName, URL);
		
		if(!config.save()){
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
