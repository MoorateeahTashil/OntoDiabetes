package OntoDiabetes;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static Connection con;
	public static String errorMessage;

	/**
	 * Default constructor.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Login() {
		errorMessage = "";
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("emailTextBox");
		String password = request.getParameter("passwordTextBox");
		try {
			if (ValidateData(username, password)) {
				
				updateLoginDate(username);
				String userID = getUserId(username);
				HttpSession session = request.getSession(false);
				if (session != null) {
				    // a session exists
					session.setAttribute("userID", userID);

				} else {
				    // no session
				}
				RequestDispatcher req = request.getRequestDispatcher(userID);
				req.forward(request, response);

			} else {
				request.setAttribute("errorMessage", errorMessage);
				RequestDispatcher req = request.getRequestDispatcher("login.jsp");
				req.include(request, response);
			}
		} catch (ClassNotFoundException | SQLException | ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getUserId(String email) throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables
		final String url = "jdbc:sqlserver://DESKTOP-V30A0OF\\SQLEXPRESS;databaseName=OntoDiabetes;";
		final String user = "sa";
		final String password = "Password001!";
		String userID = "";

		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = "select [id] from [OntoDiabetes_User] where [email]='" + email + "';";

			// send and execute SQL query in Database software
			ResultSet rs = st.executeQuery(query);

			// process the ResultSet object
			while (rs.next()) {
				userID = rs.getString(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();
		return userID;

	}

	private Boolean ValidateData(String email, String Password) throws ClassNotFoundException, SQLException {
		if (email == null || email == "") {
			errorMessage += "Email cannot be empty <br/>";
		}

		if (Password == null || Password == "") {
			errorMessage += "Password cannot be empty <br/>";

		}

		if (!(email == null || email == "") && !(Password == null || Password == "")) {

			if (!emailExist(email)) {
				errorMessage += "Email does not exist <br/>";
			} else {

				if (!validatePassword(email, Password)) {
					errorMessage = "Incorrect combination of username and password <br/>";
					updateNumberOfAttemps(email);
					updateAccountLocked(email);

					if (isAccountLocked(email)) {
						errorMessage = "Account is locked. Please contact system adminstrators <br/>";
					} else {
						errorMessage += "Number of attempts left to login : <b>"
								+ String.valueOf((3 - getNumberofAttempts(email))) + "</b> <br/>";

					}
				}
			}

		}

		if (errorMessage == null || errorMessage == "") {
			return true;
		} else {
			return false;
		}
	}

	// DATABASE QUERIES

	public Boolean isAccountLocked(String email) throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables
		final String url = "jdbc:sqlserver://DESKTOP-V30A0OF\\SQLEXPRESS;databaseName=OntoDiabetes;";
		final String user = "sa";
		final String password = "Password001!";
		String accountLocked = "";
		Boolean accountStatus = false;
		// establish the connection
		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = "select [account_locked] from [OntoDiabetes_User] where [email]='" + email + "';";

			// send and execute SQL query in Database software
			ResultSet rs = st.executeQuery(query);

			// process the ResultSet object
			while (rs.next()) {
				accountLocked = rs.getString(1);
			}

			if (accountLocked.equals("No")) {
				accountStatus = false;
			} else {
				accountStatus = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();
		return accountStatus;
	}

	public Boolean emailExist(String email) throws ClassNotFoundException, SQLException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables
		final String url = "jdbc:sqlserver://DESKTOP-V30A0OF\\SQLEXPRESS;databaseName=OntoDiabetes;";
		final String user = "sa";
		final String password = "Password001!";
		String emailDb = "";
		Boolean emailExistance = false;
		// establish the connection
		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = "select [email] from [OntoDiabetes_User] where [email]='" + email + "';";

			// send and execute SQL query in Database software
			ResultSet rs = st.executeQuery(query);

			// process the ResultSet object
			while (rs.next()) {
				emailDb = rs.getString(1);
			}

			if (emailDb == null || emailDb == "") {
				emailExistance = false;
			} else {
				emailExistance = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();
		return emailExistance;

	}

	public Boolean validatePassword(String email, String webPassword) throws ClassNotFoundException, SQLException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables
		final String url = "jdbc:sqlserver://DESKTOP-V30A0OF\\SQLEXPRESS;databaseName=OntoDiabetes;";
		final String user = "sa";
		final String password = "Password001!";
		Boolean passwordValidator = false;

		// establish the connection
		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = "select [password] from [OntoDiabetes_User] where [email]='" + email + "';";

			String passwordDb = "";
			// send and execute SQL query in Database software
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				passwordDb = rs.getString(1);
			}

			if (passwordDb.equals(CreateMD5(webPassword))) {
				passwordValidator = true;
			} else {
				passwordValidator = false;
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}

		con.close();
		return passwordValidator;

	}

	public int getNumberofAttempts(String email) throws ClassNotFoundException, SQLException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables
		final String url = "jdbc:sqlserver://DESKTOP-V30A0OF\\SQLEXPRESS;databaseName=OntoDiabetes;";
		final String user = "sa";
		final String password = "Password001!";
		int NumberofAttempts = 0;

		// establish the connection
		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = "select [number_of_attempts] from [OntoDiabetes_User] where [email]='" + email + "';";

			// send and execute SQL query in Database software
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				NumberofAttempts = rs.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}

		con.close();
		return NumberofAttempts;

	}

	public void updateNumberOfAttemps(String email) throws SQLException, ClassNotFoundException {
		int numberofAttempts = getNumberofAttempts(email) + 1;
		if (numberofAttempts >= 3) {
			numberofAttempts = 3;
		}
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables
		final String url = "jdbc:sqlserver://DESKTOP-V30A0OF\\SQLEXPRESS;databaseName=OntoDiabetes;";
		final String user = "sa";
		final String password = "Password001!";

		// establish the connection
		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = "update [OntoDiabetes_User] set [number_of_attempts] = " + numberofAttempts
					+ " where [email]='" + email + "';";

			// send and execute SQL query in Database software
			st.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}

	}

	public void updateAccountLocked(String email) throws SQLException, ClassNotFoundException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables
		final String url = "jdbc:sqlserver://DESKTOP-V30A0OF\\SQLEXPRESS;databaseName=OntoDiabetes;";
		final String user = "sa";
		final String password = "Password001!";

		// establish the connection
		con = DriverManager.getConnection(url, user, password);
		try {
			int numberofAttempts = getNumberofAttempts(email);
			if (numberofAttempts >= 3) {
				Statement st = con.createStatement();

				String query = "update [OntoDiabetes_User] set [account_locked] = 'Yes' where [email]='" + email + "';";

				// send and execute SQL query in Database software
				st.executeUpdate(query);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}

	}

	public void updateLoginDate(String email) throws SQLException, ClassNotFoundException {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables
		final String url = "jdbc:sqlserver://DESKTOP-V30A0OF\\SQLEXPRESS;databaseName=OntoDiabetes;";
		final String user = "sa";
		final String password = "Password001!";

		// establish the connection
		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = "update [OntoDiabetes_User] set [date_last_login] = '" + formatter.format(date)
					+ "' where [email]='" + email + "';";

			// send and execute SQL query in Database software
			st.executeQuery(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}

	}

	public static String CreateMD5(String message) {
		String md5 = "";
		if (null == message)
			return null;
		String salt = "DGE$5SGr@3VsPDDSORN23E4d57vfBfFSTRU@!DSH(*%FDSdfg13sgfsg";
		message = message + salt;// adding a salt to the string before it gets hashed.
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");// Create MessageDigest object for MD5
			digest.update(message.getBytes(), 0, message.length());// Update input string in message digest
			md5 = new BigInteger(1, digest.digest()).toString(16);// Converts message digest value in base 16 (hex)

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}

}
