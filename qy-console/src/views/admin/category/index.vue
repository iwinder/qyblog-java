<template>
    <div>
        <a-row>
            <a-col :xs="{span:24}"  :lg="{ span: 5}" >
                  <a-directory-tree ref="categoryTree"  :load-data="onLoadData" 
                        :tree-data="treeData" 
                        :replaceFields="replaceTreeFields" 
                        :default-selected-keys="[0]"
                         :default-expanded-keys="[0]"
                        @select="onSelectTree"/>
            </a-col>
            <a-col   :xs="{span:24}"  :lg="{ span: 17, offset: 1 }"   >
                <a-row class="table-operations">
                     <a-col :xs="{span:24}"  :lg="{ span: 7}" ><h3 style=" margin: 5px; ">{{selectedTreeData.namePath || selectedTreeData.name}}</h3></a-col>
                    <a-col    :xs="{span:24}"  :lg="{ span: 7, offset: 10 }"  style=" margin-top: 5px;">
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
                        :scroll = "{ x:  800}"
                        :row-selection="{ selectedRowKeys: selectedIds, onChange: onSelectChange }"
                        @change="handleTableChange"
                >

                    <span slot="name" slot-scope="name, record">
                           <a-tooltip placement="topLeft" :title="record.namePath" arrow-point-at-center>
                        {{name}}
                           </a-tooltip>
                    </span>

                    <!-- <span slot="name" slot-scope="name">
                           <a-tooltip placement="topLeft" :title="editParentData.namePath" arrow-point-at-center>
                        {{name}}
                           </a-tooltip>
                    </span> -->

                    <span slot="parent"  > 

                          <template v-if="selectedTreeData.id==0" >  无</template>
                        <template v-else > 
                                <a-tooltip placement="topLeft" :title="selectedTreeData.namePath" arrow-point-at-center>
                            {{selectedTreeData.name}}
                        </a-tooltip>
                    </template>
                    </span>

                    <span slot="action" slot-scope="text, record">
                        <router-link :to="{name:'category-id',params:{ id: record.id,obj: record,parent: {
                            id: selectedTreeData.id,
                            name: selectedTreeData.name,
                            namePath: selectedTreeData.namePath
                        } }}">编辑 </router-link>
                        <a-divider type="vertical" />

                            <a  href="javascript:void(0)"  @click="deleted([record.id])" >删除</a>

                    </span>
                </a-table>
            </a-col>
        </a-row>
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
            scopedSlots: { customRender: 'name' },
        },

        {
            title: '父级分类',
            dataIndex: 'parent',
            key: 'parent',
             scopedSlots: { customRender: 'parent' },
        },
        {
            title: '创建时间',
            dataIndex: 'createdDate',
            key: 'createdDate',
             ellipsis: true,
        },
        {
            title: '更新时间',
            dataIndex: 'lastModifiedDate',
            key: 'lastModifiedDate',
             ellipsis: true,
        },
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
                treeData: [
                    { name: '文章分类', id: 0}
                ],
                replaceTreeFields: {
                    children:'children', 
                    title:'name', 
                    key:'id' 
                },
                



                treeParent: {},
                treeObj: {},
              
                selectedTreeNode: null,
                firstTreeNode:null,
                 selectedParentTreeNode: null,
                selectedTreeData: {},
               
            };
        },
        mounted() {
            let _this = this;
            // _this.initData();
        },
        methods: {
            initData() {
                let _this = this;
                _this.list({
                    page: 1,
                    size: _this.pagination.pageSize
                });
            },
            list(params) {
                let _this = this;
                _this.loading = true;

                _this.$axios.get('/admin/blogCategorys',{ params: params

                }).then(res => {
                    let resp  = res.data
                    _this.data = resp.content;
                    _this.loading = false;
                    _this.selectedIds = [];
                }).catch((response) => {
                    _this.loading = false;
                   this.$message.error(response,5);
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
            let  parent = {
                id: _this.selectedTreeData.id,
                name: _this.selectedTreeData.name,
                namePath: _this.selectedTreeData.namePath,
            }
               
                _this.$router.push( {path: "/category/add", query: {parent: JSON.stringify(parent) } });

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
                            _this.$axios.delete("/admin/blogCategorys", {data:  ids}).then(res => {
                                if(res.data.success) {
                                    _this.$message.success("删除成功",5);
                                    _this.initData();
                                }
                            }).catch((response) => {
                               this.$message.error(response,5);
                            });
                        },
                        onCancel() {
                        },
                        class: 'test',
                    });
                }
            },
            onLoadData(treeNode) {
                let _this = this;
                _this.selectedTreeNode = treeNode;
                _this.selectedTreeData = treeNode.dataRef;
                _this.firstTreeNode = treeNode;
                return new Promise(resolve => {
                     if(treeNode.dataRef.id === 0) {
                          let params = {};
                           treeNode.dataRef.children = [];
                           _this.loadCategoryData(params,function( resp) {
                                if(resp.success) {
                                        treeNode.dataRef.children =   [...resp.content];
                                        _this.treeData = [..._this.treeData];
                                        _this.data = resp.content;
                                    }
                                    resolve();
                                    return;
                           });
                     } else {
                         let params = {
                             parentId: treeNode.dataRef.id 
                         }
                        _this.loadCategoryData(params,function( resp) {
                                if(resp.success) {
                                    treeNode.dataRef.children =   [...resp.content];
                                    _this.treeData = [..._this.treeData];
                                    _this.data = resp.content;
                                 }
                                resolve();
                                return;
                        });
                     }
                })
            },
            loadCategoryData(params, callBack) {
                let _this = this;
                _this.$axios.get('/admin/blogCategorys',{ params: params}).then(res => {
                        let resp = res.data;
                        callBack(resp);
                });
            },
           

            onSelectTree(selectedKeys, info) {
                   
                    let _this = this;
                    let params = {};
                    if(selectedKeys[0]==0) {
                        params = {};
                    } else {
                        params.parentId = selectedKeys[0];
                    }

                    _this.selectedTreeNode = info.selectedNodes[0];
                    _this.selectedTreeData = info.selectedNodes[0].dataRef;


                     _this.loadCategoryData(params,function( resp) {
                                if(resp.success) {
                                        _this.data = resp.content;
                                    }
                           }); 
                    _this.treeParent.id = selectedKeys[0];  
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

