# Using Postman to Play with FHIR

This tutorial shows how to use Postman (a browser based REST tool) to take FHIR for a spin.

## Installing Postman

* Open Chrome, and browse to the following URL: [Postman](https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en) 

<img src="./images/install_postman.png"/>

* Open the Postman App
* Either sign in or click "`skip this, go straight to app`"

# Create a Resource

The following example shows how to create a resource by POSTing it to a server. If you prefer JSON or XML, you can skip one of the following two examples.

## Create a JSON Resource

First, set the method to `POST`, and the URL to `http://fhirtest.uhn.ca/baseDstu2/Patient` as shown in the screenshot below:

<img src="./images/postman_url.png" style="border: 1px solid black;"/>

Now, switch to the `Headers` tab, and add a header with a key/name of `Content-Type` and a value of `application/json+fhir`

<img src="./images/header_json.png"/>

Add the following content, which is a simple Patient resource:

```json
{
  "resourceType": "Patient",
  "identifier": [
    {
      "system": "urn:oid:1.2.36.146.595.217.0.1",
      "value": "12345",
    }
  ],
  "name": [
    {
      "family": [
        "Chalmers"
      ],
      "given": [
        "Peter",
        "James"
      ]
    }
  ],
  "telecom": [
    {
      "system": "phone",
      "value": "(03) 5555 6473",
      "use": "work"
    }
  ]
}
```

<img src="./images/patient_json.png"/>

Click `Send` and then scroll to the bottom of the window.

<img src="./images/create_response_json.json"/>


