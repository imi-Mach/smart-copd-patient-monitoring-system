<template>
  <div>
    <el-form ref="form" :model="form" label-width="auto">
      <el-form-item label="Heart Rate: ">
        <el-input v-model="form.hr"></el-input>
      </el-form-item>

      <el-form-item label="Oxygen Saturation (SpO2): ">
        <el-input v-model="form.spo2"></el-input>
      </el-form-item>

      <!-- <el-form-item label="Respiration Rate: ">
        <el-input v-model="form.rr"></el-input>
      </el-form-item> LOOKS LIKE WE ARE NOT USING THIS VALUE -->

      <el-form-item label="Weight: ">
        <el-input v-model="form.w"></el-input>
      </el-form-item>

      <el-form-item label="Temperature: ">
        <el-input v-model="form.t"></el-input>
      </el-form-item>

      <el-form-item label="Blood Pressure: ">
        <el-input v-model="form.bp"></el-input>
      </el-form-item>

      <el-form-item label="Glucose Level: ">
        <el-input v-model="form.gl"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" :plain="true" @click="onSubmit">Submit</el-button>
        <el-button @click="onClear">Clear</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

export default {
  name: "PatientBioData",
  data() {
    return {
      form: {
        sessionID: "",
        q1: "",
        q2: "",
        q3: "",
        q4: "",
        q5: "",
        q7: "",
        q8: "",
        q9: "",
        q10: "",
        q11: "",
        q12: "",
        bt: "",
        fev1: "",
        spo2: "",
      },
    };
  },
  methods: {
    insertData: async function() {
      console.log('Inserting data');

      var today = new Date();
      
      var dd = String(today.getDate()).padStart(2, '0');
      var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
      var yyyy = today.getFullYear();

      today = mm + '/' + dd + '/' + yyyy;

      // should we be using oxygen level or respiration level
      // might need an equation using these two
      var request = {"sessionID": this.$store.getters.getSession, 
      "date": today,
      "q1": this.q1,
      "q2": this.q2,
      "q3": this.q3,
      "q4": this.q4,
      "q5": this.q5,
      "q6": this.q6,
      "q7": this.q7,
      "q8": this.q8,
      "q9": this.q9,
      "q10": this.q10,
      "q11": this.q11,
      "q12": this.q12,
      "bt": this.bt,
      "fev1": this.fev1,
      "spo2": this.spo2}
      console.log(request);
      this.$http.post("https://smart-copd-patient.herokuapp.com/insertData", request).then(
        (response) => {
          console.log('it did work');
          this.someData = response.body;
          console.log(response);
          console.log(response.body);
        },
        (response) => {
          console.log(reponse.mStatus);
          console.log('it did not work');
        }
      )
    },
    onSubmit: async function() {
      console.log("submit!");
      console.log(typeof this.form);
      console.log(this.form);
      await this.insertData();
      console.log('data inserted');
      onClear();
    },
    onClear() {
      this.form.hr = "";
      this.form.spo2 = "";
      this.form.rr = "";
      this.form.w = "";
      this.form.t = "";
      this.form.bp = "";
      this.form.gl = "";
    },
  },
};
</script>