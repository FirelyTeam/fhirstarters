package ca.uhn.fhir.example;

import org.hl7.fhir.dstu3.model.ContactPoint.ContactPointSystem;
import org.hl7.fhir.dstu3.model.ContactPoint.ContactPointUse;
import org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.dstu3.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

public class Example04_EncodeResource {
	public static void main(String[] theArgs) {

		// Create a Patient
		Patient pat = new Patient();
		pat.addName().setFamily("Simpson").addGiven("Homer").addGiven("J");
		pat.addIdentifier().setSystem("http://acme.org/MRNs").setValue("7000135");
		pat.addTelecom().setUse(ContactPointUse.HOME).setSystem(ContactPointSystem.PHONE).setValue("1 (416) 340-4800");
		pat.setGender(AdministrativeGender.MALE);

		// Create a context
		FhirContext ctx = FhirContext.forDstu3();

		// Create a JSON parser
		IParser parser = ctx.newJsonParser();
		parser.setPrettyPrint(true);

		String encode = parser.encodeResourceToString(pat);
		System.out.println(encode);

	}
}
