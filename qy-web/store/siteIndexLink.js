import axios from 'axios'
export const state= ()=>({
    siteIndexLink: {

    }, 
  })
  export const getters = {
    setSiteIndexLink(state, data) {
        state.siteIndexLink =  data;
    },
  }
  export const mutations={
    setSiteGo(state, payload) {
      state.siteIndexLink = payload; 
    }
  }
  export const actions={
    async getSiteIndexLink({commit})  {
        let _this  =  this;
        let  siteIndexLink=  {}; 
        siteIndexLink =   await     _this.$axios.get('/web/siteInfo/indexlinks',{useCache: true}).then(res => {
            if(!res) { 
                return;
            }
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
        commit('setSiteIndexLink',  siteIndexLink); 
    }
  }