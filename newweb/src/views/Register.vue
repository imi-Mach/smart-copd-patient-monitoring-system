<template>
  <div>
    <img src="@/assets/logo.jpg" id="logo" />
    <h3 id="text">Please Complete the Form below to Sign up</h3>
    <el-card class="box-card">
      <SignUp></SignUp>
    </el-card>
  </div>
</template>

<script>
import SignUp from "@/components/SignUp.vue";

export default {
  name: "Register",
  components: {
    SignUp,
  },
};
</script>

<style>
.box-card {
  width: 480px;
  margin: 0 auto;
}
#logo {
  width: 90px;
  height: 90px;
  display: block;
  margin-left: auto;
  margin-right: auto;
  margin-top: 80px;
  margin-bottom: 10px;
}
#text {
  text-align: center;
    margin-bottom: 20px;
}
</style>