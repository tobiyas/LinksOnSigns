package de.tobiyas.linksonsigns.permissions;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.permission.Permission;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import ru.tehkode.permissions.bukkit.PermissionsEx;

import de.tobiyas.linksonsigns.LinksOnSigns;

public class PermissionManager{

	boolean useVault;
	boolean usePex;
	boolean useGroupManager;
	
	private LinksOnSigns plugin;
	
	private Permission vaultPermission;
	private PermissionsEx pexPlugin;
	private GroupManager groupManager;
	
	public PermissionManager(){
		plugin = LinksOnSigns.getPlugin();
		checkForPermissionsPlugin();
	}
	
	private void checkForPermissionsPlugin(){
		useVault = checkForVault();
		if(useVault){
			initVault();
		}
		
		usePex = checkForPex();
		if(usePex){
			pexPlugin = initPEX();
		}
		
		useGroupManager = checkForGroupManger();
		if(useGroupManager){
			groupManager = initGroupManager();
		}
	}
	
	private boolean checkForVault(){
		try{
			Vault vault = (Vault) plugin.getServer().getPluginManager().getPlugin("Vault");
			if(vault != null) return true;
		}catch(Exception e){
			return false;
		}
		return false;
	}
	
	private boolean checkForPex(){
		try{
			PermissionsEx pexPlugin = (PermissionsEx) plugin.getServer().getPluginManager().getPlugin("PermissionsEx");
			if(pexPlugin != null)
				return true;
		}catch(Exception e){
			return false;
		}
		return false;
	}
	
	private boolean checkForGroupManger(){
		try{
			GroupManager groupManager = (GroupManager) plugin.getServer().getPluginManager().getPlugin("GroupManager");
			if(groupManager != null)
				return true;
		}catch(Exception e){
			return false;
		}
		return false;
	}
	
	/**
	 * Sets up vault
	 * 
	 * @return if it worked 
	 */
	private boolean initVault(){
		try{
			RegisteredServiceProvider<Permission> permissionProvider = this.plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
			if (permissionProvider != null)
				vaultPermission = permissionProvider.getProvider();
			}catch(Exception e){}
		
		return (vaultPermission != null);
	}
	
	private PermissionsEx initPEX(){
		return (PermissionsEx) plugin.getServer().getPluginManager().getPlugin("PermissionsEx");
	}
	
	private GroupManager initGroupManager(){
		return (GroupManager) plugin.getServer().getPluginManager().getPlugin("GroupManager");
	}
	
	private boolean hasPermissionGroupManager(final Player base, final String node){
		final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(base);
		if (handler == null)
		{
			return false;
		}
		return handler.has(base, node);
	}
	
	
	/**
	 * The Check of Permissions on the inited Permission-System
	 * 
	 * @param player the Player to check
	 * @param permissionNode the String to check
	 * @return if the Player has Permissions
	 */
	public boolean checkPermissions(Player player, String permissionNode){
		if(useVault){
			return vaultPermission.has(player, permissionNode);
		}
		
		if(usePex){
			return pexPlugin.has(player, permissionNode);
		}
		
		if(useGroupManager){
			return hasPermissionGroupManager(player, permissionNode);
		}
		
		return false;
	}	
	
}
