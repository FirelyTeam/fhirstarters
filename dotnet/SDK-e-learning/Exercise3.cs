using System.Xml.Linq;
using Hl7.Fhir.Model;
using Hl7.Fhir.Rest;

namespace SDK_e_learning
{
    internal class Exercise3
    {
        internal static async System.Threading.Tasks.Task UsingFhirClient(Patient pat, Observation obs)
        {
            var settings = new FhirClientSettings
            {
                PreferredFormat = ResourceFormat.Json,
                PreferredParameterHandling = SearchParameterHandling.Strict
            };

            var client = new FhirClient("http://server.fire.ly/R4", settings);

            try
            {
                // Send the Patient to the server to be created
                Patient createdPat = await client.CreateAsync(pat);

                Console.WriteLine($"The Patient was created, and assigned this id: {createdPat?.Id}");

                // Use the technical identity of the Patient to link it to the Observation
                // And change the Performer to the correct reference as well
                var patientRef = new ResourceReference(createdPat?.TypeName + "/" + createdPat?.Id);
                obs.Subject = patientRef;
                obs.Performer.Clear(); // removing existing reference
                obs.Performer.Add(patientRef);
                Observation createdObs = await client.CreateAsync(obs);

                Console.WriteLine($"The Observation was created with technical id: {createdObs?.Id}");


                // Example of a Read request
                Observation readObs = await client.ReadAsync<Observation>(createdObs.ResourceIdentity());

                // Change some details and update the Observation on the server
                ((Quantity)createdObs.Value).Value += 5;
                Observation updatedObs = await client.UpdateAsync(createdObs);


                // Now delete the Patient, using its identity
                await client.DeleteAsync(new ResourceIdentity(patientRef.Reference));
                // And delete the Observation, using the POCO
                await client.DeleteAsync(updatedObs);

                Console.WriteLine("Deleted the resources successfully");

                // If we try and read one of the again, it will cause an exception
                // Uncomment the following line to see that:
                // var error = await client.ReadAsync<Patient>(new ResourceIdentity(patientRef.Reference));


                // Performing a search
                string[] criteria = { "birthdate=1987" };
                Bundle searchResult = await client.SearchAsync<Patient>(criteria);

                Console.WriteLine("The total number of results is " +
                                    searchResult.Total is null ? "not specified in the Bundle"
                                                               : searchResult.Total);

                // Display some data
                foreach (var res in searchResult.GetResources())
                {
                    if (res is Patient)
                    {
                        string name = ((Patient)res).Name?.First().Given?.First() + " " +
                                      ((Patient)res).Name?.First().Family;
                        if (name is null) name = "<anonymous>";
                        Console.WriteLine($"Patient with technical id {res.Id} is called {name}");
                    }
                }

                // retrieve the next page of results
                searchResult = await client.ContinueAsync(searchResult);

                // Optionally repeat the foreach above to display the next patients

                // Adding some extras to the search
                string[] revIncludes = { "Observation:patient" };
                int count = 5;

                searchResult = await client.SearchAsync<Patient>(criteria, pageSize: count, revIncludes: revIncludes);

                foreach (var res in searchResult.GetResources())
                {
                    // We're only interested in the included resources now
                    if (res is Observation)
                    {
                        string text = ((Observation)res).Code.Text;
                        if (text is null) text = "<unknown>";
                        Console.WriteLine($"Observation with technical id {res.Id} is about: {text}");
                    }
                }
            }
            catch (FhirOperationException e)
            {
                Console.WriteLine(e.Message);
            }
  
        }
    }
}