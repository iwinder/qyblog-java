<template>
    <div>
        <a-row class="table-operations">
             <a-row> 
                 <a-col> 

                         <a href="javascript:void(0)"  @click="searchStatus(null)" class="search-status">全部</a>
                        <a-divider type="vertical" />
                        <a href="javascript:void(0)"  @click="searchStatus('APPLIED')"  class="search-status">待审</a>
                         <a-divider type="vertical" />
                         <a href="javascript:void(0)" @click="searchStatus('ENROLLED')"   class="search-status">已批准</a>
                 </a-col>
             </a-row>
            <a-row> 
            <a-col :xs="{span:24}"  :lg="{ span: 12}">
                <a-form-model layout="inline" :model="searchFrom"    >
                    <a-form-model-item>
                        <a-input v-model="searchFrom.searchText" placeholder="请输入标题">
                            <a-icon slot="prefix" type="search" style="color:rgba(0,0,0,.25)" />
                        </a-input>
                    </a-form-model-item>
                    <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
                        <a-button type="primary"  :loading ="searchLoading"  @click="searchForm">
                            搜索
                        </a-button>
                    </a-form-model-item>
                </a-form-model>

            </a-col>
            <a-col  :xs="{span:24}"  :lg="{ span: 5, offset: 7 }" style=" margin-top: 5px;">
                <!-- <a-button  type="primary"  @click="add()">
                    新增
                </a-button> -->
                <a-dropdown>
                    <a-menu slot="overlay" @click="handleMenuClick">
                       <a-menu-item key="1">
                            批准
                        </a-menu-item>
                        <a-menu-item key="2">
                            驳回
                        </a-menu-item>
                        <a-menu-item key="3">
                            删除
                        </a-menu-item>
                    </a-menu>
                    <a-button> 批量操作 <a-icon type="down" /> </a-button>
                </a-dropdown>
            </a-col>
            </a-row>
        </a-row>


        <a-table :columns="columns"
                 :data-source="data"
                 :rowKey = "record => record.id"
                 :pagination="pagination"
                 :scroll = "{ x:  800}"
                 :loading="loading"
                 :row-selection="{ selectedRowKeys: selectedIds, onChange: onSelectChange }"
                 @change="handleTableChange"
                :customRow="customRow"
        >
   <span slot="author" slot-scope="text, record"  > 
                    <strong v-if="record.user && record.user.nickname">
            <a-avatar class="comment-avatar" 
                   :src="record.user.avatar"
              ></a-avatar>
            {{  record.user.nickname }}
             </strong>
                  <strong v-else>
            <a-avatar class="comment-avatar"  
                   :src="'//sdn.geekzu.org/avatar/'+emailToMd5(record.authorEmail)"
                 ></a-avatar>
                      {{  record.authorName}}
             </strong>
             <br>
             <template v-if="record.authorUrl">
            <a  :href="record.authorUrl" target="_blank">{{record.authorUrl}}</a>
            <a href="#" class="akismet_remove_url" commentid="284" title="移除此URL">x</a>
            <br>
             </template>
            <a :href="'mailto:'+record.authorEmail">{{record.authorEmail}}</a><br>
            <a>{{record.authorIp}}</a>
   </span>
  <span slot="content" slot-scope="text, record, index"  >
            <a-row v-if="record.parent && record.parent.authorName">
                    回复给
                    <a>{{record.parent.authorName}}</a> 
             </a-row>        
            <a-row>   {{record.content}}</a-row>
            <a-row v-show="showRow[index]==true">
            
                <template> 
                        <a href="javascript:void(0)" @click="updateStatus([record.id],'ENROLLED')" class="text-primary"  v-if="record.status == 'APPLIED'">批准</a>
                        <a href="javascript:void(0)" @click="updateStatus([record.id],'APPLIED')" class="text-warning"  v-else-if="record.status == 'ENROLLED'">驳回</a>
                        <!-- <a class="text-danger"  v-else-if="record.status == 'REFUSED'">恢复</a> -->
                         <a-divider type="vertical" />
                </template>
                <a  href="javascript:void(0)"   @click="repliesComment(record)" class="text-primary" >回复</a>
                <a-divider type="vertical" />
                 <a  href="javascript:void(0)"    @click="editComment(record)" class="text-primary" >编辑</a>  
                <a-divider type="vertical" />
                <!-- <a class="text-danger" >移至回收站</a>  
                <a-divider type="vertical" /> -->
               <a href="javascript:void(0)" @click="updateStatus([record.id],'REFUSED')"  class="text-danger" >删除</a>  
             </a-row>
    </span>
      <span slot="target" slot-scope="text, record"  >
                <a-row>{{record.target.targetName}}</a-row>
           </span>
        </a-table>


        <a-modal v-model="visibleSave"   :footer="null" @cancel="handleCancel">
              <template slot="title">  
                  <template v-if="editFlag">
                      编辑  
                  </template>
                  <template v-else>
                      回复 {{tmpParent.authorName}}
                  </template>
              </template>
                  <a-form-model ref="commentForm" :model="commentForm" layout="vertical" :rules="rules" >
                            <a-form-model-item has-feedback label="内容" prop="content">
                                 <a-textarea :rows="4" v-model="commentForm.content"   placeholder="评论内容" />
                            </a-form-model-item>
                            <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
                            <a-button type="primary" :loading="editLoading" @click="saveObj">
                                保存
                            </a-button>
                            <a-button style="margin-left: 10px" @click="handleCancel">
                                取消
                            </a-button>
                            </a-form-model-item>
                    </a-form-model>
         </a-modal>
    </div>
