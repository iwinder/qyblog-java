<template>
        <a-layout-header   :style="{background: '#fff', padding: 0,  width: '100%' ,position: 'fixed',zIndex: 2}">
            <a-row style="margin: 0 20px;">
                <a-col   :xs="{span:2}" :sm="{span:2}"  :md="{ span: 0}">
            <a-icon   
          class="trigger"
          :type="collapsed ? 'menu-unfold' : 'menu-fold'"
          @click="callChange"
        />
</a-col>
   <a-col  class="logo-col" :xs="{span:6,offset: 8}"  :sm="{span:6,offset: 7}"  :md="{ span: 3, offset:1}">
        <nuxt-link class="logo nav-col" to="/"  :title="siteInfo.site_name"   @click.native="toIndex"> 
                <img  :src="siteInfo.site_logo" :alt="siteInfo.site_name"  >
        </nuxt-link>
 </a-col>
      <a-menu  class="horizontalShow"  ref="headerMenus"
         mode="horizontal"
           :defaultSelectedKeys="[$route.path]"
           :selectedKeys= "selectedKey"
        :style="{ lineHeight: '64px',float:'left' }" 
          @select="handleSelectKeys"
      >
            <a-menu-item key="/"  style="display:none"  >
             首页
          </a-menu-item>
           <!-- {{  siteInfo.header}} -->
      <template  v-for="(menu) in  siteInfo.header"  >  
        <!-- <qy-header-menus :menu="menu"  :key="menu.url" > </qy-header-menus>
         -->
              <a-sub-menu  v-if="menu.children && menu.children.length>0 " :key="menu.url" @titleClick="titleClick">
                      <span slot="title" class="submenu-title-wrapper"
                        >   {{menu.name}} </span >
                        <template v-for="(children) in menu.children">    
                                  <a-menu-item :key="children.url"  >
                                        <qy-to-url :targetObj="children"> </qy-to-url> 
                                </a-menu-item>
                        </template>
            </a-sub-menu>  
         
          <a-menu-item :key="menu.url"   v-else>
                <qy-to-url :targetObj="menu"> </qy-to-url> 
          </a-menu-item>
      </template>
      </a-menu>
      <a-col  :xs="{span:3,offset:4}"  :sm="{span:3,offset: 4}"  :md="{ span: 2, offset:3}"  class="search-button">  <a-button shape="circle" icon="search"  :loading="searchLoading"   @click="openSearch"/></a-col>
      </a-row>

      <a-modal 
      :visible="visible" 
      :closable="closable"
      centered
      @ok="handleOk"
      @cancel="handleCancel"
      :footer="null"
    >
      <a-form-model ref="searchForm"  layout="horizontal" :model="form" >
        <a-form-model-item style="margin: 6px 0;" prop="searchText">
    <a-input-search placeholder="请输入关键字"  v-model="form.searchText"   enter-button @search="onSearch"  />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
    </a-layout-header>

    
</template>

<script>
 import { mapState } from 'vuex'
import  QyToUrl from '~/components/qy-to-url.vue'
import Vue from 'vue'
import { FormModel,Modal } from 'ant-design-vue';

Vue.use(FormModel,Modal); 
export default {
    props: { 
        isCollapsed: {
            default:  false
        },
        headMenuSelect: {
          default: []
        },
        screenWidths: {
            default:  false
        },      
          afterMenuSelect: {
            type: Function,
            default: null
        },  
    }, 
    components: {
        QyToUrl
    },
      head () {
        return { 
            link: [
                {rel:"icon" , type:'image/x-icon', href: this.siteInfo.site_icon}
            ],
            
        }
    },
    data() {
      return {
          mode: "horizontal",
          isInline: false,
          horizontalShow: false,
          collapsed: false,
          screenWidth: '',
          screenHeight: '',
          selectedKey:["/"],
          visible: false,
          closable: false,
          searchLoading:false,
          form: {
              searchText:""
          }
      };
    },
    watch: {
        isCollapsed(val) {
            this.collapsed = val;
        },
        headMenuSelect(val) {
          this.selectedKey = val;
        },
        screenWidth(val) {
            let  _this  = this;
            if(val >= 768 && _this.collapsed ) { 
                    _this.callChange(false);
            }  
        },
    },
    computed: {
    ...mapState({
      siteInfo: state => state.siteInfo.siteInfo
    })
  },
    mounted() {
      let _this = this;
      _this.initScreenWidth();
    },
    methods: {
        callChange() {
          let _this = this;
          _this.collapsed = !this.collapsed;
           _this.$emit('on-collapsed',this.collapsed);
        },
        handleSelectKeys(e) {
           let _this = this;
          this.current = e.key;
          _this.selectedKey = [e.key];
          _this.afterMenuSelect(_this.selectedKey );
      },
        titleClick(e) { 
        },
        initScreenWidth() {
            this.screenWidth = document.body.clientWidth;   
            window.onresize = () => {
                return (() => {
                  this.screenWidth = document.body.clientWidth; 
                })();
          };
        },
        toIndex(obj) { 
          let _this =  this;
          _this.$refs.headerMenus.handleSelect({key: "/" });
        },
        openSearch(e) { 
           if( event.detail ==0) {
             return;
           }
        let _this = this;
          _this.visible = true;
        },
        handleCancel() {
          let _this = this;
          _this.visible = false;
        },
        handleOk() {

        },
        onSearch(e) {
          let _this = this;

          if(_this.$QyServeTool().isEmpty(_this.form.searchText)) {
            _this.visible = false;
            return;
          }
          let searchText = _this.form.searchText;
            _this.$refs.searchForm.resetFields();
            // debugger
           _this.handleCancel() ;
          _this.$router.push("/?searchText="+searchText);
 _this.$router.push({path: "/",query: {searchText:searchText }});
          


        }
        
  }
}
</script>


<style scoped>
 .trigger {
    float: left;
    width: 48px;
    line-height: 64px;
    height: 64px; 
    font-size: 18px;
    padding: 0 24px 0 0; 
    transition: color 0.3s;
}

.trigger:hover {
  color: #1890ff;
}

 .logo {
  height: 80%;
  background: rgba(255, 255, 255, 0.2);
 
  display: inline-block;
}
.logo img {
    max-width:  100%;
    height: 100%;
    display: inline-block;
}
 .logo-col {
       text-align: center;
    vertical-align: middle;
    line-height: 64px;
    height: 64px; 
 }
 
.search-button {
      line-height: 64px;
    float: left;
}

@media (max-width: 767px) {
  .horizontalShow {
    display: none;
  }
  .trigger {
     display: inline-block;
  }
}
@media (min-width: 768px){
  .horizontalShow {
    display: inline-block;
  }
    .trigger {
     display: none;
  }
}

</style>