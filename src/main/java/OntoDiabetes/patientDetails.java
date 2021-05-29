package OntoDiabetes;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import java.time.LocalDate;
import java.time.Period;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterPreferences;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * /** Servlet implementation class patientDetails
 */
@WebServlet("/patientDetails")
public class patientDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DatabaseConnection db = new DatabaseConnection();
	
	public final String url = db.getUrlConnection();
	public final String user = db.getUser();
	public final String password = db.getPassword();
	
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
		errorMessage = "";
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
				addSymtompsTask(userID);
				addPatientStage(userID);
				response.sendRedirect("dashboard.jsp");
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

				errorMessage = errorMessage.replace("null", "");

				session.setAttribute("errorMessage", errorMessage);
				
				response.sendRedirect("patientDetails.jsp");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addPatientToOntology(String userId, String name, double height, double weight, double bmi,
			String gender, String hasChild) throws OWLOntologyCreationException, OWLOntologyStorageException {

		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = ontologyManager
				.loadOntologyFromOntologyDocument(new File(getServletContext().getRealPath("Diabetes.owl")));
		String base = "http://www.semanticweb.org/adarsh/ontologies/2021/2/Diabetes_ontology#";
		PrefixManager pm = new DefaultPrefixManager(null, null, base);
		OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();

		// Adding an instance to class patient
		OWLClass patientClass = dataFactory.getOWLClass(":Patient", pm);
		OWLNamedIndividual patient = dataFactory.getOWLNamedIndividual(":Patient_" + userId, pm);

		OWLClassAssertionAxiom classAssertion = dataFactory.getOWLClassAssertionAxiom(patientClass, patient);
		ontologyManager.addAxiom(ontology, classAssertion);

		XMLWriterPreferences.getInstance().setUseNamespaceEntities(true);

		// Adding Data Property

		// USERID
		OWLDataProperty hasPatientID = dataFactory.getOWLDataProperty(":hasPatientID", pm);
		OWLDataPropertyAssertionAxiom idPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasPatientID,
				patient, userId);
		ontologyManager.addAxiom(ontology, idPropertyAssertion);

		// FULL NAME
		OWLDataProperty hasFullName = dataFactory.getOWLDataProperty(":hasFullName", pm);
		OWLDataPropertyAssertionAxiom namePropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasFullName,
				patient, name);
		ontologyManager.addAxiom(ontology, namePropertyAssertion);

		// HEIGHT
		OWLDataProperty hasHeight = dataFactory.getOWLDataProperty(":hasHeight", pm);
		OWLDataPropertyAssertionAxiom heightPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasHeight,
				patient, height);
		ontologyManager.addAxiom(ontology, heightPropertyAssertion);

		// WEIGHT
		OWLDataProperty hasWeight = dataFactory.getOWLDataProperty(":hasWeight", pm);
		OWLDataPropertyAssertionAxiom weightPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasWeight,
				patient, weight);
		ontologyManager.addAxiom(ontology, weightPropertyAssertion);

		// BMI
		OWLDataProperty hasBmi = dataFactory.getOWLDataProperty(":hasBmi", pm);
		OWLDataPropertyAssertionAxiom bmiPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasBmi,
				patient, bmi);
		ontologyManager.addAxiom(ontology, bmiPropertyAssertion);

		// Children
		OWLDataProperty hasChildren = dataFactory.getOWLDataProperty(":hasChildren", pm);
		OWLDataPropertyAssertionAxiom childPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasChildren,
				patient, hasChild);
		ontologyManager.addAxiom(ontology, childPropertyAssertion);

		// Gender
		OWLDataProperty hasGender = dataFactory.getOWLDataProperty(":hasGender", pm);
		OWLDataPropertyAssertionAxiom genderPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasGender,
				patient, gender);
		ontologyManager.addAxiom(ontology, genderPropertyAssertion);

		ontologyManager.saveOntology(ontology);

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
			String homenumber, String worknumber, String other, String haschild) throws ClassNotFoundException,
			SQLException, ParseException, OWLOntologyCreationException, OWLOntologyStorageException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables


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
			addPatientToOntology(userid, surname + " " + middlename + " " + lastname, height, weight, bmi, gender,
					haschild);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();

	}

	public void addSymtompsTask(String userid) throws ClassNotFoundException, SQLException, ParseException,
			OWLOntologyCreationException, OWLOntologyStorageException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables


		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = " INSERT INTO [OntoDiabetes_Task]([status],[assign_to],[description],[link]) VALUES('Pending','"
					+ userid
					+ "','Please fill in the questionnaire to describe any kind of symptoms you are currently having.','symptoms.jsp')";

			log(query);
			// send and execute SQL query in Database software
			st.execute(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();

	}

	public void addPatientStage(String userid) throws ClassNotFoundException, SQLException, ParseException,
			OWLOntologyCreationException, OWLOntologyStorageException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = " INSERT INTO [OntoDiabetes_PatientStage]([stage],[patient_id]) VALUES('Symptoms','" + userid
					+ "')";

			log(query);
			// send and execute SQL query in Database software
			st.execute(query);

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
