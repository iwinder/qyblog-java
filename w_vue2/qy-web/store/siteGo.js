import axios from 'axios'
export const state= ()=>({
    siteGo: {

    },
  })
  export const getters = {
    getSiteGoUrl: (state) => (key) => {
      return state.siteGo[key];
    }, 
  }
  export const mutations={
    setSiteGo(state, payload) {
      state.siteGo = payload; 
    }
  }
  export const actions={
    async getSiteGo({state, commit}, val)  {
        let _this  =  this; 
            let  siteGo =   await  axios.get('/web/siteInfo/shortLinks',{useCache: true}).then(res => {
                let resp  = res.data;	
                  if(resp.success) {  
                    return resp.content;
                  } else {
                    if(resp.code == '404') {
                      return  Promise.reject({ statusCode: 404, message: resp.message}); 
                    } else  {
                      return  Promise.reject( { statusCode: 500, message: resp.message});
                    }
                  }
              });  
        commit('setSiteGo',  siteGo); 
    }
  }