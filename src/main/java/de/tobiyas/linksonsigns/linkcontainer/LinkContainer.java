package de.tobiyas.linksonsigns.linkcontainer;

public class LinkContainer {
	
	private String linkName;
	private String URL;
	private String specialText;
	
	public LinkContainer(String linkName, String URL, String specialText){
		this.linkName = linkName;
		this.URL = URL;
		this.specialText = specialText;
	}
	
	public String getURL(){
		return URL;
	}
	
	public String getLinkName(){
		return linkName;
	}

	public String getSpecialText() {
		return specialText;
	}
	
	public boolean hasSpecialText() {
		return specialText != null && !specialText.isEmpty();
	}
}
