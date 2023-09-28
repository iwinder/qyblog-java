<template>
    <a-row> 
        <a-card  style="width: 100%">  
            <img
            slot="cover"
            alt="example"
            src ="/img/user-default-cover-mini.png"
            />
        <a-row class="card_site_header" >
                <a-card-meta  :title="siteInfo.nickname" :description="siteInfo.site_desc">
                <a-avatar
                icon="user"
                size="large"
                    slot="avatar"
                    :src ="siteInfo.avatar"
                    alt="C" 
                />
                </a-card-meta>
        </a-row>
            <a-row class="card_site_meta" type="flex" justify="center" align="middle"> 
                <a-col :span="4" v-if=" $QyServeTool().isNotEmpty( siteInfo.site_qq) " >   <a class="fts-qq"    :href="'http://wpa.qq.com/msgrd?v=3&amp;uin='+siteInfo.site_qq+'&amp;site=qq&amp;menu=yes'" title="QQ聊天" target="_blank" rel="noopener"> <a-icon type="qq" /> </a> </a-col>
                <a-col  :span="4" v-if="$QyServeTool().isNotEmpty(siteInfo.site_qq_group)"  >    <a class="fts-qq-group"    :href="'https://shang.qq.com/wpa/qunwpa?idkey='+siteInfo.site_qq_group "  title="点击加入QQ群" target="_blank" rel="noopener">    <a-icon type="team" /> </a></a-col>
                <a-col  :span="4"  v-if="$QyServeTool().isNotEmpty(siteInfo.site_weixin_qr)"  >   
                        <a-popover  v-lazy-container="{ selector: 'img' }">
                            <template slot="content">
                            <img  :data-src="siteInfo.site_weixin_qr" :data-loading="'/img/image-pending.gif'" alt=""   v-lazy="siteInfo.site_weixin_qr" style="width: 121px;">
                            </template>
                        <a class="fts-wechat"    href="javascript:void(0);" rel="noopener"> <a-icon type="wechat"   /> </a>
                        </a-popover> 
                    </a-col>
                    <a-col  :span="4"   v-if="$QyServeTool().isNotEmpty(siteInfo.site_weibo)" >  <a class="fts-weibo"    :href="'http://www.weibo.com/'+siteInfo.site_weibo" title="微博" target="_blank" rel="noopener"> <a-icon type="weibo-circle"   /> </a> </a-col>
                    <a-col  :span="4"  v-if="$QyServeTool().isNotEmpty(siteInfo.site_mailme_id)" > <a class="fts-mailme"    :href="'http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email='+siteInfo.site_mailme_id"  title="发送Email" target="_blank" rel="noopener"> <a-icon type="mail" /> </a> </a-col>
            </a-row>
        </a-card>
        <!-- 友情链接 -->
        <a-card   title="友情链接"  style="width: 100%" v-show="siteIndexLink && siteIndexLink.length>0">  
            <!-- <nuxt-link    slot="extra"  :to="{name:'name',params:{name:'link'}}"   target="_blank"> 更多 </nuxt-link> -->
       
                <a-row class="card_site_index_url" >
                    <ul>
                        <li v-for="(link) in siteIndexLink" :key="link.id"  v-lazy-container="{ selector: 'img' }">
                                <a :href="'//'+link.url"  :title="link.description"  target="_blank" rel="noopener">
                                <!-- <img src="/favicon.ico"     v-real-img="'//'+link.url+'/favicon.ico'"  width="18px" />  -->
                                 <img :data-src="'//'+link.url+'/favicon.ico'"  data-error="/favicon.ico" data-loading="/favicon.ico"       width="18px">   
                                     {{link.name}}</a>
                        </li>
                    </ul>
            </a-row>
        </a-card>
           <!-- 自定义 -->
          <a-card  class="card_site_sider"   style="width: 100%" v-show="$QyServeTool().isNotEmpty(siteInfo.site_sider_code)">   
              <div class="ant-row" v-html="siteInfo.site_sider_code"></div>
           </a-card>
    </a-row>
</template>

<script >
import Vue from 'vue'
import { mapState } from 'vuex'
export default Vue.extend({
    computed: {
            ...mapState({
                siteInfo: state => state.siteInfo.siteInfo,
                siteIndexLink: state => state.siteInfo.siteIndexLink,
        })
    },
    data() {
        return {
 
        }
    },
    methods: {
 
    }
})
</script>

<style lang="scss"   scoped> 

.ant-card {
        margin-bottom: 10px;
}
.card_site_header {
  margin: auto;
    padding: 0px 0 10px 0;
    border-bottom: 1px solid #e8e8e8;
}
.card_site_meta {
  padding: 10px 0;
  i {
  font-size: 20px;
  } 

}
.card_site_index_url {
            ul {
              list-style: none;
              li {
                      width: 50%;
                         display: inline-block;
              }
      }
  }
</style>