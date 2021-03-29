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

		XMLWriterPreferences.getInstance().setUseNamespaceEntities(true);

		// tension
		OWLDataProperty hastension = dataFactory.getOWLDataProperty(":hasTension", pm);
		OWLDataPropertyAssertionAxiom tensionPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hastension, patient, tension);
		ontologyManager.addAxiom(ontology, tensionPropertyAssertion);

		// Fatigue
		OWLDataProperty hasFatigue = dataFactory.getOWLDataProperty(":hasFatigue", pm);
		OWLDataPropertyAssertionAxiom fatiguePropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasFatigue, patient, fatigue);
		ontologyManager.addAxiom(ontology, fatiguePropertyAssertion);

		// hunger
		OWLDataProperty hasHunger = dataFactory.getOWLDataProperty(":hasHunger", pm);
		OWLDataPropertyAssertionAxiom hungerPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasHunger,
				patient, hunger);
		ontologyManager.addAxiom(ontology, hungerPropertyAssertion);

		// lossweight
		OWLDataProperty hasLossWeight = dataFactory.getOWLDataProperty(":hasLossWeight", pm);
		OWLDataPropertyAssertionAxiom lossWeightPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasLossWeight, patient, lossweight);
		ontologyManager.addAxiom(ontology, lossWeightPropertyAssertion);

		// vison
		OWLDataProperty hasBlurredVision = dataFactory.getOWLDataProperty(":hasBlurredVision", pm);
		OWLDataPropertyAssertionAxiom visionPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasBlurredVision, patient, vison);
		ontologyManager.addAxiom(ontology, visionPropertyAssertion);

		// tingling
		OWLDataProperty hasTinglingHandsAndFeet = dataFactory.getOWLDataProperty(":hasTinglingHandsAndFeet", pm);
		OWLDataPropertyAssertionAxiom tinglingPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasTinglingHandsAndFeet, patient, tingling);
		ontologyManager.addAxiom(ontology, tinglingPropertyAssertion);

		// patches

		OWLDataProperty hasDarkPatches = dataFactory.getOWLDataProperty(":hasDarkPatches", pm);
		OWLDataPropertyAssertionAxiom patchesPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasDarkPatches, patient, patches);
		ontologyManager.addAxiom(ontology, patchesPropertyAssertion);

		// infections
		OWLDataProperty hasInfection = dataFactory.getOWLDataProperty(":hasInfection", pm);
		OWLDataPropertyAssertionAxiom infectionPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasInfection, patient, infections);
		ontologyManager.addAxiom(ontology, infectionPropertyAssertion);

		// healing
		OWLDataProperty hasSlowHealing = dataFactory.getOWLDataProperty(":hasSlowHealing", pm);
		OWLDataPropertyAssertionAxiom healingPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasSlowHealing, patient, healing);
		ontologyManager.addAxiom(ontology, healingPropertyAssertion);

		// relative
		OWLDataProperty hasFirstDegreeRelative = dataFactory.getOWLDataProperty(":hasFirstDegreeRelative", pm);
		OWLDataPropertyAssertionAxiom relativePropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasFirstDegreeRelative, patient, relative);
		ontologyManager.addAxiom(ontology, relativePropertyAssertion);

		// polyuria
		OWLDataProperty hasPolyuria = dataFactory.getOWLDataProperty(":hasPolyuria", pm);
		OWLDataPropertyAssertionAxiom polyuriaPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasPolyuria, patient, polyuria);
		ontologyManager.addAxiom(ontology, polyuriaPropertyAssertion);

		// polydipsia
		OWLDataProperty hasPolydipsia = dataFactory.getOWLDataProperty(":hasPolydipsia", pm);
		OWLDataPropertyAssertionAxiom polydipsiaPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasPolydipsia, patient, polydipsia);
		ontologyManager.addAxiom(ontology, polydipsiaPropertyAssertion);

		String ObsetricHistory = "no";
		if (stillbirth.toLowerCase().equals("yes") || stillbirth.toLowerCase().equals("yes")
				|| stillbirth.toLowerCase().equals("yes")) {
			ObsetricHistory = "yes";
		}
		// ObsetricHistory
		OWLDataProperty hasObsetricHistory = dataFactory.getOWLDataProperty(":hasObsetricHistory", pm);
		OWLDataPropertyAssertionAxiom obPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasObsetricHistory, patient, ObsetricHistory);
		ontologyManager.addAxiom(ontology, obPropertyAssertion);

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
