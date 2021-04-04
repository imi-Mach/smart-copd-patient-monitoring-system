package edu.lehigh.cse216.tbt220.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.security.SecureRandom;
import java.util.Base64.Encoder;
import java.util.Base64;


public class Database {
    
    // Airtable Patient Prepared Statements
    private PreparedStatement pCreateTable;
    private PreparedStatement pDropTable;
    private PreparedStatement pGetPatient;
    private PreparedStatement pInsertNewPatient;
    private PreparedStatement pDeletePatient;
    private PreparedStatement pCheckIfPatientExists;
    
    // Airtable HealthCare Prepared Statements
    private PreparedStatement hCreateTable;
    private PreparedStatement hDropTable;
    private PreparedStatement hGetProvider;
    private PreparedStatement hInsertProvider;
    private PreparedStatement hDeleteProvider;
    private PreparedStatement hCheckIfProviderExists;
    
    // Airtable LogStats Prepared Statements
    private PreparedStatement lSCreateTable;
    private PreparedStatement lSDropTable;
    private PreparedStatement lSGetLog;
    private PreparedStatement lSInsertNewLog;
    private PreparedStatement lSDeleteLog;
    private PreparedStatement lSCheckIfLogExists;
    
    // Airtable PatientOf Prepared Statements
    private PreparedStatement pOCreateTable;
    private PreparedStatement pODropTable;
    private PreparedStatement pOGetPatientOf;
    private PreparedStatement pOInsertNewPatientOf;
    private PreparedStatement pODeletePatientOf;
    private PreparedStatement pOCheckIfPatientOfExists;
   
    // Airtable DailyStats Prepared Statements
    private PreparedStatement dSCreateTable;
    private PreparedStatement dSDropTable;
    private PreparedStatement dSGetStat;
    private PreparedStatement dSInsertNewStat;
    private PreparedStatement dSDeleteStat;
    private PreparedStatement dSCheckIfStatExists;
   
    // Airtable Classes 
    public static class Patient {
        String pPatientID;
        String pFirstName;
        String pLastName;
        String pDOB;
        String pPhoneNumber;
        int pRiskLevel;
        
        public Patient(String patientID, String firstName, String lastName, String DOB, String phoneNumber, int riskLevel) {
            pPatientID = patientID;
            pFirstName = firstName;
            pLastName = lastName;
            pDOB = DOB;
            pPhoneNumber = phoneNumber;
            pRiskLevel = riskLevel;
        }
    }
    
    public static class HealthCareProvider {
        String hHealthCareID;
        String hFirstName;
        String hLastName;
        String hWorkLocation;
        String hPhoneNumber;
        String hEmail;
        
        public HealthCareProvider(String healthCareID, String firstName, String lastName, String workLocation, String phoneNumber, String email) {
            hHealthCareID = healthCareID;
            hFirstName = firstName;
            hLastName = lastName;
            hWorkLocation = workLocation;
            hPhoneNumber = phoneNumber;
            hEmail = email;
        }
    }
    
    public static class DailyStats {
        String dSdailyStatID;
        String dSdateRecorded;
        int dSHeartRate;
        int dSOxygenLevel;
        int dSWeight;
        int dSTemperature;
        String dSBloodPressure;
        int dSglucoseLevel;
        
        public DailyStats(String dailyStatID, String dateRecorded, int heartRate, int oxygenLevel, int weight, int temperature, String bloodPressure, int glucoseLevel) {
            dSdailyStatID = dailyStatID;
            dSdateRecorded = dateRecorded;
            dSHeartRate = heartRate;
            dSOxygenLevel = oxygenLevel;
            dSWeight = weight;
            dSTemperature = temperature;
            dSBloodPressure = bloodPressure;
            dSglucoseLevel = glucoseLevel;
        }
    }
    
    public static class LogStats {
        String lSPatientID;
        String dSDailyStatID;
        
        public LogStats(String patientID, String dailyStatID) {
            lSPatientID = patientID;
            dSDailyStatID = dailyStatID;
        }
    }
    
    public static class PatientOf {
        String pOHealthCareID;
        String pOPatientID;
        
        public PatientOf(String healthCareID, String patientID) {
            pOHealthCareID = healthCareID;
            pOPatientID = patientID;
        }
    }

    /**
     * The connection to the database.  When there is no connection, it should
     * be null.  Otherwise, there is a valid open connection
     */
    private Connection mConnection;

