<template>
    <div class="page page--about">
        <h1>Welcome to RateMyCourse!</h1>
        <div v-if="this.$cookies.get('profile') == 'Guest' || !this.$cookies.isKey('profile')">
          <div class='buttons'>
            <h2>Sign in Using Google:</h2>
            <div id="google-signin-button"></div>
            <router-link style="display: none;" class='router' to='/sprint3'>Continue as {{this.$cookies.get('profile')}}</router-link>
          </div>
          <span>or</span>
          <div id='guest-button'>
            <router-link class='router' to='/sprint3'>Continue as Guest</router-link>
            <button style="display: none;" class='router' v-on:click='signOut'>Sign Out</button>
          </div>
        </div>
        <div v-else>
          <div class='buttons'>
            <h2 style="display: none;">Sign in Using Google:</h2>
            <div style="display: none;" id="google-signin-button"></div>
          </div>
          <div id='guest-button'>
            <router-link class='router' to='/sprint3'>Continue as {{this.$cookies.get('profile')}}</router-link>
          </div>
          <span>or</span>
          <div id='guest-button'>
            <router-link style="display: none;" class='router' to='/sprint3'>Continue as Guest</router-link>
            <button class='router' v-on:click='signOut'>Sign Out</button>
          </div>
        </div>
    </div>
</template>

<script>
export default {
  mounted() {
    if(!this.$cookies.isKey('profile')) {
      this.$cookies.set('profile', 'Guest');
      window.location.reload();
    }

    gapi.signin2.render('google-signin-button', {
        onsuccess: this.onSignIn
    });

  },
  methods: {
    onSignIn (user) {
        if(this.$cookies.get('profile') == 'Guest') {
            const profile = user.getBasicProfile();
            console.log('profile', profile);
            this.$cookies.set('profile', profile.sd);
            window.location.reload();
        }
    },
    signOut() {
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut().then(function () {
            console.log('User signed out.');
        });
        this.$cookies.set('profile', 'Guest');
        auth2.disconnect();
        window.location.reload();
    }
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
  text-align: center;
}
.buttons {
    display: flex;
    justify-content: center;
    margin: 40px;
}
.buttons * {
    margin: 10px;
}
h2 {
  font-family: "Roboto Slab", serif;
}
span {
  text-align: center;
  display: block;
  font-size: 24px;
  font-family: "Roboto Slab", serif;
}
#guest-button {
  display: flex;
  justify-content: center;
}

#guest-button .router {
  margin: 40px auto;
  background-color: #4987e8;
  color: white;
  border: none;
  border-radius: 10px;
  font-family: "Roboto Slab", serif;
  font-size: 24px;
  padding: 4px 20px;
}

.router:hover {
  text-decoration: underline;
}

</style>