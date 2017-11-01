package ca.uhn.fhir.example;

import ca.uhn.fhir.model.api.TemporalPrecisionEnum;
import org.hl7.fhir.dstu3.model.*;

import java.util.Date;

public class Tmp {

   public static void main(String[] theArgs) {

      DateTimeType effective = new DateTimeType();
      effective.setValue(new Date());
      effective.setValue(new Date(), TemporalPrecisionEnum.MINUTE);
      effective.setValueAsString("2017-09-11T14:35:00-07:00");

      BooleanType active = new BooleanType();
      active.setValue(true);
      active.setValueAsString("true");

      DecimalType value = new DecimalType();
      value.setValue(1.2d);
      value.setValueAsString("1.20000");


      Observation obs = new Observation();

      // These are equivalent
      obs.setComment("This is a comment");
      obs.setCommentElement(new StringType("This is a comment"));

      // Get the primitive or get the FHIR type
      String comment = obs.getComment();
      StringType commentElement = obs.getCommentElement();


   }

}
