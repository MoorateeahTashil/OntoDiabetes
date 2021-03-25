package OntoDiabetes;

import java.io.IOException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static Connection con;
	public static String errorMessage;

	/**
	 * Default constructor.
	 */
	public Register() {
		// TODO Auto-generated constructor stub
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
		String password = request.getParameter("repeatPasswordTextBox");
		String repeatPassword = request.getParameter("passwordTextBox");
		String Type = request.getParameter("switch");
		String specialDocCode = request.getParameter("txtSpecialDocCode");

		errorMessage = "";
		try {
			if (ValidateData(username, password, Type, repeatPassword, specialDocCode)) {

				createUser(username, password, Type);

				updateLoginDate(username);
				String userID = getUserId(username);
				String userType = getType(username);			

				HttpSession session = request.getSession(false);
				if (session != null) {
					// a session exists
					session.setAttribute("userID", userID);

				} else {
					// no session
				}
				if (userType.toLowerCase().equals("patient")) {
					response.sendRedirect("patientDetails.jsp");
				}

			} else {

				errorMessage = errorMessage.replace("null", "");
				request.setAttribute("errorMessage", errorMessage);
				RequestDispatcher req = request.getRequestDispatcher("register.jsp");
				req.include(request, response);
			}
		} catch (ClassNotFoundException | SQLException | ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean ValidateData(String email, String Password, String type, String repeatPassword,
			String specialDocCode) throws ClassNotFoundException, SQLException {

		if (email == null || email == "") {
			errorMessage += "Email cannot be empty <br/>";
		}

		if (Password == null || Password == "") {
			errorMessage += "Password cannot be empty <br/>";

		}

		if (type == null || type == "") {
			errorMessage += "Type cannot be empty <br/>";
		}

		if (repeatPassword == null || repeatPassword == "") {
			errorMessage += "Repeat Password cannot be empty <br/>";
		}

		if (!(email == null || email == "") && !(Password == null || Password == "")
				&& !(repeatPassword == null || repeatPassword == "")) {
			String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&!+=])" + "(?=\\S+$).{8,20}$";

			// Compile the ReGex
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(Password);

			boolean b = m.matches();

			if (!b) {
				errorMessage += "Password should be in the following format <br/>";
				errorMessage += "<ul>";
				errorMessage += "<li>";
				errorMessage += "Password should be at least 8 characters";
				errorMessage += "</li>";
				errorMessage += "<li>";
				errorMessage += "Password should have at least 2 alphabets";
				errorMessage += "</li>";
				errorMessage += "<li>";
				errorMessage += "Password should have at least 2 numbers";
				errorMessage += "</li>";
				errorMessage += "<li>";
				errorMessage += "Password should have at least 1 Uppercase";
				errorMessage += "</li>";
				errorMessage += "<li>";
				errorMessage += "Password should have at least 1 Lowercase";
				errorMessage += "</li>";
				errorMessage += "<li>";
				errorMessage += "Password should have at least 1 symbol";
				errorMessage += "</li>";
				errorMessage += "</ul>";
			} else {
				if (!Password.equals(repeatPassword)) {
					errorMessage += "Password is not the same as Repeat Password <br/>";
				}

				if (emailExist(email)) {
					errorMessage += "Email address is already been used <br/>";
				}
			}

		}

		if (type.equals("Doctor")) {
			if (specialDocCode == null || specialDocCode == "") {
				errorMessage += "Special Doctor Code cannot be empty <br/>";

			}

			else {
				if (!specialDocCode.equals("OntoDiabetes")) {
					errorMessage += "Special Doctor Code is not correct <br/>";

				}

			}
		}

		if (errorMessage == null || errorMessage == "") {
			return true;
		} else {
			return false;
		}
	}

	public void createUser(String email, String webPassword, String type) throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables
		final String url = "jdbc:sqlserver://DESKTOP-V30A0OF\\SQLEXPRESS;databaseName=OntoDiabetes;";
		final String user = "sa";
		final String password = "Password001!";

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = " INSERT INTO OntoDiabetes_User([email] ,[password] ,[account_locked] ,[number_of_attempts] ,[date_last_login] ,[type] ,[date_created]) VALUES('"
					+ email + "','" + CreateMD5(webPassword) + "','No','0','" + formatter.format(date) + "','" + type
					+ "','" + formatter.format(date) + "')";

			// send and execute SQL query in Database software
			st.execute(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();

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

	public String getType(String email) throws ClassNotFoundException, SQLException {
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
		return type;

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
