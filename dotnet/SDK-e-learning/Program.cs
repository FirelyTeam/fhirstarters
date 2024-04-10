using Hl7.Fhir.Model;

namespace SDK_e_learning
{
    internal class Program
    {
        private static async System.Threading.Tasks.Task Main(string[] args)
        {
            //Patient pat = Exercise1.UseModel();
            //string jsonPat = Exercise2.SerializePatient(pat);
            //Observation obs = Exercise2.ParseData(jsonPat);
            //await Exercise3.UsingFhirClient(pat, obs);

            await ExtraExercise.ReadMapAndStoreData();
        }
    }
}