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
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class treatmentResult
 */
@WebServlet("/treatmentResult")
public class treatmentResult extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public treatmentResult() {
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

		HttpSession session = request.getSession(false);
		String userID = (String) session.getAttribute("userID");

		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology;
		try {
			ontology = ontologyManager
					.loadOntologyFromOntologyDocument(new File(getServletContext().getRealPath("Diabetes.owl")));
			SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
			try {
				String uncontrolled = getUncontrolled(userID, queryEngine);
				String acceptable = getAcceptable(userID, queryEngine);
				String optimal = getOptimal(userID, queryEngine);

				uncontrolled = uncontrolled.replace("null", "");
				acceptable = acceptable.replace("null", "");
				optimal = optimal.replace("null", "");

				session.setAttribute("uncontrolled", uncontrolled);
				session.setAttribute("acceptable", acceptable);
				session.setAttribute("optimal", optimal);

				response.sendRedirect("treatmentResult.jsp");

			} catch (SQWRLException | OWLOntologyCreationException | SWRLParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (OWLOntologyCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public String getUncontrolled(String UserID, SQWRLQueryEngine queryEngine)
			throws SQWRLException, SWRLParseException, OWLOntologyCreationException {

		String message = "<ul>";

		SQWRLResult results = queryEngine.runSQWRLQuery("q5", "#Patient(?p) ^ #hasPatientID(?p, \"" + UserID
				+ "\") ^ #has_Uncontrollable(?p,?uncontrol) -> sqwrl:select(?uncontrol) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (results.next()) {
			message += "<li>";
			String[] arrOfStr = results.getValue("x").toString().split("Diabetes_ontology:");
			message += arrOfStr[1].substring(0, 1).toUpperCase() + arrOfStr[1].substring(1).toLowerCase();
			message += "</li>";

		}

		return message;
	}

	public String getOptimal(String UserID, SQWRLQueryEngine queryEngine)
			throws SQWRLException, SWRLParseException, OWLOntologyCreationException {

		String message = "<ul>";

		SQWRLResult results = queryEngine.runSQWRLQuery("q3", "#Patient(?p) ^ #hasPatientID(?p, \"" + UserID
				+ "\") ^ #has_Optimal(?p,?uncontrol) -> sqwrl:select(?uncontrol) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (results.next()) {
			message += "<li>";
			String[] arrOfStr = results.getValue("x").toString().split("Diabetes_ontology:");
			message += arrOfStr[1].substring(0, 1).toUpperCase() + arrOfStr[1].substring(1).toLowerCase();
			message += "</li>";

		}

		return message;
	}

	public String getAcceptable(String UserID, SQWRLQueryEngine queryEngine)
			throws SQWRLException, SWRLParseException, OWLOntologyCreationException {

		String message = "<ul>";

		SQWRLResult results = queryEngine.runSQWRLQuery("q4", "#Patient(?p) ^ #hasPatientID(?p, \"" + UserID
				+ "\") ^ #has_Acceptable(?p,?uncontrol) -> sqwrl:select(?uncontrol) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (results.next()) {
			message += "<li>";
			String[] arrOfStr = results.getValue("x").toString().split("Diabetes_ontology:");
			message += arrOfStr[1].substring(0, 1).toUpperCase() + arrOfStr[1].substring(1).toLowerCase();
			message += "</li>";
			System.out.println(arrOfStr[1]);

		}

		return message;
	}

}