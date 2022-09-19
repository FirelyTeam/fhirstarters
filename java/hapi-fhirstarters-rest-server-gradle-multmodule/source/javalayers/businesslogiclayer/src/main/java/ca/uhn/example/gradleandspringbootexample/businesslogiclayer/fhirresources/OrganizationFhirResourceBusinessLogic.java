package ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources;

import ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.interfaces.IOrganizationFhirResourceBusinessLogic;
import ca.uhn.fhir.rest.annotation.IdParam;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Organization;

import java.util.Optional;

public final class OrganizationFhirResourceBusinessLogic implements IOrganizationFhirResourceBusinessLogic {

   public static final String DEMO_ORGANIZATION_ONE_FHIR_LOGICAL_ID = "101";

   public Optional<Organization> readOrganization(@IdParam IdType theId) {

      Optional<Organization> returnItem = Optional.empty();

      /*
       * We only support one organization, so the following
       * exception causes an HTTP 404 response if the
       * ID of "101" isn't used.
       */
      String justTheFhirLogicalId = theId.getIdPart();
      if (DEMO_ORGANIZATION_ONE_FHIR_LOGICAL_ID.equals(justTheFhirLogicalId)) {

         Organization retVal = new Organization();
         retVal.setId(DEMO_ORGANIZATION_ONE_FHIR_LOGICAL_ID);
         retVal.addIdentifier().setSystem("urn:example:orgs").setValue("FooOrganization");
         retVal.addAddress().addLine("123 Fake Street").setCity("Toronto");
         retVal.addTelecom().setUse(ContactPoint.ContactPointUse.WORK).setValue("1-888-123-4567");

         returnItem = Optional.of(retVal);
      }

      return returnItem;
   }

}
