package edu.uci.ics.fabflixmobile;

import java.util.*;

public class Genre {
	
	private String id; 
	private String name; 
	
	public Genre(String id, String name){
		
		this.id = id; 
		this.name = name; 
		
	}
	
	public String getID() { return id; }
	public String getName() { return name; }
	
}
