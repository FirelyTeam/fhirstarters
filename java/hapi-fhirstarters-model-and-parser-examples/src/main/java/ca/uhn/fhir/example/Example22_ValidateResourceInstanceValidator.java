package ca.uhn.fhir.example;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.r4.model.Encounter;

public class Example22_ValidateResourceInstanceValidator {
   public static void main(String[] args) {
      // Create an incomplete encounter (status is required)
      Encounter enc = new Encounter();
      enc.addIdentifier().setSystem("http://acme.org/encNums").setValue("12345");

      // Create a new validator
      FhirContext fhirContext = FhirContext.forR4();
      FhirValidator validator = fhirContext.newValidator();

      // Cache this object! Supplies structure definitions
      DefaultProfileValidationSupport support = new DefaultProfileValidationSupport(fhirContext);

      // Create the validator
      FhirInstanceValidator module = new FhirInstanceValidator(support);
      validator.registerValidatorModule(module);

      // Did we succeed?
      IParser parser = FhirContext.forR4().newXmlParser().setPrettyPrint(true);
      System.out.println(parser.encodeResourceToString(validator.validateWithResult(enc).toOperationOutcome()));
   }
}
