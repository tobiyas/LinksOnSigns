package de.tobiyas.linksonsigns.linkcontainer;

public class LinkContainer {
	
	private String linkName;
	private String URL;
	
	public LinkContainer(String linkName, String URL){
		this.linkName = linkName;
		this.URL = URL;
	}
	
	public String getURL(){
		return URL;
	}
	
	public String getLinkName(){
		return linkName;
	}
}
