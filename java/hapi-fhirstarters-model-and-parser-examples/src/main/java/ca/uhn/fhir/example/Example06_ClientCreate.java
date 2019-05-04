package ca.uhn.fhir.example;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.Patient;

public class Example06_ClientCreate {
   public static void main(String[] theArgs) {

      Patient pat = new Patient();
      pat.addName().setFamily("Simpson").addGiven("Homer").addGiven("J");
      pat.addIdentifier().setSystem("http://acme.org/MRNs").setValue("7000135");
      pat.setGender(AdministrativeGender.MALE);

      // Create a context
      FhirContext ctx = FhirContext.forR4();

      // Create a client
      String serverBaseUrl = "http://hapi.fhir.org/baseR4";
      IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

      // Use the client to store a new resource instance
      MethodOutcome outcome = client
         .create()
         .resource(pat)
         .execute();

      // Print the ID of the newly created resource
      System.out.println(outcome.getId());
   }
}