    //AUTHENTICATION
    /**
     * Create table for sessionStore
     */
    private PreparedStatement sCreateTable;

    /**
     * Drop table for sessionStore
     */
    private PreparedStatement sDropTable;

    /**
     * Login user
     */
    private PreparedStatement sLoginUser;

    /**
     * Logout user
     */
    private PreparedStatement sLogoutUser;

    /**
     * Check login
     */
    private PreparedStatement sCheckLogin;

    /**
     * Get new session ID
     */
    private PreparedStatement sGetNewSID;

    /**
     * Remove old login state?
     */
    private PreparedStatement sRemoveOldLogin;

    /**
     * The Database constructor is private: we only create Database objects 
     * through the getDatabase() method.
     */
    private Database() {
    }

//     public int numLikes = 0;
//     public int numDislikes = 0;
    /**
     * Get a fully-configured connection to the database
     * 
     * @param ip   The IP address of the database server
     * @param port The port on the database server to which connection requests
     *             should be sent
     * @param user The user ID to use when connecting
     * @param pass The password to use when connecting
     * 
     * @return A Database object, or null if we cannot connect properly
     */
    static Database getDatabase(String db_url) {
        // Create an un-configured Database object
        Database db = new Database();

    
        // Give the Database object a connection, fail if we cannot get one
        try {
            Class.forName("org.postgresql.Driver");
            URI dbUri = new URI(db_url);
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
            Connection conn = DriverManager.getConnection(dbUrl, username, password);
            if (conn == null) {
                System.err.println("Error: DriverManager.getConnection() returned a null object");
                return null;
            }
            db.mConnection = conn;
        } catch (SQLException e) {
            System.err.println("Error: DriverManager.getConnection() threw a SQLException");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Unable to find postgresql driver");
            return null;
        } catch (URISyntaxException s) {
            System.out.println("URI Syntax Error");
            return null;
        }

        // Attempt to create all of our prepared statements.  If any of these 
        // fail, the whole getDatabase() call should fail
        try {
            // NB: we can easily get ourselves in trouble here by typing the
            //     SQL incorrectly.  We really should have things like "tblData"
            //     as constants, and then build the strings for the statements
            //     from those constants.

            // Note: no "IF NOT EXISTS" or "IF EXISTS" checks on table 
            // creation/deletion, so multiple executions will cause an exception
            
            //AUTHENTICATION
            db.sCreateTable = db.mConnection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS sessionStore (" +
                    "user_id VARCHAR(50)," +
                    "session_id VARCHAR(30))"
            );
            //Session Store
            db.sDropTable = db.mConnection.prepareStatement("DROP TABLE sessionStore");
            db.sRemoveOldLogin = db.mConnection.prepareStatement("DELETE FROM sessionStore WHERE user_id = ?");
            db.sLoginUser = db.mConnection.prepareStatement("INSERT INTO sessionStore VALUES(?,?)");
            db.sLogoutUser = db.mConnection.prepareStatement("DELETE FROM sessionStore WHERE session_id = ?");
            
            //https://stackoverflow.com/questions/46061989/jdbc-check-if-entry-exists
            db.sCheckLogin = db.mConnection.prepareStatement("SELECT session_id FROM sessionStore WHERE user_id = ?");
            // db.sGetNewSID = db.mConnection.prepareStatement("SELECT session_id FROM sessionStore where user_id = ?");

            
            // Airtable Create Patient Table
            db.pCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS patients (" +
                "patientID varchar(8) not null primary key," +
                "firstName varchar(20) not null," +
                "lastName varchar(20) not null," +
                "DOB varchar(10) not null," +
                "phoneNumber varchar(10) not null," +
                "riskLevel int check(riskLevel > 0)"
            );
            
            //Patient table
            db.pDropTable = db.mConnection.prepareStatement("DROP TABLE patients");
            db.pGetPatient = db.mConnection.prepareStatement("SELECT * FROM patients where patientID = ?");
            db.pInsertNewPatient = db.mConnection.prepareStatement("INSERT INTO patients VALUES(?,?,?,?,?,?)");
            db.pDeletePatient = db.mConnection.prepareStatement("DELETE FROM patient WHERE patientID = ?");
            db.pCheckIfPatientExists = db.mConnection.prepareStatement("SELECT patientID FROM patients WHERE patientID = ?");
            
            // Airtable Create HealthCare Providers Table
            db.hCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS healthCareProvider (" +
                "healthCareID varchar(8) not null primary key," +
                "firstName varchar(20) not null," +
                "lastName varchar(20)"
            );
            
            //HealthCare Provider Table
            db.hDropTable = db.mConnection.prepareStatement("DROP TABLE healthCareProvider");
            db.hGetProvider = db.mConnection.prepareStatement("SELECT * FROM healthCareProvider where healthCareID = ?");
            db.hInsertProvider = db.mConnection.prepareStatement("INSERT INTO healthCareProvider VALUES(?,?,?)");
            db.hDeleteProvider = db.mConnection.prepareStatement("DELETE FROM healthCareProvider WHERE healthCareID = ?");
            db.hCheckIfProviderExists = db.mConnection.prepareStatement("SELECT healthCareID FROM healthCareProvider WHERE healthCareID = ?");
                
            // Airtable Create DailyStats Table
            db.dSCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS dailyStats (" +
                "dailyStatID varchar(8) not null primary key," +
                "dateRecorded varchar(8) not null," +
                "heartRate int check(heartRate > 0)," +
                "oxygenLevel int check(oxygenLevel > 70)," +
                "weight int check(weight > 0)," +
                "temperature int check(temperature > 90)," +
                "bloodPressure varchar(6)," +
                "glucoseLevel int check(glucoseLevel > 0)"
            );
            
            //DailyStats table
            db.dSDropTable = db.mConnection.prepareStatement("DROP TABLE dailyStats");
            db.dSGetStat = db.mConnection.prepareStatement("SELECT * FROM dailyStats where dailyStatID = ?");
            db.dSInsertNewStat = db.mConnection.prepareStatement("INSERT INTO dailyStats VALUES(?,?,?,?,?,?,?,?); INSERT INTO logStats(?,?)");
            db.dSDeleteStat = db.mConnection.prepareStatement("DELETE FROM logStats WHERE dailyStatID = ?; DELETE FROM dailyStats WHERE dailyStatID = ?");
            db.dSCheckIfStatExists = db.mConnection.prepareStatement("SELECT dailyStatID FROM dailyStats WHERE dailyStatID = ?");
            
            // Airtable Create LogStats Table
            db.lSCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS logStats (" +
                "patientID varchar(8) not null," +
                "dailyStatID varchar(8) not null," +
                "foreign key(patientID) references patients(patientID)," +
                "foreign key(dailyStatID) references dailyStats(dailyStatID)"
            );
            
            //LogStats table
            db.lSDropTable = db.mConnection.prepareStatement("DROP TABLE logStats");
            db.lSGetLog = db.mConnection.prepareStatement("SELECT * FROM logStats where patientID = ? AND dailyStatID = ?");
            db.lSInsertNewLog = db.mConnection.prepareStatement("INSERT INTO logStats VALUES(?,?)");
            db.lSDeleteLog = db.mConnection.prepareStatement("DELETE FROM logStats WHERE patientID = ? AND dailyStatID = ?; DELETE FROM dailtStats WHERE dailyStatID = ?");
            db.lSCheckIfLogExists = db.mConnection.prepareStatement("SELECT dailyStatID AND patientID FROM dailyStats WHERE dailyStatID = ? AND patientID = ?");
            
            // Airtable Create PatientOf Table
            db.pOCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS patientOf (" +
                "healthCareID varchar(8) not null," +
                "patientID varchar(8) not null," +
                "foreign key(healthCareID) references healthCareProvider(healthCareID)," +
                "foreign key(patientID) references patients(patientID)"
            );
            
            //PatientOf table
            db.pODropTable = db.mConnection.prepareStatement("DROP TABLE patientOf");
            db.pOGetPatientOf = db.mConnection.prepareStatement("SELECT * FROM patientOf where patientID = ? AND healthCareID = ?");
            db.pOInsertNewPatientOf = db.mConnection.prepareStatement("INSERT INTO patientOf VALUES(?,?)");
            db.pODeletePatientOf = db.mConnection.prepareStatement("DELETE FROM patientOf WHERE patientID = ? AND healthCareID = ?");
            db.pOCheckIfPatientOfExists = db.mConnection.prepareStatement("SELECT patientID AND healthCareID FROM patientOf WHERE healthCareID = ? AND patientID = ?");

        } catch (SQLException e) {
            System.err.println("Error creating prepared statement");
            e.printStackTrace();
            db.disconnect();
            return null;
        }
        return db;
    }

