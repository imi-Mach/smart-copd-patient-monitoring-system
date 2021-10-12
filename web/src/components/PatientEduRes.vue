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
  methods: {
    navContent(index) {
      switch (index) {
        case 1:
          window.location.href = "https://www.lvhn.org/find-a-doctor";
          break;
        case 2:
          window.location.href = "https://findadoctor.slhn.org/";
          break;
        case 3:
          window.location.href = "https://www.slhn.org/blog/2021/college-students-home-on-break-help-in-fight-against-covid";
          break;
        case 4:
          window.location.href = "https://easyoxygen.com.au/blogs/articles/understanding-copd-medical-oxygen-help";
          break;
        case 5:
          window.location.href = "https://www.healthline.com/health/copd/is-copd-hereditary";
          break;
        case 6:
          window.location.href = "https://www.unitypoint.org/article.aspx?id=2dbeb838-11b2-47d5-9cc7-95c8a9f28631";
          break;
        case 7:
          window.location.href = "https://www.facebook.com/COPD-Friends-for-Friends-110451268982117/";
          break;
        case 8:
          window.location.href = "https://www.lung.org/lung-health-diseases/lung-disease-lookup/copd/living-with-copd/finding-support";
          break;
      }
    }
  }
};
</script>

<style>

  .title {
    float: "left";
    font-size: 42px;
  }

  .header {
    font-size: 24px;
    font-weight: bold;
    float: "center";
  }

  .image {
     height: 100%;
     padding-top: 10px;
  }

  .image2 {
    height: 140px;
    padding-bottom: 10px;
  }

  .box-card {
    width: 40em;
    height: 250px;
    float: "center";
    text-align: "center";
  }

  .el-col {
    border-radius: 4px;
  } 
  .grid-content {
    border-radius: 4px;
    min-height: 400px;
  }
</style>