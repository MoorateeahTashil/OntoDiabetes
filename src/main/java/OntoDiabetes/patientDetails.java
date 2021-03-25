package OntoDiabetes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * /** Servlet implementation class patientDetails
 */
@WebServlet("/patientDetails")
public class patientDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static Connection con;
	public static String errorMessage;

	/**
	 * Default constructor.
	 */
	public patientDetails() {
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
		String gender = "";
		String nic = "";
		String dob = "";
		String maritalstatus = "";
		double height = (double) 0.00;
		double weight = (double) 0.00;
		String mobilenumber = "";
		String homenumber = "";
		String worknumber = "";
		String haschild = "No";

		if (request.getParameter("SurnameTextBox") != null) {
			surname = request.getParameter("SurnameTextBox");
		}

		if (request.getParameter("middlenameTextBox") != null) {
			middlename = request.getParameter("middlenameTextBox");
		}

		if (request.getParameter("forenameTextBox") != null) {
			forename = request.getParameter("forenameTextBox");
		}

		if (request.getParameter("genderDropDown") != null) {
			gender = request.getParameter("genderDropDown");
		}

		if (request.getParameter("nicTextBox") != null) {
			nic = request.getParameter("nicTextBox");
		}

		if (request.getParameter("dobTextBox") != null) {
			dob = request.getParameter("dobTextBox");
		}

		if (request.getParameter("maritalstatusDropDown") != null) {
			maritalstatus = request.getParameter("maritalstatusDropDown");
		}

		if (request.getParameter("heightTextBox") != null || request.getParameter("heightTextBox") != "") {
			height = ParseDouble(request.getParameter("heightTextBox"));
		}

		if (request.getParameter("weightTextBox") != null || request.getParameter("weightTextBox") != "") {
			weight = ParseDouble(request.getParameter("weightTextBox"));
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

		if (request.getParameter("haschildrenDropDown") != null) {
			haschild = request.getParameter("haschildrenDropDown");
		}

		HttpSession session = request.getSession(false);
		String userID = (String) session.getAttribute("userID");

		try {
			if (ValidateData(nic, userID)) {

				savePatient(userID, surname, middlename, forename, gender, nic, dob, maritalstatus, height, weight,
						mobilenumber, homenumber, worknumber, other, haschild);

				RequestDispatcher req = request.getRequestDispatcher("patientDetails.jsp");
				req.include(request, response);
			} else {

				errorMessage = errorMessage.replace("null", "");
				request.setAttribute("errorMessage", errorMessage);

				request.setAttribute("SurnameTextBox", request.getParameter("SurnameTextBox"));
				request.setAttribute("middlenameTextBox", request.getParameter("middlenameTextBox"));
				request.setAttribute("forenameTextBox", request.getParameter("forenameTextBox"));
				request.setAttribute("Others", request.getParameter("Others"));
				request.setAttribute("genderDropDown", request.getParameter("genderDropDown"));
				request.setAttribute("nicTextBox", request.getParameter("nicTextBox"));
				request.setAttribute("dobTextBox", request.getParameter("dobTextBox"));
				request.setAttribute("maritalstatusDropDown", request.getParameter("maritalstatusDropDown"));
				request.setAttribute("heightTextBox", request.getParameter("heightTextBox"));
				request.setAttribute("weightTextBox", request.getParameter("weightTextBox"));
				request.setAttribute("mobilenumber", request.getParameter("mobilenumber"));
				request.setAttribute("homenumber", request.getParameter("homenumber"));
				request.setAttribute("worknumber", request.getParameter("worknumber"));
				request.setAttribute("haschildrenDropDown", request.getParameter("haschildrenDropDown"));

				RequestDispatcher req = request.getRequestDispatcher("patientDetails.jsp");
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
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addPatientToOntology(String userId , String name , double height , double weight, double bmi , String gender , String hasChild) {

	}

	public boolean ValidateData(String nic, String userId) throws ClassNotFoundException, SQLException {

		if (checkNic(nic)) {
			errorMessage += "Nic already exist <br/>";
		}

		if (checkUser(userId)) {
			errorMessage += "User already exist <br/>";
		}

		if (errorMessage == null || errorMessage == "") {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkNic(String nic) throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables
		final String url = "jdbc:sqlserver://DESKTOP-V30A0OF\\SQLEXPRESS;databaseName=OntoDiabetes;";
		final String user = "sa";
		final String password = "Password001!";
		String dbNic = "";
		Boolean present = false;
		// establish the connection
		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = "select [nic] from [OntoDiabetes_PatientDetails] where [nic]='" + nic + "';";

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
		final String url = "jdbc:sqlserver://DESKTOP-V30A0OF\\SQLEXPRESS;databaseName=OntoDiabetes;";
		final String user = "sa";
		final String password = "Password001!";
		String dbUserId = "";
		Boolean present = false;
		// establish the connection
		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = "select [userid] from [OntoDiabetes_PatientDetails] where [userid]='" + userId + "';";

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

	public void savePatient(String userid, String surname, String middlename, String lastname, String gender,
			String nic, String dateofbirth, String maritalstatus, double height, double weight, String phonenumber,
			String homenumber, String worknumber, String other, String haschild)
			throws ClassNotFoundException, SQLException, ParseException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables
		final String url = "jdbc:sqlserver://DESKTOP-V30A0OF\\SQLEXPRESS;databaseName=OntoDiabetes;";
		final String user = "sa";
		final String password = "Password001!";

		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();
			int age = getAge(dateofbirth);
			double bmi = getBmi(height, weight);
			String query = " INSERT INTO [OntoDiabetes_PatientDetails]([userid]" + "      ,[surname]"
					+ "      ,[middlename]" + "      ,[lastname]" + "      ,[gender]" + "      ,[nic]"
					+ "      ,[dateofbirth]" + "      ,[maritalstatus]" + "      ,[height]" + "      ,[weight]"
					+ "      ,[phonenumber]" + "      ,[homenumber]" + "      ,[worknumber]" + "      ,[age]"
					+ "      ,[bmi]" + "" + "      ,[other],[hasChildren]) VALUES('" + userid + "','" + surname + "','"
					+ middlename + "','" + lastname + "','" + gender + "','" + nic + "','" + dateofbirth + "','"
					+ maritalstatus + "','" + height + "','" + weight + "','" + phonenumber + "','" + homenumber + "','"
					+ worknumber + "','" + age + "','" + bmi + "','" + other + "','" + haschild + "')";

			log(query);
			// send and execute SQL query in Database software
			st.execute(query);

			// adding Data to ontology
			addPatientToOntology(userid, surname + " " + middlename + " " + lastname, height, weight, bmi, gender,haschild);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();

	}

	public int getAge(String dob) throws ParseException {
		String str[] = dob.split("-");
		int day = Integer.parseInt(str[2]);
		int month = Integer.parseInt(str[1]);
		int year = Integer.parseInt(str[0]);

		LocalDate l = LocalDate.of(year, month, day); // specify year, month, date directly
		LocalDate now = LocalDate.now(); // gets localDate
		Period diff = Period.between(l, now); // difference between the dates is calculated

		return diff.getYears();
	}

	public double getBmi(double height, double weight) {

		return weight / (height * height);
	}

	double ParseDouble(String strNumber) {
		if (strNumber != null && strNumber.length() > 0) {
			try {
				return Double.parseDouble(strNumber);
			} catch (Exception e) {
				return -1; // or some value to mark this field is wrong. or make a function validates field
							// first ...
			}
		} else
			return 0;
	}

}
