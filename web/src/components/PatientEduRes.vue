<template>
  <el-card>
	 <el-carousel :interval="2000" type="card" height="250px">
	    <el-carousel-item v-for="item in imgList" :key="item">
        <a :href="item.link" target="_blank">
	        <img :src="item.img" style="height:auto;width:100%;">
          </a>
	    </el-carousel-item>
	 </el-carousel>
 </el-card>
</template>

    <script>
export default {
  name: "PatientEduRes",
  data() {
    return {
      imgList: [
        {img: require('@/assets/1.png'), link: "https://www.lvhn.org/find-a-doctor"},
        {img: require('@/assets/2.png'), link: "https://findadoctor.slhn.org"},
        {img: require('@/assets/3.png'), link: "https://www.slhn.org/blog/2021/college-students-home-on-break-help-in-fight-against-covid"}
      ],
    };
  },
};
</script>

<style>
.el-carousel__item img {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}
.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}
.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>