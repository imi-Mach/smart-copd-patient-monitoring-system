<template>
  <div class="small">
    <line-chart :chart-data="datacollection"></line-chart>
  </div>
</template>

<script>
  import LineChart from './LineChart.js'

  export default {
    components: {
      LineChart
    },
    data () {
      return {
        datacollection: null
      }
    },
    mounted () {
      this.fillData()
    },
    methods: {
      fillData () {
        this.datacollection = {
          labels: ["1", "2", "3", "4", "5", "6", "7"],
          datasets: [
            {
              label: 'Risk Levels',
              backgroundColor: '#f87979',
              data: [this.get1(), this.get2(), this.get3(), this.get4(), this.get5(), this.get6(), this.get7()],
              fill: false,
            },
          ]
        }
      },
      get1 () {
          var point1 = this.$cookies.get("risk0");
          console.log(point1);
          if (point1 != null) {
              return point1
          } else {
              return 0;
          }
        //return Math.floor(Math.random() * (50 - 5 + 1)) + 5
      },
      get2() {
          var point2 = this.$cookies.get("risk1");
          console.log(point2);
          if (point2 != null) {
              return point2
          } else {
              return 0;
          }
      },
      get3() {
          var point3 = this.$cookies.get("risk2");
          console.log(point3);
          if (point3 != null) {
              return point3
          } else {
              return 0;
          }
      },
      get4() {
          var point4 = this.$cookies.get("risk3");
          console.log(point4);
          if (point4 != null) {
              return point4
          } else {
              return 0;
          }
      },
      get5() {
          var point5 = this.$cookies.get("risk4");
          console.log(point5);
          if (point5 != null) {
              return point5
          } else {
              return 0;
          }
      },
      get6() {
          var point6 = this.$cookies.get("risk5");
          console.log(point6);
          if (point6 != null) {
              return point6
          } else {
              return 0;
          }
      },
      get7() {
          var point7 = this.$cookies.get("risk6");
          console.log(point7);
          if (point7 != null) {
              return point7
          } else {
              return 0;
          }
      },
    }
  }
</script>

<style>
  .small {
    max-width: 600px;
    margin:  10px auto;
  }
</style>