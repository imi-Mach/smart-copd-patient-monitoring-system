<template>
  <div>
    <div id="options">
      <el-button
        icon="el-icon-circle-plus-outline"
        type="warning"
        plain
        @click="addPatient"
        id="addp"
      >
        Add a New Patient
      </el-button>
    </div>
    <el-table ref="filterTable" :data="tableData" border style="width: 100%">
      <el-table-column prop="name" label="Name" sortable></el-table-column>
      <!-- <el-table-column prop="address" label="地址" :formatter="formatter"> -->
      <!-- </el-table-column> -->
      <el-table-column
        prop="tag"
        label="Risk Level"
        :filters="[
          { text: 'Normal', value: '0' },
          { text: 'Warning', value: '1' },
          { text: 'Danger', value: '2' },
        ]"
        :filter-method="filterTag"
        filter-placement="bottom-end">

        <template slot-scope="scope">
          <el-tag :type="whichTag(scope.row.tag)" disable-transitions>{{ whichTagDisplay(scope.row.tag) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Operations">
        <el-button type="info" round plain icon="el-icon-info"
          >Detail</el-button
        >
        <el-button type="danger" round plain icon="el-icon-warning"
          >Delete Patient</el-button
        ></el-table-column
      >
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [
        {
          name: "Thomas",
          tag: "0",
        },
        {
          name: "Max",
          tag: "0",
        },
        {
          name: "Brian",
          tag: "1",
        },
        {
          name: "Thanos",
          tag: "2",
        },
      ],
      nameSearch: "",
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
      if (tag === "0") {
        return "success";
      } else if (tag === "1") {
        return "warning";
      } else {
        return "danger";
      }
    },
    whichTagDisplay(tag) {
      if (tag === "0") {
        return "Normal";
      } else if (tag === "1") {
        return "Warning";
      } else {
        return "Danger";
      }
    },

    addPatient() {
      this.$prompt("Please Enter Patient's Email", "Add a New Patient", {
        confirmButtonText: "Confirm",
        cancelButtonText: "Cancel",
        inputPattern:
          /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
        inputErrorMessage: "Please Enter a Valid Email",
      })
        .then(({ value }) => {
          this.$message({
            type: "success",
            message: "Adding " + value,
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "Cancelled",
          });
        });
    },
  },
};
</script>

<style>
#options {
  padding: 15px;
}
/* #addp{
    text-align: left;
} */
/* #caf{
    position: relative;
    right: 500px;
}  */
</style>