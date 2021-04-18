<template>
  <el-carousel :interval="4000" type="card" height="400px">
    <el-carousel-item v-for="item in resources" :key="item">
      <h3>{{ item }}</h3>
    </el-carousel-item>
  </el-carousel>
</template>

    <script>
export default {
  name: "HelloWorld",
  data() {
    return {
      resources: [
        "resources1",
        "resources2",
        "resources3",
        "resources4",
        "resources5",
        "resources6",
      ],
    };
  },
};
</script>
