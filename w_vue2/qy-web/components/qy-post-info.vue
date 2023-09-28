<template>
     <a-row> 
         <a-row class="single-header"  >
                <a-card  class="header-card"  :style="{backgroundImage:'url(' + postData.defImg + ')'}"  v-real-background-img="postData.thumbnail">
                    <a-row class="header-wrap">
                            <a-row class="header-meta">
                                    <template v-if="postData.category!=null">
                                <span class="meta-category">
                                    <nuxt-link :to=" {name:'category-name',  params:{name:postData.category.identifier, aid:postData.category.id} }" rel="category tag"> {{postData.category.name}}</nuxt-link>
                                    </span>
                                <span class="separator" role="separator">·</span></template>
                                <span class="meta-date"><time class="entry-date"  :datetime="postData.publishedDateTime" :title="postData.publishedDateTime">{{postData.publishedDateMD }}</time></span>
                                    <span class="separator" role="separator">·</span>
                                    <span class="meta-user">By/{{postData.author.nickname}}</span>
                            </a-row>
                            <h1 class="h2">{{postData.title}}</h1>
                    </a-row>
                </a-card>
        </a-row>
        <a-row class="single-body">
            <a-row class="article-header">
                <template v-if="postData.tags!=null">
                    <a-col  class="post-tags"  :xs="{span:24}"  :sm="{ span: 16}"> 
                        <a-tag color="#f50" v-for=" tag in postData.tags" :key="tag.id">
                             <nuxt-link :to=" {name:'tag-name',  params:{name:tag.identifier, aid:tag.id} }"> {{tag.name}} </nuxt-link>
                        </a-tag>
                    </a-col>
                </template>
                <a-col class="post-meta" :xs="{span:24}"  :sm="{ span: 4,offset: 3}" :lg="{offset: 2}" :xl="{offset: 4}"> 
                      <a class="post-meta-views" href="javascript: void(0)">   <a-icon type="eye" />   <span class="num">{{postData.viewCount}}</span>  </a>
                     <a class="post-meta-comments" href="javascript: void(0)">    <a-icon type="message" /> <span class="num">{{postData.commentCount}}</span>   </a>
                     
                </a-col>

            </a-row> 
            <a-row class="" v-highlight> 
                    <div  class="markdown-body"   v-html="postData.contentHtml" style="white-space: pre-wrap;" v-lazy-container="{ selector: 'img',loading: '/img/image-pending.gif' }" @click="previewPicture($event)"></div>
            </a-row>
            <a-row >
                   <a-modal :visible="previewVisible" title="预览" :footer="null" @cancel="handleCancel" key="preview"  width="80%">
                         <a-icon type="close-circle" slot="closeIcon"/>  
                        <img alt="example" style="width: 100%" :src="previewImage"  />
                    </a-modal>
            </a-row>

            <a-row class="single-copyright"> 
              除特别注明外，本站所有文章均为<a :href="siteInfo.site_url">{{siteInfo.site_name}}</a>原创，转载请注明出处来自<a :href="siteInfo.site_url+'/'+postData.permaLink">{{siteInfo.site_url}}/{{postData.permaLink}}</a>
              <a-row class="license-tag"><a href="https://creativecommons.org/licenses/by-sa/4.0/deed.zh" target="_blank"><span class="name">License</span> <span class="type">CC BY-SA 4.0</span>
              以商业目的使用本网站原创内容需获许可，非商业目的使用授权遵循CC BY-NC 4.0
              </a></a-row>
          </a-row>
        </a-row> 
        <a-row class="single-comments" > 
                <qy-comment-list :commentAgentId= "postData.commentAgentId" :site_comment_flag="siteInfo.site_comment_flag" :post_comment_flag="postData.commentAgentFlag"></qy-comment-list>
        </a-row>

    </a-row>
</template>

<script  >
import Vue from 'vue'
 import  QyCommentList from '~/components/qy-comment-list.vue'
 import { mapState } from 'vuex'
export default Vue.extend({
    components: {
        QyCommentList
    },
    props: { 
        postData: {
            default: {}
        }
    },
    computed: {
        ...mapState({
            siteInfo: state => state.siteInfo.siteInfo
        })
    },
    data() {
        return {
            previewVisible:false,
            previewImage:""

        }
    },
    methods: {
        previewPicture: function(e) {  
            if (e.target.tagName == 'IMG') {
                this.previewImage = e.target.src;
                this.previewVisible = true;
            }
        },
        handleCancel: function(){
            this.previewVisible = false;
            this.previewImage = "/img/image-pending.gif";
        }
    }
})
</script>
 

<style lang="scss" scoped>

 

.single-header {
    background: #fff;
}
.ant-card-bordered {
    background-size: cover;
    background-position: center;
    color: #fff;
    overflow: hidden;
    z-index: 1;
    width: 100%;
    height: 400px;
    .header-wrap {
        position: absolute; 
        left: 0; 
        width: 100%;
        z-index: 2;
    }
    h1 {
    padding: 16px 0 0;
    margin: 24px 0 0;
    border-top: 1px dotted rgba(255,255,255,.3);
    color: #fff;
    font-weight: 600;
    }
  

    

}
 .ant-card-bordered::before {
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        content: '';
        background-color: rgba(0,0,0,.55);
    }
 
.single-body {
    padding: 20px;
    background: #fff;
}
 .markdown-body  /deep/ img {
    margin: 5px 0;
}
.article-header {
        margin-bottom: 30px;
        
}
.single-copyright{
    border-radius: 3px;
    background: #fcf8e3;
    margin: 20px 0;
    padding: 12px 20px 12px 40px;
    font-size: 0.8rem;
    position: relative;
    color: #000;
    word-break: break-all;
}
.single-comments {
        background: #fff;
    margin-top: 30px;
    padding: 20px;
}
.post-tags {
        display: table-cell;
    padding-right: 40px;
}
.post-meta {
    display: table-cell;
    padding-left: 1px;
    white-space: nowrap;
    vertical-align: top;
    width: 1%;
    a{
        font-size: 1.1rem;
        line-height: 24px;
        padding: 0 5px;
        color: #34495e;
    }
}
@media (max-width: 576px) {
   .post-meta {
        display: block;
        width: 100%;
        margin: 10px 0px;
    }
    .header-card {
        height: 210px;
    }
    .header-wrap {
        // top:0;
        bottom: 0;
        padding: 40px 20px;
        overflow: hidden;
        .h2   {
            font-size: 1.2rem;
            margin: 10px 0 0;
            padding: 10px 0 0;
        } 
    }
}
@media (min-width: 577px) {
    .ant-card-bordered {
        .header-wrap {
            bottom: 40px;
            padding-left: 25px;
            .h2   {
                font-size: 2rem;
            } 
        }
      
    }
    

}
.license-tag {
        margin: 2px 0;
}
 .license-tag  .name,
      .license-tag .type {
    text-align: center;
    padding: 1px 3px;
    font-size: 0.1rem;
    display: table-cell;
      }
   .license-tag   .name {
        background-color: #409eff;
    color: #fefefe;
    border: 1px solid #409eff;
    font-weight: 700;

    }
      .license-tag  .type{
background-color: #fefefe;
    color: #409eff;
    border: 1px solid #409eff; 
}
</style>