# HAPI FHIR Plain Server Skeleton

To try this project out:

* Run the following command to compile the project and start a local testing server that runs it:

```
mvn jetty:run
```

* Test that your server is running by fetching its CapabilityStatement:

   http://localhost:8080/metadata

* Try reading back a resource from your server using the following URL:

   http://localhost:8080/Patient/1

* Try reading back a resource that does not exist by using the following URL:

   http://localhost:8080/Patient/999

The responses to the Patient read operations above come from the resource provider called [Example01_PatientResourceProvider.java](https://github.com/FirelyTeam/fhirstarters/blob/master/java/hapi-fhirstarters-simple-server/src/main/java/ca/uhn/fhir/example/Example01_PatientResourceProvider.java)

# Let's Build - Introduction

This project is intended to help you get started using the HAPI FHIR Plain Server project. 

Have a look at the [REST Operations: Overview](https://hapifhir.io/hapi-fhir/docs/server_plain/rest_operations.html) documentation for some examples of how different kinds of FHIR operations in the server. You may also want to review the subsequent pages in the docs for even more examples.

* [ ] Try adding support for create operation

   * [ ] Use a REST tool (Postman, Insomnia) to invoke your create operation and read the same resource back
   
* [ ] Add support for the delete operation

* [ ] Add a simple Search operation where you can search by name. You could implement a very basic version of this where you simply loop through the Patient resources in memory and return those matching the given search term.

**Hint:** You can also look at [HashMapResourceProvider](https://github.com/jamesagnew/hapi-fhir/blob/master/hapi-fhir-server/src/main/java/ca/uhn/fhir/rest/server/provider/HashMapResourceProvider.java) for an example resource provider with many operations implemented (using a simple in-memory HashMap as the storage mechanism, so it's easy to follow).

