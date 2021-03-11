import Vue from 'vue';
import App from './components/App.vue';
import router from './router/router';
import vSelect from 'vue-select';
import VueCookies from 'vue-cookies';
import 'vue-select/dist/vue-select.css';

Vue.config.productionTip = false;
Vue.component('v-select', vSelect);
Vue.use(VueCookies);
Vue.$cookies.config('1d');          // Test

new Vue({
  router,
  render: h => h(App)
}).$mount('#app');