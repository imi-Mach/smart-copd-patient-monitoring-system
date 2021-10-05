<template>
    <!-- TODO: Check user input, using another form -->
  <el-form ref="form" label-width="120px">
    <el-form-item label="First name: ">
      <el-input v-model="firstName" class="uinput"></el-input>
    </el-form-item>

    <el-form-item label="Last name: ">
      <el-input v-model="lastName" class="uinput"></el-input>
    </el-form-item>

    <el-form-item label="Date of Birth: ">
      <el-date-picker
        v-model="DOB"
        type="date"
        placeholder="Please select your DOB"
        default-value="1990-10-01"
      >
      </el-date-picker>
    </el-form-item>

    <el-form-item label="Phone Number: ">
      <el-input v-model="phoneNumber" class="uinput"></el-input>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="processForm">Submit</el-button>
      <!-- TODO: Cancel will need to sign out the user -->
      <el-button>Cancel</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data: () => ({
    firstName: "",
    lastName: "",
    DOB: "",
    phoneNumber: "",
    errors: {
      firstName: false,
      lastName: false,
      DOB: false,
    },
  }),
  methods: {
    processForm: function () {
      
    // TODO: now DOB is a js Date object with format similar to 
    // Tue Sep 11 1990 00:00:00 GMT-0400 (Eastern Daylight Time
    // We will need to convert this format to MM/DD/YYYY using js function
    // getfullYear(), getfullDate(), getfullmonth()...
      var request = {
        sessionID: this.$store.getters.getSession,
        firstName: this.firstName,
        lastName: this.lastName,
        DOB: this.DOB,
        phoneNumber: this.phoneNumber,
      };
      console.log(request);

      this.$http
        .post("https://smart-copd-patient.herokuapp.com/register", request)
        .then(
          (response) => {
            this.someData = response.body;
            console.log(this.someData);
            console.log(this.someData.mStatus);
            console.log(this.someData.mExists);
            console.log(!this.someData.mExists);
            if (this.someData.mStatus == "ok") {
              console.log("it worked");
              this.$router.push({ name: "Patient" });
            } else {
              alert("Error with submission, returning to home");
              this.$router.push({ name: "Home" });
            }
          },
          (response) => {
            console.log(reponse.mStatus);
          }
        );
    },
  },
};
</script>

<style>
.uinput {
    width: 220px
}
</style>