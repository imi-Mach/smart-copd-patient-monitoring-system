<template>
  <div>
    <h1>COPD Educational Resources</h1>
  <el-collapse v-model="activeNames" @change="handleChange" accordion>
    <el-collapse-item title="Find a Doctor">
        <el-row :gutter="20">  
          <el-col :span="10">
            <div class="grid-content">
              <el-card class="box-card" shadow="always">
                <div slot="header" class="header">
                  <span>Lehigh Valley Health Network</span>
                  <el-button style="float: right; padding: 3px 0" @click="navContent(1)" type="text">Learn More</el-button>
                </div>
                <div class="image">
                  <img src="../assets/1.png" class="image">
                </div>
              </el-card>
            </div>
          </el-col>
          <el-col :span="10">
            <div class="grid-content">
              <el-card class="box-card" shadow="always">
                <div slot="header" class="header">
                  <span>St. Luke's</span>
                  <el-button style="float: right; padding: 3px 0" @click="navContent(2)" type="text">Learn More</el-button>
                </div>
                <div class="image">
                  <img src="../assets/2.png" class="image">
                </div>
              </el-card>
            </div>
          </el-col>
        </el-row>
    </el-collapse-item>
    <el-collapse-item title="COPD Info">
      <el-row>
        <el-col :span="10">
          <div class="grid-content">
            <el-card class="box-card" shadow="always">
              <div slot="header" class="header">
                <span>Is COPD Hereditary?</span>
                <el-button style="float: right; padding: 3px 0" @click="navContent(3)" type="text">Learn More</el-button>
              </div>
              <div class="image2">
                <img src="../assets/5.jpg" class="image2">
              </div>
            </el-card>
          </div>
        </el-col>
        <el-col :span="10">
          <div class="grid-content">
            <el-card class="box-card" shadow="always">
              <div slot="header" class="header">
                <span>Understanding COPD</span>
                <el-button style="float: right; padding: 3px 0" @click="navContent(4)" type="text">Learn More</el-button>
              </div>
              <div class="image2">
                <img src="../assets/4.jpg" class="image2">
              </div>
            </el-card>
          </div>
        </el-col>
      </el-row>
    </el-collapse-item>
    <el-collapse-item title="COPD News">
        <el-row :gutter="20">  
          <el-col :span="10">
            <div class="grid-content">
              <el-card class="box-card" shadow="always">
                <div slot="header" class="header">
                  <span>COVID-19</span>
                  <el-button style="float: right; padding: 3px 0" @click="navContent(6)" type="text">Learn More</el-button>
                </div>
                <div class="image2">
                  <img src="../assets/3.png" class="image2">
                </div>
              </el-card>
            </div>
          </el-col>
          <el-col :span="10">
            <div class="grid-content">
              <el-card class="box-card" shadow="always">
                <div slot="header" class="header">
                  <span>Lung Cancer Awareness Month</span>
                  <el-button style="float: right; padding: 3px 0" @click="navContent(7)" type="text">Learn More</el-button>
                </div>
                <div class="image2">
                  <img src="../assets/6.jpg" class="image2">
                </div>
              </el-card>
            </div>
          </el-col>
        </el-row>
    </el-collapse-item>
    <el-collapse-item title="Community">
        <el-row :gutter="20">  
          <el-col :span="10">
            <div class="grid-content">
              <el-card class="box-card" shadow="always">
                <div slot="header" class="header">
                  <span>Facebook Groups</span>
                  <el-button style="float: right; padding: 3px 0" @click="navContent(6)" type="text">Learn More</el-button>
                </div>
                <div class="image2">
                  <img src="../assets/7.png" class="image">
                </div>
              </el-card>
            </div>
          </el-col>
          <el-col :span="10">
            <div class="grid-content">
              <el-card class="box-card" shadow="always">
                <div slot="header" class="header">
                  <span>Finding a Support Group</span>
                  <el-button style="float: right; padding: 3px 0" @click="navContent(7)" type="text">Learn More</el-button>
                </div>
                <div class="image2">
                  <img src="../assets/8.jpg" class="image2">
                </div>
              </el-card>
            </div>
          </el-col>
        </el-row>
    </el-collapse-item>
  </el-collapse>
  </div>
</template>

<script>


export default {
  name: "HelloWorld",
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