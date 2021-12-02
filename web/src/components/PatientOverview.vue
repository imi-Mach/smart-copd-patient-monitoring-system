<template>
<div v-loading="loading">
  <el-result
    icon="success"
    title="Good"
    subTitle="According to your recent data, you are in a good shape. Keep it up!"
    v-if="showGood"
  ></el-result>
  <el-result
    icon="info"
    title="Loading..."
    subTitle="Trying our best to load..."
    v-if="showNothing"
  ></el-result>
  <el-result
    icon="warning"
    title="Warning"
    subTitle="Please be cautious about your health condition."
    v-if="showCaution"
  ></el-result>
  <el-result
    icon="error"
    title="Danger"
    subTitle="Please contact your healthcare provider as soon as possible; or call 911 for emergency."
    v-if="showDanger"
  ></el-result>
  <Graph></Graph>
</div>
</template>

<script>
import Graph from './Graph.vue'
export default {
  components: {
    Graph
  },
  data() {
    return {
      showGood: false,
      showCaution: false,
      showDanger: false,
      showNothing: true,
      loading: true,
    };
  },
  methods: {
    checkCookies() {
      if (this.$cookies.isKey("sessionID")) {
        this.$store.commit('setSessionID', this.$cookies.get("sessionID"));
        // Should update the session key to stay logged in
        this.$cookies.set("sessionID", this.$store.getters.getSessionID);
      } else {
        this.$router.push(this.$router.push("/"));
      }
    },
    getPatientData() {
      this.$http.get("https://smart-copd-patient.herokuapp.com/patient/" + this.$store.getters.getSessionID).then((response) => {
      console.log(response)
      if(response.body.mData.pRiskLevel == 0){
        this.showGood = true;
        this.showNothing = false;
        this.loading = false;
      }
      if(response.body.mData.pRiskLevel == 1){
        this.showCaution = true;
        this.showNothing = false;
        this.loading = false;
      }
      if(response.body.mData.pRiskLevel == 2){
        this.showDanger = true;
        this.showNothing = false;
        this.loading = false;
      }
    });
    },
  },
  mounted() {
    this.checkCookies();
    window.setTimeout(this.getPatientData, 500);
  },
};
</script>

<style>
</style>