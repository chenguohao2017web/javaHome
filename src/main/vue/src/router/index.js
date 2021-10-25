import Vue from 'vue'
import VueRouter from 'vue-router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import Login from "../views/Login/login"

Vue.use(ElementUI);
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: "/login"
  },
  {
    path: "/login",
    component: Login
  },
  {
    path: "/home",
    component: () => import(/* webpackChunkName: "about" */ '../views/Home')
  },
  {
    path: '/user/select',
    component: () => import(/* webpackChunkName: "about" */ '../views/User/select')
  },
  {
    path: '/user/edit',
    component: () => import(/* webpackChunkName: "about" */ '../views/User/edit')
  },
  {
    path: '/room/select',
    component: () => import(/* webpackChunkName: "about" */ '../views/Room/select')
  },
  {
    path: '/room/edit',
    component: () => import(/* webpackChunkName: "about" */ '../views/Room/edit')
  },
  {
    path: '/count/select',
    component: () => import(/* webpackChunkName: "about" */ '../views/Count/select')
  },
  {
    path: "/count/insert",
    component: () => import(/* webpackChunkName: "about" */ '../views/Count/insert')
  },
  {
    path: "/count/view",
    component: () => import(/* webpackChunkName: "about" */ '../views/Count/view')
  }


]

const router = new VueRouter({
  // mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
