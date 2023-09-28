<template>
    <a-row>
    <a-row class="commentClose"  v-if="parentId==null">  <h2>参与评论</h2> </a-row>
        <a-comment>
            <a-avatar
                slot="avatar"
                :src="'//sdn.geekzu.org/avatar/'+commentForm.authotImg"
                :alt="commentForm.authorName"
                v-if="!isInitForm"
            />
        <div slot="content">
                <a-form-model ref="commentForm" :model="commentForm" :rules="rules" v-bind="layout"> 
                    <a-row  v-if="isInitForm">
                        <a-col   :xs="{span:24}"  :lg="{ span: 8}">
                            <a-form-model-item    prop="authorName">
                                <a-input   v-model="commentForm.authorName"   placeholder="昵称" />
                            </a-form-model-item>
                        </a-col>
                        <a-col   :xs="{span:24}"  :lg="{ span: 8}">
                        <a-form-model-item    prop="authorEmail">
                            <a-input   v-model="commentForm.authorEmail"    placeholder="邮箱"/>
                        </a-form-model-item>
                        </a-col>
                        <a-col   :xs="{span:24}"  :lg="{ span: 8}">
                            <a-form-model-item    prop="authorUrl">
                                <a-input   v-model="commentForm.authorUrl" placeholder="网址"/>
                            </a-form-model-item>
                                    </a-col>
                    </a-row>
                    <a-row  v-else style="margin-bottom: 10px;">
                    <a-col>  <a-tooltip placement="topLeft"  :title="commentForm.authorEmail" arrow-point-at-center> {{ commentForm.authorName}}  
                    </a-tooltip> 
                    <a-button type="dashed" @click="opneEditLink()" size="small">
                    编辑
                    </a-button>
                    </a-col>
                    </a-row>
                    
                <a-form-model-item has-feedback  prop="content">
                    <a-textarea :rows="4" v-model="commentForm.content"   :placeholder="placeholderText" />
                </a-form-model-item>
                <a-form-model-item>
                    <a-button html-type="submit" :loading="submitting" type="primary" @click="handleSubmit">
                    回复
                    </a-button>
                </a-form-model-item>
            </a-form-model>
        </div>
        </a-comment>
    </a-row>
</template>
<script  >
import Vue from 'vue'
import { FormModel,Comment,Avatar, Tooltip } from 'ant-design-vue';

Vue.use(FormModel,Comment,Avatar,Tooltip); 
// Vue.use(Cookies); 
export default Vue.extend({
        props: { 
            commentAgentId: null,
            parentCommentId: null,
            repliesIndex: null,
            repliesUser: null,
            afterSubmit: {
                type: Function,
                default: null
            }, 
            afterPageChange: {
                type: Function,
                default: null
            }
    },
    watch: {
        commentAgentId(val) {
            let _this  = this;
            _this.agentId = val;
        },
        parentCommentId (val) {
            let  _this = this;
            _this.parentId = val;

        },
       repliesIndex (val) {
            let _this  = this;
            _this.parentIndex = val;
        },
        repliesUser(val) {
            let _this = this;
            _this.parentUser = val;
            if(val != null) {
                _this.placeholderText = "回复@"+val;
            }
        }
    },
    mounted() {
            let _this = this;
            _this.agentId = _this.commentAgentId;
            if(_this.parentCommentId) {
                 _this.parentId = _this.parentCommentId;
            }
            if(   _this.repliesIndex) {
                _this.parentIndex = _this.repliesIndex;
            } 
            if(_this.repliesUser != null) {
                _this.placeholderText = "回复@"+_this.repliesUser;
            }
            _this.initCommentByCookie();
 
    },
     data() {
         return {
             agentId: null,
             parentId: null,
             parentIndex : null,
             submitting: false,
             isInitForm: true,
             parentUser: null,
             commentForm: {
                content: '',
                authorName: '',
                authorEmail: '',
                authorUrl: '',
                authorImg:""
            },
            rules: {
                authorName :[
                    {required: true,whitespace: true, message: "昵称不可为空",  trigger:"change"}
                ],
                authorEmail: [
                    {required: true,whitespace: true, message: "邮箱不可为空",  trigger:"change"},
                    {pattern:new RegExp(/^([a-zA-Z\d])(\w|\-)+@[a-zA-Z\d]+\.[a-zA-Z]{2,6}$/ig), message: "邮箱格式错误"}
                          
                ],
                content: [
                    {required: true,whitespace: true, message: "内容不可为空",  trigger:"change"}
                ]
            },
             layout: {
                labelCol: { span: 1 },
                wrapperCol: { span: 23 },
            },
            placeholderText: "评论内容"
         }
     },
     methods: {
         handleSubmit() {
             let _this = this;
             _this.$refs.commentForm.submitting = true;
            _this.$refs.commentForm.validate(valid => {
                if (valid) { 
                    let val = _this.commentForm;
                    let url = '/web/comment/'+_this.agentId;
                    url = _this.parentId !==null? url + "/" +_this.parentId+"/replies/add" :  url +"/add";
                    _this.$axios.post(url,val).then(res => {
                            _this.$refs.commentForm.submitting = false; 
                            if(res.data.success) {
                                 _this.$refs.commentForm.content = null;
                                 _this.$notification.success({
                                    key:"coment-notif",
                                    message: '评论提交成功',
                                    description: '若未及时展示，可能需要等待管理员审核。',
                                });
                                _this.afterSubmit( _this.parentIndex);
                                            _this.isInitForm = false;
                                _this.initCommentByCookie();
                            } else {
                                _this.$notification.error({
                                    key:"coment-notif",
                                    message: '保存失败',
                                    description: res.data.message,
                                }); 
                            }
                    }).catch((response) => {
                        _this.$refs.commentForm.submitting = false;
                        _this.$notification.error({
                                key:"coment-notif",
                                message: '保存失败',
                                description: response,
                        });  
                    });
                       
                } else {
                    //  _this.$message.warning('校验失败，请根据提示修改',5); 
                    return false;
                }
            });
         },
         handleChange(e) {
            this.value = e.target.value;
        },
        resetForm() {
                this.$refs.commentForm.resetFields();
        },
         initCommentByCookie() {
            let _this  = this;
            _this.$QyServeTool().initCommentByCookie( _this.commentForm);
             _this.isInitForm = _this.commentForm.authorName? false : true; 
        },
        opneEditLink() {
            let _this = this;
            _this.isInitForm = true;
        }
    }
})
</script>