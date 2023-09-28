<template>
     <a-row > 
         <a-row class="single-header"  >
                <a-card    >
                    <!-- <a-row class="header-wrap"> -->
                            <h1 class="h2">{{postData.title}}</h1>
                            <a-row class="header-meta">
                            
                                <span class="meta-date"><time class="entry-date"  :datetime="postData.publishedDateTime" :title="postData.publishedDateTime">{{postData.publishedDateMD }}</time></span>
                                <span class="separator" role="separator">·</span>
                                <span class="meta-user">By/{{postData.author.nickname}}</span>
                                 <span class="separator" role="separator">·</span>
                                <span class="post-meta">
                                    <a class="post-meta-views" href="javascript: void(0)">   <a-icon type="eye" />   <span class="num">{{postData.viewCount}}</span>  </a>
                                    <a class="post-meta-comments" href="javascript: void(0)">    <a-icon type="message" /> <span class="num">{{postData.commentCount}}</span>   </a>
                                </span>
                            </a-row>
                        
                    <!-- </a-row> -->
                </a-card>
        </a-row>
        <a-row class="single-body">
            
            <a-row class="" v-highlight> 
                    <div  class="markdown-body"   v-html="postData.contentHtml" style="white-space: pre-wrap;"></div>
            </a-row>
             <a-row v-show="linkList && linkList.length>0">
                    <ul class="ant-row" style="
    margin: 0;
    padding: 0;
"> 
                        <li v-for="link in linkList" :key="'link-' + link.id" class="link-li ant-col ant-col-xs-12  ant-col-md-8 ant-col-lg-6"     > 
                            <a-row class="link-div">
                            <img src="/favicon.ico" v-real-img="'//'+link.url+'/favicon.ico'" class="link-img">
                            <a  :href="'//'+link.url"  :title="link.name" target="_blank" rel="noopener">{{link.name}}</a>
                            <p>{{link.description}}</p>
                            </a-row>
                        </li>
                    </ul>     
             </a-row>
        </a-row>
        <a-row class="single-comments"  v-if="siteInfo.site_comment_flag =='true' && postData.commentAgentFlag == true"> 
                <qy-comment-list :commentAgentId= "postData.commentAgentId"   ></qy-comment-list>
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
          },
          linkList: {
              default: []
          }
      },
          computed: {
            ...mapState({
                siteInfo: state => state.siteInfo.siteInfo, 
        })
    },
})
</script>

<style lang="scss" scoped>
@media (max-width: 480px) {
#main>.post>.single-body>.article-header>.post-meta {
    display: block;
    width: 100%;
    margin-top: 10px;
}
}

</style>

<style lang="scss" scoped>

 

.single-header {
    background: #fff;
    text-align: center
}
.ant-card-bordered {
    // background-size: cover;
    // background-position: center;
    // color: #fff;
    // overflow: hidden;
    // z-index: 1;
    // width: 100%;
    // height: 400px;
    // .header-wrap {
    //     position: absolute;
    //     bottom: 40px;
    //     left: 0;
    //     padding-right: 60px;
    //     padding-left: 25px;
    //     width: 100%;
    //     z-index: 2;
    // }
    h1 {
    padding: 16px 0 0;
    margin: 24px 0 0;
    // border-top: 1px dotted rgba(255,255,255,.3);
    // color: #fff;
    font-weight: 600;
    }
    .h2   {
    font-size: 2rem;
}

}
//  .ant-card-bordered::before {
//         position: absolute;
//         top: 0;
//         bottom: 0;
//         left: 0;
//         right: 0;
//         content: '';
//         background-color: rgba(0,0,0,.55);
//     }
 
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
.single-comments {
        background: #fff;
    margin-top: 30px;
    padding: 20px;
}
.post-tags {
        display: table-cell;
    padding-right: 40px;
}
.post-meta{ 
    padding-left: 1px;
    white-space: nowrap;
    vertical-align: top;
    width: 1%;
    a{
font-size: 0.9rem;
    line-height: 24px;
    padding: 0 5px;
    color: rgba(0, 0, 0, 0.65);
    }
}

.link-li {
    line-height: 1.8;
    opacity: .9;
    text-align: center;
}
.link-div { 
    background: #e6faf0;
    margin: 10px;
    padding: 10px;
    position: relative;
    border: 1px solid #d5efe2;
    border-radius: 2px;
}
 
.link-img {
    width: 30px;
    height: 30px!important;
    box-shadow: none;
    margin-right: 10px!important;
    vertical-align: bottom;
    display: inline-block!important;
    position: absolute;
    bottom: -15px;
    left: 50%;
    margin-left: -15px!important;
    margin-bottom: 0!important;
    border-radius: 50%;
    padding: 1px;
    border: 1px solid #bbb;
    background: #fff;
    -webkit-transition: all .5s ease;
    -moz-transition: all .5s ease;
    transition: all .5s ease;
}
</style>