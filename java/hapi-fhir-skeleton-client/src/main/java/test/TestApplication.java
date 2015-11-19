package test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.rest.client.IGenericClient;

public class TestApplication {

	/**
	 * This is the Java main method, which gets executed
	 */
	public static void main(String[] args) {

		// Create a context
		FhirContext ctx = FhirContext.forDstu2();

		// Create a client
		IGenericClient client = ctx.newRestfulGenericClient("http://fhirtest.uhn.ca/baseDstu2");

		// Read a patient with the given ID
		Patient patient = client.read().resource(Patient.class).withId("952975").execute();

		// Print the output
		String string = ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(patient);
		System.out.println(string);

		/*
		 * Some more things to try:
		 * 
		 * Read a resource from the server
		 *   Bonus: Display an error if it has been deleted
		 * 
		 * Search for Patient resources with the name “Test” and print the results
		 *   Bonus: Load the second page
		 * 
		 * Create a new Patient resource and upload it to the server
		 *   Bonus: Log the ID that the server assigns to your resource
		 */
		
	}

}
