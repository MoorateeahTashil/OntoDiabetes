package OntoDiabetes;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterPreferences;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class reviewTest
 */
@WebServlet("/reviewTest")
public class reviewTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DatabaseConnection db = new DatabaseConnection();

	public final String url = db.getUrlConnection();
	public final String user = db.getUser();
	public final String password = db.getPassword();

	public static Connection con;
	public static String errorMessage;
	public static ArrayList<String> message = new ArrayList<String>();

	/**
	 * Default constructor.
	 */
	public reviewTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		errorMessage = "";

		message.clear();

		String id = request.getParameter("patient_ID");
		String messages = request.getParameter("messages");
		try {
			updateTask(id);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (messages.contains("Diabetes Mellitus")) {
			try {
				updateStage(id, "Treatment Phase(Diagnosed with Diabetes)");
				addTask(id, "Pending", "You have been diagnosed with diabetes. We will start your treatment plan.",
						"treatment.jsp?patientID=" + id);
			} catch (ClassNotFoundException | SQLException | OWLOntologyCreationException | OWLOntologyStorageException
					| ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (messages.contains("Impaired Glucose Tolerance")) {
			try {
				updateStage(id, "Impaired Glucose Tolerance (Not diagnosed with Diabetes)");
				addTask(id, "Completed", "You have been not diagnosed with diabetes but you have Impaired Glucose Tolerance.Glucose intolerance can be treated through diet and lifestyle changes or with assistance from anti-diabetic medication, such as tablets and/or insulin.",
						"dashboard.jsp");
			} catch (ClassNotFoundException | SQLException | OWLOntologyCreationException | OWLOntologyStorageException
					| ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (messages.contains("Impaired Fasting Glycaemia")) {
			try {
				updateStage(id, "Impaired Fasting Glycaemia (Not diagnosed with Diabetes)");
				addTask(id, "Completed", "You have been not diagnosed with diabetes but you have Impaired Fasting Glycaemia.Impaired Fasting Glycaemia can be treated through diet and lifestyle changes or with assistance from anti-diabetic medication, such as Metformin  and/or Rosiglitazone.",
						"dashboard.jsp");
			} catch (ClassNotFoundException | SQLException | OWLOntologyCreationException | OWLOntologyStorageException
					| ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				updateStage(id, "Not diagnosed with Diabetes)");
				addTask(id, "Completed", "You have been not diagnosed with diabetes. We advise you to cut sugar and refined carbohydrates from your diet , eat balanced diet ,quit smoking , drink water and eat fibers and exercise.",
						"dashboard.jsp");
			} catch (ClassNotFoundException | SQLException | OWLOntologyCreationException | OWLOntologyStorageException
					| ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		response.sendRedirect("dashboardDoc.jsp");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		errorMessage = "";

		message.clear();

		String id = request.getParameter("patientID");
		try {
			Queries(id);
		} catch (SQWRLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SWRLParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		errorMessage += "<ul>";
		for (String element : message) {
			errorMessage += "<li>";
			errorMessage += element;
			errorMessage += "</li>";
		}
		errorMessage += "</ul>";

		if (errorMessage == null || errorMessage == "") {
			errorMessage = "NoDiabetes";
		}

		errorMessage = errorMessage.replace("null", "");

		HttpSession session = request.getSession(false);

		session.setAttribute("errorMessage", errorMessage);

		response.sendRedirect("reviewTest.jsp?patientid=" + id);
	}

	public void Queries(String UserID)
			throws SQWRLException, SWRLParseException, OWLOntologyCreationException, OWLOntologyStorageException {

		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = ontologyManager
				.loadOntologyFromOntologyDocument(new File(getServletContext().getRealPath("Diabetes.owl")));

		// Create SQWRL query engine using the SWRLAPI
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result = queryEngine.runSQWRLQuery("q", "#Patient(?p) ^ #hasPatientID(?p, \"" + UserID
				+ "\") ^ #hasFbs(?p,?fbs) ^ swrlb:greaterThan(?fbs , 126)  -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result.next()) {
			message.clear();
			message.add("Patient has Diabetes Mellitus(FBS > 125).");
		}

		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result1 = queryEngine.runSQWRLQuery("q1", "#Patient(?p) ^ #hasPatientID(?p, \"" + UserID
				+ "\") ^ #hasRbs(?p,?rbs) ^ swrlb:greaterThan(?rbs , 200)  -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result1.next()) {
			message.clear();
			message.add("Patient has Diabetes Mellitus (Rbs > 200).");
		}

		SQWRLResult result2 = queryEngine.runSQWRLQuery("q2", "#Patient(?p) ^ #hasPatientID(?p, \"" + UserID
				+ "\") ^ #hasFbs(?p,126) -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result2.next()) {
			message.clear();
			message.add("Patient has Impaired Glucose Tolerance (IGT) (FBS = 126).");
		}

		SQWRLResult result3 = queryEngine.runSQWRLQuery("q3", "#Patient(?p) ^ #hasPatientID(?p, \"" + UserID
				+ "\") ^ #hasRbs(?p,?rbs)  ^ swrlb:greaterThan(?rbs , 140)  ^ swrlb:lessThan(?rbs , 200) -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result3.next()) {
			message.clear();
			message.add("Patient has Impaired Glucose Tolerance (IGT) (140 > Rbs > 200).");
		}

		SQWRLResult result4 = queryEngine.runSQWRLQuery("q4", "#Patient(?p) ^ #hasPatientID(?p, \"" + UserID
				+ "\") ^ #hasRbs(?p,?rbs)  ^ swrlb:greaterThan(?rbs , 100)  ^ swrlb:lessThan(?rbs , 140) -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result4.next()) {
			message.clear();
			message.add("Patient has Impaired Fasting Glycaemia (IFG) (100 > Rbs > 140).");
		}

		SQWRLResult result5 = queryEngine.runSQWRLQuery("q5", "#Patient(?p) ^ #hasPatientID(?p, \"" + UserID
				+ "\") ^ #hasFbs(?p,?fbs)  ^ swrlb:greaterThan(?fbs , 110)  ^ swrlb:lessThan(?fbs , 126) -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result5.next()) {
			message.clear();
			message.add("Patient has Impaired Fasting Glycaemia (IFG) (110 > FBS > 126).");
		}

	}

	public void updateTask(String userid) throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
// variables

		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = " UPDATE [OntoDiabetes_Task] SET Status = 'Completed'  where  Description like '%Waiting for Doctor to review the plasma glucose test%'  and [assign_to] = "
					+ userid + "";

			log(query);
			// send and execute SQL query in Database software
			st.execute(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();

	}

	public void updateStage(String userid, String message) throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//variables

		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = " UPDATE [OntoDiabetes_PatientStage] SET stage = '" + message + "'  where   [patient_id] = "
					+ userid + "";

			log(query);
			// send and execute SQL query in Database software
			st.execute(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();

	}

	public void addTask(String userid, String status, String message, String link) throws ClassNotFoundException,
			SQLException, ParseException, OWLOntologyCreationException, OWLOntologyStorageException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//variables

		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = " INSERT INTO [OntoDiabetes_Task]([status],[assign_to],[description],[link] ,[to_doc]) VALUES('"
					+ status + "','" + userid + "','" + message + "','" + link + "','no')";

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
