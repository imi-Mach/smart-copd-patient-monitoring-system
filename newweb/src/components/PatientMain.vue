<template>
  <el-tabs tab-position="left" style="height: 100%" :stretch="stretch" type="border-card" v-model="tab_opt">
    <el-tab-pane label="Overview">
      <patient-overview></patient-overview>
    </el-tab-pane>
    <el-tab-pane label="Record Biodata">
      <patient-bio-data></patient-bio-data>
    </el-tab-pane>
    <el-tab-pane label="Data">
      <patient-data :biodata='biodata'></patient-data>
    </el-tab-pane>
    <el-tab-pane label="Education Resources">
      <patient-edu-res></patient-edu-res>
    </el-tab-pane>
    <el-tab-pane label="Contact a Healthcare Profession">
      <patient-help></patient-help>
    </el-tab-pane>
  </el-tabs>
</template>


<script>
import PatientOverview from '@/components/PatientOverview.vue'
import PatientData from '@/components/PatientData.vue'
import PatientBioData from '@/components/PatientBioData.vue'
import PatientEduRes from '@/components/PatientEduRes.vue'
import PatientHelp from '@/components/PatientHelp.vue'


export default {
  name: "PatientMain",
  data() {
    return {
      stretch: true,
      tab_opt: "0",
      
      // now the biodata will need to be changed to the res from the json
      // please pass in the whole 16 attributes, as for future use
      biodata: [{
        "date": "04/06/2021",
        "hr": 1,
        "spo2": 2,
        "rr": 3,
        "rl": 5
      },
      
      {
        "date": "04/02/2021",
        "hr": 80,
        "spo2": 99,
        "rr": 18,
        "rl": 1
      },
      {
        "date": "04/01/2021",
        "hr": 68,
        "spo2": 95,
        "rr": 16,
        "rl": 3
      },
      {
        "date": "03/31/2021",
        "hr": 99,
        "spo2": 98,
        "rr": 25,
        "rl": 2
      },
      {
        "date": "03/30/2021",
        "hr": 89,
        "spo2": 97,
        "rr": 22,
        "rl": 1
      },
      {
        "date": "03/29/2021",
        "hr": 58,
        "spo2": 96,
        "rr": 17,
        "rl": 3
      },
      {
        "date": "03/28/2021",
        "hr": 120,
        "spo2": 92,
        "rr": 24,
        "rl": 4
      },
      {
        "date": "03/27/2021",
        "hr": 77,
        "spo2": 98,
        "rr": 22,
        "rl": 1
      },
      {
        "date": "03/26/2021",
        "hr": 82,
        "spo2": 100,
        "rr": 25,
        "rl": 1
      },
      {
        "date": "03/25/2021",
        "hr": 91,
        "spo2": 100,
        "rr": 30,
        "rl": 1
      },
      {
        "date": "03/24/2021",
        "hr": 80,
        "spo2": 99,
        "rr": 28,
        "rl": 1
      },
      {
        "date": "03/23/2021",
        "hr": 117,
        "spo2": 97,
        "rr": 20,
        "rl": 3
      },
      {
        "date": "03/22/2021",
        "hr": 102,
        "spo2": 96,
        "rr": 21,
        "rl": 2
      }],
    };
  },
  mounted:function(){
    console.log('on load working');
    this.getPatientInfo();
    console.log('attempting to enter getting daily data');
    this.getDailyData();
    console.log('attempting to insert data');
    this.insertData();

    //pop up window
    this.$confirm("Let's fill out the daily survey", 'Hi', {
          confirmButtonText: 'Sure',
          cancelButtonText: 'Later',
          type: 'info'
        }).then(() => {
          this.tab_opt="1";
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'See you later'
          });          
        });
  },
  methods: {
    getPatientInfo: function() {
      console.log('Getting patient data');
      this.$http.get("https://smart-copd-patient.herokuapp.com/patient", this.$store.getters.getSession).then(
        (response) => {
          console.log('it did work');
          this.someData = response.body;
          console.log(response)
        },
        (response) => {
          console.log(reponse.mStatus);
          console.log('it did not work');
        }
      )
    },
    getDailyData: function() {
      console.log('Getting daily data');
      this.$http.get("https://smart-copd-patient.herokuapp.com/myData", this.$store.getters.getSession).then(
        (response) => {
          console.log('it did work');
          this.someData = response.body;
          console.log(response)
        },
        (response) => {
          console.log(reponse.mStatus);
          console.log('it did not work');
        }
      )
    },
    insertData: function() {
      console.log('Inserting data');
      var request = {"sessionID": this.$store.getters.getSession, "date": "01/01/2000", "heartRate": "90", "oxygenLevel": "85", "weight": "135", "temperature": "92", "bloodPressure": "80/120", "glucose": "12"};
      console.log(request);
      this.$http.post("https://smart-copd-patient.herokuapp.com/insertData", request).then(
        (response) => {
          console.log('it did work');
          this.someData = response.body;
          console.log(response)
        },
        (response) => {
          console.log(reponse.mStatus);
          console.log('it did not work');
        }
      )
    },
    //updateBioData(childBioData){
    //  this.biodata.push(childBioData);
    //},
  },
  
  components:{
      PatientOverview,
      PatientData,
      PatientBioData,
      PatientEduRes,
      PatientHelp

  }
};
</script>