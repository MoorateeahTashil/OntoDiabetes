package OntoDiabetes;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
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
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class treatment
 */
@WebServlet("/treatment")
public class treatment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Connection con;

	DatabaseConnection db = new DatabaseConnection();

	public final String url = db.getUrlConnection();
	public final String user = db.getUser();
	public final String password = db.getPassword();

	/**
	 * Default constructor.
	 */
	public treatment() {
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
		double height = ParseDouble(request.getParameter("height"));
		double weight = ParseDouble(request.getParameter("weight"));
		double waist = ParseDouble(request.getParameter("waist"));
		String sensation = request.getParameter("sensation");
		String infection = request.getParameter("infection");
		double acuity = ParseDouble(request.getParameter("acuity"));
		String vessel = request.getParameter("vessel");
		double systolic = ParseDouble(request.getParameter("systolic"));
		double diastolic = ParseDouble(request.getParameter("diastolic"));
		double hdl = ParseDouble(request.getParameter("hdl"));
		double ldl = ParseDouble(request.getParameter("ldl"));
		double triglycerides = ParseDouble(request.getParameter("triglycerides"));
		double cholesterol = ParseDouble(request.getParameter("cholesterol"));
		String albuminuria = request.getParameter("albuminuria");
		String ketones = request.getParameter("ketones");
		double creatine = ParseDouble(request.getParameter("creatine"));
		double microalbuminuria = ParseDouble(request.getParameter("microalbuminuria"));
		double hbalc = ParseDouble(request.getParameter("hbalc"));
		String switches = request.getParameter("switch");
		double valueText = ParseDouble(request.getParameter("valueText"));
		String patient = request.getParameter("patientID");
		String userID = patient;

		try {

			try {
				addTreatmentsToOntology(userID, height, weight, waist, sensation, infection, acuity, vessel, systolic,
						diastolic, hdl, ldl, triglycerides, cholesterol, albuminuria, ketones, creatine,
						microalbuminuria, hbalc, switches, valueText);
			} catch (SQWRLException | SWRLParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			addTask(userID, "Pending", "Review Treatment information for patient.",
					"reviewTreatment.jsp?patientid=" + userID);
			updateTask(userID);

			response.sendRedirect("dashboardDoc.jsp");

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

	public void addTreatmentsToOntology(String userid, double height, double weight, double waist, String sensation,
			String infection, double acuity, String vessel, double systolic, double diastolic, double hdl, double ldl,
			double triglycerides, double cholesterol, String albuminuria, String ketones, double creatine,
			double microalbuminuria, double hbalc, String switches, double valuetext)
			throws ClassNotFoundException, SQLException, OWLOntologyCreationException, OWLOntologyStorageException,
			SQWRLException, SWRLParseException {

		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = ontologyManager
				.loadOntologyFromOntologyDocument(new File(getServletContext().getRealPath("Diabetes.owl")));
		String base = "http://www.semanticweb.org/adarsh/ontologies/2021/2/Diabetes_ontology#";
		PrefixManager pm = new DefaultPrefixManager(null, null, base);
		OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();

		// Adding an instance to class patient
		OWLClass patientClass = dataFactory.getOWLClass(":Patient", pm);
		OWLNamedIndividual patient = dataFactory.getOWLNamedIndividual(":Patient_" + userid, pm);

		OWLClassAssertionAxiom classAssertion = dataFactory.getOWLClassAssertionAxiom(patientClass, patient);
		ontologyManager.addAxiom(ontology, classAssertion);

		XMLWriterPreferences.getInstance().setUseNamespaceEntities(true);

		// HEIGHT
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasHeight");
		OWLDataProperty hasHeight = dataFactory.getOWLDataProperty(":hasHeight", pm);
		OWLDataPropertyAssertionAxiom heightPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasHeight,
				patient, height);
		ontologyManager.addAxiom(ontology, heightPropertyAssertion);

		// WEIGHT
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasWeight");
		OWLDataProperty hasWeight = dataFactory.getOWLDataProperty(":hasWeight", pm);
		OWLDataPropertyAssertionAxiom weightPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasWeight,
				patient, weight);
		ontologyManager.addAxiom(ontology, weightPropertyAssertion);

		double bmi = getBmi(height, weight);
		// BMI
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasBmi");
		OWLDataProperty hasBmi = dataFactory.getOWLDataProperty(":hasBmi", pm);
		OWLDataPropertyAssertionAxiom bmiPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasBmi,
				patient, bmi);
		ontologyManager.addAxiom(ontology, bmiPropertyAssertion);

		// waist
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasWaist");
		OWLDataProperty hasWaist = dataFactory.getOWLDataProperty(":hasWaist", pm);
		OWLDataPropertyAssertionAxiom waistPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasWaist,
				patient, waist);
		ontologyManager.addAxiom(ontology, waistPropertyAssertion);

		// sensation
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasNoFeetSensation");
		OWLDataProperty hasSensation = dataFactory.getOWLDataProperty(":hasNoFeetSensation", pm);
		OWLDataPropertyAssertionAxiom sensationPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasSensation, patient, sensation);
		ontologyManager.addAxiom(ontology, sensationPropertyAssertion);

		// infection
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasFeetInfection");
		OWLDataProperty hasFeetInfection = dataFactory.getOWLDataProperty(":hasFeetInfection", pm);
		OWLDataPropertyAssertionAxiom feetPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasFeetInfection, patient, infection);
		ontologyManager.addAxiom(ontology, feetPropertyAssertion);

		// acuity
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasVisualAcuity");
		OWLDataProperty hasAcuity = dataFactory.getOWLDataProperty(":hasVisualAcuity", pm);
		OWLDataPropertyAssertionAxiom acuityPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasAcuity,
				patient, acuity);
		ontologyManager.addAxiom(ontology, acuityPropertyAssertion);

		// vessel
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasDamagedBloodVessel");

		OWLDataProperty hasVessel = dataFactory.getOWLDataProperty(":hasDamagedBloodVessel", pm);
		OWLDataPropertyAssertionAxiom vesselPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasVessel,
				patient, vessel);
		ontologyManager.addAxiom(ontology, vesselPropertyAssertion);

		// systolic
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasSystolic");
		OWLDataProperty hasSystolic = dataFactory.getOWLDataProperty(":hasSystolic", pm);
		OWLDataPropertyAssertionAxiom systolicPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasSystolic, patient, systolic);
		ontologyManager.addAxiom(ontology, systolicPropertyAssertion);

		// diastolic
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasDiastolic");
		OWLDataProperty hasDiastolic = dataFactory.getOWLDataProperty(":hasDiastolic", pm);
		OWLDataPropertyAssertionAxiom diastolicPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasDiastolic, patient, diastolic);
		ontologyManager.addAxiom(ontology, diastolicPropertyAssertion);

		// hdl
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hashdl");
		OWLDataProperty hashdl = dataFactory.getOWLDataProperty(":hashdl", pm);
		OWLDataPropertyAssertionAxiom hdlPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hashdl,
				patient, hdl);
		ontologyManager.addAxiom(ontology, hdlPropertyAssertion);

		// ldl
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasldl");
		OWLDataProperty hasldl = dataFactory.getOWLDataProperty(":hasldl", pm);
		OWLDataPropertyAssertionAxiom ldlPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasldl,
				patient, ldl);
		ontologyManager.addAxiom(ontology, ldlPropertyAssertion);

		// triglycerides
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasTriglycerides");
		OWLDataProperty hasTriglycerides = dataFactory.getOWLDataProperty(":hasTriglycerides", pm);
		OWLDataPropertyAssertionAxiom triglyceridesPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasTriglycerides, patient, triglycerides);
		ontologyManager.addAxiom(ontology, triglyceridesPropertyAssertion);

		// cholesterol
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasCholesterol");
		OWLDataProperty hasCholesterol = dataFactory.getOWLDataProperty(":hasCholesterol", pm);
		OWLDataPropertyAssertionAxiom cholesterolPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasCholesterol, patient, cholesterol);
		ontologyManager.addAxiom(ontology, cholesterolPropertyAssertion);

		// albuminuria
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasAlbuminuria");
		OWLDataProperty hasAlbuminuria = dataFactory.getOWLDataProperty(":hasAlbuminuria", pm);
		OWLDataPropertyAssertionAxiom albuminuriaPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasAlbuminuria, patient, albuminuria);
		ontologyManager.addAxiom(ontology, albuminuriaPropertyAssertion);

		// ketones
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasKetones");
		OWLDataProperty hasKetones = dataFactory.getOWLDataProperty(":hasKetones", pm);
		OWLDataPropertyAssertionAxiom ketonesPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasKetones, patient, ketones);
		ontologyManager.addAxiom(ontology, ketonesPropertyAssertion);

		// creatine
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasCreatine");
		OWLDataProperty hasCreatine = dataFactory.getOWLDataProperty(":hasCreatine", pm);
		OWLDataPropertyAssertionAxiom creatinePropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasCreatine, patient, creatine);
		ontologyManager.addAxiom(ontology, creatinePropertyAssertion);

		// microalbuminuria
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasMicroalbuminuria");
		OWLDataProperty hasMicroalbuminuria = dataFactory.getOWLDataProperty(":hasMicroalbuminuria", pm);
		OWLDataPropertyAssertionAxiom microalbuminuriaPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(hasMicroalbuminuria, patient, microalbuminuria);
		ontologyManager.addAxiom(ontology, microalbuminuriaPropertyAssertion);

		// hbalc
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hashbalc");
		OWLDataProperty hashbalc = dataFactory.getOWLDataProperty(":hashbalc", pm);
		OWLDataPropertyAssertionAxiom hashbalcPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hashbalc,
				patient, hbalc);
		ontologyManager.addAxiom(ontology, hashbalcPropertyAssertion);

		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

		SQWRLResult result1 = queryEngine.runSQWRLQuery("q2",
				"#Patient(?p)  ^ #hasPatientID(?p, \"" + userid	+ "\")  ^ #treatmentWeek(?p, ?x) -> sqwrl:select(?x)  ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		int treatmentWeek = 0;
		ArrayList<Integer> ResultArray = new ArrayList<Integer>();
		while (result1.next()) {
			String[] arrOfStr = result1.getValue("x").toString().split("\"\\^\\^xsd:integer");
			String[] arrOfStr1 = arrOfStr[0].split("\"");
			ResultArray.add(Integer.parseInt(arrOfStr1[1]));
		}

		if (!ResultArray.isEmpty()) {
			treatmentWeek = Collections.max(ResultArray);

		}
		treatmentWeek = treatmentWeek + 1;

		OWLDataProperty treatmentWeeks = dataFactory.getOWLDataProperty(":treatmentWeek", pm);
		OWLDataPropertyAssertionAxiom treatmentWeekPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(treatmentWeeks, patient, treatmentWeek);
		ontologyManager.addAxiom(ontology, treatmentWeekPropertyAssertion);

		boolean doTreatment = true;
		OWLDataProperty doTreatments = dataFactory.getOWLDataProperty(":doTreatment", pm);
		OWLDataPropertyAssertionAxiom doTreatmentPropertyAssertion = dataFactory
				.getOWLDataPropertyAssertionAxiom(doTreatments, patient, doTreatment);
		ontologyManager.addAxiom(ontology, doTreatmentPropertyAssertion);

		String TestResults = switches.toLowerCase();
		OWLDataProperty glucoseTest;
		if (switches.toLowerCase().equals("fbs")) {
			glucoseTest = dataFactory.getOWLDataProperty(":hasFbs", pm);

		} else {
			glucoseTest = dataFactory.getOWLDataProperty(":hasRbs", pm);

		}
		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasFbs");

		removeDataProperty(ontologyManager, ontology, base, pm, dataFactory, patient, ":hasRbs");

		OWLDataPropertyAssertionAxiom idPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(glucoseTest,
				patient, valuetext);
		ontologyManager.addAxiom(ontology, idPropertyAssertion);

		ontologyManager.saveOntology(ontology);

	}

	public void updateTask(String userid) throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
// variables

		con = DriverManager.getConnection(url, user, password);
		try {
			Statement st = con.createStatement();

			String query = " UPDATE [OntoDiabetes_Task] SET Status = 'Completed'  where  Description like '%treatment plan%'  and [assign_to] = "
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

	public static void removeDataProperty(OWLOntologyManager ontologyManager, OWLOntology ontology, String base,
			PrefixManager pm, OWLDataFactory dataFactory, OWLNamedIndividual indi, String dataProp)
			throws OWLOntologyCreationException, OWLOntologyStorageException {

		OWLDataProperty dataProperty = dataFactory.getOWLDataProperty(dataProp, pm);

		for (OWLAxiom a : ontology.getAxioms(AxiomType.DATA_PROPERTY_ASSERTION)) {
			{

				if (a.getSignature().contains(dataProperty)) {
					ontologyManager.removeAxiom(ontology, a);

					break;

				}
			}
		}
		ontology.saveOntology();
	}
}
