<template>
  <div>
    <el-form ref="form" :model="form" label-width="auto">
      <el-form-item label="Heart Rate: ">
        <el-input v-model="form.hr"></el-input>
      </el-form-item>

      <el-form-item label="Oxygen Saturation (SpO2): ">
        <el-input v-model="form.spo2"></el-input>
      </el-form-item>

      <el-form-item label="Respiration Rate: ">
        <el-input v-model="form.rr"></el-input>
      </el-form-item>

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
import axios from "axios";

const port = process.env.PORT || '3000'

const apiClient = axios.create({
  // This can likely just be moved to a non-component page so this only has to be done once
    baseURL: 'http://localhost:3000',
    //baseURL = '';
    //baseURL: 'https://smart-copd-patient.herokuapp.com:' + port,
    withCredentials: false, // This is the default
    crossDomain: true,
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json'
    },
    timeout: 10000
})

console.log('PORT:', 'smart-copd-patient.herokuapp.com:' + port) /* eslint-disable-line no-console */

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
    onSubmit() {
      console.log("submit!");
      console.log(typeof this.form);
      console.log(this.form);

      //to be fixed, should assembly the object first then send it
      
			//   this.$http.post(
      //     "http://localhost:3000/patients/api",
      //     '{"hr": this.form.hr, "spo2": this.form.spo2, "rr": this.form.rr}'
      //   );

      this.$http.get

      this.$message({
          message: 'Data Successfully Recorded',
          type: 'success'
        });
      this.onClear();

      //for presentation
      //this.$emit('updateData', form);
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