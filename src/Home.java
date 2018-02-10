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
        import javax.servlet.http.HttpSession;
        
        public class Home extends HttpServlet {
            public String getServletInfo() {
                return "Display the Home page and the functionality��of searching and browsering";
            }
            // Use http GET
            public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                String loginUser = "root";
                String loginPasswd = "1356713njl*@^";
                String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
                
                HttpSession mysession = request.getSession();
                mysession.setAttribute("page_offset", "0");	
                
                response.setContentType("text/html"); // Response mime type

                // Output stream to STDOUT
                PrintWriter out = response.getWriter();

                out.println("<html>\r\n" + 
                		"	<head>\r\n" + 
                		"		<meta charset = \"UTF-8\">\r\n" + 
                		"		<title> Home </title>\r\n" + 
                		"	<style type = \"text/css\"> \r\n" + 
                		"	.menu{position:relative;width:2000px;height:50px;margin:0 auto;padding:8px 0 0;margin-bottom:4px;z-index:99;}\r\n" + 
                		"	.menu .nav-menu>li{float:left;position:relative;margin-right:50}\r\n" + 
                		"	</style>\r\n"+
                		"	</head>\r\n" + 
                		"	<body style=\"background: url(../../project2/img/homebg.jpg) no-repeat 0 0;\r\n" + 
                		"	background-size:100%;\">\r\n" + 
                		"	<h1 style=\"text-align:center; left:0px; top:0px;font-family:'Comic Sans MS', cursive, sans-serif;font-size:250%;\"> <a href = \"/project2/Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>"+
                		"		<form action = \"MovieList\">\r\n" + 
                		"		\r\n" + 
                		"		<div id=\"title\" style=\"\r\n" + 
                		"    height: 30px;\r\n" + 
                		"    margin: auto;\r\n" + 
                		"    top: -450px;\r\n" + 
                		"    width: 220px;\r\n" + 
                		"    padding: 5px 10px 0 10px;\r\n" + 
                		"    \">\r\n" + 
                		"		<label>Title: <input type=\"text\" name=\"title\"></label>\r\n" + 
                		"		<br>\r\n" + 
                		"		</div>\r\n" + 
                		"		\r\n" + 
                		"		<div id=\"year\" style=\"\r\n" + 
                		"    height: 30px;\r\n" + 
                		"    margin: auto;\r\n" + 
                		"    top: -380px;\r\n" + 
                		"    width: 220px;\r\n" + 
                		"    padding: 5px 10px 0 10px;\r\n" + 
                		"    \">\r\n" + 
                		"		<label>Year: <input type=\"number\" name=\"year\"></label>	\r\n" + 
                		"		<br>\r\n" + 
                		"		</div>\r\n" + 
                		"		\r\n" + 
                		"		<div id=\"director\" style=\"\r\n" + 
                		"    height: 30px;\r\n" + 
                		"    margin: auto;\r\n" + 
                		"    top: -310px;\r\n" + 
                		"    width: 270px;\r\n" + 
                		"    padding: 5px 10px 0 10px;\r\n" + 
                		"    \">\r\n" + 
                		"		<label>Director: <input type=\"text\" name=\"director\"></label>\r\n" + 
                		"		<br>\r\n" + 
                		"		</div>\r\n" + 
                		"		\r\n" + 
                		"		<div id=\"star's name\" style=\"\r\n" + 
                		"    height: 30px;\r\n" + 
                		"    margin: auto;\r\n" + 
                		"    top: -240px;\r\n" + 
                		"    width: 310px;\r\n" + 
                		"    padding: 5px 10px 0 10px;\r\n" + 
                		"    \">\r\n" + 
                		"		<label>Star's name: <input type=\"text\" name=\"starname\"></label>\r\n" + 
                		"		\r\n" + 
                		"		<br>\r\n" + 
                		"		</div><br>\r\n"+
                		"<div id=\"submit\" style=\"\r\n" + 
                		"    height: 30px;\r\n" + 
                		"    margin: auto;\r\n" + 
                		"    top: -380px;\r\n" + 
                		"    width: 140px;\r\n" + 
                		"    padding: 5px 10px 0 10px;\r\n" + 
                		"    \">\r\n" + 
                		"		<input type=\"submit\" value=\"Search\">\r\n" + 
                		"		<br>\r\n" + 
                		"		</div>\r\n" +
                		"<input type=\"hidden\" name=\"lim\" value=\"10\"> "+
                		"		</form>\r\n" + 
                		"		<br>"+
                		"	<p style=\"color:blue;text-align:center; left:0px; top:0px;font-family:Tahoma, Geneva, sans-serif;font-size:150%;\">Search by first character of the title:</p>\r\n"+
                		"<Table width=\"100%\" border=\"1\" style=\"background-color: #FFF5EE; opacity: 0.5; filter: alpha(opacity = 30);table-layout:fixed;word-break:break-all\"><tr>\r\n");
                for(int i = 65;i<=90;i++) {
                	char a;
                	char b = (char) i;
                	String c = "" + b;
                	out.println("<td><a href=\"MovieList?title2=" + c + "&lim=10\">"+ c + "</a></td>");
                }		
                for(int i = 0;i<=9;i++) {
                	String a = "" + i;
                	out.println("<td><a href=\"MovieList?title3=" + a + "&lim=10\">"+ a + "</a></td>");	
                }					
                out.println("		</tr></Table><br>\r\n"
                		+ "<p style=\"color:blue;text-align:center; left:0px; top:0px;font-family:Tahoma, Geneva, sans-serif;font-size:150%;\">Search by genre of the title:</p><Table width=\"100%\" border=\"2\" style=\"background-color: #FFF5EE; opacity: 0.5; filter: alpha(opacity = 30);word-wrap:break-word\"><tr>\r\n");
                try {
                    //Class.forName("org.gjt.mm.mysql.Driver");
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
                    // Declare our statement
                    Statement statement = dbcon.createStatement();
                    String query = "select g.name\r\n" + 
                    		"from movies m, genres_in_movies gm, genres g\r\n" + 
                    		"where m.id = gm.movieID and gm.genreID = g.id\r\n" + 
                    		"group by g.name;";
                    // Perform the query
                    ResultSet rs = statement.executeQuery(query);
                    // Iterate through each row of rs
                    int i=0;
                    while (rs.next()) {
                    	if (i==14) {out.println("</tr></Table><Table Table width=\"50%\" border=\"3\" style=\"background-color: #FFF5EE; opacity: 0.5; filter: alpha(opacity = 30);word-wrap:break-word\"><tr>");}
                        String h_genre = rs.getString(1);
                        out.println("<td><a href=\"MovieList?genre=" + h_genre + "&lim=10\">"+ h_genre + "</a></td>");
                        i++;
                    }
                    out.println("</tr></Table>");
                    rs.close();
                    statement.close();
                    dbcon.close();
                    out.println("       <div id=\"menu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
                    		"		<ul class=\"nav-menu\">\r\n" +
                    		"		<li class=\"\">\r\n" + 
                    		"		<a = href=\"CheckOut\"><button>Checkout</button></a></li>\r\n" +
                    		"		<li class=\"\">\r\n" + 
                    		"		<form action = \"MyCart\">\r\n" + 
                    		"		<button name=\"mycart\" value=\"show\"> MyCart </button>\r\n"+
                    		"		</form></li>"+
    
                    		"		</ul></div>");
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
                    out.println("<HTML>" + "<HEAD><TITLE>" + "Home: Error" + "</TITLE></HEAD>\n<BODY>"
                            + "<P>SQL error in doGet: " + ex.getMessage() + "</P></BODY></HTML>");
                    return;
                }
                out.println("	</body>\r\n" + 
                		"</html>");
                out.close();
            }
        }
