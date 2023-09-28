<template>
    <div>
            <a-form-model ref="articleForm" :model="articleForm" :rules="rules" :layout="'vertical'">
                <a-row>
                    <a-col :xs="24"   :lg="24"  >
                        <a-form-model-item has-feedback   prop="title"> 
                            <a-input v-model="articleForm.title" type="text" autocomplete="off" placeholder="添加标题"    @blur="initPermaLink"/>
                        </a-form-model-item>
                        <a-form-model-item has-feedback   prop="permaLink">
                             <template v-if="articleForm.permaLink">
                                   <qy-post-link-edit :permaLink="articleForm.permaLink"  :postId="articleForm.id"    :afterSave="updateLink" > </qy-post-link-edit>
                            </template> 
                        </a-form-model-item>
                        <a-form-model-item has-feedback  v-if="saveEndTime!=null && saveEndTime!=''"> {{saveEndTime}}</a-form-model-item>
                        <a-form-model-item has-feedback   prop="content">
                            <!-- <a-input v-model="articleForm.content" type="textarea" /> -->
                            <div class="mavonEditor">
                                <!-- <no-ssr> -->
                                <mavon-editor  ref="md"  v-model="markdownContent"   
                                placeholder = "添加内容"
                                    defaultOpen="edit"
                                     @imgAdd="markImgAdd" 
                                     @save = "markSave"
                                   @change="markChange"/>
                                <!-- </no-ssr> -->
                            </div>
                        </a-form-model-item>
                        <a-form-model-item has-feedback label="封面" prop="thumbnail">
                            <a-input v-model="articleForm.thumbnail" type="text" autocomplete="off" />
                            <a-upload
                                name="file"
                                list-type="picture-card"
                                class="thumbnail-uploader"
                                :action = "'api/admin/fileMeta/upload'"
                                :accept="acceptType"
                                :show-upload-list="false" 
                                :before-upload="beforeUpload"
                                @change="handleThumbnailChange"
                            >
                                <img v-if="articleForm.thumbnail" :src="articleForm.thumbnail" alt="avatar" style="width: 100%;" />
                                <div v-else>
                                    <a-icon :type="thumbnailLoading ? 'loading' : 'plus'" />
                                    <div class="ant-upload-text">
                                        Upload
                                    </div>
                                </div>
                            </a-upload>
                        </a-form-model-item>

                        <a-form-model-item has-feedback label="标签" prop="tags">
                             <a-select show-search mode="tags" style="width: 100%" 
                             v-model="articleForm.tagStrings"
                             placeholder="Tags Mode" @change="handleChange"
                      
                                    @search="fetchTags">
                                    <a-spin v-if="fetching" slot="notFoundContent" size="small" />
                                    <a-select-option v-for="d in tagsData" :key="d.value">
                                    {{ d.text }}
                                    </a-select-option>

                             </a-select>
                        </a-form-model-item>
                        <a-form-model-item has-feedback label="所属分类" prop="parent">
                            <a> 
                                <template v-if="editParentData.id==0" >  未分类</template>
                            <template v-else > 
                                    <a-tooltip placement="topLeft" :title="editParentData.namePath" arrow-point-at-center>
                                {{editParentData.name}}
                                    </a-tooltip>
                                </template>
                            </a>
                         </a-form-model-item>
                            <a-form-model-item has-feedback label="选择分类" 
                                        extra="若无修改分类的需求，请勿选择"
                                        prop="editParentId">
                                <qy-article-category-tree-select   ref="categoryTreeSelect"   :afterSelect="afterSelectTree" placeholdera="请选择新分类"></qy-article-category-tree-select>
                            </a-form-model-item>

                        <a-form-model-item has-feedback label="发布" prop="published">
                            <a-switch v-model="articleForm.published" >
                                <a-icon slot="checkedChildren" type="check" />
                                    <a-icon slot="unCheckedChildren" type="close" />
                            </a-switch>
                        </a-form-model-item>
                       <a-form-model-item has-feedback label="规范链接" prop="canonicalLink">
                            <a-input v-model="articleForm.canonicalLink" type="text" autocomplete="off" />
                            <template slot="help">
                                <ul>
                                    <li>1. 留空默认当前链接</li>
                                    <li>2. 原创文章无需填写，转载文章可填写转载地址</li>
                                </ul>
                            </template>
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
        saveContentLabel:null
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
                    thumbnail: '',
                    content: '',
                    type: 1,
                    published: true,
                    deleted:  false,
                    tags:''
                },
                editParentData:{},
                rules:{
                title: [
                    {required: true,whitespace: true, message: "标题不可为空",  trigger:"change"}
                ]
            }, 
                layout:   {
                    labelCol: { span: 5 },
                    wrapperCol: { span: 18 }
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
                saveEndTime: null,
                acceptType: "image/*",
                thumbnailLoading: false
        }
    },
    watch: {
        articleObj(val) {
             let _this = this;
             _this.articleForm = val;
            _this.editParentData =  val.category ?val.category : {id:0}
            _this.articleForm.tagStrings = val.tagStrings? val.tagStrings:[];
            _this.selectTags = _this.articleForm.tagStrings ;
             _this.markdownContent = val.content;
        },
        saveContentLabel(val) {
            let _this = this;
            _this.saveEndTime = val;
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
            let _this  = this;
             _this.$refs.articleForm.resetFields();
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
             _this.$axios.post('/admin/fileMeta/upload', formdata).then((res) => {
                let resp = res.data;
                if (resp.success) {
                     // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
                    _this.$refs.md.$img2Url(pos, resp.content.defUrl);
                    _this.imageNum ++;
                    if ( _this.imageNum==1 && !_this.articleForm.thumbnail ) {
                            _this.articleForm.thumbnail  = resp.content.defUrl;
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
        },
        beforeUpload(file) {
            const isLt2M = file.size / 1024 / 1024 < 2;
            if (!isLt2M) {
                this.$message.destroy();
                this.$message.error('图片必须小于 2MB!');
            }
            return  isLt2M;

        },
        handleThumbnailChange(info) { 
            if (info.file.status === 'uploading') {
                    this.thumbnailLoading = true;
                    return;
                }
                if (info.file.status === 'done') { 
                     let resp = info.file.response;
                     if(resp.success) {
                         this.articleForm.thumbnail = resp.content.defUrl;
                         
                     }
                      this.thumbnailLoading = false;
                }
        },
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
    z-index:1 !important;
}

//  body /deep/ .ant-message {
//   z-index: 16001;
// }

</style>