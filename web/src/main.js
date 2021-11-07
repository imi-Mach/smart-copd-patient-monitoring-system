import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueResource from 'vue-resource'
import VueSession from "vue-session"
import ElementUI from 'element-ui';
import locale from 'element-ui/lib/locale/lang/en'
import VueCookies from 'vue-cookies'
import 'element-ui/lib/theme-chalk/index.css';


Vue.use(VueSession);
Vue.use(VueResource);
Vue.use(ElementUI, { locale });
Vue.use(VueCookies);

Vue.$cookies.config('1d')

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
