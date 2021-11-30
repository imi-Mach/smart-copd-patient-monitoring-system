<template>
  <div>
    <HealthcareHeader></HealthcareHeader>
    <el-descriptions title="Profile" :column="2" border id="profilecontent">
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
          Work Phone Number
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
        <el-tag size="medium" type="warning">{{ role }}</el-tag>
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
import HealthcareHeader from '@/components/HealthcareHeader.vue'
export default {
  data() {
    return {
      firstName: "loading...",
      lastName: "loading...",
      phoneNumber: "loading...",
      email: "loading...",
      role: "Healthcare"
    };
  },
  components: {
      HealthcareHeader,
  },
  methods: {
  },
  mounted() {
    // get healthcare provider data
    if (this.$cookies.isKey("email")) {
      this.firstName = this.$cookies.get("firstName");
      this.lastName = this.$cookies.get("lastName");
      this.phoneNumber = this.$cookies.get("phoneNumber");
      this.email = this.$cookies.get("email");
    } else {
      // Route needs to be fixed
      this.$http
      .get("https://smart-copd-patient.herokuapp.com/healthcareProfile/" + 
        this.$store.getters.getSessionID
      )
      .then((response) => {
        this.firstName = response.data.mData.hFirstName;
        this.lastName = response.data.mData.hLastName;
        this.phoneNumber = response.data.mData.hPhoneNumber;
        this.email = response.data.mData.hHealthCareID;
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