import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
      sessionID: undefined,
  },
  mutations: {
      setSession(state, newSessionID) {
          state.sessionID = newSessionID
      },
  },
  getters: {
      async getSession() {
          return this.state.sessionID;
      }
  },
})
