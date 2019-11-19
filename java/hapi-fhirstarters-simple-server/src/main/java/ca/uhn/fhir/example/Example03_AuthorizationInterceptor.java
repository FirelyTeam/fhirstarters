package ca.uhn.fhir.example;

import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.interceptor.auth.AuthorizationInterceptor;
import ca.uhn.fhir.rest.server.interceptor.auth.IAuthRule;
import ca.uhn.fhir.rest.server.interceptor.auth.RuleBuilder;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Observation;

import java.util.List;

public class Example03_AuthorizationInterceptor extends AuthorizationInterceptor {
   @Override
   public List<IAuthRule> buildRuleList(RequestDetails theRequestDetails) {

      // Process this header
      String authHeader = theRequestDetails.getHeader("Authorization");

      RuleBuilder builder = new RuleBuilder();
      builder
         .allow().metadata().andThen()
         .allow().read().allResources().withAnyId().andThen()
         .allow().write().resourcesOfType(Observation.class).inCompartment("Patient", new IdType("Patient/123"));

      return builder.build();
   }

}
