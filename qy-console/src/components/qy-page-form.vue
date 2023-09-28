<template>
    <div>
            <a-form-model ref="articleForm" :model="articleForm" :rules="rules" v-bind="layout">
                <a-row>
                    <a-col :xs="24"   :lg="24"  >
                        <a-form-model-item has-feedback   prop="title"> 
                            <a-input v-model="articleForm.title" type="text" autocomplete="off" placeholder="添加标题"    @blur="initPermaLink" />
                        </a-form-model-item>
                         <a-form-model-item has-feedback   prop="permaLink">
                             <template v-if="articleForm.permaLink">
                                   <qy-post-link-edit :permaLink="articleForm.permaLink"  :postId="articleForm.id"    :afterSave="updateLink" > </qy-post-link-edit>
                            </template> 
                        </a-form-model-item>

                        <a-form-model-item has-feedback   prop="content">
                            <!-- <a-input v-model="articleForm.content" type="textarea" /> -->
                            <div class="mavonEditor">
                                <mavon-editor  ref="md"  v-model="markdownContent"   
                                placeholder = "添加内容"
                                    defaultOpen="edit"
                                     @imgAdd="markImgAdd" 
                                     @save = "markSave"
                                   @change="markChange"/>
                            </div>
                        </a-form-model-item>
                      <a-form-model-item has-feedback label="发布" prop="published">
                            <a-switch v-model="articleForm.published" >
                                <a-icon slot="checkedChildren" type="check" />
                                    <a-icon slot="unCheckedChildren" type="close" />
                            </a-switch>
                        </a-form-model-item>

                        <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
                            <a-button  ref="saveButton"  type="primary"  :loading ="loading"  @click="submitForm('articleForm')">
                                保存
                            </a-button>
                            <a-button v-show="!articleForm.id" style="margin-left: 10px" @click="resetForm">
                                重置
                            </a-button>
                        </a-form-model-item>
                    </a-col>
                     <!-- <a-col :xs="24"     :lg="9"  >

                    </a-col> -->
             </a-row>
        </a-form-model>
    </div>
</template>

<script>

import Vue from 'vue'
import { FormModel } from 'ant-design-vue';
 import  QyArticleCategoryTreeSelect from './qy-article-category-tree-select.vue';
import  QyPostLinkEdit from"./qy-post-link-edit.vue"
import debounce from 'lodash/debounce';
 import { mapState } from 'vuex'


