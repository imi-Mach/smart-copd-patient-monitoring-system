

<template>
  <div id="root">
    <span>View All Data</span>
    <el-button style="float: right; padding: 10px 0" type="text" @click="refresh"
      >Refresh</el-button
    >
    <el-divider></el-divider>
    <el-table :data="tableData" style="width: 900px; margin: 0 auto">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="..experience more shortness of breath?">
              <span>{{ props.row.q1 }}</span>
            </el-form-item>
            <el-form-item
              label="..experience more fear because of your shortness of breath?"
            >
              <span>{{ props.row.q2 }}</span>
            </el-form-item>
            <el-form-item label="..experience more fatigue?">
              <span>{{ props.row.q3 }}</span>
            </el-form-item>
            <el-form-item
              label="..feel more hindered by your COPD during your daily activities?"
            >
              <span>{{ props.row.q4 }}</span>
            </el-form-item>
            <el-form-item label="..experience more sputum in your airway?">
              <span>{{ props.row.q5 }}</span>
            </el-form-item>
            <el-form-item
              label="..notice any difference in your sputum color/composition?"
            >
              <span>{{ props.row.q6 }}</span>
            </el-form-item>
            <el-form-item label="..experience more wheezing?">
              <span>{{ props.row.q7 }}</span>
            </el-form-item>
            <el-form-item label="..experience more coughing?">
              <span>{{ props.row.q8 }}</span>
            </el-form-item>
            <el-form-item label="..have a sore throat?">
              <span>{{ props.row.q9 }}</span>
            </el-form-item>
            <el-form-item label="..have a cold or a runny nose?">
              <span>{{ props.row.q10 }}</span>
            </el-form-item>
            <el-form-item label="..experience more stress or tension?">
              <span>{{ props.row.q11 }}</span>
            </el-form-item>
            <el-form-item label="..have used more of your bronchodilators?">
              <span>{{ props.row.q12 }}</span>
            </el-form-item>
            <el-form-item label="Body Temperature (Degrees Celsius)">
              <span>{{ props.row.bt }}</span>
            </el-form-item>
            <el-form-item label="FEV1 (Liters)">
              <span>{{ props.row.fev1 }}</span>
            </el-form-item>
            <el-form-item label="SpO2 (Percentages)">
              <span>{{ props.row.spo2 }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="Date" width="450"> 2021-10-12 </el-table-column>
      <el-table-column label="Status" width="450">
        <el-tag type="success">Good</el-tag>
      </el-table-column>
    </el-table>
  </div>
</template>

<style>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 400px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>

<script>
export default {
  data() {
    return {
      tableData: [
      ],
    };
    
  },
  mounted() {
    this.refresh();
  },
  methods: {
      refresh(){
          this.$http
      .get(
        "https://smart-copd-patient.herokuapp.com/myData/" +
          this.$store.getters.getSessionID
      )
      .then((response) => {
        this.tableData = response.body.mData;
        this.tableData = this.tableData.reverse();
        this.tableData.forEach(element => {
            if(element.q1 == "0"){
                element.q1 = "Yes";
            }
            else{
                element.q1 = "No";
            }
            if(element.q2 == "0"){
                element.q2 = "Yes";
            }
            else{
                element.q2 = "No";
            }
            if(element.q3 == "0"){
                element.q3 = "Yes";
            }
            else{
                element.q3 = "No";
            }
            if(element.q4 == "0"){
                element.q4 = "Yes";
            }
            else{
                element.q4 = "No";
            }
            if(element.q5 == "0"){
                element.q5 = "Yes";
            }
            else{
                element.q5 = "No";
            }
            if(element.q6 == "0"){
                element.q6 = "Yes";
            }
            else{
                element.q6 = "No";
            }
            if(element.q7 == "0"){
                element.q7 = "Yes";
            }
            else{
                element.q7 = "No";
            }
            if(element.q8 == "0"){
                element.q8 = "Yes";
            }
            else{
                element.q8 = "No";
            }
            if(element.q9 == "0"){
                element.q9 = "Yes";
            }
            else{
                element.q9 = "No";
            }
            if(element.q10 == "0"){
                element.q10 = "Yes";
            }
            else{
                element.q10 = "No";
            }
            if(element.q11 == "0"){
                element.q11 = "Yes";
            }
            else{
                element.q11 = "No";
            }
            if(element.q12 == "0"){
                element.q12 = "Yes";
            }
            else{
                element.q12 = "No";
            }
        })
      });
      }
  }
};
</script>