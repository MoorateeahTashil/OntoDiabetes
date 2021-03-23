package OntoDiabetes;



import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;



public class DbConnect {

	public static void main(String[] args) throws OWLOntologyCreationException, SQWRLException, SWRLParseException  {
		// Create OWLOntology instance using the OWLAPI
		 OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		 OWLOntology ontology 
		   = ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/java/Diabetes.owl"));
		 

		 // Create SQWRL query engine using the SWRLAPI
		 SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

		 // Create and execute a SQWRL query using the SWRLAPI
		 SQWRLResult result = queryEngine.runSQWRLQuery("q1","#Patient(?x) -> sqwrl:select(?x)");

		 // Process the SQWRL result

	
    while (result.next()) {
		 System.out.println(" Patient Name : " + result.getNamedIndividual("x"));

      }
	}

}
