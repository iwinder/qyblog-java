<template>
    <div>
        <a-row class="table-operations">
            <a-col :span="12">
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
            <a-col :span="5" :offset="7" style=" margin-top: 5px;">
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
                 :loading="loading"
                 :row-selection="{ selectedRowKeys: selectedIds, onChange: onSelectChange }"
                 @change="handleTableChange"
        >

            <span slot="action" slot-scope="text, record">
                <nuxt-link :to="{name:'${domain}-id',params:{ id: record.id }}">编辑 </nuxt-link >
                <a-divider type="vertical" />

                     <a  href="javascript:void(0)"  @click="deleted([record.id])" >删除</a>

            </span>
        </a-table>
    </div>
</template>

<script  >
    import Vue from 'vue'
    import { FormModel } from 'ant-design-vue';
    Vue.use(FormModel);

    const columns = [
        <#list fieldList as field>
        {
            title: '${field.nameCn}',
            dataIndex: '${field.nameHump}',
            key: '${field.nameHump}',
        },
        </#list>
        {
            title: 'Action',
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


                _this.$axios.get('${domain}s',{ params: params

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
                _this.$router.push("/${domain}/add");
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
                            _this.$axios.delete("${domain}", {data:  ids}).then(res => {
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

