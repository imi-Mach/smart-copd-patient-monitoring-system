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
    },
  },
};
</script>
