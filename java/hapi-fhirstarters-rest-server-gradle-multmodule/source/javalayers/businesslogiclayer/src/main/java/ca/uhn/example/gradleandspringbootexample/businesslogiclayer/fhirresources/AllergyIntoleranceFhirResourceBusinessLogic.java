package ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources;

import ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.interfaces.IAllergyIntoleranceFhirResourceBusinessLogic;
import ca.uhn.fhir.model.primitive.InstantDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.server.exceptions.InvalidRequestException;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import ca.uhn.fhir.rest.server.exceptions.UnprocessableEntityException;
import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public final class AllergyIntoleranceFhirResourceBusinessLogic implements IAllergyIntoleranceFhirResourceBusinessLogic {

   private static final int ENCOUNTER_SEED_COUNT = 10;

   private static final Logger LOGGER = LoggerFactory.getLogger(AllergyIntoleranceFhirResourceBusinessLogic.class);

   /**
    * This map has a resource ID as a key, and each key maps to a Deque list containing all versions of the resource with that ID.
    */
   private Map<Long, Deque<AllergyIntolerance>> myIdToAllergyIntoleranceVersions = new HashMap<Long, Deque<AllergyIntolerance>>();

   /**
    * This is used to generate new IDs.
    */
   private long myNextId = 1;

   /**
    * Constructor, which pre-populates the provider with one resource instance.
    */
   public AllergyIntoleranceFhirResourceBusinessLogic() {
      this.setupFakeResourceProvider();
   }

   private void setupFakeResourceProvider() {

      /* add i number of seed data AllergyIntolerances */
      for (int i = 0; i < ENCOUNTER_SEED_COUNT; i++) {

         long resourceId = myNextId++;

         AllergyIntolerance allergyIntol = new AllergyIntolerance();
         allergyIntol.setId(Long.toString(resourceId));
         Identifier id1 = allergyIntol.addIdentifier();
         id1.setSystem("http://allergies.are.us.com");
         id1.setValue("All.R.Us..." + Long.toString(resourceId));

         Identifier id2 = allergyIntol.addIdentifier();
         id2.setSystem("http://allergies.is.all.we.do.com");
         id2.setValue("Allergies.Is.All.We.Do..." + Long.toString(resourceId));

         if ((resourceId % 2) == 0) {
            allergyIntol.setType(AllergyIntolerance.AllergyIntoleranceType.ALLERGY);
         } else {
            allergyIntol.setType(AllergyIntolerance.AllergyIntoleranceType.INTOLERANCE);
         }

         if ((resourceId % 2) == 0) {
            allergyIntol.setCriticality(AllergyIntolerance.AllergyIntoleranceCriticality.HIGH);
         } else {
            allergyIntol.setCriticality(AllergyIntolerance.AllergyIntoleranceCriticality.LOW);
         }

         /* REFERENCE to a subject-patient.  You should NOT under-estimate the issue of "references" in FHIR */
         /* https://www.hl7.org/fhir/allergyintolerance-definitions.html#AllergyIntolerance.patient */
         /* below, we randomly get a patient, the real logic would be better of course */
         int subjectPatientFhirLogicalId = new Random().nextInt((PatientFhirResourceBusinessLogic.DEMO_PATIENT_FHIR_LOGICAL_ID_START + PatientFhirResourceBusinessLogic.PATIENT_SEED_COUNT) - PatientFhirResourceBusinessLogic.DEMO_PATIENT_FHIR_LOGICAL_ID_START) + PatientFhirResourceBusinessLogic.DEMO_PATIENT_FHIR_LOGICAL_ID_START;
         allergyIntol.setPatient(new Reference(String.format("/Patient/%1$s", subjectPatientFhirLogicalId)));

         LinkedList<AllergyIntolerance> list = new LinkedList<AllergyIntolerance>();
         list.add(allergyIntol);

         myIdToAllergyIntoleranceVersions.put(resourceId, list);

      }
   }

   /**
    * Stores a new version of the AllergyIntolerance in memory so that it can be retrieved later.
    *
    * @param theAllergyIntolerance The AllergyIntolerance resource to store
    * @param theId                 The ID of the AllergyIntolerance to retrieve
    */
   private void addNewVersion(AllergyIntolerance theAllergyIntolerance, Long theId) {
      InstantDt publishedDate;
      if (!myIdToAllergyIntoleranceVersions.containsKey(theId)) {
         myIdToAllergyIntoleranceVersions.put(theId, new LinkedList<AllergyIntolerance>());
         publishedDate = InstantDt.withCurrentTime();
      } else {
         AllergyIntolerance currentPatitne = myIdToAllergyIntoleranceVersions.get(theId).getLast();
         Meta resourceMetadata = currentPatitne.getMeta();
         publishedDate = InstantDt.withCurrentTime(); ////(InstantDt) resourceMetadata.get(ResourceMetadataKeyEnum.PUBLISHED);
      }
      /*
       * PUBLISHED time will always be set to the time that the first version was stored. UPDATED time is set to the time that the new version was stored.
       */
      //theAllergyIntolerance.getMeta().put(ResourceMetadataKeyEnum.PUBLISHED, publishedDate);
      //theAllergyIntolerance.getMeta().put(ResourceMetadataKeyEnum.UPDATED, InstantDt.withCurrentTime());

      Deque<AllergyIntolerance> existingVersions = myIdToAllergyIntoleranceVersions.get(theId);

      // We just use the current number of versions as the next version number
      String newVersion = Integer.toString(existingVersions.size());

      // Create an ID with the new version and assign it back to the resource
      IdType newId = new IdType("AllergyIntolerance", Long.toString(theId), newVersion);
      theAllergyIntolerance.setId(newId);

      existingVersions.add(theAllergyIntolerance);
   }

   @Override
   public IdType createAllergyIntolerance(AllergyIntolerance theAllergyIntolerance) {
      validateResource(theAllergyIntolerance);

      // Here we are just generating IDs sequentially
      long id = myNextId++;

      addNewVersion(theAllergyIntolerance, id);

      // Let the caller know the ID of the newly created resource
      return new IdType(id);
   }

   public List<AllergyIntolerance> findAllergyIntoleranceByFilters(StringDt theCriticality) {

      /* the input parameters/filters are not actually used (yet) */

      List<AllergyIntolerance> lastEntriesInDequeueAllergyIntolerances = myIdToAllergyIntoleranceVersions
         .values()
         .stream()
         .map(dq -> dq.getLast())
         .collect(Collectors.toList());

      return lastEntriesInDequeueAllergyIntolerances;
   }

   public Optional<AllergyIntolerance> readAllergyIntolerance(@IdParam IdType theId) {

      Optional<AllergyIntolerance> returnItem = Optional.empty();

      Deque<AllergyIntolerance> retVal;
      try {
         retVal = myIdToAllergyIntoleranceVersions.get(theId.getIdPartAsLong());

         if (!theId.hasVersionIdPart()) {
            returnItem = Optional.of(retVal.getLast());
         } else {
            for (AllergyIntolerance nextVersion : retVal) {
               String nextVersionId = nextVersion.getId();
               if (theId.getVersionIdPart().equals(nextVersionId)) {
                  returnItem = Optional.of(nextVersion);
               }
            }

         }

      } catch (Exception ex) {
         throw new RuntimeException(ex);
      }

      return returnItem;
   }

   public void updateAllergyIntolerance(@IdParam IdType theId, AllergyIntolerance theAllergyIntolerance) {
      validateResource(theAllergyIntolerance);

      Long id;
      id = theId.getIdPartAsLong();

      if (null == id) {
         throw new InvalidRequestException("Invalid ID " + theId.getValue() + " - Must be numeric");
      }

      /*
       * Throw an exception (HTTP 404) if the ID is not known
       */
      if (!myIdToAllergyIntoleranceVersions.containsKey(id)) {
         throw new ResourceNotFoundException(theId);
      }
      addNewVersion(theAllergyIntolerance, id);

   }

   /**
    * This method just provides simple business validation for resources we are storing.
    *
    * @param theAllergyIntolerance The AllergyIntolerance to validate
    */
   private void validateResource(AllergyIntolerance theAllergyIntolerance) {
      /*
       * Our server will have a rule that AllergyIntolerances must have a Location
       */
      if (null == theAllergyIntolerance.getCriticality()) {
         OperationOutcome outcome = new OperationOutcome();
         CodeableConcept cc = new CodeableConcept();
         cc.setText("No Criticality provided, AllergyIntolerance resources must have a Criticality.");
         outcome.addIssue().setSeverity(OperationOutcome.IssueSeverity.FATAL).setDetails(cc);
         throw new UnprocessableEntityException("Something bad", outcome);
      }
   }

}
