# Searching Hands-On

Use the following base URL for the server in doing this tutorial:

http://hapi.fhir.org/baseDstu3/

You can also use the following alternate base URL:

https://try.smilecdr.com:8000/

Go through the following searches and try to build these searchs yourself by entering the appropriate URL in the URL bar of a browser.

* Search for all patients with family name of "Smith"

Hint: Start with the following URL and add search parameters to it: http://hapi.fhir.org/baseDstu3/Patient

[Answer](http://hapi.fhir.org/baseDstu3/Patient?family=smith)

* Search for all patients with family name "Smith" and a first name beginning with the letter "J"

[Answer](http://hapi.fhir.org/baseDstu3/Patient?family=smith&given=j)

* Search for all clinical observations for the Patient with ID: Patient/29081

[Answer](http://hapi.fhir.org/baseDstu3/Observation?subject=Patient/29081)

* Search for all Erythrocyte counts for the patient with ID: Patient/203

Hint: The LOINC code for the code `Erythrocytes [#/volume] in Blood by Automated count` is:

System: `http://loinc.org`
Code: `789-8`

[Answer](http://hapi.fhir.org/baseDstu3/Observation?subject=Patient/203&code=http://loinc.org|789-8)

* Search for all Erythrocyte counts across all patients, where the count is ABOVE 4.12

Hint, you can omit the unit on the `value-quantity` parameter if you trust that the units will be consistent in your dataset.

[Answer](http://hapi.fhir.org/baseDstu3/Observation?code=http://loinc.org|789-8&value-quantity=gt4.12)
