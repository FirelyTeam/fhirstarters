package ca.uhn.fhir.example;

import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

public class Example99_Extensions {

	public static void main(String[] args) {
		Patient pat = new Patient();
		pat.addName().addFamily("Simpson").addGiven("Homer");
		
		String url = "http://acme.org#eyeColour";
		pat.addExtension().setUrl(url).setValue(new CodeType("blue"));
		
		IParser p = FhirContext.forDstu3().newXmlParser().setPrettyPrint(true);
		String encoded = p.encodeResourceToString(pat);
		
		System.out.println(encoded);
	}
	
}
