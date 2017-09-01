package ca.uhn.fhir.example;

import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IRestfulClient;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Patient;

public interface SampleClient extends IRestfulClient {

   @Create
   MethodOutcome create(@ResourceParam Patient thePatient);

   @Read
   Patient read(@IdParam IdType theId);

}
