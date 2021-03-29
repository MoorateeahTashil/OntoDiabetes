package OntoDiabetes;

import java.io.File;
import java.io.IOException;

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
	public static String errorMessage;

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
		String id = request.getParameter("patientID");

	}

	public void Queries(String UserID) throws SQWRLException, SWRLParseException, OWLOntologyCreationException, OWLOntologyStorageException {
		// Create OWLOntology instance using the OWLAPI

		Ontology o = new Ontology();

		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File(getServletContext().getRealPath("Diabetes.owl")));

		// Create SQWRL query engine using the SWRLAPI
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
		
		
		
		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "#Patient(?x) -> sqwrl:select(?x)");

		// Process the SQWRL result

		while (result.next()) {
			System.out.println(" Patient Name : " + result.getNamedIndividual("x"));

		}
	}

}
