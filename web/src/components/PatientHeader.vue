<template>
  <div>
    <el-menu default-active="0" mode="horizontal" id="fullscreen">
      <el-menu-item index="1" @click="toPatientPortal">{{ msg }}</el-menu-item>

      <el-submenu index="2" id="user">
        <template slot="title">Welcome, {{ name }}</template>
        <el-menu-item index="2-1" @click="toProfile">Profile</el-menu-item>
        <el-menu-item @click="signout" index="2-2" text-color="#fff"
          >Sign out</el-menu-item
        >
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "PatientHeader",
  props: {
    option: String,
  },
  data() {
    return {
      msg: "Patient Portal",
      name: "",
    };
  },
  methods: {
    signout() {
      this.$store.commit("setSessionID", "");
      this.$router.push("/");
    },
    toProfile(){
      //in portal
      if(this.option == 0){this.$router.push("pprofile");}
    },
    toPatientPortal(){
      if(this.option == 1){this.$router.push("patients");} 
    },
    checkCookies() {
      // console.log('testing');
      // console.log(this.$cookies.get("sessionID"));
      // console.log('testing 2');
      // console.log(this.$cookies.get("sessionID") != null);
      if (this.$cookies.get("sessionID") != null) {
        // console.log('Session ID set');
        this.$store.commit('setSessionID', this.$cookies.get("sessionID"));
        // console.log(this.$store.getters.getSessionID);
      } else {
        // console.log('Session ID NOT set');
        this.$router.push(this.$router.push("/"));
      }
    },
    getPatientData() {
      this.$http.get("https://smart-copd-patient.herokuapp.com/patient/" + this.$store.getters.getSessionID).then((response) => {
      this.name = response.data.mData.pFirstName;
    });
    }
  },
  mounted: function () {
    this.checkCookies();
    window.setTimeout(this.getPatientData, 500);
  },
};
</script>


<style>
#user {
  position: absolute;
  right: 0;
}
#fullscreen {
  height: 100%;
}
</style>