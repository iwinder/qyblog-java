<template>
    <a-row>
          <a-page-header
            style="border: 1px solid rgb(235, 237, 240)"
            title="管理菜单组" 
            @back="backF()"
        > 
            <template slot="subTitle">
               「  {{targetName}}  」  的具体菜单
             </template>
        </a-page-header>
        <a-row class="table-operations">
            <a-col :xs="{span:24}"  :lg="{ span: 12}">


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
        <template slot="blanked" slot-scope="text, record">
            <template v-if="record.blanked">是</template>
            <template v-else>否</template>
        </template>

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
				<a-form-model ref="linkForm" :model="linkForm" :rules="rules" v-bind="layout">
					<a-form-model-item label="菜单名称" prop="name">
						<a-input v-model="linkForm.name" placeholder="菜单名称" />
					</a-form-model-item>
					<a-form-model-item label="菜单地址" prop="url" :extra="linkForm.url">
						<a-input v-model="linkForm.url" placeholder="菜单地址" />
					</a-form-model-item>
					<a-form-model-item label="新窗口打开" prop="blanked"  >
						<a-switch v-model="linkForm.blanked">
							<a-icon slot="checkedChildren" type="check" />
							<a-icon slot="unCheckedChildren" type="close" />
						</a-switch>
					</a-form-model-item>
                    <a-form-model-item label="父级" prop="parentId"  > 
                            <a-tree-select ref="treeSelect"
                                            tree-data-simple-mode
                                            v-model="linkForm.parentId"
                                            style="width: 100%"
                                            :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                                            :tree-data="parentList"
                                            :replaceFields="replaceTreeFields" 
                                            placeholder="选择父级菜单"
                                         
                                        />

                        <template slot="help">
                            <ul>
                                <li> 菜单暂时仅支持最多二级菜单</li>
                                <li> 页尾菜单暂不支持多级菜单</li>
                                <li> 当存在子菜单时，父菜单链接将失效</li>
                                <li> 外部链接必须以http或https开头</li>
                                 <li>内部链接无需加域名前缀</li>
                             </ul>
                        </template>

                    </a-form-model-item>
					<a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
						<a-button type="primary" :loading="editLoading" @click="editLink">保存</a-button>
						<a-button v-if="linkId !=null" style="margin-left: 10px" @click="onDrawerClose">取消</a-button>
						<a-button v-else style="margin-left: 10px" @click="resetForm">重置</a-button>
					</a-form-model-item>
				</a-form-model>
			</a-row>
		</a-drawer>
    </a-row>


    

</template>

<script  >
import Vue from 'vue'


    const columns = [

        {
            title: "链接名称",
            dataIndex: "name",
            key: "name"
        },
        {
            title: "链接地址",
            dataIndex: "url",
            key: "url"
        },
    	{
            title: "新窗口打开",
            dataIndex: "blanked",
            key: "blanked",
            scopedSlots: { customRender: 'blanked' },
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
            targetId: null,
            targetName:"",
            targetIdentifier:  null,
            columns,
            loading: false,
            selectedIds:[],
            data: [],
            pagination: {
                pageSize: 10,
                current: 1,
                total: 0
            },

            visible: false,
			editLoading: false,
            linkId: null,
            parentList: [],
			linkForm: {
				name: "",
                url: "",
                parentId: null,
                blanked: false
			},
			rules: {
				name: [
					{
						required: true,
						whitespace: true,
						message: "链接名称不可为空",
						trigger: "change"
					}
				],
				url: [
					{
						required: true,
						whitespace: true,
						message: "链接地址不可为空",
						trigger: "change"
					}
				]
			},
			layout: {
				labelCol: { span: 5 },
				wrapperCol: { span: 16 }
            },
            replaceTreeFields: {
                    children:'children', 
                    value: 'id',
                    title:'name', 
                    key:'id' 
            },
        }
    },
    created() {
        let _this = this; 
        _this.targetId = _this.$route.params.id;
        if(!_this.$route.params.name) {
            _this.findTarget();
        } else {
            _this.targetName = _this.$route.params.name;
             _this.targetIdentifier = _this.$route.params.identifier;
        }
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
        onSelectChange(selectedRowKeys) {
            let _this = this;
            _this.selectedIds = selectedRowKeys;
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
        list(pageInfo) {
                let _this = this;
                _this.loading = true;
                let params = { 
                    targetId: _this.targetId,
                    ...pageInfo
                }
 

                _this.$axios.get('/admin/menus',{ params: params

                }).then(res => {
                    let resp  = res.data
                    _this.loading = false;
                    if(resp.success) { 
                        _this.data = resp.content.list;
                        _this.pagination.total =   resp.content.total;
                        _this.pagination.current =   resp.content.page;
                        _this.pagination.pageSize =   resp.content.size;
                        _this.selectedIds = [];
                    } else {
                        this.$message.error(resp.message,5);
                    }
                  
                }).catch((response) => {
                    _this.loading = false; 
                });
        },
        findTarget() {
            let _this = this;
            _this.$axios.get('/admin/menusAgent/' +  _this.targetId   ).then(res => {
                    let resp  = res.data
                    if(resp.success) {
                            _this.targetName = resp.content.name;
                            _this.targetIdentifier =  resp.content.identifier;
                    }   
                }).catch((response) => {
                    _this.loading = false; 
                });
        },
        showDrawer(obj) {
			let _this = this;
            _this.visible = true;
            _this.  findParentList();
			if (obj) { 
				_this.linkId = obj.id;
				_this.linkForm = {
					name: obj.name,
                    url: obj.url,
                    parentId: obj.parentId,
                    blanked: obj.blanked,
				};
			} else { 
				_this.linkId = null;
            }
            
        },
        onDrawerClose() {
                let _this = this;
                _this.initForm();
                _this.visible = false;
            
        },
        editLink() {
			let _this = this;
            _this.editLoading = true; 
			let param = {
                id: _this.linkId,
                ..._this.linkForm,
                targetId: _this.targetId,
                targetIdentifier: _this.targetIdentifier
            }; 
			_this.$axios
				.post("/admin/menus/save", param)
				.then(res => {
					_this.editLoading = false;
					if (res.data.success) {
						_this.$message.success("保存成功", 5);
						_this.onDrawerClose();
						_this.initData();
					} else {
						_this.$message.error(res.data.message, 5);
					}
				})
				.catch(response => {
					_this.editLoading = false;
					_this.$message.error(response, 5);
				});
		},
		initForm() {
			let _this = this;
			_this.linkForm = {
				name: "",
				url: "",
                parentId: null,
                blanked: false
			};
			//  _this.$refs.linkForm.resetFields();
		},
		resetForm() {
			let _this = this;
			_this.$refs.linkForm.resetFields();
        },
        findParentList() {
            let _this  = this;
            let params = { 
                    targetId: _this.targetId,
                }
             _this.$axios.get('/admin/menus/parent',{ params: params}).then(res => {
                    let resp  = res.data
                    _this.loading = false;
                    if(resp.success) {
                        _this.parentList = resp.content;
                     }  
                })	.catch(response => {
					_this.editLoading = false;
					_this.$message.error(response, 5);
				});

        },
        backF() {
            let _this  = this;
            this.$router.go(-1);
        }
    }


            
    
})
</script>