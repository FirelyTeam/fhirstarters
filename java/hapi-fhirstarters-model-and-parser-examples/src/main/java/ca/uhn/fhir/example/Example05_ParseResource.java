package ca.uhn.fhir.example;

import java.util.List;

import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

public class Example05_ParseResource {
	public static void main(String[] theArgs) {
		
		String resourceBody = "{\"resourceType\":\"Patient\",\"identifier\":[{\"system\":\"http://acme.org/MRNs\",\"value\":\"7000135\"}],\"name\":[{\"family\":[\"Simpson\"],\"given\":[\"Homer\",\"J\"]}]}";

		// Create a context
		FhirContext ctx = FhirContext.forDstu3();
		
		// Create a JSON parser
		IParser parser = ctx.newJsonParser();
		Patient pat = parser.parseResource(Patient.class, resourceBody);
		
		List<Identifier> identifiers = pat.getIdentifier();
		String idSystemString = identifiers.get(0).getSystem();
		String idValueString = identifiers.get(0).getValue();
		
		System.out.println(idSystemString + " " + idValueString);
		
	}
}
