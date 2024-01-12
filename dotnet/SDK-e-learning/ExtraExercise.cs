using CsvHelper;
using Hl7.Fhir.Model;
using Hl7.Fhir.Rest;
using System.Formats.Asn1;
using System.Globalization;

namespace SDK_e_learning
{
    internal class ExtraExercise
    {
        internal static async System.Threading.Tasks.Task ReadMapAndStoreData()
        {
            TextReader reader = new StreamReader("sample-data.csv");
            var csvReader = new CsvReader(reader, CultureInfo.InvariantCulture);
            var records = csvReader.GetRecords<CSVModel>();

            var patientList = new List<Patient>();
            var observationList = new List<Observation>();

            var map = new Mapper();
            foreach (var r in records)
            {
                var pat = map.GetPatient(r);
                if (!patientList.Exists(p => p.Id == pat.Id))
                    patientList.Add(pat);

                observationList.Add(map.GetWhiteBloodCellCount(r));
                observationList.Add(map.GetRedBloodCellCount(r));
                observationList.Add(map.GetHemoglobin(r));
            }

            DisplayData(patientList, observationList);
            await StoreData(patientList, observationList);
        }

        static void DisplayData(List<Patient> patients, List<Observation> observations)
        {
            // This can be greatly improved, but just functions as an example of obtaining data from
            // a FHIR resource and displaying that


            Console.WriteLine($"There are {patients.Count} patients in the file. Here is their data:");

            foreach (var p in patients)
            {
                Console.WriteLine("-----------------------------------------------------------------------------");

                Console.WriteLine("Data for patient: " + p.Name.First().Given.First() + " " + p.Name.First().Family);

                var patsObservations = observations.FindAll(o => o.Subject.Reference == "Patient/" + p.Id);

                // Split the observations based on type
                var wbcCode = new CodeableConcept("http://loinc.org", "6690-2");
                var rbcCode = new CodeableConcept("http://loinc.org", "789-8");
                var hbCode = new CodeableConcept("http://loinc.org", "718-7");

                var wbcObservations = patsObservations.FindAll(o => o.Code.Matches(wbcCode));
                var rbcObservations = patsObservations.FindAll(o => o.Code.Matches(rbcCode));
                var hbObservations = patsObservations.FindAll(o => o.Code.Matches(hbCode));

                Console.WriteLine("  WBC Observations:");
                foreach (var obs in wbcObservations)
                {
                    var qty = (Quantity)obs.Value;
                    Console.WriteLine($"    {obs.Effective}  {qty.Value} {qty.Code}");
                }
                Console.WriteLine("  RBC Observations:");
                foreach (var obs in rbcObservations)
                {
                    var qty = (Quantity)obs.Value;
                    Console.WriteLine($"    {obs.Effective}  {qty.Value} {qty.Code}");
                }
                Console.WriteLine("  HB Observations:");
                foreach (var obs in hbObservations)
                {
                    var qty = (Quantity)obs.Value;
                    Console.WriteLine($"    {obs.Effective}  {qty.Value} {qty.Code}");
                }
            }
            Console.WriteLine("-----------------------------------------------------------------------------");

        }

        private static async System.Threading.Tasks.Task StoreData(List<Patient> patients, List<Observation> observations)
        {
            // This method will send all the data as resources to the FHIR server

            var client = new FhirClient("https://server.fire.ly/R4");

            try
            {
                Patient createdPat = null;
                foreach (var p in patients)
                {
                    createdPat = await client.CreateAsync(p);

                    var patsObservations = observations.FindAll(o => o.Subject.Reference == "Patient/" + p.Id);
                    foreach (var o in patsObservations)
                    {
                        // Updating the reference of the Observation
                        o.Subject.Reference = "Patient/" + createdPat.Id;
                        var createdObs = await client.CreateAsync(o);
                    }

                    Console.WriteLine($"The patient and its {patsObservations.Count} observations have been created on the server");
                }

                // Try an operation call to get the patient record for the last Patient
                // This may throw an error if the server does not support the 'everything' operation
                if (createdPat != null)
                {
                    Bundle record = await client.FetchPatientRecordAsync(createdPat.ResourceIdentity());

                    Console.WriteLine($"The record contains {record.Entry.Count} resources, the first of which is the Patient");
                }
            }
            catch (FhirOperationException e)
            {
                Console.WriteLine(e.Message);
            }
        }

