package edu.uci.ics.fabflixmobile;

import java.sql.*;
import java.util.*; 

public class Movie {
	
	private String id; 
	private String title; 
	private String year; 
	private String director;

	private String genres; 
	private String stars; 
	
	public Movie(String id, String title,String year, String director,String genres,String stars){
		
		this.id = id; 
		this.title = title; 
		this.year = year; 
		this.director = director; 
		this.genres = genres; 
		this.stars = stars; 
		}
	public String getID() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getYear() {
		return year;
	}

	public String getDirector() {
		return director;
	}

	public String getGenres() {
		return genres;
	}

	public String getStars() {
		return stars;
	}



}


