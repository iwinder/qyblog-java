<template>
  <a-row class="err-body">
      <qy-error-404 >  </qy-error-404> 
    
    <!-- <nuxt-link to="/">返回首页</nuxt-link> -->
  </a-row>
</template>

<script>
import QyError404 from '~/components/qy-error-404.vue' 
 import { mapState } from 'vuex'

export default {
  props: ["error"],
    components: {
    QyError404, 
  },
  layout: "allPage" ,// 可以为错误页面指定自定义的布局
head () {
    // let sc =  this.siteInfo.site_head_code;
    // let scarr = sc.split("<\/script>");


        return {
            title:     "404未找到   - "+ this.siteInfo.site_name ,
            meta: [
               { hid: "keywords", name: "keywords", content: this.siteInfo. site_key},
                { hid: "description", name: "description", content: this.siteInfo. site_description},

            ],
            link: [
                      {rel:"canonical" , href: this.siteInfo.site_url+this.$nuxt.$route.path}
            ],
            
        }
    },
         computed: {
  ...mapState({
    siteInfo: state => state.siteInfo.siteInfo
  })
},
}
</script>

<style scoped>
.err-body {
        height: 100vh;
}
</style>