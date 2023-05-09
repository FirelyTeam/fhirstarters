package ca.uhn.fhir.example;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.ValidationResult;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.r5.model.OperationOutcome;

public class Example21_ValidateResourceString {
   public static void main(String[] args) {

      String input = """
         {
            "resourceType": "Encounter",
            "id": "123"
         }
         """;

      // Create a new validator
      FhirContext ctx = FhirContext.forR5Cached();
      FhirValidator validator = ctx.newValidator();

      // Register a validation module
      validator.registerValidatorModule(new FhirInstanceValidator(ctx));

      // Did we succeed?
      ValidationResult result = validator.validateWithResult(input);
      System.out.println("Success: " + result.isSuccessful());

      // What was the result
      OperationOutcome outcome = (OperationOutcome) result.toOperationOutcome();
      IParser parser = ctx.newJsonParser().setPrettyPrint(true);
      System.out.println(parser.encodeResourceToString(outcome));
   }
}
