<template>
  <el-container>
    <el-header>
      <PatientHeader option="0"></PatientHeader>
    </el-header>
    <el-main>
      <el-tabs
        tab-position="left"
        style="height: 100%"
        :stretch="stretch"
        type="border-card"
        v-model="tab_opt"
      >
        <el-tab-pane label="Overview">
          <PatientOverview></PatientOverview>
        </el-tab-pane>
        <el-tab-pane label="Daily Survey">
          <PatientSurvey></PatientSurvey>
        </el-tab-pane>
        <el-tab-pane label="Data">
          <PatientAllData></PatientAllData>
        </el-tab-pane>
        
        <el-tab-pane label="Your Healthcare Provider">
          <PatientHelp></PatientHelp>
        </el-tab-pane>
        <el-tab-pane label="Daily Tips">
          <PatientEduRes></PatientEduRes>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
// @ is an alias to /src
import PatientHeader from "@/components/PatientHeader.vue";
import PatientOverview from "@/components/PatientOverview.vue";
import PatientSurvey from "@/components/PatientSurvey.vue";
import PatientAllData from "@/components/PatientAllData.vue";
import PatientEduRes from "@/components/PatientEduRes.vue";
import PatientHelp from "@/components/PatientHelp.vue";

export default {
  name: "PatientPortal",
  data(){
    return {
      tab_opt: '0',
      stretch: true,
    }
  },
  components: {
    PatientHeader,
    PatientOverview,
    PatientSurvey,
    PatientAllData,
    PatientEduRes,
    PatientHelp,
  },
  mounted(){
    if (!this.$cookies.isKey("dailyCounter")) {
      this.$cookies.set("dailyCounter", "test");
      this.$confirm("Let's fill out the daily survey", "Hi", {
      confirmButtonText: "Sure",
      cancelButtonText: "Later",
      type: "info",
    })
      .then(() => {
        this.tab_opt = "1";
      })
      .catch(() => {
        this.$message({
          type: "info",
          message: "See you later",
        });
      });
    }
  },
};
</script>