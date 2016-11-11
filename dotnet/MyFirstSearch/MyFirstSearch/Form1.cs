using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Hl7.Fhir.Model;
using Hl7.Fhir.Rest;

namespace MyFirstSearch
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void btnStart_Click(object sender, EventArgs e)
        {
            // Choose your preferred FHIR server or add your own
            // More at http://wiki.hl7.org/index.php?title=Publicly_Available_FHIR_Servers_for_testing

            var client = new FhirClient("http://fhir2.healthintersections.com.au/open");
            //var client = new FhirClient("http://spark.furore.com/fhir");
            //var client = new FhirClient("http://fhirtest.uhn.ca/");
            //var client = new FhirClient("https://fhir-open-api-dstu2.smartplatforms.org");
            //var client = new FhirClient("http://fhirplace.health-samurai.io/");

            try
            {
                var results = client.Search<Patient>(new string[] { "name=pete" });
                //var results = client.Search<Patient>(new string[] { "name=pete", "birthdate=1974-12-25" });

                txtDisplay.Text = "";

                while (results != null)
                {
                    if (results.Total == 0) txtDisplay.Text = "No results found";

                    foreach (var entry in results.Entry)
                    {
                        txtDisplay.Text += "Found patient with id " + entry.Resource.Id + Environment.NewLine;
                    }

                    // get the next page of results
                    results = client.Continue(results);
                }
            }
            catch (Exception err)
            {
                txtError.Lines = new string[] { "An error has occurred:", err.Message };
            }

        }
    }
}
