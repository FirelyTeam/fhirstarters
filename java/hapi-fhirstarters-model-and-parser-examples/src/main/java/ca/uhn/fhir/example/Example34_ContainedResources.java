package ca.uhn.fhir.example;

import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.StringType;

public class Example34_ContainedResources {

   public static void main(String[] args) {

      // Create an Observation
      Observation obs = new Observation();
      obs.setStatus(Observation.ObservationStatus.FINAL);
      obs.setValue(new StringType("This is a value"));

      // Create a Patient
      Patient pat = new Patient();
      pat.addName().setFamily("Simpson").addGiven("Homer");

      // Assign the Patient to the Observation
      obs.getSubject().setResource(pat);

      FhirContext ctx = FhirContext.forDstu3();
      String output = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(obs);
      System.out.println(output);

   }

}
