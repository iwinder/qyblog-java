import axios from 'axios'
export const state = () => ({
    siteInfo: {

    }, 
  })
  
  export const getters = { 

  }
  export const mutations = {
    setSiteBase(state, payload) {
      state.siteInfo =  payload;      
    } 
  }

  export const actions = {
    async getSiteInfo({commit}) {
      let _this  =  this;  
        let  siteInfo =    {}; 
          try{ 
              siteInfo  =  await  _this.$axios.get('/web/siteInfo/all',{useCache: true}).then(res => {
              console.log("getSiteInfo res:",res);
              if(!res|| !res.data) { 
                Promise.reject({ statusCode: 500, message: res});
              } 
              let resp  = res.data;	
                if(resp.success) {  
                  return resp.content;
                } else {
                  if(resp.code == '404') {
                    return  Promise.reject( { statusCode: 404, message: resp.message} ); 
                  } else  {
                    return  Promise.reject( { statusCode: 500, message: resp.message});
                  }
                }
            })
         } catch (error) {
            if(error.code == '404') {
              return  Promise.reject( { statusCode: 404, message: error.message} ); 
            } else  {
              return  Promise.reject({ statusCode: 500, message: error.message} );
            }
        }
        commit('setSiteBase',  siteInfo); 
    
    }, 
 
};