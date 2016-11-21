package ca.uhn.fhir.example;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.parser.LenientErrorHandler;
import ca.uhn.fhir.parser.StrictErrorHandler;

public class Example12_ParserErrorHandler {
	public static void main(String[] args) {
		String input = "<Encounter xmlns=\"http://hl7.org/fhir\"><AAAA value=\"foo\"/></Encounter>";

		IParser p = FhirContext.forDstu3().newXmlParser();
		
		// Parse with (default) lenient error handler
		p.setParserErrorHandler(new LenientErrorHandler());
		p.parseResource(input);

		// Parse with strict error handler
		p.setParserErrorHandler(new StrictErrorHandler());
		p.parseResource(input);
	}
}
