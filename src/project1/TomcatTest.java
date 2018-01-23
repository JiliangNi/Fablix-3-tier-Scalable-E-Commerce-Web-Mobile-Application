package project1;


/* A servlet to display the contents of the MySQL movieDB database */

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

public class TomcatTest extends HttpServlet {
    public String getServletInfo() {
        return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String loginUser = "root";
        String loginPasswd = "1356713njl*@^";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html"); // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        out.println("<HTML><HEAD><TITLE>Movie list</TITLE>"
        		+ "<style type = \"text/css\">"
        		+ "H1 {text-align:center;}"
        		+ "body {background-image:url('https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516675656073&di=ae2be6eceba5f2276aa8a07d456bd8fc&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D646697680%2C381297522%26fm%3D214%26gp%3D0.jpg');}"
        		+ ""
        		+ ""
        		+ ""
        		+ "</style>"
        		+ "</HEAD>"
        		+ "<BODY><H1>Top 20 rated movies</H1>");
        
        try {
            //Class.forName("org.gjt.mm.mysql.Driver");
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // Declare our statement
            Statement statement = dbcon.createStatement();

            String query = "Select m.title, m.year, m.director, group_concat(distinct g.name), group_concat(distinct s.name), rating\r\n" + 
            		"From (Select m.id, m.title, m.year, m.director, r.rating\r\n" + 
            		"From movies m, ratings r\r\n" + 
            		"where m.id = r.movieId\r\n" + 
            		"group by m.id\r\n" + 
            		"order by -r.rating\r\n" + 
            		"limit 20) as m, genres_in_movies gim, genres g, stars_in_movies sim, stars s\r\n" + 
            		"where m.id = gim.movieId and gim.genreId = g.id and m.id = sim.movieId and sim.starId = s.id\r\n" + 
            		"group by m.id\r\n" + 
            		"order by -rating\r\n" + 
            		"limit 20\r\n" + 
            		";";

            // Perform the query
            ResultSet rs = statement.executeQuery(query);

            out.println("<TABLE border>");

            // Iterate through each row of rs
            while (rs.next()) {
                String m_title = rs.getString(1);
                String m_year = rs.getString(2);
                String m_director = rs.getString(3);
                String m_genres = rs.getString(4);
                String m_stars = rs.getString(5);
                String m_rating = rs.getString(6);
                out.println("<tr>" + "<td>" + m_title + "</td>" + "<td>" 
                		+ m_year + "</td>" + "<td>"
                		+ m_director + "</td>" + "<td>"
                		+ m_genres + "</td>" + "<td>"
                		+ m_stars + "</td>" + "<td>"
                		+ m_rating + "</td>"
                        + "</tr>");
            }

            out.println("</TABLE>");

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
            out.println("<HTML>" + "<HEAD><TITLE>" + "MovieDB: Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>SQL error in doGet: " + ex.getMessage() + "</P></BODY></HTML>");
            return;
        }
        out.close();
    }
}
