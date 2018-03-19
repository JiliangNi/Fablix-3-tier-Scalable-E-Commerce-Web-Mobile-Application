import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

public class MovieList extends HttpServlet {
    public String getServletInfo() {
        return "Display the Movie List";
    }
    // Use http GET
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	long startTime = System.nanoTime();
    	
    	
		String title = request.getParameter("title");
		String year = request.getParameter("year");
		String director = request.getParameter("director");
		String starname = request.getParameter("starname");
		String title2 = request.getParameter("title2");
		String title3 = request.getParameter("title3");
		String genre = request.getParameter("genre");
		String orderby = request.getParameter("orderby");
		String aord = request.getParameter("aord");
		String lim = request.getParameter("lim");
		String count = request.getParameter("count");
		String direction = request.getParameter("direction");
		String pre_title = request.getParameter("pre_title");
		
		System.out.println(pre_title);
		
		
		
		String query_pv = request.getQueryString();
		
		
		
		
        HttpSession mysession = request.getSession();
        String page_offset = (String)mysession.getAttribute("page_offset");

        if(page_offset == null) {
        	page_offset = "0";
        }
        
		if(direction != null && direction.equals("prev"))
		{
			if(Integer.parseInt(page_offset)-1 < 0)
			{
				page_offset = "0";
			}
			else {
				page_offset = Integer.parseInt(page_offset)-1 +"";		
			}
			mysession.setAttribute("page_offset", page_offset);	
		}
		else if(direction != null && direction.equals("next")){
			page_offset = Integer.parseInt(page_offset)+1 +"";
			mysession.setAttribute("page_offset", page_offset);
		}
        
        System.out.println(page_offset);
		
		System.out.println(title + " " + year+ " " + director+ " " + starname+ " " + title2+ " " + title3+ " " + genre + " " + query_pv);
		
		/*
        String loginUser = "root";
        String loginPasswd = "1356713njl*@^";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        */
        
        
        response.setContentType("text/html"); // Response mime type
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
            
