<template>
    <div>
        <a-row class="table-operations">
           <a-col :xs="{span:24}"  :lg="{ span: 12}" >
              <a-form-model layout="inline" :model="searchFrom"    >
                    <a-form-model-item>
                        <a-input v-model="searchFrom.searchText" placeholder="请输入标题">
                          <a-icon slot="prefix" type="search" style="color:rgba(0,0,0,.25)" />
                        </a-input>
                      </a-form-model-item>
                      <a-form-model-item :wrapper-col="{ span: 14  }">
                            <a-button type="primary"  :loading ="searchLoading"  @click="searchForm('articleForm')">
                                搜索
                            </a-button>
                    </a-form-model-item>
              </a-form-model>
          </a-col>
          <a-col class='table-operations-right' :xs="{span:24}"  :lg="{ span: 8, offset: 3 }" style=" margin-top: 5px;">
            <a-button  type="primary"  @click="add()">
              新增
            </a-button>
            <a-dropdown >
                <a-menu slot="overlay" @click="handleMenuClick">
                    <a-menu-item key="1">
                      删除
                    </a-menu-item>
                  </a-menu>
                  <a-button> 批量操作 <a-icon type="down" /> </a-button>
            </a-dropdown>
            <a-dropdown >
                <a-menu slot="overlay" @click="handleSystemMenuClick">
                    <a-menu-item key="1">
                      导入
                    </a-menu-item>
                     <a-menu-item key="2">
                      更新链接列表
                    </a-menu-item>
                  </a-menu>
                  <a-button> 系统操作 <a-icon type="down" /> </a-button>
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

            <a slot="name" slot-scope="text">{{ text }}</a>

           <span slot="thumbnail" slot-scope="thumbnail">
             <img :src="thumbnail" @error="defImg(this)"  style=" height: 62px;   max-width: 100%;">
           </span>
          <span slot="published" slot-scope="published">
              <template v-if="published"> 已发布 </template> 
              <template v-else> 未发布 </template> 
          </span>
            <span slot="tags" slot-scope="tags">
                <a-tag
                v-for="tag in tags"
                :key="tag"
                :color="tag === 'loser' ? 'volcano' : tag.length > 5 ? 'geekblue' : 'green'"
                >
                {{ tag.toUpperCase() }}
                </a-tag>
            </span>

            <span slot="action" slot-scope="text, record"> 
                <router-link :to="{name:'article-id',params:{ id: record.id }}">编辑 </router-link>
                <a-divider type="vertical" />
 
                     <a  href="javascript:void(0)"  @click="deleted([record.id])" >删除</a>
                 
            </span>
        </a-table>

        <a-modal v-model="visible"  on-ok="editImport" :footer="null" @cancel="handleCancel" :maskClosable="false">
              <template slot="title">
                   从WordPress导入
              </template>
                  <a-form-model ref="importForm" :model="importForm" :rules="rules" layout="vertical">
                            <a-form-model-item has-feedback label="IP/地址" prop="name">
                                   <a-input v-model="importForm.ip"   placeholder="ip地址" /> 
                            </a-form-model-item>
                          <a-form-model-item has-feedback label="端口号" prop="port">
                                   <a-input-number v-model="importForm.port"   placeholder="端口号" /> 
                            </a-form-model-item>
                          <a-form-model-item has-feedback label="数据库名称" prop="database">
                                   <a-input v-model="importForm.database"   placeholder="数据库名称" /> 
                            </a-form-model-item>
                           <a-form-model-item has-feedback label="用户名" prop="username">
                                   <a-input v-model="importForm.username"   placeholder="用户名" /> 
                            </a-form-model-item>
                          <a-form-model-item has-feedback label="密码" prop="password">
                                   <a-input v-model="importForm.password"   placeholder="密码" /> 
                            </a-form-model-item>
                            <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
                            <a-button type="primary" :loading="editLoading" @click="editImport">
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
import { mapState } from 'vuex'
// import VueDraggableResizable from 'vue-draggable-resizable';
Vue.use(FormModel); 




