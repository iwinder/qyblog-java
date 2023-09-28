<template>
    <a-layout-footer >
        <a-row class="footer-nav"  type="flex" justify="center" align="middle" >
           
        <a-row  class="footer-menus" v-if="siteInfo.footer">
         
            <a-menu  class="footerMenus"  ref="footerMenus"
                mode="horizontal"
                theme="dark"
                :defaultSelectedKeys="[$route.path]"
                :style="{ lineHeight: '32px' }" 
            > 
                <template   v-for="(menu) in  siteInfo.footer">  
                        <a-menu-item :key="menu.url"  >
                                <qy-to-url :targetObj="menu"> </qy-to-url> 
                        </a-menu-item>
                </template>
            </a-menu>
           
        </a-row>
        <a-row class="footer-copy" >
            <!-- :xs="{span:8}"  :sm="{span:6}"  :md="{ span: 3}" -->
            <a-col  > © 
                <template v-if="siteInfo.site_open_date">  {{moment(siteInfo.site_open_date).year()}} - </template>
                 {{moment().year()}}     <a :href="siteInfo.site_url" title="WindCoder" rel="link noopener" target="_blank" v-if="siteInfo.site_url">{{siteInfo.site_name}}</a>
                 <nuxt-link to="/" title="WindCoder" rel="link noopener" target="_blank"  v-else > {{siteInfo.site_name}}</nuxt-link>
                 </a-col>   
                   <!-- :xs="{span:8}"  :sm="{span:6}"  :md="{ span: 3}" -->
            <a-col  v-if="siteInfo.site_beian"> <a href="http://beian.miit.gov.cn/" rel="link noopener" target="_blank">{{siteInfo.site_beian}}</a></a-col>
            <a-col   >  <b style="color: #ff4425;">♥</b>   Design by 
                <a href="https://windcoder.com" title="WindCoder" rel="link noopener" target="_blank">WindCoder.</a>
                </a-col>   
        </a-row>
        <template   v-if="siteInfo.site_foot_code"> 
                <div class="footer-diy ant-row"   v-html="siteInfo.site_foot_code"></div> 
        </template>
        
  </a-row>



   <!-- :xs="{span:24}"  :sm="{span:9}"  :md="{ span: 5}" -->
    </a-layout-footer>
</template>


<script>
 import { mapState } from 'vuex'
 import moment from 'moment'
 import  QyToUrl from '~/components/qy-to-url.vue'

export default {
    name: 'qy-web-footer',
        serverCacheKey () {
        // Will change every 10 secondes
        return 'qy-web-footer'+Math.floor(Date.now() / 10000)
    },
    computed: {
        ...mapState({
        siteInfo: state => state.siteInfo.siteInfo
        })
    },
    // props: { 
    //     isCollapsed: {
    //         default:  false
    //     }
    // },
    //  data() {
    //      return {
    //          collapsed: this.isCollapsed
    //      }
    //  },
    components: {
        QyToUrl
    },
     methods:{ 
         moment
     }
}
</script>

<style lang="scss" scoped>
.ant-layout-footer{
    text-align: center;
    // height: 100px;
    background: #000;
    color: #fff;
    padding: 12px 25px;
    .footer-nav {
        padding: 20px 0;
        .footer-menus {
             width: 100%;
             .footerMenus {
                    background: rgba(0, 0, 0, 0);
                
                    li {
                        padding: 0 5px;
                        a{ color: #fff;  }
                        a:before {
                                position: absolute;
                                top: inherit;
                                left: 0;
                                width: 100%;
                                height: 1px;
                                background-color: #c2c2c2;
                                content: ' ';
                                -webkit-transition: all .2s;
                                transition: all .2s;
                                backface-visibility: hidden;
                                -webkit-transform: scaleX(0);
                                transform: scaleX(0);
                                transform-origin: center;
                        }
                        a:hover:before {
                                        -webkit-transform: scaleX(1);
                                            transform: scaleX(1);
                            }
                  
                }
                .ant-menu-item-selected {background: rgba(0, 0, 0, 0.15);}
             }
            margin-bottom: 15px;
           

            
        }
        .footer-copy {
            .ant-col {
                    display: inline-block;
                     a{ color: #fff;  }
            }
        }
    }
}
</style>