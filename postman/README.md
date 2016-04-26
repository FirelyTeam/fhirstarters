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

First, set the method to `POST`, and the URL to `http://fhirtest.uhn.ca/baseDstu2` as shown in the screenshot below:

<img src="./images/postman_url.png"/>
