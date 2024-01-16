package ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.interfaces;

import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.param.DateParam;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientFhirResourceBusinessLogic {

   List<Patient> findBySearchParameters(
      StringDt theFamilyName,
      StringDt theGivenName,
      DateParam theBirthDate);

   Optional<Patient> readPatient(@IdParam IdType theId);

   /* note the contract.  it is for a single patient (based o the fhir-logical-id), BUT the return is a collection of Patient resources because of the possibility of history-versions for the single Patient */
   List<Patient> findAllHistoryForSingle(@IdParam IdType theId);

   IdType createPatient(Patient thePatient);

   void updatePatient(@IdParam IdType theId, Patient thePatient);

}
