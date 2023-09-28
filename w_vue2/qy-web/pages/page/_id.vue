<template>
    <a-row class="content">

        <a-col :xs="{span:24}"  :lg="{ span: 16}"  class="content-left">  
           <h2 v-if="!isSearchFlag">近期文章</h2>
          <h2 v-else>搜索 “{{searchText}}”  的结果</h2>
           <qy-post-list  :pagination="pagination" :listData="listData" ></qy-post-list>
        </a-col>

       <a-col :xs="{span:24}"      :lg="{  span: 7, offset: 1 }" class="content-right" >  
         <qy-post-right-sider></qy-post-right-sider>
        </a-col>

    </a-row>
</template>

<script  >
import Vue from 'vue'
import QyPostList from '~/components/qy-post-list.vue'
import QyPostRightSider from '~/components/qy-post-right-sider.vue'
  import { mapState } from 'vuex'
import moment from 'moment';
export default Vue.extend({
      components: { 
    QyPostList,
    QyPostRightSider
  },
  
    validate ({ params }) {
        // 必须是number类型
        return /^\d+$/.test(params.id)
    },
         async fetch({ store, params }) {
  // await store. dispatch('siteInfo/getSiteInfo');
},
    async  asyncData (context) { 
        let _this = context; 
        let searchText =  _this.query.searchText; 
        let isSearchFlag = false;
        if(_this.$QyServeTool().isNotEmpty(searchText)) {
               isSearchFlag = true;
        }
         let res1 =  {};
         try{
           if(parseInt(_this.params.id)>2147483647) { 
              _this.error({ statusCode: 404, message: "我们是有底线的"});
           } else {
              res1 = await   _this.$axios.get('/web/articles',{ params: {
                  searchText: searchText,
                            page:  _this.params.id,
                size:  10
            } }).then(res => {
                let resp  = res.data				
                let result = {};
                let listData = [];
                if(resp.success) {
                    let data = resp.content.list; 
                    let defImg = "/img/image-pending.gif";
                    data.forEach((e,i ) => {  
                            
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
                            status: e.status,
                            author: e.author,
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
                    pageSize:  resp.content.size,
                    };
              }else {
                if(resp.code == '404') {
                    _this.error({ statusCode: 404, message: resp.message});
                } else  {
                    _this.error({ statusCode: 500, message: resp.message});
                }
            }    
                return result;

            });
           }
          
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
                searchText: searchText,
                isSearchFlag: isSearchFlag 
            }
    },
    head () {
        return {
            title:   (this.isSearchFlag? "搜索 "+ this.searchText +" - "+ this.siteInfo.site_name: this.siteInfo.site_name)  + " --第" +this.pagination.current+"页" ,
            meta: [
               { hid: "keywords", name: "keywords", content: this.siteInfo. site_key},
                { hid: "description", name: "description", content: this.siteInfo. site_description},

            ],
            link: [
                      {rel:"canonical" , href: this.siteInfo.site_url+this.$nuxt.$route.path}
            ],
            
        }
    },
  created() {
    let _this  =  this;  
     _this.pagination.onChange= page => { 
                        let baseUrl =  "/page/"+page;
                        if(page==1) { 
                              baseUrl = "/"
                        } 
                        let url  = baseUrl;  
                        if(_this.isSearchFlag) {
                            url = url +"?searchText="+_this.searchText;
                          } 
                              _this.$router.push(url); 
                      };
  },
    watchQuery: ['searchText'],
    computed: {
    ...mapState({
      siteInfo: state => state.siteInfo.siteInfo
    })
  },
    data() {
      return {
        listData:[],
        data:[],
        pagination: {
          onChange: page => { 
          },
          pageSize: 3,
          total: 23,
          showLessItems: true,
        },
        isSearchFlag: false,
        searchText: "",
        actions: [
      
          { type: 'like-o', text: '156' },
          { type: 'message', text: '2' },
        ],
    }
  },
  methods: {
     moment,
  }
})
</script>

<style lang="scss"   scoped>
@import "@/assets/css/qy-index.scss"
</style>