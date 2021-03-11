<template>
  <div>
    <form id="newReport" onsubmit="return false">
      <label for="reason">Reason for reporting</label>
      <v-select
        id="reason"
        v-model="reason"
        name="reason"
        :options="options"
        :reduce="(label) => label.code"
        required
        form="newReport"
        @keyup="formError = ''">
      </v-select>

      <label for="report">Report (optional)</label>
      <textarea class="form-control" id="report" v-model="report"></textarea>

      <button
        type="button"
        id="submitDetails"
        name="submitDetails"
        @click="addReport"
      >
        Report
      </button>

      <h6 id="error">Fill in all fields to submit form.</h6>
    </form>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import PostsDataService from "../services/PostsDataService";

interface Report {
  reviewId?: number;
  id?: number;
  reason: string;
  report: string;
  date?: string;
}

@Component
export default class AddReport extends Vue {
  $cookies: any;

  private options = [
    { label: "It's inappropriate", code: "inappropriate" },
    { label: "Spammer no spamming", code: "spam" },
    { label: "Other (please explain)", code: "other" },
  ];

  private reviewId = this.$cookies.get("review-id");
  private fullReport: Report[] = []; // TEST
  private report: string = "";
  private reason: string = "";
  private formError: string = "";

  public addReport(): void {
    const newReport: Report = {
      reviewId: this.reviewId,
      reason: this.reason,
      report: this.report,
    };

    console.log("Report", newReport);

    PostsDataService.createReport(newReport)
      .then((response) => {
        window.history.back();
      })
      .catch((err) => {
        this.formError = err.reponse.statusText;
      });

    if (!this.reason) {
      if (document.getElementById("error")) {
        document.getElementById("error")!.style.visibility = "visible";
      }
    }
  }
}
</script>

<style scoped>
/* form {
      display: grid;
      grid-template: repeat(4, 40px 30px) / repeat(2, 300px);
      grid-column-gap: 20px;
      grid-row-gap: 5px;
  } */
#reason {
  width: 100%;
}
#submitDetails {
  width: 100%;
  /* grid-row: 10 / 11;
      grid-column: 1 / 3; */
  background-color: #4987e8;
  color: white;
  font-family: "Roboto Slab", serif;
  font-weight: 700;
  border-radius: 10px;
  border-color: #93b7f0;
  margin-top: 20px;
  height: 40px;
  text-transform: uppercase;
  letter-spacing: 2px;
  font-size: 20px;
  padding: auto auto;
}
#submitDetails:hover {
  background-color: #6399ee;
}
#report {
  width: 100%;
  height: 20vmin;
  /* grid-row: 4 / 6;
      grid-column: 1 / 3; */
  padding: 0;
}
textarea {
  resize: none;
}
label {
  margin-top: 20px;
  font-family: "Roboto Slab", serif;
  font-weight: 700;
}
#error {
  color: red;
  font-weight: 700;
  font-family: "Roboto Slab", serif;
  grid-row: 12 / 13;
  grid-column: 1 / 3;
  text-align: center;
  visibility: hidden;
}
</style>
