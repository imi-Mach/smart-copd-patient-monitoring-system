<template>
    <div class="page page--about">
        <router-link class="back-home" to="/sprint3">Back Home</router-link>
        <h1>{{ this.$cookies.get('profile') }}'s Profile</h1>
        <h3>Your Posts</h3>
        <div class='no-reviews' v-if='reviews.length == 0'>
          <br>
          <h6>You haven't written any reviews yet.</h6>
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
            </div>
            <div class="card-body">
              <h6>Course:</h6>
              <p class="card-text">{{review.courseNumber}}</p>
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
  public user: string = this.$cookies.get('profile');
  public reviews: string[] = [];

  mounted(): void {
    PostsDataService.getUserReviews(this.user).then((response) => {
      let array: string[] = [];
      array = response.data.map(function(item: any) {
        return item;
      });
      this.reviews = array.reverse();
      console.log('reviews', this.reviews);
      console.log('user', this.user);
    });
  };

  reportCookie(value: any) {
    console.log(value);
    this.$cookies.set("review-id", value);
  }

}

</script>

<style scoped>

h1, .mt-3 {
  font-family: "Roboto Slab", serif;
  padding: 0;
}
h1 {
  color: #4987e8;
  font-weight: 700;
  margin: 8px 0;
}
h3 {
   font-family: "Roboto Slab", serif;
   font-weight: 700; 
   margin: 30px;
   margin-left: 0;
}

</style>