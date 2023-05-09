package ca.uhn.fhir.example;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r5.model.Bundle;
import org.hl7.fhir.r5.model.Patient;

import ca.uhn.fhir.context.FhirContext;

public class Example08_ClientSearch {
   public static void main(String[] theArgs) {
      FhirContext ctx = FhirContext.forR5Cached();
      IGenericClient client = ctx.newRestfulGenericClient("https://hapi.fhir.org/baseR5");

      // Build a search and execute it
      Bundle response = client.search()
         .forResource(Patient.class)
         .where(Patient.NAME.matches().value("Test"))
         .and(Patient.BIRTHDATE.before().day("2014-01-01"))
         .count(100)
         .returnBundle(Bundle.class)
         .execute();

      // How many resources did we find?
      System.out.println("Responses: " + response.getTotal());

      // Print the ID of the first one
      System.out.println("First response ID: " + response.getEntry().get(0).getResource().getId());
   }
}
