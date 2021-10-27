import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: ""
  },
  mutations: {
    setToken(state, str) {
      state.token = str
    }
  },
  actions: {
  },
  modules: {
  }
})
