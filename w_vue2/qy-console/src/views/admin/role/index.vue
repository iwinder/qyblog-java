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
                <a-button  type="primary"  @click="add()">
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

            <span slot="action" slot-scope="text, record">
               <a href="javascript:void(0)"  @click="add(record)">编辑 </a >
                <template v-if="record.roleType!=='SYSTEM'">
                <a-divider type="vertical" />

              <a  href="javascript:void(0)"  @click="deleted([record.id])" >删除</a>
                </template>

            </span>
        </a-table>


        <a-modal v-model="visible"  on-ok="editRole" :footer="null" @cancel="handleCancel">
              <template slot="title">
                  <template v-if="roleObj.id">
                      修改角色
                  </template>
                  <template v-else>
                      新增角色
                  </template>
              </template>
                  <a-form-model ref="roleForm" :model="roleForm" :rules="rules" v-bind="layout">
                            <a-form-model-item has-feedback label="名称" prop="name">
                            <a-input v-model.number="roleForm.name" />
                            </a-form-model-item>
                            <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
                            <a-button type="primary" :loading="editLoading" @click="editRole">
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

    const columns = [
        {
            title: '名称',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: '角色类型',
            dataIndex: 'roleType',
            key: 'roleType',
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
                roleObj: {},
                roleForm: {
                    name:''
                },
                rules:  {
                },
                layout: {
                    labelCol: { span: 4 },
                    wrapperCol: { span: 14 },
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


                _this.$axios.get('/admin/role',{ params: params

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
            add(obj) {
                let _this = this;
                if(obj) {
                    _this.roleObj = obj;
                    _this.roleForm.name = _this.roleObj.name;
                }
                 _this.visible = true;
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
                            _this.$axios.delete("/admin/role", {data:  ids}).then(res => { 
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
            editRole() {
                let _this = this;
                  _this.editLoading = true;
                _this.$refs.roleForm.validate(valid => {
                    if (valid) {
                        _this.roleObj.name = _this.roleForm.name;
                        _this.$axios.post('/admin/role/save', _this.roleObj).then(res => {
                                _this.editLoading = false;
                                if(res.data.success) {
                                    this.$message.success('保存成功',5);
                                     _this.roleObj = {}; 
                                     _this.$refs.roleForm.resetFields();
                                    this.visible = false;
                                    _this.initData();
                                }
                        }).catch((response) => {
                                _this.editLoading = false;
                                this.$message.error(response,5); 
                            });
                    } else {
                        _this.editLoading = false;
                        return false;
                    }
                });
            },
            handleCancel() {
                let _this = this;
                 _this.roleForm= {
                     name: ""
                 };
                _this.$refs.roleForm.resetFields();
                _this.visible = false;
                _this.roleObj = {}; 
            },
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

