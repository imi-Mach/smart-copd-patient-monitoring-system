<template>
  <div>
    <h1>Patient Profile and Overview Data will be stored here</h1>
    <router-link to="/patients">Go Back</router-link> 
  </div>
</template>

<script>

export default {
  mounted: function() {
    console.log('getting patient data works');
    await this.getPatientInfo();
  },
  methods: {
    getPatientInfo: async function() {
      // When should this function be called? It's just getting the patient
      console.log('Getting patient data');
      console.log(this.$store.getters.getSession);
      this.$http.get("https://smart-copd-patient.herokuapp.com/patient/"+this.$store.getters.getSession).then(
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
  }
}
</script>
