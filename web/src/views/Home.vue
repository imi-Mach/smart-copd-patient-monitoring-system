<template>
  <div class="home">
    <div id="nav">
      <router-link to="/">Patient Portal</router-link> |
      <router-link to="/about">Healthcare Profession</router-link> 
    </div>
    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Smart COPD Patient Monitoring System"/>
    <Login></Login>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import Login from '@/components/Login.vue'

export default {
  name: 'Home',
  components: {
    HelloWorld,
    Login

  }
}
</script>
