package ca.uhn.fhir.examples.overlay;

import ca.uhn.fhir.context.FhirVersionEnum;
import ca.uhn.fhir.to.FhirTesterMvcConfig;
import ca.uhn.fhir.to.TesterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * This spring config file configures the web testing module. It serves two
 * purposes:
 * 1. It imports FhirTesterMvcConfig, which is the spring config for the
 * tester itself
 * 2. It tells the tester which server(s) to talk to, via the testerConfig()
 * method below
 */
@Configuration
@Import(FhirTesterMvcConfig.class)
public class FhirTesterConfig {

   /**
    * This bean tells the testing webpage which servers it should configure itself
    * to communicate with. In this example we configure it to talk to the local
    * server, as well as one public server. If you are creating a project to
    * deploy somewhere else, you might choose to only put your own server's
    * address here.
    */
   @Bean
   public TesterConfig testerConfig() {
      TesterConfig retVal = new TesterConfig();
      retVal
         .addServer()
         .withId("home3")
         .withFhirVersion(FhirVersionEnum.DSTU3)
         .withBaseUrl("http://fhirtest.uhn.ca/baseDstu3")
         .withName("UHN/HAPI Server (STU3 FHIR)")
         .addServer()
         .withId("hi3")
         .withFhirVersion(FhirVersionEnum.DSTU3)
         .withBaseUrl("http://test.fhir.org/r3")
         .withName("Health Intersections (STU3 FHIR)");

      return retVal;
   }


}
