package ca.uhn.fhir.example;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.dstu3.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;

public class Example07_ClientReadAndUpdate {
	public static void main(String[] theArgs) {

		// Create a client
		String serverBaseUrl = "http://fhirtest.uhn.ca/baseDstu3";
		FhirContext ctx = FhirContext.forDstu3();
		IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

		// Use the client to read back the new instance using the
		// ID we retrieved from the read
      Patient patient = client
         .read()
         .resource(Patient.class)
         .withId("example")
         .execute();

      // Print the ID of the newly created resource
		System.out.println("Found ID:    " + patient.getId());
		
		// Change the gender
		patient.setGender(patient.getGender() == AdministrativeGender.MALE ?
         AdministrativeGender.FEMALE : AdministrativeGender.MALE);

		// Update the patient
		MethodOutcome outcome = client
         .update()
         .resource(patient)
         .execute();
		
		System.out.println("Now have ID: " + outcome.getId());
	}
}
