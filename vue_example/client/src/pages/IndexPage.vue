<template>
  <div class="page page--index">
    
    <div class="alert alert-success" role="alert">
      Congrats! It looks like you got the app up & running.
    </div>
    <h3 class="mt-2">Welcome</h3>
    <p>This will be the project that you will build off of during this course.</p>
    <p>It's not super pretty right now, but mostly what we did was set up a lot of the foundation for you to build off of.</p>

    <div class="list-group">
      <a class="list-group-item list-group-item-action active" aria-current="true">
        Sprint Scheduele
      </a>
      <router-link class="list-group-item list-group-item-action" to="/sprint0">Sprint 0 [DONE]</router-link>
      <router-link class="list-group-item list-group-item-action" to="/sprint1">Sprint 1</router-link>
      <router-link class="list-group-item list-group-item-action" to="/sprint2">Sprint 2</router-link>
      <router-link class="list-group-item list-group-item-action" to="/login">Sprint 3</router-link>
    </div>
  </div>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';

@Component
export default class IndexPage extends Vue {}
</script>
