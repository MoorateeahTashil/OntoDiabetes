package OntoDiabetes;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

public class testing {
	public static Connection con;
	public static String email = "patient@gmail.com";

	public static void main(String[] args) throws SQLException, ClassNotFoundException, OWLOntologyCreationException,
			SQWRLException, SWRLParseException, OWLOntologyStorageException {
	
//		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
//		OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/java/Diabetes.owl"));
//		String base = "http://www.semanticweb.org/adarsh/ontologies/2021/2/Diabetes_ontology#";
//		PrefixManager pm = new DefaultPrefixManager(null, null, base);
//		OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();
//		OWLNamedIndividual patient = dataFactory.getOWLNamedIndividual(":Patient_1" , pm);
//		
//		
//		SWRLRuleEngine swrlRuleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
//		SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
//		
//		removeObjectProperty(ontologyManager,ontology,base,pm, dataFactory,patient,":has_Optimal");
//		removeObjectProperty(ontologyManager,ontology,base,pm, dataFactory,patient,":has_Acceptable");
//		removeObjectProperty(ontologyManager,ontology,base,pm, dataFactory,patient,":has_Uncontrollable");
//		removeObjectProperty(ontologyManager,ontology,base,pm, dataFactory,patient,":has_complications");
//		removeObjectProperty(ontologyManager,ontology,base,pm, dataFactory,patient,":take_medications");
		
		String test = "\"1\"^^xsd:integer";
	        String[] arrOfStr = test.split("\"\\^\\^xsd:integer");
	        String[] arrOfStr1 = arrOfStr[0].split("\"");
			


		System.out.print(arrOfStr1[1]);

	}

	public static void removeDataProperty() throws OWLOntologyCreationException, OWLOntologyStorageException {

		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/java/Diabetes.owl"));

		String base = "http://www.semanticweb.org/adarsh/ontologies/2021/2/Diabetes_ontology#";
		PrefixManager pm = new DefaultPrefixManager(null, null, base);
		OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();
		OWLNamedIndividual indi = dataFactory.getOWLNamedIndividual(":Patient_1", pm);

		OWLDataProperty hasHeight = dataFactory.getOWLDataProperty(":hasWeight", pm);

		for (OWLAxiom a : ontology.getAxioms(AxiomType.DATA_PROPERTY_ASSERTION)) {
			{

				if (a.getSignature().contains(hasHeight)) {
					ontologyManager.removeAxiom(ontology, a); // What should I use instead of ???? ß

					break;

				}
			}
		}
		ontology.saveOntology();
	}
	
	
	public static void removeObjectProperty(OWLOntologyManager ontologyManager, OWLOntology ontology, String base,
			PrefixManager pm, OWLDataFactory dataFactory, OWLNamedIndividual indi, String objProp)
			throws OWLOntologyCreationException, OWLOntologyStorageException {

		OWLObjectProperty objectProperty = dataFactory.getOWLObjectProperty(objProp, pm);

		for (OWLAxiom a : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION)) {
			{

				if (a.getSignature().contains(objectProperty)) {
					System.out.println("dsa");
					ontologyManager.removeAxiom(ontology, a);

				

				}
			}
		}
		ontology.saveOntology();
	}
}
