<template>
  <div class="demo-image">
    <image src='src/assets/logo.png'></image>
    <div>
      <h3 style="text-align: center">Data Summary (Last 7 days)</h3>
    </div>
    <div class="block">
      <el-image style="width: 100%; height: 350px" :src='src' :fit="fit">
        <div slot="error" class="image-slot">
          <i class="el-icon-picture-outline"></i>
        </div>
      </el-image>
    </div>
  </div>
</template>

<script>
export default {
  name: "PatientOverview",
  data() {
    return {
      fit: 'contain',
      src: ''
    };
  },
};
</script>