Vue.use(FormModel);
export default {
    components: {
        QyArticleCategoryTreeSelect,
        QyPostLinkEdit
    },
    props: { 
       articleObj:null,
        afterSubmit: {
            type: Function,
            default: null
        }, 
        
    },
    computed: {
        ...mapState({
        siteInfo: state => state.siteInfo.siteInfo
        })
    },
    data() {
         this.lastFetchId = 0;
        this.fetchTags = debounce(this.fetchTags, 800);
        return {
            articleForm:  {
                    title: '',
                    type: 2,
                    published: true,
                    deleted:  false,
                },
                editParentData:{},
                rules: {

                },
                layout:   {
                    labelCol: { span: 1 },
                    wrapperCol: { span: 22 }
                },
                loading: false,
                categoryId:null,
                fetching: false,
                tagsData:[],
                selectTags:[],
                markdownContent: "",
                htmlContent:" ",
                markdownOption:{
                    toolbarsFlag: true
                },
                imageNum: 0,
                // showPermaLink: false

        }
    },
    watch: {
        articleObj(val) {
             let _this = this;
             _this.articleForm = val;
            // _this.editParentData =  val.category ?val.category : {id:0}
            // _this.articleForm.tagStrings = val.tagStrings? val.tagStrings:[];
            // _this.selectTags = _this.articleForm.tagStrings ;
             _this.markdownContent = val.content;
        }
    },
    mounted() {
        let _this = this;
        if(_this.articleObj) {
            _this.articleForm = _this.articleObj;
            _this.editParentData =  _this.articleObj.category ?_this.articleObj.category : {id:0}
            _this.markdownContent = _this.articleObj.content;
            _this.selectTags = _this.articleForm.tagStrings ;
            // _this.editParentData = _this.articleObj.category
        }
    },
    methods: {
         submitForm(formName) {
            let  _this = this;
            _this.loading = true;
            _this.$refs[formName].validate(valid => {
                if (valid) {
                    if(_this.categoryId ) {
                        _this.articleForm.category  = {
                            id:  _this.categoryId 
                        }
                    }
                    _this.articleForm.content =  _this.markdownContent ;
                    if (_this.htmlContent) {
                       _this.articleForm.contentHtml = _this.htmlContent;
                    }
                    if(_this.selectTags) {
                        _this.articleForm.tagStrings = _this.selectTags;
                    }
                    _this.afterSubmit(_this.articleForm); 
                } else {
                     _this.loading = false;
                    return false;
                }
            });
        },
        resetForm() {
             this.$refs.articleForm.resetFields();
               _this.categoryId = null;
        },
        afterSelectTree(id, obj) {
            let _this = this;
            _this.categoryId = id;
            _this.editParentData = obj.dataRef;
            // _this.selectedParentTreeNode = obj;
        },
        fetchTags(value) {
            if(QyTool.isEmpty(value)) {
                return;
            }
            let _this = this;
             _this.lastFetchId += 1;
              const fetchId = this.lastFetchId;
              _this.tagsData = [];
            _this.fetching = true;
            const params = {
                name: value
            }
             _this.$axios.get("/admin/blogTags/search",{params: params}).then( res => {
                    if (fetchId !== _this.lastFetchId) {
                        return;
                    }
                    let resp  = res.data;
                    if(resp.success) {
                        const data = resp.content.map(tag =>({
                            text: tag.name,
                            value: tag.name
                        }));
                        _this.tagsData = data;
                        this.fetching = false;
                    }
             });

            
        },
        handleChange(value) {
            let  _this = this;
            // this.value = value;
            // fetch(value, data => (this.data = data));
            _this.selectTags = [...value];
            Object.assign(this, {
                value,
                data: [],
                fetching: false,
            });
        },
        markChange(value, render) {
            let _this = this;
           
            _this.htmlContent = render;
         
        },
        markImgAdd(pos, files) {
            // 第一步.将图片上传到服务器.
            let _this = this;
            var formdata = new FormData();
            formdata.append('file', files);
             _this.$axios.post('/admin/upload/file', formdata).then((res) => {
                let resp = res.data;
                if (resp.success) {
                     // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
                    _this.$refs.md.$img2Url(pos, resp.content.relativePath);
                    _this. imageNum ++;
                    if ( _this.imageNum==1 && !_this.articleForm.thumbnail ) {
                            _this.articleForm.thumbnail  = resp.content.relativePath;
                    }
                }
               
            })
        },
        markSave(value, render) {
                let  _this = this;
                _this.htmlContent = render;
                _this.$refs.saveButton.$emit('click');
        }, 
        initPermaLink() {
            let _this  =this; 
            if(QyTool.isNotEmpty(_this.articleForm.title) && QyTool.isEmpty(_this.articleForm.permaLink) ) {
                    let params = {
                        title: _this.articleForm.title
                    }
                    _this.checkAndGetNewLink(params);
            }
        },
        updateLink(val) { 
            let _this = this;
            _this.articleForm.permaLink = val;
        },
        checkAndGetNewLink(params) {
             let _this = this;
            _this.$axios.post('/admin/pages/checkPermaLink', params).then((res) => {
                let resp = res.data;
                if(resp.success) { 
                    _this.articleForm.permaLink = resp.content;
                }
            });
        }
 
    }
}
</script>

<style scoped>
.mavonEditor {
  width: 100%;
  height: 100%;

}

</style>

<style lang="scss" scoped>
  .mavonEditor /deep/ .v-note-panel {
    max-height: 200px;
    overflow-y: scroll;
  }


   .mavonEditor /deep/ .v-note-wrapper{
    z-index:0 !important;
}

//  body /deep/ .ant-message {
//   z-index: 16001;
// }

</style>