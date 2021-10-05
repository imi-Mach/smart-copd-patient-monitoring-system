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
        hr: "",
        spo2: "",
        rr: "",
        w: "",
        t: "",
        bp: "",
        gl: "",
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
      var request = {"sessionID": this.$store.getters.getSession, "date": today, "heartRate": this.form.hr, "oxygenLevel": this.form.spo2, "weight": this.form.w, "temperature": this.form.t, "bloodPressure": this.form.bp, "glucose": this.form.gl};
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