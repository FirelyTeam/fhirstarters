package ca.uhn.fhir.example;

import org.hl7.fhir.dstu3.model.ContactPoint;
import org.hl7.fhir.dstu3.model.ContactPoint.ContactPointSystem;
import org.hl7.fhir.dstu3.model.ContactPoint.ContactPointUse;
import org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.dstu3.model.Patient;

public class Example02_EnumeratedTypes {
	public static void main(String[] theArgs) {
		
		Patient pat = new Patient();
		
		pat.addName().addFamily("Simpson").addGiven("Homer").addGiven("J");
		pat.addIdentifier().setSystem("http://acme.org/MRNs").setValue("7000135");

		// Enumerated types are provided for many coded elements
		ContactPoint contact = pat.addTelecom();
		contact.setUse(ContactPointUse.HOME);
		contact.setSystem(ContactPointSystem.PHONE);
		contact.setValue("1 (416) 340-4800");
		
		pat.setGender(AdministrativeGender.MALE);
	}
}
