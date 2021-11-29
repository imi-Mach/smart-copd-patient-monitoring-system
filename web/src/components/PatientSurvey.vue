<template>
  <el-form ref="form" :model="form" label-width="480px" label-position="left">
    <el-divider></el-divider>
    <span style="font-weight: bold"
      >In comparison to normal, do you today ...</span
    >
    <el-divider></el-divider>

    <el-form-item label="..experience more shortness of breath?">
      <el-radio-group v-model="form.q1">
        <el-radio label="Yes"></el-radio>
        <el-radio label="No"></el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item
      label="..experience more fear because of your shortness of breath?"
    >
      <el-radio-group v-model="form.q2">
        <el-radio label="Yes"></el-radio>
        <el-radio label="No"></el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="..experience more fatigue?">
      <el-radio-group v-model="form.q3">
        <el-radio label="Yes"></el-radio>
        <el-radio label="No"></el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item
      label="..feel more hindered by your COPD during your daily activities?"
    >
      <el-radio-group v-model="form.q4">
        <el-radio label="Yes"></el-radio>
        <el-radio label="No"></el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="..experience more sputum in your airway?">
      <el-radio-group v-model="form.q5">
        <el-radio label="Yes"></el-radio>
        <el-radio label="No"></el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item
      label="..notice any difference in your sputum color/composition?"
    >
      <el-radio-group v-model="form.q6">
        <el-radio label="Yes"></el-radio>
        <el-radio label="No"></el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="..experience more wheezing?">
      <el-radio-group v-model="form.q7">
        <el-radio label="Yes"></el-radio>
        <el-radio label="No"></el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="..experience more coughing?">
      <el-radio-group v-model="form.q8">
        <el-radio label="Yes"></el-radio>
        <el-radio label="No"></el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="..have a sore throat?">
      <el-radio-group v-model="form.q9">
        <el-radio label="Yes"></el-radio>
        <el-radio label="No"></el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="..have a cold or a runny nose?">
      <el-radio-group v-model="form.q10">
        <el-radio label="Yes"></el-radio>
        <el-radio label="No"></el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="..experience more stress or tension?">
      <el-radio-group v-model="form.q11">
        <el-radio label="Yes"></el-radio>
        <el-radio label="No"></el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="..have used more of your bronchodilators?">
      <el-radio-group v-model="form.q12">
        <el-radio label="Yes"></el-radio>
        <el-radio label="No"></el-radio>
      </el-radio-group>
    </el-form-item>

    <el-divider></el-divider>
    <span style="font-weight: bold">Physiologic measurements</span>
    <br />
    <span>Please select your devise for the measurements: </span>

    <el-dropdown @command="handleCommand">
      <span class="el-dropdown-link">
        {{ dropdownContent }} <i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="a">Manual Input</el-dropdown-item>
        <el-dropdown-item command="b">Res Pi V1</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

    <el-divider></el-divider>

    <el-form-item label="Body Temperature in Degrees Celsius">
      <el-input v-model="form.bt" :disabled="disableinput"></el-input>
    </el-form-item>
    <el-form-item label="FEV1 in Liters">
      <el-input v-model="form.fev1" :disabled="disableinput"></el-input>
    </el-form-item>
    <el-form-item label="SpO2 in Percentages">
      <el-input v-model="form.spo2" :disabled="disableinput"></el-input>
    </el-form-item>

    <el-upload
      class="upload-demo"
      drag
      action="https://jsonplaceholder.typicode.com/posts/"
      accept="text/plain"
      :before-upload="onBeforeUpload"
      :on-remove="handleRemove"
      :file-list="fileList"
      multiple
      :limit="3"
      :on-exceed="handleExceed"
      v-if="respiSelected"
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">
        Drop file here or <em>click to upload</em>
      </div>
      <div class="el-upload__tip" slot="tip">
        Only .txt file can be accepted
      </div>
    </el-upload>
    <br />
    <div id="submitB">
      <el-button type="primary" @click="onSubmit">Submit</el-button>
      <el-button @click="onClear">Clear</el-button>
    </div>
  </el-form>
</template>

