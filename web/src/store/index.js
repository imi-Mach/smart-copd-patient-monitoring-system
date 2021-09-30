import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
      sessionID: "",
      userID: "",
  },
  mutations: {
      setSession(state, newSessionID) {
          state.sessionID = newSessionID
      },
      setUserID(state, newUserID) {
        state.userID = newUserID;
      },
  },
  getters: {
      getSession: state => {
        return state.sessionID;
      },
      getUserID: state => {
        return state.userID;
      },
  },
})
