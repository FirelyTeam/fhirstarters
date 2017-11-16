package ca.uhn.example.model;

import ca.uhn.fhir.model.api.BaseIdentifiableElement;
import ca.uhn.fhir.model.api.IElement;
import ca.uhn.fhir.model.api.IExtension;
import ca.uhn.fhir.model.api.annotation.*;
import ca.uhn.fhir.util.ElementUtil;
import org.hl7.fhir.dstu3.model.BooleanType;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.ContactPoint;
import org.hl7.fhir.dstu3.model.Organization;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an example of a customized model class. Essentially we have taken the
 * built-in Organization resource class, and extended with a custom extension.
 */
@ResourceDef(name = "Organization")
public class MyOrganization extends Organization {

	/* *****************************
    * Fields
	 * *****************************/

   /**
    * This is a basic extension, with a DataType value (in this case, String)
    */
   @Description(shortDefinition = "Contains a simple code indicating the billing code for this organization")
   @Extension(url = "http://foo#billingCode", isModifier = false, definedLocally = true)
   @Child(name = "billingCode")
   private CodeType myBillingCode;

   /**
    * This is a composite extension, containing further extensions instead of
    * a value. The class "EmergencyContact" is defined at the bottom
    * of this file.
    */
   @Description(shortDefinition = "Contains emergency contact details")
   @Extension(url = "http://foo#emergencyContact", isModifier = false, definedLocally = true)
   @Child(name = "emergencyContact", min = 0, max = Child.MAX_UNLIMITED)
   private List<EmergencyContact> myEmergencyContact;
	
	/* *****************************
	 * Getters and setters
	 * *****************************/

   public CodeType getBillingCode() {
      if (myBillingCode == null) {
         myBillingCode = new CodeType();
      }
      return myBillingCode;
   }

   public void setBillingCode(CodeType theBillingCode) {
      myBillingCode = theBillingCode;
   }

   public List<EmergencyContact> getEmergencyContact() {
      if (myEmergencyContact == null) {
         myEmergencyContact = new ArrayList<EmergencyContact>();
      }
      return myEmergencyContact;
   }

   public void setEmergencyContact(List<EmergencyContact> theEmergencyContact) {
      myEmergencyContact = theEmergencyContact;
   }

	/* *****************************
	 * Boilerplate methods- Hopefully these will be removed or made optional
	 * in a future version of HAPI but for now they need to be added to all block
	 * types. These two methods follow a simple pattern where a utility method from
	 * ElementUtil is called and all fields are passed in.
	 * *****************************/

   @Override
   public boolean isEmpty() {
      return super.isEmpty() && ElementUtil.isEmpty(myBillingCode, myEmergencyContact);
   }

   /**
    * This "block definition" defines an extension type with multiple child extensions.
    * It is referenced by the field myEmergencyContact above.
    */
   @Block
   public static class EmergencyContact extends BaseIdentifiableElement implements IExtension {
		/* *****************************
		 * Fields
		 * *****************************/

      /**
       * This is a primitive datatype extension
       */
      @Description(shortDefinition = "Should be set to true if the contact is active")
      @Extension(url = "http://foo#emergencyContactActive", isModifier = false, definedLocally = true)
      @Child(name = "active")
      private BooleanType myActive;

      /**
       * This is a composite datatype extension
       */
      @Description(shortDefinition = "Contains the actual contact details")
      @Extension(url = "http://foo#emergencyContactContact", isModifier = false, definedLocally = true)
      @Child(name = "contact")
      private ContactPoint myContact;

		/* *****************************
		 * Getters and setters
		 * *****************************/

      public BooleanType getActive() {
         if (myActive == null) {
            myActive = new BooleanType();
         }
         return myActive;
      }

      public void setActive(BooleanType theActive) {
         myActive = theActive;
      }

      @Override
      public <T extends IElement> List<T> getAllPopulatedChildElementsOfType(Class<T> theType) {
         return ElementUtil.allPopulatedChildElements(theType, myActive, myContact);
      }

      public ContactPoint getContact() {
         if (myContact == null) {
            myContact = new ContactPoint();
         }
         return myContact;
      }

		/* *****************************
		 * Boilerplate methods- Hopefully these will be removed or made optional
		 * in a future version of HAPI but for now they need to be added to all block
		 * types. These two methods follow a simple pattern where a utility method from
		 * ElementUtil is called and all fields are passed in.
		 * *****************************/

      public void setContact(ContactPoint theContact) {
         myContact = theContact;
      }

      @Override
      public boolean isEmpty() {
         return ElementUtil.isEmpty(myActive, myContact);
      }


   }

}
