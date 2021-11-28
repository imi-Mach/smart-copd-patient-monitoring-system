package edu.lehigh.cse216.tbt220.backend;

import java.util.Collections;
// Import the Spark package, so that we can make use of the "get" function to 
// create an HTTP GET route
import spark.Spark;

// Import Google's JSON library
import com.google.gson.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// For ML
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/* Check List

- Add new column to session store to sort between patient and healthcare provider sessions
    - Do this last
- Add int when parsing insertData request for the ML calculated risk level
- Calculate the new risklevel of the patient (patient condition) when inserting new data
    - Get max of most recent 7 measurements for risk level (daily stat of particular patient)
    - Updated the database JDBC call for patient table
- "IF I am a patient, I want to know who my healthcare provider is."
    - Input: Session ID
    - Output: Healthcare provider data (firstname, lastname, phone number, email)
    - Add route for obtaining the healthcare provider data from a patient user id
    - Updated Database.java to include JDBC for obtaining healthcare provider info
        - Use patient of to obtain the healthcare provider of patient
- patientCsv route needs to be changed
- delete "/patientInfo/:userID" route
- change "/myData/" to have input patient ID and return all patient daily stats from input patient id
- add route "/healthcareprovider/" it should get the profile information (hcp table data) for the "Profile" button



*/



/**
 * For now, our app creates an HTTP server that can only get and add data.
 */
