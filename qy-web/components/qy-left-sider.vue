<template>
     <a-drawer 
      placement="left"
      :closable="false"
      :visible="visible"
      :after-visible-change="afterVisibleChange"
      @close="onClose"
    >
    <template slot="title">
      <nuxt-link to="/"  @click.native="toIndex">  <img  :src="siteInfo.site_small_logo" :alt="siteInfo.site_name"  class="small_logo"  > </nuxt-link>
    </template>


        <a-menu theme="light" mode="inline"   ref="leftSiderMenus"
            :defaultSelectedKeys="[$route.path]"
              :selectedKeys= "selectedKey"
             :defaultOpenKeys="['/']"
                @select="handleSelectKeys"
             >
          <a-menu-item key="/"  style="display:none"  >
             首页
          </a-menu-item>

          <template  v-for="(menu) in  siteInfo.header"  v-show="siteInfo.header">   
              <a-sub-menu  v-if="menu.children && menu.children.length>0 " :key="menu.url" @titleClick="titleClick">
                      <span slot="title" class="submenu-title-wrapper"
                        >   {{menu.name}} </span >
                        <template v-for="(children) in menu.children">    
                                  <a-menu-item :key="children.url" >
                                        <qy-to-url :targetObj="children"> </qy-to-url> 
                                </a-menu-item>
                        </template>
            </a-sub-menu>  
         
          <a-menu-item :key="menu.url"   v-else>
                <qy-to-url :targetObj="menu"> </qy-to-url> 
          </a-menu-item>
      </template>

 


        </a-menu>
            </a-drawer>
    <!-- </a-layout-sider> -->
</template>

<script>

 import { mapState } from 'vuex'
 import  QyToUrl from '~/components/qy-to-url.vue'
export default {
    props: { 
        isCollapsed: {
            default:  false
        },
        headMenuSelect: {
          default: []
        },
         afterClose: {
            type: Function,
            default: null
        }, 
        afterMenuSelect: {
            type: Function,
            default: null
        },  
    },
    components: {
        QyToUrl
    },
    data() {
        return {
            visible: this.isCollapsed,
            selectedKey:["/"]
        }
    },
    watch: {
        isCollapsed(val) {
                  
            this.visible = val;
        },
        headMenuSelect(val) {
          this.selectedKey = val;
        },
    },
    computed: {
    ...mapState({
      siteInfo: state => state.siteInfo.siteInfo
    })
  },
    methods: {
         afterVisibleChange(val) {
             let _this = this; 
                // _this.visible = !val;
                // if(val) {
                //     _this.visible = false;
                // }
        },
      onClose() {
        this.visible = false;
        this.afterClose(false);
      },
      titleClick(e) { 
        },
        handleSelectKeys(e) { 
           let _this = this;
          // this.current = e.key;
          _this.selectedKey = [e.key];
          _this.afterMenuSelect(_this.selectedKey );
          _this.onClose();
      },
      toIndex(obj) { 
          let _this =  this;
          _this.$refs.leftSiderMenus.handleSelect({key: "/" });
        }
    }
}
</script>

<style scoped>
.ant-layout-sider  {
    position: absolute;
}
.small_logo {
  max-width: 100%;
    height: 32px;
    line-height: 32px;
}
</style> 