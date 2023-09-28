import axios from 'axios'
export const state= ()=>({
    siteMenus: {
        header:{},
        footer:{}
    }, 
  })
  export const getters = {
    setSiteIndexLink(state, data) {
        state.siteMenus =  data;
    },
  }
  export const mutations={
    setSiteGo(state, payload) {
      state.siteMenus = payload; 
    }
  }
  export const actions={
    async getSiteIndexLink({commit})  {
        let _this  =  this;
        let  menus=  {}; 
        menus  = await _this.$axios.get('/web/siteInfo/menus',{useCache: true}).then(res => {
            let resp  = res.data;	
              if(resp.success) { 
                return resp.content;
              } else {
                if(resp.code == '404') {
                  return  Promise.reject( { statusCode: 404, message: resp.message}); 
                } else  {
                  return  Promise.reject({ statusCode: 500, message: resp.message});
                }
              }
        })
        if ((typeof menus.header == 'string')) {
            menus.header = JSON.parse(menus.header);
            menus.footer = JSON.parse(menus.footer );
        }
        commit('setSiteIndexLink',  menus); 
    }
  }