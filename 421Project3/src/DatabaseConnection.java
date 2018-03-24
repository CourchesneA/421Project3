
import java.awt.List;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JOptionPane;
public class DatabaseConnection{
	private static String uri = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
	private static String user = "cs421g31";
	private static String pw = "g31groupproject";
	
	public DatabaseConnection() throws ClassNotFoundException{
		Class.forName("org.postgresql.Driver");
	}
	
	public static void registerPlayer(String gamertag, Date dob, String teamname) {
		
		String dialog = "Unexpected error";
		Connection connection = null;
		PreparedStatement stmt = null;
		int msgType = JOptionPane.INFORMATION_MESSAGE;
		
		try {
			connection = DriverManager.getConnection(uri,user,pw);
			
			stmt = connection.prepareStatement( "INSERT INTO players (gamertag, dob, teamname) VALUES (?,?,?)");
			stmt.setString(1, gamertag);
			stmt.setDate(2, dob);
			stmt.setString(3, teamname);
			int result = stmt.executeUpdate();
			
			if(result == 1) {
				dialog = "Successfully created user";
			}else {
				dialog = "Error, Query succeeded but no users were modified in the database";
				msgType = JOptionPane.WARNING_MESSAGE;
			}
			
			connection.close();
			stmt.close();
		}catch(SQLException se) {
			msgType = JOptionPane.ERROR_MESSAGE;
			dialog = "SQL Error: "+se.getMessage();
			System.out.println("Error msg: "+se.getMessage());
			System.out.println("State: "+se.getSQLState());


		}catch(Exception e) {
			System.out.println("Unexpected error occured: ");
			e.printStackTrace();
			
		}finally {
			 try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(connection!=null)
		            connection.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		      Application.mainframe.setEnabled(true);
		      JOptionPane.showMessageDialog(Application.mainframe, dialog, "Registration result", msgType );
		}
		
	}
	
