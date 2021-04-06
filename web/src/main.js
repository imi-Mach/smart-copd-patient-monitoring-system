import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vueresource from 'vue-resource'

Vue.use(vueresource)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
