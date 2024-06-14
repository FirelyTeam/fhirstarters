package ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.interfaces;

import ca.uhn.fhir.rest.annotation.IdParam;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Organization;

import java.util.Optional;

public interface IOrganizationFhirResourceBusinessLogic {
   Optional<Organization> readOrganization(@IdParam IdType theId);
}
