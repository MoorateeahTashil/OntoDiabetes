package OntoDiabetes;

public class DatabaseConnection {

	public DatabaseConnection() {

	}
	
	
	public String getUrlConnection() {
		return "jdbc:sqlserver://DESKTOP-V30A0OF\\SQLEXPRESS;databaseName=OntoDiabetes;";
	}
	
	

	public String getUser() {
		return "sa";
	}
	
	
	public String getPassword() {
		return "Password001!";
	}
}
