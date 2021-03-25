package OntoDiabetes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {
	public static Connection con;
	public static String email = "patient@gmail.com";
	
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables
		final String url = "jdbc:sqlserver://DESKTOP-V30A0OF\\SQLEXPRESS;databaseName=OntoDiabetes;";
		final String user = "sa";
		final String password = "Password001!";
		String type = "";

		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = "select [type] from [OntoDiabetes_User] where [email]='" + email + "';";

			// send and execute SQL query in Database software
			ResultSet rs = st.executeQuery(query);

			// process the ResultSet object
			while (rs.next()) {
				type = rs.getString(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();
		System.out.println(type);

	}

}
