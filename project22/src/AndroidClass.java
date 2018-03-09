

import java.util.*;

public class AndroidClass {
	
	private ArrayList<Movie> listOfMovies; 
	private int numberOfMovies; 
	private String titleQuery; 
	private String currentPageNumber; 
	
	public AndroidClass(ArrayList<Movie> listOfMovies, int numberOfMovies, String titleQuery, String currentPageNumber){
		
		this.listOfMovies = new ArrayList<Movie>(listOfMovies); 
		this.numberOfMovies = numberOfMovies; 
		this.titleQuery = titleQuery; 
		this.currentPageNumber = currentPageNumber; 
		
	}
	
	public void setCurrentPageNumber(String currentPageNumber) { this.currentPageNumber = currentPageNumber; }
	
	public ArrayList<Movie> getListOfMovies() { return listOfMovies; }
	public int getNumberOfMovies() { return numberOfMovies; }
	public String getTitleQuery() { return titleQuery; }
	public String getCurrentPageNumber() { return currentPageNumber; }
	
}