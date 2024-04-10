using Hl7.Fhir.Model;

namespace SDK_e_learning
{
    internal class Exercise1
    {
        internal static Patient UseModel()
        {
            // Set a breakpoint anywhere below, or watch 'pat' to inspect the data added to the POCO

            Patient pat = new();

            HumanName name = new()
            {
                Use = HumanName.NameUse.Usual,
                Given = new string[] { "Adam" },
                Family = "Everyman"
            };

            // another option is to use this fluent notation, but if you want the Use field filled,
            // you would need to add that separately:
            //
            // HumanName name = new HumanName().WithGiven("Adam").AndFamily("Everyman");
            // name.Use = HumanName.NameUse.Usual;

            pat.Name.Add(name);

            pat.Identifier.Add(new Identifier("http://www.miniaf.alp/citreg", "1020304050"));

            pat.BirthDate = "1978-07-26";

            pat.Gender = AdministrativeGender.Male;

            // Adding an extension on a property using the FHIR type
            pat.GenderElement.AddExtension("http://hl7.org/fhir/StructureDefinition/originalText", new FhirString("M"));

            Address address = new()
            {
                Line = new string[] { "62, Anystreet" },
                City = "Alphatown",
                PostalCode = "59450",
                Country = "Alpha"
            };
            pat.Address.Add(address);

            pat.Telecom.Add(new ContactPoint(ContactPoint.ContactPointSystem.Email,
                                             ContactPoint.ContactPointUse.Work,
                                             "adam.everyman@example.org"));
            pat.Telecom.Add(new ContactPoint(ContactPoint.ContactPointSystem.Phone,
                                             ContactPoint.ContactPointUse.Home,
                                             "020 7422 3666"));


            // Extra options

            Address birthPlace = new() { City = "Portsmouth", Country = "UK" };
            pat.AddExtension("http://hl7.org/fhir/StructureDefinition/patient-birthPlace", birthPlace);
            // or
            // Extension ext = new Extension();
            // ext.Url = "http://hl7.org/fhir/StructureDefinition/patient-birthPlace";
            // ext.Value = birthPlace;
            // pat.Extension.Add(ext);

            pat.Deceased = new FhirBoolean(false);

            // Adding an extra name
            HumanName oldName = new()
            {
                Use = HumanName.NameUse.Old,
                Family = "Jones"
            };
            pat.Name.Add(oldName);

            return pat;
        }
    }
}
