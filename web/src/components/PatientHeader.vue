<template>
  <div>
    <el-menu default-active="1" class="el-menu-demo" mode="horizontal">
      <el-menu-item index="1">{{ msg }}</el-menu-item>

      <el-submenu index="2">
        <template slot="title">Welcome, {{ name }}</template>
        <el-menu-item index="2-1">Profile</el-menu-item>
        <el-menu-item index="2-2" text-color="#fff">Log out</el-menu-item>
      </el-submenu>

    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'PatientHeader',
  data(){
      return{
        msg: 'Smart COPD Patient Monitoring System',
        name: 'Max'
      }
  }
}
</script>