<template>
<div>
  <img v-google-signin-button="clientId" class="google-signin-button" src="@/assets/btn_google_signin_dark_pressed_web@2x.png">
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
      console.log(idToken);
      
      //to be modified and put in methods
      this.$http.post("/login", idToken).then(
        (response) => {
          // get body data
          this.someData = response.body;
        },
        (response) => {
          // error callback
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