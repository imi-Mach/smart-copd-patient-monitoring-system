<template>
  <el-container>
    <el-header>
      <Navbar></Navbar>
    </el-header>
    <el-main>
      <el-card class="box-card">
        <div>
          <h2>Patient Portal</h2>
          <p>If you are a COPD patient, please use the button below to sign in. </p>
          <p>New to our app? Use the button below to register.</p>
          <Login patient="0"></Login>
        </div>
      </el-card>
      <el-card class="box-card">
        <h2>Healthcare Provider Portal</h2>
        <p>Healthcare providers, please use the button below to sign in.</p>
        <p>If you are new to our app, you will need a designated Registration Code ready to register.</p>
        <Login patient="1"></Login>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
// @ is an alias to /src
import Login from "@/components/Login.vue";
import Navbar from "@/components/NavBar.vue";
export default {
  name: "Home",
  components: {
    Login,
    Navbar,
  },
};
</script>

<style>
.box-card {
    width: 480px;
    margin: 0 auto;
    margin-top: 50px;

  }
</style>
