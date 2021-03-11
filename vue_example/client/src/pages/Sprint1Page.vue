<template>
  <div class="page page--about">
    <h1>{{ title }}</h1>
    <p class="mt-3">{{ description }}</p>

    <div id="search_bar">
      <h2>Vue Select</h2>
      <v-select :options="posts"></v-select>
    </div>
    <br>
    <br>
    <posts></posts>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Posts from '../components/Posts.vue'
import PostsDataService from '../services/PostsDataService';

@Component({
  components: {
    Posts
  }
})

export default class MessagesPage extends Vue {
  private title: string = 'Welcome to RateMyCourse!';
  private description: string = 'Use the search bar below to filter classes by course number. Then, click on a course to see students\' reviews of the class, or write a review for that class. If your class does not exist yet, enter in the course number and click "add class" to add the first review.';
  public posts: string[] = [];

  mounted(): void {
    PostsDataService.getAll().then((response) => {
      let array: string[] = [];
      array = response.data.map(function(item: any) {
        return item['text'];
      });
      this.posts = array;
      this.posts.sort();
    });
  };
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
  h1, .mt-3 {
    font-family: 'Roboto Slab', serif;
  }
  h1 {
    color: #4987E8;
    font-weight: 700;
  }
</style>