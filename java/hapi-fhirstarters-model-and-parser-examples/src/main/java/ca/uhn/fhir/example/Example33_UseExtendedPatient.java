package ca.uhn.fhir.example;

import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

public class Example33_UseExtendedPatient {

	public static void main(String[] args) {
      Example32_ExtendedPatient pat = new Example32_ExtendedPatient();
		pat.addName().setFamily("Simpson").addGiven("Homer");

		pat.setEyeColour(new CodeType("blue"));

		IParser p = FhirContext.forDstu3().newXmlParser().setPrettyPrint(true);
		String encoded = p.encodeResourceToString(pat);
		
		System.out.println(encoded);
	}
	
}
