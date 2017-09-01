package ca.uhn.fhir.example;

import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.Patient;

import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import ca.uhn.fhir.model.primitive.CodeDt;

@ResourceDef(name="Patient")
public class Example32_ExtendedPatient extends Patient {

	@Child(name = "eyeColour")
	@Extension(url="http://acme.org/#extpt", definedLocally = false, isModifier = false)
	private CodeType myEyeColour;

	public CodeType getEyeColour() {
		if (myEyeColour == null) {
			myEyeColour = new CodeType();
		}
		return myEyeColour;
	}

	public void setEyeColour(CodeType theEyeColour) {
		myEyeColour = theEyeColour;
	}
	
}
