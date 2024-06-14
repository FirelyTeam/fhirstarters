package ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.interfaces;

import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.annotation.IdParam;
import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.IdType;

import java.util.List;
import java.util.Optional;

public interface IAllergyIntoleranceFhirResourceBusinessLogic {

   IdType createAllergyIntolerance(AllergyIntolerance theAllergyIntolerance);

   List<AllergyIntolerance> findAllergyIntoleranceByFilters(StringDt theCriticality);

   Optional<AllergyIntolerance> readAllergyIntolerance(@IdParam IdType theId);

   void updateAllergyIntolerance(@IdParam IdType theId, AllergyIntolerance theAllergyIntolerance);
}
