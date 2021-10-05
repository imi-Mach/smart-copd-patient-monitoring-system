<template>
  <div>
    <h1>Patient Profile and Overview Data will be stored here</h1>
    <router-link to="/patients">Go Back</router-link> 
  </div>
</template>

<script>

export default {
  mounted: async function() {
    console.log('getting patient info');
    await this.getPatientInfo();
  },
  methods: {
    getPatientInfo: async function() {
      // When should this function be called? It's just getting the patient
      console.log('Getting patient data');
      console.log(this.$store.getters.getSession);
      this.$http.get("https://smart-copd-patient.herokuapp.com/patient/"+this.$store.getters.getSession).then(
        (response) => {
          console.log('Patient Profile data worked');
          this.someData = response.body;
          // NOTE: Patient ID returns Test, should investigate this and see why
          console.log(response.body.mData);
        },
        (response) => {
          console.log(reponse.mStatus);
          console.log('it did not work');
        }
      )
    },
  }
}
</script>