const columns = [
                        {
                              title: '所略图',
                              dataIndex: 'thumbnail',
                              key: 'thumbnail',
                              scopedSlots: { customRender: 'thumbnail' },
                        },
                        {
                          title: '标题',
                          dataIndex: 'title',
                          key: 'title',
                        },
                        {
                          title: '作者',
                          dataIndex: 'user',
                          key: 'user',
                        },
                        {
                          title: '发布状态',
                          dataIndex: 'published',
                          key: 'published',
                          scopedSlots: { customRender: 'published' },
                        },

                        {
                          title: '标签',
                          key: 'tagStrings',
                          dataIndex: 'tagStrings',
                          scopedSlots: { customRender: 'tags' },
                        },
                        {
                          title: '操作',
                          key: 'action',
                          scopedSlots: { customRender: 'action' },
                        },
            ];
 

export default Vue.extend({
      components: {
        // VueDraggableResizable,
    },
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
          importForm: {
              ip: '',
              port: 3306,
              database: '',
              username: '',
              password: ''
          },
          siteUrl:'',
          rules:  {
                ip: [
                    {required: true,whitespace: true, message: "ip地址不可为空",  trigger:"change"}
                ],
                port: [
                    {required: true,whitespace: true, type: 'number', message: "端口号不可为空",  trigger:"change"}
                ],
                database: [
                    {required: true,whitespace: true, message: "数据库名称不可为空",  trigger:"change"}
                ],
                username: [
                    {required: true,whitespace: true, message: "用户名不可为空",  trigger:"change"}
                ],
                password: [
                    {required: true,whitespace: true, message: "密码不可为空",  trigger:"change"}
                ]
          },
        };
    },
   computed: {
        ...mapState({
        site: state => state.siteInfo.siteInfo
        })
    },
    mounted() {
      let _this = this; 
      _this.siteUrl = _this.site.site_url.endsWith('/')?_this.site.site_url:_this.site.site_url+'/';
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

       
        _this.$axios.get('/admin/articles',{ params: params
            
          }).then(res => {
            let resp  = res.data				
            _this.data = resp.content.list;
             let defImg = "";
            _this.data.forEach(e  => {
                defImg = _this.siteUrl+'img/thumb/'+ QyTool.randomNum(1,32)+'.jpg';
                e.thumbnail= e.thumbnail?e.thumbnail:defImg;
            });
            _this.pagination.total =   resp.content.total;
            _this.pagination.current =   resp.content.page;
            _this.pagination.pageSize =   resp.content.size;
            _this.loading = false;
             _this.selectedIds = [];
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
        _this.$router.push("/article/add");
      },
      importb() {
              let _this = this; 
                _this.visible = true;
      },
      handleCancel() {
                let _this = this; 
                _this.$refs.importForm.resetFields();
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
                _this.$axios.delete("/admin/articles", {data:  ids}).then(res => {
                  if(res.data.success) {
                    _this.$message.success("删除成功",5); 
                    _this.initData();
                  }
                });
              },
              onCancel() { 
              },
              class: 'test',
            });
        }
      },
      editImport() {  
          let  _this = this;
            _this.editLoading = true;
            _this.$refs.importForm.validate(valid => {
                if (valid) {
                      _this.$axios.post('/admin/blogMove/save',_this.importForm).then(res => { 
                              _this.editLoading= false;
                              if(res.data.success) { 
                                  _this.$message.destroy();
                                   _this.$message.success('导入成功',5);
                              }
                      }).catch((response) => {
                              _this.editLoading = false;
                              _this.$message.destroy();
                              _this.$message.error('导入失败: ' + response,5);
                      }); 
                } else {
                    _this.editLoading= false;
                    return false;
                }
            });
      },
      refreshLink() {
        let _this = this;
         _this.$axios.post("/admin/articles/refreshLink").then(res => {
          if(res.data.success) {
            _this.$message.success("更新成功",5);  
          }
        });
      },
      handleSystemMenuClick(e) {
        let _this = this;
        if(e.key === "1") {
          _this.importb(); 
        } else if(e.key === "2") {
          _this.refreshLink();
        }
      },
      defImg() {
        let _this = this;  
        let defImg = _this.siteUrl+'img/thumb/'+ QyTool.randomNum(1,32)+'.jpg';
        let tImg = new Image();
        tImg.src = defImg;
        if(tImg.filesize>0||(tImg.width>0&&tImg.height>0)) {
          let img =    window.event.target ||  window.event.srcElement;
          img.src = defImg;  
          img.onerror = null; //防止闪图,无效
        } 
      },
    },

})
</script>

<style lang="scss" scoped>
.table-operations {
  margin-bottom: 16px;
}

.table-operations  {
  button {
    margin-right: 8px;
  }
}
</style>

