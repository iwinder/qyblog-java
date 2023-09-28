<template>
    <a-row>
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
          </a-col>
      </a-row>
      <a-table :columns="columns"
                 :data-source="data"
                 :rowKey = "record => record.jobName + '-' + record.jobGroup "
                :pagination="pagination"
                 :loading="loading"
                 :scroll = "{ x:  800}"  >

            <a slot="name" slot-scope="text">{{ text }}</a> 
            <span slot="cronFlag" slot-scope="cronFlag">
                <template v-if="cronFlag">
                    Cron任务类型
                </template>
                <template v-else>
                    简单任务类型
                </template>
            </span>

            <span slot="action" slot-scope="text, record"> 
                <a  href="javascript:void(0)"  @click="showInfo(record)" >详情 </a>
                <a-divider type="vertical" />
                <a  href="javascript:void(0)"  @click="editInfo(record)" >编辑</a>
                <a-divider type="vertical" /> 
                <a  href="javascript:void(0)"  @click="deleted(record.jobName,record.jobGroup)" >删除</a> 
            </span>
      </a-table>

      <a-modal v-model="visible"  on-ok="editData" :footer="null" @cancel="handleCancel" :maskClosable="false">
          <template slot="title">
              <template v-if="dataForm.jobName"> 
                    编辑任务
              </template>
                <template v-else> 
                    新增任务
              </template>
          </template>
          <a-form-model ref="dataForm" :model="dataForm" :rules="rules" layout="vertical">
              <a-form-model-item has-feedback label="任务名称" prop="jobName">
                      <a-input v-model="dataForm.jobName"   placeholder="任务名称" /> 
              </a-form-model-item>
              <a-form-model-item has-feedback label="任务组" prop="jobGroup">
                        <a-input v-model="dataForm.jobGroup"   placeholder="任务组" /> 
                </a-form-model-item>
              <a-form-model-item has-feedback label="任务描述" prop="description">
                        <a-input v-model="dataForm.description"   placeholder="任务描述" /> 
              </a-form-model-item>
              <a-form-model-item has-feedback label="任务执行类" prop="jobClass">
                        <a-input v-model="dataForm.jobClass"   placeholder="任务执行类" /> 
              </a-form-model-item>
              <a-form-model-item has-feedback label="任务类型" prop="cronFlag">
                  <a-radio-group  v-model="dataForm.cronFlag"  :default-value="true">
                      <a-radio :value="true"  name="cronFlag">  Cron任务类型 </a-radio>
                      <a-radio :value="false"  name="cronFlag">  简单任务类型 </a-radio>
                  </a-radio-group>
              </a-form-model-item>
              <template v-if="dataForm.cronFlag"> 
                <a-form-model-item has-feedback label="Cron时间表达式" prop="cronExpression">
                        <a-input v-model="dataForm.cronExpression"   placeholder="任务运行时间表达式" /> 
                </a-form-model-item>
              </template>
              <template v-else>
                  <a-form-model-item has-feedback label="开始时间" prop="startTime"> 
                    <a-date-picker show-time placeholder="Select Time" v-model="dataForm.startTime" format="YYYY/MM/DD HH:mm:ss"/>
                  </a-form-model-item>
              <a-form-model-item has-feedback label="结束时间" prop="endTime"> 
                       <a-date-picker show-time placeholder="Select Time" v-model="dataForm.endTime" format="YYYY/MM/DD HH:mm:ss"/>
                  </a-form-model-item>
                  <a-form-model-item has-feedback label="任务循环间隔(秒)" prop="interval">
                      <a-input v-model="dataForm.interval"  placeholder="单位：秒" /> 
                  </a-form-model-item>
              </template>
              <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
              <a-button type="primary" :loading="editLoading" @click="editData">
                  保存
              </a-button>
              <a-button style="margin-left: 10px" @click="handleCancel">
                  取消
              </a-button>
              </a-form-model-item>
          </a-form-model>
      </a-modal>
      <a-drawer  placement="right" :closable="false" :visible="infoVisible" @close="onInfoClose">
          <template slot="title">
             任务详情
          </template>
          <a-row>
            <a-descriptions
                title="" 
                :column="1"
              >
              <a-descriptions-item label="任务名称">
                {{dataObj.jobName}}
              </a-descriptions-item>
               <a-descriptions-item label="任务组">
                {{dataObj.jobGroup}}
              </a-descriptions-item>
               <a-descriptions-item label="任务描述">
                 {{dataObj.description}}
              </a-descriptions-item>
              <a-descriptions-item label="任务执行类">
                <span style="margin-right: 10px;" class="wordBreak"> {{dataObj.jobClass}}</span>
              </a-descriptions-item>
              <a-descriptions-item label="任务类型"> 
                <a-radio-group  v-model="dataObj.cronFlag" disabled :default-value="true">
                      <a-radio :value="true"  name="cronFlag">  Cron任务类型 </a-radio>
                      <a-radio :value="false"  name="cronFlag">  简单任务类型 </a-radio>
                  </a-radio-group>
              </a-descriptions-item>
              <template v-if="dataObj.cronFlag">
                  {{dataObj.jobGroup}}   
                <a-descriptions-item label="Cron时间表达式">
                  <span style="margin-right: 10px;" class="wordBreak">  {{dataObj.cronExpression}}</span>
                </a-descriptions-item> 
              </template>
              <template v-else>
                <a-descriptions-item label="开始时间">
                  {{dataObj.startTime}}
                </a-descriptions-item> 
                <a-descriptions-item label="结束时间">
                  {{dataObj.endTime}}
                </a-descriptions-item>
                <a-descriptions-item label="任务循环间隔(分钟)">
                  {{dataObj.interval}}
                </a-descriptions-item>
              </template>
            </a-descriptions>
          </a-row>
      </a-drawer>
    </a-row>
