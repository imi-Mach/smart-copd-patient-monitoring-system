# Back-End Server


# Route Implementation

* "/login":

request : { googleIdToken :}
response : { mData: null
             mStatus: "ok"
             mSessionID: 
             mExists: true (if user is already part of database)}

* "/register" :

request : { sessionID : 
            firstName: 
            lastName: 
            DOB: 
            phoneNumber: }

response: {mStatus: ok
            rest you dont care about}}

* "/patient"
request: {sessionID: }

response: {mData: (all data of patient)}

* "/insertData"

request: {sessionID:
          date:
          heartRate:
          oxygenLevel:
          weight: 
          temperature:
          bloodPressure:
          date: }

response: {mStatus: ok 
           rest you do not care about}

* "/myData"

request: {sessionID:}

response: {mData: all daily data}