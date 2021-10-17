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
      // console.log('testing');
      // console.log(this.$cookies.get("sessionID"));
      // console.log('testing 2');
      // console.log(this.$cookies.get("sessionID") != null);
      if (this.$cookies.get("sessionID") != null) {
        // console.log('Session ID set');
        this.$store.commit('setSessionID', this.$cookies.get("sessionID"));
        // console.log(this.$store.getters.getSessionID);
      } else {
        // console.log('Session ID NOT set');
        this.$router.push(this.$router.push("/"));
      }
    },
    getPatientData() {
      this.$http.get("https://smart-copd-patient.herokuapp.com/myData/" + this.$store.getters.getSessionID).then((response) => {
      console.log(response.body.mData.length)
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