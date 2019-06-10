package ca.uhn.example.interceptor;

import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;
import ca.uhn.fhir.rest.server.interceptor.InterceptorAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LegacyLoggingInterceptor extends InterceptorAdapter {
   private static final Logger ourLog = LoggerFactory.getLogger(LegacyLoggingInterceptor.class);

   @Override
   public boolean incomingRequestPostProcessed(RequestDetails theRequest,
                                               HttpServletRequest theSrvRequest,
                                               HttpServletResponse theSrvResponse) {

      ourLog.info("Handling {} client operation on ID {}", theRequest.getRequestType(), theRequest.getId());

      return true; // Processing should continue
   }
}
