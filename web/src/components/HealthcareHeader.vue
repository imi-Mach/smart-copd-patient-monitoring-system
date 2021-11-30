<template>
  <div>
      <el-menu default-active="0" mode="horizontal" id="fullscreen">
      <el-menu-item index="1">{{ msg }}</el-menu-item>

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
        }
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
        this.$router.push("hprofile")
      }
    }
}
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