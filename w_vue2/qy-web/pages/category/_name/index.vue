<template>
    <a-row class="content">
        <a-col :xs="{span:24}"  :lg="{ span: 16}"  class="content-left" >
                       <h2  >分类  {{targetObj.name}} 的结果</h2>  
        <qy-post-list  :pagination="pagination" :listData="listData"></qy-post-list>
        </a-col>

       <a-col :xs="{span:24}"      :lg="{  span: 7, offset: 1 }" class="content-right" >  
         <qy-post-right-sider></qy-post-right-sider>
        </a-col>

    </a-row>
</template>

<script>
import Vue from 'vue'
import moment from 'moment';
import { FormModel } from 'ant-design-vue';
import QyPostList from '~/components/qy-post-list.vue'
import QyPostRightSider from '~/components/qy-post-right-sider.vue'
 import { mapState } from 'vuex'
 
Vue.use(FormModel); 
export default {
      components: { 
        QyPostList,
        QyPostRightSider
     },
     async fetch({ store, params }) {
  // await store. dispatch('siteInfo/getSiteInfo');
},
     async  asyncData (context) { 
       let _this = context;
         let id = context.params.aid;
        let name = context.params.name;
        let params = {
              page: 1,
              size:  10
         }
          let baseTagurl = "/web/category/";
          let tagUrl = "";
            if  (id) {
                   params.categoryId = id;
                     tagUrl = baseTagurl + id;
              } else {
                   params.categoryName = name;
                 tagUrl = baseTagurl + "name/" + name ;
              }
              let res1 = {};
               let  category =  {};
                try{
           res1 = await     context.$axios.get("/web/articles", {params: params}).then(res => {
            let resp  = res.data				
            let result = {}; 
            let listData = [];
            if(resp.success) {
                let data = resp.content.list;
               let defImg = "/img/image-pending.gif";
                data.forEach(e  => { 
                      // if(process.browser) {
                                defImg = '/img/thumb/'+ _this.$QyServeTool().randomNum(1,32)+'.jpg';
                          // } 
                    listData.push({
                             id: e.id,
                            href:  e.permaLink,
                            title: e.title,
                            thumbnail: e.thumbnail?e.thumbnail:defImg,
                            tagStrings: e.tagStrings,
                            tags: e.tags,
                            category: e.category,
                            avatar: e.author.avatar,
                            author: e.author,
                            status: e.status,
                            defImg: defImg,
                            viewCount: e.viewCount,
                            commentCount: e.commentCount,
                            publishedDateMD:   moment(e.publishedDate).format('YYYY-MM-DD'),
                            content: e.summary,
                    });
               });
              result = {
                  listData:  listData,
                  total:   resp.content.total,
                  current:  resp.content.page,
                  pageSize:  resp.content.size
                };
            } else { 
                if(resp.code == '404') {
                    _this.error({ statusCode: 404, message: resp.message});
                } else  {
                    _this.error({ statusCode: 500, message: resp.message});
                }
            }
            return result;
          }) 
          category = await  context.$axios.get(tagUrl).then(res => {
              let resp  = res.data			
               if(resp.success) {
                 return resp.content;
            } else { 
                if(resp.code == '404') {
                    _this.error({ statusCode: 404, message: resp.message});
                } else  {
                    _this.error({ statusCode: 500, message: resp.message});
                }
            }
          }); 
        } catch (error) {
            if(error.status == 404)  {
                 _this.error({ statusCode: 404, message: error.message});
            } else {
                _this.error({ statusCode: 500, message: error.message});
            }
    }
        return{ 
             listData : res1.listData, 
              pagination: {
                total:  res1.total,
                current : res1.current ,
                pageSize : res1.pageSize, 
                 showLessItems: true, 
              },
            targetObj  : category,
               paramName: name
        }

     },
    head () {
        return {
            title:  this.targetObj.name + " - "+  this.siteInfo.site_name,
            meta: [
                // { hid: "keywords", name: "keywords", content: this.postData. tagStrings},
                // { hid: "description", name: "description", content: this.postData. summary},

            ],
            link: [
               {rel:"canonical" , href: this.siteInfo.site_url+this.$nuxt.$route.path}
            ],
            
        }
    },
 
     data() {
         return {
            listData:[], 
            pagination: {
                pageSize: 3,
                total: 0,
                showLessItems: true,
                onChange: page => { }
            },
            targetObj: {},
              paramName: "",
         }
           
            
     },
     computed: {
  ...mapState({
    siteInfo: state => state.siteInfo.siteInfo
  })
},
  created() {
        let _this  =  this;    
     _this.pagination.onChange= function(page) { 
                let url ="/category/"+_this.paramName+"/page/"+page;
                _this.$router.push(url); 
      };
  },
     mounted() { 
     },
    methods: {
        moment,
  },
}
</script>

  <style lang="scss"   scoped>
@import "@/assets/css/qy-index.scss"
</style>