<template>
  <div>
    <img src="@/assets/logo.jpg" id="logo" />
    <h3 id="text">Please Complete the Form below to Sign up</h3>
    <el-card class="box-card">
      <el-form ref="form" label-width="120px">
        <el-form-item label="First Name: ">
          <el-input v-model="firstName" class="uinput"></el-input>
        </el-form-item>

        <el-form-item label="Last Name: ">
          <el-input v-model="lastName" class="uinput"></el-input>
        </el-form-item>

        <!-- NOTE this needs to parse the value before sending it --->
        <template v-if="isPatient">
          <el-form-item label="Date of Birth: ">
            <el-date-picker
              v-model="DOB"
              type="date"
              placeholder="Please select your DOB"
              default-value="1990-10-01"
            >
            </el-date-picker>
          </el-form-item>
        </template>

        <template v-if="isHealthCare">
          <el-form-item label = "License Number: ">
            <el-input v-model="licenseNumber" class="uinput"></el-input>
          </el-form-item>
        </template>

        <el-form-item label="Phone Number: ">
          <el-input v-model="phoneNumber" class="uinput"></el-input>
        </el-form-item>
        <template v-if="isPatient">
        <el-button type="primary" @click="processForm">Submit</el-button>
        </template>
        <template v-if="isHealthCare">
        <el-button type="warning" @click="processForm">Submit</el-button>
        </template>
        <!-- TODO: Cancel will need to sign out the user -->
        <el-button>Cancel</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Register",
  components: {},
  data() {
    return {
      firstName: "",
      lastName: "",
      DOB: "",
      phoneNumber: "",
      licenseNumber: "",
      temp: "",
      isPatient: (this.$cookies.get("isPatient")) === 'true',
      isHealthCare: (this.$cookies.get("isHealthCare")) === 'true',
    };
  },
  methods: {
    processForm() {
      if (this.firstName.length == 0) {
        this.$notify.error({
          title: "Form Error",
          message: "Please enter a valid first name",
        });
      } else if (this.lastName.length == 0) {
        this.$notify.error({
          title: "Form Error",
          message: "Please enter a valid last name",
        });
      } else if (this.phoneNumber.length != 10) {
        this.$notify.error({
          title: "Form Error",
          message: "Please enter a valid phone number",
        });
      } else if (this.isHealthCare && this.licenseNumber.length == 0) {
        console.log("Send an error for less length");
        this.$notify.error({
          title: "Form Error",
          message: "Please enter a valid license number",
        });
      } else if (this.isPatient) {
        if (this.DOB.getMonth() + 1 < 10) {
          this.temp = this.DOB.getMonth() + 1;
          this.temp = "0" + this.temp;
        } else {
          this.temp = this.DOB.getMonth() + 1;
        }
      }
        console.log("Sanity check");
        //construct the request
        if (this.isPatient) {
          console.log("entered patients area");
          var request = {
            sessionID: this.$store.getters.getSessionID,
            firstName: this.firstName,
            lastName: this.lastName,
            DOB: this.DOB,
            phoneNumber: this.phoneNumber,
          };
          this.$http
          .post("https://smart-copd-patient.herokuapp.com/register", request)
          .then((response) => {
            if (response.body.mStatus == "ok") {
              this.$cookies.set("sessionID", this.$store.getters.getSessionID);
              this.$cookies.set("firstName", this.firstName);
              this.$cookies.set("lastName", this.lastName);
              this.$cookies.set("DOB", this.DOB);
              this.$cookies.set("phoneNumber", this.phoneNumber);
              this.$router.push("patients");
            } else {
              this.$notify.error({
                title: "Error Registering",
                message: "Please try again",
              });
							this.$router.push("/")
            }
          });
        } else {
          console.log("making request to server for hp");
          var request = {
            sessionID: this.$store.getters.getSessionID,
            firstName: this.firstName,
            lastName: this.lastName,
            phoneNumber: this.phoneNumber,
            licenseNumber: this.licenseNumber,
          };
          console.log(request);
          this.$http
          .post("https://smart-copd-patient.herokuapp.com/healthcareregister", request)
          .then((response) => {
            console.log(response.body);
            if (response.body.mStatus == "ok") {
              this.$cookies.set("sessionID", this.$store.getters.getSessionID);
              this.$cookies.set("firstName", this.firstName);
              this.$cookies.set("lastName", this.lastName);
              this.$cookies.set("phoneNumber", this.phoneNumber);
              this.$cookies.set("licenseNumber", this.licenseNumber);
              this.$router.push("healthcares");
            } else {
              this.$notify.error({
                title: "Error Registering",
                message: "Please try again",
              });
							this.$router.push("/");
            }
          });
      }
    },
  },
};
</script>

<style>
.box-card {
  width: 460px;
  margin: 0 auto;
  margin-top: 50px;
  padding: 20px;
}
#logo {
  width: 90px;
  height: 90px;
  display: block;
  margin-left: auto;
  margin-right: auto;
  margin-top: 80px;
  margin-bottom: 10px;
}
#text {
  text-align: center;
  margin-bottom: 20px;
}
.uinput {
  width: 220px;
}
</style>