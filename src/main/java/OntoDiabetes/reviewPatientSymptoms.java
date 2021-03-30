package OntoDiabetes;

import java.io.File;
import java.io.IOException;
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
 * Servlet implementation class reviewPatientSymptoms
 */
@WebServlet("/reviewPatientSymptoms")
public class reviewPatientSymptoms extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String errorMessage = "";
	public static ArrayList<String> message = new ArrayList<String>();

	/**
	 * Default constructor.
	 */
	public reviewPatientSymptoms() {
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
		errorMessage="";
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
			errorMessage = "NoSymptoms";
		}

		errorMessage = errorMessage.replace("null", "");
	


		HttpSession session = request.getSession(false);

		session.setAttribute("errorMessage", errorMessage);
		
		
		response.sendRedirect("reviewPatientSymptoms.jsp?patientid=" + id);

	}

	public void Queries(String UserID)
			throws SQWRLException, SWRLParseException, OWLOntologyCreationException, OWLOntologyStorageException {
		// Create OWLOntology instance using the OWLAPI

		ArrayList<String> ResultArray = new ArrayList<String>();

		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = ontologyManager
				.loadOntologyFromOntologyDocument(new File(getServletContext().getRealPath("Diabetes.owl")));

		// Create SQWRL query engine using the SWRLAPI
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"#Patient(?p) ^ #has_symptoms(?p, #RelativeDiabetes) -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result.next()) {
			ResultArray.add(result.getNamedIndividual("x").toString());
		}

		for (String element : ResultArray) {
			if (element.endsWith("Patient_" + UserID)) {
				message.add("Patient has first degree relative with diabetes.");
			}
		}

		ResultArray.clear();
		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result1 = queryEngine.runSQWRLQuery("q2",
				"#Patient(?p) ^ #has_symptoms(?p, #Hypertension) -> sqwrl:select(?p)  ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result1.next()) {
			ResultArray.add(result1.getNamedIndividual("x").toString());
		}

		for (String element : ResultArray) {
			if (element.endsWith("Patient_" + UserID)) {
				message.add("Patient has HyperTension.");
			}
		}

		ResultArray.clear();
		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result2 = queryEngine.runSQWRLQuery("q3",
				"#Patient(?p) ^ #has_symptoms(?p, #Poor_obstettric_history) ^ #hasGender(?p, \"Female\") -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result2.next()) {
			ResultArray.add(result2.getNamedIndividual("x").toString());
		}

		for (String element : ResultArray) {
			if (element.endsWith("Patient_" + UserID)) {
				message.add("Patient has a poor obstetric history.");
			}
		}

		ResultArray.clear();
		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result3 = queryEngine.runSQWRLQuery("q4",
				"#Patient(?p) ^ #has_symptoms(?p, #Polyuria) -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result3.next()) {
			ResultArray.add(result3.getNamedIndividual("x").toString());
		}

		for (String element : ResultArray) {
			if (element.endsWith("Patient_" + UserID)) {
				message.add("Patient has Polyuria.");
			}
		}

		ResultArray.clear();
		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result4 = queryEngine.runSQWRLQuery("q5",
				"#Patient(?p) ^ #has_symptoms(?p, #Polydipsia) -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result4.next()) {
			ResultArray.add(result4.getNamedIndividual("x").toString());
		}

		for (String element : ResultArray) {
			if (element.endsWith("Patient_" + UserID)) {
				message.add("Patient has Polydipsia.");
			}
		}

		ResultArray.clear();
		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result5 = queryEngine.runSQWRLQuery("q6",
				"#Patient(?p) ^ #hasBmi(?p,?x) ^ swrlb:greaterThan(?x , 24) -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result5.next()) {
			ResultArray.add(result5.getNamedIndividual("x").toString());
		}

		for (String element : ResultArray) {
			if (element.endsWith("Patient_" + UserID)) {
				message.add("Patient is overweight.");
			}
		}

		ResultArray.clear();
		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result6 = queryEngine.runSQWRLQuery("q7",
				"#Patient(?p) ^ #has_symptoms(?p, #Blurred_vision) -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result6.next()) {
			ResultArray.add(result6.getNamedIndividual("x").toString());
		}

		for (String element : ResultArray) {
			if (element.endsWith("Patient_" + UserID)) {
				message.add("Patient has blurred vision.");
			}
		}

		ResultArray.clear();
		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result7 = queryEngine.runSQWRLQuery("q8",
				"#Patient(?p) ^ #has_symptoms(?p, #Fatigue) -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result7.next()) {
			ResultArray.add(result7.getNamedIndividual("x").toString());
		}

		for (String element : ResultArray) {
			if (element.endsWith("Patient_" + UserID)) {
				message.add("Patient has been experiencing fatigue lately.");
			}
		}

		ResultArray.clear();
		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result8 = queryEngine.runSQWRLQuery("q9",
				"#Patient(?p) ^ #has_symptoms(?p, #Tingling_hands_and_feet) -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result8.next()) {
			ResultArray.add(result8.getNamedIndividual("x").toString());
		}

		for (String element : ResultArray) {
			if (element.endsWith("Patient_" + UserID)) {
				message.add("Patient is feeling unusual tingling in hands and feet.");
			}
		}

		ResultArray.clear();
		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result9 = queryEngine.runSQWRLQuery("q10",
				"#Patient(?p) ^ #has_symptoms(?p, #Patches_of_dark_skin) ^ #has_symptoms(?p, #Frequent_infections) -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result9.next()) {
			ResultArray.add(result9.getNamedIndividual("x").toString());
		}

		for (String element : ResultArray) {
			if (element.endsWith("Patient_" + UserID)) {
				message.add("Patient has dark patches and is having frequent infections.");
			}
		}

		ResultArray.clear();
		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result10 = queryEngine.runSQWRLQuery("q11",
				"#Patient(?p) ^ #has_symptoms(?p, #Slow-healing_sores_and_wounds) -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result10.next()) {
			ResultArray.add(result10.getNamedIndividual("x").toString());
		}

		for (String element : ResultArray) {
			if (element.endsWith("Patient_" + UserID)) {
				message.add("Patient is experiencing slow healing in sores and wound.");
			}
		}

		ResultArray.clear();
		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result11 = queryEngine.runSQWRLQuery("q12",
				"#Patient(?p) ^ #has_symptoms(?p, #Weight_loss) ^ #has_symptoms(?p, #Increased_hunger) -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result11.next()) {
			ResultArray.add(result11.getNamedIndividual("x").toString());
		}

		for (String element : ResultArray) {
			if (element.endsWith("Patient_" + UserID)) {
				message.add("Patient having weight loss despite having increased hunger.");
			}
		}
	}

}
