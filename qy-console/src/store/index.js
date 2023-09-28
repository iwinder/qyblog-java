import Vue from 'vue'
import Vuex from 'vuex'
import siteInfo from './modules/siteInfo.js'
Vue.use(Vuex)       
const store = new Vuex.Store({
    modules: {
        siteInfo
      },
    // state: {
    //      count: 0,

    // },
    // mutations: {
    //   increment (state) {
    //     state.count++
    //   }
    // }
  })

  export default store