<template>
  <div>
    <!-- the first button at the top -->
    <div id="options">
      <el-button
        icon="el-icon-circle-plus-outline"
        type="warning"
        plain
        @click="addPatient"
      >
        Add a New Patient
      </el-button>
      <el-button icon="el-icon-refresh" type="info" @click="refreshTable">
        Refresh
      </el-button>
    </div>

    <!-- the whole table -->
    <el-table ref="filterTable" :data="patientProfiles" border style="width: 100%">
      <!-- name -->
      <el-table-column prop="name" label="Name" sortable>
        <template slot-scope="scope">
          <el-popover placement="top-start" trigger="hover" width="300">
            <el-descriptions title="Patient Profile" :column="numCol">
              <el-descriptions-item label="Date of Birth">{{
                scope.row.pDOB
              }}</el-descriptions-item>
              <el-descriptions-item label="Email">{{
                scope.row.pPatientID
              }}</el-descriptions-item>

              <el-descriptions-item label="phoneNumber">{{
                scope.row.pPhoneNumber
              }}</el-descriptions-item>
            </el-descriptions>
            <span slot="reference">{{ scope.row.name }}</span>
          </el-popover>
        </template>
      </el-table-column>


      <!-- tag -->
      <el-table-column
        prop="pRiskLevel"
        label="Risk Level"
        :filters="[
          { text: 'Normal', value: '0' },
          { text: 'Warning', value: '1' },
          { text: 'Danger', value: '2' },
        ]"
        :filter-method="filterTag"
        filter-placement="bottom-end"
      >
        <template slot-scope="scope">
          <el-tag :type="whichTag(scope.row.pRiskLevel)" disable-transitions>
            {{ whichTagDisplay(scope.row.pRiskLevel) }}
          </el-tag>
        </template>
      </el-table-column>

      <!-- operations -->
      <el-table-column label="Operations">
        <template slot-scope="scope">
          <!-- the detail button contains a table in the dialog -->
          <el-button
            type="info"
            round
            plain
            icon="el-icon-info"
            @click="dialogTableVisible = true"
          >
            Detail
          </el-button>

          <el-dialog title="Patient Data" width = 900px :visible.sync="dialogTableVisible">
            <el-table
              :data="scope.row.data"
              style="width: 900px; margin: 0 auto"
            >
              <el-table-column type="expand">
                <template slot-scope="props">
                  <el-form
                    label-position="left"
                    inline
                    class="demo-table-expand"
                  >
                    <el-form-item
                      label="..experience more shortness of breath?"
                    >
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
                    <el-form-item
                      label="..experience more sputum in your airway?"
                    >
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
                    <el-form-item
                      label="..have used more of your bronchodilators?"
                    >
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
              <el-table-column label="Date" width="450" prop="time"></el-table-column>
              <el-table-column label="Status" width="450">
                <el-tag type="success">Good</el-tag>
              </el-table-column>
            </el-table>
          </el-dialog>

          <el-button
            type="danger"
            round
            plain
            icon="el-icon-warning"
            @click="deletePatient(scope.row.pPatientID)"
            
          >
            Delete Patient
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      numCol: 1,
      dialogTableVisible: false,
      nameSearch: "",
      patientProfiles: [], 
    };
  },
  methods: {
    clearFilter() {
      this.$refs.filterTable.clearFilter();
    },
    // formatter(row, column) {
    //   return row.address;
    // },
    filterTag(value, row) {
      return row.tag === value;
    },
    whichTag(tag) {
      if (tag == "0") {
        return "success";
      } else if (tag == "1") {
        return "warning";
      } else if (tag == "2"){
        return "danger";
      }
      return "info";
    },
    whichTagDisplay(tag) {
      if (tag == "0") {
        return "Normal";
      } else if (tag == "1") {
        return "Warning";
      } else if (tag == "2"){
        return "Danger";
      }
      return "No info"
    },

    addPatient() {
      this.$prompt("Please Enter Patient's Email", "Add a New Patient", {
        confirmButtonText: "Confirm",
        cancelButtonText: "Cancel",
        inputPattern:
          /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
        inputErrorMessage: "Please Enter a Valid Email",
      })
        .then((value) => {
          var request = {
            patientEmail: value.value,
            sessionID: this.$store.getters.getSessionID,
          };
          console.log(request)
          this.$http.post("https://smart-copd-patient.herokuapp.com/insertPatientOf", request)
          .then((response) => {
            console.log('adding patient route called');
            console.log(response);
            if ( ! response.body.mExists) {
              console.log('adding patient');
              this.$message({
                type: "success",
                message: "Patient added.",
              });
              this.getPatientsProfile()
            } else {
              console.log('patient already exist');
              this.$message({
                type: "error",
                message: "The provided patient: " + value + " was not found",
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "Cancelled",
          });
        });
    },
    deletePatient(entry) {
      console.log(entry)
      // request to backend
      this.$http.delete("https://smart-copd-patient.herokuapp.com/deletePatient/" + entry)
      .then((response) => {
        // console.log('deleting route called');
        console.log(response);
        if (response.status == 200) {
          // var index = this.res.indexOf(entry);
          // this.res.splice(index, 1);
          this.$message({
            type: "success",
            message: "Patient Deleted"
          });
          this.getPatientsProfile()
        } else {
          this.$message({
            type: "error",
            message: "Failed to delete",
          });
        }
      })
      
    },
    refreshTable() {
      // refresh table, resend the request
      this.getPatientsProfile();
    },
    getPatientsProfile(){
      this.$http
        .get(
          "https://smart-copd-patient.herokuapp.com/getAllPatients/" +
            this.$store.getters.getSessionID
        ).then((response) => {
          
          this.patientProfiles = response.body.mData
          //console.log(this.patientProfiles)
          this.patientProfiles.forEach((element) => {
            element.name = element.pFirstName + ' ' + element.pLastName;
          })
          console.log(this.patientProfiles)
        })
    },
  },
  mounted(){
    this.getPatientsProfile();
  },
};
</script>

<style>
#options {
  padding-bottom: 20px;
  padding-right: 10px;
  text-align: right;
}
/* #addp{
    text-align: left;
} */
/* #caf{
    position: relative;
    right: 500px;
}  */
#mydetail{
  padding-left: 10px;
}
</style>