	/**
	 * @deprecated TEST query
	 */
	public static void listPlayers() {
		
		String dialog = "Unexpected error";
		Connection connection = null;
		Statement stmt = null;
		int msgType = JOptionPane.INFORMATION_MESSAGE;
		
		try {
			connection = DriverManager.getConnection(uri,user,pw);
			
			stmt = connection.createStatement();
			
			//TEST query
			String sql = "SELECT * FROM players";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String gt = rs.getString("gamertag");
				String db = rs.getString("dob");
				String score = rs.getString("playerscore");
				String tn = rs.getString("teamname");
				
				 System.out.print("Gamertag: " + gt);
		         System.out.print(", Date of birth: " + db);
		         System.out.print(", Score: " + score);
		         System.out.println(", Team name " + tn);
		         if(rs.getRow() > 10) break;
			}
			
			rs.close();
			connection.close();
			stmt.close();
			dialog = "Success! ";
		}catch(SQLException se) {
			msgType = JOptionPane.ERROR_MESSAGE;
			dialog = "SQL Error: "+se.getMessage();
		}catch(Exception e) {
			System.out.println("Unexpected error occured: ");
			e.printStackTrace();
			
		}finally {
			 try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(connection!=null)
		            connection.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		      Application.mainframe.setEnabled(true);
		      JOptionPane.showMessageDialog(Application.mainframe, dialog, "Registration result", msgType );
		}
		
	}

	public static void announceWinner(int matchID, String teamname) {

		String dialog = "Unexpected error";
		Connection connection = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		final int matchPointValue=100;
		int msgType = JOptionPane.INFORMATION_MESSAGE;
		
		try {
			connection = DriverManager.getConnection(uri,user,pw);
			
			// Statement 1: Update match.winner
			stmt1 = connection.prepareStatement("UPDATE matches SET winner=? WHERE matchid=? AND winner IS DISTINCT FROM ?");
			stmt1.setString(1, teamname);
			stmt1.setInt(2, matchID);
			stmt1.setString(3, teamname);
			stmt2 = connection.prepareStatement("UPDATE teams SET teamscore=teamscore+? WHERE teamname=?");
			stmt2.setInt(1, matchPointValue);
			stmt2.setString(2, teamname);
			int result = stmt1.executeUpdate();
			int result2 = -1;
			
			if(result ==1) {
				//Match was updated, also update the score of the team
				System.out.println("Q1 succeeded");
				result2 = stmt2.executeUpdate();
				System.out.println("nb of result: "+result2);
				if(result2 == 1) {
					dialog = "Successfully modified match winner and team score";
				}else {
					dialog = "Successfully modified match winner but no team score was updated";
				}
			}else {
				dialog = "Error, Query succeeded but match was modified in the database";
				msgType = JOptionPane.WARNING_MESSAGE;
			}
			
			connection.close();
			stmt1.close();
			stmt2.close();
		}catch(SQLException se) {
			msgType = JOptionPane.ERROR_MESSAGE;
			dialog = "SQL Error: "+se.getMessage();
			System.out.println("Error msg: "+se.getMessage());
			System.out.println("State: "+se.getSQLState());


		}catch(Exception e) {
			System.out.println("Unexpected error occured: ");
			e.printStackTrace();
			
		}finally {
			 try{
		         if(stmt1!=null)
		            stmt1.close();
		         if(stmt2!=null)
		        	 stmt2.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(connection!=null)
		            connection.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		      Application.mainframe.setEnabled(true);
		      JOptionPane.showMessageDialog(Application.mainframe, dialog, "Registration result", msgType );
		}
	}
	

	public static void disqualifyTeam(String teamname) {
		String dialog = "Unexpected error";
		Connection connection = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		int msgType = JOptionPane.INFORMATION_MESSAGE;
		
		try {
			connection = DriverManager.getConnection(uri,user,pw);
			
			//Get the game that the team plays and get a replacement team
			//Replace the team for all matches
			
			// Statement 1: Find a new team to replace them (The team must play the same game)
			stmt1 = connection.prepareStatement("SELECT teamname FROM teams WHERE gamename=(SELECT gamename FROM teams WHERE teamname=? LIMIT(1)) AND teamname IS DISTINCT FROM ? ORDER BY RANDOM() LIMIT(1)");
			stmt1.setString(1, teamname);
			stmt1.setString(2, teamname);
			
			ResultSet rs = stmt1.executeQuery();	
			int result2 = -1;
			
			if(rs.next()) {
				//Replacement team found
				String replacementTeam = rs.getString("teamname");
				stmt2 = connection.prepareStatement("UPDATE participation SET teamname=? WHERE teamname=?");
				stmt2.setString(1, replacementTeam);
				stmt2.setString(2, teamname);
				result2 = stmt2.executeUpdate();
				dialog = "The team was replaced by '"+replacementTeam+"' in "+result2+" matches";
			}else {
				//No replacement team found
				stmt2 = connection.prepareStatement("DELETE FROM participation WHERE teamname=?");
				stmt2.setString(1, teamname);
				result2 = stmt2.executeUpdate();
				if(result2 <=0) {
					dialog = "No team were available to substitute but no matcch participation were found";
				}else {
					dialog = "No team were available to substitute but "+result2+" matches were cancelled";
				}
				msgType = JOptionPane.WARNING_MESSAGE;
			}
			
			connection.close();
			stmt1.close();
			stmt2.close();
		}catch(SQLException se) {
			msgType = JOptionPane.ERROR_MESSAGE;
			dialog = "SQL Error: "+se.getMessage();
			System.out.println("Error msg: "+se.getMessage());
			System.out.println("State: "+se.getSQLState());


		}catch(Exception e) {
			System.out.println("Unexpected error occured: ");
			e.printStackTrace();
			
		}finally {
			 try{
		         if(stmt1!=null)
		            stmt1.close();
		         if(stmt2!=null)
		        	 stmt2.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(connection!=null)
		            connection.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		      Application.mainframe.setEnabled(true);
		      JOptionPane.showMessageDialog(Application.mainframe, dialog, "Registration result", msgType );
		}
		
	}
	
	/**
	 * Update the venue of a given match in the database
	 * @param matchID
	 * @param startDate
	 * @param expDuration
	 * @param roomnb
	 * @param buildingname
	 */
	public static void updateVenue(int matchID, Date startDate, int expDuration, int roomnb, String buildingname) {
		String dialog = "Unexpected error";
		Connection connection = null;
		PreparedStatement stmt1 = null;
		int msgType = JOptionPane.INFORMATION_MESSAGE;
		
		try {
			connection = DriverManager.getConnection(uri,user,pw);
			
			// Statement 1: Update match.winner
			stmt1 = connection.prepareStatement("UPDATE matches SET start=?, roomnb=?, bname=?, expdur=? WHERE matchid=?");
			stmt1.setDate(1, startDate);
			stmt1.setInt(2, roomnb);
			stmt1.setString(3, buildingname);
			stmt1.setInt(4, expDuration);
			stmt1.setInt(5, matchID);
			int result = stmt1.executeUpdate();
			
			if(result ==1) {
				//Match was updated, also update the score of the team
				System.out.println("Q1 succeeded");
				
				dialog = "Successfully modified match venue";
			}else {
				dialog = "Error, Query succeeded but no match was modified in the database";
				msgType = JOptionPane.WARNING_MESSAGE;
			}
			
			connection.close();
			stmt1.close();
		}catch(SQLException se) {
			msgType = JOptionPane.ERROR_MESSAGE;
			dialog = "SQL Error: "+se.getMessage();
			System.out.println("Error msg: "+se.getMessage());
			System.out.println("State: "+se.getSQLState());


		}catch(Exception e) {
			System.out.println("Unexpected error occured: ");
			e.printStackTrace();
			
		}finally {
			 try{
		         if(stmt1!=null)
		            stmt1.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(connection!=null)
		            connection.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		      Application.mainframe.setEnabled(true);
		      JOptionPane.showMessageDialog(Application.mainframe, dialog, "Registration result", msgType );
		}
		
	}
	
	public static void getTournamentPlayers(String tournamentname){
		
		JList<String> players;
		String dialog = "Unexpected error";
		Connection connection = null;
		PreparedStatement stmt = null;
		int msgType = JOptionPane.INFORMATION_MESSAGE;
		
		try {
			connection = DriverManager.getConnection(uri,user,pw);
			
			
			//TEST query
			String sql = "SELECT DISTINCT gamertag FROM players,participation WHERE participation.teamname=players.teamname AND participation.matchid IN (SELECT matches.matchid FROM matches,tournaments WHERE tournaments.tournamentname=matches.tournamentname AND tournaments.tournamentname=?)";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, tournamentname);

			ResultSet rs = stmt.executeQuery();
			
			ArrayList<String> al = new ArrayList<String>();
			while(rs.next()) {
				String gt = rs.getString("gamertag");
				al.add(gt);
				
		        if(rs.getRow() > 100) break;	//Set max to 100 to prevent overflow
			}
			players = new JList<String>(al.toArray(new String[al.size()]));
			
			rs.close();
			connection.close();
			stmt.close();
			if(!al.isEmpty()) {
				ListDisplayer display = new ListDisplayer(players);
				display.setVisible(true);
				return;
			}else {
				dialog="No player registered in this tournament";
				msgType = JOptionPane.WARNING_MESSAGE;
			}
			
		}catch(SQLException se) {
			msgType = JOptionPane.ERROR_MESSAGE;
			dialog = "SQL Error: "+se.getMessage();
		}catch(Exception e) {
			System.out.println("Unexpected error occured: ");
			e.printStackTrace();
			
		}finally {
			 try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(connection!=null)
		            connection.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		      Application.mainframe.setEnabled(true);
		}
	    JOptionPane.showMessageDialog(Application.mainframe, dialog, "Registration result", msgType );
		
	}
	
	public static String getRandomTeam() {
		
		Connection connection = null;
		PreparedStatement stmt1 = null;
		
		try {
			connection = DriverManager.getConnection(uri,user,pw);
			
			stmt1 = connection.prepareStatement("SELECT teamname FROM teams ORDER BY RANDOM() LIMIT(1)");
			ResultSet rs = stmt1.executeQuery();
			
			String teamname = null;
			rs.next();
			if(rs != null) {
				teamname = rs.getString("teamname");
			}
			
			if(teamname != null) {
				return teamname;
			}
			
			connection.close();
			stmt1.close();
		}catch(SQLException se) {
			System.out.println("Error msg: "+se.getMessage());
			System.out.println("State: "+se.getSQLState());


		}catch(Exception e) {
			System.out.println("Unexpected error occured: ");
			e.printStackTrace();
			
		}finally {
			 try{
		         if(stmt1!=null)
		            stmt1.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(connection!=null)
		            connection.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		}
		JOptionPane.showMessageDialog(Application.mainframe, "Unable to query database for a random tournament name", "Query error", JOptionPane.ERROR_MESSAGE );
		return null;
	}
	
	public static String getRandomTournament() {
		
		Connection connection = null;
		PreparedStatement stmt1 = null;
		
		try {
			connection = DriverManager.getConnection(uri,user,pw);
			
			stmt1 = connection.prepareStatement("SELECT tournamentname FROM tournaments ORDER BY RANDOM() LIMIT(1)");
			ResultSet rs = stmt1.executeQuery();
			
			String tournamentname = null;
			rs.next();
			if(rs != null) {
				tournamentname = rs.getString("tournamentname");
			}
			
			if(tournamentname != null) {
				return tournamentname;
			}
			
			connection.close();
			stmt1.close();
		}catch(SQLException se) {
			System.out.println("Error msg: "+se.getMessage());
			System.out.println("State: "+se.getSQLState());


		}catch(Exception e) {
			System.out.println("Unexpected error occured: ");
			e.printStackTrace();
			
		}finally {
			 try{
		         if(stmt1!=null)
		            stmt1.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(connection!=null)
		            connection.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		}
		JOptionPane.showMessageDialog(Application.mainframe, "Unable to query database for a random tournament name", "Query error", JOptionPane.ERROR_MESSAGE );
		return null;
	}
	
	public static String getRandomBuilding() {
		
		Connection connection = null;
		PreparedStatement stmt1 = null;
		
		try {
			connection = DriverManager.getConnection(uri,user,pw);
			
			stmt1 = connection.prepareStatement("SELECT buildingname FROM buildings ORDER BY RANDOM() LIMIT(1)");
			ResultSet rs = stmt1.executeQuery();
			
			String buildingname = null;
			rs.next();
			if(rs != null) {
				buildingname = rs.getString("buildingname");
			}
			
			if(buildingname != null) {
				return buildingname;
			}
			
			connection.close();
			stmt1.close();
		}catch(SQLException se) {
			System.out.println("Error msg: "+se.getMessage());
			System.out.println("State: "+se.getSQLState());


		}catch(Exception e) {
			System.out.println("Unexpected error occured: ");
			e.printStackTrace();
			
		}finally {
			 try{
		         if(stmt1!=null)
		            stmt1.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(connection!=null)
		            connection.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		}
		JOptionPane.showMessageDialog(Application.mainframe, "Unable to query database for a random building name", "Query error", JOptionPane.ERROR_MESSAGE );
		return null;
	}



	
	
}