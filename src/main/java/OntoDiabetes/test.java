package OntoDiabetes;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

public class test {
	public static Connection con;
	public static String email = "patient@gmail.com";
	
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException, OWLOntologyCreationException, SQWRLException, SWRLParseException {
		ArrayList<String> ResultArray = new ArrayList<String>();


		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/java/Diabetes.owl"));
		// Create SQWRL query engine using the SWRLAPI
		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

		// Create and execute a SQWRL query using the SWRLAPI
		SQWRLResult result = queryEngine.runSQWRLQuery("q1",
				"#Patient(?p) ^ #has_symptoms(?p, #RelativeDiabetes) -> sqwrl:select(?p) ^ sqwrl:columnNames(\"x\")");

		// Process the SQWRL result

		while (result.next()) {
			ResultArray.add(result.getNamedIndividual("test").toString());
		}

		for (String element : ResultArray) {
			if (element.endsWith("Patient_" + 1)) {
				System.out.print("Patient has first degree relative with diabetes");
			}
		}
	}

}
