<template>
    <div>
        <a-row>
            <a-page-header
                style="border: 1px solid rgb(235, 237, 240)"
                :title="'配置'+typeObj.name"
                sub-title="This is a subtitle"
                @back="backF()"
            />
            <a-tabs default-active-key="1" @change="callback">
                <a-tab-pane key="1" tab="基础信息">
                    <qy-file-lib-type-form :typeForm="typeObj" :tloading="typeLoading" :afterSubmit="editType" :afterCancel="handleTypeCancel"></qy-file-lib-type-form>
                </a-tab-pane>
                <a-tab-pane key="2" tab="详细配置" force-render>
                    <qy-file-lib-config-form :configForm="configObj" :loading="configLoading" :configType="typeId"  :afterSubmit="editConfig" :afterCancel="handleConfigCancel"></qy-file-lib-config-form>
                </a-tab-pane> 
            </a-tabs>
        </a-row>
    </div>
</template>

<script  >
import Vue from 'vue'
import  QyFileLibTypeForm  from '../../../../components/qy-file-lib-type-form.vue'
import  QyFileLibConfigForm  from '../../../../components/qy-file-lib-config-form.vue'
 
export default Vue.extend({
    components: {
        QyFileLibTypeForm,
        QyFileLibConfigForm
    },
    data()  {
        return {
            typeObj:{
                name:''
            },
            configObj:{},
            typeLoading: false,
            configLoading: false,
            typeId:null
        }
    },
    created() {
        let _this = this; 
        _this.typeId = parseInt(_this.$route.params.id);
        if(_this.$route.params.obj) {
            _this.typeObj = _this.$route.params.obj; 
        }
        
    },
    mounted() {
        let _this = this;
        if(_this.typeId) {
            if(!_this.typeObj || !_this.typeObj.name) {
                _this.loadTypeInfo();
            }
            _this.loadConfigInfo();
        }

    },  
    methods:{
        callback() {

        },
        backF() {
            let _this = this;
            _this.$router.push("/media-lib-config");
        },
        loadTypeInfo() {
            let  _this = this;
            _this.$axios.get("/admin/fileLibType/" + _this.typeId).then(res => {
                if(res.data.success) {
                    _this.typeObj =  res.data.content; 
                }
            });
        },
        loadConfigInfo() {
            let  _this = this;
            _this.$axios.get("/admin/fileLibConfig/byType/" + _this.typeId).then(res => {
                if(res.data.success) {
                    _this.configObj =  res.data.content; 
                }
            });
        },
        editConfig(param){
            let _this = this;
            _this.configLoading = true;
            _this.$axios.post('/admin/fileLibConfig/save', param).then(res => {
                _this.configLoading = false;
                if(res.data.success) {
                    _this.$message.success('保存成功',5); 
                    _this.targetConfig = {};
                } else {
                    _this.$message.error(res.data.message,5);
                }
            }).catch((response) => {
                _this.configLoading = false;
                _this.$message.error(response,5);
            });
         
          
        },
        handleConfigCancel() {
            let _this = this;
            _this.configVisible = false;
            _this.editConfigLoading = false;
            _this.targetConfig = {};
        },
        editType(param) {
            let _this = this;
             _this.typeLoading = true;
            _this.$axios.post('/admin/fileLibType/save', param).then(res => {
                _this.typeLoading = false;
                if(res.data.success) {
                    _this.$message.success('保存成功',5); 
                    _this.targetConfig = {};
                } else {
                    _this.$message.error(res.data.message,5);
                }
            }).catch((response) => {
                _this.typeLoading = false;
                _this.$message.error(response,5);
            });
        },
        handleTypeCancel() {
            let _this = this;
            _this.configVisible = false;
            _this.editConfigLoading = false;
            _this.targetConfig = {};
        },
    }
})
</script>

<style lang="scss" scoped>

</style>