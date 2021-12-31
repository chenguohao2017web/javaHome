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
    component: Login,
    name: 'login'
  },
  {
    path: "/home",
    component: () => import(/* webpackChunkName: "about" */ '../views/Home'),
    name: 'home'
  },
  {
    path: '/user/select',
    component: () => import(/* webpackChunkName: "about" */ '../views/User/select'),
    name: 'userSelect'
  },
  {
    path: '/user/edit',
    component: () => import(/* webpackChunkName: "about" */ '../views/User/edit'),
    name: 'userEdit'
  },
  {
    path: '/room/select',
    component: () => import(/* webpackChunkName: "about" */ '../views/Room/select'),
    name: 'roomSelect'
  },
  {
    path: '/room/edit',
    component: () => import(/* webpackChunkName: "about" */ '../views/Room/edit'),
    name: 'roomEdit'
  },
  {
    path: '/count/select',
    component: () => import(/* webpackChunkName: "about" */ '../views/Count/select'),
    name: 'countSelect'
  },
  {
    path: "/count/insert",
    component: () => import(/* webpackChunkName: "about" */ '../views/Count/insert'),
    name: 'countInsert'
  },
  {
    path: "/count/view",
    component: () => import(/* webpackChunkName: "about" */ '../views/Count/view'),
    name: 'countView'
  }


]

const router = new VueRouter({
  // mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// const originalPush = VueRouter.prototype.push
// const originalReplace = VueRouter.prototype.replace
//
// VueRouter.prototype.push = function push(location,onResolve, onReject) {
//   if(onReject || onReject) {
//     return originalPush.call(this, location, onResolve, onReject)
//     return originalPush.call(this, location).catch(err => err)
//   }
// }


export default router
