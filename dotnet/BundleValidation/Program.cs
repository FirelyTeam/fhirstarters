using Hl7.Fhir.Model;
using Hl7.Fhir.Rest;
using Hl7.Fhir.Serialization;
using Hl7.Fhir.Validation;
using Hl7.Fhir.Specification.Source;

namespace FirelyBundleValidation
{
    public class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("Validating Bundles with Firely .NET SDK!");

            FhirJsonParser fp = new FhirJsonParser();

            // parse valid Patient payload using FHIRJsonParser
            string strPatient = "{\"resourceType\": \"Patient\",\"id\": \"1006\",\"extension\": [{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateVersion\",\"valueString\": \"1.0.0\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateDate\",\"valueString\": \"2021-12-01\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateTargetMarket\",\"valueString\": \"France\"}],\"identifier\": {\"system\": \"https://fhir-data-converter.azurehealthcareapis.com/patient\",\"value\": \"1006\"},\"meta\": {\"profile\": [\"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/FHIRPatientProfile\"]},\"name\": [{\"use\": \"official\",\"given\": [\"Mia\"],\"family\": \"Anderson\"}],\"telecom\": [{\"system\": \"phone\",\"value\": \"7199 284153\"}],\"gender\": \"male\",\"birthDate\": \"2022-01-07\",\"address\": [{\"country\": \"N/A\"}]}";            
            Patient validPatient = fp.Parse<Patient>(strPatient);
            // print patient details
            Console.WriteLine("\nValid patient details:");
            Console.WriteLine("Family name: {0}", validPatient.Name.FirstOrDefault().Family);
            Console.WriteLine("Gender: {0}", validPatient.Gender);
            Console.WriteLine("BirthDate: {0}", validPatient.BirthDate);

            //// parse patient without gender
            string strPatientWithNoGender = "{\"resourceType\": \"Patient\",\"id\": \"1006\",\"extension\": [{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateVersion\",\"valueString\": \"1.0.0\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateDate\",\"valueString\": \"2021-12-01\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateTargetMarket\",\"valueString\": \"France\"}],\"identifier\": {\"system\": \"https://fhir-data-converter.azurehealthcareapis.com/patient\",\"value\": \"1006\"},\"meta\": {\"profile\": [\"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/FHIRPatientProfile\"]},\"name\": [{\"use\": \"official\",\"given\": [\"Mia\"],\"family\": \"Anderson\"}],\"telecom\": [{\"system\": \"phone\",\"value\": \"7199 284153\"}],\"birthDate\": \"2022-01-07\",\"address\": [{\"country\": \"N/A\"}]}";
            Patient patWithNoGender = fp.Parse<Patient>(strPatientWithNoGender);

