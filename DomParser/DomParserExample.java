import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomParserExample {
	
	HashMap<String,movie> xmlMovies;
	HashMap<String,star>  xmlStars;
	HashMap<String,ArrayList<String>> xmlCasts; 
	Document dom;
	Document actor;
	Document cast;
	
	public DomParserExample()
	{
		xmlMovies = new HashMap<String,movie>();
		xmlStars = new HashMap<String,star>();
		xmlCasts = new HashMap<String,ArrayList<String>>();
	}
	
	public void runExample() {
		
		//parse the xml file and get the dom object
        parseXmlFile();
        
        //get each employee element and create a Employee object
        parseDocument();
        parseActors();
        parseCasts();

        insert_data();
	}
	
	private int num_rows(int[] row)
	{
		int i = 0;
		int total = 0;
		for(i = row.length-1;i>=0;i--) {
			total = total + row[i];
		}
		return total;
	}
	
	private void insert_data() {
        String loginUser = "root";
        String loginPasswd = "1356713njl*@^";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        try {
        	Class.forName("com.mysql.jdbc.Driver").newInstance();
        	Connection conn = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        	Statement cs = conn.createStatement();
        	ResultSet rs  = cs.executeQuery("select distinct(title),id from movies;");
        	HashMap<String,String> movie_titles; 
        	HashMap<String,String> star_names;
        	HashMap<String,Integer> genre_names;
        	
        	movie_titles	= new HashMap<String,String>();
        	star_names	= new HashMap<String,String>();
        	genre_names = new HashMap<String,Integer>();
        	while(rs.next())
        	{
        		movie_titles.put(rs.getString(1),rs.getString(2));
        	}
        	Statement cs1  = conn.createStatement();
        	ResultSet rs1  = cs1.executeQuery("select distinct(name),id from stars;");
        	while(rs1.next())
        	{
        		star_names.put(rs1.getString(1),rs1.getString(2));
        	}

        	rs.close();
        	cs.close();
        	rs1.close();
        	cs1.close();
        	
        	 cs1  = conn.createStatement();
        	 rs1  = cs1.executeQuery("select distinct(name),id from genres;");
        	
        	
        	while(rs1.next())
        	{
        		genre_names.put(rs1.getString(1),rs1.getInt(2));
        	}
        	
        	rs1.close();
        	cs1.close();
        	
        	int year_max = 1998;
        	cs1 = conn.createStatement();
        	rs1 = cs1.executeQuery("select max(id)+1 from genres;");
        	while(rs1.next())
        	{
        		year_max = rs1.getInt(1);
        	}
        	rs1.close();
        	cs1.close();
        	
        	int s_min = 1105;
        	cs1 = conn.createStatement();
        	rs1 = cs1.executeQuery("select min(id)-1 from stars;");
        	while(rs1.next())
        	{
        		s_min = Integer.parseInt(rs1.getString(1));
        	}
        	rs1.close();
        	cs1.close();
        	
        	System.out.println("Stars in XML: "+xmlStars.size());
        	System.out.println("Movies in XML: "+xmlMovies.size());
        	System.out.println("Movies in cast: "+xmlCasts.size()); 	
        	System.out.println("Movie size: " + movie_titles.size());
        	System.out.println("Genre size: " + genre_names.size());
        	System.out.println("Star size: " + star_names.size());
        	
        	PreparedStatement ps1=null;
        	PreparedStatement ps2=null;
        	PreparedStatement ps3=null;
        	PreparedStatement ps4=null;
        	PreparedStatement ps5=null;
        	
        	int[] iNoRows=null;
        	int[] iNoRows1=null;
        	int[] iNoRows2=null;
        	int[] iNoRows3=null;
        	int[] iNoRows4=null;
        	
        	String query_movie ="insert into movies values(?,?,?,?)";
        	String query_star = "insert into stars values(?,?,?)";
        	String query_genre = "insert into genres values(?,?)";
        	String query_sm = "insert into stars_in_movies values(?,?)";
        	String query_gm = "insert into genres_in_movies values(?,?)";
        	  	
        	try {
        		conn.setAutoCommit(false);
        		ps1=conn.prepareStatement(query_movie);
        		ps2=conn.prepareStatement(query_star);
        		ps3=conn.prepareStatement(query_genre);
        		ps4=conn.prepareStatement(query_sm);
        		ps5=conn.prepareStatement(query_gm);

        		int count = 0;
        		Iterator it = xmlMovies.entrySet().iterator();
        	    while (it.hasNext()) {
        	            Map.Entry pair = (Map.Entry)it.next();
        	            
        	            movie m = ((movie)pair.getValue());
        	            if(!movie_titles.containsKey(m.title)&&m.title!=null && !m.title.equals("NULL"))
        	            {
        	            	String m_id = "tt22" + m.id;
        	            	ps1.setString(1, m_id);
        	            	String m_title = "NULL";
        	            	if(m.title!=null) {
        	            		m_title = m.title;
        	            		}
        	            	ps1.setString(2, m_title);
        	            	
        	            	ps1.setInt(3, m.year);
        	            	String m_director = "NULL";
        	            	if(m.director!=null) {
        	            		m_director = m.director;
        	            		}
        	            	ps1.setString(4, m_director);
        	            	ps1.addBatch();
        	            	movie_titles.put(m_title,m_id);
        	            	for(int i=0;i<m.genre.size();i++) {
        	            		String g_name = m.genre.get(i);
        	            		if(!genre_names.containsKey(g_name)&&g_name!=null) {
        	            			int g_id = count+year_max;
        	            			ps3.setInt(1, g_id);
        	            			ps3.setString(2, g_name);
        	            			ps3.addBatch();
        	            			count++;
        	            			ps5.setInt(1, g_id);
        	            			ps5.setString(2,m_id);
        	            			ps5.addBatch();
        	            			genre_names.put(g_name,g_id);
        	            		}
        	            		else {
        	            			if(g_name!=null) {
        	            			ps5.setInt(1, genre_names.get(g_name));
        	            			ps5.setString(2, m_id);
        	            		    ps5.addBatch();
        	            			}}}}}
        		
        	    it = xmlStars.entrySet().iterator();
        	    int count_st=0;
        	    while(it.hasNext())
        	    {
        	    	Map.Entry pair = (Map.Entry)it.next();
        	    	String s_name = (String) pair.getKey();
        	    	String s_id ="";
    	    		if(!star_names.containsKey(s_name)&&!s_name.equals("NULL")) {
    	    			int s_year = 0000;
    	    			try {
    	    			 s_year = ((star)xmlStars.get(s_name)).year;
    	    			 ps2.setInt(3, s_year);
    	    			}
    	    			catch(NullPointerException e){
    	    				ps2.setNull(3, java.sql.Types.INTEGER);
    	    			}
    	    			s_id = "tt22" + String.format("%d", (s_min - count_st));
    	    			ps2.setString(1, s_id);
    	    			ps2.setString(2, s_name);
    	    			ps2.addBatch();
    	    			star_names.put(s_name,s_id);
    	    			count_st++;
    	    		}}
        	    it = xmlCasts.entrySet().iterator();

        	    while (it.hasNext()) {
        	    	Map.Entry pair = (Map.Entry)it.next();
        	    	ArrayList<String> sList = (ArrayList<String>) pair.getValue();
        	    	String c_id = (String)pair.getKey();
        	    	String c_title = "NULL";
        	    	try {
        	    		c_title = xmlMovies.get(c_id).title;
        	    	}
        	    	catch(NullPointerException e)
        	    	{
        	    		System.out.println("Movie ID of Error Date: "+c_id);	
        	    	}
        	    	if(movie_titles.containsKey(c_title)) // we can only link star with a existing movie
    	    			c_id = movie_titles.get(c_title);
        	    	else
        	    		c_id = "NULL";
        	    	for(int i=0;i<sList.size();i++)
        	    	{
        	    		String s_id ="";
        	    		String s_name = sList.get(i);
        	    		if(star_names.containsKey(s_name)&&!s_name.equals("NULL")&&!c_id.equals("NULL")) {
        	    			s_id = star_names.get(s_name);
        	    			if(s_id!=null) {
        	    				ps4.setString(1, s_id);
        	    				ps4.setString(2, c_id);
        	    				ps4.addBatch();
        	    			}}}}
        	    iNoRows = ps1.executeBatch();
        	    iNoRows1=ps3.executeBatch();
        	    iNoRows2=ps5.executeBatch();
        	    iNoRows3=ps2.executeBatch();
        	    iNoRows4=ps4.executeBatch();
        	    
        	    conn.commit();
        	    
        	    System.out.println("Updated: " + num_rows(iNoRows) + " rows of movies");
        	    System.out.println("Updated: " + num_rows(iNoRows1) + " rows of genres");
        	    System.out.println("Updated: " + num_rows(iNoRows2) + " rows of genres in movies");
        	    System.out.println("Updated: " + num_rows(iNoRows3) + " rows of stars");
        	    System.out.println("Updated: " + num_rows(iNoRows4) + " rows of stars in movies");
        	}
        	catch (SQLException e)
        	{
        		e.printStackTrace();
        		System.out.println("All rows fail to insert");
        	}}
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }}

	
	private void parseXmlFile() {
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            
            //parse using builder to get DOM representation of the XML file
            dom = db.parse("mains243.xml");
            actor = db.parse("actors63.xml");
            cast = db.parse("casts124.xml");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
		
	}
	private void parseDocument() {
		//get the root elememt
		Element docEle = dom.getDocumentElement();
		//get a nodelist of <employee> elements
		NodeList nl = docEle.getElementsByTagName("film");
		if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
            	
                //get the employee element
            	
                Element el = (Element) nl.item(i);
                
                //get the Employee object
                movie e = getMovie(el);

                //add it to list
                xmlMovies.put(getTextValue(el,"fid"),e);
            }
        }
	}
	
	private void parseActors() {
		//get the root elememt
		Element docEle = actor.getDocumentElement();
		
		//get a nodelist of <employee> elements
		NodeList nl = docEle.getElementsByTagName("actor");
		if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                //get the employee element
                Element el = (Element) nl.item(i);

                //get the Employee object
                star e = getStar(el);

                //add it to list
                xmlStars.put(e.name,e);
            }
        }	
	}
	
	
	private void parseCasts() {
		//get the root elememt
		Element docEle = cast.getDocumentElement();
		
		//get a nodelist of <employee> elements
		NodeList nl = docEle.getElementsByTagName("filmc");
		if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                //get the employee element
                Element el = (Element) nl.item(i);

                //add it to list
                NodeList nl1 = el.getElementsByTagName("m");
                for(int j=0;j<nl1.getLength();j++)
                {
                	Element el1  = (Element) nl1.item(j);
                	String key = getTextValue(el1,"f");
                	String value = getTextValue(el1,"a");
                	if(xmlCasts.containsKey(key))
                	{
                		ArrayList<String> a = xmlCasts.get(key);
                		if(!value.equals("s a")) {
                			a.add(value);
                		}
                		xmlCasts.put(key, a);
                	}
                	else {
                		ArrayList<String> a = new ArrayList();
                		if(!value.equals("s a")) {
                			a.add(value);
                		}
                		xmlCasts.put(key, a);
                	}}}}}

	private star getStar(Element empEl)
	{
		//for each <employee> element get text or int values of 
        //name ,id, age and name
		String name = getTextValue(empEl,"stagename");
		int year = getIntValue(empEl,"dob");
		
		//Create a new Employee with the value read from the xml nodes
		star s = new star(name,year);
		
		return s;
	}

    private movie getMovie(Element empEl) 
    {
        //for each <employee> element get text or int values of 
        //name ,id, age and name
        String id = getTextValue(empEl, "fid");
        String title = getTextValue(empEl, "t");
        int year = getIntValue(empEl, "year");
        String director = "";
        ArrayList<String> genre  = new ArrayList<String>();
        NodeList nl = empEl.getElementsByTagName("dirs");
        if(nl!=null&&nl.getLength()>0)
        {
        	Element el = (Element) nl.item(0);
        	NodeList nl1 = el.getElementsByTagName("dir");
        	if(nl1!=null&&nl.getLength()>0)
        	{
        		Element el1 = (Element)nl1.item(0);
        		director = getTextValue(el1,"dirn");
        	}}
        NodeList nl2  = empEl.getElementsByTagName("cats");
        if(nl2!=null&&nl2.getLength()>0)
        {
        	for(int i=0;i<nl2.getLength();i++)
        	{
        		genre.add ( getTextValue((Element)nl2.item(0),"cat") );
        	}}

        //Create a new Employee with the value read from the xml nodes
        movie m = new movie(id, title, year, director,genre);

        return m;
    }
    
    private String getTextValue(Element ele, String tagName)
    {
    	try {
    		String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }
        return textVal;
    	}
    	catch(NullPointerException e) {
    		return "NULL";
    	}}

    /**
     * Calls getTextValue and returns a int value
     * 
     * @param ele
     * @param tagName
     * @return
     */
    private int getIntValue(Element ele, String tagName) {
        //in production application you would catch the exception
    	try {
         return Integer.parseInt(getTextValue(ele, tagName));
         }
    	catch(NumberFormatException e){
    		return 0000; 
    	}}
    public static void main(String[] args) 
	{
    		//create an instance
		DomParserExample p = new DomParserExample();
		
		//call run example
		p.runExample();
	}
 
}
