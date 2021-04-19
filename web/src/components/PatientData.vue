<template>
  <div>
    <div>
      <h3 style="text-align: center">All Data</h3>
    </div>
    <el-table :data="biodata" height="350" border stripe style="width: 100%">
      <el-table-column prop="date" label="Date" width="180"> </el-table-column>
      <el-table-column prop="hr" label="Heart Rate (bpm)" width="180">
      </el-table-column>
      <el-table-column prop="spo2" label="SpO2 (%)"> </el-table-column>
      <el-table-column prop="rr" label="Respiration Rate (bpm)" width="180">
      </el-table-column>
      <el-table-column prop="rl" label="Risk Level (5-high; 1-low)" width="180">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    biodata: Array
  }
}
</script>
