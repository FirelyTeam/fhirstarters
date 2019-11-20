# HAPI FHIR Plain Server Skeleton

To try this project out:

* Run the following command to compile the project and start a local testing server that runs it:

```
man jetty:run
```

* Point your browser to the following URL:

http://localhost:8080/Patient/1

* The response to the query above comes from the resource provider called [Example01_PatientResourceProvider.java](https://github.com/FirelyTeam/fhirstarters/blob/master/java/hapi-fhirstarters-simple-server/src/main/java/ca/uhn/fhir/example/Example01_PatientResourceProvider.java)

* Try adding other operations: create, update, search! You can use the [Operations Documentation](http://hapifhir.io/doc_rest_operations.html) for details on how other methods should look. You can also look at [HashMapResourceProvider](https://github.com/jamesagnew/hapi-fhir/blob/master/hapi-fhir-server/src/main/java/ca/uhn/fhir/rest/server/provider/HashMapResourceProvider.java) for an example resource provider with many operations implemented (using a simple in-memory HashMap as the storage mechanism, so it's easy to follow).
