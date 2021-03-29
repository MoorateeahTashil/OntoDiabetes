package OntoDiabetes;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class doctorDetils
 */
@WebServlet("/doctorDetails")
public class doctorDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DatabaseConnection db = new DatabaseConnection();
	
	public final String url = db.getUrlConnection();
	public final String user = db.getUser();
	public final String password = db.getPassword();;

	public static Connection con;
	public static String errorMessage;

	/**
	 * Default constructor.
	 */
	public doctorDetails() {
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
		String surname = "";
		String middlename = "";
		String forename = "";
		String other = "";
		String mobilenumber = "";
		String homenumber = "";
		String worknumber = "";
		String reg = "";
		String prof = "";
		String dob = "";

		if (request.getParameter("SurnameTextBox") != null) {
			surname = request.getParameter("SurnameTextBox");
		}

		
		if (request.getParameter("dobTextBox") != null) {
			dob = request.getParameter("dobTextBox");
		}
		
		
		if (request.getParameter("middlenameTextBox") != null) {
			middlename = request.getParameter("middlenameTextBox");
		}

		if (request.getParameter("forenameTextBox") != null) {
			forename = request.getParameter("forenameTextBox");
		}

		if (request.getParameter("reg") != null || request.getParameter("reg") != "") {
			reg = request.getParameter("reg");
		}

		if (request.getParameter("prof") != null || request.getParameter("prof") != "") {
			prof = request.getParameter("prof");
		}

		if (request.getParameter("mobilenumber") != null || request.getParameter("mobilenumber") != "") {
			mobilenumber = request.getParameter("mobilenumber");
		}

		if (request.getParameter("homenumber") != null || request.getParameter("homenumber") != "") {
			homenumber = request.getParameter("homenumber");
		}

		if (request.getParameter("worknumber") != null || request.getParameter("worknumber") != "") {
			worknumber = request.getParameter("worknumber");
		}

		if (request.getParameter("Others") != null) {
			other = request.getParameter("Others");
		}

		HttpSession session = request.getSession(false);
		String userID = (String) session.getAttribute("userID");
		try {
			if (ValidateData(reg, userID)) {
		
					saveDoctor(userID, surname, middlename, forename, other, mobilenumber, homenumber, worknumber,reg, prof,dob);
			

					response.sendRedirect("dashboardDoc.jsp");

			} else {

				errorMessage = errorMessage.replace("null", "");
				request.setAttribute("errorMessage", errorMessage);

				RequestDispatcher req = request.getRequestDispatcher("doctorDetails.jsp");
				req.include(request, response);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean ValidateData(String reg, String userId) throws ClassNotFoundException, SQLException {

		if (checkNic(reg)) {
			errorMessage += "Doctor with Registration Number already exist <br/>";
		}

		if (checkUser(userId)) {
			errorMessage += "Doctor already exist <br/>";
		}

		if (errorMessage == null || errorMessage == "") {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkNic(String reg) throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables

		String dbNic = "";
		Boolean present = false;
		// establish the connection
		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = "select [registrationnumber] from [OntoDiabetes_DoctorDetails] where [registrationnumber]='"
					+ reg + "';";

			// send and execute SQL query in Database software
			ResultSet rs = st.executeQuery(query);

			// process the ResultSet object
			while (rs.next()) {
				dbNic = rs.getString(1);
			}

			if (dbNic == null || dbNic == "") {
				present = false;
			} else {
				present = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();
		return present;
	}

	public boolean checkUser(String userId) throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables

		String dbUserId = "";
		Boolean present = false;
		// establish the connection
		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = "select [userid] from [OntoDiabetes_DoctorDetails] where [userid]='" + userId + "';";

			// send and execute SQL query in Database software
			ResultSet rs = st.executeQuery(query);

			// process the ResultSet object
			while (rs.next()) {
				dbUserId = rs.getString(1);
			}

			if (dbUserId == null || dbUserId == "") {
				present = false;
			} else {
				present = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();
		return present;
	}

	public void saveDoctor(String userid, String surname, String middlename, String lastname,String other, String phonenumber,
			String homenumber, String worknumber, String reg, String prof,String pracfrom) throws ClassNotFoundException,
			SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables

		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();
			String query = " INSERT INTO [OntoDiabetes_DoctorDetails]([userid]" + "      ,[registrationnumber]"
					+ "      ,[surname]" + "      ,[middlename]" + "      ,[lastname]" + "      ,[phonenumber]"
					+ "      ,[homenumber]" + "      ,[worknumber]" + "      ,[praticingfrom]"
					+ "      ,[professionalstatement]" + "      ,[other]) VALUES('" + userid + "','" + reg + "','" + surname + "','"
					+ middlename + "','" + lastname + "','"  + phonenumber + "','" + homenumber + "','"
					+ worknumber + "','" + pracfrom + "','" + prof + "','" + other + "')";

			log(query);
			// send and execute SQL query in Database software
			st.execute(query);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();

	}
}