</template>

<script  >
    import Vue from 'vue'
    import { FormModel } from 'ant-design-vue';
    Vue.use(FormModel);
    import   md5 from 'js-md5'

    const columns = [ 
        {
            title: '作者',
            dataIndex: 'author',
            key: 'author',
             scopedSlots: { customRender: 'author' },
             width: "20%"
        }, 
        {
            title: '评论',
            dataIndex: 'content',
            key: 'content',
              scopedSlots: { customRender: 'content' },
        },
        {
            title: '回复至',
            dataIndex: 'target',
            key: 'target',
              scopedSlots: { customRender: 'target' },
              width: "15%"
        }, 
        {
            title: '提交于',
            dataIndex: 'createdDate',
            key: 'createdDate',
              width: "15%"
        }, 
        // {
        //     title: '操作',
        //     key: 'action',
        //     scopedSlots: { customRender: 'action' },
        // },

    ];


    export default Vue.extend({
        data() {
            return {
                columns,
                loading: false,
                searchLoading: false,
                searchFrom: {
                    searchText: null
                },
                selectedIds:[],
                data: [],
                pagination: {
                    pageSize: 10,
                    current: 1,
                    total: 0
                },
                showRow: [false],
                tmpParent: {},
                tmpObj: {},
                commentForm: {
                    content:""
                },
                visibleSave: false,
                editFlag: false,
                editLoading: false,
                rules: {
                content: [
                    {required: true,whitespace: true, message: "内容不可为空",  trigger:"change"}
                ]
            },
             layout: {
                labelCol: { span: 1 },
                wrapperCol: { span: 23 },
            },

            };
        },
        mounted() {
            let _this = this;
            _this.initData();
        },
        methods: {
            initData() {
                let _this = this;
                _this.list({
                    page: 1,
                    size: _this.pagination.pageSize
                });
            },
            list(pageInfo) {
                let _this = this;
                _this.loading = true;
                let params = {
                    searchText:  _this.searchFrom.searchText,
                    ...pageInfo
                }
                if (QyTool.isEmpty(params.searchText)) {
                    delete params.searchText;
                }


                _this.$axios.get('/admin/comment',{ params: params

                }).then(res => {
                    let resp  = res.data
                    _this.data = resp.content.list;
                    _this.pagination.total =   resp.content.total;
                    _this.pagination.current =   resp.content.page;
                    _this.pagination.pageSize =   resp.content.size;
                    _this.loading = false;
                    _this.selectedIds = [];
                }).catch((response) => {
                    _this.loading = false;
                });
            },
            handleTableChange(pagination, filters, sorter ) {
                const pager = { ...this.pagination };
                pager.current = pagination.current;
                this.pagination = pager;
                this.list({
                    size: pagination.pageSize,
                    page: pagination.current,
                }) 
            },
            add() {
                let _this = this;
                _this.$router.push("/admin/comment/add");
            },
            searchForm() {
                let _this = this;
                _this.initData();

            },
            updateStatus(ids, status) {
                 let _this = this;
                if(ids) {
                    if(status == 'REFUSED') { 
                        _this.deleted(ids);
                    }  else {
                        let params = {
                            ids: ids,
                            status: status
                        }
                        let data = new FormData();
                        data.append('ids',ids);
                          data.append('status',status);
                        _this.$axios.post("/admin/comment/updateStatus",data).then(res => {
                            if(res.data.success) { 
                                _this.$message.destroy();
                                    _this.$message.success("更新成功",5);
                                    _this.initData();
                            }
                        });
                    }
                }

            },
            handleMenuClick(e) {
                let _this = this; 
                if( !_this.selectedIds ||  _this.selectedIds.length<=0) {
                        this.$message.warning("请选择要操作项",5);
                        return false;
                }
                if(e.key === "1") {
                    _this.updateStatus(_this.selectedIds ,'ENROLLED') ; 
                } else  if(e.key === "2"){
                    _this.updateStatus(_this.selectedIds ,'APPLIED') ; 
                }else  if(e.key === "3") { 
                        _this.updateStatus(_this.selectedIds ,'REFUSED') ; 
                }
            },
            onSelectChange(selectedRowKeys) {
                let _this = this;
                _this.selectedIds = selectedRowKeys;
            },
            confirmDeleted(e) {
                let _this = this;
                _this.deleted(e);
            },
            searchStatus(e) {

            },
            deleted(ids) {
                let _this = this;
                if(ids) {
                    this.$confirm({
                        title: '确认删除?',
                        onOk() { 
                            _this.$axios.delete("/admin/comment/deleted", {data:  ids}).then(res => { 
                                if(res.data.success) {
                                   _this.$message.destroy();
                                    _this.$message.success("删除成功",5);
                                    _this.initData();
                                }
                            }).catch((response) => { 
                            });
                        },
                        onCancel() { 
                        },
                        class: 'test',
                    });
                }
            },
            editComment(obj) {
                let _this  = this;
                _this.visibleSave= true;
                _this. editFlag= true;
                _this.tmpObj = obj;
                _this.commentForm.content = _this.tmpObj.content;
            },
            repliesComment(obj) {
                let _this  = this;
                _this.visibleSave= true;
                _this. editFlag= false;
                _this.tmpParent = obj;
            },
            saveObj() {
                 let _this  = this; 
                let params = {
                     content :  _this.commentForm.content,
                 };
                 let tip = "更新";
                if( _this. editFlag) { 
                    params.id = _this.tmpObj.id;
                } else {
                      let  user =  QyTool.getLoginUser();
                    params.user = {
                        id: user.id
                    };
                    params.targetId = _this.tmpParent.target.id;
                    params.parentId = _this.tmpParent.id;
                     tip = "回复";
                }
                _this.$axios.post("/admin/comment/save",params).then(res => {
                            if(res.data.success) { 
                                _this.$message.destroy();
                                    _this.$message.success(tip+"成功",5);
                                    _this.initData();
                                    _this.handleCancel();
                            }
                        });

            },
            handleCancel() {
                   let _this  = this;
                   _this.$refs.commentForm.resetFields();
                  _this.visibleSave= false;
                   _this.tmpObj = {};
                 _this.tmpParent = {};

            },
             emailToMd5(email) {
                return md5(email);
            },
            customRow(record,index) {
                return {
                    props: { 
                    },
                    on: {
                        mouseenter: () => {
                         
                            let _this = this;
                            Vue.set( _this.showRow, index, true);

                        }, 
                        mouseleave: (event) => {
                                              let _this = this;
                              Vue.set( _this.showRow, index, false);
                        }
                    }
            }}

        },

    })
</script>

<style lang="scss"  scoped>
     .table-operations {
        margin-bottom: 16px;
        button {
            margin-right: 8px;
        }
    }

    
   .comment-avatar{
            float: left;
    margin-right: 10px;
    margin-top: 1px;
}
 /deep/  .ant-table-tbody td {
                      font-size: 12px;
                        line-height: 1.5em;
 }
 
</style>

 

