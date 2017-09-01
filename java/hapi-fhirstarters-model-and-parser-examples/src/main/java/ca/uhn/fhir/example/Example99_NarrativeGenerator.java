package ca.uhn.fhir.example;

import org.hl7.fhir.dstu3.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.narrative.DefaultThymeleafNarrativeGenerator;

public class Example99_NarrativeGenerator {

	public static void main(String[] args) {
		
		// Create an encounter with an invalid status and no class
		Patient pat = new Patient();
		pat.addName().setFamily("Simpson").addGiven("Homer").addGiven("Jay");
		pat.addAddress().addLine("342 Evergreen Terrace").addLine("Springfield");
		pat.addIdentifier().setSystem("http://acme.org/mrns").setValue("12345");
		
		// Create a new context and enable the narrative generator
		FhirContext ctx = FhirContext.forDstu2();
		ctx.setNarrativeGenerator(new DefaultThymeleafNarrativeGenerator());
		
		String res = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(pat);
		System.out.println(res);
	}
	
}
