<template>
  <el-descriptions
    class="margin-top"
    title="Your primary healthcare provider"
    :column="2"
    border
  >
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-user"></i>
        Name
      </template>
      {{ this.res.name }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-warning"></i>
        Emergency
      </template>
      911
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-phone"></i>
        Call
      </template>
      <a v-bind:href="this.tel + this.res.phoneNumber">{{
        this.res.phoneNumber
      }}</a>
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-message"></i>
        Email
      </template>
      <a v-bind:href="this.mailto + this.res.email" target="_blank">{{
        this.res.email
      }}</a>
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-printer"></i>
        Fax:
      </template>
      {{ this.res.fax }}
    </el-descriptions-item>
    <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-custom"></i>
          Title
        </template>
        <el-tag type="warning" size="medium">Critical Care Medicine and Pulmonologist</el-tag>
      </el-descriptions-item>
  </el-descriptions>
</template>

<script>
export default {
  name: "PatientHelp",
  data() {
    return {
      res: {
        name: "Yaniv Dotan",
        phoneNumber: "2673091066",
        email: "ydotan@temple.edu",
        fax: "2155707502",
      },
      tel: "tel:",
      mailto:"mailto:",
    };
  },
};
</script>

<style>
</style>