
# Use Case: Salesforce Account Management API

# Scenario:
  You are tasked with creating a Spring Boot application that allows users to create, read, update, and delete (CRUD) 
  Salesforce/LocalDB Accounts through a RESTful API. The API will authenticate with Salesforce using OAuth 2.0 and interact 
  with the Salesforce REST API to manage account records.

# Features:
1) Authenticate with Salesforce using OAuth 2.0.
2) CRUD operations on Salesforce Account objects:
    1) Create: Add a new Account in Salesforce/LocalDB.
    2) Read: Retrieve Account details from Salesforce/LocalDB.
    3) Update: Modify an existing Account in Salesforce/LocalDB.
    4) Delete: Remove an Account from Salesforce/LocalDB.

# Required Setup:
    1) Salesforce Connected App: You need to create a Connected App in Salesforce to obtain the Client ID and Client Secret.
    2) Salesforce API Enabled: Ensure your Salesforce org has API access.
    3) Security Token: Obtain the security token from your Salesforce account if needed.  

# Steps to Implement
  1. Create Salesforce Connected App:
     1) Go to Setup → App Manager in Salesforce.
     2) Create a new Connected App and configure OAuth settings:
     3) Enable OAuth Settings.
     4) Add OAuth Scopes like api and refresh_token.
     5) Set Callback URL to something like http://[hostname]:[portnumber]/oauth/callback (this is used in the authorization process).
  2. Spring Boot Project Setup:
     1) Configure Run -> Edit Configuration -> Environment variable(set all fields(client_id,client_secret,username,password,grant_type) needed for salesforce authentication)
     2) Configure Run -> Edit Configuration -> Application -> set Application Name (normally main class)

  3. Configuration for Salesforce OAuth 2.0:
     1) Add your Salesforce Client ID, Client Secret, and other configuration details to your environment variable:

  4. Salesforce OAuth 2.0 Authentication Service:
     1) Create a service that will authenticate with Salesforce using OAuth 2.0 and get the access token.

  5. Salesforce Account Service (CRUD Operations):
     1)  This service will handle CRUD operations using the Salesforce REST API and the access token obtained from the authentication service.

## Running the Application:
    Start Application only from Intellij but not from cmd using mvn spring-boot:run

## Swagger-ui:
    swagger-ui is integrated into project, once the application is started, then just type http://[hostname]:[portnumber]/swagger-ui/index.html on browser.
    user will be prompted for username/password, once user provided correct username and correct password then user will be forwarded  
    
## Testing the API Endpoints:
     1) Create Account: POST /api/salesforce/accounts?name=TestAccount
     2) Get Account: GET /api/salesforce/accounts/{id}
     3) Update Account: PUT /api/salesforce/accounts/{id}?name=NewAccountName
     4) Delete Account: DELETE /api/salesforce/accounts/{id}
     5) This is a basic structure for integrating Salesforce with a Spring Boot application. You can expand it further to handle additional objects, handle pagination, add logging, and manage error handling.

Please Note:
use the following URL to view your account in salesforce : 
https://[YOUR INSTANCE URL]/lightning/o/Account/list

