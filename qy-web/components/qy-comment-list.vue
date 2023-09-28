<template>
    <a-row>
        <qy-replies-form  ref="repliesTopForm"  v-if="siteCommentFlag == 'true' &&postCommentFlag == true"     :commentAgentId="agentId"  :afterSubmit="repliesFormAfterSubmit"> </qy-replies-form>
       <a-row class="commentClose"  v-else>  <h2>评论已关闭</h2> </a-row>
        <a-list
            v-if="comments.length"
            :pagination ="pagination" 
            :data-source="comments"
            :header="`${pagination.total} 条评论`"
            item-layout="horizontal"
            >
      <a-list-item slot="renderItem" slot-scope="item, index" class="commentItem"> 
            <a-comment
            :author="item.authorName" 
            :datetime="item.createdDate"
            >
            <a-avatar
                icon="user"
                size="large"
                    slot="avatar"
                    :src="item.avatar"
                    alt="C"/>
            <template slot="actions">        
                <span > 
                    <a-row>
                    <a href="javascript:void(0)"  @click="onShow(index)">回复</a>
                    </a-row>
                    <qy-replies-form  :ref="repliesForm[index]"   v-show="repliesFormShow[index]"  :repliesUser="item.authorName" :repliesIndex="repliesIndex" :afterSubmit="repliesChildFormAfterSubmit"  :commentAgentId="agentId"  :parentCommentId="item.id"> </qy-replies-form>
                </span>
            </template>
                <p slot="content"> 
                    {{item.content}}
                </p>
            <qy-comment-child-list  v-show="item.replyCount>0"   :ref="repliesChildList[index]"   :parentId="item.id"  :commentAgentId="agentId"  :afteReplieShow="parentReplieChange"></qy-comment-child-list>

             </a-comment>
        </a-list-item>
    </a-list>
    </a-row>
</template>

<script >
import Vue from 'vue'
import  QyCommentChildList from '~/components/qy-comment-child-list.vue'
import  QyRepliesForm from '~/components/qy-replies-form.vue'
import   md5 from 'js-md5'
 
export default Vue.extend({
    
    props: {  
        commentAgentId: null,
        site_comment_flag: false,
        post_comment_flag:false
    },
    components: {
        QyCommentChildList,
        QyRepliesForm
    },
    data() {
        return {
              comments: [],
              pagination:{
                  onChange: page => {
                    let _this  = this;
                    _this.repliesChildList = ["repliesChildList0"];
                    _this.comments = [];
                    _this.list({
                            page: page,
                            size: _this.defPageSize
                    })
                },
              },
              repliesIndex: null,
              defPageSize: 10,
              agentId: null,
              repliesFormShow: [false],
              repliesChildList:["repliesChildList0"],
              repliesForm:["repliesForm0"],
              siteCommentFlag: false,
              postCommentFlag: false
        }
    },
    watch: {
        site_comment_flag(val) {
            let _this = this;
            _this.siteCommentFlag = val;
        },
        post_comment_flag(val) {
            let _this = this;
            _this.postCommentFlag = val;
        }
    },
    mounted() {
           let _this = this;
         _this.agentId = _this.commentAgentId;
        _this.siteCommentFlag = _this.site_comment_flag;
        _this.postCommentFlag = _this.post_comment_flag;
         _this.initData();
    },
    methods: {
        initData() {
            let _this = this;
            _this.list({
                page: 1,
                size: _this.defPageSize
            });
        },
        list(pageInfo)  {
            let _this = this;
            let params = {
                ...pageInfo
            } ;
            _this.$axios.get('/web/comment/' +  _this.agentId, { params: params })
                .then(res => {
                    let resp  = res.data;
                    if(resp.success) {
                        _this.comments = resp.content.list;
                        _this.comments.forEach((e,i)  => { 
                            // e.avatar =  'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png';
                             if(e.user && e.user.avatar) {
                                e.avatar =  e.user.avatar;
                            } else {
                                  e.avatar =  '//sdn.geekzu.org/avatar/'+e.authorEmail;
                            }
                            Vue.set(_this.repliesFormShow,i, false);
                             _this.repliesForm[i] = "repliesForm" + i; 
                             _this.repliesChildList[i] = "repliesChildList" + i;
                        });
 
                    }
                    _this.pagination.total =   resp.content.total;
                    _this.pagination.current =   resp.content.page;
                    _this.pagination.pageSize =   resp.content.size; 
          });
        },
        onShow(index) {
            let _this = this;
            let nowFlag =  !_this.repliesFormShow[index] ;
            Vue.set(_this.repliesFormShow, index,nowFlag ); 
            _this.repliesFormShow.forEach((e,i)=>{
                if(i!=index && e == true) {
                    Vue.set(_this.repliesFormShow, i, false );
                     _this.$refs[_this.repliesForm[i]].resetForm();
                }
                if(nowFlag) {
                    _this.$refs[_this.repliesChildList[i]].initChildRepliesForm(); 
                    _this.repliesIndex = index;
                }
            })
            if(!nowFlag) {
                  _this.$refs[_this.repliesForm[index]].resetForm();
                 
            } 

        },
        parentReplieChange(falg) {
            let _this = this;
            if(falg) {
                _this.repliesFormShow.forEach((e,i)=>{
                    if( e == true) {
                        Vue.set(_this.repliesFormShow, i, false );
                        _this.$refs[_this.repliesForm[i]].resetForm();
                    }
                })
            }
        },
        repliesFormAfterSubmit(params) {
            let _this  = this; 
            _this.$refs.repliesTopForm.resetForm();
            _this.initData();

        },
        repliesChildFormAfterSubmit(index) {
            let _this = this;
            if(_this.comments[index].replyCount ==0) {
                _this.comments[index].replyCount  = 1;
            }
              _this.$refs[_this.repliesChildList[index]].initData(); 
              _this.onShow(index);
        },

    }
    
})
</script>

<style  lang="scss"  scoped>
.commentClose{
    padding: 10px 0;
}
.commentItem {
    line-height: 1;
    /deep/ .ant-comment{
        width: 100%;
        .ant-comment-actions {
            width: 100%;
            li {
                width: 100%;
            }
        }
    }
}
 
</style>