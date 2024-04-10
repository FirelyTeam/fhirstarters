using Hl7.Fhir.Model;
using Hl7.Fhir.Rest;

FhirClientSettings clientSettings = new FhirClientSettings();
clientSettings.PreferredFormat = ResourceFormat.Json;
clientSettings.PreferredParameterHandling = SearchParameterHandling.Strict;
// Change the above, or check and set any other settings you want

// Choose your preferred FHIR server or add your own
// More at https://confluence.hl7.org/display/FHIR/Public+Test+Servers

FhirClient client = new FhirClient("https://server.fire.ly/R4", clientSettings);
// FhirClient client = new FhirClient("http://hapi.fhir.org/baseR4", clientSettings);
// FhirClient client = new FhirClient("http://test.fhir.org/r4", clientSettings);

try
{
    // Let's do a Read interaction first
    Observation obs = await client.ReadAsync<Observation>("Observation/example");

    Console.WriteLine($"Observation was read, id: {obs.Id}, type: {obs.Code.Coding.First().Display}");
    Console.WriteLine($"The summary of the resource: '{obs.Text?.Div}'");


    // You can create a new Patient resource in memory
    // You can change the values for the Patient to your own, more unique, values
    // Note however that the server we're going to send this to is a public server
    Patient pat = new Patient();
    pat.Name.Add(new HumanName().WithGiven("Adam").AndFamily("Everyman"));
    pat.Identifier.Add(new Identifier("http://www.miniaf.alp/citreg", "1020304050"));
    pat.BirthDate = "1987-02-16";
    Address address = new Address();
    address.Line = new string[] { "62, Anystreet" };
    address.City = "Alphatown";
    address.PostalCode = "59450";
    pat.Address.Add(address);

    // Send it to the server to be created, and capture the result
    Patient createdPat = await client.CreateAsync(pat);

    Console.WriteLine($"Patient was created, new id: {createdPat.Id}");


    // Now try to update the resource, using the captured result from the server
    HumanName name = new HumanName() { Use = HumanName.NameUse.Old, Family = "Jones" };
    createdPat.Name.Add(name);
    Patient updatedPat = await client.UpdateAsync(createdPat);

    Console.WriteLine($"Patient was updated, new version id: {updatedPat.Meta.VersionId}");
}
catch (Exception err)
{
    Console.WriteLine($"An error has occurred: {err.Message}");
}