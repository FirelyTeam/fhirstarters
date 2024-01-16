package ca.uhn.example.gradleandspringbootexample.resourceproviderlayer;

import ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.interfaces.IOrganizationFhirResourceBusinessLogic;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * This is a simple resource provider which only implements "read/GET" methods, but
 * which uses a custom subclassed resource definition to add statically bound
 * extensions.
 * <p>
 * See the Organization definition to see how the custom resource
 * definition works.
 */
public class OrganizationResourceProvider implements IResourceProvider {

   private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationResourceProvider.class);

   private IOrganizationFhirResourceBusinessLogic organizationFhirResourceBusinessLogic;

   public OrganizationResourceProvider(IOrganizationFhirResourceBusinessLogic organizationFhirResourceBusinessLogic) {

      if (null == organizationFhirResourceBusinessLogic) {
         throw new IllegalArgumentException("IOrganizationFhirResourceBusinessLogic is null");
      }
      this.organizationFhirResourceBusinessLogic = organizationFhirResourceBusinessLogic;

   }


   /**
    * The getResourceType method comes from IResourceProvider, and must be overridden to indicate what type of resource this provider supplies.
    *
    * @return
    */
   @Override
   public Class<Organization> getResourceType() {
      return Organization.class;
   }

   /**
    * The "@Read" annotation indicates that this method supports the read operation. It takes one argument, the Resource type being returned.
    *
    * @param theId The read operation takes one parameter, which must be of type IdDt and must be annotated with the "@Read.IdParam" annotation.
    * @return Returns a resource matching this identifier, or null if none exists.
    */
   @Read()
   public Organization getResourceById(@IdParam IdType theId) {

      Optional<Organization> foundOrganization = this.organizationFhirResourceBusinessLogic.readOrganization(theId);

      if (foundOrganization.isEmpty()) {
         String errorMsg = null == theId ? "theId is null" : theId.toString();
         LOGGER.error(errorMsg);
         throw new ResourceNotFoundException(errorMsg);
      }

      return foundOrganization.get();
   }
}
