 
import { cacheAdapterEnhancer } from 'axios-extensions'
import LRUCache from 'lru-cache'
import createError from 'http-errors'
let cacheCfg = new LRUCache({
  maxAge: 1000 * 60*5, //有效期5m
  max: 1000 // 最大缓存数量
});
// let createError = require('http-errors');
export default function ({ $axios, redirect }) {
  // 请求中使用了useCache: true的话，会被缓存起来以便下次直接返回从而减少服务器压力
  let defaultAdapter = $axios.defaults.adapter;
  $axios.defaults.adapter = cacheAdapterEnhancer(defaultAdapter, { enabledByDefault: false, cacheFlag: 'useCache', defaultCache: cacheCfg });
  $axios.interceptors.response.use((response)=>{ 
    if(response.data.success) { 
       return response;
    } else {
      if(response.data.code == '404') {
          return  Promise.reject( createError(404,response.data.message));
      } else if (response.data.code == '500') {
        return Promise.reject( createError(500,response.data.message));
      } else {
        return response;
      }
    }
    
  },(err) => {
    if(err.code == '404') {
      createError({ statusCode: 404, message: err.message});
  } else  {
    createError({ statusCode: 500, message: err.message});
  }
  });
}

// 3. 在api请求时添加useCache标志即可，如在某个页面中
// export default {
//   asyncData ({app, params}) {
//     let id = params.id
//     return app.$axios.get(`RStar/getRStarDetail/?itemId=${id}`, {useCache: true}).then(res => {
//       return (res && res.data) || {}
//     })
//   }
// }