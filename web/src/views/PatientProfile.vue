<template>
  <div>
    <PatientHeader option="1"></PatientHeader>
    <el-descriptions title="Profile" :column="2" border id="profilecontent">
      <template slot="extra">
        <el-button type="primary" size="medium" @click="open">Edit</el-button>
      </template>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          First Name
        </template>
        {{ firstName }}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          Last Name
        </template>
        {{ lastName }}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          Date of Birth
        </template>
        {{ DOB }}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          Phone Number
        </template>
        {{ phoneNumber }}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          Email
        </template>
        {{ email }}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          Role
        </template>
        <el-tag size="medium">Patient</el-tag>
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
import PatientHeader from '@/components/PatientHeader.vue'
export default {
  data() {
    return {
      firstName: "loading...",
      lastName: "loading...",
      DOB: "loading...",
      phoneNumber: "loading...",
      email: "loading...",
    };
  },
  components: {
      PatientHeader,
  },
  methods: {
      open() {
        this.$alert('This functionality is not yet implemented. You catch us.', 'Oops', {
          confirmButtonText: 'OK',
        });
      }
  },
  mounted() {
    if (this.$cookies.isKey("email")) {
      this.firstName = this.$cookies.get("firstName");
      this.lastName = this.$cookies.get("lastName");
      this.DOB = this.$cookies.get("DOB");
      console.log(this.$cookies.get("DOB"));
      this.phoneNumber = this.$cookies.get("phoneNumber");
      this.email = this.$cookies.get("email");
    } else {
      this.$http
      .get(
        "https://smart-copd-patient.herokuapp.com/patient/" +
          this.$store.getters.getSessionID
      )
      .then((response) => {
        this.firstName = response.data.mData.pFirstName;
        this.lastName = response.data.mData.pLastName;
        this.DOB = response.data.mData.pDOB.slice(0, 10);
        this.phoneNumber = response.data.mData.pPhoneNumber;
        this.email = response.data.mData.pPatientID;
        this.$cookies.set("firstName", this.firstName);
        this.$cookies.set("lastName", this.lastName);
        this.$cookies.set("DOB", this.DOB);
        this.$cookies.set("phoneNumber", this.phoneNumber);
        this.$cookies.set("email", this.email);
      });
    }
  },
};
</script>

<style>
#profilecontent {
  margin: 50px;
}
</style>