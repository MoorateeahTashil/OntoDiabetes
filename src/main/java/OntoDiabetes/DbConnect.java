package OntoDiabetes;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
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

public class DbConnect {

	public static void main(String[] args) throws OWLOntologyCreationException, SQWRLException, SWRLParseException, OWLOntologyStorageException {
		// Create OWLOntology instance using the OWLAPI
		
		
		Ontology o = new Ontology();
		
		
//		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
//		OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/java/Diabetes.owl"));
//		
//		
//		
//		
//	    String defPrefix = ontology.getOntologyID().getOntologyIRI() + "#";
//		String base = "http://www.semanticweb.org/adarsh/ontologies/2021/2/Diabetes_ontology#";
//		PrefixManager pm = new DefaultPrefixManager(null, null, base);
//		OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();
//		OWLClass person = dataFactory.getOWLClass(":Patient", pm);
//		OWLNamedIndividual mary = dataFactory.getOWLNamedIndividual(":Mary", pm);
//		
//	
//		OWLClassAssertionAxiom classAssertion = dataFactory.getOWLClassAssertionAxiom(person, mary);
//		ontologyManager.addAxiom(ontology, classAssertion);
//
//		XMLWriterPreferences.getInstance().setUseNamespaceEntities(true);
//
//		
//		OWLDataProperty indlName = dataFactory.getOWLDataProperty(":hasGender", pm);
//		OWLDataPropertyAssertionAxiom dataPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(indlName, mary,"Female");
//		
//		ontologyManager.addAxiom(ontology,dataPropertyAssertion);
//
//		
//		ontologyManager.saveOntology(ontology);

//		// Create SQWRL query engine using the SWRLAPI
//		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
//
//		// Create and execute a SQWRL query using the SWRLAPI
//		SQWRLResult result = queryEngine.runSQWRLQuery("q1", "#Patient(?x) -> sqwrl:select(?x)");
//
//		// Process the SQWRL result
//
//		while (result.next()) {
//			System.out.println(" Patient Name : " + result.getNamedIndividual("x"));
//
//		}
	}

}
