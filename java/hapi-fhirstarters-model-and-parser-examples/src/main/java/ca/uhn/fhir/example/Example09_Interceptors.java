package ca.uhn.fhir.example;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.CookieInterceptor;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.dstu3.model.Patient;

public class Example09_Interceptors {
	public static void main(String[] theArgs) {

		// Create a client
      IGenericClient client = FhirContext.forDstu3().newRestfulGenericClient("http://fhirtest.uhn.ca/baseDstu3");

      // Register some interceptors
      client.registerInterceptor(new CookieInterceptor("mycookie=Chips Ahoy"));
      client.registerInterceptor(new LoggingInterceptor());

      // Read a Patient
      Patient patient = client.read().resource(Patient.class).withId("example").execute();

		// Change the gender
		patient.setGender(patient.getGender() == AdministrativeGender.MALE ? AdministrativeGender.FEMALE : AdministrativeGender.MALE);

		// Update the patient
		MethodOutcome outcome = client.update().resource(patient).execute();
		
		System.out.println("Now have ID: " + outcome.getId());
	}
}
