import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
@WebServlet("/AndroidMovieList")
public class AndroidMovieList extends HttpServlet {
    public String getServletInfo() {
        return "Display the Movie List";
    }
    // Use http GET
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String pre_title = request.getParameter("pre_title");
		String lim = request.getParameter("lim");
		String pagen = request.getParameter("pn");
		
		
		
		System.out.println(pre_title);
		
		ArrayList<Movie> movieList = new ArrayList<Movie>(); 
		
		
        String page_offset = pagen;

        
        
        System.out.println(page_offset);
		
        String loginUser = "root";
        String loginPasswd = "1356713njl*@^";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        response.setContentType("text/html"); // Response mime type
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
            
        try {
            //Class.forName("org.gjt.mm.mysql.Driver");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // Declare our statement
            Statement statement = dbcon.createStatement();
            String query = "";
            ResultSet rs;
            
                 
            
            if(pre_title != null)
            {
                String [] pre_ls = pre_title.trim().split("\\s+");
                String pre_parameter = "+" + pre_ls[0] + "*";
                
                for(int i = 1; i<pre_ls.length;i++) {
                	pre_parameter += " " + "+" + pre_ls[i] + "*";
                }
            	query += "select m.id, m.title, m.year, m.director, group_concat(distinct g.name), group_concat(distinct s.name)  \r\n" + 
            			"from(\r\n" + 
            			"select * \r\n" + 
            			"from movies m\r\n" + 
            			"where match(m.title) against ('" + pre_parameter + "' in boolean mode)) m, genres_in_movies gm, genres g, stars_in_movies sm, stars s \r\n" + 
            			"where m.id = gm.movieID and gm.genreID = g.id and sm.movieID = m.id and sm.starID = s.id \r\n" + 
            			"group by m.id\r\n";
            	
            	String temp2 = Integer.parseInt(page_offset) * Integer.parseInt(lim)+"";
            	query += "limit " + lim + " offset " + temp2 + ";";
            	System.out.println(query);
                rs = statement.executeQuery(query);
                int movies=0;
                while (rs.next()) {
                    String s_id = rs.getString(1);
                    String s_title = rs.getString(2);
                    String s_year = rs.getString(3);
                    String s_director = rs.getString(4);
                    String s_genres = rs.getString(5);
                    String s_stars = rs.getString(6);
                    String [] s_stars_ls = s_stars.split(",");
                    movieList.add(new Movie(s_id, s_title, s_year, s_director, s_genres, s_stars)); 
    				movies+=1;
                    }
                rs.close();
                statement.close();
                dbcon.close();
                
                
                AndroidClass androidMovieList = new AndroidClass(movieList, movies, pre_title, pagen); 
				
				String listOfMoviesJson = new Gson().toJson(androidMovieList); 
				out.println(listOfMoviesJson); 

            }
				
				
				
				
				
				
        } catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception:  " + ex.getMessage());
            } // end while
            out.println("sqlerror");
            return;
        } // end catch SQLException
        catch (java.lang.Exception ex) {
            out.println("javaerror");
            return;
        }
        out.close();
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException
    	{
    	 
    		doGet(request, response); 
    	   
    	}
}