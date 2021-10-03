<template>
  <div class="root">
    <!-- <img alt="Vue logo" src="../assets/logo.png" /> -->
    <el-menu
      :default-active="activeIndex"
      class="fullscreen"
      mode="horizontal"
      @select="handleSelect"
      text-color="#050505"
    >
      <el-menu-item index="1">
        <a href="https://engineering.lehigh.edu/cse" target="_blank">
          <img
            alt="Vue logo"
            src="@/assets/LehighU_official-logo_Color-300dpi.jpg"
          />
        </a>
      </el-menu-item>

      <el-menu-item index="2">Patient Portal </el-menu-item>

      <el-menu-item index="3" disabled>Healthcare Portal</el-menu-item>

      <el-menu-item index="4">Services</el-menu-item>

      <el-menu-item index="5">About us </el-menu-item>

      <el-submenu index="6">
        <template slot="title">Services</template>
        <el-menu-item index="2-1">Patients</el-menu-item>
        <el-menu-item index="2-2">Doctors</el-menu-item>
          
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeIndex: "1",
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
  },
};
</script>

<style>
.root {
  display: flex;
}
img {
  width: auto;
  height: 50%;
  object-fit: cover;
}
.fullscreen {
  width: 100%;
}
</style>