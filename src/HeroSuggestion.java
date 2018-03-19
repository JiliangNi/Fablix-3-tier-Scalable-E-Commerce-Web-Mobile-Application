
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.google.gson.JsonObject;
import java.io.*;


import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

// server endpoint URL
@WebServlet("/hero-suggestion")
public class HeroSuggestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*
	 * populate the Marvel heros and DC heros hash map.
	 * Key is hero ID. Value is hero name.
	 */
	
    
    public HeroSuggestion() {
        super();
    }

    /*
     * 
     * Match the query against Marvel and DC heros and return a JSON response.
     * 
     * For example, if the query is "super":
     * The JSON response look like this:
     * [
     * 	{ "value": "Superman", "data": { "category": "dc", "heroID": 101 } },
     * 	{ "value": "Supergirl", "data": { "category": "dc", "heroID": 113 } }
     * ]
     * 
     * The format is like this because it can be directly used by the 
     *   JSON auto complete library this example is using. So that you don't have to convert the format.
     *   
     * The response contains a list of suggestions.
     * In each suggestion object, the "value" is the item string shown in the dropdown list,
     *   the "data" object can contain any additional information.
     * 
     * 
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// setup the response json arrray
			JsonArray jsonArray = new JsonArray();
			
			// get the query string from parameter
			String query = request.getParameter("query");
			
			// return the empty json array if query is null or empty
			if (query == null || query.trim().isEmpty()) {
				response.getWriter().write(jsonArray.toString());
				return;
			}	
			
			// search on marvel heros and DC heros and add the results to JSON Array
			// this example only does a substring match
			// TODO: in project 4, you should do full text search with MySQL to find the matches on movies and stars
			
			
			
			
			/*
            String loginUser = "root";
            String loginPasswd = "1356713njl*@^";
            String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
            */
			
            String [] quert_ls = query.trim().split("\\s+");
            String query_parameter = "+" + quert_ls[0] + "*";
            
            for(int i = 1; i<quert_ls.length;i++) {
            	query_parameter += " " + "+" + quert_ls[i] + "*";
            }
            /* no fuzzy search
            String movie_query = "";
        	movie_query += "select m.id, m.title\n" + 
        			"from movies m\n" + 
        			"where match(m.title) against ('" + query_parameter + "' in boolean mode)\n" +
        			"limit 5;";
        	String star_query = "";
        	star_query += "select s.id, s.name\n" + 
        			"from stars s\n" + 
        			"where match(s.name) against ('" + query_parameter + "' in boolean mode)\n" + 
        			"limit 5;";
             */
            
            String movie_query = "";
        	movie_query += "select m.id, m.title\n" + 
        			"from movies m\n" + 
        			"where match(m.title) against ('" + query_parameter + "' in boolean mode) or edth('" + query + "', m.title, 2) = 1\n" +
        			"limit 5;";
        	String star_query = "";
        	star_query += "select s.id, s.name\n" + 
        			"from stars s\n" + 
        			"where match(s.name) against ('" + query_parameter + "' in boolean mode) or edth('" + query + "', s.name, 2) = 1\n" + 
        			"limit 5;";
            

        	

			
            
            // the following few lines are for connection pooling
            // Obtain our environment naming context

            Context initCtx = new InitialContext();
            if (initCtx == null) {}
                //out.println("initCtx is NULL");

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            if (envCtx == null) {}
                //out.println("envCtx is NULL");

            // Look up our data source
            DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");

            // the following commented lines are direct connections without pooling
            //Class.forName("org.gjt.mm.mysql.Driver");
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

            if (ds == null) {}
                //out.println("ds is null.");

            Connection dbcon = ds.getConnection();
            if (dbcon == null) {}
                //out.println("dbcon is null.");

            
            
            
            // Declare our statement
            
            PreparedStatement movie_statement = null;

            movie_statement = dbcon.prepareStatement(movie_query);
            
            // Perform the query
            ResultSet movie_rs = movie_statement.executeQuery();
            
            
            
            // Iterate through each row of rs
            
            while(movie_rs.next()) {
            	String id = movie_rs.getString(1);
            	String title = movie_rs.getString(2);
            	jsonArray.add(generateJsonObject(id, title, "movies"));
            }
            
            movie_rs.close();
            movie_statement.close();
            
            
            // Declare our statement
            
            PreparedStatement star_statement = null;

            star_statement = dbcon.prepareStatement(star_query);
            
            // Perform the query
            ResultSet star_rs = star_statement.executeQuery();
            
            
           
            // Iterate through each row of rs
            
            while(star_rs.next()) {
            	String id = star_rs.getString(1);
                String name = star_rs.getString(2);
                jsonArray.add(generateJsonObject(id, name, "stars"));
            }
            
            star_rs.close();
            star_statement.close();               
            dbcon.close();
			
			response.getWriter().write(jsonArray.toString());
			return;
			
		} catch (Exception e) {
			System.out.println(e);
			response.sendError(500, e.getMessage());
		}
		
	}
	
	/*
	 * Generate the JSON Object from hero and category to be like this format:
	 * {
	 *   "value": "Iron Man",
	 *   "data": { "category": "marvel", "heroID": 11 }
	 * }
	 * 
	 */
	private static JsonObject generateJsonObject(String id, String title_or_name, String categoryName) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("value", title_or_name);
		JsonObject additionalDataJsonObject = new JsonObject();
		additionalDataJsonObject.addProperty("category", categoryName);
		additionalDataJsonObject.addProperty("ID", id);
		jsonObject.add("data", additionalDataJsonObject);
		return jsonObject;
	}


}
