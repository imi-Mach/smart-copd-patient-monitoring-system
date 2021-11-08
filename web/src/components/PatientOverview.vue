<template>
<div>
  <el-result
    icon="success"
    title="Good"
    subTitle="According to your recent data, you are in a good shape. Keep it up!"
    v-if="hasResult"
  ></el-result>
  <el-result
    icon="info"
    title="Oops"
    subTitle="We do not have enough data from you to evaluate your condition. Please take your daily survey!"
    v-if="!hasResult"
  ></el-result>
</div>
</template>

<script>
export default {
  data() {
    return {
      hasResult: false,
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
      this.$http.get("https://smart-copd-patient.herokuapp.com/myData/" + this.$store.getters.getSessionID).then((response) => {
      if(response.body.mData.length > 7){
        this.hasResult = true;
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