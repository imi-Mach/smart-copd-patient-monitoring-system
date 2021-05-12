<template>
  <div>
    <div>
      <img v-google-signin-button="clientId" class="google-signin-button" src="@/assets/btn_google_signin_dark_pressed_web@2x.png">
    </div>
    <div>
      <router-link to="/register">Sign Up</router-link> 
    </div>
  </div>
</template>

<script>
import GoogleSignInButton from "vue-google-signin-button-directive";
var test = '';

export default {
  directives: {
    GoogleSignInButton,
  },
  data: () => ({
    clientId:
      "391364610933-efk7s0n53hv067p25v31dovu9d236vp7.apps.googleusercontent.com",
  }),
  methods: {
    OnGoogleAuthSuccess(idToken) {
      // Receive the idToken and make your magic with the backend
      console.log('This is a test:')
      console.log(idToken);
      test = idToken;
      console.log('lets see if this works');
      console.log(patientID);
      
      //to be modified and put in methods
      this.$http.post("/login", idToken).then(
        (response) => {
          // get body data
          this.someData = response.body;
          console.log(this.someData);
        },
        (response) => {
          // error callback
          console.log(response.mStatus);
          console.log('message pt2');
          console.log(response.mMessage);
        }
      );

    console.log('This is a test:')
    this.$http.get("/check/" + idToken).then(
      (response) => {
        // get body data
        this.someData = response.body;
        console.log(this.someData);
        if (response.mData) {
          console.log('goig to patients');
          this.$router.push('patients');
        } else {
          console.log('going to signup');
          this.$router.push('register');
        }
        console.log('didnt work');
      },
      (response) => {
        console.log(response.mStatus);
        console.log('some message');
        console.log(response.mMessage);
      }
    );

    },
    OnGoogleAuthFail(error) {
      console.log(error);
    },
  }
};
export const patientID = test;
</script>
<style>
.google-signin-button{
  width:150px
}
</style>