<template>
    <div class="page page--about">
        <router-link class="back-home" to="/sprint2">Back Home</router-link>
        <h1>{{ welcome }}</h1>
        <add-report></add-report>
    </div>
</template>

<script lang="ts">

import { Component, Vue } from 'vue-property-decorator';
import { component } from 'vue/types/umd';
import PostsDataService from '../services/PostsDataService';
import AddReport from '../components/AddReport.vue';

@Component({
    components: {
        AddReport
    }
})

export default class NewReport extends Vue {
    private welcome: string = 'New Course Report';
}

</script>

<style scoped>

  h1 {
      font-family: 'Roboto Slab', serif;
      color: #4987E8;
      font-weight: 700;
  }

</style>