</template>

<script  >
import Vue from 'vue'
import { FormModel } from 'ant-design-vue'; 
import { mapState } from 'vuex'
// import VueDraggableResizable from 'vue-draggable-resizable';
Vue.use(FormModel); 
import moment from 'moment';



const columns = [ 
            {
                title: '任务名称',
                dataIndex: 'jobName',
                key: 'jobName',
            },
            {
                title: '任务组',
                dataIndex: 'jobGroup',
                key: 'jobGroup',
            }, 
            {
                title: '任务描述',
                dataIndex: 'description',
                key: 'description', 
            },  
            {
                title: '任务状态',
                dataIndex: 'statusStr',
                key: 'statusStr',
                scopedSlots: { customRender: 'statusStr' },
            },
            {
                title: '任务类型',
                key: 'cronFlag',
                dataIndex: 'cronFlag',
                scopedSlots: { customRender: 'cronFlag' },
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
          data: [],
          pagination: {
            pageSize: 10,
            current: 1,
            total: 0
          },
          editLoading: false,
          visible: false,
          infoVisible: false,
          jobModelType: 1,
          dataForm: {
              jobName: '',
              jobGroup: '',
              description: '',
              jobClass: '',
              startTime: null,
              interval: null,
              endTime: null,
              cronExpression: '',
              cronFlag: true
          },
          dataObj: {
              jobName: '',
              jobGroup: '',
              description: '',
              jobClass: '',
              startTime: null,
              interval: 0,
              endTime: null,
              cronExpression: '',
              cronFlag: true
          },
          siteUrl:'',
          rules:  {
                jobName: [
                    {required: true,whitespace: true, message: "任务名称不可为空",  trigger:"change"}
                ],
                jobClass: [
                    {required: true,whitespace: true, message: "任务执行类不可为空",  trigger:"change"}
                ], 
          },
           moment,
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

       
        _this.$axios.get('/admin/jobInfo',{ params: params
            
          }).then(res => {
            let resp  = res.data				
            _this.data = resp.content.list; 
            _this.pagination.total =   resp.content.total;
            _this.pagination.current =   resp.content.page;
            _this.pagination.pageSize =   resp.content.size;
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
        _this.jobModelType = 1;
        _this.importb(); 
      },
      importb() {
            let _this = this; 
            _this.visible = true;
      },
      handleCancel() {
        let _this = this; 
        _this.$refs.dataForm.resetFields();
        _this.visible = false; 
        _this.dataForm = {
              jobName: '',
              jobGroup: '',
              description: '',
              jobClass: '',
              startTime: null,
              interval: null,
              endTime: null,
              cronExpression: '',
              cronFlag: true  
        }
      },
      searchForm() {
        let _this = this;
        _this.initData();

      },
      editInfo(obj) {
        let _this = this;
        _this.dataForm = {
              jobName: obj.jobName,
              jobGroup: obj.jobGroup,
              description: obj.description,
              jobClass: obj.jobClass,
              startTime: _this.moment(obj.startTime),
              interval: obj.interval?obj.interval/1000.0:null,
              endTime: obj.endTime?_this.moment(obj.endTime):null,
              cronExpression: obj.cronExpression,
              cronFlag: obj.cronFlag
        }
        _this.jobModelType = 2;
        _this.importb(); 
      },    
      deleted(jobName,jobGroup) {
        let _this = this;
        if(jobName) {
          this.$confirm({
              title: '确认删除?',
              onOk() { 
                let param = {
                  jobName: jobName,
                  jobGroup: jobGroup
                }
                _this.$axios.delete("/admin/jobInfo/deleteOne", {data:param}).then(res => {
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
      editData() {  
          let  _this = this;
            _this.editLoading = true;
            _this.$refs.dataForm.validate(valid => {
                if (valid) {
                  let param = JSON.parse(JSON.stringify(_this.dataForm));
                  if(param.startTime) {
                    param.startTime = new Date(param.startTime).getTime();
                  }
                   if(param.endTime) {
                      param.endTime = new Date(param.startTime).getTime();
                   }
                   if(param.interval==0) {
                     param.interval = null;
                   }
                  let url = _this.jobModelType == 1 ? '/admin/jobInfo/save': '/admin/jobInfo/update';
                  _this.$axios.post(url,param).then(res => { 
                          _this.editLoading= false;
                          if(res.data.success) { 
                            _this.$message.destroy();
                            _this.$message.success('保存成功',5);
                            _this.handleCancel(); 
                            _this.initData();
                          } else {
                             _this.$message.destroy();
                            _this.$message.error('保存失败: ' + response,5);
                          }
                  }).catch((response) => {
                          _this.editLoading = false;
                          _this.$message.destroy();
                          _this.$message.error('保存失败: ' + response,5);
                  }); 
                } else {
                    _this.editLoading= false;
                    return false;
                }
            });
      },  
      showInfo(obj) {
        let _this = this;
        _this.infoVisible = true;
        _this.dataObj = obj;
      },
      onInfoClose() {
        let _this = this
        _this.infoVisible = false;
      }
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
.wordBreak{
    word-break:break-all;  
    word-wrap:break-word; 
}
</style>