public class App {
    public static void main(String[] args) {

        // gson provides us with a way to turn JSON into objects, and objects
        // into JSON.
        //
        // NB: it must be final, so that it can be accessed from our lambdas
        //
        // NB: Gson is thread-safe. See
        // https://stackoverflow.com/questions/10380835/is-it-ok-to-use-gson-instance-as-a-static-field-in-a-model-bean-reuse
        final Gson gson = new Gson();

        // For Google OAuth
        final HttpTransport htrans = new NetHttpTransport();
        final JsonFactory jfac = new GsonFactory();

        // dataStore holds all of the data that has been provided via HTTP
        // requests
        //
        // NB: every time we shut down the server, we will lose all data, and
        // every time we start the server, we'll have an empty dataStore,
        // with IDs starting over from 0.
        // Get the port on which to listen for requests
        Spark.port(getIntFromEnv("PORT", 5432));
        String db_url = System.getenv("DATABASE_URL");

        // Get a fully-configured connection to the database, or exit
        // immediatelymvn

        // TODO
        Database db = Database.getDatabase(db_url);
        if (db == null)
            return;
        db.dropTables();
        db.createTables();

        // Set up the location for serving static files. If the STATIC_LOCATION
        // environment variable is set, we will serve from it. Otherwise, serve
        // from "/web"
        String static_location_override = System.getenv("STATIC_LOCATION");
        // System.out.println(static_location_override);
        if (static_location_override == null) {
            Spark.staticFileLocation("/web/dist");
        } else {
            Spark.staticFiles.externalLocation(static_location_override);
        }

        String cors_enabled = System.getenv("CORS_ENABLED");
        if (cors_enabled.equals("True")) {
            final String acceptCrossOriginRequestsFrom = "*";
            final String acceptedCrossOriginRoutes = "GET,PUT,POST,DELETE,OPTIONS";
            final String supportedRequestHeaders = "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin";
            enableCORS(acceptCrossOriginRequestsFrom, acceptedCrossOriginRoutes, supportedRequestHeaders);
        }

        // Set up a route for serving the main page
        Spark.get("/", (req, res) -> {
            res.redirect("/index.html");
            return "";
        });

        // Set up a route for login
        Spark.post("/login", (request, response) -> {

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(htrans, jfac)
                    .setAudience(Collections
                            .singletonList("391364610933-efk7s0n53hv067p25v31dovu9d236vp7.apps.googleusercontent.com"))
                    .build();

            // (Receive idTokenString by HTTPS POST)
            String idTokenString = gson.fromJson(request.body(), String.class);
            System.out.println(idTokenString);
            System.out.println(request.body());

            GoogleIdToken idToken = verifier.verify(idTokenString);
            response.type("application/json");
            if (idToken != null) {
                Payload payload = idToken.getPayload();

                // Get userID
                String userId = payload.getEmail();

                // Generate new session ID
                String newSessionID = db.generateSessionID();

                String sessionID = db.loginUser(userId, newSessionID);

                boolean check = db.checkIfPatientExists(userId);

                if(check){
                    return gson.toJson(new StructuredResponse("ok", sessionID, true, null));
                }
                return gson.toJson(new StructuredResponse("ok", sessionID, false, null));

            } else {
                return gson.toJson(new StructuredResponse("error", "error during login", null));
            }

        });

        // Set up a route for login
        Spark.post("/healthcarelogin", (request, response) -> {

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(htrans, jfac)
                    .setAudience(Collections
                            .singletonList("391364610933-efk7s0n53hv067p25v31dovu9d236vp7.apps.googleusercontent.com"))
                    .build();

            // (Receive idTokenString by HTTPS POST)
            String idTokenString = gson.fromJson(request.body(), String.class);
            System.out.println(idTokenString);
            System.out.println(request.body());

            GoogleIdToken idToken = verifier.verify(idTokenString);
            response.type("application/json");
            if (idToken != null) {
                Payload payload = idToken.getPayload();

                // Get userID
                String userId = payload.getEmail();

                // Generate new session ID
                String newSessionID = db.generateSessionID();

                String sessionID = db.loginUser(userId, newSessionID);

                boolean check = db.checkIfProviderExists(userId);

                if(check){
                    return gson.toJson(new StructuredResponse("ok", sessionID, true, null));
                }
                return gson.toJson(new StructuredResponse("ok", sessionID, false, null));

            } else {
                return gson.toJson(new StructuredResponse("error", "error during login", null));
            }

        });

        Spark.post("/register", (request, response) -> {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(request.body());
            
            String sessionID = (String) jsonObject.get("sessionID");
            String firstName = (String) jsonObject.get("firstName");
            String lastName = (String) jsonObject.get("lastName");
            String DOB = (String) jsonObject.get("DOB");
            String phoneNumber = (String) jsonObject.get("phoneNumber");

            //get user id
            String userID = db.getUserID(sessionID);

            int result = db.insertNewPatient(userID, firstName ,lastName, DOB, phoneNumber);

            if(result == 0) { 
                return gson.toJson(new StructuredResponse("error", "insert failed", null));
            }
            return gson.toJson(new StructuredResponse("ok", null, null));
        });

        Spark.post("/healthcareregister", (request, response) -> {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(request.body());
            
            String sessionID = (String) jsonObject.get("sessionID");
            String firstName = (String) jsonObject.get("firstName");
            String lastName = (String) jsonObject.get("lastName");
            String phoneNumber = (String) jsonObject.get("phoneNumber");
            String licenseNumber = (String) jsonObject.get("licenseNumber");

            //get user id
            String userID = db.getUserID(sessionID);

            int result = db.inserNewHealthCareProvider(userID, firstName ,lastName, phoneNumber, licenseNumber);
 
            if(result == 0) {
                return gson.toJson(new StructuredResponse("error", "insert failed", null));
            }
            return gson.toJson(new StructuredResponse("ok", null, null));
        });

        // Input: session ID (healthcare provider)
        // Ouput: List of patient ids under given healthcare provider
        Spark.get("/getAllPatients/:session_id", (request, response) ->{

            String sessionID = request.params("session_id");

            String userID = db.getUserID(sessionID);

            Object data = db.getPatients(userID);

            if (data == null) {

                return gson.toJson(new StructuredResponse("error", userID + " not found", null));

            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }

        });

        // as a patient, check patient info
        Spark.get("/patient/:session_id", (request, response) -> {

            String sessionID = request.params("session_id");

            String userID = db.getUserID(sessionID);

            Object data = db.getPatient(userID);
            if (data == null) {
                return gson.toJson(new StructuredResponse("error", userID + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }

        });

        // get one patient from input healthcare provider id
        // schdule for delete
        Spark.get("/patientInfo/:userID", (request, response) -> {

            String userID = request.params("userID");

            Object data = db.getPatient(userID);
            if (data == null) {
                return gson.toJson(new StructuredResponse("error", userID + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });

        // as a hcp, insert patient
        Spark.post("/insertPatientOf", (request, response) -> {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(request.body());
            
            String patientID = (String) jsonObject.get("patientEmail");
            String sessionID = (String) jsonObject.get("sessionID");
            String userID = db.getUserID(sessionID);

            int result = db.insertPatientOf(userID, patientID);

            if (result == 0) {
                return gson.toJson(new StructuredResponse("error", userID + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, null));
            }
        });



        Spark.get("/patientCSV/:userID", (request, response) -> {

            String userID = request.params("userID");

            db.getPatientCSV(userID);

            return gson.toJson(new StructuredResponse("ok", null, null));

        });

        Spark.post("/insertData", (request, response) -> {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(request.body());
            
            String sessionID = (String) jsonObject.get("sessionID");

            int q1 =  Integer.parseInt((String) jsonObject.get("q1"));
            int q2 =  Integer.parseInt((String) jsonObject.get("q2"));
            int q3 =  Integer.parseInt((String) jsonObject.get("q3"));
            int q4 =  Integer.parseInt((String) jsonObject.get("q4"));
            int q5 =  Integer.parseInt((String) jsonObject.get("q5"));
            int q6 =  Integer.parseInt((String) jsonObject.get("q6"));
            int q7 =  Integer.parseInt((String) jsonObject.get("q7"));
            int q8 =  Integer.parseInt((String) jsonObject.get("q8"));
            int q9 =  Integer.parseInt((String) jsonObject.get("q9"));
            int q10 =  Integer.parseInt((String) jsonObject.get("q10"));
            int q11 =  Integer.parseInt((String) jsonObject.get("q11"));
            int q12 =  Integer.parseInt((String) jsonObject.get("q12"));
            float bt =  Float.parseFloat((String) jsonObject.get("bt"));
            float fev1 =  Float.parseFloat((String) jsonObject.get("fev1"));
            float spo2 =  Float.parseFloat((String) jsonObject.get("spo2"));

            String userID = db.getUserID(sessionID);

            String userData = q1 + "," + q2 + "," + q3 + "," + q4 + "," + q5 + "," + q6 + "," + q7 + "," + q8 + "," + q9 + "," + q10 + "," + q11 + "," + q12 + "," + bt + "," + fev1 + "," + spo2;

            String risk = "Yoo Hello";
            risk = db.runScript("python3 Classify.py " + userData);

            int riskLevel = Integer.parseInt(risk);  
            int result = db.insertNewData(userID, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, bt, fev1, spo2, riskLevel);
            
            System.out.println("Risk output: "+risk);

            if(result == 0) {

                return gson.toJson(new StructuredResponse("error", "insert failed", null));
            }

            return gson.toJson(new StructuredResponse("ok", null, null));
        });

        Spark.get("/myData/:session_id", (request,response) -> {

            // parse session key
            String sessionID = request.params("session_id");

            // validate session key, then get user id pertaining to given session key
            String userID = db.getUserID(sessionID);

            // error check if session key is invalid --> userID == Null

            // retrieve array of daily stats for the given user
            Object data = db.getAllDailyStats(userID);

            if (data == null) {

                return gson.toJson(new StructuredResponse("error", userID + " not found", null));

            } else {

                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });

        Spark.put("/updateRiskLevel", (request, response) ->{

            JSONParser jsonParser = new JSONParser();

            JSONObject jsonObject = (JSONObject) jsonParser.parse(request.body());
            
            String sessionID = (String) jsonObject.get("sessionID");
            int riskLevel =  Integer.parseInt((String) jsonObject.get("riskLevel"));

            String userID = db.getUserID(sessionID);

            db.updateRiskLevel(userID, riskLevel);

            return gson.toJson(new StructuredResponse("ok", null, null));

        });

        Spark.delete("/deletePatient/:userID",(request, response) ->{

            String userID = request.params("userID");

            db.deletePatientOf(userID);

            return gson.toJson(new StructuredResponse("ok", null, null));
        });


    }

    

    /**
     * Get an integer environment varible if it exists, and otherwise return the
     * default value.
     * 
     * @envar The name of the environment variable to get.
     * @defaultVal The integer value to use as the default if envar isn't found
     * 
     * @returns The best answer we could come up with for a value for envar
     */
    static int getIntFromEnv(String envar, int defaultVal) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get(envar) != null) {
            return Integer.parseInt(processBuilder.environment().get(envar));
        }
        return defaultVal;
    }

    /**
     * Set up CORS headers for the OPTIONS verb, and for every response that the
     * server sends. This only needs to be called once.
     * 
     * @param origin  The server that is allowed to send requests to this server
     * @param methods The allowed HTTP verbs from the above origin
     * @param headers The headers that can be sent with a request from the above
     *                origin
     */
    private static void enableCORS(String origin, String methods, String headers) {
        // Create an OPTIONS route that reports the allowed CORS headers and methods
        Spark.options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        // 'before' is a decorator, which will run before any
        // get/post/put/delete. In our case, it will put three extra CORS
        // headers into the response
        Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
        });
    }
}
