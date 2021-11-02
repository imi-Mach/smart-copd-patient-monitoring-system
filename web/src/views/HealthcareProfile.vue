<template>
  <div>
    <HealthcareHeader></HealthcareHeader>
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
      open() {
        this.$alert('This functionality is not yet implemented. You catch us.', 'Oops', {
          confirmButtonText: 'OK',
        });
      }
  },
  mounted: {
    // get healthcare provider data
  },
  
};
</script>

<style>
#profilecontent {
  margin: 50px;
}
</style>