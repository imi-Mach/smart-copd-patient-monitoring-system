import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
      sessionID: "",
  },
  mutations: {
      setSession(state, newSessionID) {
          state.sessionID = newSessionID
      },
  },
  getters: {
      getSession: state => {
        return state.sessionID
      }
  },
})
