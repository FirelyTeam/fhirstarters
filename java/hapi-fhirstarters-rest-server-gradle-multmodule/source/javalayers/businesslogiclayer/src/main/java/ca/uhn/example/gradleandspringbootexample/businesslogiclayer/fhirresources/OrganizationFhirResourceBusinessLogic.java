package ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources;

import ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.interfaces.IOrganizationFhirResourceBusinessLogic;
import ca.uhn.fhir.rest.annotation.IdParam;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Organization;

import java.util.Optional;

public final class OrganizationFhirResourceBusinessLogic implements IOrganizationFhirResourceBusinessLogic {

   public Optional<Organization> readOrganization(@IdParam IdType theId) {

      Optional<Organization> returnItem = Optional.empty();

      /*
       * We only support one organization, so the following
       * exception causes an HTTP 404 response if the
       * ID of "1" isn't used.
       */
      if ("1".equals(theId.getValue())) {

         Organization retVal = new Organization();
         retVal.setId("1");
         retVal.addIdentifier().setSystem("urn:example:orgs").setValue("FooOrganization");
         retVal.addAddress().addLine("123 Fake Street").setCity("Toronto");
         retVal.addTelecom().setUse(ContactPoint.ContactPointUse.WORK).setValue("1-888-123-4567");

         returnItem = Optional.of(retVal);
      }

      return returnItem;
   }

}
