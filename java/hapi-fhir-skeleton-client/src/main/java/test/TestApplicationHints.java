package test;

import org.hl7.fhir.instance.model.api.IIdType;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;
import ca.uhn.fhir.model.primitive.DateDt;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;

public class TestApplicationHints {

	/*
	 * This class contains hints for the tasks outlined in TestApplication
	 */
	
	public static void step1_read_a_resource() {
		
		FhirContext ctx = FhirContext.forDstu2();
		IGenericClient client = ctx.newRestfulGenericClient("http://fhirtest.uhn.ca/baseDstu2");
		
		Patient patient;
		try {
			// Try changing the ID from 952975 to 999999999999
			patient = client.read().resource(Patient.class).withId("952975").execute();
		} catch (ResourceNotFoundException e) {
			System.out.println("Resource not found!");
			return;
		}
		
		String string = ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(patient);
		System.out.println(string);
		
	}

	
	public static void step2_search_for_patients_named_test() {
		FhirContext ctx = FhirContext.forDstu2();
		IGenericClient client = ctx.newRestfulGenericClient("http://fhirtest.uhn.ca/baseDstu2");
		
		ca.uhn.fhir.model.dstu2.resource.Bundle results = client
			.search()
			.forResource(Patient.class)
			.where(Patient.NAME.matches().value("test"))
			.returnBundle(ca.uhn.fhir.model.dstu2.resource.Bundle.class)
			.execute();
		
		System.out.println("First page: ");
		System.out.println(ctx.newXmlParser().encodeResourceToString(results));
		
		// Load the next page
		ca.uhn.fhir.model.dstu2.resource.Bundle nextPage = client
			.loadPage()
			.next(results)
			.execute();

		System.out.println("Next page: ");
		System.out.println(ctx.newXmlParser().encodeResourceToString(nextPage));

	}

	
	public static void step3_create_patient() {
		// Create a patient
		Patient newPatient = new Patient();

		// Populate the patient with fake information
		newPatient
			.addName()
				.addFamily("DevDays2015")
				.addGiven("John")
				.addGiven("Q");
		newPatient
			.addIdentifier()
				.setSystem("http://acme.org/mrn")
				.setValue("1234567");
		newPatient.setGender(AdministrativeGenderEnum.MALE);
		newPatient.setBirthDate(new DateDt("2015-11-18"));
		
		// Create a client
		FhirContext ctx = FhirContext.forDstu2();
		IGenericClient client = ctx.newRestfulGenericClient("http://fhirtest.uhn.ca/baseDstu2");
		
		// Create the resource on the server
		MethodOutcome outcome = client
			.create()
			.resource(newPatient)
			.execute();
		
		// Log the ID that the server assigned
		IIdType id = outcome.getId();
		System.out.println("Created patient, got ID: " + id);
	}
	
	public static void main(String[] args) {
//		step1_read_a_resource();
//		step2_search_for_patients_named_test();
//		step3_create_patient();
	}
	
}
