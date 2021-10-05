import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "Home",
      alias: "/home",
      component: () => import('../views/Home.vue')
    },
    {
      path: '/about',
      name: 'About',
      component: () => import('../views/About.vue')
    },
    {
      path: '/patients',
      name: 'Patient',
      component: () => import('../views/Patient.vue')
    },
    {
      path: '/register',
      name: 'Register',
      component: () => import('../views/Register.vue')
    },
    {
      path: '/profile',
      name: 'Profile',
      component: () => import('../views/PatientProfile.vue')
    }
  ]
});
