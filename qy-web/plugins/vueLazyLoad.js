import Vue from 'vue'
import VueLazyLoad from 'vue-lazyload' 
import QyClientTool from './qy-client-tool'
Vue.use(VueLazyLoad, {
  preLoad: 1.33, // 预加载的宽高比，4:3
  error: '/img/thumb/'+ QyClientTool.randomNum(1,32)+'.jpg', // 加载失败时使用的图片
  loading: '/img/image-pending.gif', // 加载时的loading图
  attempt: 2 //尝试加载次数
});