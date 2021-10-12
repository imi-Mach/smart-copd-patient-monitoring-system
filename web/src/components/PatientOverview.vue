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
  mounted() {
    this.$http
      .get(
        "https://smart-copd-patient.herokuapp.com/myData/" +
          this.$store.getters.getSessionID
      )
      .then((response) => {
        console.log(response.body.mData.length)
        if(response.body.mData.length > 7){
            this.hasResult = true;
        }
      });
  },
};
</script>

<style>
</style>