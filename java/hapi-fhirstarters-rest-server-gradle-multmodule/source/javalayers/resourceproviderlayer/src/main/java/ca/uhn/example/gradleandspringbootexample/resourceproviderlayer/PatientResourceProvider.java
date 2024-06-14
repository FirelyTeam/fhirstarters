package ca.uhn.example.gradleandspringbootexample.resourceproviderlayer;

import ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.interfaces.IPatientFhirResourceBusinessLogic;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.History;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.annotation.Update;
import ca.uhn.fhir.rest.api.Constants;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * This is a resource provider which stores Patient resources in memory using a HashMap. This is obviously not a production-ready solution for many reasons,
 * but it is useful to help illustrate how to build a fully-functional server.
 */
public final class PatientResourceProvider implements IResourceProvider {

   private static final Logger LOGGER = LoggerFactory.getLogger(PatientResourceProvider.class);

   private IPatientFhirResourceBusinessLogic patientFhirResourceBusinessLogic;

   public PatientResourceProvider(IPatientFhirResourceBusinessLogic patientFhirResourceBusinessLogic) {

      if (null == patientFhirResourceBusinessLogic) {
         throw new IllegalArgumentException("IPatientFhirResourceBusinessLogic is null");
      }
      this.patientFhirResourceBusinessLogic = patientFhirResourceBusinessLogic;

   }

   /**
    * The getResourceType method comes from IResourceProvider, and must be overridden to indicate what type of resource this provider supplies.
    */
   @Override
   public Class<Patient> getResourceType() {
      return Patient.class;
   }


   /**
    * The "@Create" annotation indicates that this method implements "create=type", which adds a
    * new instance of a resource to the server.
    */
   @Create()
   public MethodOutcome createPatient(@ResourceParam Patient thePatient) {

      IdType createdIdType = this.patientFhirResourceBusinessLogic.createPatient(thePatient);
      // Let the caller know the ID of the newly created resource
      return new MethodOutcome(createdIdType);
   }

   /**
    * The "@Search" annotation indicates that this method supports the search operation. You may have many different method annotated with this annotation, to support many different search criteria.
    * This example searches by family name.
    *
    * @param theFamilyName This operation takes one parameter which is the search criteria. It is annotated with the "@Required" annotation. This annotation takes one argument, a string containing the name of
    *                      the search criteria. The datatype here is StringDt, but there are other possible parameter types depending on the specific search criteria.
    * @return This method returns a list of Patients. This list may contain multiple matching resources, or it may also be empty.
    */
   @Search()
   public List<Patient> findBySearchParameters(
      final @OptionalParam(name = Patient.SP_FAMILY) StringDt theFamilyName,
      final @OptionalParam(name = Patient.SP_GIVEN) StringDt theGivenName,
      final @OptionalParam(name = Patient.SP_BIRTHDATE) DateParam theBirthDate,
      final @OptionalParam(name = Constants.PARAM_COUNT) String theCount) {
      List<Patient> retVal = this.patientFhirResourceBusinessLogic.findBySearchParameters(theFamilyName, theGivenName, theBirthDate);
      return retVal;
   }


   /**
    * This is the "read" operation. The "@Read" annotation indicates that this method supports the read and/or vread operation.
    * <p>
    * Read operations take a single parameter annotated with the {@link IdParam} paramater, and should return a single resource instance.
    * </p>
    *
    * @param theId The read operation takes one parameter, which must be of type IdDt and must be annotated with the "@Read.IdParam" annotation.
    * @return Returns a resource matching this identifier, or null if none exists.
    */
   @Read(version = true)
   public Patient readPatient(@IdParam IdType theId) {
      Optional<Patient> foundPatient = this.patientFhirResourceBusinessLogic.readPatient(theId);

      if (foundPatient.isEmpty()) {
         String errorMsg = null == theId ? "theId is null" : theId.toString();
         LOGGER.error(errorMsg);
         throw new ResourceNotFoundException(errorMsg);
      }

      return foundPatient.get();

   }

   @History()
   public List<Patient> getPatientHistory(
      @IdParam IdType theId
   ) {
      List<Patient> retVal = this.patientFhirResourceBusinessLogic.findAllHistoryForSingle(theId);
      return retVal;
   }

   /**
    * The "@Update" annotation indicates that this method supports replacing an existing
    * resource (by ID) with a new instance of that resource.
    *
    * @param theId      This is the ID of the Patient to update
    * @param thePatient This is the actual resource to save
    * @return This method returns a "MethodOutcome"
    */
   @Update()
   public MethodOutcome updatePatient(@IdParam IdType theId, @ResourceParam Patient thePatient) {
      this.patientFhirResourceBusinessLogic.updatePatient(theId, thePatient);
      return new MethodOutcome();
   }

}