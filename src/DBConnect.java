import java.sql.*;

public class DBConnect {

	private static Connection connection =  null;
	
	public static Connection getConnection() throws Exception {
		String url = "jdbc:mysql://localhost:8080/ATOM";
		String user="root";
		String password="123456";
		
		
		connection = DriverManager.getConnection(url,
				                                 user,
				                                 password);
		
		connection.setAutoCommit(true);
		
		return connection;
		
		} //else
		
	} //Connection
	