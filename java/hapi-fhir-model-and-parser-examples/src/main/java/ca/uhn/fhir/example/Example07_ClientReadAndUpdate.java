package ca.uhn.fhir.example;

import org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.dstu3.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.IGenericClient;

public class Example07_ClientReadAndUpdate {
	public static void main(String[] theArgs) {

		// Create a client
		String serverBaseUrl = "http://fhirtest.uhn.ca/baseDstu3";
		FhirContext ctx = FhirContext.forDstu3();
		IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

		// Use the client to read back the new instance using the
		// ID we retrieved from the read
		Patient patient = client.read(Patient.class, "190002");
	
		// Print the ID of the newly created resource
		System.out.println("Found ID:    " + patient.getId());
		
		// Change the gender and send an update to the server
		patient.setGender(AdministrativeGender.FEMALE);
		MethodOutcome outcome = client.update().resource(patient).execute();
		
		System.out.println("Now have ID: " + outcome.getId());
	}
}