<script>
export default {
  name: "PatientSurvey",
  data() {
    return {
      disableinput: true,
      respiSelected: false,
      dropdownContent: "Select",
      form: {
        q1: "",
        q2: "",
        q3: "",
        q4: "",
        q5: "",
        q6: "",
        q7: "",
        q8: "",
        q9: "",
        q10: "",
        q11: "",
        q12: "",
        bt: "",
        fev1: "",
        spo2: "",
      },
    };
  },
  methods: {
    onBeforeUpload(file) {
      const isText = file.type === 'text/plain';
      if (!isText) {
        this.$message({
                message: "Upload file can only be in text format.",
                type: "error",
        });
      } else {
        var reader = new FileReader();
        reader.onload = (e) => {
          var text = e.target.result;
          var array = text.split('\n');
          var dataArray = array[1].split(',');
          this.form.bt = dataArray[0];
          this.form.spo2 = dataArray[1];
          this.form.fev1 = dataArray[2];
        };
        reader.readAsText(file);
      }
    },
    handleRemove(file, fileList) {
      let resultArr = this.fileData.filter((item) => {
        return item.url === file.url
      });
      console.log(fileList);
      this.dataForm.id = resultArr[0].id
      this.$nextTick(() => {
        this.deleteHandle(this.dataForm.id)
      })
    },
    handleExceed() {
      this.$message({
        message: "Please limit your file uploads to 3 files at a time.",
        type: "error",
      });
    },
    handleCommand(command) {
      if (command == "a") {
        this.dropdownContent = "Manual Input";
        this.disableinput = false;
        this.respiSelected = false;
      } else if (command == "b") {
        this.dropdownContent = "Ras Pi";
        this.disableinput = true;
        this.respiSelected = true;
      }
    },
    onSubmit() {
      if (
        this.form.q1 &&
        this.form.q2 &&
        this.form.q3 &&
        this.form.q4 &&
        this.form.q5 &&
        this.form.q6 &&
        this.form.q7 &&
        this.form.q8 &&
        this.form.q9 &&
        this.form.q10 &&
        this.form.q11 &&
        this.form.q12 &&
        this.form.bt &&
        this.form.fev1 &&
        this.form.spo2
      ) {
        // sending request
        var request = {
          sessionID: this.$store.getters.getSessionID,
          q1: this.form.q1,
          q2: this.form.q2,
          q3: this.form.q3,
          q4: this.form.q4,
          q5: this.form.q5,
          q6: this.form.q6,
          q7: this.form.q7,
          q8: this.form.q8,
          q9: this.form.q9,
          q10: this.form.q10,
          q11: this.form.q11,
          q12: this.form.q12,
          bt: this.form.bt,
          fev1: this.form.fev1,
          spo2: this.form.spo2,
        };

        if (request.q1 == "Yes") {
          request.q1 = "0";
        } else {
          request.q1 = "1";
        }
        if (request.q2 == "Yes") {
          request.q2 = "0";
        } else {
          request.q2 = "1";
        }
        if (request.q3 == "Yes") {
          request.q3 = "0";
        } else {
          request.q3 = "1";
        }
        if (request.q4 == "Yes") {
          request.q4 = "0";
        } else {
          request.q4 = "1";
        }
        if (request.q5 == "Yes") {
          request.q5 = "0";
        } else {
          request.q5 = "1";
        }
        if (request.q6 == "Yes") {
          request.q6 = "0";
        } else {
          request.q6 = "1";
        }
        if (request.q7 == "Yes") {
          request.q7 = "0";
        } else {
          request.q7 = "1";
        }
        if (request.q8 == "Yes") {
          request.q8 = "0";
        } else {
          request.q8 = "1";
        }
        if (request.q9 == "Yes") {
          request.q9 = "0";
        } else {
          request.q9 = "1";
        }
        if (request.q10 == "Yes") {
          request.q10 = "0";
        } else {
          request.q10 = "1";
        }
        if (request.q11 == "Yes") {
          request.q11 = "0";
        } else {
          request.q11 = "1";
        }
        if (request.q12 == "Yes") {
          request.q12 = "0";
        } else {
          request.q12 = "1";
        }
        request.bt = request.bt.toString();
        request.fev1 = request.fev1.toString();
        request.spo2 = request.spo2.toString();

        console.log(JSON.stringify(request));
        this.$http
          .post("https://smart-copd-patient.herokuapp.com/insertData", request)
          .then((response) => {
            if (response.body.mStatus == "ok") {
              console.log(response.body);
              this.$message({
                message: "Successfully submitted!",
                type: "success",
              });
              this.onClear();
              var data = {
                q1: request.q1,
                q2: request.q2,
                q3: request.q3,
                q4: request.q4,
                q5: request.q5,
                q6: request.q6,
                q7: request.q7,
                q8: request.q8,
                q9: request.q9,
                q10: request.q10,
                q11: request.q11,
                q12: request.q12,
                bt: request.bt,
                fev1: request.fev1,
                spo2: request.spo2,
              };
              var data_str = JSON.stringify(data); // to de-string: JSOIN.parse(data_str)
              var index = this.$counter % 7;
              switch (index) {
                case 0:
                  this.$cookies.set("data0", data_str);
                  console.log("Set Cookie 0");
                  break;
                case 1:
                  this.$cookies.set("data1", data_str);
                  console.log("Set Cookie 1");
                  break;
                case 2:
                  this.$cookies.set("data2", data_str);
                  console.log("Set Cookie 2");
                  break;
                case 3:
                  this.$cookies.set("data3", data_str);
                  console.log("Set Cookie 3");
                  break;
                case 4:
                  this.$cookies.set("data4", data_str);
                  console.log("Set Cookie 4");
                  break;
                case 5:
                  this.$cookies.set("data5", data_str);
                  console.log("Set Cookie 5");
                  break;
                case 6:
                  this.$cookies.set("data6", data_str);
                  console.log("Set Cookie 6");
                  break;
                default:
                  console.log("THERE WAS AN ERROR");
                  break;
              }
              this.$counter++;
            } else {
              this.$message({
                message: "Error, please try again.",
                type: "error",
              });
              window.location.reload();
            }
          });
      } else {
        this.$message({
          message: "Please complete all questions",
          type: "error",
        });
      }
    },
    onClear() {
      this.form.q1 = "";
      this.form.q2 = "";
      this.form.q3 = "";
      this.form.q4 = "";
      this.form.q5 = "";
      this.form.q6 = "";
      this.form.q7 = "";
      this.form.q8 = "";
      this.form.q9 = "";
      this.form.q10 = "";
      this.form.q11 = "";
      this.form.q12 = "";
      this.form.bt = "";
      this.form.fev1 = "";
      this.form.spo2 = "";
    },
  },
};
</script>

<style>
#submitB {
  margin: auto;
  width: 30%;
}
.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>