    /**
     * Check session ID associated with user ID to make sure it matches
     * If they do not match from front-end, then
     * should make user logout automatically
     * @param sessionID
     * @return true or false
     */
    boolean checkLogin(String sessionID){
        ResultSet rs = null;
        try {
            sCheckLogin.setString(1, sessionID);

            rs = sCheckLogin.executeQuery();
            
            if (rs.next()) {
                if (rs.getString("session_id") == sessionID){
                    return true;
                } else{
                    return false;
                }
            }
            
        } catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Logging in the user to the application
     * @param userID
     * @param sessionID
     * @return a sessionID
     */
    String loginUser(String userID, String sessionID){
        try {

            //Try removing old login if there is one
            try {
                sRemoveOldLogin.setString(1, userID);
                sRemoveOldLogin.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("No old login found");
                ex.printStackTrace();
            }
            
            sLoginUser.setString(1, userID);
            sLoginUser.setString(2, sessionID);
            sLoginUser.executeUpdate();

            sGetNewSID.setString(1, userID);
            ResultSet rs = sGetNewSID.executeQuery();

            String newSessionID = rs.getString("sessionID");
            return newSessionID;

        } catch(SQLException e){
            e.printStackTrace();
        }
        
        return "";
    }

    /**
     * Logging out the user by deleting the session key of the table
     * @param sessionID
     */
    void logoutUser(String sessionID){
        try {
        sLogoutUser.setString(1,sessionID);
        sLogoutUser.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * checking if the patient exists in the database
     * @param userID
     * @return true or false
     */
    boolean checkIfPatientExists(String userID) {
        ResultSet rs = null;
        try {
            pCheckIfPatientExists.setString(1, userID);
            rs = pCheckIfPatientExists.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * getting the information of the patient
     * @param userID
     * @return patient object
     */
    Patient getPatient(String userID) {
        Patient res = null;
        try {
            pGetPatient.setString(1, userID);
            ResultSet rs = pGetPatient.executeQuery();
            if (rs.next()) {
                res = new Patient(rs.getString("patientID"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("DOB"), rs.getString("phoneNumber"),rs.getInt("riskLevel"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Inserting a new patient into the database
     * @param userID
     * @param name
     * @param email
     * @param imageURL
     * @return
     */
    int insertNewPatient(String userID, String firstName, String lastName, String DOB, String phoneNumber) {
        int rowUpdate = 0;
        try {

            pInsertNewPatient.setString(1, userID);
            pInsertNewPatient.setString(2, firstName);
            pInsertNewPatient.setString(3, lastName);
            pInsertNewPatient.setString(4, DOB);
            pInsertNewPatient.setString(5, phoneNumber);
            pInsertNewPatient.setInt(6, 0);

            rowUpdate += pInsertNewPatient.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowUpdate;
    }



    //Generates a random string of length 20 to use as session id
    String generateSessionID() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String sessionID = encoder.encodeToString(bytes);
        return sessionID;
    }

    //CREATE AND DROP TABLES
    /**
     * Create tables. If it already exists, this will print an error
     */
    void createTables() {
        try {

            //creating all the tables
            dSCreateTable.execute();
            pOCreateTable.execute();
            lSCreateTable.execute();
            sCreateTable.execute();
            hCreateTable.execute();
            pCreateTable.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tables from the database. If it does not exist, this will print
     * an error.
     */
    void dropTables() {
        try {

            //dropping all the tabless
            pDropTable.execute();
            sDropTable.execute();
            hDropTable.execute();
            lSDropTable.execute();
            pODropTable.execute();
            dSDropTable.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //CLOSE CONNECTION
    /**
     * Close the current connection to the database, if one exists.
     * 
     * NB: The connection will always be null after this call, even if an 
     *     error occurred during the closing operation.
     * 
     * @return True if the connection was cleanly closed, false otherwise
     */
    boolean disconnect() {
        if (mConnection == null) {
            System.err.println("Unable to close connection: Connection was null");
            return false;
        }
        try {
            mConnection.close();
        } catch (SQLException e) {
            System.err.println("Error: Connection.close() threw a SQLException");
            e.printStackTrace();
            mConnection = null;
            return false;
        }
        mConnection = null;
        return true;
    }

}
