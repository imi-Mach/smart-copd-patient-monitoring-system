<template>
  <div>
      <el-menu default-active="0" mode="horizontal" id="fullscreen">
      <el-menu-item index="1" @click="toHealthCarePortal">{{ msg }}</el-menu-item>

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
    name: "HealthcareHeader",
    data(){
      return {
           msg: "Healthcare Provider Portal",
           name: "",
      };
    },
    methods: {
      signout() {
        this.$store.commit("setSessionID", "");
        this.$cookies.remove("sessionID");
        this.$cookies.remove("firstName");
        this.$cookies.remove("lastName");
        this.$cookies.remove("licenseNumber");
        this.$cookies.remove("phoneNumber");
        this.$cookies.remove("email");
        this.$router.push("/");
      },
      toProfile(){
        console.log('going to patient');
        this.$router.push("hprofile");
      },
      toHealthCarePortal() {
        console.log('going to health care')
        this.$router.push("healthcares");
      },
      checkCookies() {
        console.log('checking cookies');
        if (this.$cookies.isKey("sessionID")) {
          console.log('resetting cookies');
          this.$store.commit('setSessionID', this.$cookies.get("sessionID"));
          this.$cookies.set("sessionID", this.$store.getters.getSessionID);
        } else {
          console.log('exiting');
          this.$router.push("/");
        }
      },
      getProviderData() {
        console.log('getting provider data');
        if (this.$cookies.isKey("firstName")) {
          console.log('There was already a name saved');
          this.name = this.$cookies.get("firstName");
        } else {
          // New Route needs to go here
          this.$http.get("https://smart-copd-patient.herokuapp.com/healthcareProfile/" + this.$store.getters.getSessionID).then((response) => {
            console.log(response)
            this.name = response.data.mData.hFirstName; // HAVE TO CHANGE
            console.log('TESTING');
          });
        }
      }
    },
    mounted: function () {
      console.log('Testing the mounted function');
      this.checkCookies();
      window.setTimeout(this.getProviderData, 500);
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