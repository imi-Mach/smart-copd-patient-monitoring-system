<template>
  <div>
  <!-- <button v-google-signin-button="clientId" class="google-signin-button">{{ option }}</button> -->
  <el-button type="primary" v-google-signin-button="clientId" class="google-signin-button" plain v-if="patient == 0">{{ option }}</el-button>
  <el-button type="warning" v-google-signin-button="clientId" class="google-signin-button" plain v-if="patient == 1">{{ option }}</el-button>
  </div>
</template>
 
<script>
import GoogleSignInButton from 'vue-google-signin-button-directive'

export default {
  name: "Login",
  props: {
    option: String,
    patient: Number,
  },
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

// Helpful resources:
// https://vue-view.com/making-a-one-click-google-sign-in-for-vue-js-application/
// https://developers.google.com/identity/sign-in/web/sign-in
// https://www.npmjs.com/package/vue-google-signin-button


// https://stackoverflow.com/questions/43027499/vuex-state-on-page-refresh
// Link for persistant session ID

          console.log('LOGIN ROUTE');
          console.log(response.body);

          this.$store.commit('setUserID', idToken);
          //console.log(this.$store.getters.getUserID);

          this.someData = response.body;
          //console.log(this.someData);

          this.$store.commit('setSession', this.someData.mSessionID);
          //console.log(this.$store.getters.getSession);

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
/* .google-signin-button {
  color: white;
  background-color: red;
  height: 50px;
  font-size: 16px;
  border-radius: 10px;
  padding: 10px 20px 25px 20px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
} */
</style> 