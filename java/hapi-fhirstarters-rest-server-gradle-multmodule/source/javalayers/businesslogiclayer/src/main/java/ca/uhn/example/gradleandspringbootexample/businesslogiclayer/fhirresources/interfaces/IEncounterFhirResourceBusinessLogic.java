package ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.interfaces;

import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.annotation.IdParam;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.IdType;

import java.util.List;
import java.util.Optional;

public interface IEncounterFhirResourceBusinessLogic {

   IdType createEncounter(Encounter theEncounter);

   List<Encounter> findEncountersByName(StringDt theLocationName);

   Optional<Encounter> readEncounter(@IdParam IdType theId);

   void updateEncounter(@IdParam IdType theId, Encounter theEncounter);
}
