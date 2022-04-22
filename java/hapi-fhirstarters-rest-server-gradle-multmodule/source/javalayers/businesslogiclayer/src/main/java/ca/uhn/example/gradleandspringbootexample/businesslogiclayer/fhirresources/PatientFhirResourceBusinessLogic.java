package ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources;

import ca.uhn.example.gradleandspringbootexample.businesslogiclayer.fhirresources.interfaces.IPatientFhirResourceBusinessLogic;
import ca.uhn.fhir.model.primitive.InstantDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.ParamPrefixEnum;
import ca.uhn.fhir.rest.server.exceptions.InvalidRequestException;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import ca.uhn.fhir.rest.server.exceptions.UnprocessableEntityException;
import org.apache.commons.lang3.time.DateUtils;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class PatientFhirResourceBusinessLogic implements IPatientFhirResourceBusinessLogic {

   private static final int GIVEN_MODULUS = 4;

   private static final int GIVEN_MODULUS_MATCH_ZERO = 0;

   private static final int GIVEN_MODULUS_MATCH_ONE = 1;

   private static final int GIVEN_MODULUS_MATCH_TWO = 2;

   private static final int GIVEN_MODULUS_MATCH_THREE = 3;

   private static final int GENDER_MODULUS = 2;

   private static final int PATIENT_SEED_COUNT = 10;

   private static final int FAMILY_MODULUS = 3;

   private static final Logger LOGGER = LoggerFactory.getLogger(EncounterFhirResourceBusinessLogic.class);

   /**
    * This map has a resource ID as a key, and each key maps to a Deque list containing all versions of the resource with that ID.
    */
   private Map<Long, Deque<Patient>> myIdToPatientVersions = new HashMap<Long, Deque<Patient>>();

   /**
    * This is used to generate new IDs.
    */
   private long myNextId = 1;

   @Inject
   public PatientFhirResourceBusinessLogic() {
      this.setupFakeResourceProvider();
   }

   private void setupFakeResourceProvider() {

      /* add i number of seed data patients */
      for (int i = 0; i < PATIENT_SEED_COUNT; i++) {

         long resourceId = myNextId++;

         Patient patient = new Patient();
         patient.setId(Long.toString(resourceId));
         Identifier id1 = patient.addIdentifier();
         id1.setSystem("http://mycompany.com/us-memberid");

         Identifier id2 = patient.addIdentifier();
         id2.setSystem("http://mycompany.com/us-subscriberid");
         id2.setValue("mymycompanySubscriberId_" + Long.toString(resourceId));

         //patient.addName().addGiven("Test");
         //patient.getName().get(0).addGiven("PatientOne");

         HumanName hn1 = new HumanName();

         if ((resourceId % FAMILY_MODULUS) == 0) {
            hn1.setFamily("Patel");
         } else if ((resourceId % FAMILY_MODULUS) == 1) {
            hn1.setFamily("Jones");
         } else {
            hn1.setFamily("Smith");
         }

         hn1.addGiven("MyFirstName" + Long.toString(resourceId));
         hn1.addGiven("MyMiddleOneName" + Long.toString(resourceId));
         hn1.addGiven("MyMiddleTwoName" + Long.toString(resourceId));

         patient.addName(hn1);

         //patient.addName().addGiven("MyFirstName" + Long.toString(resourceId)).setFamily("Smith" + Long.toString(resourceId));
         //patient.addName().addGiven("MyMiddleOneName" + Long.toString(resourceId)).setFamily("Smith" + Long.toString(resourceId));
         //patient.addName().addGiven("MyMiddleTwoName" + Long.toString(resourceId)).setFamily("Smith" + Long.toString(resourceId));

         if ((resourceId % GENDER_MODULUS) == 0) {
            patient.setGender(AdministrativeGender.FEMALE);

            if ((resourceId % GIVEN_MODULUS) == GIVEN_MODULUS_MATCH_ZERO) {
               hn1.addGiven("Fatima");
            } else if ((resourceId % GIVEN_MODULUS) == GIVEN_MODULUS_MATCH_TWO) {
               hn1.addGiven("Mary");
            } else {
               hn1.setFamily("Pat");
            }

         } else {
            patient.setGender(AdministrativeGender.MALE);

            if ((resourceId % GIVEN_MODULUS) == GIVEN_MODULUS_MATCH_ONE) {
               hn1.addGiven("John");
            } else if ((resourceId % GIVEN_MODULUS) == GIVEN_MODULUS_MATCH_THREE) {
               hn1.addGiven("Sri");
            } else {
               hn1.setFamily("Pat");
            }

         }

         patient.setBirthDate(java.util.Date
            .from(LocalDateTime.from(LocalDateTime.now().minusMonths(resourceId)).atZone(ZoneId.systemDefault())
               .toInstant()));

         LinkedList<Patient> list = new LinkedList<Patient>();
         list.add(patient);

         myIdToPatientVersions.put(resourceId, list);

      }
   }

   /**
    * Stores a new version of the patient in memory so that it can be retrieved later.
    *
    * @param thePatient The patient resource to store
    * @param theId      The ID of the patient to retrieve
    */
   private void addNewVersion(final Patient thePatient, final Long theId) {
      InstantDt publishedDate;
      if (!myIdToPatientVersions.containsKey(theId)) {
         myIdToPatientVersions.put(theId, new LinkedList<Patient>());
         publishedDate = InstantDt.withCurrentTime();
      } else {
         Patient currentPatitne = myIdToPatientVersions.get(theId).getLast();
         Meta resourceMetadata = currentPatitne.getMeta();
         publishedDate = InstantDt.withCurrentTime();  //(InstantDt) resourceMetadata.get(ResourceMetadataKeyEnum.PUBLISHED);
      }

      /*
       * PUBLISHED time will always be set to the time that the first version was stored. UPDATED time is set to the time that the new version was stored.
       */
      //thePatient.getMeta().put(ResourceMetadataKeyEnum.PUBLISHED, publishedDate);
      thePatient.getMeta().setLastUpdated(InstantDt.withCurrentTime().getValue());

      Deque<Patient> existingVersions = myIdToPatientVersions.get(theId);

      // We just use the current number of versions as the next version number
      String newVersion = Integer.toString(existingVersions.size());

      // Create an ID with the new version and assign it back to the resource
      IdType newId = new IdType("Patient", Long.toString(theId), newVersion);
      thePatient.setId(newId);

      existingVersions.add(thePatient);
   }

   public IdType createPatient(@ResourceParam Patient thePatient) {
      validateResource(thePatient);

      // Here we are just generating IDs sequentially
      long id = myNextId++;

      addNewVersion(thePatient, id);

      // Let the caller know the ID of the newly created resource
      return new IdType(id);
   }

   public List<Patient> findBySearchParameters(
      StringDt theFamilyName,
      StringDt theGivenName,
      DateParam theBirthDate) {

      List<Patient> retVal = new LinkedList<Patient>();

      List<Patient> lastEntriesInDequeuePatients = myIdToPatientVersions
         .values()
         .stream()
         .map(dq -> dq.getLast())
         .collect(Collectors.toList());

//      List<Deque<Patient>> patients = myIdToPatientVersions
//         .values()
//         .stream()
//         .filter(pat ->
//               theFamilyName == null ? 1 == 1 : pat.getLast().getName().stream().anyMatch(hn -> null != hn.getFamily() && hn.getFamily().equalsIgnoreCase(theFamilyName.getValue()))
//            //   && theBirthDate == null ? 1==1 : null != pat.getLast().getBirthDate() && pat.getLast().getBirthDate() == theBirthDate.getValue()
//         )
//         .collect(Collectors.toList());

      List<Patient> matchPatients = lastEntriesInDequeuePatients.stream()
         .filter(pat ->
            theFamilyName != null ? null != pat.getName() && pat.getName().stream().anyMatch(hn -> null != hn.getFamily() && hn.getFamily().equalsIgnoreCase(theFamilyName.getValue())) : true
         )

         .filter(pat ->
            theGivenName != null ? null != pat.getName() && pat.getName().stream().anyMatch(hn -> null != hn.getGiven() && hn.getGiven().stream().anyMatch(gv -> gv.getValue().equalsIgnoreCase(theGivenName.getValue()))) : true
         )

         /* start birth date permutations */
         /* precision is not even considered here.  "modifier" searches are not trivial. here we are saying "equals" is same DAY for this proof of concept.  */
         /* only 6 of the 9 modifiers are coded here.  see : https://build.fhir.org/search.html#prefix */
         .filter(pat -> theBirthDate != null && null != theBirthDate.getValue() && (theBirthDate.getPrefix() == ParamPrefixEnum.EQUAL || null == theBirthDate.getPrefix()) ? null != pat.getBirthDate() && DateUtils.isSameDay(pat.getBirthDate(), theBirthDate.getValue()) : true)
         .filter(pat -> theBirthDate != null && null != theBirthDate.getValue() && theBirthDate.getPrefix() == ParamPrefixEnum.NOT_EQUAL ? null != pat.getBirthDate() && !DateUtils.isSameDay(pat.getBirthDate(), theBirthDate.getValue()) : true)

         .filter(pat -> theBirthDate != null && null != theBirthDate.getValue() && theBirthDate.getPrefix() == ParamPrefixEnum.GREATERTHAN ? null != pat.getBirthDate() && (pat.getBirthDate().after(theBirthDate.getValue()) && !DateUtils.isSameDay(pat.getBirthDate(), theBirthDate.getValue())) : true)
         .filter(pat -> theBirthDate != null && null != theBirthDate.getValue() && theBirthDate.getPrefix() == ParamPrefixEnum.GREATERTHAN_OR_EQUALS ? null != pat.getBirthDate() && (pat.getBirthDate().after(theBirthDate.getValue()) || DateUtils.isSameDay(pat.getBirthDate(), theBirthDate.getValue())) : true)

         .filter(pat -> theBirthDate != null && null != theBirthDate.getValue() && theBirthDate.getPrefix() == ParamPrefixEnum.LESSTHAN ? null != pat.getBirthDate() && (pat.getBirthDate().before(theBirthDate.getValue()) && !DateUtils.isSameDay(pat.getBirthDate(), theBirthDate.getValue())) : true)
         .filter(pat -> theBirthDate != null && null != theBirthDate.getValue() && theBirthDate.getPrefix() == ParamPrefixEnum.LESSTHAN_OR_EQUALS ? null != pat.getBirthDate() && (pat.getBirthDate().before(theBirthDate.getValue()) || DateUtils.isSameDay(pat.getBirthDate(), theBirthDate.getValue())) : true)

         .collect(Collectors.toList());

      return matchPatients;
   }

   public Optional<Patient> readPatient(@IdParam IdType theId) {

      Optional<Patient> returnItem = Optional.empty();

      Deque<Patient> retVal;
      try {
         retVal = myIdToPatientVersions.get(theId.getIdPartAsLong());

         if (!theId.hasVersionIdPart()) {
            returnItem = Optional.of(retVal.getLast());
         } else {
            for (Patient nextVersion : retVal) {
               String nextVersionId = nextVersion.getId();
               if (theId.getVersionIdPart().equals(nextVersionId)) {
                  returnItem = Optional.of(nextVersion);
               }
            }
            // No matching version
            throw new ResourceNotFoundException("Unknown version: " + theId.getValue());
         }

      } catch (Exception e) {
         /*
          * If we can't parse the ID as a long, it's not valid so this is an unknown resource
          */

         LOGGER.error("Prove injected logger works. " + e.getMessage(), e);
         throw new ResourceNotFoundException(theId.toString());
      }

      return returnItem;

   }

   public void updatePatient(@IdParam IdType theId, @ResourceParam Patient thePatient) {
      validateResource(thePatient);

      Long id;
      try {
         id = theId.getIdPartAsLong();
      } catch (DataFormatException e) {
         throw new InvalidRequestException("Invalid ID " + theId.getValue() + " - Must be numeric");
      }

      /*
       * Throw an exception (HTTP 404) if the ID is not known
       */
      if (!myIdToPatientVersions.containsKey(id)) {
         throw new ResourceNotFoundException(theId);
      }

      addNewVersion(thePatient, id);
   }

   /**
    * This method just provides simple business validation for resources we are storing.
    *
    * @param thePatient The patient to validate
    */
   private void validateResource(final Patient thePatient) {
      /*
       * Our server will have a rule that patients must have a family name or we will reject them
       */
      if (thePatient.getNameFirstRep().isEmpty()) {
         OperationOutcome outcome = new OperationOutcome();
         CodeableConcept cc = new CodeableConcept();
         cc.setText("No family name provided, Patient resources must have at least one family name.");
         outcome.addIssue().setSeverity(OperationOutcome.IssueSeverity.FATAL).setDetails(cc);
         throw new UnprocessableEntityException("Something bad", outcome);
      }
   }
}
