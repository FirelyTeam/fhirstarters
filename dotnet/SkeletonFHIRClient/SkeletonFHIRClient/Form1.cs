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

namespace SkeletonFHIRClient
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

            //var client = new FhirClient("http://fhir2.healthintersections.com.au/open");
            var client = new FhirClient("http://spark.furore.com/fhir");
            //var client = new FhirClient("http://fhirtest.uhn.ca/baseDstu2");
            //var client = new FhirClient("https://fhir-open-api-dstu2.smarthealthit.org/");

        }
    }
}
