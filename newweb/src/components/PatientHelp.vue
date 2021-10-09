<template>
<el-descriptions class="margin-top" title="Your primary healthcare provider" :column="2" :size="size" border>

    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-user"></i>
        Name
      </template>
      {{this.res.name}}
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
      <a v-bind:href="this.tel+this.res.phoneNumber">{{this.res.phoneNumber}}</a>
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-message"></i>
        Email
      </template>
      <a v-bind:href="this.mailto+this.res.email" target="_blank">{{this.res.email}}</a>
    </el-descriptions-item>

  </el-descriptions>
</template>

<script>
export default {
  name: "PatientHelp",
  data() {
    return {
      // the json get from the route
      res:{
        name: "Thomas Liu",
        phoneNumber: "4844560001",
        email: "xil322@lehigh.edu",
      },
      tel: "tel:",
      mailto:"mailto:",
    };
  },
  methods: {
    onSubmit() {
      console.log("submit!");
      this.$message({
          message: 'Message Successfully Sent',
          type: 'success'
        });
      this.onClear();
    },
    onClear() {
      this.form.type = [];
      this.form.desc = "";
    },
  },
};
</script>

<style>
#help {
    text-align: left;
}
</style>