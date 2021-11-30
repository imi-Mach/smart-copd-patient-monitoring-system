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
      this.$cookies.remove("sessionID");
      this.$cookies.remove("firstName");
      this.$cookies.remove("lastName");
      this.$cookies.remove("DOB");
      this.$cookies.remove("phoneNumber");
      this.$cookies.remove("email");
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
      if (this.$cookies.isKey("sessionID")) {
        this.$store.commit('setSessionID', this.$cookies.get("sessionID"));
        // Should update the session key to stay logged in
        this.$cookies.set("sessionID", this.$store.getters.getSessionID);
      } else {
        this.$router.push("/");
      }
    },
    getPatientData() {
      if (this.$cookies.isKey("firstName")) {
        this.name = this.$cookies.get("firstName");
      } else {
        this.$http.get("https://smart-copd-patient.herokuapp.com/patient/" + this.$store.getters.getSessionID).then((response) => {
        this.name = response.data.mData.pFirstName;
        });
      }
    }
  },
  mounted: function () {
    this.checkCookies();
    window.setTimeout(this.getPatientData(), 500);
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