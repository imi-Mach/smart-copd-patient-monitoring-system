<template>
  <button v-google-signin-button="clientId" class="google-signin-button"> Continue with Google</button>
</template>
 
<script>
import GoogleSignInButton from 'vue-google-signin-button-directive'

export default {
  name: "Login",
  directives: {
    GoogleSignInButton
  },
  data: () => ({
    clientId: '391364610933-efk7s0n53hv067p25v31dovu9d236vp7.apps.googleusercontent.com'
  }),
  methods: {
    OnGoogleAuthSuccess (idToken) {
      this.$http.post("https://smart-copd-patient.herokuapp.com/login", idToken).then(
        (response) => {

          this.$store.commit('setUserID', idToken);
          console.log(this.$store.getters.getUserID);

          this.someData = response.body;
          console.log(this.someData);

          this.$store.commit('setSession', this.someData.mSessionID);
          console.log(this.$store.getters.getSession);

          if(this.someData.mExists){
            
            console.log("user exists");
            this.$router.push({name: "Patient"});
          }
          else{
            console.log("user does not exist")
            this.$router.push({ name: "Register"})
          }
        },
        (response) => {
          console.log(response.mStatus);
          console.log('message pt2');
          console.log(response.mMessage);
        }
      );

    },
    OnGoogleAuthFail (error) {
      console.log(error)
    }
  }
}
</script> 
 
<style>
.google-signin-button {
  color: white;
  background-color: red;
  height: 50px;
  font-size: 16px;
  border-radius: 10px;
  padding: 10px 20px 25px 20px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
</style> 