import java.sql.*;

//database connection class
public class DBconnector 
	{
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	/**
	 * database connection constructor
	 */
	public DBconnector(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Correct");
			
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/databasehomework", "root","");
			
			st = con.createStatement();
		}catch(Exception ex){
			System.out.println("Error");
		}
	}
	/**
	 * adds team to the team table in database given the parameter
	 * @param teamname team name of the 
	 * @param year the year that the team has founded
	 */
	public void addTeam(String teamname, int year){
		try{
			String query = " INSERT INTO `databasehomework`.`team` (`TeamName`, `FoundYear`)"
			        + " values (?, ?)";
			 
			      // create the mysql insert preparedstatement
			      PreparedStatement preparedStmt = con.prepareStatement(query);
			      preparedStmt.setString (1, teamname);
			      preparedStmt.setInt(2, year);
			      preparedStmt.execute();
			      System.out.println("The team added perfectly");
			
		}catch(Exception ex){
			System.out.println(ex);
		}
	}
	
	public int getEventId(String eventname){
		try{
			String query = "SELECT eventid FROM `event` where eventname like '" +eventname+"'";
			rs = st.executeQuery(query);
			//System.out.println(query);
			if(rs.next()){
				int id = rs.getInt("eventid");
				return id;
			}
			else{
				return -1;
			}
			
			
			
		}catch(Exception ex){
			System.out.println(ex);
			return -1;
			
		}
	}
	
	public int getGenreId(String eventname){
		try{
			String query = "SELECT genreid FROM `event` where eventname like '" +eventname+"'";
			rs = st.executeQuery(query);
			//System.out.println(query);
			if(rs.next()){
				int id = rs.getInt("genreid");
				return id;
			}
			else{
				return -1;
			}
			
			
			
		}catch(Exception ex){
			System.out.println(ex);
			return -1;
			
		}
	}
	/**
	 * adds event to the event table in the database
	 * @param eventname
	 * @param price
	 * @param genre
	 * @param location
	 * @param date
	 * @return
	 */
	public int addEvent(String eventname, int price,String genre, String location, String date){
		try{
			int genreid = addgenre(genre);
			int locationid = addlocation(location);
			String query = " INSERT INTO `databasehomework`.`event` (`EventName`,`Price`,`GenreID`, `LocationID`, `Date`,`Topic`)"
			        + " values (?, ?, ?, ?, ?,'')";
			 
			      // create the mysql insert preparedstatement
			      PreparedStatement preparedStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			      preparedStmt.setString (1, eventname);
			      preparedStmt.setInt(2, price);
			      preparedStmt.setInt(3, genreid);
			      preparedStmt.setInt(4, locationid);
			      preparedStmt.setString (5, date);
			      preparedStmt.execute();
			      System.out.println("Event added perfectly");
			      
			      rs = st.executeQuery("select last_insert_id() as last_id from event");
			      
			      ResultSet keys = preparedStmt.getGeneratedKeys();    
			      keys.next();  
			      int lastid = keys.getInt(1);
			      System.out.println("lastid: " + lastid);
			      return lastid;
			      
			
		}catch(Exception ex){
			System.out.println(ex);
			return 0;
		}
	}
	/**
	 * creates an event in event table and creates a sport event in sports table.
	 * @param hometeamid the team id of the home team
	 * @param awayteamid the team id of the away team
	 * @param price
	 * @param eventname
	 * @param genre
	 * @param location
	 * @param date
	 */
	public void addSport(int hometeamid, int awayteamid ,int price ,String eventname, String genre, String location, String date){
		try{
			
			String query = "INSERT INTO `databasehomework`.`sports` (`HomeTeam`, `AwayTeam`, `EventID`)"
			        + " values (?, ?, ?)";
			 
			//add event and get last autoincrement value which was created in the event table
			int lastid = addEvent(eventname, price, genre, location, date);
				
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setInt (1, hometeamid);
		      preparedStmt.setInt(2, awayteamid);
		      preparedStmt.setInt(3, lastid);
		      
		      preparedStmt.execute();
		      System.out.println("Sport event added perfectly");
		
		}catch(Exception ex){
			System.out.println(ex);
		}
	}
	
	/**
	 * Search an event in the event database given by parameter and return an event class value to 
	 * return multiple values.
	 * @param search the name of the event which will be searched in the event table.
	 * @return event will be returned
	 */
	public Event searchEvent(String search){
		try{
			String query = "SELECT eventname, price, locationname, date "
					+ "FROM event, location "
					+ "WHERE eventname like '%" + search + "%' and event.locationid = location.locationid";
			rs = st.executeQuery(query);
			//System.out.println(query);
			while(rs.next()){
				String eventname = rs.getString("eventname");
				int price = rs.getInt("price");
				String locationname = rs.getString("locationname");
				String date = rs.getString("date");
				//System.out.println(eventname + " " + price + " " + locationname);
				Event event = new Event(eventname, price, locationname, date);
				return event;
			}
			return new Event();
			
		}catch(Exception ex){
			System.out.println(ex);
			return new Event();
			
		}
		
	}
	/**
	 * Search the login and password values in the admin table. Returns true if there is a match
	 * @param login
	 * @param password
	 * @return 
	 */
	public boolean searchAdmin(String login, String password){
		try{
			String query = "SELECT * FROM `admin` where login like '" +login+"' and password like '" + password +"'";
			rs = st.executeQuery(query);
			//System.out.println(query);
			if(rs.next()){
				return true;
			}
			else{
				return false;
			}
			
			
			
		}catch(Exception ex){
			System.out.println(ex);
			return false;
			
		}
	}
	
	/**
	 * adds a location to the location table given by the parameter.
	 * @param name name of the location which will be created.
	 * @return
	 */
