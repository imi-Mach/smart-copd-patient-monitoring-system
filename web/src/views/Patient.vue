<template>
  <el-container>
    <el-header height="80px">
      <patient-header></patient-header>
    </el-header>
    <el-container>
      <el-main>
        <patient-main></patient-main>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
// @ is an alias to /src
import PatientHeader from '@/components/PatientHeader.vue'
import PatientMain from '@/components/PatientMain.vue'

export default {
  name: 'Home',
  components: {
    PatientHeader,
    PatientMain

  }
}
</script>