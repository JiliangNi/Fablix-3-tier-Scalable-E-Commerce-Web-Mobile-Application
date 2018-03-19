
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

public class CheckOut extends HttpServlet {
    public String getServletInfo() {
        return "Display CheckOut Window";
    }
    // Use http GET
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String id = request.getParameter("id");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String expiration = request.getParameter("expiration");
		
		/*
        String loginUser = "root";
        String loginPasswd = "1356713njl*@^";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		 */
        
        HttpSession mysession = request.getSession();
        Dictionary<String, String> cart_ls = (Hashtable<String,String>)mysession.getAttribute("cart_ls");

        response.setContentType("text/html"); // Response mime type
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        
        if(id == null)
        {
            out.println("<html>\r\n" + 
            		"	<head>\r\n" + 
            		"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\r\n" + 
            		"		<title>CheckOut</title>\r\n" + 
            		"	</head>\r\n" +
			"	<body style=\"background: url(../../project2/img/homebg.jpg) no-repeat 0 0;\r\n" + 
            		"	background-size:100%;\">\r\n" + 
            		"	<h1 style=\"text-align:center; left:0px; top:0px;font-family:'Comic Sans MS', cursive, sans-serif;font-size:250%;\"> <a href = \"/project2/Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>"+ 
            		"	<body>\r\n" + 
            		"		<form action=\"CheckOut\" method=\"post\">\r\n" + 
            		"			<center>\r\n" + 
            		"				<input type =\"number\" name =\"id\" placeholder=\"card number\" required autofocus>\r\n" + 
            		"				<input type =\"text\" name =\"firstname\" placeholder=\"firstname\" required autofocus>\r\n" + 
            		"				<input type =\"text\" name =\"lastname\" placeholder=\"lastname\" required autofocus>\r\n" + 
            		"				<input type =\"text\" name =\"expiration\" placeholder=\"expiration\" required autofocus>\r\n" + 
            		"				<input type =\"submit\" value=\"submit\">\r\n" + 
            		"			</center>\r\n" + 
            		"		</form>\r\n" + 
            		"	</body>\r\n" + 
            		"</html>");
        }
        else {
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
	            DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB2");

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
	            
	            String query = "select c.id as id, c.firstName as firstName, c.lastName as lastName, c.expiration as expiration, cu.id as customerid\r\n" + 
	            		"from creditcards c, customers cu\r\n" + 
	            		"where c.id = " + id + " and c.id = cu.ccID;\r\n" + 
	            		"";

	            statement = dbcon.prepareStatement(query);
	            
	            // Perform the query
	            ResultSet rs = statement.executeQuery();
	            
	            
	            
	            // Iterate through each row of rs
	            boolean matched = false;
	            String s_customerid = "";
	            
	            while (rs.next()) {
	                String s_id = rs.getString(1);
	                String s_firstname = rs.getString(2);
	                String s_lastname = rs.getString(3);
	                String s_expiration = rs.getString(4);
	                s_customerid = rs.getString(5);
	                
	                if(s_id.equals(id) && s_firstname.equals(firstname) && s_lastname.equals(lastname) && s_expiration.equals(expiration)) {
	                	matched = true;
	                	break;
	                }
	            }
	            if(!matched)
	            {
	                out.println("<html>\r\n" + 
	                		"	<head>\r\n" + 
	                		"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\r\n" + 
	                		"		<title>CheckOut</title>\r\n" + 
	                		"	</head>\r\n" + 
	                		"	<body style=\"background: url(../../project2/img/homebg.jpg) no-repeat 0 0;\r\n" + 
	                		"	background-size:100%;\">\r\n" + 
	                		"	<h1 style=\"text-align:center; left:0px; top:0px;font-family:'Comic Sans MS', cursive, sans-serif;font-size:250%;\"> <a href = \"/project2/Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>"+ 
	                		
	                		
	                		"	<body>\r\n" + 
	                		"		<form action=\"CheckOut\" method=\"post\">\r\n" + 
	                		"			<center>\r\n" + 
	                		"				<input type =\"text\" name =\"id\" placeholder=\"card number\" required autofocus>\r\n" + 
	                		"				<input type =\"text\" name =\"firstname\" placeholder=\"firstname\" required autofocus>\r\n" + 
	                		"				<input type =\"text\" name =\"lastname\" placeholder=\"lastname\" required autofocus>\r\n" + 
	                		"				<input type =\"text\" name =\"expiration\" placeholder=\"expiration\" required autofocus>\r\n" + 
	                		"				<input type =\"submit\" value=\"submit\">\r\n" + 
	                		"			</center>\r\n" + 
	                		"		</form>\r\n" + 
	                		"		<p style=\"color:red;text-align:center\">Invalid Information!</p>\r\n" + 
	                		"	</body>\r\n" + 
	                		"</html>");
	            }
	            else {
	            	if(cart_ls != null && cart_ls.size()!=0)
	            	{
	    				for(Enumeration<String> e = cart_ls.keys();e.hasMoreElements();) {
	    					String key = (String)e.nextElement();
	    					int value = Integer.parseInt(cart_ls.get(key));
	    					for(int i = 0;i<value;i++)
	    					{
	    						String insert_query = "INSERT INTO sales(customerId,movieId,saleDate) VALUES(" + s_customerid + ",'" + key + "',(select curdate()));";
	    			            // Perform the query
	    						System.out.println(insert_query);
	    			            Statement statement2 = dbcon.createStatement();
	    						int a = statement2.executeUpdate(insert_query); 
	    					}
	    				}
	            	}

	            	
    	        	out.println("<html>\r\n" + 
    	        			"	<head>\r\n" + 
    	        			"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\r\n" + 
    	        			"		<title>Successful Payment</title>\r\n" + 
    	        			"	<body style=\"background: url(../../project2/img/homebg.jpg) no-repeat 0 0;\r\n" + 
    	            		"	background-size:100%;\">\r\n" + 
    	            		"	<h1 style=\"text-align:center; left:0px; top:0px;font-family:'Comic Sans MS', cursive, sans-serif;font-size:250%;\"> <a href = \"/project2/Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>"+
    	        			"	</head>\r\n" + 
    	        			"	<body>\r\n" + 
    	        			"		<p style=\"color:red;text-align:center\">Successful Payment!</p>\r\n" + 
    	        			"		<a href=\"Home\">Back to Home</a>\r\n" + 
    	        			"	</body>\r\n" + 
    	        			"</html>");
    	        	
					cart_ls = new Hashtable<String, String>();
	    			mysession.setAttribute("cart_ls", cart_ls);	
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
	                    + "<P>SQL error!</P>" + "<a href=\"Home\">Back to Home</a>\r\n" + "</BODY></HTML>");
	            return;
	            
	        } // end catch SQLException
	        catch (java.lang.Exception ex) {
	            out.println("<HTML>" + "<HEAD><TITLE>" + "CheckOut: Error" + "</TITLE></HEAD>\n<BODY>"
	                    + "<P>SQL error in doGet: " + ex.getMessage() + "</P></BODY></HTML>");
	            return;
	        }
	        out.close();
        }
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

    
    
    
}
