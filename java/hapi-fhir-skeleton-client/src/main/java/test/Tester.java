package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.ResourceMetadataKeyEnum;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.primitive.IdDt;

public class Tester {

	public static void main(String[] args) {

		Observation obs = new Observation();
		obs.addIdentifier().setValue("aaa");
		
		List<IdDt> list = new ArrayList<IdDt>();
		list.add(new IdDt("http://172.ol"));
		ResourceMetadataKeyEnum.PROFILES.put(obs, list);
		
		System.out.println(FhirContext.forDstu2().newJsonParser().setPrettyPrint(true).encodeResourceToString(obs));
		
	}

}
