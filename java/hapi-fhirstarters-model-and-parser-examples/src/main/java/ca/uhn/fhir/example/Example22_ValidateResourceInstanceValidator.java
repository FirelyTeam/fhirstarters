package ca.uhn.fhir.example;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import org.hl7.fhir.dstu3.hapi.ctx.DefaultProfileValidationSupport;
import org.hl7.fhir.dstu3.hapi.validation.FhirInstanceValidator;
import org.hl7.fhir.dstu3.model.Encounter;

public class Example22_ValidateResourceInstanceValidator {
   public static void main(String[] args) {
      // Create an incomplete encounter (status is required)
      Encounter enc = new Encounter();
      enc.addIdentifier().setSystem("http://acme.org/encNums").setValue("12345");

      // Create a new validator
      FhirValidator validator = FhirContext.forDstu3().newValidator();

      // Cache this object! Supplies structure definitions
      DefaultProfileValidationSupport support = new DefaultProfileValidationSupport();

      // Create the validator
      FhirInstanceValidator module = new FhirInstanceValidator(support);
      validator.registerValidatorModule(module);

      // Did we succeed?
      IParser parser = FhirContext.forDstu3().newXmlParser().setPrettyPrint(true);
      System.out.println(parser.encodeResourceToString(validator.validateWithResult(enc).toOperationOutcome()));
   }
}
