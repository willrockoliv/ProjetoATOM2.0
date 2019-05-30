import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {

	private static Connection connection =  null;
	
	public static Connection getConnection() throws Exception {
		
		if (connection != null)
			return connection;
		else  {
		
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/FTT";
			String user="scott";
			String password="tiger";
			
			Class.forName(driver); //Opcional
			connection = DriverManager.getConnection(url,
					                                 user,
					                                 password);
			
			connection.setAutoCommit(true);
			
			return connection;
		} //else
		
	} //Connection
	
}