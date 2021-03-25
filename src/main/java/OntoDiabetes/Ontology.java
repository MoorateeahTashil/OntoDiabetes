package OntoDiabetes;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
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

public class Ontology {
	
	public static OWLOntologyManager ontologyManager;
	public static OWLOntology ontology;
	public static String defPrefix;
	public static String base;
	public static PrefixManager pm;
	public static OWLDataFactory dataFactory;

	public Ontology() throws OWLOntologyCreationException, OWLOntologyStorageException
	{
		ontologyManager = OWLManager.createOWLOntologyManager();
		ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/java/Diabetes.owl"));
		defPrefix = ontology.getOntologyID().getOntologyIRI() + "#";
		base = "http://www.semanticweb.org/adarsh/ontologies/2021/2/Diabetes_ontology#";
		pm = new DefaultPrefixManager(null, null, base);
		dataFactory = OWLManager.getOWLDataFactory();
	}
	
	public void CreatePatient(String userId, String name, double height, double weight, double bmi,
			String gender, String hasChild) throws OWLOntologyStorageException
	{
		// Adding an instance to class patient
		OWLClass patientClass = dataFactory.getOWLClass(":Patient", pm);
		OWLNamedIndividual patient = dataFactory.getOWLNamedIndividual(":Paitent_" + userId, pm);

		OWLClassAssertionAxiom classAssertion = dataFactory.getOWLClassAssertionAxiom(patientClass, patient);
		ontologyManager.addAxiom(ontology, classAssertion);

		XMLWriterPreferences.getInstance().setUseNamespaceEntities(true);

		// Adding Data Property

		// USERID
		OWLDataProperty hasPatientID = dataFactory.getOWLDataProperty(":hasPatientID", pm);
		OWLDataPropertyAssertionAxiom idPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasPatientID,
				patient, userId);
		ontologyManager.addAxiom(ontology, idPropertyAssertion);

		// FULL NAME
		OWLDataProperty hasFullName = dataFactory.getOWLDataProperty(":hasFullName", pm);
		OWLDataPropertyAssertionAxiom namePropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasFullName,
				patient, name);
		ontologyManager.addAxiom(ontology, namePropertyAssertion);

		// HEIGHT
		OWLDataProperty hasHeight = dataFactory.getOWLDataProperty(":hasHeight", pm);
		OWLDataPropertyAssertionAxiom heightPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasHeight,
				patient, height);
		ontologyManager.addAxiom(ontology, heightPropertyAssertion);

		// WEIGHT
		OWLDataProperty hasWeight = dataFactory.getOWLDataProperty(":hasWeight", pm);
		OWLDataPropertyAssertionAxiom weightPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasWeight,
				patient, weight);
		ontologyManager.addAxiom(ontology, weightPropertyAssertion);

		// BMI
		OWLDataProperty hasBmi = dataFactory.getOWLDataProperty(":hasBmi", pm);
		OWLDataPropertyAssertionAxiom bmiPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasBmi,
				patient, bmi);
		ontologyManager.addAxiom(ontology, bmiPropertyAssertion);

		// Children
		OWLDataProperty hasChildren = dataFactory.getOWLDataProperty(":hasChildren", pm);
		OWLDataPropertyAssertionAxiom childPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasChildren,
				patient, hasChild);
		ontologyManager.addAxiom(ontology, childPropertyAssertion);

		// Gender
		OWLDataProperty hasGender = dataFactory.getOWLDataProperty(":hasGender", pm);
		OWLDataPropertyAssertionAxiom genderPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasGender,
				patient, gender);
		ontologyManager.addAxiom(ontology, genderPropertyAssertion);

		ontologyManager.saveOntology(ontology);
	}
	
}
