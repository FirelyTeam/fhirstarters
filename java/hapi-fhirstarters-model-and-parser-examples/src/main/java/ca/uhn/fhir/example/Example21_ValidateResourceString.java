package ca.uhn.fhir.example;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.ValidationResult;
import org.hl7.fhir.dstu3.model.OperationOutcome;

public class Example21_ValidateResourceString {
   public static void main(String[] args) {

      String input = "<Encounter xmlns=\"http://hl7.org/fhir\"></Encounter>";

      // Create a new validator
      FhirContext ctx = FhirContext.forDstu3();
      FhirValidator validator = ctx.newValidator();

      // Did we succeed?
      ValidationResult result = validator.validateWithResult(input);
      System.out.println("Success: " + result.isSuccessful());

      // What was the result
      OperationOutcome outcome = (OperationOutcome) result.toOperationOutcome();
      IParser parser = ctx.newXmlParser().setPrettyPrint(true);
      System.out.println(parser.encodeResourceToString(outcome));
   }
}
