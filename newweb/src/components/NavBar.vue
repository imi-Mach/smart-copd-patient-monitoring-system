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
        <img alt="Logo" src="@/assets/logo.png" />
      </el-menu-item>

      <el-submenu index="2">
        <template slot="title">Explore</template>
        <el-menu-item index="2-1">
          <span style="font-family:Avenir">Patients</span>
        </el-menu-item>
        <el-menu-item index="2-2">
          <span style="font-family:Avenir">Healthcare</span>
        </el-menu-item>
      </el-submenu>

      <el-menu-item index="3">Team</el-menu-item>

      <el-menu-item index="4">More about COPD</el-menu-item>
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
.drop-down{
  font-family: Arial, Helvetica, sans-serif;
}
</style>