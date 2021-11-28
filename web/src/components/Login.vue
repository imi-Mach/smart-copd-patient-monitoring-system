<template>
  <div>
  <el-button type="primary" v-google-signin-button="clientId" class="google-signin-button" plain v-if="patient == 0" @click="patientClicked">Continue with Google</el-button>
  <el-button type="warning" v-google-signin-button="clientId" class="google-signin-button" plain v-if="patient == 1" @click="healthCareClicked">Continue with Google</el-button>
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
      console.log(idToken)
      if (this.$cookies.get("isPatient")) {
        this.$http.post("https://smart-copd-patient.herokuapp.com/login", idToken).then((response) => {
          this.$store.commit('setSessionID', response.data.mSessionID);
          if(response.data.mExists) {
            this.$cookies.set("sessionID", this.$store.getters.getSessionID);
            this.$router.push("patients");
          } else {
            this.$router.push("register");
          }
        }
      );
      } else if (this.$cookies.get("isHealthCare")) {
        this.$http.post("https://smart-copd-patient.herokuapp.com/healthcarelogin", idToken).then((response) => {
          this.$store.commit('setSessionID', response.data.mSessionID);
          if (response.data.mExists) {
            this.$cookies.set("sessionID", this.$store.getters.getSessionID);
            this.$router.push("healthcares");
          } else {
            this.$router.push("register");
          }
        }
      );
      }
    },
    OnGoogleAuthFail (error) {
      console.log(error)
    },
    healthCareClicked() {
      this.$cookies.set("isHealthCare", true);
      this.$cookies.set("isPatient", false);
    },
    patientClicked() {
      this.$cookies.set("isHealthCare", false);
      this.$cookies.set("isPatient", true);
    }
  },
}
</script> 