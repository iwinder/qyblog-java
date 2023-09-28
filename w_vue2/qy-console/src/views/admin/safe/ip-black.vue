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
            <a-col  :xs="{span:24}"  :lg="{ span: 7, offset: 5 }" style=" margin-top: 5px;">
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
                <!-- <router-link :to="{name:'sysIpBlack-id',params:{ id: record.id }}">编辑 </router-link >
                <a-divider type="vertical" /> -->

                     <a  href="javascript:void(0)"  @click="deleted([record.id])" >删除</a>

            </span>
        </a-table>

            <a-modal v-model="visible"  on-ok="editIp" :footer="null" @cancel="handleCancel">
              <template slot="title">
                   新增黑名单
              </template>
                  <a-form-model ref="ipForm" :model="ipForm" :rules="rules" layout="vertical">
                            <a-form-model-item has-feedback label="IP" prop="name">
                                   <a-textarea :rows="4" v-model="ipForm.name"   placeholder="多个IP请换行输入" /> 
                            </a-form-model-item>
                            <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
                            <a-button type="primary" :loading="editLoading" @click="editIp">
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
            title: 'id',
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: '访客ip',
            dataIndex: 'visitorIp',
            key: 'visitorIp',
        },
        {
            title: '访客客户端',
            dataIndex: 'visitorAgent',
            key: 'visitorAgent',
              ellipsis: true, 
        },
        {
            title: '类型',
            dataIndex: 'type',
            key: 'type',
        },
        {
            title: '备注',
            dataIndex: 'remarks',
            key: 'remarks',
            ellipsis: true, 
        },
        {
            title: '被封次数',
            dataIndex: 'blackNum',
            key: 'blackNum',
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
            ellipsis: true, 
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
                editLoading: false,
                visible: false,
                ipForm: {
                    name:''
                },
                rules:  {
                },
                tagObj: {},
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


                _this.$axios.get('/admin/sysIpBlack',{ params: params

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
                    _this.tagObj = obj[0];
                    _this.tagForm.name = _this.tagObj.name;
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
                            _this.$axios.delete("/admin/sysIpBlack/deleted", {data:  ids}).then(res => { 
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
         editIp() {
                let _this = this;
                  _this.editLoading = true;
                _this.$refs.ipForm.validate(valid => {
                    if (valid) { 
                        _this.tagObj.visitorIp = _this.ipForm.name;
                        
                        _this.$axios.post('/admin/sysIpBlack/save', _this.tagObj).then(res => {
                                _this.editLoading = false;
                                if(res.data.success) {
                                    this.$message.success('保存成功',5);
                                     _this.tagObj = {}; 
                                     _this.$refs.ipForm.resetFields();
                                    this.visible = false;
                                    _this.initData();
                                }
                        }).catch((response) => {
                                _this.editLoading = false; 
                            });
                    } else {
                        _this.editLoading = false;
                        return false;
                    }
                });
            },
        handleCancel() {
                let _this = this;
                 _this.ipForm= {
                     name: ""
                 };
                _this.$refs.ipForm.resetFields();
                _this.visible = false;
                _this.tagObj = {}; 
            },
        },

    })
</script>

<style lang="scss" scoped>
    .table-operations {
        margin-bottom: 16px;
        button {
            margin-right: 8px;
        }
    }
 
</style>

