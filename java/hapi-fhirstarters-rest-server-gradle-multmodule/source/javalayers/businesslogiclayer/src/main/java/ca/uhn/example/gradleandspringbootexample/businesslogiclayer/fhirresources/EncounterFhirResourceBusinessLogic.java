package ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources;

import ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.interfaces.IEncounterFhirResourceBusinessLogic;
import ca.uhn.fhir.model.primitive.InstantDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.server.exceptions.InvalidRequestException;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import ca.uhn.fhir.rest.server.exceptions.UnprocessableEntityException;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.Period;
import org.hl7.fhir.r4.model.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public final class EncounterFhirResourceBusinessLogic implements IEncounterFhirResourceBusinessLogic {

   private static final int ENCOUNTER_SEED_COUNT = 10;

   private static final Logger LOGGER = LoggerFactory.getLogger(EncounterFhirResourceBusinessLogic.class);

   /**
    * This map has a resource ID as a key, and each key maps to a Deque list containing all versions of the resource with that ID.
    */
   private Map<Long, Deque<Encounter>> myIdToEncounterVersions = new HashMap<Long, Deque<Encounter>>();

   /**
    * This is used to generate new IDs.
    */
   private long myNextId = 1;

   /**
    * Constructor, which pre-populates the provider with one resource instance.
    */
   public EncounterFhirResourceBusinessLogic() {
      this.setupFakeResourceProvider();
   }

   private void setupFakeResourceProvider() {

      /* add i number of seed data Encounters */
      for (int i = 0; i < ENCOUNTER_SEED_COUNT; i++) {

         long resourceId = myNextId++;

         Encounter enc = new Encounter();
         enc.setId(Long.toString(resourceId));
         Identifier id1 = enc.addIdentifier();
         id1.setSystem("http://mycompany.com/us-memberid");

         Identifier id2 = enc.addIdentifier();
         id2.setSystem("http://mycompany.com/us-subscriberid");
         id2.setValue("myCounterId_" + Long.toString(resourceId));

         //Encounter.addName().addGiven("Test");
         //Encounter.getName().get(0).addGiven("EncounterOne");

         org.hl7.fhir.r4.model.Encounter.DiagnosisComponent hn1 = new Encounter.DiagnosisComponent();
         hn1.setId("DiagnosisComponent" + Long.toString(resourceId));
         //hn1.set(java.util.Date
         //      .from(LocalDateTime.from(LocalDateTime.now().minusMonths(resourceId)).atZone(ZoneId.systemDefault())
         //            .toInstant()));

         enc.addDiagnosis(hn1);

         Encounter.EncounterLocationComponent elc = new Encounter.EncounterLocationComponent();

         Period elcper = new Period();
         elcper.setEnd(java.util.Date
            .from(LocalDateTime.from(LocalDateTime.now().minusMonths(resourceId)).atZone(ZoneId.systemDefault())
               .toInstant()));
         elc.setPeriod(elcper);
         enc.addLocation(elc);

         if ((resourceId % 2) == 0) {
            enc.setStatus(Encounter.EncounterStatus.ARRIVED);
         } else {
            enc.setStatus(Encounter.EncounterStatus.TRIAGED);
         }

         LinkedList<Encounter> list = new LinkedList<Encounter>();
         list.add(enc);

         myIdToEncounterVersions.put(resourceId, list);

      }
   }

   /**
    * Stores a new version of the Encounter in memory so that it can be retrieved later.
    *
    * @param theEncounter The Encounter resource to store
    * @param theId        The ID of the Encounter to retrieve
    */
   private void addNewVersion(Encounter theEncounter, Long theId) {
      InstantDt publishedDate;
      if (!myIdToEncounterVersions.containsKey(theId)) {
         myIdToEncounterVersions.put(theId, new LinkedList<Encounter>());
         publishedDate = InstantDt.withCurrentTime();
      } else {
         Encounter currentPatitne = myIdToEncounterVersions.get(theId).getLast();
         Meta resourceMetadata = currentPatitne.getMeta();
         publishedDate = InstantDt.withCurrentTime(); ////(InstantDt) resourceMetadata.get(ResourceMetadataKeyEnum.PUBLISHED);
      }
      /*
       * PUBLISHED time will always be set to the time that the first version was stored. UPDATED time is set to the time that the new version was stored.
       */
      //theEncounter.getMeta().put(ResourceMetadataKeyEnum.PUBLISHED, publishedDate);
      //theEncounter.getMeta().put(ResourceMetadataKeyEnum.UPDATED, InstantDt.withCurrentTime());

      Deque<Encounter> existingVersions = myIdToEncounterVersions.get(theId);

      // We just use the current number of versions as the next version number
      String newVersion = Integer.toString(existingVersions.size());

      // Create an ID with the new version and assign it back to the resource
      IdType newId = new IdType("Encounter", Long.toString(theId), newVersion);
      theEncounter.setId(newId);

      /* REFERENCE to a subject-patient.  You should NOT under-estimate the issue of "references" in FHIR */
      /* https://www.hl7.org/fhir/encounter-definitions.html#Encounter.subject */
      /* below, we randomly get a patient, the real logic would be better of course */
      int subjectPatientFhirLogicalId = new Random().nextInt((PatientFhirResourceBusinessLogic.DEMO_PATIENT_FHIR_LOGICAL_ID_START + PatientFhirResourceBusinessLogic.PATIENT_SEED_COUNT) - PatientFhirResourceBusinessLogic.DEMO_PATIENT_FHIR_LOGICAL_ID_START) + PatientFhirResourceBusinessLogic.DEMO_PATIENT_FHIR_LOGICAL_ID_START;
      theEncounter.setSubject(new Reference(String.format("/Patient/%1$s", subjectPatientFhirLogicalId)));

      existingVersions.add(theEncounter);
   }

   @Override
   public IdType createEncounter(Encounter theEncounter) {
      validateResource(theEncounter);

      // Here we are just generating IDs sequentially
      long id = myNextId++;

      addNewVersion(theEncounter, id);

      // Let the caller know the ID of the newly created resource
      return new IdType(id);
   }

   public List<Encounter> findEncountersByName(StringDt theLocationName) {

      List<Encounter> lastEntriesInDequeueEncounters = myIdToEncounterVersions
         .values()
         .stream()
         .map(dq -> dq.getLast())
         .collect(Collectors.toList());

      return lastEntriesInDequeueEncounters;
   }

   public List<Encounter> findEncountersUsingArbitraryCtriteria() {
      LinkedList<Encounter> retVal = new LinkedList<Encounter>();

      for (Deque<Encounter> nextEncounterList : myIdToEncounterVersions.values()) {
         Encounter nextEncounter = nextEncounterList.getLast();
         retVal.add(nextEncounter);
      }

      return retVal;
   }

   public Optional<Encounter> readEncounter(@IdParam IdType theId) {

      Optional<Encounter> returnItem = Optional.empty();

      Deque<Encounter> retVal;
      try {
         retVal = myIdToEncounterVersions.get(theId.getIdPartAsLong());

         if (!theId.hasVersionIdPart()) {
            returnItem = Optional.of(retVal.getLast());
         } else {
            for (Encounter nextVersion : retVal) {
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

   public void updateEncounter(@IdParam IdType theId, Encounter theEncounter) {
      validateResource(theEncounter);

      Long id;
      id = theId.getIdPartAsLong();

      if (null == id) {
         throw new InvalidRequestException("Invalid ID " + theId.getValue() + " - Must be numeric");
      }

      /*
       * Throw an exception (HTTP 404) if the ID is not known
       */
      if (!myIdToEncounterVersions.containsKey(id)) {
         throw new ResourceNotFoundException(theId);
      }
      addNewVersion(theEncounter, id);

   }

   /**
    * This method just provides simple business validation for resources we are storing.
    *
    * @param theEncounter The Encounter to validate
    */
   private void validateResource(Encounter theEncounter) {
      /*
       * Our server will have a rule that Encounters must have a Location
       */
      if (theEncounter.getLocation().isEmpty()) {
         OperationOutcome outcome = new OperationOutcome();
         CodeableConcept cc = new CodeableConcept();
         cc.setText("No getLocation provided, Encounter resources must have at least one getLocation.");
         outcome.addIssue().setSeverity(OperationOutcome.IssueSeverity.FATAL).setDetails(cc);
         throw new UnprocessableEntityException("Something bad", outcome);
      }
   }

}
