import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    sessionID: "",
  },
  mutations: {
    setSessionID(state, sessionID){
      state.sessionID = sessionID
    },
    addData(state, data) {
      state.entries.push(data);
    },
  },
  actions: {
  },
  modules: {
  },
  getters: {
    getSessionID: state =>{
      return state.sessionID;
    },
  }
})
