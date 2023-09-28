<template>
    <div>
        <a-row class="table-operations">
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
                <a-button  type="primary"  @click="showDrawer(null)">
                    新增
                </a-button>
                <a-dropdown>
                    <a-menu slot="overlay" @click="handleMenuClick">
                        <a-menu-item key="1">
                            删除
                        </a-menu-item>
                    </a-menu>
                    <a-button> 批量操作 <a-icon type="down" /> </a-button>
                </a-dropdown>
            </a-col>
        </a-row>
        <a-table :columns="columns"
                 :data-source="data"
                 :rowKey = "record => record.id"
                 :pagination="pagination"
                 :scroll = "{ x:  800}"
                 :loading="loading"
                 :row-selection="{ selectedRowKeys: selectedIds, onChange: onSelectChange }"
                 @change="handleTableChange"
        >
            <span slot="showIndex" slot-scope="text,record">
                <template v-if="record.showIndex"> 是</template>
                <template v-else> 否  </template>
            </span>
            <span slot="action" slot-scope="text, record">
                 <a  href="javascript:void(0)"  @click="showDrawer(record)" >编辑</a>
                <a-divider type="vertical" />

                 <a  href="javascript:void(0)"  @click="deleted([record.id])" >删除</a>

            </span>
        </a-table>
        <a-drawer
                    title="Create a new account"
                    :width="'60%'"
                    :visible="visible"
                    :body-style="{ paddingBottom: '80px' }"
                    @close="onDrawerClose"
                    >
                    <a-row>
                    <a-form-model  ref="linkForm"  :model="linkForm"  :rules="rules" v-bind="layout">
                         <a-form-model-item    label="链接名称"  prop="name">
                                <a-input   v-model="linkForm.name"   placeholder="链接名称" />
                         </a-form-model-item>
                        <a-form-model-item    label="链接链接"  prop="url" :extra="linkForm.url">
                                <a-input   v-model="linkForm.url"   placeholder="链接链接" />
                         </a-form-model-item>
                          <a-form-model-item    label="是否首页"  prop="showIndex" help="设置非首页时，仅在内页展示">
                                <a-switch v-model="linkForm.showIndex" >
                                    <a-icon slot="checkedChildren" type="check" />
                                    <a-icon slot="unCheckedChildren" type="close" />
                                </a-switch> 
                          </a-form-model-item>
                          <a-form-model-item    label="站点描述"  prop="description" >
                                <a-input   v-model="linkForm.description"   placeholder="一句话描述站点" />
                         </a-form-model-item>
                                 <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
                                        <a-button type="primary" :loading="editLoading" @click="editLink">
                                            保存
                                        </a-button>
                                        <a-button  v-if="linkId !=null" style="margin-left: 10px" @click="onDrawerClose">
                                            取消
                                        </a-button>
                                         <a-button  v-else  style="margin-left: 10px" @click="resetForm">
                                            重置
                                        </a-button>
                                    </a-form-model-item>
                    </a-form-model>
                        
                    </a-row>
        </a-drawer>
    </div>



</template>

<script  >
    import Vue from 'vue'
    import { FormModel } from 'ant-design-vue';
    Vue.use(FormModel);

    const columns = [
        {
            title: 'id',
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: '链接名称',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: '链接地址',
            dataIndex: 'url',
            key: 'url',
        },
        {
            title: '首页外链',
            dataIndex: 'showIndex',
            key: 'showIndex',
             scopedSlots: { customRender: 'showIndex' },
        },{
            title: '描述',
             dataIndex: 'description',
             key: 'description',
        },
        {
            title: '创建者',
            dataIndex: 'createdBy',
            key: 'createdBy',
        },
        {
            title: '更新者',
            dataIndex: 'lastModifiedBy',
            key: 'lastModifiedBy',
        },
        {
            title: '创建时间',
            dataIndex: 'createdDate',
            key: 'createdDate',
        },
        {
            title: '更新时间',
            dataIndex: 'lastModifiedDate',
            key: 'lastModifiedDate',
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: { customRender: 'action' },
        },

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
                 visible: false,
                 editLoading: false,
                 linkId:null,
                 linkForm: {
                     name:"",
                     url:"",
                     showIndex: false,
                     description: ""
                 },
                rules: {
                    name :[
                      {required: true,whitespace: true, message: "链接名称不可为空",  trigger:"change"}
                    ],
                    url: [
                        {required: true,whitespace: true, message: "链接地址不可为空",  trigger:"change"},
                            
                    ],
                },
                layout: {
                    labelCol: { span: 5 },
                    wrapperCol: { span: 16 },   
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


                _this.$axios.get('/admin/link',{ params: params

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
                _this.$router.push("/admin/link/add");
            },
            showDrawer(obj) {
                  let _this = this;
                _this.visible = true;
                if(obj) { 
                    _this.linkId  = obj.id;
                    _this.linkForm = {
                        name: obj.name,
                        url: obj.url,
                        showIndex:  obj.showIndex,
                        description: obj.description
                    }
                } else { 
                    _this.linkId  = null;
                }
                
               

            },
           onDrawerClose() {
                        let _this = this;
                         _this.initForm();
                     _this.visible = false;
                    
              },
            searchForm() {
                let _this = this;
                _this.initData();

            },
            handleMenuClick(e) {
                let _this = this;
                if(e.key === "1") {
                    if( _this.selectedIds &&  _this.selectedIds.length>0) {
                        _this.deleted(_this.selectedIds );
                    } else {
                        this.$message.warning("请选择要删除项",5);
                        return false;
                    }

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
            deleted(ids) {
                let _this = this;
                if(ids) {
                    this.$confirm({
                        title: '确认删除?',
                        onOk() { 
                            _this.$axios.delete("/admin/link/deleted", {data:  ids}).then(res => { 
                                if(res.data.success) {
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
            editLink() {
                 let _this = this;
                _this.editLoading = true;
                let param = {
                   id:  _this.linkId,
                    ..._this.linkForm,
                }
                _this.$axios.post('/admin/link/save', param).then(res => {
                    _this.editLoading = false;
                    if(res.data.success) {
                        _this.$message.success('保存成功',5);
                            _this.onDrawerClose() ;
                            _this.initData();
                    } else {
                        _this.$message.error(res.data.message,5);
                    }
                }).catch((response) => {
                    _this.editLoading = false; 
                     _this.$message.error(response,5);
                });

            },
            initForm() {
                let _this = this;
                _this.linkForm = {
                    name:"",
                     url:"",
                     showIndex: false,
                     description: ""
                }
                //  _this.$refs.linkForm.resetFields();
            },
            resetForm() {
                 let _this = this;
                 _this.$refs.linkForm.resetFields();
            }
        },

    })
</script>

<style scoped>
    .table-operations {
        margin-bottom: 16px;
    }

    .table-operations > button {
        margin-right: 8px;
    }
</style>

