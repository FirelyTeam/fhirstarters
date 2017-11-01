package ca.uhn.fhir.example;
import java.util.List;

import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.StringParam;

public class Example01_StubResourceProvider implements IResourceProvider {

   public Class<? extends IBaseResource> getResourceType() {
		return Patient.class;
	}

	@Read
	public Patient read(@IdParam IdType theId) {
		return null; // populate this
	}
	
	@Create
	void create(@ResourceParam Patient thePatient) {
		// save the resource
	}
	
	@Search
	List<Patient> search(
			@OptionalParam(name="family") StringParam theFamily,
			@OptionalParam(name="given") StringParam theGiven
			) {
		return null; // populate this
	}
}
