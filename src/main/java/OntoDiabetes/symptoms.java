package OntoDiabetes;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
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
 * Servlet implementation class symptoms
 */
@WebServlet("/symptoms")
public class symptoms extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Connection con;

	DatabaseConnection db = new DatabaseConnection();

	public final String url = db.getUrlConnection();
	public final String user = db.getUser();
	public final String password = db.getPassword();

	/**
	 * Default constructor.
	 */
	public symptoms() {
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
		String tension = request.getParameter("tension");
		String fatigue = request.getParameter("fatigue");
		String hunger = request.getParameter("hunger");
		String lossweight = request.getParameter("lossweight");
		String vision = request.getParameter("vision");
		String tingling = request.getParameter("tingling");
		String patches = request.getParameter("patches");
		String infections = request.getParameter("infections");
		String healing = request.getParameter("healing");
		String relative = request.getParameter("relative");
		String polyuria = request.getParameter("polyuria");
		String polydipsia = request.getParameter("polydipsia");

		String adverse = "N/A";
		String miscarriage = "N/A";
		String stillbirth = "N/A";

		if (request.getParameter("adverse") != null) {
			adverse = request.getParameter("adverse");
		}

		if (request.getParameter("miscarriage") != null) {
			miscarriage = request.getParameter("miscarriage");
		}

		if (request.getParameter("stillbirth") != null) {
			stillbirth = request.getParameter("stillbirth");
		}

		HttpSession session = request.getSession(false);
		String userID = (String) session.getAttribute("userID");

		try {
			saveSymptoms(userID, tension, fatigue, hunger, lossweight, vision, tingling, patches, infections, healing,
					relative, polyuria, polydipsia, adverse, miscarriage, stillbirth);

			addSymptomsToOntology(userID, tension, fatigue, hunger, lossweight, vision, tingling, patches, infections,
					healing, relative, polyuria, polydipsia, adverse, miscarriage, stillbirth);

			addDoctorReviewTask(userID);
			updateTask(userID);
			updateStage(userID);

			response.sendRedirect("dashboard.jsp");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addSymptomsToOntology(String userid, String tension, String fatigue, String hunger, String lossweight,
			String vison, String tingling, String patches, String infections, String healing, String relative,
			String polyuria, String polydipsia, String adverse, String miscarriage, String stillbirth)
			throws ClassNotFoundException, SQLException, OWLOntologyCreationException, OWLOntologyStorageException {

		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = ontologyManager
				.loadOntologyFromOntologyDocument(new File(getServletContext().getRealPath("Diabetes.owl")));
		String base = "http://www.semanticweb.org/adarsh/ontologies/2021/2/Diabetes_ontology#";
		PrefixManager pm = new DefaultPrefixManager(null, null, base);
		OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();

		// Adding an instance to class patient
		OWLNamedIndividual patient = dataFactory.getOWLNamedIndividual(":Patient_" + userid, pm);
		OWLNamedIndividual HyperTension = dataFactory.getOWLNamedIndividual(":Hypertension", pm);
		OWLNamedIndividual BlurredVision = dataFactory.getOWLNamedIndividual(":Blurred_vision", pm);
		OWLNamedIndividual RelativeDiabetes = dataFactory.getOWLNamedIndividual(":RelativeDiabetes", pm);
		OWLNamedIndividual Fatigue = dataFactory.getOWLNamedIndividual(":Fatigue", pm);
		OWLNamedIndividual FrequentInfections = dataFactory.getOWLNamedIndividual(":Frequent_infections", pm);
		OWLNamedIndividual IncreasedHunger = dataFactory.getOWLNamedIndividual(":Increased_hunger", pm);
		OWLNamedIndividual Patches = dataFactory.getOWLNamedIndividual(":Patches_of_dark_skin", pm);
		OWLNamedIndividual Polydipsia = dataFactory.getOWLNamedIndividual(":Polydipsia", pm);
		OWLNamedIndividual Polyuria = dataFactory.getOWLNamedIndividual(":Polyuria", pm);
		OWLNamedIndividual OH = dataFactory.getOWLNamedIndividual(":Poor_obstettric_history", pm);
		OWLNamedIndividual Healing = dataFactory.getOWLNamedIndividual(":Slow-healing_sores_and_wounds", pm);
		OWLNamedIndividual Tingling = dataFactory.getOWLNamedIndividual(":Tingling_hands_and_feet", pm);
		OWLNamedIndividual WeightLoss = dataFactory.getOWLNamedIndividual(":Weight_loss", pm);

		
		
		XMLWriterPreferences.getInstance().setUseNamespaceEntities(true);
        OWLObjectProperty hasSymptoms = dataFactory.getOWLObjectProperty(":has_symptoms", pm);

		// tension

		if (tension.equals("Yes")) {
		    OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
	                .getOWLObjectPropertyAssertionAxiom(hasSymptoms, patient, HyperTension);
			ontologyManager.addAxiom(ontology, propertyAssertion);
		}

		// Fatigue
		if (fatigue.equals("Yes")) {
			 OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
		                .getOWLObjectPropertyAssertionAxiom(hasSymptoms, patient, Fatigue);
				ontologyManager.addAxiom(ontology, propertyAssertion);
		}

		// hunger
		if (hunger.equals("Yes")) {
			 OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
		                .getOWLObjectPropertyAssertionAxiom(hasSymptoms, patient, IncreasedHunger);
				ontologyManager.addAxiom(ontology, propertyAssertion);
		}


		// lossweight
		if (lossweight.equals("Yes")) {
			 OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
		                .getOWLObjectPropertyAssertionAxiom(hasSymptoms, patient, WeightLoss);
				ontologyManager.addAxiom(ontology, propertyAssertion);
		}
		


		// vison
		
		
		if (vison.equals("Yes")) {
			 OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
		                .getOWLObjectPropertyAssertionAxiom(hasSymptoms, patient, BlurredVision);
				ontologyManager.addAxiom(ontology, propertyAssertion);
		}
		


		// tingling
		
		if (tingling.equals("Yes")) {
			 OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
		                .getOWLObjectPropertyAssertionAxiom(hasSymptoms, patient, Tingling);
				ontologyManager.addAxiom(ontology, propertyAssertion);
		}
		


		// patches
		if (patches.equals("Yes")) {
			 OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
		                .getOWLObjectPropertyAssertionAxiom(hasSymptoms, patient, Patches);
				ontologyManager.addAxiom(ontology, propertyAssertion);
		}
	

		// infections
		if (infections.equals("Yes")) {
			 OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
		                .getOWLObjectPropertyAssertionAxiom(hasSymptoms, patient, FrequentInfections);
				ontologyManager.addAxiom(ontology, propertyAssertion);
		}


		// healing
		
		
		if (healing.equals("Yes")) {
			 OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
		                .getOWLObjectPropertyAssertionAxiom(hasSymptoms, patient, Healing);
				ontologyManager.addAxiom(ontology, propertyAssertion);
		}

		// relative
		
		if (relative.equals("Yes")) {
			 OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
		                .getOWLObjectPropertyAssertionAxiom(hasSymptoms, patient, RelativeDiabetes);
				ontologyManager.addAxiom(ontology, propertyAssertion);
		}


		// polyuria
		
		
		if (polyuria.equals("Yes")) {
			 OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
		                .getOWLObjectPropertyAssertionAxiom(hasSymptoms, patient, Polyuria);
				ontologyManager.addAxiom(ontology, propertyAssertion);
		}



		// polydipsia
		if (polydipsia.equals("Yes")) {
			 OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
		                .getOWLObjectPropertyAssertionAxiom(hasSymptoms, patient, Polydipsia);
				ontologyManager.addAxiom(ontology, propertyAssertion);
		}

	
		String ObsetricHistory = "no";
		if (miscarriage.toLowerCase().equals("yes") || adverse.toLowerCase().equals("yes")
				|| stillbirth.toLowerCase().equals("yes")) {
			ObsetricHistory = "Yes";
		}
		// ObsetricHistory
		if (ObsetricHistory.equals("Yes")) {
			 OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
		                .getOWLObjectPropertyAssertionAxiom(hasSymptoms, patient, OH);
				ontologyManager.addAxiom(ontology, propertyAssertion);
		}


		ontologyManager.saveOntology(ontology);

	}

	public void saveSymptoms(String userid, String tension, String fatigue, String hunger, String lossweight,
			String vison, String tingling, String patches, String infections, String healing, String relative,
			String polyuria, String polydipsia, String adverse, String miscarriage, String stillbirth)
			throws ClassNotFoundException, SQLException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables

		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = " INSERT INTO [OntoDiabetes_Symptoms]([patient_id]" + "      ,[tension]" + "      ,[fatigue]"
					+ "      ,[hunger]" + "      ,[lossweight]" + "      ,[vision]" + "      ,[tingling]"
					+ "      ,[patches]" + "      ,[infections]" + "      ,[healing]" + "      ,[relative]"
					+ "      ,[polyuria]" + "      ,[polydipsia]" + "      ,[adverse]" + "      ,[miscarriage]"
					+ "      ,[stillbirth]) VALUES('" + userid + "','" + tension + "','" + fatigue + "','" + hunger
					+ "','" + lossweight + "','" + vison + "','" + tingling + "','" + patches + "','" + infections
					+ "','" + healing + "','" + relative + "','" + polyuria + "','" + polydipsia + "','" + adverse
					+ "','" + miscarriage + "','" + stillbirth + "')";

			log(query);
			// send and execute SQL query in Database software
			st.execute(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();

	}

	public void addDoctorReviewTask(String userid) throws ClassNotFoundException, SQLException, ParseException,
			OWLOntologyCreationException, OWLOntologyStorageException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// variables

		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = " INSERT INTO [OntoDiabetes_Task]([status],[assign_to],[description],[link] ,[to_doc]) VALUES('Pending','"
					+ userid
					+ "','Your symptoms has been submitted and your doctor will follow up with you. Kindly wait','reviewPatientSymptoms.jsp?patientid="
					+ userid + "','yes')";

			log(query);
			// send and execute SQL query in Database software
			st.execute(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con.close();

	}

	public void updateTask(String userid) throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
// variables

		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = " UPDATE [OntoDiabetes_Task] SET Status = 'Completed'  where  Description like '%symptoms you are currently having%'  and [assign_to] = "
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

	public void updateStage(String userid) throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//variables

		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = " UPDATE [OntoDiabetes_PatientStage] SET stage = 'Waiting doctor to review symptoms'  where   [patient_id] = "
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
}
