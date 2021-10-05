<template>
  <el-container>
    <el-header>
      <Navbar></Navbar>
    </el-header>
    <el-main>
      <el-card class="box-card">
        <div>
          <h2>Patient Portal</h2>
          <Login option="Patients Please Sign in Here" patient="0"></Login>
        </div>
      </el-card>
      <el-card class="box-card">
        <h2>Healthcare Provider Portal</h2>
        <Login option="Healthcare Provider Please Sign in Here" patient="1"></Login>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";
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
    margin: 3%;
  }
</style>
