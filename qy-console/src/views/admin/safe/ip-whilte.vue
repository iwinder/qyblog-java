<template> 
    <a-row>
        <a-page-header
            title="管理IP白名单"
            sub-title="" 
        />
        <a-row>
             <a-form-model ref="ipForm" :model="ipForm" layout="vertical"  > 
                <a-form-model-item has-feedback  label="IP列表"  prop="configValue"> 
                    <a-input v-model="ipConfigObj.configValue"    type="textarea"  :auto-size="{ minRows: 3, maxRows: 15 }"  placeholder="每行一个" />
                    <template v-if="ipConfigObj.configTip" slot="extra">
                        {{ipConfigObj.configTip}}
                    </template>
                </a-form-model-item>
                <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
                    <a-button  ref="saveButton"  type="primary"  :loading ="loading"  @click="submitForm()">
                        保存
                    </a-button> 
                </a-form-model-item>
             </a-form-model>

        </a-row>

    </a-row>
</template>

<script>
import Vue from 'vue'
export default Vue.extend({
    components: {
        
    },
    data() {
        return {
            ipForm:  {
                configKey: '',
                configValue: '',
                configName: '',
                type: 1,
                configTip: '', 
            },
            ipConfigObj:{},
            loading:false
        }
    },
    mounted() {
        let _this = this;
        _this.initData(); 
    },
    methods: {
        initData() {
            let _this = this;
             _this.$axios.get('/admin/siteInfo/getIpWhiltConfig').then(res => {
                    let resp  = res.data 
                    if(res.data.success) {
                         _this.ipConfigObj =  resp.content;
                    }
            }).catch((response) => {
                     _this.$message.error('获取失败: ' + response,5);
            });
        },
        submitForm(val) {
            let  _this = this;
            if(!_this.ipConfigObj.configKey) {
                  _this.$message.error('数据Key异常无法保存',5);
                  return;
            }
             _this.loading = true;
            _this.$axios.post('/admin/siteInfo/save',_this.ipConfigObj).then(res => {
                _this.loading = false;
                if(res.data.success) {
                    this.$message.success('保存成功',15);
                    _this.initData(); 
                }
            }).catch((response) => {
                    _this.loading = false;
                     _this.$message.error('保存失败: ' + response,5);
            });
        }
    }
})
</script>

<style>

</style>