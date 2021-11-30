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


/* Check List

1. Add new column to session store to sort between patient and healthcare provider sessions
    - Do this last



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

        Database db = Database.getDatabase(db_url);
        if (db == null)
            return;

        // db.dropTables();
        // db.createTables();

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

        /*
         * Route for logging in patients by verifying the incoming Google ID token
         * and responding to front end if the user is regisitered or not.
         * 
         * Input Request:
         *      params:
         *          None
         *      request body:
         *          Google ID Token: a raw text string (e.g "ey239fds...")
         * 
         * Output Response:
         *      status:
         *          "ok" if valid google email, "error" if internal error
         *      sessionID:
         *          Session key is generated and returned if successful login, otherwise null
         *      exists:
         *          "true" if returning patient, "false" for non-registered patient, null if failed login
         *      message:
         *          if an error occured, then a message notification. Otherwise, null
         *      data:
         *          null
         * 
         * @returns JSON Response
         */
        Spark.post("/login", (request, response) -> {

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(htrans, jfac)
                    .setAudience(Collections
                            .singletonList("391364610933-efk7s0n53hv067p25v31dovu9d236vp7.apps.googleusercontent.com"))
                    .build();

            // (Receive idTokenString by HTTPS POST)
            System.out.println(request);
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

        /*
         * Route for logging in healthcare providers by verifying the incoming 
         * Google ID token and responding to front end if the user is regisitered or not.
         * 
         * Input Request:
         *      params:
         *          None
         *      request body:
         *          Google ID Token: a raw text string (e.g "ey239fds...")
         * 
         * Output Response:
         *      status:
         *          "ok" if valid google email, "error" if internal error
         *      sessionID:
         *          Session key is generated and returned if successful login, otherwise null
         *      exists:
         *          "true" if returning patient, "false" for non-registered HCP, null if failed login
         *      message:
         *          if an error occured, then a message notification. Otherwise, null
         *      data:
         *          null
         * 
         * @returns JSON Response
         */
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

        /*
         * Route for registering patients by checking first if the patient has a 
         * valid session key, then attempts to insert patient profile data into 
         * database system.
         * 
         * Input Request:
         *      params:
         *          None
         *      request body:
         *          sessionID:   random string for authorized access (patient)
         *          firstName:   string for patient's first name
         *          lastName:    string for patient's last name
         *          DOB:         date format for patient date of birth
         *          phoneNumber: string of integers
         * 
         * Output Response:
         *      status:
         *          "ok" if patient was registered, "error" if internal error
         *      sessionID:
         *          null
         *      exists:
         *          null
         *      message:
         *          if an error occured, then a message notification. Otherwise, null
         *      data:
         *          null
         * 
         * @returns JSON Response
         */
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

        /*
         * Route for registering healthcare providers by checking first if the HCP has a 
         * valid session key, then attempts to insert HCP profile data into 
         * database system.
         * 
         * Input Request:
         *      params:
         *          None
         *      request body:
         *          sessionID:     random string for authorized access (HCP)
         *          firstName:     string for HCP's first name
         *          lastName:      string for HCP's last name
         *          licenseNumber: string of integers
         *          phoneNumber:   string of integers
         * 
         * Output Response:
         *      status:
         *          "ok" if HCP was registered, "error" if internal error
         *      sessionID:
         *          null
         *      exists:
         *          null
         *      message:
         *          if an error occured, then a message notification. Otherwise, null
         *      data:
         *          null
         * 
         * @returns JSON Response
         */
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

        /*
         * Route for valid HCP to obtain all of their patients' profiles.
         * 
         * Input Request:
         *      params:
         *          session_id: random string for authorized access (HCP)
         *      request body:
         *          None
         * 
         * Output Response:
         *      status:
         *          "ok" if the patients' profile data was obtain, "error" if HCP could not be resolved
         *      sessionID:
         *          null
         *      exists:
         *          null
         *      message:
         *          if an error occured, then a message notification. Otherwise, null
         *      data:
         *          if HCP exists, then any data on their patients' profiles. Otherwise, null
         * 
         * @returns JSON Response
         */
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

        /*
         * Route to get patients profile information (mainly for when 
         * a patient logs in after being registerd).
         * 
         * Input Request:
         *      params:
         *          session_id: random string for authorized access (patient)
         *      request body:
         *          None
         * 
         * Output Response:
         *      status:
         *          "ok" if the patient's profile data was obtained, "error" if patient could not be resolved
         *      sessionID:
         *          null
         *      exists:
         *          null
         *      message:
         *          if an error occured, then a message notification. Otherwise, null
         *      data:
         *          if patient exists, then data on their profile. Otherwise, null
         * 
         * @returns JSON Response
         */
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

        Spark.get("/healthcareProfile/:session_id", (request, response) -> {

            String sessionID = request.params("session_id");

            String healthCareID = db.getUserID(sessionID);

            Object data = db.hGetProfile(healthCareID);
            if (data == null) {
                return gson.toJson(new StructuredResponse("error", healthCareID + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }

        });

        /*
         * Route to get HCP profile information (mainly for when 
         * a HCP logs in after being registerd).
         * 
         * Input Request:
         *      params:
         *          session_id: random string for authorized access (HCP)
         *      request body:
         *          None
         * 
         * Output Response:
         *      status:
         *          "ok" if the HCP's profile data was obtained, "error" if session could not be resolved
         *      sessionID:
         *          null
         *      exists:
         *          null
         *      message:
         *          if an error occured, then a message notification. Otherwise, null
         *      data:
         *          if session exists, then data on their profile. Otherwise, null
         * 
         * @returns JSON Response
         */
        Spark.get("/healthcare/:session_id", (request, response) -> {

            String sessionID = request.params("session_id");

            String patientID = db.getUserID(sessionID);

            Object data = db.getHealthcareProfile(patientID);
            if (data == null) {
                return gson.toJson(new StructuredResponse("error", patientID + " not found", null));
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
            int risklevel = Integer.parseInt((String) jsonObject.get("risklevel"));

            // retrieve user ID from session key (validates session key)
            String userID = db.getUserID(sessionID);

            // String userData = q1 + "," + q2 + "," + q3 + "," + q4 + "," + q5 + "," + q6 + "," + q7 + "," + q8 + "," + q9 + "," + q10 + "," + q11 + "," + q12 + "," + bt + "," + fev1 + "," + spo2 + "," + risklevel;

            // TODO: add IF statement to check if userID is null (meaning the session key might not be valid)
            
            int result = db.insertNewData(userID, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, bt, fev1, spo2, risklevel);
            

            if(result == 0) {

                return gson.toJson(new StructuredResponse("error", "insert failed", null));
            }

            // if insert was successful attempt update on risk level
            result = db.checkRiskLevel(userID);

            if(result == 0) {
                return gson.toJson(new StructuredResponse("ok", "no update occured", null));
            }
            else if (result > 0) {
                return gson.toJson(new StructuredResponse("ok", result + " updates occured", null));
            }

            return gson.toJson(new StructuredResponse("error", "risklevel failed to update", null));
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

        // TODO: should use session store to check HCP id
        Spark.get("/myPatientData/:patientID", (request,response) -> {

            // parse session key
            //String sessionID = request.params("session_id");

            // validate session key, then get user id pertaining to given session key
            //String userID = db.getUserID(sessionID);

            String patientID = request.params("patientID");

            // error check if session key is invalid --> userID == Null

            // retrieve array of daily stats for the given user
            Object data = db.getAllDailyStats(patientID);

            if (data == null) {

                return gson.toJson(new StructuredResponse("error", patientID + " not found", null));

            } else {

                return gson.toJson(new StructuredResponse("ok", null, data));
            }
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
