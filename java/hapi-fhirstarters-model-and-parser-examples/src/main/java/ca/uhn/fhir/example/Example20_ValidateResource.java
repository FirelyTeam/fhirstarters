package ca.uhn.fhir.example;

import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.r5.model.Encounter;
import org.hl7.fhir.r5.model.OperationOutcome;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.ValidationResult;

public class Example20_ValidateResource {
	public static void main(String[] args) {
		
		// Create an incomplete encounter (status is required)
		Encounter enc = new Encounter();
		enc.addIdentifier().setSystem("http://acme.org/encNums").setValue("12345");
		
		// Create a new validator
		FhirContext ctx = FhirContext.forR5Cached();
		FhirValidator validator = ctx.newValidator();

      // Register a validation module
      validator.registerValidatorModule(new FhirInstanceValidator(ctx));
		
		// Did we succeed?
		ValidationResult result = validator.validateWithResult(enc);
		System.out.println("Success: " + result.isSuccessful());
		
		// What was the result
		OperationOutcome outcome = (OperationOutcome) result.toOperationOutcome();
		IParser parser = ctx.newJsonParser().setPrettyPrint(true);
		System.out.println(parser.encodeResourceToString(outcome));
	}
}
