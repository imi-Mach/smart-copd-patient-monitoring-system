<template>
  <div class="root">
    <!-- <img alt="Vue logo" src="../assets/logo.png" /> -->
    <el-menu
      class="fullscreen"
      mode="horizontal"
      @select="handleSelect"
      text-color="#050505"
    >
      <el-menu-item index="1">
        <img alt="Logo" src="@/assets/logo.jpg" />
      </el-menu-item>
        <el-menu-item index="2">
        Home
      </el-menu-item>

      <el-menu-item index="3">Team</el-menu-item>

      <el-menu-item index="4">More about COPD</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
    };
  },
  methods: {
      handleSelect(key, keyPath) {
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