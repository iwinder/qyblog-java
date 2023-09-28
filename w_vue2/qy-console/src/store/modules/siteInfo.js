import axios from 'axios'
// initial state
const state = () => ({
    siteInfo: {
    }, 
  })
  
  // getters
  const getters = {
    getSiteBase({ state, commit, rootState }) {
      return state.siteInfo;
    }
  }
  
  // actions
  const actions = {
    async  initSiteBase({ commit, state  }) { 
      let siteInfo = QyTool.getSiteInfoBase();
      if (QyTool.isEmpty(siteInfo) ) {
        siteInfo =   await  axios.get('/web/siteInfo/all' ).then(res => {
            let resp  = res.data				
            if(resp.success) {
              return  resp.content;
            }
        }).catch( e=>{
          console.log(e);
        }

        );
      }
      QyTool.refreshSiteInfoBase(siteInfo);
      commit('setSiteBase',siteInfo);

    }
  }
  
  // mutations
  const mutations = {
    setSiteBase(state, siteInfo) {
      state.siteInfo = siteInfo;
      QyTool.setSiteInfoBase( state.siteInfo);
    }, 
  
    decrementProductInventory (state, { id }) {
      const product = state.all.find(product => product.id === id)
      product.inventory--
    }
  }
  
  export default {
    // namespaced: true,
    state,
    getters,
    actions,
    mutations
  }