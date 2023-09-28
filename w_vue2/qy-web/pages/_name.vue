<template>
    <a-row class="content">
        <template  v-if=" postData.type==1">
            <a-col  class="single-left" :xs="{span:24}"  :lg=" { span: 16}" > 
                <qy-post-info :postData="postData"> </qy-post-info> 
            </a-col>

            <a-col  class="single-right"   :xs="{span:24}"  v-if="postData.type==1" :lg=" {  span: 7, offset: 1 } " > 
                         <qy-post-right-sider></qy-post-right-sider>
            </a-col>
        </template>
        <template v-else>
              <qy-page-info :postData="postData" :linkList="linkList"> </qy-page-info> 
        </template>
        
    </a-row>
</template>

<script>
import Vue from 'vue'
import moment from 'moment';
import { FormModel } from 'ant-design-vue';
import QyPostRightSider from '~/components/qy-post-right-sider.vue'
 import  QyPostInfo from '~/components/qy-post-info.vue'
  import  QyPageInfo from '~/components/qy-page-info.vue'

Vue.use(FormModel); 
 import { mapState } from 'vuex'
export default {
    async fetch({ store, params }) {
        //  await store. dispatch('siteInfo/getSiteInfo');
},
     async  asyncData (context) { 
         let _this = context;
         let id = context.params.aid;
        let name = context.params.name;
         let url = "/web/articles/";
            if  (name) {
                    url = "/web/articles/name/" + name ;
              } else {
                    url = url  +id;
                 
              }
        let res1  = {};
         try{
            res1  = await   context.$axios.get(url, {useCache: true}).then(res => {
            let resp  = res.data				
            let result = {};
            if(resp.success) { 
                result  = resp.content;  
                if(result.type == 1) {
                    let defImg = "/img/image-pending.gif";  
                    defImg = '/img/thumb/'+ _this.$QyServeTool().randomNum(1,32)+'.jpg';
                    
                    result.defImg = defImg;
                    result.thumbnail =  result.thumbnail?result.thumbnail:defImg;
                }
                let tagStrs = "";
                if(result.tags) { 
                    let len = result.tags.length;
                    for(let i=0;i<len;i++) {
                      tagStrs +=result.tags[i].name;
                      if(i<len-1) {
                          tagStrs +=", ";
                      }
                    }
                }
                result.tagStrings = tagStrs;
                result.publishedDateMD =   moment(result.publishedDate).format('YYYY-MM-DD');
                result.publishedDateTime =   moment(result.publishedDate).format('YYYY-MM-DD HH:mm:ss');
                result.contentHtml=result.contentHtml.replace(/src/g,"data-src")
            } else {
                if(resp.code == '404') {
                    _this.error({ statusCode: 404, message: resp.message});
                } else  {
                    _this.error({ statusCode: 500, message: resp.message});
                }
            }   
            return result; 
          });
          } catch (error) {
            if(error.status == 404)  {
                 _this.error({ statusCode: 404, message: error.message});
            } else {
                _this.error({ statusCode: 500, message: error.message});
            }
          }
          let linkList = [];

         if(name && name.indexOf( "links")==0) {
           linkList =  await   context.$axios.get("/web/siteInfo/allLinks", {useCache: true}).then(res => {
                    let resp  = res.data		
                   if(resp.success) { 
                       return  resp.content;
                   } else {
                       return [];
                   }
             }); 
   
          
         };
       
          return{
              postData : res1,  
              linkList: linkList
        }

     },
    head () {
        return {
            title: this.postData.title + "-" + this.siteInfo.site_name,
            meta: [
                { hid: "keywords", name: "keywords", content: this.postData.tagStrings},
                { hid: "description", name: "description", content: this.postData. summary},

            ],
            link: [
                {rel:"stylesheet" ,type:"text/css", href:"/css/github-markdown.css"},
                {rel:"canonical" , href:this.postData.canonicalLink ? this.postData.canonicalLink : this.siteInfo.site_url+"/" +this.postData.permaLink }
            ],
            
        }
    },
    components: {
        QyPostInfo,
        QyPageInfo,
        QyPostRightSider
    },
      computed: {
    ...mapState({
      siteInfo: state => state.siteInfo.siteInfo,
    //    siteIndexLink: state => state.siteInfo.siteIndexLink,
    })
  },
     data() {
         return {
             postData: {},
             submitting: false,
             value: '',
             commentForm: {
                content: '',
                authorName: '',
                authorEmail: '',
                authorUrl: ''
            },
            linkList:[]
         }
     },
     mounted() { 
            // 更新统计
            let _this = this;
        if(process.browser && _this.postData!=null ) { 
            _this.updateViews(_this.postData.id);
        }
     },
    methods: {
        moment,
        updateViews(blogId) {
            let _this = this; 
            let formData = new FormData();
            formData.append(" articleId", blogId);
            _this.$axios.post("/web/articles/updateViews", formData).then(res => {
                    let resp  = res.data		
                   if(resp.success) {  
                   } else {
                       return [];
                   }
             }); 
        }
  },
}
</script>



<style lang="scss" scoped>

.content {
  padding: 20px 15px;
    width: 85%;
    margin-right: auto;
    margin-left: auto;
    @media (max-width: 992px) {
      width: 100%;
      margin: 0;
    }
    @media (min-width: 992px){ 
      width: 85%;
      margin-right: auto;
      margin-left: auto;
    }
}


   @media screen and (max-width: 992px) {
    .single-right {
      padding: 20px 0;
    }
  }

</style>