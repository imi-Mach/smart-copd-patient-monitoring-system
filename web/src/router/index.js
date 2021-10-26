import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/patients',
    name: 'PatientPortal',
    component: () => import('../views/PatientPortal.vue')
  },
  {
    path: '/pprofile',
    name: 'PatientProfile',
    component: () => import('../views/PatientProfile.vue')
  },
  {
    path: '/healthcares',
    name: 'HealthcarePortal',
    component: () => import('../views/HealthcarePortal.vue')
  },
  {
    path: '/hprofile',
    name: "HealthcareProfile",
    component: () => import('../views/HealthcareProfile.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
