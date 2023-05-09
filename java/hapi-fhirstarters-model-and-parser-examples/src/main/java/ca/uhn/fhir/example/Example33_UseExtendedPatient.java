package ca.uhn.fhir.example;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.r5.model.CodeType;

public class Example33_UseExtendedPatient {

   public static void main(String[] args) {
      Example32_ExtendedPatient pat = new Example32_ExtendedPatient();
      pat.addName().setFamily("Simpson").addGiven("Homer");

      pat.setEyeColour(new CodeType("blue"));

      IParser p = FhirContext.forR5Cached().newXmlParser().setPrettyPrint(true);
      String encoded = p.encodeResourceToString(pat);

      System.out.println(encoded);
   }

}
