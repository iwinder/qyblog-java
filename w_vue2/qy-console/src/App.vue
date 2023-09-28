<template>
  <div id="app">
    <a-config-provider  :locale="zh_CN">
      <router-view />
    </a-config-provider >  
  </div>
</template>

<script>
import moment from 'moment';
import zh_CN from 'ant-design-vue/lib/locale-provider/zh_CN';
import 'moment/locale/zh-cn'; 
moment.locale('zh-cn');
export default {
  name: 'App',
  components: {
     
  },
created() {
  let _this = this; 
  _this.site  = QyTool.getSiteInfoBase();
  if(QyTool.isNotEmpty( _this.site)) {
    QyTool.refreshSiteInfoBase( _this.site);
  } else {
     document.title = "WindCoder";
  } 
  _this.initEndWithF();

  },
  data() {
    return {
      site:{}, 
      zh_CN,
    }
  },
  methods:  {
      initEndWithF() { 
        if (typeof String.prototype.endsWith != 'function') {
            String.prototype.endsWith = function (str){
                return this.slice(-str.length) == str;
            };
        }
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
  /* margin-top: 60px; */
}
</style>