public int addlocation(String name){
		
		if(searchLocation(name)!=-1){
			return searchLocation(name);
		}
		
		try{
			String query = " INSERT INTO `databasehomework`.`location` (`LocationName`, `Capacity`, `Address`)"
			        + " values (?, '0', '')";
			 
			      // create the mysql insert preparedstatement
			      PreparedStatement preparedStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			      preparedStmt.setString (1, name);
			      preparedStmt.execute();
			      System.out.println("The team added perfectly");
			      
			      ResultSet keys = preparedStmt.getGeneratedKeys();    
			      keys.next();  
			      int lastid = keys.getInt(1);
			      System.out.println("lastid: " + lastid);
			      return lastid;
			
		}catch(Exception ex){
			System.out.println(ex);
			return -1;
		}
	}

/**
 * searchs if there is a location given by the parameter. If there is returns it's id. 
 * If not returns -1
 * @param name
 * @return
 */
	public int searchLocation(String name){
		try{
			String query = "SELECT * FROM `location` where locationname like '" +name+"'";
			rs = st.executeQuery(query);
			//System.out.println(query);
			if(rs.next()){
				int id = rs.getInt("locationid");
				return id;
			}
			else{
				return -1;
			}
			
			
			
		}catch(Exception ex){
			System.out.println(ex);
			return -1;
			
		}
	}
	
	/**
	 * Adds a new genre to the genre table.
	 * @param name
	 * @return
	 */
	public int addgenre(String name){
		
		if(searchGenre(name)!=-1){
			return searchGenre(name);
		}
		
		try{
			String query = " INSERT INTO `databasehomework`.`genre` (`genrename`)"
			        + " values (?)";
			 
			      // create the mysql insert preparedstatement
			      PreparedStatement preparedStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			      preparedStmt.setString (1, name);
			      preparedStmt.execute();
			      System.out.println("The genre added perfectly");
			      
			      ResultSet keys = preparedStmt.getGeneratedKeys();    
			      keys.next();  
			      int lastid = keys.getInt(1);
			      System.out.println("lastid: " + lastid);
			      return lastid;
			
		}catch(Exception ex){
			System.out.println(ex);
			return -1;
		}
	}
	
	/**
	 * Searchs if there is a genre which was given by the parameter.
	 * If there is, returns the id of the genre. If not returns -1.
	 * @param name
	 * @return
	 */
	public int searchGenre(String name){
		try{
			String query = "SELECT * FROM `genre` where genrename like '" +name+"'";
			rs = st.executeQuery(query);
			//System.out.println(query);
			if(rs.next()){
				int id = rs.getInt("genreid");
				return id;
			}
			else{
				return -1;
			}
			
			
			
		}catch(Exception ex){
			System.out.println(ex);
			return -1;
			
		}
	}
	/**
	 * Given by the parameter it returns football, basketball or volleyball matches by a string. 
	 * In the string there are attributes of these types of games.
	 * 
	 * @param x football:1 basketball:2 volleyball:3 should be given as parameter.
	 * @return returns sports events given by the parameter.
	 */
	public String searchsports(int x){
		try{
			String query = "SELECT eventname, price, locationname, date, t1.teamname, t2.teamname "
					+"FROM sports s "
					+"inner join team t1 on "
					+"s.hometeam = t1.teamid "
					+"inner join team t2 on "
					+"s.awayteam = t2.teamid "
					+"inner join event on "
					+"event.eventid = s.eventid "
					+"inner join location on "
					+"location.locationid = event.locationid "
					+"where genreid = '" + x + "'";
			String returnvalue = "";
			rs = st.executeQuery(query);
			System.out.println("Records from database");
			while(rs.next()){
				String id = rs.getString("eventname");
				String name = rs.getString("price");
				String point = rs.getString("locationname");
				String date = rs.getString("date");
				String home = rs.getString("t1.teamname");
				String away = rs.getString("t2.teamname");
				returnvalue = returnvalue + (id + " " + name + " " + point + " " + date + " " + home + " " + away  + "\n"); 
			}
			return returnvalue + "*";
		}catch(Exception ex){
			System.out.println(ex);
			return "";
		}
	}
	/**
	 * Searchs music events and returns their attributes in a string value.
	 * @return
	 */
	public String searchmusic(){
		try{
			String query = "select eventname, price, date, groupname, musicgenre, locationname "
					+"from music, musicgroup, event,location "
					+"where musicgroup.groupid = music.groupid "
					+"and music.eventid = event.eventid "
					+"and location.locationid = event.locationid";
			String returnvalue = "";
			rs = st.executeQuery(query);
			System.out.println("Records from database");
			while(rs.next()){
				String id = rs.getString("eventname");
				String name = rs.getString("price");
				String point = rs.getString("locationname");
				String date = rs.getString("date");
				String home = rs.getString("musicgenre");
				String away = rs.getString("groupname");
				returnvalue = returnvalue + (id + " " + name + " " + point + " " + date + " " + home + " " + away  + "\n"); 
			}
			returnvalue = returnvalue + "*";
			return returnvalue;
		}catch(Exception ex){
			System.out.println(ex);
			return "";
		}
	}
	
	/**
	 * Given by the parameter it returns cinema or theatre by a string. 
	 * In the string there are attributes of these types of performing arts like name, language, price.
	 * 
	 * @param x cinema:4 theatre:5
	 * @return
	 */
	public String searchperforming(int x){
		try{
			String query = "select eventname, price, date, personname, language "
					+"from event, performingarts, person "
					+"where event.eventid = performingarts.eventid "
					+"and performingarts.directorid = person.personid "
					+"and event.genreid = '" + x + "'";
			String returnvalue = "";
			rs = st.executeQuery(query);
			System.out.println("Records from database");
			while(rs.next()){
				String id = rs.getString("eventname");
				String name = rs.getString("price");
				String director = rs.getString("personname");
				String date = rs.getString("date");
				String language = rs.getString("language");
				returnvalue = returnvalue + (id + " " + name + " " + director + " " + date + " " + language + " \n"); 
			}
			return returnvalue + "*";
		}catch(Exception ex){
			System.out.println(ex);
			return "";
		}
	}
	/**
	 * deletes the event which were given by as a parameter.
	 * @param name
	 */
	public void deleteEvent(String name){
		try{
			String query = "delete from event where eventname = '" + name +"'";
			st.executeUpdate(query);
			System.out.println("Deleted Successfully");
		
		}catch(Exception ex){
			System.out.println(ex);
			
		}
	}
	/**
	 * updates the event by searching eventname in the event table.
	 * After that updates it's attributes.
	 * @param eventname
	 * @param price
	 * @param genre
	 * @param location
	 * @param date
	 */
	public void updateEvent(String eventname, int price,String genre, String location, String date){
		try{
			int genreid = addgenre(genre);
			int locationid = addlocation(location);

			String query = "UPDATE `databasehomework`.`event` SET `Price` = '" + price + "', `GenreID` = '"+ genreid +"', `LocationID` = '"+ locationid +"', `Date` = '" + date +"' WHERE `event`.`Eventname` = '"+eventname+"';";
			st.executeUpdate(query);
			System.out.println("Updated successfully");
		
		}catch(Exception ex){
			System.out.println(ex);
			
		}
	}
	
	public void updateEvent(String eventname, int price,String genre, String location, String date, int x){
		try{
			//x:1 sport x:2 performing x3:music
			int eventid = getEventId(eventname);
			int oldgenreid = getGenreId(eventname);
			int genreid = addgenre(genre);
			
			System.out.println("old genre id:"+oldgenreid);
			System.out.println("genre id:" +genreid);
			System.out.println("eventid:"+eventid);
			if(oldgenreid==1 || oldgenreid == 2 || oldgenreid == 3){//sport
				
				if(x == 2){
					try{
						String query = "delete from sports where eventid="+ eventid;
						st.executeUpdate(query);
						System.out.println("Deleted Successfully");
						
						
						int directorid = 666;
						query = "INSERT INTO `databasehomework`.`performingarts` (`DirectorID`, `Language`, `EventID`) VALUES (?, ?, ?);";
						 
						
							
					      // create the mysql insert preparedstatement
					      PreparedStatement preparedStmt = con.prepareStatement(query);
					      preparedStmt.setInt (1, directorid);
					      preparedStmt.setString(2, "");
					      preparedStmt.setInt(3, eventid);
					      
					      preparedStmt.execute();
					
					}catch(Exception ex){
						System.out.println(ex);
						
					}
					
				}
				else if(x == 3){
					try{
						String query = "delete from sports where eventid="+ eventid;
						st.executeUpdate(query);
						System.out.println("Deleted Successfully");
						
						int groupid = 666;
						query = "INSERT INTO `databasehomework`.`music` (`GroupID`, `EventID`) VALUES (?, ?);";
						 
						
							
					      // create the mysql insert preparedstatement
					      PreparedStatement preparedStmt = con.prepareStatement(query);
					      preparedStmt.setInt (1, groupid);
					      preparedStmt.setInt(2, eventid);
					      
					      preparedStmt.execute();
					      System.out.println("konser event added perfectly");
						
						
					
					}catch(Exception ex){
						System.out.println(ex);
						
					}
					
				}
				
			}
			else if(oldgenreid == 4 || oldgenreid == 5){//performing
				if(x==1){
					try{
						String query = "delete from performingarts where eventid="+ eventid;
						st.executeUpdate(query);
						System.out.println("Deleted Successfully");
					
						int hometeamid = 666;
						int awayteamid = 666;
						query = "INSERT INTO `databasehomework`.`sports` (`HomeTeam`, `AwayTeam`, `EventID`)"
						        + " values (?, ?, ?)";
						 
						
							
					      // create the mysql insert preparedstatement
					      PreparedStatement preparedStmt = con.prepareStatement(query);
					      preparedStmt.setInt (1, hometeamid);
					      preparedStmt.setInt(2, awayteamid);
					      preparedStmt.setInt(3, eventid);
					      
					      preparedStmt.execute();
					}catch(Exception ex){
						System.out.println(ex);
						
					}
				}
				else if(x == 3){
					String query = "delete from performingarts where eventid="+ eventid;
					st.executeUpdate(query);
					System.out.println("Deleted Successfully");
					
					int groupid = 666;
					query = "INSERT INTO `databasehomework`.`music` (`GroupID`, `EventID`) VALUES (?, ?);";
					 
					
						
				      // create the mysql insert preparedstatement
				      PreparedStatement preparedStmt = con.prepareStatement(query);
				      preparedStmt.setInt (1, groupid);
				      preparedStmt.setInt(2, eventid);
				      
				      preparedStmt.execute();
				      System.out.println("konser event added perfectly");
				}
			}
			
			else if(oldgenreid == 6 || oldgenreid == 7 || oldgenreid == 12){//music
				if(x==1){
					try{
						String query = "delete from music where eventid="+ eventid;
						st.executeUpdate(query);
						System.out.println("Deleted Successfully");
						
						int hometeamid = 666;
						int awayteamid = 666;
						query = "INSERT INTO `databasehomework`.`sports` (`HomeTeam`, `AwayTeam`, `EventID`)"
						        + " values (?, ?, ?)";
						 
						
							
					      // create the mysql insert preparedstatement
					      PreparedStatement preparedStmt = con.prepareStatement(query);
					      preparedStmt.setInt (1, hometeamid);
					      preparedStmt.setInt(2, awayteamid);
					      preparedStmt.setInt(3, eventid);
					
					}catch(Exception ex){
						System.out.println(ex);
						
					}
				}
				else if(x==2){
					String query = "delete from music where eventid="+ eventid;
					st.executeUpdate(query);
					System.out.println("Deleted Successfully");
					
					int directorid = 666;
					query = "INSERT INTO `databasehomework`.`performingarts` (`DirectorID`, `Language`, `EventID`) VALUES (?, ?, ?);";
					 
					
						
				      // create the mysql insert preparedstatement
				      PreparedStatement preparedStmt = con.prepareStatement(query);
				      preparedStmt.setInt (1, directorid);
				      preparedStmt.setString(2, "");
				      preparedStmt.setInt(3, eventid);
				      
				      preparedStmt.execute();
					
				}
			}
			else{
				updateEvent(eventname, price, genre, location, date);
			}
			int locationid = addlocation(location);

			String query = "UPDATE `databasehomework`.`event` SET `Price` = '" + price + "', `GenreID` = '"+ genreid +"', `LocationID` = '"+ locationid +"', `Date` = '" + date +"' WHERE `event`.`Eventname` = '"+eventname+"';";
			st.executeUpdate(query);
			System.out.println("Updated successfully");
		
		}catch(Exception ex){
			System.out.println(ex);
			
		}
	}
	
	/**
	 * first adds an event by the given name and by it's eventid, creates a sport match(football, basketball, volleyball) in the sports table.
	 * @param eventname
	 * @param price
	 * @param genre
	 * @param location
	 * @param date
	 */
	public void addSport(String eventname, int price,String genre, String location, String date){
		try{
			//666 named id has empty attributes. Because it is a foreign key it cannot be empty. It can be altered in the database
			//to add correct values
			int hometeamid = 666;
			int awayteamid = 666;
			String query = "INSERT INTO `databasehomework`.`sports` (`HomeTeam`, `AwayTeam`, `EventID`)"
			        + " values (?, ?, ?)";
			 
			int lastid = addEvent(eventname, price, genre, location, date);
				
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setInt (1, hometeamid);
		      preparedStmt.setInt(2, awayteamid);
		      preparedStmt.setInt(3, lastid);
		      
		      preparedStmt.execute();
		      System.out.println("Sport event added perfectly");
		
		}catch(Exception ex){
			System.out.println(ex);
		}
	}
	
	/**
	 * first adds an event by the given name and by it's eventid, creates a music(concert etc.) in the music table.
	 * @param eventname
	 * @param price
	 * @param genre
	 * @param location
	 * @param date
	 */
	public void addMusic(String eventname, int price,String genre, String location, String date){
		try{
			//666 named id has empty attributes. Because it is a foreign key it cannot be empty. It can be altered in the database
			//to add correct values
			int groupid = 666;
			String query = "INSERT INTO `databasehomework`.`music` (`GroupID`, `EventID`) VALUES (?, ?);";
			 
			int lastid = addEvent(eventname, price, genre, location, date);
				
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setInt (1, groupid);
		      preparedStmt.setInt(2, lastid);
		      
		      preparedStmt.execute();
		      System.out.println("konser event added perfectly");
		
		}catch(Exception ex){
			System.out.println(ex);
		}
	}
	
	/**
	 * first adds an event by the given name and by it's eventid, creates a performing arts(cinema, theatre etc.) in the music table.
	 * @param eventname
	 * @param price
	 * @param genre
	 * @param location
	 * @param date
	 */
	public void addPerforming(String eventname, int price,String genre, String location, String date){
		try{
			//666 named id has empty attributes. Because it is a foreign key it cannot be empty. It can be altered in the database
			//to add correct values
			int directorid = 666;
			String query = "INSERT INTO `databasehomework`.`performingarts` (`DirectorID`, `Language`, `EventID`) VALUES (?, ?, ?);";
			 
			int lastid = addEvent(eventname, price, genre, location, date);
				
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setInt (1, directorid);
		      preparedStmt.setString(2, "");
		      preparedStmt.setInt(3, lastid);
		      
		      preparedStmt.execute();
		      System.out.println("Performing event added perfectly");
		
		}catch(Exception ex){
			System.out.println(ex);
		}
	}
}

