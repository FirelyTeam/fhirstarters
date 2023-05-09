package ca.uhn.fhir.example;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.parser.StrictErrorHandler;
import org.hl7.fhir.instance.model.api.IBaseResource;

public class Example35_ParserErrorHandler {

   public static void main(String[] args) {

      String input =
         "{" +
            "\"resourceType\": \"Patient\"," +
            "\"foo\": \"bar\"" +
            "}";

      FhirContext ctx = FhirContext.forR5Cached();
      IParser parser = ctx.newJsonParser();
      parser.setParserErrorHandler(new StrictErrorHandler());
      IBaseResource output = parser.parseResource(input);
   }

}
