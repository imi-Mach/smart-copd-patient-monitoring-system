### login route
##### login_res.json
"/login"
This route is for all *patients* who have a google account, whether registered in our system or not.

The request is a string containing the google id token (should be in json, but we will leave it this way).

The response looks like: login_res.json
mData is random thing that should be null
mStat represents if the server handle the request correctly, should be "ok" in normal case
mSessionID is session id associated with the user and will be used in every request
mExists is true when the user is already in the database, false when the user is not, then we call the register route.

### register route
##### register_req.json
"/register"
This route is called when the login route responses with mExists: false.
Note the format of DOB and phoneNumber is varchar

##### register_res
a json with mStatus: ok if request was handled correctly

###  patient route
##### patient req
a json of session ID, in the form of sessionID.json
hppyoyxLiDZjLRn17XL2QFmlozY

##### patient_res.json
a json containing all patients information, in the form of patient_res.json

### insertData route
##### insertData_req.json
a json file in the form of insertData_req.json
q1 - q12 are the answer to the 12 questions, 0 is yes, 1 is no
all attributes except for "sessionID" are float

##### insertData_res
a json file contains mStatus: ok

### myData route
##### myData_req
a json of session ID, in the form of sessionID.json

##### myData_res.json
a json file in the form of myData_res.json
NOTE: We are adding "risk" to this


