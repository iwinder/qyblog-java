import Vue from 'vue'
import App from './App.vue'
import Antd from 'ant-design-vue/lib'
import axios from 'axios'
import router from './router'
import mavonEditor from 'mavon-editor'
import store from './store'
 import 'mavon-editor/dist/css/index.css'
 import 'ant-design-vue/dist/antd.css';
 
Vue.config.productionTip = false
Vue.prototype.$axios = axios;
// Vue.prototype.$api_url = 'http://127.0.0.1:9000/';
Vue.prototype.$isDeBug = false;

// 解决每次ajax请求，对应的sessionId不一致的问题
axios.defaults.withCredentials = true;
axios.defaults.baseURL= '/api/',

Vue.use(Antd);
Vue.use(mavonEditor)



/**
 *  axios拦截器
 */
axios.interceptors.request.use((config)=>{
  let token = QyTool.getLoginToken();
  if(QyTool.isNotEmpty(token)) {
    config.headers.Authorization = token;
  }
  return config;
},error=>{
  if (error.response) {  
      console.log(error.response.status); 
  }   else if (error.request) {
    console.log("request  error:", error.request);
  } else {
    console.log("请求headers增加token error:", error);
  }

  return error;
})

axios.interceptors.response.use((response)=>{ 
  return response;
},error=>{
  if (error.response) {
    if(error.response.status && error.response.status==401) {
      QyTool.removeLoginToken();
        router.replace({
          path: '/login'
          //登录成功后跳入浏览的当前页面
          // query: {redirect: router.currentRoute.fullPath}
      })
    } 
}   else if (error.request) {
  console.log("interceptors request  error:", error.request);
  console.log("interceptors request  error:", error.response);
} else {
  console.log("interceptors 请求headers增加token error:", error);
}
  return error;
});




new Vue({
  // 'el': '#main',
  router,
  store,
  render: h => h(App),
}).$mount('#app')
