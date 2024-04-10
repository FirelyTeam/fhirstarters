using Hl7.Fhir.Model;
using Hl7.Fhir.Serialization;
using Hl7.FhirPath.Sprache;
using System.IO;
using System.Text;
using System.Text.Json;
using System.Xml;
using System.Xml.Serialization;

namespace SDK_e_learning
{
    internal class Exercise2
    {
        internal static string SerializePatient(Patient pat)
        {
            // pat is the Patient from the precious exercise

            // Let's serialize it to JSON and output the string to Console
            // We have already created the options as a private variable
            var options = new JsonSerializerOptions().ForFhir().Pretty();
            string patientJson = JsonSerializer.Serialize(pat, options);

            Console.WriteLine("Here is the Patient data as FHIR JSON:\n" + patientJson);

            // We can also serialize to XML
            FhirXmlPocoSerializer xmlSerializer = new();

            StringBuilder sb = new();
            XmlWriterSettings settings = new() { Indent = true };

            using (var w = XmlWriter.Create(sb, settings))
            {
                xmlSerializer.Serialize(pat, w);
            }
            string patientXml = sb.ToString();

            Console.WriteLine("\n\nHere is the Patient data as FHIR XML:\n" + patientXml);

            // returning the JSON string to parse it in the next step
            return patientJson;
        }

        internal static Observation ParseData(string patientJson)
        {
            // Now for parsing some data...

            // JSON first:
            Observation obsFromJson = null;
            try
            {
                var options = new JsonSerializerOptions().ForFhir().Pretty();
                string obsFile = "observation.json";
                using FileStream openStream = File.OpenRead(obsFile);

                obsFromJson = JsonSerializer.Deserialize<Observation>(openStream, options);

                // We can now manipulate the POCO
                if (obsFromJson?.Value.TypeName == "Quantity")
                {
                    Quantity value = (Quantity)obsFromJson.Value;
                    value.Value -= 10;
                }

                Console.WriteLine("The Observation JSON was parsed, the value was changed:");
                Console.WriteLine(JsonSerializer.Serialize(obsFromJson, options));
                Console.WriteLine();

                // To do a structural check on the previously created Patient,
                // you can parse the serialized data:
                if (patientJson != null)
                {
                    var patFromString = JsonSerializer.Deserialize<Patient>(patientJson, options);
                    Console.WriteLine("The Patient was parsed successfully\n");
                }

            }
            catch (DeserializationFailedException e)
            {
                // Show the parsing error(s)
                Console.WriteLine(e.Message);

                // You can inspect the PartialResult to see what was possible to parse
                Console.WriteLine("If a partial result is available, this is it's type: " +
                                  e.PartialResult?.TypeName);
            }
            catch (Exception e)
            {
                // Some other exception occurred, possibly while reading the file
                Console.WriteLine(e.Message);
            }


            // XML next:
            try
            {
                string obsFile = "observation.xml";
                using FileStream openStream = File.OpenRead(obsFile);

                var xmlReader = XmlReader.Create(openStream);
                var deserializer = new FhirXmlPocoDeserializer();

                Resource parsedXml = deserializer.DeserializeResource(xmlReader);

                if (parsedXml.TypeName == "Observation")
                {
                    Observation obsFromXml = (Observation)parsedXml;

                    // We can now manipulate the POCO
                    if (obsFromXml.Value.TypeName == "Quantity")
                    {
                        Quantity value = (Quantity)obsFromXml.Value;
                        value.Value -= 10;
                    }

                    // Display the new XML
                    FhirXmlPocoSerializer xmlSerializer = new();

                    StringBuilder sb = new();
                    XmlWriterSettings settings = new() { Indent = true };

                    using (var w = XmlWriter.Create(sb, settings))
                    {
                        xmlSerializer.Serialize(obsFromXml, w);
                    }
                    Console.WriteLine("The Observation XML was parsed, the value was changed:");
                    Console.WriteLine(sb.ToString());
                    Console.WriteLine();
                }
            }
            catch (DeserializationFailedException e)
            {
                // Show the parsing error(s)
                Console.WriteLine(e.Message);

                // You can inspect the PartialResult to see what was possible to parse
                Console.WriteLine("If a partial result is available, this is it's type: " +
                                  e.PartialResult?.TypeName);
            }
            catch (Exception e)
            {
                // Some other exception occurred, possibly while reading the file
                Console.WriteLine(e.Message);
            }

            return obsFromJson;
        }
    }
}