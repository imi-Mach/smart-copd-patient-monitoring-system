<template>
  <div>
    <div>
      <img v-google-signin-button="clientId" class="google-signin-button" src="@/assets/btn_google_signin_dark_pressed_web@2x.png">
    </div>
    <div>
      <router-link to="/signup">Sign Up</router-link> 
    </div>
  </div>
</template>

<script>
import GoogleSignInButton from "vue-google-signin-button-directive";


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
      
      //to be modified and put in methods
      this.$http.post("/login", idToken).then(
        (response) => {
          // get body data
          this.someData = response.body;
          console.log(this.someData);
          this.$http.get("/check/" + idToken).then(
            (response) => {
              // get body data
              console.log('help');
              this.someData = response.body;
              console.log('Patient is within the database');
              this.$router.push('patients');
              console.log(response.mStatus);
              console.log(response.mData);
              console.log('help pt 2');
              console.log(response.mMessage);
            },
            (response) => {
              console.log(response.mStatus);
              console.log('some message');
              console.log(response.mMessage);
            }
          );
          
          console.log('router should have pushed');
        },
        (response) => {
          // error callback
          console.log(response.mStatus);
          console.log('message pt2');
          console.log(response.mMessage);
        }
      );


    },
    OnGoogleAuthFail(error) {
      console.log(error);
    },
  }
};
</script>
<style>
.google-signin-button{
  width:150px
}
</style>