            // parse valid bundle
            string strBundle = "{\"resourceType\": \"Bundle\",\"type\": \"batch\",\"entry\": [{\"resource\": {\"resourceType\": \"Patient\",\"id\": \"1006\",\"extension\": [{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateVersion\",\"valueString\": \"1.0.0\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateDate\",\"valueString\": \"2021-12-01\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateTargetMarket\",\"valueString\": \"France\"}],\"identifier\": {\"system\": \"https://fhir-data-converter.azurehealthcareapis.com/patient\",\"value\": \"1006\"},\"meta\": {\"profile\": [\"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/FHIRPatientProfile\"]},\"name\": [{\"use\": \"official\",\"given\": [\"Mia\"],\"family\": \"Anderson\"}],\"telecom\": [{\"system\": \"phone\",\"value\": \"7199 284153\"}],\"gender\": \"male\",\"birthDate\": \"2022-01-07\",\"address\": [{\"country\": \"N/A\"}]},\"request\": {\"method\": \"PUT\",\"url\": \"Patient/1006\"}},{\"resource\": {\"resourceType\": \"Claim\",\"id\": \"3000\",\"extension\": [{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateVersion\",\"valueString\": \"1.0.0\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateDate\",\"valueString\": \"2021-12-01\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateTargetMarket\",\"valueString\": \"France\"}],\"identifier\": [{\"system\": \"https://fhir-data-converter.azurehealthcareapis.com/claim\",\"value\": \"300030003000\"}],\"status\": \"active\",\"type\": {\"coding\": [{\"system\": \"http://terminology.hl7.org/CodeSystem/claim-type\",\"code\": \"oral\"}]},\"use\": \"claim\",\"created\": \"2021-12-10\",\"patient\": {\"reference\": \"Patient/1006\"},\"enterer\": {\"reference\": \"Practitioner/5002\"},\"insurer\": {\"reference\": \"Organization/2022\"},\"provider\": {\"reference\": \"Organization/2032\"},\"priority\": {\"coding\": [{\"code\": \"normal\"}]},\"payee\": {\"type\": {\"coding\": [{\"code\": \"subscriber\"}]}},\"careTeam\": [{\"sequence\": 1,\"provider\": {\"reference\": \"Practitioner/5002\"}}],\"diagnosis\": [{\"sequence\": 1,\"diagnosisCodeableConcept\": {\"coding\": [{\"code\": \"60022\"}]}}],\"insurance\": [{\"sequence\": 1,\"focal\": true,\"identifier\": {\"system\": \"https://fhir-data-converter.azurehealthcareapis.com/claim\",\"value\": \"12345\"},\"coverage\": {\"reference\": \"Coverage/9876B1\"}}],\"item\": [{\"sequence\": 1,\"careTeamSequence\": [1],\"productOrService\": {\"coding\": [{\"code\": \"11875059479265419\"}]},\"servicedDate\": \"1982-01-18\",\"unitPrice\": {\"value\": 9012,\"currency\": \"EUR\"},\"net\": {\"value\": 9012,\"currency\": \"EUR\"}}]},\"request\": {\"method\": \"PUT\",\"url\": \"Claim/3000\"}},{\"resource\": {\"resourceType\": \"Organization\",\"id\": \"2022\",\"extension\": [{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateVersion\",\"valueString\": \"1.0.0\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateDate\",\"valueString\": \"2021-12-01\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateTargetMarket\",\"valueString\": \"France\"}],\"identifier\": [{\"system\": \"https://fhir-data-converter.azurehealthcareapis.com/organization\",\"value\": \"org-2022\"}],\"name\": \"Broadway Insurances\",\"alias\": \"Broadway Insurances\",\"address\": [{\"line\": \"83 Washington Drive Great Sankey WA5 8WZ \"}]},\"request\": {\"method\": \"PUT\",\"url\": \"Organization/2022\"}},{\"resource\": {\"resourceType\": \"Organization\",\"id\": \"2032\",\"extension\": [{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateVersion\",\"valueString\": \"1.0.0\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateDate\",\"valueString\": \"2021-12-01\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateTargetMarket\",\"valueString\": \"France\"}],\"identifier\": [{\"system\": \"https://fhir-data-converter.azurehealthcareapis.com/organization\",\"value\": \"org-2032\"}],\"name\": \"The Provider\",\"alias\": \"The Provider\",\"address\": [{\"line\": \"83 Washington Drive Great Sankey WA5 8WZ \"}]},\"request\": {\"method\": \"PUT\",\"url\": \"Organization/2032\"}},{\"resource\": {\"resourceType\": \"Practitioner\",\"id\": \"5002\",\"extension\": [{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateVersion\",\"valueString\": \"1.0.0\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateDate\",\"valueString\": \"2021-12-01\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateTargetMarket\",\"valueString\": \"France\"}],\"identifier\": [{\"system\": \"https://fhir-data-converter.azurehealthcareapis.com/practitioner\",\"value\": \"pract-5002\"}],\"name\": [{\"use\": \"official\",\"given\": [ \"Sigmund Freud\" ],\"family\": \"Sigmund Freud\"}],\"address\": [{\"line\": \"83 Washington Drive Great Sankey WA5 8WZ \"}]},\"request\": {\"method\": \"PUT\",\"url\": \"Practitioner/5002\"}}]}";
            Bundle bund = fp.Parse<Bundle>(strBundle);

