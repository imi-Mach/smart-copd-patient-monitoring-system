<template>
  <div>
  <el-button type="primary" v-google-signin-button="clientId" class="google-signin-button" plain v-if="patient == 0">Continue with Google</el-button>
  <el-button type="warning" v-google-signin-button="clientId" class="google-signin-button" plain v-if="patient == 1">Continue with Google</el-button>
  </div>
</template>
 
<script>
import GoogleSignInButton from 'vue-google-signin-button-directive'

export default {
  name: "Login",
  props: {
    patient: String,
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
          this.$store.commit('setSessionID', response.data.mSessionID);
          if(response.data.mExists) {
            this.$cookies.set("sessionID", this.$store.getters.getSessionID);
            console.log('Patient exists and setting patient ID');
            console.log(this.$cookies.get("sessionID"));
            this.$router.push("patients");
          } else {
            this.$router.push("register");
          }
        }
      );
    },
    OnGoogleAuthFail (error) {
      console.log(error)
    },
  },
}
</script> 