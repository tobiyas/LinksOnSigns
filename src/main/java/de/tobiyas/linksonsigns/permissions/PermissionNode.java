package de.tobiyas.linksonsigns.permissions;

public enum PermissionNode {
	
	create("linksonsigns.create"),
	read("linksonsigns.use"),
	reload("linksonsigns.reload");
	
	private String node;
	PermissionNode(String node){
		this.node = node;
	}
	
	public String getNode(){
		return node;
	}
	
	public String toString(){
		return node;
	}

}
