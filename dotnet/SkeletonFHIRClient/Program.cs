using Hl7.Fhir.Rest;

FhirClientSettings clientSettings = new FhirClientSettings();
clientSettings.PreferredFormat = ResourceFormat.Json;
clientSettings.PreferredParameterHandling = SearchParameterHandling.Strict;
// Change the above, or check and set any other settings you want

FhirClient client = new FhirClient("https://server.fire.ly/R4", clientSettings);

// This skeleton setup does not use the FhirClient yet
// Add your implementation here, or see the other projects for examples