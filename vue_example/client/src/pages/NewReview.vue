<template>
    <div class="page page--about">
        <router-link class="back-home" to="/sprint3">Back Home</router-link>
        <h1>{{ welcome }}</h1>
        <add-review></add-review>
    </div>
</template>

<script lang="ts">

import { Component, Vue } from 'vue-property-decorator';
import { component } from 'vue/types/umd';
import PostsDataService from '../services/PostsDataService';
import AddReview from '../components/AddReview.vue';

@Component({
    components: {
        AddReview
    }
})

export default class NewReview extends Vue {
    private welcome: string = 'New Course Review';
}

</script>

<style scoped>

  h1 {
      font-family: 'Roboto Slab', serif;
      color: #4987E8;
      font-weight: 700;
  }

</style>