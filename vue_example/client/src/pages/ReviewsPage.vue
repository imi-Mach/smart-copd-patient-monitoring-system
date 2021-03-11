<template>
    <div class="page page--about">
        <router-link class="back-home" to="/sprint3">Back Home</router-link>
        <h1>{{ $route.params.course }} Reviews</h1>
        <div class='no-reviews' v-if='reviews.length == 0'>
          <br>
          <h6>No reviews for this class.</h6>
          <router-link to="/new-review">Write a review >></router-link>
        </div>
        <div class='no-reviews' v-if='reviews.length !== 0'>
          <router-link to="/new-review">Write a review >></router-link>
        </div>
        <div class='review-list' v-for="review in reviews">
          <div class="card my-5">
            <div class="card-header">
              <h5 v-if="review.name && review.name != 'Guest'">{{review.name}}, {{review.date}}</h5>
              <h5 v-else>Anonymous, {{review.date}}</h5>
              <a v-if="$cookies.get('profile') != 'Guest'" class="report-review" href="/new-report" @click="reportCookie(review.id)">Report review 🚩</a>
            </div>
            <div class="card-body">
              <h6>Professor:</h6>
              <p class="card-text">{{review.professor}}</p>
              <h6>Semester:</h6>
              <p class="card-text">{{review.semester}} {{review.year}}</p>
              <h6>Review:</h6>
              <p class="card-text">{{review.review}}</p>
            </div>
          </div>
        </div>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import PostsDataService from '../services/PostsDataService';

@Component
export default class Reviews extends Vue {
  public course: string = window.location.href.split("/").pop() || "";
  public reviews: string[] = [];

  mounted(): void {
    PostsDataService.getCourseReviews(this.course).then((response) => {
      let array: string[] = [];
      array = response.data.map(function(item: any) {
        return item;
      });
      this.reviews = array.reverse();
      console.log('reviews', this.reviews);
      console.log('course', this.course);
    });
  };

  reportCookie(value: any) {
    console.log(value);
    this.$cookies.set("review-id", value);
  }

}

</script>

<style scoped>
  .about-logo a {
    padding: 20px;
    outline: 1px solid rgb(169, 169, 169);
    display: inline-block;
  }
  .about-logo a:hover {
    opacity: 0.8;
  }
  h1 {
    font-family: 'Roboto Slab', serif;
    color: #4987E8;
    font-weight: 700;
  }
  h6 {
    font-family: 'Roboto Slab', serif;
    font-weight: 700;
  }
  h5 {
    float: left;
  }
  .report-review {
    float: right;
  }
  
</style>