            // parse bundle patient without gender
            string strBundleWithNoGender = "{\"resourceType\": \"Bundle\",\"type\": \"batch\",\"entry\": [{\"resource\": {\"resourceType\": \"Patient\",\"id\": \"1006\",\"extension\": [{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateVersion\",\"valueString\": \"1.0.0\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateDate\",\"valueString\": \"2021-12-01\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateTargetMarket\",\"valueString\": \"France\"}],\"identifier\": {\"system\": \"https://fhir-data-converter.azurehealthcareapis.com/patient\",\"value\": \"1006\"},\"meta\": {\"profile\": [\"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/FHIRPatientProfile\"]},\"name\": [{\"use\": \"official\",\"given\": [\"Mia\"],\"family\": \"Anderson\"}],\"telecom\": [{\"system\": \"phone\",\"value\": \"7199 284153\"}],\"birthDate\": \"2022-01-07\",\"address\": [{\"country\": \"N/A\"}]},\"request\": {\"method\": \"PUT\",\"url\": \"Patient/1006\"}},{\"resource\": {\"resourceType\": \"Claim\",\"id\": \"3000\",\"extension\": [{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateVersion\",\"valueString\": \"1.0.0\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateDate\",\"valueString\": \"2021-12-01\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateTargetMarket\",\"valueString\": \"France\"}],\"identifier\": [{\"system\": \"https://fhir-data-converter.azurehealthcareapis.com/claim\",\"value\": \"300030003000\"}],\"status\": \"active\",\"type\": {\"coding\": [{\"system\": \"http://terminology.hl7.org/CodeSystem/claim-type\",\"code\": \"oral\"}]},\"use\": \"claim\",\"created\": \"2021-12-10\",\"patient\": {\"reference\": \"Patient/1006\"},\"enterer\": {\"reference\": \"Practitioner/5002\"},\"insurer\": {\"reference\": \"Organization/2022\"},\"provider\": {\"reference\": \"Organization/2032\"},\"priority\": {\"coding\": [{\"code\": \"normal\"}]},\"payee\": {\"type\": {\"coding\": [{\"code\": \"subscriber\"}]}},\"careTeam\": [{\"sequence\": 1,\"provider\": {\"reference\": \"Practitioner/5002\"}}],\"diagnosis\": [{\"sequence\": 1,\"diagnosisCodeableConcept\": {\"coding\": [{\"code\": \"60022\"}]}}],\"insurance\": [{\"sequence\": 1,\"focal\": true,\"identifier\": {\"system\": \"https://fhir-data-converter.azurehealthcareapis.com/claim\",\"value\": \"12345\"},\"coverage\": {\"reference\": \"Coverage/9876B1\"}}],\"item\": [{\"sequence\": 1,\"careTeamSequence\": [1],\"productOrService\": {\"coding\": [{\"code\": \"11875059479265419\"}]},\"servicedDate\": \"1982-01-18\",\"unitPrice\": {\"value\": 9012,\"currency\": \"EUR\"},\"net\": {\"value\": 9012,\"currency\": \"EUR\"}}]},\"request\": {\"method\": \"PUT\",\"url\": \"Claim/3000\"}},{\"resource\": {\"resourceType\": \"Organization\",\"id\": \"2022\",\"extension\": [{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateVersion\",\"valueString\": \"1.0.0\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateDate\",\"valueString\": \"2021-12-01\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateTargetMarket\",\"valueString\": \"France\"}],\"identifier\": [{\"system\": \"https://fhir-data-converter.azurehealthcareapis.com/organization\",\"value\": \"org-2022\"}],\"name\": \"Broadway Insurances\",\"alias\": \"Broadway Insurances\",\"address\": [{\"line\": \"83 Washington Drive Great Sankey WA5 8WZ \"}]},\"request\": {\"method\": \"PUT\",\"url\": \"Organization/2022\"}},{\"resource\": {\"resourceType\": \"Organization\",\"id\": \"2032\",\"extension\": [{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateVersion\",\"valueString\": \"1.0.0\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateDate\",\"valueString\": \"2021-12-01\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateTargetMarket\",\"valueString\": \"France\"}],\"identifier\": [{\"system\": \"https://fhir-data-converter.azurehealthcareapis.com/organization\",\"value\": \"org-2032\"}],\"name\": \"The Provider\",\"alias\": \"The Provider\",\"address\": [{\"line\": \"83 Washington Drive Great Sankey WA5 8WZ \"}]},\"request\": {\"method\": \"PUT\",\"url\": \"Organization/2032\"}},{\"resource\": {\"resourceType\": \"Practitioner\",\"id\": \"5002\",\"extension\": [{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateVersion\",\"valueString\": \"1.0.0\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateDate\",\"valueString\": \"2021-12-01\"},{\"url\": \"https://fhir-data-converter.azurehealthcareapis.com/StructureDefinition/templateTargetMarket\",\"valueString\": \"France\"}],\"identifier\": [{\"system\": \"https://fhir-data-converter.azurehealthcareapis.com/practitioner\",\"value\": \"pract-5002\"}],\"name\": [{\"use\": \"official\",\"given\": [ \"Sigmund Freud\" ],\"family\": \"Sigmund Freud\"}],\"address\": [{\"line\": \"83 Washington Drive Great Sankey WA5 8WZ \"}]},\"request\": {\"method\": \"PUT\",\"url\": \"Practitioner/5002\"}}]}";
            Bundle bundWithNoGender = fp.Parse<Bundle>(strBundleWithNoGender);
            Console.WriteLine(bundWithNoGender.Id);

            // print some entries in bundle
            Console.WriteLine("\nValid bundle entries:");
            foreach (var entry in bund.Entry)
            {
                Console.WriteLine("Type: {0}", entry.Resource.TypeName);
                Console.WriteLine("Id: {0}\n", entry.Resource.Id);
            }

            // create profile source using `Profiles` folder
            var profileSource = new CachedResolver(new MultiResolver(
                new DirectorySource(@"Profiles",
                    new DirectorySourceSettings() { IncludeSubDirectories = true }),
                    ZipSource.CreateValidationSource()));

            // validation settings
            var validationSettings = new ValidationSettings
            {
                ResourceResolver = profileSource,
                GenerateSnapshot = true,
                Trace = false,
                ResolveExternalReferences = false
            };
            var validator = new Hl7.Fhir.Validation.Validator(validationSettings);

            // print validation results
            Console.WriteLine("\n------------------------------");
            Console.WriteLine("Validate a 'Patient':");
            var result = validator.Validate(validPatient);
            Console.WriteLine(result);

            Console.WriteLine("\n------------------------------");
            Console.WriteLine("Validate 'Patient' with no gender field:");
            var resultNoGender = validator.Validate(patWithNoGender);
            Console.WriteLine(resultNoGender);

            Console.WriteLine("\n------------------------------");
            Console.WriteLine("Validate 'Bundle':");
            var resultBundle = validator.Validate(bund);
            Console.WriteLine(resultBundle);

            Console.WriteLine("\n------------------------------");
            Console.WriteLine("Validate 'Bundle' with a patient with no gender field:");
            var resultBundleNoGender = validator.Validate(bundWithNoGender);
            Console.WriteLine(resultBundleNoGender);
        }
    }
}