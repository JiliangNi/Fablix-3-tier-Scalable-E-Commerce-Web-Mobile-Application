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

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*; 
import java.util.Dictionary;

public class MyCart extends HttpServlet {
    public String getServletInfo() {
        return "Display My Cart";
    }
    // Use http GET
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String mycart = request.getParameter("mycart");
		String add = request.getParameter("add");
		String reduce = request.getParameter("reduce");
		String removeall= request.getParameter("removeall");
		
		
        String loginUser = "root";
        String loginPasswd = "1356713njl*@^";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        response.setContentType("text/html"); // Response mime type
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        
        HttpSession mysession = request.getSession();
        Dictionary<String, String> cart_ls = (Hashtable<String,String>)mysession.getAttribute("cart_ls");


		
		if(mycart == null && add == null && reduce == null && removeall == null)
		{
            out.println("<HTML>" + "<HEAD><TITLE>" + "Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>Did not get my cart type or command of reducing and adding! </P></BODY></HTML>");
            return;
		}
        
        try {
            //Class.forName("org.gjt.mm.mysql.Driver");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // Declare our statement
            Statement statement = dbcon.createStatement();
            
            
            
            
            String query = "";
            
            // Perform the query
            ResultSet rs;
            // Iterate through each row of rs
            
    		if(mycart!=null && mycart.equals("show")){
    			out.println("<html>\r\n" + 
    					"	<head>\r\n" + 
    					"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\r\n" + 
    					"		<title>My Cart</title>\r\n" + 
    					"	</head>\r\n" + 
    					"	<body style=\"background: url(../../project2/img/homebg.jpg) no-repeat 0 0;\r\n" + 
    	        		"	background-size:100%;\">\r\n" + 
    	        		"	<h1 style=\"text-align:center; left:0px; top:0px;font-family:'Comic Sans MS', cursive, sans-serif;font-size:250%;\"> <a href = \"/project2/Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>"+
    					"	<body>");

    			
    			if(cart_ls != null && cart_ls.size()!=0)
    			{
    				for(Enumeration<String> e = cart_ls.keys();e.hasMoreElements();) {
    					String key = (String)e.nextElement();
    	    			out.println("		<a href=\"SingleMovie?movieid=" + key + "\"> " + key + "</a>\r\n" + 
    	    					"		<form action=\"MyCart\">\r\n" + 
    	    					"			<button name=\"reduce\" value=" + key + ">-</button>\r\n" + 
    	    					"			<a>" + cart_ls.get(key) + "</a>\r\n" + 
    	    					"			<button name=\"add\" value=" + key + ">+</button>\r\n" + 
    	    					"		</form>");
    				}
    			}
    			
    			
    			
                
    	        out.println("		<form action=\"MyCart\">\r\n" + 
    	        		"			<button name= \"removeall\" value =\"true\">Remove All</button>\r\n" + 
    	        		"		</form>\r\n" + 
    	        		"		<br>\r\n" + 
    	        		"		<a = href=\"Home\">Back to Home</a>\r\n" + 
    	        		"		<br>\r\n" + 
    	        		"		<a = href=\"CheckOut\"><button>Checkout</button></a>\r\n" +
    	        		"	</body>\r\n" + 
    	        		"</html>");
//    	        rs.close();
    		}
    		else if(add !=null && !add.equals(""))
    		{
    			out.println("<html>\r\n" + 
    					"	<head>\r\n" + 
    					"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\r\n" + 
    					"		<title>My Cart</title>\r\n" + 
    					"	</head>\r\n" + 
    					"	<body style=\"background: url(../../project2/img/homebg.jpg) no-repeat 0 0;\r\n" + 
    	        		"	background-size:100%;\">\r\n" + 
    	        		"	<h1 style=\"text-align:center; left:0px; top:0px;font-family:'Comic Sans MS', cursive, sans-serif;font-size:250%;\"> <a href = \"/project2/Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>"+
    					"	<body>");
    			if(cart_ls == null || cart_ls.size() == 0)
    			{
    				cart_ls = new Hashtable<String, String>();
    				cart_ls.put(add, "1");
    				mysession.setAttribute("cart_ls", cart_ls);
    			}
    			else {
    				if(cart_ls.get(add) == null)
    				{
        				cart_ls.put(add, "1");
        				mysession.setAttribute("cart_ls", cart_ls);
    				}
    				else {
        				cart_ls.put(add, Integer.parseInt(cart_ls.get(add))+1+"");
        				mysession.setAttribute("cart_ls", cart_ls);
    				}
    			}
    			

    			
    			if(cart_ls != null && cart_ls.size()!=0)
    			{
    				for(Enumeration<String> e = cart_ls.keys();e.hasMoreElements();) {
    					String key = (String)e.nextElement();
    	    			out.println("		<a href=\"SingleMovie?movieid=" + key + "\"> " + key + "</a>\r\n" + 
    	    					"		<form action=\"MyCart\">\r\n" + 
    	    					"			<button name=\"reduce\" value=" + key + ">-</button>\r\n" + 
    	    					"			<a>" + cart_ls.get(key) + "</a>\r\n" + 
    	    					"			<button name=\"add\" value=" + key + ">+</button>\r\n" + 
    	    					"		</form>");
    				}
    			}
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    	        out.println("		<form action=\"MyCart\">\r\n" + 
    	        		"			<button name= \"removeall\" value =\"true\">Remove All</button>\r\n" + 
    	        		"		</form>\r\n" + 
    	        		"		<br>\r\n" + 
    	        		"		<a = href=\"Home\">Back to Home</a>\r\n" + 
    	        		"		<br>\r\n" + 
    	        		"		<a = href=\"CheckOut\"><button>Checkout</button></a>\r\n" + 
    	        		"	</body>\r\n" + 
    	        		"</html>");
    			
    	        
    	        
    	        
    	        
//    	        rs.close();
    			
    		}
    		else if(reduce != null && !reduce.equals(""))
    		{
    			out.println("<html>\r\n" + 
    					"	<head>\r\n" + 
    					"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\r\n" + 
    					"		<title>My Cart</title>\r\n" + 
    					"	</head>\r\n" +
    					"	<body style=\"background: url(../../project2/img/homebg.jpg) no-repeat 0 0;\r\n" + 
    	        		"	background-size:100%;\">\r\n" + 
    	        		"	<h1 style=\"text-align:center; left:0px; top:0px;font-family:'Comic Sans MS', cursive, sans-serif;font-size:250%;\"> <a href = \"/project2/Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>"+
    					"	<body>");
    			
    			
    			
    			if(cart_ls.get(reduce).equals("1"))
    			{
    				cart_ls.remove(reduce);
    				mysession.setAttribute("cart_ls", cart_ls);
    			}
    			else {
    				cart_ls.put(reduce, Integer.parseInt(cart_ls.get(reduce))-1+"");
    				mysession.setAttribute("cart_ls", cart_ls);
    			}
    			
    			
    			
    			if(cart_ls != null && cart_ls.size()!=0)
    			{
    				for(Enumeration<String> e = cart_ls.keys();e.hasMoreElements();) {
    					String key = (String)e.nextElement();
    	    			out.println("		<a href=\"SingleMovie?movieid=" + key + "\"> " + key + "</a>\r\n" + 
    	    					"		<form action=\"MyCart\">\r\n" + 
    	    					"			<button name=\"reduce\" value=" + key + ">-</button>\r\n" + 
    	    					"			<a>" + cart_ls.get(key) + "</a>\r\n" + 
    	    					"			<button name=\"add\" value=" + key + ">+</button>\r\n" + 
    	    					"		</form>");
    				}
    			}
    			
    			
    			
    			
    			
    	        out.println("		<form action=\"MyCart\">\r\n" + 
    	        		"			<button name= \"removeall\" value =\"true\">Remove All</button>\r\n" + 
    	        		"		</form>\r\n" + 
    	        		"		<br>\r\n" + 
    	        		"		<a = href=\"Home\">Back to Home</a>\r\n" + 
    	        		"		<br>\r\n" + 
    	        		"		<a = href=\"CheckOut\"><button>Checkout</button></a>\r\n" +
    	        		"	</body>\r\n" + 
    	        		"</html>");
    		}
    		else if(removeall !=null && !removeall.equals(""))
    		{
    			out.println("<html>\r\n" + 
    					"	<head>\r\n" + 
    					"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\r\n" + 
    					"		<title>My Cart</title>\r\n" + 
    					"	</head>\r\n" + 
    					"	<body style=\"background: url(../../project2/img/homebg.jpg) no-repeat 0 0;\r\n" + 
    	        		"	background-size:100%;\">\r\n" + 
    	        		"	<h1 style=\"text-align:center; left:0px; top:0px;font-family:'Comic Sans MS', cursive, sans-serif;font-size:250%;\"> <a href = \"/project2/Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>"+
    					
    					"	<body>");
    			
    			
				cart_ls = new Hashtable<String, String>();
    			mysession.setAttribute("cart_ls", cart_ls);
    			
    			
    			if(cart_ls != null && cart_ls.size()!=0)
    			{
    				for(Enumeration<String> e = cart_ls.keys();e.hasMoreElements();) {
    					String key = (String)e.nextElement();
    	    			out.println("		<a href=\"SingleMovie?movieid=" + key + "\"> " + key + "</a>\r\n" + 
    	    					"		<form action=\"MyCart\">\r\n" + 
    	    					"			<button name=\"reduce\" value=" + key + ">-</button>\r\n" + 
    	    					"			<a>" + cart_ls.get(key) + "</a>\r\n" + 
    	    					"			<button name=\"add\" value=" + key + ">+</button>\r\n" + 
    	    					"		</form>");
    				}
    			}
    			
    	        out.println("		<form action=\"MyCart\">\r\n" + 
    	        		"			<button name= \"removeall\" value =\"true\">Remove All</button>\r\n" + 
    	        		"		</form>\r\n" + 
    	        		"		<br>\r\n" + 
    	        		"		<a = href=\"Home\">Back to Home</a>\r\n" + 
    	        		"		<br>\r\n" + 
    	        		"		<a = href=\"CheckOut\"><button>Checkout</button></a>\r\n" +
    	        		"	</body>\r\n" + 
    	        		"</html>");
    		}
            
            statement.close();
            dbcon.close();
        } catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception:  " + ex.getMessage());
                ex = ex.getNextException();
            } // end while
        } // end catch SQLException
        catch (java.lang.Exception ex) {
            out.println("<HTML>" + "<HEAD><TITLE>" + "Search: Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>SQL error in doGet: " + ex.getMessage() + "</P></BODY></HTML>");
            return;
        }
        out.close();
    }
}
