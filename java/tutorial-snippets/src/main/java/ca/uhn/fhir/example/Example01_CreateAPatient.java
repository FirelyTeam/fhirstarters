package ca.uhn.fhir.example;

import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Patient;

public class Example01_CreateAPatient {
   public static void main(String[] theArgs) {

      // Create a resource instance
      Patient pat = new Patient();

      // Add a "name" element
      HumanName name = pat.addName();
      name.setFamily("Simpson").addGiven("Homer").addGiven("J");

      // Add an "identifier" element
      Identifier identifier = pat.addIdentifier();
      identifier.setSystem("http://acme.org/MRNs").setValue("7000135");

      // Model is designed to be chained
      pat.addIdentifier().setSystem("http://acme.org/MRNs").setValue("12345");

   }
}
