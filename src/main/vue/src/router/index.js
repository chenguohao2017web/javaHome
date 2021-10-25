import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: "/home",
    component: Home
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
