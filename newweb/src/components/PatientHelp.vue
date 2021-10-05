<template>
  <div id='help'>
    <div>

    </div>
    <div>
      <h3 style="text-align: center">Contact us for non-emergency</h3>
      <el-form ref="form" :model="form" label-width="auto">
        <el-form-item label="Share my: ">
          <el-checkbox-group v-model="form.type">
            <el-checkbox label="Name and Contacts" name="type"></el-checkbox>
            <el-checkbox label="Biodata" name="type"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="Message: ">
          <el-input type="textarea" v-model="form.desc"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :plain="true" @click="onSubmit">Submit</el-button>
          
          <el-button @click="onClear">Clear</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "PatientHelp",
  data() {
    return {
      form: {
        type: [],
        desc: "",
      },
    };
  },
  methods: {
    onSubmit() {
      console.log("submit!");
      this.$message({
          message: 'Message Successfully Sent',
          type: 'success'
        });
      this.onClear();
    },
    onClear() {
      this.form.type = [];
      this.form.desc = "";
    },
  },
};
</script>

<style>
#help {
    text-align: left;
}
</style>