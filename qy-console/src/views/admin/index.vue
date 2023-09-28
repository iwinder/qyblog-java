<template>
	<a-layout id="components-layout-demo-custom-trigger"  style="min-height: 100vh">
        <qy-left-sider :isCollapsed="collapsed"  :afterBreakpoint="afterBreakpoint"></qy-left-sider>

        <a-layout>
            <qy-header  :isCollapsed="collapsed" @on-collapsed="onCollapsed"></qy-header>

             <a-layout-content
                    :style="{ margin: '24px 16px', padding: '24px', background: '#fff', minHeight: '280px' , marginTop: '75px'}">
               <router-view/>
            </a-layout-content>

            <qy-footer></qy-footer>
        </a-layout>

    </a-layout>

</template>

<script >
import Vue from 'vue'
import QyLeftSider from '../../components/qy-left-sider.vue'
import QyHeader from '../../components/qy-header.vue'
import QyFooter from '../../components/qy-footer.vue'
import { mapState } from 'vuex'

export default Vue.extend({
  components: {
    QyLeftSider,
    QyHeader,
    QyFooter
  },
  data() {
    return {
      collapsed: false,
    };
  },
      computed: {
        ...mapState({
        site: state => state.siteInfo.siteInfo
        })
    },
    created() {
      let _this = this; 
      if(QyTool.isEmpty(_this.site)) { 
            _this.$store.dispatch("initSiteBase");
      }
      // _this.site = QyTool.getSiteInfoBase();
  },
  mounted() {
  },
  methods: {
    onCollapsed(val) {
      this.collapsed = val;
    },
    afterBreakpoint(val) { 
      this.collapsed = val;
    }
  }
})
</script>
<style>
html {
  font-family: 'Source Sans Pro', -apple-system, BlinkMacSystemFont, 'Segoe UI',
    Roboto, 'Helvetica Neue', Arial, sans-serif;
  font-size: 16px;
  word-spacing: 1px;
  -ms-text-size-adjust: 100%;
  -webkit-text-size-adjust: 100%;
  -moz-osx-font-smoothing: grayscale;
  -webkit-font-smoothing: antialiased;
  box-sizing: border-box;
}

*,
*:before,
*:after {
  box-sizing: border-box;
  margin: 0;
}

.button--green {
  display: inline-block;
  border-radius: 4px;
  border: 1px solid #3b8070;
  color: #3b8070;
  text-decoration: none;
  padding: 10px 30px;
}

.button--green:hover {
  color: #fff;
  background-color: #3b8070;
}

.button--grey {
  display: inline-block;
  border-radius: 4px;
  border: 1px solid #35495e;
  color: #35495e;
  text-decoration: none;
  padding: 10px 30px;
  margin-left: 15px;
}

.button--grey:hover {
  color: #fff;
  background-color: #35495e;
}

#components-layout-demo-custom-trigger .trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}

#components-layout-demo-custom-trigger .trigger:hover {
  color: #1890ff;
}

#components-layout-demo-custom-trigger .logo {
    height: 32px;
    background: rgba(255, 255, 255, 0.8);
    margin: 16px;
    text-align: center;
}
</style>
