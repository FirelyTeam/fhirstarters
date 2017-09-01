package ca.uhn.fhir.example;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.TimeType;

public class Example30_AddSomeExtensions {
	public static void main(String[] theArgs) {
		Patient pat = new Patient();
		pat.addName().setFamily("Simpson").addGiven("Homer").addGiven("J");

		// Add an extension on the resource
		pat.addExtension()
				.setUrl("http://hl7.org/fhir/StructureDefinition/patient-importance")
				.setValue(new CodeableConcept().setText("Patient is a VIP"));
		
		// Add an extension on a primitive
		pat.getBirthDateElement().setValueAsString("1955-02-22");
		pat.getBirthDateElement().addExtension()
				.setUrl("http://hl7.org/fhir/StructureDefinition/patient-birthTime")
				.setValue(new TimeType("23:30"));

      IParser parser = FhirContext.forDstu3().newJsonParser().setPrettyPrint(true);
      System.out.println(parser.encodeResourceToString(pat));
   }
}
