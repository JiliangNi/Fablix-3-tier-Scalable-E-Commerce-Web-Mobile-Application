import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/AndroidLogin")
public class AndroidLogin extends HttpServlet {
    public String getServletInfo() {
    	return "Servlet connects to MySQL database and displays result of a SELECT";
    }
    // Use http GET
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    		
    	
    		HttpSession session = request.getSession();
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
            String email = request.getParameter("username");
			String password = request.getParameter("password");
			//System.out.println(email);
			//System.out.println(password);
			if (email == null){
				email = "";
			}
			if (password == null){
				password = "";
			}
			String query = "SELECT * from customers where email = \"" + email + "\" AND password = \"" + password + "\"";

            
            
            // Perform the query
            ResultSet rs = statement.executeQuery(query);
            Boolean main_page = false;
			  
			if (rs.next()){
				main_page = true;
				
			}

			rs.close();
			statement.close();
			dbcon.close();
			
			if (main_page){
				request.getSession().setAttribute("user", new User(email));
				out.print("true");
			}
			else{
				out.print("false");
			}
        
            
        } catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception:  " + ex.getMessage());
                ex = ex.getNextException();
            } // end while
        } // end catch SQLException
        catch(java.lang.Exception ex){
			out.println("<HTML>" +
						"<HEAD><TITLE>" +
						"MovieDB: Error" +
						"</TITLE></HEAD>\n<BODY>" +
						"<P>SQL error in doGet: " +
						ex.getMessage() + "</P></BODY></HTML>");
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
