<template>
<div>
  <el-card class="box-card" style="padding: 15px; margin: 20px">
    <el-form ref="form" :model="form" label-width="480px" label-position="left">
      <el-divider></el-divider>
      <span style="font-weight: bold"
        >In comparison to normal, do you today ...</span
      >
      <el-divider></el-divider>

      <el-form-item label="..experience more shortness of breath?">
        <el-radio-group v-model="form.q1">
          <el-radio label="Yes"></el-radio>
          <el-radio label="No"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item
        label="..experience more fear because of your shortness of breath?"
      >
        <el-radio-group v-model="form.q2">
          <el-radio label="Yes"></el-radio>
          <el-radio label="No"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="..experience more fatigue?">
        <el-radio-group v-model="form.q3">
          <el-radio label="Yes"></el-radio>
          <el-radio label="No"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item
        label="..feel more hindered by your COPD during your daily activities?"
      >
        <el-radio-group v-model="form.q4">
          <el-radio label="Yes"></el-radio>
          <el-radio label="No"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="..experience more sputum in your airway?">
        <el-radio-group v-model="form.q5">
          <el-radio label="Yes"></el-radio>
          <el-radio label="No"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item
        label="..notice any difference in your sputum color/composition?"
      >
        <el-radio-group v-model="form.q6">
          <el-radio label="Yes"></el-radio>
          <el-radio label="No"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="..experience more wheezing?">
        <el-radio-group v-model="form.q7">
          <el-radio label="Yes"></el-radio>
          <el-radio label="No"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="..experience more coughing?">
        <el-radio-group v-model="form.q8">
          <el-radio label="Yes"></el-radio>
          <el-radio label="No"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="..have a sore throat?">
        <el-radio-group v-model="form.q9">
          <el-radio label="Yes"></el-radio>
          <el-radio label="No"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="..have a cold or a runny nose?">
        <el-radio-group v-model="form.q10">
          <el-radio label="Yes"></el-radio>
          <el-radio label="No"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="..experience more stress or tension?">
        <el-radio-group v-model="form.q11">
          <el-radio label="Yes"></el-radio>
          <el-radio label="No"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="..have used more of your bronchodilators?">
        <el-radio-group v-model="form.q12">
          <el-radio label="Yes"></el-radio>
          <el-radio label="No"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-divider></el-divider>
      <span style="font-weight: bold"
        >Physiologic measurements (later will be connected to COTS)</span
      >
      <el-divider></el-divider>

      <el-form-item label="Body Temperature in Degrees Celsius">
        <el-input v-model="form.bt"></el-input>
      </el-form-item>
      <el-form-item label="FEV1 in Liters">
        <el-input v-model="form.fev1"></el-input>
      </el-form-item>
      <el-form-item label="SpO2 in Percentages">
        <el-input v-model="form.spo2"></el-input>
      </el-form-item>

    <div id="submitB">
      <el-button type="primary" @click="onSubmit">Submit</el-button>
      <el-button @click="onClear">Clear</el-button>
    </div>
    </el-form>
  </el-card>
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
      this.form.q1 = "";
      this.form.q2 = "";
      this.form.q3 = "";
      this.form.q4 = "";
      this.form.q5 = "";
      this.form.q6 = "";
      this.form.q7 = "";
      this.form.q8 = "";
      this.form.q9 = "";
      this.form.q10 = "";
      this.form.q11 = "";
      this.form.q12 = "";
      this.form.bt = "";
      this.form.fev1 = "";
      this.form.spo2 = "";
    },
  },
};
</script>

<style>
#submitB {
  margin: auto;
  width: 30%;
}
</style>