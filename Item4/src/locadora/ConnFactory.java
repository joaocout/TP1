package locadora;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnFactory {

	public Connection getConn() {
		try {
			String url = "jdbc:mysql://localhost:3306/tp1";
			String user = "joao";
			String pwd = "12345";
			return DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
