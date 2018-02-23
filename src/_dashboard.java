
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class _dashboard extends HttpServlet {
    public String getServletInfo() {
        return "Dashboard for TA";
    }
    // Use http GET
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession mysession = request.getSession();
        User username = (User)mysession.getAttribute("user");	
        if(username.getUsername().equals("classta@email.edu")) {
        	
        	
        
		String name = request.getParameter("name");
		String birthYear = request.getParameter("birthYear");
		
		String title = request.getParameter("title");
		String year = request.getParameter("year");		
		String director = request.getParameter("director");
		String star = request.getParameter("star");
		String genre = request.getParameter("genre");	
		
        String loginUser = "root";
        String loginPasswd = "1356713njl*@^";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html"); // Response mime type
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        
        if(name == null && title == null)
        {
            out.println(
            		"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\r\n" + 
            		"		\r\n" + 
            		"		<title>Dashboard</title>\r\n" + 
            		"	</head>\r\n" + 
            		"	<body style=\"background: url(../../project2/img/homebg.jpg) fixed 0 0;\r\n" + 
            		"	background-size:100%;\">\r\n" + 
            		"	<h1 style=\"text-align:center; left:0px; top:0px;font-family:&#39;Comic Sans MS&#39;, cursive, sans-serif;font-size:250%;\"> <a href=\"Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>	\r\n" + 
            		"		<form action=\"_dashboard\">\r\n" + 
            		"			<center>\r\n" + 
            		"				<a> Add star</a>\r\n" + 
            		"				<br>\r\n" + 
            		"				<input type=\"text\" name=\"name\" placeholder=\"name\" required=\"\" autofocus=\"\">\r\n" + 
            		"				<br>\r\n" + 
            		"				<input type=\"number\" name=\"birthYear\" placeholder=\"birthYear\">\r\n" + 
            		"				<br>\r\n" + 
            		"				<input type=\"submit\" value=\"submit\">\r\n" + 
            		"				<br>\r\n" + 
            		"			</center>\r\n" + 
            		"		</form>\r\n" + 
            		"		<br>\r\n" + 
            		"		<br>\r\n" + 
            		"		<form action=\"_dashboard\">\r\n" + 
            		"			<center>\r\n" + 
            		"				<a> Add Movie</a>\r\n" + 
            		"				<br>\r\n" + 
            		"				<input type=\"text\" name=\"title\" placeholder=\"title\" required=\"\" autofocus=\"\">\r\n" + 
            		"				<br>\r\n" + 
            		"				<input type=\"number\" name=\"year\" placeholder=\"year\" required=\"\" autofocus=\"\">\r\n" + 
            		"				<br>\r\n" + 
            		"				<input type=\"text\" name=\"director\" placeholder=\"director\" required=\"\" autofocus=\"\">\r\n" + 
            		"				<br>\r\n" + 
            		"				<input type=\"text\" name=\"star\" placeholder=\"star\" required=\"\" autofocus=\"\">\r\n" + 
            		"				<br>\r\n" + 
            		"				<input type=\"text\" name=\"genre\" placeholder=\"genre\" required=\"\" autofocus=\"\">\r\n" + 
            		"				<br>\r\n" + 
            		"				<input type=\"submit\" value=\"submit\">\r\n" + 
            		"				<br>\r\n" + 
            		"			</center>\r\n" + 
            		"		</form>	\r\n");
            		
            
            
        	try {
	            //Class.forName("org.gjt.mm.mysql.Driver");
	            Class.forName("com.mysql.jdbc.Driver").newInstance();
	            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	            // Declare our statement
	            Statement statement = dbcon.createStatement();
	            String query = "show tables from moviedb;";
	            
	            ResultSet rs = statement.executeQuery(query);
	            while (rs.next()) {
	                String table = rs.getString(1);
		            out.println("		<br>\r\n" + 
		            		"		<br>\r\n" + 
		            		"		<center>"+
		            		"		<a>" + table + "</a>\r\n" + 
		            		"		</center>"+
		            		"		<table border=\"\" width=\"100%\" style=\"background-color: #FFF5EE; opacity: 0.8; filter: alpha(opacity = 30);word-wrap:break-word\">\r\n" + 
		            		"			<tbody>\r\n" + 
		            		"				<tr>\r\n" + 
		            		"					<td>Fields</td>\r\n" + 
		            		"					<td>Type</td>\r\n" + 
		            		"					<td>Null</td>\r\n" + 
		            		"					<td>Key</td>\r\n" + 
		            		"					<td>Default</td>\r\n" + 
		            		"					<td>Extra</td>\r\n" + 
		            		"				</tr>\r\n");
		            Statement statement2 = dbcon.createStatement();
		            String query2 = "show columns from moviedb." + table + ";";
		            ResultSet rs2 = statement2.executeQuery(query2);
		            while(rs2.next()) {
		            	out.println("				<tr>\r\n");
		            	int i = 1;
		            	for(;i<7;i++)
		            	{
		            		out.println("					<td>" + rs2.getString(i) + "</td>\r\n");
		            	}
	            		out.println("		</tr>\r\n");
		            }
		            		
					out.println("			</tbody>\r\n" + 
		            		"		</table>");
	            }
	            
	            rs.close();
	            statement.close();
	            dbcon.close();
	            
	            
	        } catch (SQLException ex) {
	        	String result = "";
	            while (ex != null) {
	                System.out.println("SQL Exception:  " + ex.getMessage());
	                result += ex.getMessage();
	                ex = ex.getNextException();
	            } // end while
	            out.println("<HTML>" + "<HEAD><TITLE>" + "SQL Error" + "</TITLE></HEAD>\n<BODY>"
	                    + "<P>SQL error!</P>" + "<a href=\"_dashboard\">Back to Dashboard</a>\r\n" + "</BODY></HTML>");
	            return;
	            
	        } // end catch SQLException
	        catch (java.lang.Exception ex) {
	            out.println("<HTML>" + "<HEAD><TITLE>" + "Dashboard: Error" + "</TITLE></HEAD>\n<BODY>"
	                    + "<P>SQL error in doGet: " + ex.getMessage() + "</P></BODY></HTML>");
	            return;
	        }
	        
            out.println("\r\n" + 
            		"</body></html>");
            out.close();
        }
        else if (name != null) {
        	try {
	            //Class.forName("org.gjt.mm.mysql.Driver");
	            Class.forName("com.mysql.jdbc.Driver").newInstance();
	            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	            // Declare our statement
	            Statement statement = dbcon.createStatement();
	            
	            String s_max = "";
	            String query = "select concat('nm', cast(substring_index(max(id), 'm',-1) as unsigned) + 1 ) as s_max\r\n" + 
	            		"from stars;";
	            ResultSet rs = statement.executeQuery(query);
	            while (rs.next()) {
	                s_max = rs.getString(1);
	                break;
	            }
	            
	            String insert_query = "";
	            if(birthYear.equals("")) {
	            	insert_query = "INSERT INTO stars (id, name) VALUES('" + s_max + "','" + name + "');";
	            }
	            else {
	            	insert_query = "INSERT INTO stars (id, name, birthYear) VALUES('" + s_max + "','" + name + "'," + Integer.parseInt(birthYear) + ");";
	            }
	            Statement statement2 = dbcon.createStatement();
	            int a = statement2.executeUpdate(insert_query);
	            String result_words = "This Star '" + name + "' Added!";
	            
    	        out.println(
    	        		"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\r\n" + 
    	        		"		\r\n" + 
    	        		"		<title>Dashboard</title>\r\n" + 
    	        		"	</head>\r\n" + 
    	        		"	<body style=\"background: url(../../project2/img/homebg.jpg) fixed 0 0;\r\n" + 
    	        		"	background-size:100%;\">\r\n" + 
    	        		"	<h1 style=\"text-align:center; left:0px; top:0px;font-family:&#39;Comic Sans MS&#39;, cursive, sans-serif;font-size:250%;\"> <a href=\"Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>	\r\n" + 
    	        		"		<form action=\"_dashboard\">\r\n" + 
    	        		"			<center>\r\n" + 
    	        		"				<a> Add star</a>\r\n" + 
    	        		"				<br>\r\n" + 
    	        		"				<input type=\"text\" name=\"name\" placeholder=\"name\" required=\"\" autofocus=\"\">\r\n" + 
    	        		"				<br>\r\n" + 
    	        		"				<input type=\"number\" name=\"birthYear\" placeholder=\"birthYear\">\r\n" + 
    	        		"				<br>\r\n" + 
    	        		"				<input type=\"submit\" value=\"submit\">\r\n" + 
    	        		"				<br>\r\n" + 
    	        		"				<br>\r\n" + 
    	        		"				<a>" + result_words + "</a>\r\n" + 
    	        		"			</center>\r\n" + 
    	        		"		</form>\r\n" + 
    	        		"		<br>\r\n" + 
    	        		"		<br>\r\n" + 
    	        		"		<form action=\"_dashboard\">\r\n" + 
    	        		"			<center>\r\n" + 
    	        		"				<a> Add Movie</a>\r\n" + 
    	        		"				<br>\r\n" + 
    	        		"				<input type=\"text\" name=\"title\" placeholder=\"title\" required=\"\" autofocus=\"\">\r\n" + 
    	        		"				<br>\r\n" + 
    	        		"				<input type=\"number\" name=\"year\" placeholder=\"year\" required=\"\" autofocus=\"\">\r\n" + 
    	        		"				<br>\r\n" + 
    	        		"				<input type=\"text\" name=\"director\" placeholder=\"director\" required=\"\" autofocus=\"\">\r\n" + 
    	        		"				<br>\r\n" + 
    	        		"				<input type=\"text\" name=\"star\" placeholder=\"star\" required=\"\" autofocus=\"\">\r\n" + 
    	        		"				<br>\r\n" + 
    	        		"				<input type=\"text\" name=\"genre\" placeholder=\"genre\" required=\"\" autofocus=\"\">\r\n" + 
    	        		"				<br>\r\n" + 
    	        		"				<input type=\"submit\" value=\"submit\">\r\n" + 
    	        		"				<br>\r\n" + 
    	        		"			</center>\r\n" + 
    	        		"		</form>	\r\n");
    	        
    	        
    	        
    	        
                Statement statement3 = dbcon.createStatement();
                String query3 = "show tables from moviedb;";
                
                ResultSet rs3 = statement3.executeQuery(query3);
                while (rs3.next()) {
                    String table = rs3.getString(1);
                        out.println("		<br>\r\n" + 
                                    "		<br>\r\n" + 
                                    "		<center>"+
                                    "		<a>" + table + "</a>\r\n" + 
                                    "		</center>"+
                                    "		<table border=\"\" width=\"100%\" style=\"background-color: #FFF5EE; opacity: 0.8; filter: alpha(opacity = 30);word-wrap:break-word\">\r\n" + 
                                    "			<tbody>\r\n" + 
                                    "				<tr>\r\n" + 
                                    "					<td>Fields</td>\r\n" + 
                                    "					<td>Type</td>\r\n" + 
                                    "					<td>Null</td>\r\n" + 
                                    "					<td>Key</td>\r\n" + 
                                    "					<td>Default</td>\r\n" + 
                                    "					<td>Extra</td>\r\n" + 
                                    "				</tr>\r\n");
                        Statement statement4 = dbcon.createStatement();
                        String query4 = "show columns from moviedb." + table + ";";
                        ResultSet rs4 = statement4.executeQuery(query4);
                        while(rs4.next()) {
                            out.println("				<tr>\r\n");
                            int i = 1;
                            for(;i<7;i++)
                            {
                                    out.println("					<td>" + rs4.getString(i) + "</td>\r\n");
                            }
                            out.println("		</tr>\r\n");
                        }
                        out.println("			</tbody>\r\n" + 
                                    "		</table>");
                }
                
                statement3.close();

    	        out.println("\r\n" + 
    	        		"</body></html>");
	            
	            rs.close();
	            statement.close();
	            statement2.close();
	            dbcon.close();
	            
	            
	        } catch (SQLException ex) {
	        	String result = "";
	            while (ex != null) {
	                System.out.println("SQL Exception:  " + ex.getMessage());
	                result += ex.getMessage();
	                ex = ex.getNextException();
	            } // end while
	            out.println("<HTML>" + "<HEAD><TITLE>" + "SQL Error" + "</TITLE></HEAD>\n<BODY>"
	                    + "<P>SQL error!</P>" + "<a href=\"_dashboard\">Back to Dashboard</a>\r\n" + "</BODY></HTML>");
	            return;
	            
	        } // end catch SQLException
	        catch (java.lang.Exception ex) {
	            out.println("<HTML>" + "<HEAD><TITLE>" + "Dashboard: Error" + "</TITLE></HEAD>\n<BODY>"
	                    + "<P>SQL error in doGet: " + ex.getMessage() + "</P></BODY></HTML>");
	            return;
	        }
	        out.close();
        	
        	
        }
        else if (title != null){
	        try {
	        	int valid = -1;
	        	int staradd = -1;
	        	int genreadd = -1;
	        	String result_words = "";
	            //Class.forName("org.gjt.mm.mysql.Driver");
	            Class.forName("com.mysql.jdbc.Driver").newInstance();
	            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	            // Declare our statement
	            CallableStatement statement = dbcon.prepareCall("{call add_movie(?,?,?,?,?,?,?,?)}");
	            
	            statement.setString(1, title);
	            statement.setInt(2, Integer.parseInt(year.trim()));
	            statement.setString(3, director);
	            statement.setString(4, star);
	            statement.setString(5, genre);
	            
	            statement.registerOutParameter(6, Types.INTEGER);
	            statement.registerOutParameter(7, Types.INTEGER);
	            statement.registerOutParameter(8, Types.INTEGER);	            
	            
	            statement.execute();
	            valid = statement.getInt(6);
	            staradd = statement.getInt(7);
	            genreadd = statement.getInt(8);
	            	
	            if(valid == 0) {
	            	result_words = "This Movie '" + title + "' Already Exists!";
	            }
	            else {
	            	result_words = "New Movie '" + title + "' Added! ";
	            	if(staradd == 1)
	            	{
	            		result_words += "New Star '" + star + "' Added! ";
	            	}
	            	if(genreadd == 1)
	            	{
	            		result_words += "New Genre '" + genre + "' Added! ";
	            	}
	            }
	            
	            out.println(
	            		"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\r\n" + 
	            		"		\r\n" + 
	            		"		<title>Dashboard</title>\r\n" + 
	            		"	</head>\r\n" + 
	            		"	<body style=\"background: url(../../project2/img/homebg.jpg) fixed 0 0;\r\n" + 
	            		"	background-size:100%;\">\r\n" + 
	            		"	<h1 style=\"text-align:center; left:0px; top:0px;font-family:&#39;Comic Sans MS&#39;, cursive, sans-serif;font-size:250%;\"> <a href=\"Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>	\r\n" + 
	            		"		<form action=\"_dashboard\">\r\n" + 
	            		"			<center>\r\n" + 
	            		"				<a> Add star</a>\r\n" + 
	            		"				<br>\r\n" + 
	            		"				<input type=\"text\" name=\"name\" placeholder=\"name\" required=\"\" autofocus=\"\">\r\n" + 
	            		"				<br>\r\n" + 
	            		"				<input type=\"number\" name=\"birthYear\" placeholder=\"birthYear\">\r\n" + 
	            		"				<br>\r\n" + 
	            		"				<input type=\"submit\" value=\"submit\">\r\n" + 
	            		"				<br>\r\n" + 
	            		"			</center>\r\n" + 
	            		"		</form>\r\n" + 
	            		"		<br>\r\n" + 
	            		"		<br>\r\n" + 
	            		"		<form action=\"_dashboard\">\r\n" + 
	            		"			<center>\r\n" + 
	            		"				<a> Add Movie</a>\r\n" + 
	            		"				<br>\r\n" + 
	            		"				<input type=\"text\" name=\"title\" placeholder=\"title\" required=\"\" autofocus=\"\">\r\n" + 
	            		"				<br>\r\n" + 
	            		"				<input type=\"number\" name=\"year\" placeholder=\"year\" required=\"\" autofocus=\"\">\r\n" + 
	            		"				<br>\r\n" + 
	            		"				<input type=\"text\" name=\"director\" placeholder=\"director\" required=\"\" autofocus=\"\">\r\n" + 
	            		"				<br>\r\n" + 
	            		"				<input type=\"text\" name=\"star\" placeholder=\"star\" required=\"\" autofocus=\"\">\r\n" + 
	            		"				<br>\r\n" + 
	            		"				<input type=\"text\" name=\"genre\" placeholder=\"genre\" required=\"\" autofocus=\"\">\r\n" + 
	            		"				<br>\r\n" + 
	            		"				<input type=\"submit\" value=\"submit\">\r\n" + 
	            		"				<br>\r\n" + 
	            		"				<br>\r\n" + 
	            		"				<a>" + result_words + "</a>\r\n" + 
	            		"			</center>\r\n" + 
	            		"		</form>	\r\n");
	            
                Statement statement3 = dbcon.createStatement();
                String query3 = "show tables from moviedb;";
                
                ResultSet rs3 = statement3.executeQuery(query3);
                while (rs3.next()) {
                    String table = rs3.getString(1);
                        out.println("		<br>\r\n" + 
                                    "		<br>\r\n" + 
                                    "		<center>"+
                                    "		<a>" + table + "</a>\r\n" + 
                                    "		</center>"+
                                    "		<table border=\"\" width=\"100%\" style=\"background-color: #FFF5EE; opacity: 0.8; filter: alpha(opacity = 30);word-wrap:break-word\">\r\n" + 
                                    "			<tbody>\r\n" + 
                                    "				<tr>\r\n" + 
                                    "					<td>Fields</td>\r\n" + 
                                    "					<td>Type</td>\r\n" + 
                                    "					<td>Null</td>\r\n" + 
                                    "					<td>Key</td>\r\n" + 
                                    "					<td>Default</td>\r\n" + 
                                    "					<td>Extra</td>\r\n" + 
                                    "				</tr>\r\n");
                        Statement statement4 = dbcon.createStatement();
                        String query4 = "show columns from moviedb." + table + ";";
                        ResultSet rs4 = statement4.executeQuery(query4);
                        while(rs4.next()) {
                            out.println("				<tr>\r\n");
                            int i = 1;
                            for(;i<7;i++)
                            {
                                    out.println("					<td>" + rs4.getString(i) + "</td>\r\n");
                            }
                            out.println("		</tr>\r\n");
                        }
                        out.println("			</tbody>\r\n" + 
                                    "		</table>");
                }
                statement3.close();
	            
                
                
	            out.println("\r\n" + 
	            		"</body></html>");
	            
	            
	            statement.close();
	            dbcon.close();
	            
	            
	        } catch (SQLException ex) {
	        	String result = "";
	            while (ex != null) {
	                System.out.println("SQL Exception:  " + ex.getMessage());
	                result += ex.getMessage();
	                ex = ex.getNextException();
	            } // end while
	            out.println("<HTML>" + "<HEAD><TITLE>" + "SQL Error" + "</TITLE></HEAD>\n<BODY>"
	                    + "<P>SQL error!</P>" + "<a href=\"_dashboard\">Back to Dashboard</a>\r\n" + "</BODY></HTML>");
	            return;
	            
	        } // end catch SQLException
	        catch (java.lang.Exception ex) {
	            out.println("<HTML>" + "<HEAD><TITLE>" + "Dashboard: Error" + "</TITLE></HEAD>\n<BODY>"
	                    + "<P>SQL error in doGet: " + ex.getMessage() + "</P></BODY></HTML>");
	            return;
	        }
	        out.close();
        }
        
        }
        else {
            response.setContentType("text/html"); // Response mime type
            // Output stream to STDOUT
            PrintWriter out = response.getWriter();
            out.println("<html>\r\n" + 
            		"<head></head>\r\n" + 
            		"<body>\r\n" + 
            		"<title>Not Found</title>\r\n" + 
            		"<a>Not Found</a>\r\n" + 
            		"</body>\r\n" + 
            		"<html>"
            		);
        }
    }
    
    
    
}
