package ca.uhn.example.gradleandspringbootexample.toplayers.springboottoplayers.quickexampleserver.compositionroot;

import ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.AllergyIntoleranceFhirResourceBusinessLogic;
import ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.OrganizationFhirResourceBusinessLogic;
import ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.interfaces.IAllergyIntoleranceFhirResourceBusinessLogic;
import ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.interfaces.IOrganizationFhirResourceBusinessLogic;
import ca.uhn.example.gradleandspringbootexample.resourceproviderlayer.AllergyIntoleranceResourceProvider;
import ca.uhn.example.gradleandspringbootexample.resourceproviderlayer.OrganizationResourceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("checkstyle:DesignForExtension")  // @Configuration cannot be final
@Configuration
public class CompositionRoot {

   /*
      You can define your items for the IoC container in the di.xml OR via "CompositionRoot", aka "java config" in the java world
      See : https://blog.ploeh.dk/2011/07/28/CompositionRoot/

      This place is called the Composition Root of the application and defined like this:

      A Composition Root is a (preferably) unique location in an application where modules are composed together.

      This means that all the application code relies solely on Constructor Injection (or other injection patterns), but is never composed. Only at the entry point of the application is the entire object graph finally composed.

      The Composition Root is an application infrastructure component.

      Only applications should have Composition Roots. Libraries and frameworks shouldn't.
    */

   @Bean
   public OrganizationResourceProvider addOrganizationResourceProviderToIocContainer(IOrganizationFhirResourceBusinessLogic organizationFhirResourceBusinessLogic) {
      return new OrganizationResourceProvider(organizationFhirResourceBusinessLogic);
   }

   @Bean
   public AllergyIntoleranceResourceProvider addAllergyIntoleranceResourceProviderToIocContainer(IAllergyIntoleranceFhirResourceBusinessLogic allergyIntoleranceFhirResourceBusinessLogic) {
      return new AllergyIntoleranceResourceProvider(allergyIntoleranceFhirResourceBusinessLogic);
   }

   @Bean
   public IAllergyIntoleranceFhirResourceBusinessLogic addIAllergyIntoleranceFhirResourceBusinessLogicToIocContainer() {
      return new AllergyIntoleranceFhirResourceBusinessLogic();
   }

   @Bean
   public IOrganizationFhirResourceBusinessLogic addIOrganizationFhirResourceBusinessLogicToIocContainer() {
      return new OrganizationFhirResourceBusinessLogic();
   }

}
