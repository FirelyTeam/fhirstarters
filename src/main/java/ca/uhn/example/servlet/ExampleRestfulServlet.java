package ca.uhn.example.servlet;

import java.util.ArrayList;
import java.util.List;

import ca.uhn.example.provider.OrganizationResourceProvider;
import ca.uhn.example.provider.PatientResourceProvider;
import ca.uhn.example.provider.ResearchSubjectResourceProvider;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.narrative.DefaultThymeleafNarrativeGenerator;
import ca.uhn.fhir.narrative.INarrativeGenerator;
import ca.uhn.fhir.rest.openapi.OpenApiInterceptor;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;

/**
 * This servlet is the actual FHIR server itself
 */
public class ExampleRestfulServlet extends RestfulServer {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public ExampleRestfulServlet() {
		super(FhirContext.forR5Cached()); // This is an R5 server
	}
	
	/**
	 * This method is called automatically when the
	 * servlet is initializing.
	 */
	@Override
	public void initialize() {
		/*
		 * Two resource providers are defined. Each one handles a specific
		 * type of resource.
		 */
		List<IResourceProvider> providers = new ArrayList<>();
		providers.add(new PatientResourceProvider());
		providers.add(new OrganizationResourceProvider());
		ResearchSubjectResourceProvider researchSubjectResourceProvider = new ResearchSubjectResourceProvider();
		providers.add(researchSubjectResourceProvider);
		setResourceProviders(providers);
		

		/*
		 * Use a narrative generator. This is a completely optional step, 
		 * but can be useful as it causes HAPI to generate narratives for
		 * resources which don't otherwise have one.
		 */
		INarrativeGenerator narrativeGen = new DefaultThymeleafNarrativeGenerator();
		getFhirContext().setNarrativeGenerator(narrativeGen);

		/*
		 * Use nice coloured HTML when a browser is used to request the content
		 */
		registerInterceptor(new ResponseHighlighterInterceptor());
		OpenApiInterceptor openApiInterceptor = new OpenApiInterceptor();
      	registerInterceptor(openApiInterceptor);

		
	}

}
