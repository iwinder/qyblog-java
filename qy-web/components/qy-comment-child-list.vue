<template>
    <a-row>
        <a-list
            v-if="comments.length"
            :data-source="comments"
            :pagination ="pagination" 
            :header="`${totalSize} 条评论`"
            item-layout="horizontal"
            >
            <a-list-item slot="renderItem" slot-scope="item,index" class="childItem">
                <a-comment  
                    :datetime="item.createdDate" >
                <a-avatar
                    icon="user"
                    size="large"
                        slot="avatar"
                        :src="item.avatar"
                        alt="C" 
                />
                <template slot="author">
                    <template v-if="item.user&&item.user.nickname">
                          <a-badge>
                                   {{item.user.nickname}} 
                              <!-- <img src="/img/icon/ico-comment--admin.png"> -->
                          <a-icon type="sketch" :style="{ color: 'hotpink' }"/>
                          </a-badge>
                     </template>
                     <template v-else>
                         {{item.authorName}}
                     </template>

                </template>
                <span slot="actions">
                    
                    <a-row>
                    <a href="javascript:void(0)"  @click="onShow(index)">回复</a>
                    </a-row>
                    <qy-replies-form   :ref="repliesChildForm[index]"  v-show="repliesFormChildShow[index]"  :repliesUser="(item.user&&item.user.nickname)?item.user.nickname:item.authorName" :commentAgentId="agentId"  :parentCommentId="item.id"   :afterSubmit="repliesFormAfterSubmit">> </qy-replies-form>
                </span>
                <p slot="content"> 回复 <a href="javascript:void(0)"  >{{item.parent.authorName}} </a> ：{{item.content}} 
                </p>
          

                </a-comment>
            </a-list-item>
        </a-list>
    </a-row>
</template>

<script  >
import Vue from 'vue'
import  QyRepliesForm from '~/components/qy-replies-form.vue' 


const AdminIconSvg = {
  template: `
    <svg viewBox="0 0 1024 1024" width="1em" height="1em" fill="currentColor">
      <path d="M99.096 315.634s-82.58-64.032-82.58-132.13c0-66.064 33.032-165.162 148.646-148.646 83.37 11.91 99.096 165.162 99.096 165.162l-165.162 115.614zM924.906 315.634s82.58-64.032 82.58-132.13c0-66.064-33.032-165.162-148.646-148.646-83.37 11.91-99.096 165.162-99.096 165.162l165.162 115.614z" fill="#6B676E" p-id="1143" />
      <path d="M1024 561.548c0 264.526-229.23 429.42-512.002 429.42S0 826.076 0 561.548 283.96 66.064 512.002 66.064 1024 297.022 1024 561.548z" fill="#FFEBD2" p-id="1144" />
      <path d="M330.324 842.126c0 82.096 81.34 148.646 181.678 148.646s181.678-66.55 181.678-148.646H330.324z" fill="#E9D7C3" p-id="1145" />
      <path d="M644.13 611.098C594.582 528.516 561.55 512 512.002 512c-49.548 0-82.58 16.516-132.13 99.096-42.488 70.814-78.73 211.264-49.548 247.742 66.064 82.58 165.162 33.032 181.678 33.032 16.516 0 115.614 49.548 181.678-33.032 29.18-36.476-7.064-176.93-49.55-247.74z" fill="#FFFFFF" p-id="1146" />
      <path d="M611.098 495.484c0-45.608 36.974-82.58 82.58-82.58 49.548 0 198.194 99.098 198.194 165.162s-79.934 144.904-148.646 99.096c-49.548-33.032-132.128-148.646-132.128-181.678zM412.904 495.484c0-45.608-36.974-82.58-82.58-82.58-49.548 0-198.194 99.098-198.194 165.162s79.934 144.904 148.646 99.096c49.548-33.032 132.128-148.646 132.128-181.678z" fill="#6B676E" p-id="1147" />
      <path d="M512.002 726.622c-30.06 0-115.614 5.668-115.614 33.032 0 49.638 105.484 85.24 115.614 82.58 10.128 2.66 115.614-32.944 115.614-82.58-0.002-27.366-85.556-33.032-115.614-33.032z" fill="#464655" p-id="1148" />
      <path d="M330.324 495.484m-33.032 0a33.032 33.032 0 1 0 66.064 0 33.032 33.032 0 1 0-66.064 0Z" fill="#464655" p-id="1149" />
      <path d="M693.678 495.484m-33.032 0a33.032 33.032 0 1 0 66.064 0 33.032 33.032 0 1 0-66.064 0Z" fill="#464655" p-id="1150" />
    </svg>
  `,
};

