# HAPI FHIR - Client Starter

This project is a simple "skeleton project" (a Maven project containing all dependencies needed to run the HAPI FHIR client).

Instructions:

* Import this project into your IDE
* Try executing the class `TestApplication.java`

You will see that this project loads the resource `Patient/example` from the R4 testing endpoint hosted at http://hapi.fhir.org/baseR4

# Let's Build - Introduction

Check out the [Client Documentation](https://hapifhir.io/hapi-fhir/docs/client/generic_client.html) to see the syntax for various FHIR client operations.

Here are some things to try:

* [ ] Perform a FHIR Search for Patient resources where the patient has a name of "Smith". 

   * [ ] Print the resource ID for each matching resource
   
* [ ] Use a FHIR **Create** operation to create a new Patient resource on the server. Give it your name, or a fictional name you make up.

   * [ ] Log the ID that the server assigned to this resource
   
   * [ ] Use a FHIR **Read** to read it back
   
   * [ ] Use a FHIR **Update** to update it
   
   * [ ] Use a FHIR **Delete** to delete it
   
   * [ ] Try modifying your *Read* code so that it displays a useful error if the resource has been deleted

Note that you you can find a few hints and examples in `TestApplicationHints.java`
