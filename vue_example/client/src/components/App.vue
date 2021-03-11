<template>
  <div id="app">
    <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #039BE5;">
      <a class="navbar-brand" href="#">
          <img src="images/software-dev.png" alt="" width="100px">
      </a>
      <div class="collapse navbar-collapse" id="navbarText">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <router-link class="nav-link" to="/" exact>Welcome</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/sprint0">Sprint 0</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/sprint1">Sprint 1</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/sprint2">Sprint 2</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/login">Sprint 3</router-link>
            </li>
          </ul>
        </div>
    </nav>
    <div class="container mt-3">
      <router-view class="mt-5"/>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class App extends Vue {}
</script>
