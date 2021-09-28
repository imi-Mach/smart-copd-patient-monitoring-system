import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vueresource from 'vue-resource'
import VueSession from "vue-session";
Vue.use(VueSession);

// for element ui, delete in the future if using bootstrap
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

Vue.use(vueresource)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
