package ca.uhn.example.interceptor;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewLoggingInterceptor {
   private static final Logger ourLog = LoggerFactory.getLogger(NewLoggingInterceptor.class);

   @Hook(Pointcut.SERVER_INCOMING_REQUEST_POST_PROCESSED)
   public boolean logRequestDetails(RequestDetails theRequest) {

      ourLog.info("Handling {} client operation on ID {}", theRequest.getRequestType(), theRequest.getId());

      return true; // Processing should continue
   }

}
