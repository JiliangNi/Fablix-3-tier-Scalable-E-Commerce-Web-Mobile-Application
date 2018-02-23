

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.google.gson.JsonObject;



import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;



/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// this example only allows username/password to be test/test
		// in the real project, you should talk to the database to verify username/password

        HttpSession mysession = request.getSession();
        String recaptcha = (String)mysession.getAttribute("recaptcha");		
		
		
		PrintWriter out = response.getWriter();
		
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
		// Verify CAPTCHA.
		boolean valid = VerifyUtils.verify(gRecaptchaResponse);
		
		if (!valid) {
		    //errorString = "Captcha invalid!";
			System.out.println("Recaptcha Wrong!");
			if(recaptcha == null) {
				recaptcha = "no";
				mysession.setAttribute("recaptcha", "no");				
			}
			else if(recaptcha.equals("yes")) {
			}
			else if(recaptcha.equals("no")) {
			}
		}
		else {
			recaptcha = "yes";
			mysession.setAttribute("recaptcha", "yes");
		}
		
		
		System.out.println(recaptcha);
		System.out.println(mysession.getAttribute("recaptcha"));
		
		
        String loginUser = "root";
        String loginPasswd = "1356713njl*@^";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html"); // Response mime type

        
        boolean matched = false;
		boolean ta_matched = false;
		
        try {
            //Class.forName("org.gjt.mm.mysql.Driver");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // Declare our statement
            Statement statement = dbcon.createStatement();
            String query = "select password from customers where email = '" + username + "';";
            
            // Perform the query
            ResultSet rs = statement.executeQuery(query);
            // Iterate through each row of rs
            while (rs.next()) {
                String c_password = rs.getString(1);
                if(c_password.equals(password)){
                	matched = true;
                	break;
                }
            }
            
            query = "select password from employees where email = '" + username + "';";
            rs = statement.executeQuery(query);
            while(rs.next()) {
            	String ta_password = rs.getString(1);
            	if(ta_password.equals(password)){
            		ta_matched = true;
            		break;
            	}
            }
            
            rs.close();
            statement.close();
            dbcon.close();
            
        } catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception:  " + ex.getMessage());
                ex = ex.getNextException();
            } // end while
        } // end catch SQLException
        catch (java.lang.Exception ex) {
        	System.out.println("MovieDB: Error" + ex.getMessage());
            return;
        }
        System.out.println(matched);
        System.out.println(ta_matched);
        
        
        
        if(recaptcha.equals("yes"))
        {
        	if(ta_matched)
        	{
				// login success:
				
				// set this user into the session
				request.getSession().setAttribute("user", new User(username));
				JsonObject responseJsonObject = new JsonObject();
				responseJsonObject.addProperty("status", "success");
				responseJsonObject.addProperty("message", "success");
				responseJsonObject.addProperty("ta_matched", "yes");
				response.getWriter().write(responseJsonObject.toString());
        	}
        	else {
				if (matched) {
						// login success:
						// set this user into the session
						request.getSession().setAttribute("user", new User(username));
						JsonObject responseJsonObject = new JsonObject();
						responseJsonObject.addProperty("status", "success");
						responseJsonObject.addProperty("message", "success");
						responseJsonObject.addProperty("ta_matched", "no");						
						response.getWriter().write(responseJsonObject.toString());
						
				} else {
					// login fail
					request.getSession().setAttribute("user", new User(username));
					JsonObject responseJsonObject = new JsonObject();
					responseJsonObject.addProperty("status", "fail");
					responseJsonObject.addProperty("message", "User or Password are Incorrect");
					responseJsonObject.addProperty("ta_matched", "no");
					response.getWriter().write(responseJsonObject.toString());
				}
        	}
        }
        else {
			request.getSession().setAttribute("user", new User(username));
			
			JsonObject responseJsonObject = new JsonObject();
			responseJsonObject.addProperty("status", "fail");
			responseJsonObject.addProperty("message", "Recaptcha Wrong!");
			response.getWriter().write(responseJsonObject.toString());       	
        	
        }
        
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
