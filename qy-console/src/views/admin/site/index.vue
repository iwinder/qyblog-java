<template>
    <a-row>
      <a-row>
         <a-form-model  ref="optionForm" :model="optionForm" layout="vertical"  >
          <a-tabs default-active-key="1" @change="callback">
              <a-tab-pane key="1" tab="基本信息">
                <a-col :xs="{span:24}"  :lg="{ span: 15}"> 
                <a-form-model-item v-for="(option) in options1"  :key="option.id" :label="option.configName" >
                      <a-col  > 
                                  <a-input v-model="option.configValue"   />  
                      </a-col>
                      <a-col   v-if="option.configTip"    slot="extra"   > 
                                    {{option.configTip}}
                      </a-col>

                 </a-form-model-item>
                 </a-col>
              </a-tab-pane>
              <a-tab-pane key="2" tab="内容" >
                    <a-form-model-item v-for="(option) in options2"  :key="option.id" :label="option.configName" >
                          <a-col  :xs="{span:24}"  :lg="{ span: 12}">
                              <template v-if="option.configKey == 'site_comment_flag'" >
                                            <a-switch v-model="option.configValue" >
                                                   <a-icon slot="checkedChildren" type="check" />
                                                    <a-icon slot="unCheckedChildren" type="close" />
                                            </a-switch> 
                              </template>
                                <template v-else-if="option.configKey.indexOf('site_head_')>=0  || option.configKey == 'site_foot_code'  || option.configKey == 'site_sider_code'" > 
                                          <a-textarea placeholder="输入内容"  v v-model="option.configValue"   :rows="4" />
                                </template>
                               <a-input v-else  v-model="option.configValue" />
                        </a-col>
                         <template v-if="option.configTip" slot="extra">
                                {{option.configTip}}
                          </template>
                     </a-form-model-item>
              </a-tab-pane>
              <a-tab-pane key="3" tab="社会化">
                     <a-form-model-item v-for="(option) in options3"  :key="option.id" :label="option.configName" >
                        <a-col  :xs="{span:24}"  :lg="{ span: 12}">
                          <a-input v-model="option.configValue" />
                       </a-col>
                         <template v-if="option.configTip" slot="extra">
                              {{option.configTip}}
                          </template>
                     </a-form-model-item>
              </a-tab-pane>
              <a-tab-pane key="4" tab="其他">
                      <a-form-model-item v-for="(option) in options4"  :key="option.id" :label="option.configName" >
                            <a-col  :xs="{span:24}"  :lg="{ span: 12}">
                                  <template v-if="option.configKey == 'site_pay_flag'" >
                                        <a-switch v-model="option.configValue" >
                                              <a-icon slot="checkedChildren" type="check" />
                                                <a-icon slot="unCheckedChildren" type="close" />
                                        </a-switch> 
                                  </template>
                                  <template v-else-if="option.configKey == 'site_default_media_lib'" >
                                    
                                      <a-select   
                             v-model="option.configValue"  placeholder="文件保存位置" > 
                                    <a-select-option v-for="o in typeList" :key="o.identifier+''">
                                    {{ o.name }}
                                    </a-select-option>

                             </a-select>
                                  </template>
                                 <a-input v-else  v-model="option.configValue" /> 
                             </a-col>
                             <template v-if="option.configTip" slot="extra">
                                   {{option.configTip}}
                          </template>
                     </a-form-model-item>
              </a-tab-pane>
        </a-tabs>
                <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
            <a-button  ref="saveButton"  type="primary"  :loading ="loading"  @click="submitForm()">
                保存
            </a-button>
            <!-- <a-button v-show="!articleForm.id" style="margin-left: 10px" @click="resetForm">
                重置
            </a-button> -->
        </a-form-model-item>
         </a-form-model>
      </a-row>
    </a-row>
</template>

<script  >
import Vue from 'vue'
export default Vue.extend({
  data() {
    return {
      loading: false,
      options1:[],
      options2:[],
      options3:[],
      options4:[],
      optionForm: {},
      layout: {
          labelCol: { span: 4 },
          wrapperCol: { span: 14 },
      }, 
     typeList: {},
      MEDIA_LIB_TYPE:MENUS_AGENT_IDENTIFIER_ARRAY,
    };
  },
  mounted() {
    let _this = this;
    _this.list();
    _this.initTypeList();
  },
  methods: {
    callback(key) { 
    },
    initOptions() {
      let _this = this;
      _this.options1 = [];
      _this.options2 = [];
      _this.options3 = [];
      _this.options4 = [];
    },
    list() {
      let _this = this;
      _this.initOptions();
      _this.$axios.get('/admin/siteInfo').then(res => {
            let resp  = res.data
            _this.data = resp.content;
              _this.data.forEach(e => {
                  if(e.type == 1) {
                    _this.options1 .push(e);
                  } else if(e.type == 2) {
                    if(e.configKey == 'site_comment_flag') {
                          e.configValue =   (e.configValue === "true") ? true : false;
                    }
                    _this.options2 .push(e);
                  } else if(e.type == 3){
                    _this.options3 .push(e);
                  } else {
                     if(e.configKey == 'site_pay_flag') {
                          e.configValue =  ( e.configValue === "true") ? true : false;
                    }
                    _this.options4 .push(e);
                  }
              });
            _this.loading = false;
            _this.selectedIds = [];
        }).catch((response) => {
            _this.loading = false; 
        });
    },
    submitForm() {
      let _this  = this;
      let param = [
        ..._this.options1,
        ..._this.options2,
        ..._this.options3,
         ..._this.options4,
      ]
      // param.forEach(e=>{
      //   if(e.configKey == 'site_comment_flag' || e.configKey == 'site_pay_flag') {
      //     e.configValue = e.configValue ? "true" : "false"
      //   } 
      // });
        _this.loading = true;
     _this.$axios.post('/admin/siteInfo/saveList',param).then(res => {
              _this.loading = false;
              if(res.data.success) {
                    this.$message.success('保存成功',15);
                    QyTool.removeSiteInfoBase();
                     _this.$store.dispatch("initSiteBase");
              }
      }).catch((response) => {
              _this.loading = false;
                _this.$message.error('保存失败: ' + response,5);
      });

    },
    initTypeList() {
        let _this = this; 
        let params = {
            status:  true, 
        }  
        _this.$axios.get('/admin/fileLibType/allList',
            { params: params}).then(res => {
            let resp  = res.data
            _this.typeList = resp.content; 
        }).catch((response) => { 
        });
    },
  },
})
</script>