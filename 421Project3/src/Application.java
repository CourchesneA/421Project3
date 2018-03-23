import javax.swing.JFrame;

public class Application {
	public static JFrame mainframe;

	public static void main(String[] args) {
		
		try {
			new DatabaseConnection();
		} catch (ClassNotFoundException e) {
			System.out.println("Error, unable to access JDBC driver");
			System.exit(-1);
		}
		mainframe= new MainFrame();
		mainframe.setVisible(true);
		
	}
}
