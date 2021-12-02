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
        datacollection: null,
        point1: 0,
        point2: 0,
        point3: 0,
        point4: 0,
        point5: 0, 
        point6: 0,
        point7: 0,
      }
    },
    mounted () {
      this.fillData()
    },
    methods: {
      fillData () {
          this.$http.get("https://smart-copd-patient.herokuapp.com/myData/" + this.$store.getters.getSessionID).then((response) => {
            var len = 0;
            this.point1 = 0
            if (response.body.mStatus == "ok") {
                len = response.body.mData.length;
                if (len > 0) {
                    if (len >= 7) {
                        this.point1 = response.body.mData[len-7].riskLevel;
                        this.point2 = response.body.mData[len-6].riskLevel;
                        this.point3 = response.body.mData[len-5].riskLevel;
                        this.point4 = response.body.mData[len-4].riskLevel;
                        this.point5 = response.body.mData[len-3].riskLevel;
                        this.point6 = response.body.mData[len-2].riskLevel;
                        this.point7 = response.body.mData[len-1].riskLevel;
                    } else {
                        switch(len) {
                            case 1:
                                this.point1 = response.body.mData[0].riskLevel;
                                break;
                            case 2:
                                this.point1 = response.body.mData[0].riskLevel;
                                this.point2 = response.body.mData[1].riskLevel;
                                break;
                            case 3:
                                this.point1 = response.body.mData[0].riskLevel;
                                this.point2 = response.body.mData[1].riskLevel;
                                this.point3 = response.body.mData[2].riskLevel;
                                break;
                            case 4:
                                this.point1 = response.body.mData[0].riskLevel;
                                this.point2 = response.body.mData[1].riskLevel;
                                this.point3 = response.body.mData[2].riskLevel;
                                this.point4 = response.body.mData[3].riskLevel;
                                break;
                            case 5:
                                this.point1 = response.body.mData[0].riskLevel;
                                this.point2 = response.body.mData[1].riskLevel;
                                this.point3 = response.body.mData[2].riskLevel;
                                this.point4 = response.body.mData[3].riskLevel;
                                this.point5 = response.body.mData[4].riskLevel;
                                break;
                            case 6:
                                this.point1 = response.body.mData[0].riskLevel;
                                this.point2 = response.body.mData[1].riskLevel;
                                this.point3 = response.body.mData[2].riskLevel;
                                this.point4 = response.body.mData[3].riskLevel;
                                this.point5 = response.body.mData[4].riskLevel;
                                this.point6 = response.body.mData[5].riskLevel;
                                break;
                            default:
                                console.log('Error');
                                break
                        }
                    }
                }
            } else {
                console.log('ERROR on route');
            }
        }),
        this.datacollection = {
          labels: ["1", "2", "3", "4", "5", "6", "7"],
          datasets: [
            {
              label: 'Risk Levels',
              backgroundColor: '#f87979',
              data: [this.point1, this.point2, this.point3, this.point4, this.point5, this.point6, this.point7],
              fill: false,
            },
          ]
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