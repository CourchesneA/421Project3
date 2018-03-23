
import java.sql.*;

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
	
	
}