package ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.interfaces;

import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.param.DateParam;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientFhirResourceBusinessLogic {

   IdType createPatient(Patient thePatient);

   List<Patient> findBySearchParameters(
      StringDt theFamilyName,
      StringDt theGivenName,
      DateParam theBirthDate);

   Optional<Patient> readPatient(@IdParam IdType theId);

   void updatePatient(@IdParam IdType theId, Patient thePatient);

}
