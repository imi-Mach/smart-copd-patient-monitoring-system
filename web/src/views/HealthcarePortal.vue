<template>
  <el-container>
    <el-header>
        <HealthcareHeader></HealthcareHeader>
    </el-header>
    <el-main>
      <HealthcareMain></HealthcareMain>
    </el-main>
  </el-container>
</template>

<script>

import HealthcareHeader from "@/components/HealthcareHeader.vue";
import HealthcareMain from "@/components/HealthcareMain.vue";

export default {
  name: "HealthcarePortal",
  components: {
      HealthcareHeader,
      HealthcareMain,
  },
};
</script>

<style>
</style>