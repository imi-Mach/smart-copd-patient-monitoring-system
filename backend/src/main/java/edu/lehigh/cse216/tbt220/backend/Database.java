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
    private PreparedStatement pDeleteUser;
    private PreparedStatement pCheckIfPatientExists;
    
    // Airtable HealthCare Prepared Statements
    private PreparedStatement hCreateTable;
    private PreparedStatement hDropTable;
    private PreparedStatement pGetProvider;
    private PreparedStatement pInsertProvider;
    private PreparedStatement pDeleteProvider;
    private PreparedStatement pCheckIfProviderExists;
    
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
        int DSglucoseLevel;
        
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
            dSDailyStatID;
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
            
    

    //USER PROFILES
    private PreparedStatement uCreateTable;
    private PreparedStatement uDropTable;
    private PreparedStatement uGetUser;
    private PreparedStatement uInsertNewUser;
    private PreparedStatement uDeleteUser;
    private PreparedStatement uUpdateComment;
    private PreparedStatement uCheckIfUserExists;

    /**
     * The connection to the database.  When there is no connection, it should
     * be null.  Otherwise, there is a valid open connection
     */
    private Connection mConnection;

    //MESSAGES
    /**
     * A prepared statement for getting all data in the database
     */
    private PreparedStatement mSelectAllPosts;

    /**
     * A prepared statement for getting one row from the database
     */
    private PreparedStatement mSelectOnePost;

    /**
     * A prepared statement for deleting a row from the database
     */
    private PreparedStatement mDeleteOnePost;

    /**
     * A prepared statement for inserting into the database
     */
    private PreparedStatement mInsertOnePost;

    /**
     * A prepared statement for updating a single row in the database
     */
    private PreparedStatement mUpdateOnePost;

    /**
     * A prepared statement for creating the table in our database
     */
    private PreparedStatement mCreateTable;

    /**
     * A prepared statement for dropping the table in our database
     */
    private PreparedStatement mDropTable;

    /**
     * A prepared statement for updating a like in our database
     */
    private PreparedStatement mUpdateLikeIncrement;
    private PreparedStatement mUpdateLikeDecrement;

    /**
     * A prepared statement for updating a dislike in our database
     */
    private PreparedStatement mUpdateDislikeIncrement;
    private PreparedStatement mUpdateDislikeDecrement;


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

    //COMMENT SECTION
    private PreparedStatement cCreateTable;
    private PreparedStatement cDropTable;
    private PreparedStatement cInsertOne;
    private PreparedStatement cSelectOne;
    private PreparedStatement cDeleteOne;
    private PreparedStatement cSelectAll;
    private PreparedStatement cUpdateOne;

    //LIKE DISLIKE STORE FIGURE OUT LOGIC
    private PreparedStatement ldCreateTable;
    private PreparedStatement ldDropTable;
    private PreparedStatement ldInsert;
    private PreparedStatement ldDelete;
    private PreparedStatement ldGetStatus;

    /**
     * RowData is like a struct in C: we use it to hold data, and we allow 
     * direct access to its fields.  In the context of this Database, RowData 
     * represents the data we'd see in a row.
     * 
     * We make RowData a static class of Database because we don't really want
     * to encourage users to think of RowData as being anything other than an
     * abstract representation of a row of the database.  RowData and the 
     * Database are tightly coupled: if one changes, the other should too.
     */
    public static class RowData {
        /**
         * The ID of this row of the database
         */
        int mId;

        /**
         * The ID of user that made the post
         */
        String mUserId;

        /**
         * Username
         */
        String mUsername;

        /**
         * The subject stored in this row
         */
        String mSubject;

        /**
         * The message stored in this row
         */
        String mMessage;


        //The number of likes the message has
        int mLikes;


        //the number of dislikes the message has
        int mDislikes;

        //Image url
        String mImageURL;

        /**
         * Construct a RowData object by providing values for its fields
         */
        public RowData(int post_id, String user_id, String username, String subject, String message, int likes, int dislikes, String imageURL) {
            mId = post_id;
            mUserId = user_id;
            mUsername = username;
            mSubject = subject;
            mMessage = message;
            mLikes = likes;
            mDislikes = dislikes;
            mImageURL = imageURL;
        }
    }

    public static class Comment {
        int mCommentID;
        String mUserID;
        int mPostID;
        String mComment;
        String mImageURL;
        String mUsername;

        public Comment(int commentID, String userID, String name, int postID, String comment, String imageURL) {
            mCommentID = commentID;
            mUserID = userID;
            mUsername = name;
            mPostID = postID;
            mComment = comment;
            mImageURL = imageURL;
        }
    }

    public static class UserProfile {
        String uUserID;
        String uName;
        String uEmail;
        String uImageURL;
        String uComment;

        public UserProfile(String userID, String name, String email, String imageURL, String comment) {
            uUserID = userID;
            uName = name;
            uEmail = email;
            uImageURL = imageURL;
            uComment = comment;
        }
    }

    /**
     * The Database constructor is private: we only create Database objects 
     * through the getDatabase() method.
     */
    private Database() {
    }

    public int numLikes = 0;
    public int numDislikes = 0;
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
            
            db.dSDropTable = db.mConnection.prepareStatement("DROP TABLE dailyStats");
            db.dSGetStat = db.mConnection.prepareStatement("SELECT * FROM dailyStats where dailyStatID = ?");
            db.dSInsertNewStat = db.mConnection.prepareStatement("INSERT INTO dailyStats VALUES(?,?,?,?,?,?,?,?)");
            db.dSDeleteStat = db.mConnection.prepareStatement("DELETE FROM dailyStats WHERE dailyStatID = ?");
            db.dSCheckIfStatExists = db.mConnection.prepareStatement("SELECT dailyStatID FROM dailyStats WHERE dailyStatID = ?");
            
            // Airtable Create LogStats Table
            db.lSCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS logStats (" +
                "patientID varchar(8) not null," +
                "dailyStatID varchar(8) not null," +
                "foreign key(patientID) references patients(patientID)," +
                "foreign key(dailyStatID) references dailyStats(dailyStatID)"
            );
            
            db.lSDropTable = db.mConnection.prepareStatement("DROP TABLE logStats");
            db.lSGetLog = db.mConnection.prepareStatement("SELECT * FROM logStats where patientID = ? AND dailyStatID = ?");
            db.lSInsertNewLog = db.mConnection.prepareStatement("INSERT INTO logStats VALUES(?,?)");
            db.lSDeleteLog = db.mConnection.prepareStatement("DELETE FROM dailyStats WHERE patientID = ? AND dailyStatID = ?");
            db.lSCheckIfLogExists = db.mConnection.prepareStatement("SELECT dailyStatID AND patientID FROM dailyStats WHERE dailyStatID = ? AND patientID = ?");
            
            // Airtable Create PatientOf Table
            db.pOCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS patientOf (" +
                "healthCareID varchar(8) not null," +
                "patientID varchar(8) not null," +
                "foreign key(healthCareID) references healthCareProvider(healthCareID)," +
                "foreign key(patientID) references patients(patientID)"
            );
            
            db.pODropTable = db.mConnection.prepareStatement("DROP TABLE patientOf");
            db.pOGetPatientOf = db.mConnection.prepareStatement("SELECT * FROM patientOf where patientID = ? AND healthCareID = ?");
            db.pOInsertNewPatientOf = db.mConnection.prepareStatement("INSERT INTO patientOf VALUES(?,?)");
            db.pODeletePatientOf = db.mConnection.prepareStatement("DELETE FROM patientOf WHERE patientID = ? AND healthCareID = ?");
            db.pOCheckIfPatientOfExists = db.mConnection.prepareStatement("SELECT patientID AND healthCareID FROM patientOf WHERE healthCareID = ? AND patientID = ?");
            
            //USER PROFILES
            db.uCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS users (" +
                "user_id VARCHAR(50) PRIMARY KEY," +
                "name VARCHAR(100)," +
                "email VARCHAR(100)," +
                "imageURL VARCHAR(500)," +
                "comment VARCHAR(500))"
            );

            db.uDropTable = db.mConnection.prepareStatement("DROP TABLE users");
            //'Your comment' = default user comment on profile page
            db.uGetUser = db.mConnection.prepareStatement("SELECT * FROM users WHERE user_id = ?");
            db.uInsertNewUser = db.mConnection.prepareStatement("INSERT INTO users VALUES(?,?,?,?,'Your comment')");
            db.uDeleteUser = db.mConnection.prepareStatement("DELETE FROM users WHERE user_id = ?");
            db.uUpdateComment = db.mConnection.prepareStatement("UPDATE users SET comment = ? WHERE user_id = ?");
            db.uCheckIfUserExists = db.mConnection.prepareStatement("SELECT user_id FROM users WHERE user_id = ?");

            //POSTS
            db.mCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS posts (" +
                "post_id SERIAL PRIMARY KEY," + 
                "user_id VARCHAR(50)," +
                "subject VARCHAR(50) NOT NULL," +
                "message VARCHAR(500) NOT NULL," +
                "likes INT," +
                "dislikes INT," +
                "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE)");
            db.mDropTable = db.mConnection.prepareStatement("DROP TABLE posts");
            
            // Standard CRUD operations
            db.mDeleteOnePost = db.mConnection.prepareStatement("DELETE FROM posts WHERE post_id = ?");
            db.mInsertOnePost = db.mConnection.prepareStatement("INSERT INTO posts VALUES (default,?, ?, ?, 0, 0)");
            db.mSelectAllPosts = db.mConnection.prepareStatement("SELECT * FROM posts,users WHERE posts.user_id = users.user_id ORDER BY post_id ASC");
            db.mSelectOnePost = db.mConnection.prepareStatement("SELECT * from posts,users WHERE posts.user_id = users.user_id AND post_id = ?");
            db.mUpdateOnePost = db.mConnection.prepareStatement("UPDATE posts "+ 
            "SET subject = ?, message = ? WHERE post_id = ?");
        
            //For incrementing likes/dislikes
            db.mUpdateLikeIncrement = db.mConnection.prepareStatement("UPDATE posts SET likes= likes + 1 WHERE post_id = ?");
            db.mUpdateDislikeIncrement = db.mConnection.prepareStatement("UPDATE posts SET dislikes = dislikes + 1 WHERE post_id = ?");

            //For decrementing likes/dislikes
            db.mUpdateLikeDecrement = db.mConnection.prepareStatement("UPDATE posts SET likes= likes - 1 WHERE post_id = ?");
            db.mUpdateDislikeDecrement = db.mConnection.prepareStatement("UPDATE posts SET dislikes = dislikes - 1 WHERE post_id = ?");

            //COMMENTS
            db.cCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS comments (" +
                "comment_id SERIAL PRIMARY KEY," +
                "user_id VARCHAR(50)," +
                "post_id INTEGER," +
                "comment VARCHAR(500)," +
                "FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE," +
                "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE)"
            );

            db.cDropTable = db.mConnection.prepareStatement("DROP TABLE comments");
            db.cInsertOne = db.mConnection.prepareStatement("INSERT INTO comments VALUES(default,?,?,?)");
            db.cSelectOne = db.mConnection.prepareStatement("SELECT * FROM comments,users WHERE comments.user_id = users.user_id AND post_id = ? AND comment_id = ?");
            db.cDeleteOne = db.mConnection.prepareStatement("DELETE FROM comments WHERE post_id = ? AND comment_id = ?");
            db.cSelectAll = db.mConnection.prepareStatement("SELECT * FROM comments,users WHERE comments.user_id = users.user_id AND post_id = ? ORDER BY comment_id ASC");
            db.cUpdateOne = db.mConnection.prepareStatement("UPDATE comments SET comment = ? WHERE comment_id = ? and post_id = ?");

            //LIKE DISLIKE STORE
            db.ldCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS likeDislikeStore (" +
                "user_id VARCHAR(50)," +
                "post_id INTEGER," +
                "status VARCHAR(30)," +
                "FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE," +
                "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE)"
            );
            db.ldDropTable = db.mConnection.prepareStatement("DROP TABLE likeDislikeStore");
            db.ldInsert = db.mConnection.prepareStatement("INSERT INTO likeDislikeStore VALUES(?,?,?)");
            db.ldDelete = db.mConnection.prepareStatement("DELETE FROM likeDislikeStore WHERE user_id = ? and post_id = ?");
            db.ldGetStatus = db.mConnection.prepareStatement("SELECT status FROM likeDislikeStore WHERE user_id = ? and post_id = ?");


        } catch (SQLException e) {
            System.err.println("Error creating prepared statement");
            e.printStackTrace();
            db.disconnect();
            return null;
        }
        return db;
    }

    //AUTHENTICATION
    
    //Check session ID associated with user ID to make sure it matches
    //If they do not match from front-end, then 
    //should make user logout automatically
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

    //Login user
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

            // sGetNewSID.setString(1, userID);
            // ResultSet rs = sGetNewSID.executeQuery();

            // String newSessionID = rs.getString("sessionID");
            return sessionID;
        } catch(SQLException e){
            e.printStackTrace();
        }
        
        return "";
    }

    void logoutUser(String sessionID){
        try {
        sLogoutUser.setString(1,sessionID);
        sLogoutUser.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    boolean checkIfUserExists(String userID) {
        ResultSet rs = null;
        try {
            uCheckIfUserExists.setString(1, userID);
            rs = uCheckIfUserExists.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    //USERS
    UserProfile getUser(String userID) {
        UserProfile res = null;
        try {
            uGetUser.setString(1, userID);
            ResultSet rs = uGetUser.executeQuery();
            if (rs.next()) {
                res = new UserProfile(rs.getString("user_id"), rs.getString("name"), rs.getString("email"), rs.getString("imageURL"), rs.getString("comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    int insertNewUser(String userID, String name, String email, String imageURL) {
        int rowUpdate = 0;
        try {
            uInsertNewUser.setString(1, userID);
            uInsertNewUser.setString(2, name);
            uInsertNewUser.setString(3, email);
            uInsertNewUser.setString(4, imageURL);

            rowUpdate += uInsertNewUser.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowUpdate;
    }

    int updateUserComment(String userID, String comment) {
        int rowUpdate = -1;
        try {
            uUpdateComment.setString(1, comment);
            uUpdateComment.setString(2, userID);

            rowUpdate += uUpdateComment.executeUpdate();
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

    //POSTS
    /**
     * Insert a row into the database
     * 
     * @param subject The subject for this new row
     * @param message The message body for this new row
     * 
     * @return The number of rows that were inserted
     */
    int insertPost(String userID, String subject, String message) {
        int count = 0;
        try {
            mInsertOnePost.setString(1, userID);
            mInsertOnePost.setString(2, subject);
            mInsertOnePost.setString(3, message);
            count += mInsertOnePost.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Query the database for a list of all subjects and their IDs
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<RowData> selectAllPosts() {
        ArrayList<RowData> res = new ArrayList<RowData>();
        try {
            ResultSet rs = mSelectAllPosts.executeQuery();
            while (rs.next()) {
                res.add(new RowData(rs.getInt("post_id"), rs.getString("user_id"), rs.getString("name"), rs.getString("subject"),rs.getString("message"), rs.getInt("likes"), rs.getInt("dislikes"), rs.getString("imageURL")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all data for a specific row, by ID
     * 
     * @param id The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    RowData selectPost(int id) {
        RowData res = null;
        try {
            mSelectOnePost.setInt(1, id);
            ResultSet rs = mSelectOnePost.executeQuery();
            if (rs.next()) {
                res = new RowData(rs.getInt("post_id"), rs.getString("user_id"), rs.getString("name"), rs.getString("subject"), rs.getString("message"), rs.getInt("likes"), rs.getInt("dislikes"), rs.getString("imageURL"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a row by ID
     * 
     * @param id The id of the row to delete
     * 
     * @return The number of rows that were deleted.  -1 indicates an error.
     */
    int deletePost(int id) {
        int res = -1;
        try {
            mDeleteOnePost.setInt(1, id);
            res = mDeleteOnePost.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the message for a row in the database
     * 
     * @param id The id of the row to update
     * @param message The new message contents
     * 
     * @return The number of rows that were updated.  -1 indicates an error.
     */
    int updatePost(int postID, String subject, String message) {
        int res = -1;
        try {
            mUpdateOnePost.setString(1, subject);
            mUpdateOnePost.setString(2, message);
            // mUpdateOnePost.setInt(3, likes);
            // mUpdateOnePost.setInt(4, dislikes);
            mUpdateOnePost.setInt(3, postID);
          
            res = mUpdateOnePost.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

     /**
     * Update the number of likes for a given message specified by id in the database
     * 
     * @param id The id of the message to update
     * 
     * @return The number of likes that were counted.
     */
    int updateLikes(int postID, String action) {
        try {
            if (action.equals("up")) {
                mUpdateLikeIncrement.setInt(1, postID);
                numLikes = mUpdateLikeIncrement.executeUpdate();
            } else if (action.equals("down")) {
                mUpdateLikeDecrement.setInt(1, postID);
                numLikes = mUpdateLikeDecrement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numLikes;
    }

     /**
     * Update the number of dislikes for a given message specified by id in the database
     * 
     * @param id The id of the message to update
     * 
     * @return The number of dislikes that were counted.
     */
    int updateDislikes(int postID, String action) {
        try {
            if (action.equals("up")) {
                mUpdateDislikeIncrement.setInt(1, postID);
                numDislikes = mUpdateDislikeIncrement.executeUpdate();
            } else if (action.equals("down")) {
                mUpdateDislikeDecrement.setInt(1, postID);
                numDislikes = mUpdateDislikeDecrement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numDislikes;
    }

    //COMMENTS
    int insertComment(String userID, int postID, String comment) {
        int count = -1;
        try {
            cInsertOne.setString(1, userID);
            cInsertOne.setInt(2, postID);
            cInsertOne.setString(3, comment);
            count += cInsertOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    ArrayList<Comment> selectAllComments(int postID) {
        ArrayList<Comment> res = new ArrayList<>();
        try {
            cSelectAll.setInt(1, postID);

            ResultSet rs = cSelectAll.executeQuery();
            while (rs.next()) {
                res.add(new Comment(rs.getInt("comment_id"), rs.getString("user_id"), rs.getString("name"), rs.getInt("post_id"), rs.getString("comment"), rs.getString("imageURL")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    Comment selectComment(int postID, int commentID) {
        Comment res = null;
        try {
            cSelectOne.setInt(1, postID);
            cSelectOne.setInt(2, commentID);

            ResultSet rs = cSelectOne.executeQuery();
            if (rs.next()) {
                res = new Comment(rs.getInt("comment_id"), rs.getString("user_id"), rs.getString("name"), rs.getInt("post_id"), rs.getString("comment"), rs.getString("imageURL"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int deleteComment(int postID, int commentID) {
        int res = -1;
        try {
            cDeleteOne.setInt(1, postID);
            cDeleteOne.setInt(2, commentID);

            res = cDeleteOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int updateComment(int postID, int commentID, String comment) {
        int res = -1;
        try {
            cUpdateOne.setString(1, comment);
            cUpdateOne.setInt(2, commentID);
            cUpdateOne.setInt(3, postID);
          
            res = cUpdateOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    //LIKE AND DISLIKE STORE

    //Check whether user liked/disliked or none for a post
    String checkLikeDislikeStatus(String userID, int postID) {
        String status = "none";
        ResultSet rs = null;
        try {
            ldGetStatus.setString(1, userID);
            ldGetStatus.setInt(2, postID);
            rs = ldGetStatus.executeQuery();

            //If status exists then user has liked or disliked the post already
            if (rs.next()) {
                status = rs.getString("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
    
    //Insert into like dislike store if user has not liked/disliked a post yet
    int insertLikeDislikeStore(String userID, int postID, String status) {
        int res = -1;
        try {
            ldInsert.setString(1, userID);
            ldInsert.setInt(2, postID);
            ldInsert.setString(3, status);
          
            res = ldInsert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int deleteLikeDislikeStore(String userID, int postID) {
        int res = -1;
        try {
            ldDelete.setString(1, userID);
            ldDelete.setInt(2, postID);
          
            res = ldDelete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }




    //CREATE AND DROP TABLES
    /**
     * Create tables. If it already exists, this will print an error
     */
    void createTables() {
        try {
            //Need to create tables in this order to satisfy key constraints
            //Create users table
            uCreateTable.execute();
            //Create posts tables
            mCreateTable.execute();
            //Create sessionStore table
            sCreateTable.execute();
            //Create comments table
            cCreateTable.execute();
            //Create likeDislikeStore table
            ldCreateTable.execute();
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
            //Need to drop tables in this order to not break key contraints
            //Drop likeDislikeStore table
            ldDropTable.execute();
            //Drop comments table
            cDropTable.execute();
            //Drop sessionStore table
            sDropTable.execute();
            //Drop posts table
            mDropTable.execute();
            //Drop users table
            uDropTable.execute();
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
