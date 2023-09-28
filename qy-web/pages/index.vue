<template>
  <a-row class="content">
    <a-col :xs="{span:24}"    :lg="{ span: 16}"  class="content-left"> 
          <h2 v-if="!isSearchFlag">近期文章</h2>
           <h2 v-else>搜索 “{{searchText}}”  的结果</h2>
       <qy-post-list  :pagination="pagination" :listData="listData" ></qy-post-list>
    </a-col>

  <a-col :xs="{span:24}"      :lg="{  span: 7, offset: 1 }" class="content-right" > 

<qy-post-right-sider></qy-post-right-sider>
 
  </a-col>

</a-row>


  
</template>

<script>
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
  
  watchQuery: ['searchText'],
 async asyncData (context) {
   let _this = context;   
  let asearchText =  _this.query.searchText; 
  let isSearchFlag = false;
  if(_this.$QyServeTool().isNotEmpty(asearchText)) {
    isSearchFlag = true;
  }else {
    asearchText = "";
  }
  let res1 = {};
        try{
     res1 = await _this.$axios.get('/web/articles',{ params: {
              page: 1,
              size:  10,
              searchText: asearchText
         } }).then(res => {
            let resp  = res.data				
            let result = {};
            let listData = [];
            if(resp.success) {
                let data = resp.content.list;
                let defImg = "/img/image-pending.gif"; 
                data.forEach(e  => { 
                    defImg = '/img/thumb/'+ _this.$QyServeTool().randomNum(1,32)+'.jpg'; 
                    listData.push({
                      id: e.id,
                      href:  e.permaLink,
                      title: e.title,
                      thumbnail: e.thumbnail?e.thumbnail:defImg,
                      tagStrings: e.tagStrings,
                      tags: e.tags,
                      status: e.status,
                      category: e.category,
                      avatar: e.author.avatar,
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
                  pageSize:  resp.content.size
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
    } catch (error) {
            if(error.status == 404)  {
                 _this.error({ statusCode: 404, message: error.message});
            } else {
                _this.error({ statusCode: 500, message: error.message});
            }
      }
      // const now = new Date().getTime();
      return {
            listData : res1.listData, 
            searchText: asearchText,
            isSearchFlag: isSearchFlag,
            pagination:{
                total:  res1.total,
                current : res1.current ,
                pageSize : res1.pageSize, 
                showLessItems: true 
              }
          };
        
    },
    data() {
      return {
        listData:[], 
        isSearchFlag: false,
        searchText: "",
        pagination: {
          pageSize: 3,
          total: 0,
          showLessItems: true,
          
        },
    }
  }, 
watch: {
    '$route'(to, from) {
      let _this = this; 
      if (to.fullPath !== from.fullPath) {
        this.$nextTick(() => { // 不加this.$nextTick时，路由跳转后还是上一次的数据，所以需要加上
           // 需要执行的方法 
           if(!to.query.searchText) {
              _this.isSearchFlag = false;
           }
           _this.searchText = to.query.searchText;
          if(_this.pagination) {
            _this.pagination.onChange= function(page) {  
                let url = "/page/"+page;
                if(_this.isSearchFlag) {
                  url = url +"?searchText="+_this.searchText;
                }
                _this.$router.push(url); 
            };
          }
        })
      }
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
                let url = "/page/"+page;
                if(_this.isSearchFlag) {
                  url = url +"?searchText="+_this.searchText;
                }
                _this.$router.push(url); 
      };
  },
  head () {
        return {
           title:   this.isSearchFlag? "搜索 "+ this.searchText +" - "+ this.siteInfo.site_name: this.siteInfo.site_name,
            meta: [
                { hid: "keywords", name: "keywords", content: this.siteInfo. site_key},
                { hid: "description", name: "description", content: this.siteInfo. site_description},

            ],
            link: [
                {rel:"canonical" , href: this.siteInfo.site_url+this.$nuxt.$route.path}
            ],
            
        }
    },
  mounted() { 
      let _this = this;   

  },
  methods: {
    moment,
 
  }
})
</script>


<style lang="scss"   scoped>
@import "@/assets/css/qy-index.scss";
</style>