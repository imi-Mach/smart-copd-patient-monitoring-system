<template>
  <div>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>All Data</span>
        <el-button style="float: right; padding: 3px 0" type="text"
          >Do not show normal data (....?change this haha)</el-button
        >
      </div>

      <el-table :data="biodata" height="auto" border stripe style="width: 80%; margin:0 auto;">
        <el-table-column prop="date" label="Date" width="180">
        </el-table-column>
        <el-table-column prop="hr" label="Temperature" >
        </el-table-column>
        <el-table-column prop="spo2" label="SpO2 (%)"> </el-table-column>
        <el-table-column prop="spo2" label="Fev1"> </el-table-column>
        <el-table-column label="">
          <el-button type="text">Show Detail</el-button></el-table-column
        >
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "HelloWorld",
  props: {
    biodata: Array,
  },
};
</script>
