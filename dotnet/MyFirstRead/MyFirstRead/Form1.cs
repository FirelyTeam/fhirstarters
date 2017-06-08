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

namespace MyFirstRead
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

            //var client = new FhirClient("http://test.fhir.org/r3/open");
            var client = new FhirClient("http://vonk.furore.com");
            //var client = new FhirClient("http://fhirtest.uhn.ca/baseDstu3");

            try
            {
                var obs = client.Read<Observation>("Observation/example");

                txtDisplay.Text = "Observation read, id: " + obs.Id;
            }
            catch (Exception err)
            {
                txtError.Lines = new string[] { "An error has occurred:", err.Message };
            }
        }
    }
}
