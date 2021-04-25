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

/**
 * For now, our app creates an HTTP server that can only get and add data.
 */
public class App {
    public static void main(String[] args) {

        /*Airtable airtable = new Airtable().configure();
        Base base = airtable.base("keyTtJ1q0C5hWsmeh");
        */

        // gson provides us with a way to turn JSON into objects, and objects
        // into JSON.
        //
        // NB: it must be final, so that it can be accessed from our lambdas
        //
        // NB: Gson is thread-safe. See
        // https://stackoverflow.com/questions/10380835/is-it-ok-to-use-gson-instance-as-a-static-field-in-a-model-bean-reuse
        final Gson gson = new Gson();
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
            if (idToken != null) {
                Payload payload = idToken.getPayload();

                // Get userID
                String userId = payload.getSubject();

                // Generate new session ID
                String newSessionID = db.generateSessionID();

                String sessionID = db.loginUser(userId, newSessionID);

                return gson.toJson(new StructuredResponse("ok", null, newSessionID));

            } else {
                return gson.toJson(new StructuredResponse("error", "error during login", null));
            }

        });

        Spark.get("/check/:userID", (request, response) -> {
            String userId = request.params("userID");
            boolean check = db.checkIfPatientExists(userId);
            if(check){
                return gson.toJson(new StructuredResponse("ok", null, true));
            }
            else{
                return gson.toJson(new StructuredResponse("ok", null, false));
            }
        });

        Spark.post("/sign_in/:userID:/firstName:/:lastName:/:DOB/:phoneNumber", (request, response) -> {
            String userId = request.params("userID");
            String firstName = request.params("firstName");
            String lastName = request.params("lastName");
            String DOB = request.params("DOB");
            String phoneNumber = request.params("phoneNumber");
            db.insertNewPatient(userId, firstName ,lastName, DOB,phoneNumber);
            return gson.toJson(new StructuredResponse("ok", null, null));
        });

        Spark.get("/patient/:user_id", (request, response) -> {
            String user_id = request.params("userID");
            return gson.toJson(new StructuredResponse("ok", null, db.getPatient(user_id)));
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
