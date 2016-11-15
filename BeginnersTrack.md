
1. **Setting up the environment**
	- Precondition: Visual Studio or IntelliJ IDEA or Eclipse have been installed
	- Action (.Net): create a new .Net solution and add the Hl7.Fhir.DSTU2 package with NuGet 
	- Action (Java): create a new Java application and add HAPI through Maven 

2. **Start using the API**
	- Precondition: the NuGet or Maven package has been added to the project
	- Action (.Net): add lines to your project code to use Hl7.Fhir.Model and Hl7.Fhir.Rest and create a new FHIR client that points to one of the publically available test servers 
	- Action (Java): add lines to your project code to import ca.uhn.fhir.context.FhirContext and ca.uhn.fhir.rest.client.IGenericClient and create a new FHIR client that points to one of the publically available test servers  
	- Bonus point: error handling has been set up 

3. **Retrieve a single Resource** 
	- Action: read a single Resource from the server with id “example”, e.g. an Observation, and display the text part 
	- Bonus: The UI displays an error message when the Resource does not exist 

4. **Create a Resource**
	- Action: Create a Patient model object instance and populate it with a name and identifier
	- Action: Use the .NET/Java client to upload this resource to a test server
	- Action: Print the ID of the newly created resource 

5. **Update a Resource**
	- Action: Edit the Patient resource from the previous task by adding more information
	- Action: Use the .Net/Java client to update this resource on the test server
	- Bonus: Have the UI show the version of the resource

6. **Search for a specific Resource type** 
	- Action: search the server for all Resources of a specific type, e.g. Observation, and display the id for each Resource 
	- Bonus: limit the number of results to 5
	- Bonus 2: The UI allows the user to display the next page of search results

7. **Search for specific Resource instances**
	- Action: search the server for specific Resource instances, e.g. all Patients with the name Steve
	- Tip: look at the Resource page in the specification; at the bottom of each page you'll find a list of all search parameters for that Resource type
	- Display the id for each matching resource, plus the value that matched your search parameter, e.g. the name

8. **Make a transaction**
	- Action: build a transaction Bundle
	- Action: Add at least two transactions to the Bundle, for example one create interaction and one read interaction.
	- Action: Send the transaction to a test server
	- Bonus: Display the results of the transaction

9. **Bonus: Extra assignments**
	- If you've implemented all of the previous assignments, try other interactions from the [FHIR REST](http://www.hl7.org/fhir/http.html) specification
	- Look at the [search page](http://www.hl7.org/fhir/search.html) in the specification and try to perform more specific searches, such as all retrieving all Encounters of Patients with the name John, or including another Resource type