        internal class CSVModel
        {
            //SEQN,TIMESTAMP,PATIENT_ID,PATIENT_FAMILYNAME,PATIENT_GIVENNAME,PATIENT_GENDER,WBC,RBC,HB
            public string SEQN { get; set; }
            public string TIMESTAMP { get; set; }
            public string PATIENT_ID { get; set; }
            public string PATIENT_FAMILYNAME { get; set; }
            public string PATIENT_GIVENNAME { get; set; }
            public string PATIENT_GENDER { get; set; }
            public string WBC { get; set; }
            public string RBC { get; set; }
            public string HB { get; set; }


            public CSVModel()
            { }
        }

        internal class Mapper
        {
            public Patient GetPatient(CSVModel record)
            {
                Patient patient = new Patient();

                patient.Id = record.PATIENT_ID;

                // Check your data first, and change this code if you have more options or null values for gender
                patient.Gender = (record.PATIENT_GENDER == "F") ? AdministrativeGender.Female : AdministrativeGender.Male;

                patient.Name.Add(new HumanName().WithGiven(record.PATIENT_GIVENNAME).AndFamily(record.PATIENT_FAMILYNAME));
                // This could also be accomplised without using fluent methods:
                //var name = new HumanName();
                //name.GivenElement.Add(new FhirString(record.PATIENT_GIVENNAME)); // given names go into a list
                //name.Family = record.PATIENT_FAMILYNAME;
                //patient.Name.Add(name);

                return patient;
            }

            public Observation GetWhiteBloodCellCount(CSVModel record)
            {
                // White blood cell count - This corresponds to LOINC code:
                // Code:        6690-2
                // Display:     Leukocytes [#/volume] in Blood by Automated count
                // Unit System: http://unitsofmeasure.org
                // Unit Code:   10*3/uL

                Observation wbc = new Observation();

                wbc.Code = new CodeableConcept("http://loinc.org", "6690-2", "Leukocytes [#/volume] in Blood by Automated count");

                wbc.Value = new Quantity(decimal.Parse(record.WBC), "10*3/uL");

                wbc.Subject = new ResourceReference("Patient/" + record.PATIENT_ID);

                wbc.Identifier.Add(new Identifier("http://acme.org/sequence-nos", record.SEQN));

                wbc.Effective = new FhirDateTime(record.TIMESTAMP);

                // Mandatory field, set to 'final' since this comes from a completed dataset
                wbc.Status = ObservationStatus.Final;

                // Optionally add the category
                wbc.Category.Add(new CodeableConcept("http://terminology.hl7.org/CodeSystem/observation-category", "laboratory", "Laboratory"));
                return wbc;
            }

            public Observation GetRedBloodCellCount(CSVModel record)
            {
                // Red blood cell count - This corresponds to LOINC code:
                // Code:        789-8
                // Display:     Erythrocytes [#/volume] in Blood by Automated count
                // Unit System: http://unitsofmeasure.org
                // Unit Code:   10*6/uL

                Observation rbc = new Observation();

                rbc.Code = new CodeableConcept("http://loinc.org", "789-8", "Erythrocytes [#/volume] in Blood by Automated count");

                rbc.Value = new Quantity(decimal.Parse(record.RBC), "10*6/uL");

                rbc.Subject = new ResourceReference("Patient/" + record.PATIENT_ID);

                rbc.Identifier.Add(new Identifier("http://acme.org/sequence-nos", record.SEQN));

                rbc.Effective = new FhirDateTime(record.TIMESTAMP);

                // Mandatory field, set to 'final' since this comes from a completed dataset
                rbc.Status = ObservationStatus.Final;

                // Optionally add the category
                rbc.Category.Add(new CodeableConcept("http://terminology.hl7.org/CodeSystem/observation-category", "laboratory", "Laboratory"));
                return rbc;
            }

            public Observation GetHemoglobin(CSVModel record)
            {
                // Hemoglobin
                // Code:        718-7
                // Display:     Hemoglobin [Mass/volume] in Blood
                // Unit System: http://unitsofmeasure.org
                // Unit Code:   g/dL

                Observation hb = new Observation();

                hb.Code = new CodeableConcept("http://loinc.org", "718-7", "Hemoglobin [Mass/volume] in Blood");

                hb.Value = new Quantity(decimal.Parse(record.HB), "g/dL");

                hb.Subject = new ResourceReference("Patient/" + record.PATIENT_ID);

                hb.Identifier.Add(new Identifier("http://acme.org/sequence-nos", record.SEQN));

                hb.Effective = new FhirDateTime(record.TIMESTAMP);

                // Mandatory field, set to 'final' since this comes from a completed dataset
                hb.Status = ObservationStatus.Final;

                // Optionally add the category
                hb.Category.Add(new CodeableConcept("http://terminology.hl7.org/CodeSystem/observation-category", "laboratory", "Laboratory"));

                return hb;
            }
        }
    }
}