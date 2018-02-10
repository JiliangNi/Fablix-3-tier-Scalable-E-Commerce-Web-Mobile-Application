import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SingleMovie extends HttpServlet {
    public String getServletInfo() {
        return "Display all information of single movie";
    }
    // Use http GET
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String loginUser = "root";
        String loginPasswd = "1356713njl*@^";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        String movieid = request.getParameter("movieid");
        response.setContentType("text/html"); // Response mime type
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        
        if(movieid == null)
        {
            out.println("<HTML>" + "<HEAD><TITLE>" + "Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>Did not get movie id! </P></BODY></HTML>");
            return;
        }

        try {
            //Class.forName("org.gjt.mm.mysql.Driver");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // Declare our statement
            Statement statement = dbcon.createStatement();
            
            
            String query = "select m.id, m.title, m.year, m.director, group_concat(distinct g.name), group_concat(distinct s.name)\r\n" + 
            		"from movies as m, genres_in_movies gim, genres g, stars_in_movies sim, stars s\r\n" + 
            		"where m.id = '" + movieid + "' and m.id = gim.movieId and gim.genreId = g.id and m.id = sim.movieId and sim.starId = s.id\r\n" + 
            		"group by m.id\r\n" + 
            		";";
            
            // Perform the query
            ResultSet rs = statement.executeQuery(query);
            // Iterate through each row of rs
            out.println("<html>\r\n" + 
            		"	<head>\r\n" + 
            		"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\r\n" + 
            		"		<title>Single Movie</title>\r\n" + 
            		"	<style type = \"text/css\"> \r\n" + 
            		"	.menu{position:relative;width:2000px;height:50px;margin:0 auto;padding:8px 0 0;margin-bottom:4px;z-index:99;}\r\n" + 
            		"	.menu .nav-menu>li{float:left;position:relative;margin-right:50}\r\n" + 
            		"	</style>\r\n"+
            		"	</head>\r\n" + 
            		"	<body style=\"background: url(../../project2/img/homebg.jpg) no-repeat 0 0;\r\n" + 
            		"	background-size:100%;\">\r\n" + 
            		"	<h1 style=\"text-align:center; left:0px; top:0px;font-family:'Comic Sans MS', cursive, sans-serif;font-size:250%;\"> <a href = \"/project2/Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>"+
            		"	<body>\r\n" + 
            		"		<br>" +
            		"		<table border=\"\" width=\"100%\" style=\"background-color: #FFF5EE; opacity: 0.8; filter: alpha(opacity = 30);word-wrap:break-word\">\r\n" + 
            		"			<tbody>");
            out.println("				<tr>\r\n" + 
            		"					<td>id</td>\r\n" + 
            		"					<td>title</td>\r\n" + 
            		"					<td>year</td>\r\n" + 
            		"					<td>director</td>\r\n" + 
            		"					<td>genres</td>\r\n" + 
            		"					<td>stars</td>\r\n" + 
            		"				</tr>");
            
            while (rs.next()) {
                String s_id = rs.getString(1);
                String s_title = rs.getString(2);
                String s_year = rs.getString(3);
                String s_director = rs.getString(4);
                String s_genres = rs.getString(5);
                String s_stars = rs.getString(6);
                String [] s_stars_ls = s_stars.split(",");
                String [] s_genres_ls = s_genres.split(",");
                
                
                
                out.println("				<tr>\r\n" + 
                		"					<td>" + s_id + "</td>\r\n" + 
                		"					<td>" + "<a href=\"SingleMovie?movieid=" + s_id + "\">" + s_title + "</a>"+ "</td>\r\n" + 
                		"					<td>" + s_year + "</td>\r\n" + 
                		"					<td>" + s_director + "</td>\r\n");
                
                
                
                
                
                
                out.println("					<td>");
                for(int i =0;i<s_genres_ls.length;i++)
                {
                	out.println("<a href=\"MovieList?genre=" + s_genres_ls[i] + "&lim=10\">"+s_genres_ls[i] + "</a>\r\n");
                }      
                
                out.println("</td>\r\n");
                
                out.println("					<td>");
                for(int i =0;i<s_stars_ls.length;i++)
                {
                	out.println("<a href=\"SingleStar?starname=" + s_stars_ls[i] + "\">" +s_stars_ls[i] + "</a>\r\n");
                }
                out.println("</td>\r\n");
                out.println("					<td>\r\n" + 
                		"						<form action = \"MyCart\">\r\n" + 
                		"							<button name=\"add\" value=\"" + s_id + "\"> Add </button>\r\n" + 
                		"						</form>\r\n" + 
                		"					</td>" +
                		"				</tr>");
                }
            
            
            
            
            rs.close();
            statement.close();
            dbcon.close();
            
            out.println("			</tbody>\r\n" + 
            		"		</table>\r\n" + 
            		"		<br>\r\n" + 
            		"		<a href=\"Home\">Back to Home</a>\r\n" + 
            		"       <div id=\"menu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
            		"		<ul class=\"nav-menu\">\r\n" +
            		"		<li class=\"\">\r\n" + 
            		"		<a = href=\"CheckOut\"><button>Checkout</button></a></li>\r\n" +
            		"		<li class=\"\">\r\n" + 
            		"		<form action = \"MyCart\">\r\n" + 
            		"		<button name=\"mycart\" value=\"show\"> MyCart </button>\r\n"+
            		"		</form></li>"+
            		"		</ul></div>"+
            		"	</body>\r\n" + 
            		"<html>");
            
        } catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception:  " + ex.getMessage());
                ex = ex.getNextException();
            } // end while
            out.println("<HTML>" + "<HEAD><TITLE>" + "Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>Error!</P>" + "<a href=\"CheckOut\">Back to CheckOut</a>\r\n" + "</BODY></HTML>");
            return;
        } // end catch SQLException
        catch (java.lang.Exception ex) {
            out.println("<HTML>" + "<HEAD><TITLE>" + "SingleMovie: Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>SQL error in doGet: " + ex.getMessage() + "</P></BODY></HTML>");
            return;
        }
        out.close();
    }
}
