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
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterPreferences;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.SWRLBuiltInException;
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
@WebServlet("/reviewTreatment")
public class reviewTreatment extends HttpServlet {
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
	public reviewTreatment() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	

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
			updateTask(id);
			addTask(id,"Pending" , "Results are ready.<br/> You can view them at <a href=''treatmentResult.jsp?patientid="+id +"'' target=''_blank''>View Results</a> <br/> You may proceed with the link after a week to enter your details again to continue the treatment plan." ,"treatment.jsp?patientid="+id);
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		response.sendRedirect("dashboardDoc.jsp");
	}

	public void Queries(String UserID)
			throws SQWRLException, SWRLParseException, OWLOntologyCreationException, OWLOntologyStorageException {

		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = ontologyManager
				.loadOntologyFromOntologyDocument(new File(getServletContext().getRealPath("Diabetes.owl")));
		String base = "http://www.semanticweb.org/adarsh/ontologies/2021/2/Diabetes_ontology#";
		PrefixManager pm = new DefaultPrefixManager(null, null, base);
		OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();
		OWLNamedIndividual patient = dataFactory.getOWLNamedIndividual(":Patient_" + UserID, pm);
		
		removeObjectProperty(ontologyManager,ontology,base,pm, dataFactory,patient,":has_Optimal");
		removeObjectProperty(ontologyManager,ontology,base,pm, dataFactory,patient,":has_Acceptable");
		removeObjectProperty(ontologyManager,ontology,base,pm, dataFactory,patient,":has_Uncontrollable");
		removeObjectProperty(ontologyManager,ontology,base,pm, dataFactory,patient,":has_complications");
		removeObjectProperty(ontologyManager,ontology,base,pm, dataFactory,patient,":take_medications");
		
		SWRLRuleEngine swrlRuleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
	


		// Run the SWRL rules in the ontology
		swrlRuleEngine.infer();

		ontologyManager.saveOntology(ontology);
		
	
	}

	public void updateTask(String userid) throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
// variables

		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = " UPDATE [OntoDiabetes_Task] SET Status = 'Completed'  where  Description like '%Review Treatment information for patient%'  and [assign_to] = "
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
					+ status + "','" + userid + "','" + message + "','" + link + "','yes')";

			log(query);
			// send and execute SQL query in Database software
			st.execute(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();

	}

	public static void removeObjectProperty(OWLOntologyManager ontologyManager, OWLOntology ontology, String base,
			PrefixManager pm, OWLDataFactory dataFactory, OWLNamedIndividual indi, String objProp)
			throws OWLOntologyCreationException, OWLOntologyStorageException {

		OWLObjectProperty objectProperty = dataFactory.getOWLObjectProperty(objProp, pm);

		for (OWLAxiom a : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION)) {
			{

				if (a.getSignature().contains(objectProperty)) {
					ontologyManager.removeAxiom(ontology, a);

				}
			}
		}
		ontology.saveOntology();
	}

}
