import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

public class SingleStar extends HttpServlet {
    public String getServletInfo() {
        return "Display all information of single star";
    }
    // Use http GET
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	/*
        String loginUser = "root";
        String loginPasswd = "1356713njl*@^";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		*/
    	
        String starname = request.getParameter("starname");
        response.setContentType("text/html"); // Response mime type
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        
        if(starname == null)
        {
            out.println("<HTML>" + "<HEAD><TITLE>" + "Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>Did not get star name! </P></BODY></HTML>");
            return;
        }

        try {
            
            // the following few lines are for connection pooling
            // Obtain our environment naming context

            Context initCtx = new InitialContext();
            if (initCtx == null)
                out.println("initCtx is NULL");

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            if (envCtx == null)
                out.println("envCtx is NULL");

            // Look up our data source
            DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");

            // the following commented lines are direct connections without pooling
            //Class.forName("org.gjt.mm.mysql.Driver");
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

            if (ds == null)
                out.println("ds is null.");

            Connection dbcon = ds.getConnection();
            if (dbcon == null)
                out.println("dbcon is null.");

            // Declare our statement
            
            PreparedStatement statement = null;
            
            String query = "select s.id, s.name, s.birthYear, group_concat(distinct m.title,'&', m.id)\r\n" + 
            		"from stars s, stars_in_movies sm, movies m\r\n" + 
            		"where s.name = '" + starname + "' and s.id = sm.starId and sm.movieId = m.id\r\n" + 
            		"group by s.id\r\n" + 
            		";";

            statement = dbcon.prepareStatement(query);
            
            // Perform the query
            ResultSet rs = statement.executeQuery();
            
            
            
            // Iterate through each row of rs
            out.println("<html>\r\n" + 
            		"	<head>\r\n" + 
            		"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\r\n" + 
            		"		<title>Single Star</title>\r\n" + 
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
            		"					<td>birthyear</td>\r\n" + 
            		"					<td>movies</td>\r\n" + 
            		"				</tr>");
            
            
            while (rs.next()) {
                String s_id = rs.getString(1);
                String s_name = rs.getString(2);
                String s_birthYear = rs.getString(3);
                String s_movies = rs.getString(4);
                
                String [] s_movies_ls = s_movies.split(",");
                
                out.println("				<tr>\r\n" + 
                		"					<td>" + s_id + "</td>\r\n" + 
                		"					<td>" + "<a href=\"SingleStar?starname=" + s_name + "\">" + s_name + "</a>"+ "</td>\r\n" + 
                		"					<td>" + s_birthYear + "</td>\r\n"); 
                out.println("					<td>");
                
                
                for(int i =0;i<s_movies_ls.length;i++)
                {
                	out.println("<a href=\"SingleMovie?movieid=" + s_movies_ls[i].split("&")[1] + "\">" + s_movies_ls[i].split("&")[0] + "</a>\r\n");
                }
                
                out.println("</td>\r\n");
                out.println("				</tr>");
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
                    + "<P>Error!</P>" + "<a href=\"Home\">Back to Home</a>\r\n" + "</BODY></HTML>");
            return;
        } // end catch SQLException
        catch (java.lang.Exception ex) {
            out.println("<HTML>" + "<HEAD><TITLE>" + "SingleStar: Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>SQL error in doGet: " + ex.getMessage() + "</P></BODY></HTML>");
            return;
        }
        out.close();
    }
}
