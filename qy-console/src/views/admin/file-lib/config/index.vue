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
            <template slot="status" slot-scope="text, record">
                <template v-if="record.status==true">启用</template>
                <template v-else>未启用</template>
            </template>
            <template slot="type" slot-scope="text, record">
                <template v-if="record.type=='SYSTEM'">系统</template>
                <template v-else-if="record.type=='USER'">自定义</template>
                <template v-else>未知</template>
            </template>

            <span slot="action" slot-scope="text, record">
              
                <template v-if="record.identifier!=1"> 
                    <router-link :to="{name:'media-lib-config-id',params:{ id: record.id,obj: record, }}">配置 </router-link>
                </template>
                <template v-if="record.type!='SYSTEM'">
                    <a-divider type="vertical" />
                    <a  href="javascript:void(0)"  @click="deleted([record.id])" >删除</a>
                </template>
            </span>
        </a-table>

        <a-modal v-model="configVisible"  on-ok="editConfig" :footer="null" :maskClosable="false" >
            <template slot="title">
                配置{{targetType.name}}
            </template>
            
        </a-modal>
    </div>
</template>

<script  >
    import Vue from 'vue'
    import { FormModel } from 'ant-design-vue';
    Vue.use(FormModel);

    const columns = [ 
        {
            title: '媒体库名称',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: '类型标识',
            dataIndex: 'identifier',
            key: 'identifier',
        },
        {
            title: '状态',
            dataIndex: 'status',
            key: 'status',
            scopedSlots: { customRender: 'status' },
        },
        {
            title: '类型',
            dataIndex: 'type',
            key: 'type',
            scopedSlots: { customRender: 'type' },
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
                configVisible: false,
                editConfigLoading: false,
                configForm: {
                    accessKey:'',
                    secretKey:'',
                    bucket:'',
                    endpoint:'',
                    prefix:'' 

                },
                configRules:  {
                },
                targetObj: {},
                layout: {
                    labelCol: { span: 4 },
                    wrapperCol: { span: 14 },
                },
                targetType:{},
                targetConfig:{}
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


                _this.$axios.get('admin/fileLibType',{ params: params

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
                    console.log("error：", response);
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
                _this.$router.push("/fileLibType/add");
            },
            searchForm() {
                let _this = this;
                _this.initData();

            },
            handleMenuClick(e) {
                let _this = this;
                console.log('click', e);
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
                            console.log('OK');
                            _this.$axios.delete("admin/fileLibType/deleted", {data:  ids}).then(res => {
                                console.log("deleted !res", res);
                                if(res.data.success) {
                                    _this.$message.success("删除成功",5);
                                    _this.initData();
                                }
                            }).catch((response) => {
                                console.log("error：", response);
                            });
                        },
                        onCancel() {
                            console.log('Cancel');
                        },
                        class: 'test',
                    });
                }
            },
            openConfig(obj) {
                let _this = this;
                _this.targetConfig = {};
                if(obj) {
                    _this.targetType = obj;
                    _this.$axios.get("/admin/fileLibConfig/byType/"+obj.id).then(res => {
                            if(res.data && res.data.success) {
                                _this.targetConfig =  res.data.content;
                                if( _this.targetConfig) {
                                    _this.configForm = _this.targetConfig;
                                } else {
                                     _this.targetConfig.typeId = obj.id;
                                }
                                _this.configVisible = true;
                            } else {
                                _this.$message.error('配置获取失败: ' + res.data?res.data.message:res ,5);
                            }
                    }).catch((response) => {
                        _this.$message.error('配置获取失败: ' + response,5);
                    });
                   
                }
               
            },
           

        },

    })
</script>

<style  lang="scss" scoped>
    .table-operations {
        margin-bottom: 16px;
        button {
            margin-right: 8px;
        }
    }
 
</style>