        try {
            
            // the following few lines are for connection pooling
            // Obtain our environment naming context

        	long startTime2 = System.nanoTime();
        	
        	
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
            
            String query = "";
            ResultSet rs;
            
            
            
            
            
            if(pre_title != null)
            {
                out.println("<html>\r\n" + 
                		"	<head>\r\n" + 
                		"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=window-1252\">\r\n" + 
                		"		<title>MovieList</title>\r\n" + 
                		"	<style type = \"text/css\"> \r\n" + 
                		"	.menu{position:relative;width:2000px;height:50px;margin:0 auto;padding:8px 0 0;margin-bottom:4px;z-index:99;}\r\n" + 
                		"	.menu .nav-menu>li{float:left;position:relative;margin-right:50}\r\n" + 
                		"	.secmenu{position:relative;width:2000px;height:50px;margin:0 auto;padding:8px 0 0;margin-bottom:4px;z-index:99;}\r\n" + 
                		"	.secmenu .nav-menu>li{float:left;position:relative;margin-right:50}\r\n" + 
                		"	</style>\r\n"+
                		"	</head>\r\n" + 
                		"	<body style=\"background: url(../../project2/img/homebg.jpg);\r\n" + 
                		"	background-size:100%;\">\r\n"+
                		"	<body>\r\n"+
                		"		<h1 style=\"text-align:center; left:0px; top:0px;font-family:'Comic Sans MS', cursive, sans-serif;font-size:250%;\"> <a href = \"/project2/Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>\r\n" + 
                		"");
                if(lim == null) {  
	                out.println("       <div id=\"menu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
	                		"		<ul class=\"nav-menu\">\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "pre_title=" + pre_title + "&orderby=title&aord=asc" +"&lim=10\">"+
	                		"		<div class=\"titleasd\">Title(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "pre_title=" + pre_title + "&orderby=title&aord=desc" + "&lim=10\">"+
	                		"		<div class=\"titledsd\">Title(Descending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "pre_title=" + pre_title + "&orderby=year&aord=asc" + "&lim=10\">"+
	                		"		<div class=\"yearasd\">Year(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "pre_title=" + pre_title + "&orderby=year&aord=desc" + "&lim=10\">"+
	                		"		<div class=\"yeardsd\">Year(Descending)</div></a></li>\r\n" + 
	                		"		</ul></div>");
                }
                else {
	                out.println("       <div id=\"menu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
	                		"		<ul class=\"nav-menu\">\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "pre_title=" + pre_title + "&orderby=title&aord=asc" +"&lim=" + lim +  "\">"+
	                		"		<div class=\"titleasd\">Title(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "pre_title=" + pre_title + "&orderby=title&aord=desc" + "&lim=" + lim +"\">"+
	                		"		<div class=\"titledsd\">Title(Descending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "pre_title=" + pre_title + "&orderby=year&aord=asc" + "&lim=" + lim + "\">"+
	                		"		<div class=\"yearasd\">Year(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "pre_title=" + pre_title + "&orderby=year&aord=desc" + "&lim=" + lim + "\">"+
	                		"		<div class=\"yeardsd\">Year(Descending)</div></a></li>\r\n" + 
	                		"		</ul></div>");
                }
                
                out.println("		<br>" +
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
                
                
                
                
                
                
                
                String [] pre_ls = pre_title.trim().split("\\s+");
                String pre_parameter = "+" + pre_ls[0] + "*";
                
                for(int i = 1; i<pre_ls.length;i++) {
                	pre_parameter += " " + "+" + pre_ls[i] + "*";
                }
                
                /* no fuzzy search
            	query += "select m.id, m.title, m.year, m.director, group_concat(distinct g.name), group_concat(distinct s.name)  \r\n" + 
            			"from(\r\n" + 
            			"select * \r\n" + 
            			"from movies m\r\n" + 
            			"where match(m.title) against ('" + pre_parameter + "' in boolean mode)) m, genres_in_movies gm, genres g, stars_in_movies sm, stars s \r\n" + 
            			"where m.id = gm.movieID and gm.genreID = g.id and sm.movieID = m.id and sm.starID = s.id \r\n" + 
            			"group by m.id\r\n";
            	*/
            	query += "select m.id, m.title, m.year, m.director, group_concat(distinct g.name), group_concat(distinct s.name)  \r\n" + 
            			"from(\r\n" + 
            			"select * \r\n" + 
            			"from movies m\r\n" + 
            			"where match(m.title) against ('" + pre_parameter + "' in boolean mode) or edth('" + pre_title + "', m.title, 2) = 1 ) m, genres_in_movies gm, genres g, stars_in_movies sm, stars s \r\n" + 
            			"where m.id = gm.movieID and gm.genreID = g.id and sm.movieID = m.id and sm.starID = s.id \r\n" + 
            			"group by m.id\r\n";                
                
            	
            	if(orderby !=null && aord !=null)
            	{
            		query += "order by m." + orderby + " " + aord + " \r\n";
            	}
            	String temp2 = Integer.parseInt(page_offset) * Integer.parseInt(lim)+"";
            	query += "limit " + lim + " offset " + temp2 + ";";
            	System.out.println(query);
            	
                statement = dbcon.prepareStatement(query);
                
                // Perform the query
                rs = statement.executeQuery();
                
                while (rs.next()) {
                    String s_id = rs.getString(1);
                    String s_title = rs.getString(2);
                    String s_year = rs.getString(3);
                    String s_director = rs.getString(4);
                    String s_genres = rs.getString(5);
                    String s_stars = rs.getString(6);
                    String [] s_stars_ls = s_stars.split(",");
                    out.println("				<tr>\r\n" + 
                    		"					<td>" + s_id + "</td>\r\n" + 
                    		"					<td>" + "<a href=\"SingleMovie?movieid=" + s_id + "\">" + s_title + "</a>"+ "</td>\r\n" + 
                    		"					<td>" + s_year + "</td>\r\n" + 
                    		"					<td>" + s_director + "</td>\r\n" + 
                    		"					<td>" + s_genres + "</td>\r\n"); 
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
                
                out.println("			</tbody>\r\n" + 
                		"		</table>\r\n");
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                if(orderby == null)
                {
                	if(lim == null)
                	{
                		out.println("		<a href=\"MovieList?pre_title=" + pre_title + "&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?pre_title=" + pre_title + "&direction=next\">Next</a>\r\n");
                	}
                	else
                	{
                		out.println("		<a href=\"MovieList?pre_title=" + pre_title + "&lim=" + lim +"&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?pre_title=" + pre_title + "&lim=" + lim +"&direction=next\">Next</a>\r\n");
                	}               	
                }
                else {
                	if(lim == null)
                	{
                		out.println("		<a href=\"MovieList?pre_title=" + pre_title + "&orderby=" + orderby + "&aord=" + aord +"&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?pre_title=" + pre_title + "&orderby=" + orderby + "&aord=" + aord +"&direction=next\">Next</a>\r\n");
                	}
                	else
                	{
                		out.println("		<a href=\"MovieList?pre_title=" + pre_title + "&orderby=" + orderby + "&aord=" + aord + "&lim=" + lim +"&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?pre_title=" + pre_title + "&orderby=" + orderby + "&aord=" + aord + "&lim=" + lim +"&direction=next\">Next</a>\r\n");
                	}
                }
                
                
                
                		
                
                if(orderby == null)
                {
                	out.println("<form>Results Per Page:"+
                    		"<select name=\"pageselect\" onchange=\"self.location.href=options[selectedIndex].value\" > \r\n" + 
                    		"<OPTION>" + "Please choose the number of results" + "</OPTION> \r\n" +
                    		"<OPTION value=\""+"MovieList?" + "pre_title=" + pre_title + "&lim=10" +"\">10</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "pre_title=" + pre_title + "&lim=25" +"\">25</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "pre_title=" + pre_title + "&lim=50" +"\">50</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "pre_title=" + pre_title + "&lim=100" +"\">100</OPTION> \r\n" + 
                    		"</select></form>");}
                else {
                	out.println("<form>Results Per Page:"+
                    		"<select name=\"pageselect\" onchange=\"self.location.href=options[selectedIndex].value\" > \r\n" + 
                    		"<OPTION>" + "Please choose the number of results" + "</OPTION> \r\n" +
                    		"<OPTION value=\""+"MovieList?" + "pre_title=" + pre_title + "&lim=10" +"&orderby=" + orderby + "&aord=" + aord + "\">10</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "pre_title=" + pre_title + "&lim=25" +"&orderby=" + orderby + "&aord=" + aord + "\">25</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "pre_title=" + pre_title + "&lim=50" +"&orderby=" + orderby + "&aord=" + aord + "\">50</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "pre_title=" + pre_title + "&lim=100" +"&orderby=" + orderby + "&aord=" + aord + "\">100</OPTION> \r\n" + 
                    		"</select></form>");
                }
                		
                		
                out.println(
                		"		<br>\r\n" +
                		"		<a href=\"Home\">Back to Home</a>" +
                		"       <div id=\"secmenu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
                		"		<ul class=\"nav-menu\">\r\n" +
                		"		<li class=\"\">\r\n" + 
                		"		<a = href=\"CheckOut\"><button>Checkout</button></a></li>\r\n" +
                		"		<li class=\"\">\r\n" + 
                		"		<form action = \"MyCart\">\r\n" + 
                		"		<button name=\"mycart\" value=\"show\"> MyCart </button>\r\n"+
                		"		</form></li>"+
                		"		</ul></div>"+
                		"		</form>");
                
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            else if(title2 != null)
            {
                out.println("<html>\r\n" + 
                		"	<head>\r\n" + 
                		"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=window-1252\">\r\n" + 
                		"		<title>MovieList</title>\r\n" + 
                		"	<style type = \"text/css\"> \r\n" + 
                		"	.menu{position:relative;width:2000px;height:50px;margin:0 auto;padding:8px 0 0;margin-bottom:4px;z-index:99;}\r\n" + 
                		"	.menu .nav-menu>li{float:left;position:relative;margin-right:50}\r\n" + 
                		"	.secmenu{position:relative;width:2000px;height:50px;margin:0 auto;padding:8px 0 0;margin-bottom:4px;z-index:99;}\r\n" + 
                		"	.secmenu .nav-menu>li{float:left;position:relative;margin-right:50}\r\n" + 
                		"	</style>\r\n"+
                		"	</head>\r\n" + 
                		"	<body style=\"background: url(../../project2/img/homebg.jpg);\r\n" + 
                		"	background-size:100%;\">\r\n"+
                		"	<body>\r\n"+
                		"		<h1 style=\"text-align:center; left:0px; top:0px;font-family:'Comic Sans MS', cursive, sans-serif;font-size:250%;\"> <a href = \"/project2/Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>\r\n" + 
                		"");
                if(lim == null) {  
	                out.println("       <div id=\"menu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
	                		"		<ul class=\"nav-menu\">\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title2=" + title2 + "&orderby=title&aord=asc" +"&lim=10\">"+
	                		"		<div class=\"titleasd\">Title(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title2=" + title2 + "&orderby=title&aord=desc" + "&lim=10\">"+
	                		"		<div class=\"titledsd\">Title(Descending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title2=" + title2 + "&orderby=year&aord=asc" + "&lim=10\">"+
	                		"		<div class=\"yearasd\">Year(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title2=" + title2 + "&orderby=year&aord=desc" + "&lim=10\">"+
	                		"		<div class=\"yeardsd\">Year(Descending)</div></a></li>\r\n" + 
	                		"		</ul></div>");
                }
                else {
	                out.println("       <div id=\"menu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
	                		"		<ul class=\"nav-menu\">\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title2=" + title2 + "&orderby=title&aord=asc" +"&lim=" + lim +  "\">"+
	                		"		<div class=\"titleasd\">Title(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title2=" + title2 + "&orderby=title&aord=desc" + "&lim=" + lim +"\">"+
	                		"		<div class=\"titledsd\">Title(Descending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title2=" + title2 + "&orderby=year&aord=asc" + "&lim=" + lim + "\">"+
	                		"		<div class=\"yearasd\">Year(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title2=" + title2 + "&orderby=year&aord=desc" + "&lim=" + lim + "\">"+
	                		"		<div class=\"yeardsd\">Year(Descending)</div></a></li>\r\n" + 
	                		"		</ul></div>");
                }
                
                out.println("		<br>" +
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
                
            	query += "select m.id, m.title, m.year, m.director, group_concat(distinct g.name), group_concat(distinct s.name)\r\n" + 
            			"from(\r\n" + 
            			"select *\r\n" + 
            			"from movies m\r\n" + 
            			"where m.title like \"" + title2 + "%\") m, genres_in_movies gm, genres g, stars_in_movies sm, stars s\r\n" + 
            			"where m.id = gm.movieID and gm.genreID = g.id and sm.movieID = m.id and sm.starID = s.id\r\n" + 
            			"group by m.id\r\n";
            	if(orderby !=null && aord !=null)
            	{
            		query += "order by m." + orderby + " " + aord + " \r\n";
            	}
            	
            	String temp2 = Integer.parseInt(page_offset) * Integer.parseInt(lim)+"";
            	query += "limit " + lim + " offset " + temp2 + ";";
            	
            	
                statement = dbcon.prepareStatement(query);
                
                // Perform the query
                rs = statement.executeQuery();
                
            	
                while (rs.next()) {
                    String s_id = rs.getString(1);
                    String s_title = rs.getString(2);
                    String s_year = rs.getString(3);
                    String s_director = rs.getString(4);
                    String s_genres = rs.getString(5);
                    String s_stars = rs.getString(6);
                    String [] s_stars_ls = s_stars.split(",");
                    out.println("				<tr>\r\n" + 
                    		"					<td>" + s_id + "</td>\r\n" + 
                    		"					<td>" + "<a href=\"SingleMovie?movieid=" + s_id + "\">" + s_title + "</a>"+ "</td>\r\n" + 
                    		"					<td>" + s_year + "</td>\r\n" + 
                    		"					<td>" + s_director + "</td>\r\n" + 
                    		"					<td>" + s_genres + "</td>\r\n"); 
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
                
                
                
                out.println("			</tbody>\r\n" + 
                		"		</table>\r\n");
                
                
                if(orderby == null)
                {
                	if(lim == null)
                	{
                		out.println("		<a href=\"MovieList?title2=" + title2 + "&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?title2=" + title2 + "&direction=next\">Next</a>\r\n");
                	}
                	else
                	{
                		out.println("		<a href=\"MovieList?title2=" + title2 + "&lim=" + lim +"&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?title2=" + title2 + "&lim=" + lim +"&direction=next\">Next</a>\r\n");
                	}               	
                }
                else {
                	if(lim == null)
                	{
                		out.println("		<a href=\"MovieList?title2=" + title2 + "&orderby=" + orderby + "&aord=" + aord +"&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?title2=" + title2 + "&orderby=" + orderby + "&aord=" + aord +"&direction=next\">Next</a>\r\n");
                	}
                	else
                	{
                		out.println("		<a href=\"MovieList?title2=" + title2 + "&orderby=" + orderby + "&aord=" + aord + "&lim=" + lim +"&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?title2=" + title2 + "&orderby=" + orderby + "&aord=" + aord + "&lim=" + lim +"&direction=next\">Next</a>\r\n");
                	}
                }
                
                
                
                		
                
                if(orderby == null)
                {
                	out.println("<form>Results Per Page:"+
                    		"<select name=\"pageselect\" onchange=\"self.location.href=options[selectedIndex].value\" > \r\n" + 
                    		"<OPTION>" + "Please choose the number of results" + "</OPTION> \r\n" +
                    		"<OPTION value=\""+"MovieList?" + "title2=" + title2 + "&lim=10" +"\">10</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title2=" + title2 + "&lim=25" +"\">25</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title2=" + title2 + "&lim=50" +"\">50</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title2=" + title2 + "&lim=100" +"\">100</OPTION> \r\n" + 
                    		"</select></form>");}
                else {
                	out.println("<form>Results Per Page:"+
                    		"<select name=\"pageselect\" onchange=\"self.location.href=options[selectedIndex].value\" > \r\n" + 
                    		"<OPTION>" + "Please choose the number of results" + "</OPTION> \r\n" +
                    		"<OPTION value=\""+"MovieList?" + "title2=" + title2 + "&lim=10" +"&orderby=" + orderby + "&aord=" + aord + "\">10</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title2=" + title2 + "&lim=25" +"&orderby=" + orderby + "&aord=" + aord + "\">25</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title2=" + title2 + "&lim=50" +"&orderby=" + orderby + "&aord=" + aord + "\">50</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title2=" + title2 + "&lim=100" +"&orderby=" + orderby + "&aord=" + aord + "\">100</OPTION> \r\n" + 
                    		"</select></form>");
                }
                		
                		
                out.println(
                		"		<br>\r\n" +
                		"		<a href=\"Home\">Back to Home</a>" +
                		"       <div id=\"secmenu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
                		"		<ul class=\"nav-menu\">\r\n" +
                		"		<li class=\"\">\r\n" + 
                		"		<a = href=\"CheckOut\"><button>Checkout</button></a></li>\r\n" +
                		"		<li class=\"\">\r\n" + 
                		"		<form action = \"MyCart\">\r\n" + 
                		"		<button name=\"mycart\" value=\"show\"> MyCart </button>\r\n"+
                		"		</form></li>"+
                		"		</ul></div>"+
                		"		</form>");
                
            }
            else if(title3 != null)
            {                
            	out.println("<html>\r\n" + 
                		"	<head>\r\n" + 
                		"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=window-1252\">\r\n" + 
                		"		<title>MovieList</title>\r\n" + 
                		"	<style type = \"text/css\"> \r\n" + 
                		"	.menu{position:relative;width:2000px;height:50px;margin:0 auto;padding:8px 0 0;margin-bottom:4px;z-index:99;}\r\n" + 
                		"	.menu .nav-menu>li{float:left;position:relative;margin-right:50}\r\n" + 
                		"	.secmenu{position:relative;width:2000px;height:50px;margin:0 auto;padding:8px 0 0;margin-bottom:4px;z-index:99;}\r\n" + 
                		"	.secmenu .nav-menu>li{float:left;position:relative;margin-right:50}\r\n" + 
                		"	</style>\r\n"+
                		"	</head>\r\n" + 
                		"	<body style=\"background: url(../../project2/img/homebg.jpg);\r\n" + 
                		"	background-size:100%;\">\r\n"+
                		"	<body>\r\n"+
                		"		<h1 style=\"text-align:center; left:0px; top:0px;font-family:'Comic Sans MS', cursive, sans-serif;font-size:250%;\"> <a href = \"/project2/Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>\r\n" + 
                		"");
                if(lim == null) {  
                	out.println("       <div id=\"menu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
	                		"		<ul class=\"nav-menu\">\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title3=" + title3 + "&orderby=title&aord=asc" +"&lim=10\">"+
	                		"		<div class=\"titleasd\">Title(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title3=" + title3 + "&orderby=title&aord=desc" + "&lim=10\">"+
	                		"		<div class=\"titledsd\">Title(Descending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title3=" + title3 + "&orderby=year&aord=asc" + "&lim=10\">"+
	                		"		<div class=\"yearasd\">Year(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title3=" + title3 + "&orderby=year&aord=desc" + "&lim=10\">"+
	                		"		<div class=\"yeardsd\">Year(Descending)</div></a></li>\r\n" + 
	                		"		</ul></div>");}
                else {
                	out.println("       <div id=\"menu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
	                		"		<ul class=\"nav-menu\">\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title3=" + title3 + "&orderby=title&aord=asc" +"&lim=" + lim +  "\">"+
	                		"		<div class=\"titleasd\">Title(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title3=" + title3 + "&orderby=title&aord=desc" + "&lim=" + lim +"\">"+
	                		"		<div class=\"titledsd\">Title(Descending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title3=" + title3 + "&orderby=year&aord=asc" + "&lim=" + lim + "\">"+
	                		"		<div class=\"yearasd\">Year(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title3=" + title3 + "&orderby=year&aord=desc" + "&lim=" + lim + "\">"+
	                		"		<div class=\"yeardsd\">Year(Descending)</div></a></li>\r\n" + 
	                		"		</ul></div>");}
                out.println("		<br>" +
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
                
                
                
                
            	query += "select m.id, m.title, m.year, m.director, group_concat(distinct g.name), group_concat(distinct s.name)\r\n" + 
            			"from(\r\n" + 
            			"select *\r\n" + 
            			"from movies m\r\n" + 
            			"where m.title like \"" + title3 + "%\") m, genres_in_movies gm, genres g, stars_in_movies sm, stars s\r\n" + 
            			"where m.id = gm.movieID and gm.genreID = g.id and sm.movieID = m.id and sm.starID = s.id\r\n" + 
            			"group by m.id\r\n";
            	if(orderby !=null && aord !=null)
            	{
            		query += "order by m."+ orderby + " " + aord + " \r\n";
            	}
            	String temp2 = Integer.parseInt(page_offset) * Integer.parseInt(lim)+"";
            	query += "limit " + lim + " offset " + temp2 + ";";
            	            	
            	
                statement = dbcon.prepareStatement(query);
                
                // Perform the query
                rs = statement.executeQuery();
                
                
                while (rs.next()) {
                    String s_id = rs.getString(1);
                    String s_title = rs.getString(2);
                    String s_year = rs.getString(3);
                    String s_director = rs.getString(4);
                    String s_genres = rs.getString(5);
                    String s_stars = rs.getString(6);
                    String [] s_stars_ls = s_stars.split(",");
                    out.println("				<tr>\r\n" + 
                    		"					<td>" + s_id + "</td>\r\n" + 
                    		"					<td>" + "<a href=\"SingleMovie?movieid=" + s_id + "\">" + s_title + "</a>"+ "</td>\r\n" + 
                    		"					<td>" + s_year + "</td>\r\n" + 
                    		"					<td>" + s_director + "</td>\r\n" + 
                    		"					<td>" + s_genres + "</td>\r\n"); 
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
                
                
                
                
                
                
                
                out.println("			</tbody>\r\n" + 
                		"		</table>\r\n");
                
                
                
                if(orderby == null)
                {
                	if(lim == null)
                	{
                		out.println("		<a href=\"MovieList?title3=" + title3 + "&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?title3=" + title3 + "&direction=next\">Next</a>\r\n");
                	}
                	else
                	{
                		out.println("		<a href=\"MovieList?title3=" + title3 + "&lim=" + lim +"&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?title3=" + title3 + "&lim=" + lim +"&direction=next\">Next</a>\r\n");
                	}               	
                }
                else {
                	if(lim == null)
                	{
                		out.println("		<a href=\"MovieList?title3=" + title3 + "&orderby=" + orderby + "&aord=" + aord +"&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?title3=" + title3 + "&orderby=" + orderby + "&aord=" + aord +"&direction=next\">Next</a>\r\n");
                	}
                	else
                	{
                		out.println("		<a href=\"MovieList?title3=" + title3 + "&orderby=" + orderby + "&aord=" + aord + "&lim=" + lim +"&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?title3=" + title3 + "&orderby=" + orderby + "&aord=" + aord + "&lim=" + lim +"&direction=next\">Next</a>\r\n");
                	}
                }
                

                
                if(orderby == null)
                {
                	out.println("<form>Results Per Page:"+
                    		"<select name=\"pageselect\" onchange=\"self.location.href=options[selectedIndex].value\" > \r\n" + 
                       		"<OPTION>" + "Please choose the number of results" + "</OPTION> \r\n" +
                    		"<OPTION value=\""+"MovieList?" + "title3=" + title3 + "&lim=10" +"\">10</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title3=" + title3 + "&lim=25" +"\">25</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title3=" + title3 + "&lim=50" +"\">50</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title3=" + title3 + "&lim=100" +"\">100</OPTION> \r\n" + 
                    		"</select></form>");}
                else {
                	out.println("<form>Results Per Page:"+
                    		"<select name=\"pageselect\" onchange=\"self.location.href=options[selectedIndex].value\" > \r\n" + 
                       		"<OPTION>" + "Please choose the number of results" + "</OPTION> \r\n" +
                    		"<OPTION value=\""+"MovieList?" + "title3=" + title3 + "&lim=10" +"&orderby=" + orderby + "&aord=" + aord + "\">10</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title3=" + title3 + "&lim=25" +"&orderby=" + orderby + "&aord=" + aord + "\">25</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title3=" + title3 + "&lim=50" +"&orderby=" + orderby + "&aord=" + aord + "\">50</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title3=" + title3 + "&lim=100" +"&orderby=" + orderby + "&aord=" + aord + "\">100</OPTION> \r\n" + 
                    		"</select></form>");
                    }
                
                out.println(
                		"		<br>\r\n" +
                		"		<a href=\"Home\">Back to Home</a>" +
                		"       <div id=\"secmenu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
                		"		<ul class=\"nav-menu\">\r\n" +
                		"		<li class=\"\">\r\n" + 
                		"		<a = href=\"CheckOut\"><button>Checkout</button></a></li>\r\n" +
                		"		<li class=\"\">\r\n" + 
                		"		<form action = \"MyCart\">\r\n" + 
                		"		<button name=\"mycart\" value=\"show\"> MyCart </button>\r\n"+
                		"		</form></li>"+
                		"		</ul></div>"+
                		"		</form>");
                
            }
            else if(genre != null)
            {
                out.println("<html>\r\n" + 
                		"	<head>\r\n" + 
                		"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=window-1252\">\r\n" + 
                		"		<title>MovieList</title>\r\n" + 
                		"	<style type = \"text/css\"> \r\n" + 
                		"	.menu{position:relative;width:2000px;height:50px;margin:0 auto;padding:8px 0 0;margin-bottom:4px;z-index:99;}\r\n" + 
                		"	.menu .nav-menu>li{float:left;position:relative;margin-right:50}\r\n" + 
                		"	.secmenu{position:relative;width:2000px;height:50px;margin:0 auto;padding:8px 0 0;margin-bottom:4px;z-index:99;}\r\n" + 
                		"	.secmenu .nav-menu>li{float:left;position:relative;margin-right:50}\r\n" + 
                		"	</style>\r\n"+
                		"	</head>\r\n" + 
                		"	<body style=\"background: url(../../project2/img/homebg.jpg);\r\n" + 
                		"	background-size:100%;\">\r\n"+
                		"	<body>\r\n"+
                		"		<h1 style=\"text-align:center; left:0px; top:0px;font-family:'Comic Sans MS', cursive, sans-serif;font-size:250%;\"> <a href = \"/project2/Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>\r\n" + 
                		"");
                if(lim == null) {  
	                out.println("       <div id=\"menu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
	                		"		<ul class=\"nav-menu\">\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "genre=" + genre + "&orderby=title&aord=asc" +"&lim=10\">"+
	                		"		<div class=\"titleasd\">Title(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "genre=" + genre   + "&orderby=title&aord=desc" + "&lim=10\">"+
	                		"		<div class=\"titledsd\">Title(Descending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "genre=" + genre  + "&orderby=year&aord=asc" + "&lim=10\">"+
	                		"		<div class=\"yearasd\">Year(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "genre=" + genre +  "&orderby=year&aord=desc" + "&lim=10\">"+
	                		"		<div class=\"yeardsd\">Year(Descending)</div></a></li>\r\n" + 
	                		"		</ul></div>"
	                		);}
                else {
	                out.println("       <div id=\"menu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
	                		"		<ul class=\"nav-menu\">\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "genre=" + genre  + "&orderby=title&aord=asc" +"&lim=" + lim +  "\">"+
	                		"		<div class=\"titleasd\">Title(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "genre=" + genre  + "&orderby=title&aord=desc" + "&lim=" + lim +"\">"+
	                		"		<div class=\"titledsd\">Title(Descending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" +"genre=" + genre + "&orderby=year&aord=asc" + "&lim=" + lim + "\">"+
	                		"		<div class=\"yearasd\">Year(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "genre=" + genre  + "&orderby=year&aord=desc" + "&lim=" + lim + "\">"+
	                		"		<div class=\"yeardsd\">Year(Descending)</div></a></li>\r\n" + 
	                		"		</ul></div>");
                }
                out.println("		<br>" +
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
                
                
                
            	query += "select m.id, m.title, m.year, m.director, group_concat(distinct g.name), group_concat(distinct s.name)\r\n" + 
            			"from movies m, genres_in_movies gm, genres g, stars_in_movies sm, stars s\r\n" + 
            			"where m.id = gm.movieID and gm.genreID = g.id and g.name = '" + genre + "' and sm.movieID = m.id and sm.starID = s.id\r\n" + 
            			"group by m.id\r\n";
            	if(orderby !=null && aord !=null)
            	{
            		query += "order by m."+ orderby + " " + aord + " \r\n";
            	}
            	String temp2 = Integer.parseInt(page_offset) * Integer.parseInt(lim)+"";
            	query += "limit " + lim + " offset " + temp2 + ";";
            	
            	
            	
                statement = dbcon.prepareStatement(query);
                
                // Perform the query
                rs = statement.executeQuery();
                
            	
            	
                while (rs.next()) {
                    String s_id = rs.getString(1);
                    String s_title = rs.getString(2);
                    String s_year = rs.getString(3);
                    String s_director = rs.getString(4);
                    String s_genres = rs.getString(5);
                    String s_stars = rs.getString(6);
                    String [] s_stars_ls = s_stars.split(",");
                    out.println("				<tr>\r\n" + 
                    		"					<td>" + s_id + "</td>\r\n" + 
                    		"					<td>" + "<a href=\"SingleMovie?movieid=" + s_id + "\">" + s_title + "</a>"+ "</td>\r\n" + 
                    		"					<td>" + s_year + "</td>\r\n" + 
                    		"					<td>" + s_director + "</td>\r\n" + 
                    		"					<td>" + s_genres + "</td>\r\n"); 
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
                
                
                
                out.println("			</tbody>\r\n" + 
                		"		</table>\r\n");
                
                if(orderby == null)
                {
                	if(lim == null)
                	{
                		out.println("		<a href=\"MovieList?genre=" + genre + "&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?genre=" + genre + "&direction=next\">Next</a>\r\n");
                	}
                	else
                	{
                		out.println("		<a href=\"MovieList?genre=" + genre + "&lim=" + lim +"&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?genre=" + genre + "&lim=" + lim +"&direction=next\">Next</a>\r\n");
                	}               	
                }
                else {
                	if(lim == null)
                	{
                		out.println("		<a href=\"MovieList?genre=" + genre + "&orderby=" + orderby + "&aord=" + aord +"&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?genre=" + genre + "&orderby=" + orderby + "&aord=" + aord +"&direction=next\">Next</a>\r\n");
                	}
                	else
                	{
                		out.println("		<a href=\"MovieList?genre=" + genre + "&orderby=" + orderby + "&aord=" + aord + "&lim=" + lim +"&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?genre=" + genre + "&orderby=" + orderby + "&aord=" + aord + "&lim=" + lim +"&direction=next\">Next</a>\r\n");
                	}
                }
                
                		
                
                
                if(orderby == null)
                {
                    out.println("<form>Results Per Page:"+
                    		"<select name=\"pageselect\" onchange=\"self.location.href=options[selectedIndex].value\" > \r\n" + 
                       		"<OPTION>" + "Please choose the number of results" + "</OPTION> \r\n" +
                    		"<OPTION value=\""+"MovieList?" + "genre=" + genre + "&lim=10" +"\">10</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "genre=" + genre + "&lim=25" +"\">25</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "genre=" + genre + "&lim=50" +"\">50</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "genre=" + genre + "&lim=100" +"\">100</OPTION> \r\n" + 
                    		"</select></form>");}
                else {
                	out.println("<form>Results Per Page:"+
                    		"<select name=\"pageselect\" onchange=\"self.location.href=options[selectedIndex].value\" > \r\n" + 
                       		"<OPTION>" + "Please choose the number of results" + "</OPTION> \r\n" +
                    		"<OPTION value=\""+"MovieList?" + "genre=" + genre + "&lim=10" +"&orderby=" + orderby + "&aord=" + aord +"\">10</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "genre=" + genre + "&lim=25" +"&orderby=" + orderby + "&aord=" + aord +"\">25</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "genre=" + genre + "&lim=50" +"&orderby=" + orderby + "&aord=" + aord +"\">50</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "genre=" + genre + "&lim=100" +"&orderby=" + orderby + "&aord=" + aord +"\">100</OPTION> \r\n" + 
                    		"</select></form>");
                }
                
                out.println(
                		"		<br>\r\n" + 
                		"		<a href=\"Home\">Back to Home</a>" +
                		"       <div id=\"secmenu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
                		"		<ul class=\"nav-menu\">\r\n" +
                		"		<li class=\"\">\r\n" + 
                		"		<a = href=\"CheckOut\"><button>Checkout</button></a></li>\r\n" +
                		"		<li class=\"\">\r\n" + 
                		"		<form action = \"MyCart\">\r\n" + 
                		"		<button name=\"mycart\" value=\"show\"> MyCart </button>\r\n"+
                		"		</form></li>"+
                		"		</ul></div>"+
                		"		</form>");
               
            }
            else if(title != null)
            {
            	out.println("<html>\r\n" + 
                		"	<head>\r\n" + 
                		"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=window-1252\">\r\n" + 
                		"		<title>MovieList</title>\r\n" + 
                		"	<style type = \"text/css\"> \r\n" + 
                		"	.menu{position:relative;width:2000px;height:50px;margin:0 auto;padding:8px 0 0;margin-bottom:4px;z-index:99;}\r\n" + 
                		"	.menu .nav-menu>li{float:left;position:relative;margin-right:50}\r\n" + 
                		"	.secmenu{position:relative;width:2000px;height:50px;margin:0 auto;padding:8px 0 0;margin-bottom:4px;z-index:99;}\r\n" + 
                		"	.secmenu .nav-menu>li{float:left;position:relative;margin-right:50}\r\n" + 
                		"	</style>\r\n"+
                		"	</head>\r\n" + 
                		"	<body style=\"background: url(../../project2/img/homebg.jpg);\r\n" + 
                		"	background-size:100%;\">\r\n"+
                		"	<body>\r\n"+
                		"		<h1 style=\"text-align:center; left:0px; top:0px;font-family:'Comic Sans MS', cursive, sans-serif;font-size:250%;\"> <a href = \"/project2/Home\" style=\"color:OrangeRed;text-decoration:none;\">Fablix</a></h1>\r\n" + 
                		"");
                if(lim == null) {  
                	out.println("       <div id=\"menu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
	                		"		<ul class=\"nav-menu\">\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&orderby=title&aord=asc" +"&lim=10\">"+
	                		"		<div class=\"titleasd\">Title(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&orderby=title&aord=desc" + "&lim=10\">"+
	                		"		<div class=\"titledsd\">Title(Descending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&orderby=year&aord=asc"+ "&lim=10\">"+
	                		"		<div class=\"yearasd\">Year(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&orderby=year&aord=desc"+ "&lim=10\">"+
	                		"		<div class=\"yeardsd\">Year(Descending)</div></a></li>\r\n" + 
	                		"		</ul></div>"
	                		);
                }
                else {
                	out.println("       <div id=\"menu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
	                		"		<ul class=\"nav-menu\">\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&orderby=title&aord=asc"+"&lim=" + lim +  "\">"+
	                		"		<div class=\"titleasd\">Title(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&orderby=title&aord=desc" + "&lim=" + lim +"\">"+
	                		"		<div class=\"titledsd\">Title(Descending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" +"title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&orderby=year&aord=asc" + "&lim=" + lim + "\">"+
	                		"		<div class=\"yearasd\">Year(Ascending)</div></a></li>\r\n" + 
	                		"		<li class=\"\">\r\n" + 
	                		"		<a href=\"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&orderby=year&aord=desc"+ "&lim=" + lim + "\">"+
	                		"		<div class=\"yeardsd\">Year(Descending)</div></a></li>\r\n" + 
	                		"		</ul></div>");
                }
                out.println("		<br>" +
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
                
                
                
            	query += "select m.id, m.title, m.year, m.director, group_concat(distinct g.name), group_concat(distinct s.name)\r\n" + 
            			"from \r\n" + 
            			"(select m1.id as id, m1.title as title, m1.year as year, m1.director as director\r\n";
            	if(!starname.equals("")) {
            		query += "from movies m1, stars_in_movies sm1, stars s1\r\n" + "where ";
            		query += " m1.id = sm1.movieID and sm1.starID = s1.id and s1.name like \"%" + starname + "%\"\r\n and";
            	}
            	else {
            		query += "from movies m1 \r\n" + "where ";
            	}
            	if(!title.equals("")) {
            		query += " m1.title like \"%" + title + "%\" and";
            	}
            	if(!year.equals("")){
            		query += " m1.year = " + year + " and";
            	}
            	if(!director.equals("")){
            		query += " m1.director like \"%" + director + "%\" and";
            	}
            	query += " true\r\n";
            	query += "group by m1.id\r\n" + 
            			") m, genres_in_movies gm, genres g, stars_in_movies sm, stars s\r\n" + 
            			"where m.id = gm.movieID and gm.genreID = g.id and m.id = sm.movieID and sm.starID = s.id\r\n" + 
            			"group by m.id\r\n";
            	if(orderby !=null && aord !=null)
            	{
            		query += "order by m."+ orderby + " " + aord + " \r\n";
            	}
            	String temp2 = Integer.parseInt(page_offset) * Integer.parseInt(lim)+"";
            	query += "limit " + lim + " offset " + temp2 + ";";
            	
            	
                statement = dbcon.prepareStatement(query);
                
                // Perform the query
                rs = statement.executeQuery();
                
            	
                    
                while (rs.next()) {
                    String s_id = rs.getString(1);
                    String s_title = rs.getString(2);
                    String s_year = rs.getString(3);
                    String s_director = rs.getString(4);
                    String s_genres = rs.getString(5);
                    String s_stars = rs.getString(6);
                    String [] s_stars_ls = s_stars.split(",");
                    out.println("				<tr>\r\n" + 
                    		"					<td>" + s_id + "</td>\r\n" + 
                    		"					<td>" + "<a href=\"SingleMovie?movieid=" + s_id + "\">" + s_title + "</a>"+ "</td>\r\n" + 
                    		"					<td>" + s_year + "</td>\r\n" + 
                    		"					<td>" + s_director + "</td>\r\n" + 
                    		"					<td>" + s_genres + "</td>\r\n"); 
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
                
                
                
                
                
                
                
                
                
                
                out.println("			</tbody>\r\n" + 
                		"		</table>\r\n");
                
                
                if(orderby == null)
                {
                	if(lim == null)
                	{
                		out.println("		<a href=\"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&direction=next\">Next</a>\r\n");
                	}
                	else
                	{
                		out.println("		<a href=\"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&lim=" + lim +"&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&lim=" + lim +"&direction=next\">Next</a>\r\n");
                	}               	
                }
                else {
                	if(lim == null)
                	{
                		out.println("		<a href=\"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&orderby=" + orderby + "&aord=" + aord +"&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&orderby=" + orderby + "&aord=" + aord +"&direction=next\">Next</a>\r\n");
                	}
                	else
                	{
                		out.println("		<a href=\"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&orderby=" + orderby + "&aord=" + aord + "&lim=" + lim +"&direction=prev\">Prev</a>\r\n");
                		out.println("		<a href=\"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&orderby=" + orderby + "&aord=" + aord + "&lim=" + lim +"&direction=next\">Next</a>\r\n");
                	}
                }
                		
                
                if(orderby == null)
                {
                	out.println("<form>Results Per Page:"+
                    		"<select name=\"pageselect\" onchange=\"self.location.href=options[selectedIndex].value\" > \r\n" + 
                       		"<OPTION>" + "Please choose the number of results" + "</OPTION> \r\n" +
                    		"<OPTION value=\""+"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&lim=10" +"\">10</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&lim=25" +"\">25</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&lim=50" +"\">50</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&lim=100" +"\">100</OPTION> \r\n" + 
                    		"</select></form>");}
                else {
                	out.println("<form>Results Per Page:"+
                    		"<select name=\"pageselect\" onchange=\"self.location.href=options[selectedIndex].value\" > \r\n" + 
                       		"<OPTION>" + "Please choose the number of results" + "</OPTION> \r\n" +
                    		"<OPTION value=\""+"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&lim=10" + "&orderby=" + orderby + "&aord=" + aord + "\">10</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&lim=25" + "&orderby=" + orderby + "&aord=" + aord + "\">25</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&lim=50" + "&orderby=" + orderby + "&aord=" + aord + "\">50</OPTION> \r\n" + 
                    		"<OPTION value=\""+"MovieList?" + "title=" + title + "&year=" + year + "&director=" + director + "&starname=" + starname + "&lim=100" + "&orderby=" + orderby + "&aord=" + aord + "\">100</OPTION> \r\n" + 
                    		"</select></form>");}
                
                
                
                
                out.println(
                		"		<br>\r\n" +
                		"		<a href=\"Home\">Back to Home</a>" +
                		"       <div id=\"secmenu\" class=\"menu report-wrap-module report-scroll-module \">\r\n" + 
                		"		<ul class=\"nav-menu\">\r\n" +
                		"		<li class=\"\">\r\n" + 
                		"		<a = href=\"CheckOut\"><button>Checkout</button></a></li>\r\n" +
                		"		<li class=\"\">\r\n" + 
                		"		<form action = \"MyCart\">\r\n" + 
                		"		<button name=\"mycart\" value=\"show\"> MyCart </button>\r\n"+
                		"		</form></li>"+
                		"		</ul></div>"+
                		"		</form>");
               
                
                
            }
            
            
            
            
            
            
            

            
            
            out.println("	</body>\r\n" + 
            		"</html>");
            
            dbcon.close();
            long endTime2 = System.nanoTime();
            long elapsedTime2 = endTime2 - startTime2;
            
            
            
            
            File f = new File("TJ.txt");
            PrintWriter writer = null;
            if ( f.exists() && !f.isDirectory() ) {
                writer = new PrintWriter(new FileOutputStream(new File("TJ.txt"), true));
            }
            else {
                writer = new PrintWriter("TJ.txt");
            }
            writer.println("TJ "+elapsedTime2);
            writer.close();
            
            
            
            
            
        } catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception:  " + ex.getMessage());
            } // end while
            out.println("<HTML>" + "<HEAD><TITLE>" + "Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>Error!</P>" + "<a href=\"Home\">Back to Home</a>\r\n" + "</BODY></HTML>");
            return;
        } // end catch SQLException
        catch (java.lang.Exception ex) {
            out.println("<HTML>" + "<HEAD><TITLE>" + "MovieList: Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>SQL error in doGet: " + ex.getMessage() + "</P></BODY></HTML>");
            return;
        }
        out.close();
        
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        
        File f1 = new File("TS.txt");
        PrintWriter writer1 = null;
        if ( f1.exists() && !f1.isDirectory() ) {
            writer1 = new PrintWriter(new FileOutputStream(new File("TS.txt"), true));
        }
        else {
            writer1 = new PrintWriter("TS.txt");
        }
        writer1.println("TS "+elapsedTime);
        writer1.close();
        
    }
}