const AdminIcon = {
  template: `
    <a-icon :component="AdminIconSvg" />
  `,
  data() {
    return {
     AdminIconSvg,
    };
  },
};
export default Vue.extend({
    props: {  
        parentId: null,
        commentAgentId: null,
        afteReplieShow: {
            type: Function,
            default: null
        },  
    },
    components: {
        QyRepliesForm 
    },
    data() {
        return {
              comments: [],
              topParentId: null,
              agentId: null,
              defPageSize: 5,
              pagination:  false,
              repliesFormChildShow: [false],
              repliesChildForm: ["repliesChildForm0"],
              totalSize: 0
        }
    },
    watch: {
        commentAgentId(val) {
            let _this = this;
            _this.agentId = val;
        }
    },
    mounted() {
        let _this = this;
         _this.agentId = _this.commentAgentId;
         _this.topParentId = _this.parentId;
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
            _this.$axios.get('/web/comment/' +  _this.agentId + "/"+ _this.topParentId+"/replies", { params: params })
                .then(res => {
                    let resp  = res.data				
                    if(resp.success) {
                        _this.comments = resp.content.list;
                        _this.comments.forEach((e,i)  => {
                            if(e.user && e.user.avatar) {
                                e.avatar =  e.user.avatar;
                            } else {
                                  e.avatar =  '//sdn.geekzu.org/avatar/'+e.authorEmail;
                            }
                            // e.avatar =  'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png';
                              Vue.set(_this.repliesFormChildShow,i, false);
                              _this.repliesChildForm[i] = "repliesChildForm"+i; 
                        });
                    }
                    if(resp.content.total  > resp.content.size ) {
                        if (resp.content.page==1) {
                                _this.pagination = {
                                        total:   resp.content.total,
                                        current:  resp.content.page,
                                        pageSize: resp.content.size,
                                        onChange: page => {
                                            let _this  = this;
                                            _this.list({
                                                    page: page,
                                                    size: _this.pagination.pageSize
                                            })
                                         },

                                };
                        }
                        _this.pagination.total = resp.content.total;
                        _this.pagination.current =   resp.content.page;
                        _this.pagination.pageSize =   resp.content.size; 
                        _this.totalSize = resp.content.total;
                    } else {
                        _this.pagination = false;
                          _this.totalSize = _this.comments.length;
                    }
   
          });
        },
        onShow(index) {
            let _this = this;
            let nowFlag =   !_this.repliesFormChildShow[index];
            Vue.set(_this.repliesFormChildShow, index,nowFlag );
            // _this.repliesFormShow[index]=;
            _this.repliesFormChildShow.forEach((e,i)=>{
                if(i!=index && e == true) {
                    _this.$refs[_this.repliesChildForm[i]].resetForm();
                    Vue.set(_this.repliesFormChildShow, i, false );
           
                }
            })
            _this.afteReplieShow(nowFlag);
       

        },
        initChildRepliesForm() {
            let _this = this;
            _this.repliesFormChildShow.forEach((e,i)=>{
                if( e  == true) {
                      _this.$refs[_this.repliesChildForm[i]].resetForm();
                    Vue.set(_this.repliesFormChildShow, i, false );
                  
                }
            })
        },
        repliesFormAfterSubmit(params) {
            let _this  = this;
            _this.initData();

        },
    }
})
</script>
<style   lang="scss" scoped>



// /deep/ .ant-list-item{
   

// }
/deep/.ant-list-items {
    .ant-list-item{
        margin: 0;
        padding: 0;
   
    }

}
.childItem {
    line-height: 1;

}
 
</style>