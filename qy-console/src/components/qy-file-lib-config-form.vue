<template>
    <a-form-model ref="targetForm" :model="targetForm" :rules="targetRules" v-bind="layout">
        <a-form-model-item has-feedback label="AccessKey" prop="accessKey">
            <a-input v-model="targetForm.accessKey" /> 
        </a-form-model-item>
        <a-form-model-item has-feedback label="SecretKey" prop="secretKey">
            <a-input v-model="targetForm.secretKey" /> 
        </a-form-model-item>
        <a-form-model-item has-feedback label="Bucket" prop="bucket">
            <a-input v-model="targetForm.bucket" /> 
        </a-form-model-item>  
        <a-form-model-item has-feedback label="路径前缀" prop="prefix">
                <a-input v-model="targetForm.prefix" /> 
        </a-form-model-item>
        <a-form-model-item has-feedback label="域名" prop="domain">
            <a-input v-model="targetForm.domain" /> 
        </a-form-model-item>
        <template v-if="targetType>2">
            <a-form-model-item has-feedback label="Endpoint" prop="endpoint">
                <a-input v-model="targetForm.endpoint" /> 
            </a-form-model-item> 
        </template>
        <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
        <a-button type="primary" :loading="editLoading" @click="editTarget">
            保存
        </a-button>
        <!-- <a-button style="margin-left: 10px" @click="handleCancel">
            取消
        </a-button> -->
        </a-form-model-item>
    </a-form-model>
</template>
<script >
import Vue from 'vue'
export default Vue.extend({
    props: { 
        configForm: {},
        configType: {
            type: Number,
            default: 1
        },
        loading: {
            type: Boolean,
            default: false
        },
        afterSubmit: {
            type: Function,
            default: null
        },  
        afterCancel: {
            type: Function,
            default: null
        }, 
    },
    data() {
        return {
            editLoading: false,
            targetForm: {
                accessKey:'',
                secretKey:'',
                bucket:'',
                endpoint:'',
                domain:'',
                prefix:'' 

            },
            targetRules:  {
                accessKey:[
                    { required: true, message: '请输入AccessKey', trigger: 'blur', whitespace:true },
                    { max: 255, message: '长度最大255', trigger: 'blur' },
                ],
                secretKey:[
                    { required: true, message: '请输入SecretKey', trigger: 'blur', whitespace:true },
                    { max: 255, message: '长度最大255', trigger: 'blur' },
                ],
                bucket:[
                    { required: true, message: '请输入Bucket', trigger: 'blur', whitespace:true },
                    {  max: 255, message: '长度最大255', trigger: 'blur' },
                ],
                domain:[
                    { required: true, message: '请输入域名', trigger: 'blur', whitespace:true },
                    { max: 255, message: '长度最大255', trigger: 'blur' },
                ],
                prefix:[
                    {  max: 255, message: '长度最大255', trigger: 'blur' },
                ]
            },
            targetObj: {},
            layout: {
                labelCol: { span: 4 },
                wrapperCol: { span: 14 },
            },
            targetType:1,
        }
    },
    watch: {
        configForm(val) {
            let _this = this;
            _this.targetForm = val;
            if(val) {
                _this.targetObj.id = val.id;
            } 
        },
        loading(val) {
            let _this = this;
            _this.editLoading = val;
        },
        configType(val) {
            let _this = this;
            _this.targetType = val;
        }
    },
    mounted() {
        let _this = this;
        if(_this.configForm) {
            _this.targetForm = _this.configForm;
            _this.targetObj.id =  _this.configForm.id;
        }
        if(_this.configType) {
            _this.targetType = _this.configType;
            if(_this.targetType>2) {
                _this.targetRules.endpoint = [
                    { required: true, message: '请输入Endpoint', trigger: 'blur', whitespace:true },
                    {  max: 255, message: '长度最大255', trigger: 'blur' },
                ]; 
            }
        }
    },
    methods: {
        editTarget(){
            let _this = this;
            _this.editLoading = true;
            _this.$refs.targetForm.validate(valid => {
                if (valid) {
                    _this.targetObj.accessKey = _this.targetForm.accessKey;
                    _this.targetObj.secretKey =  _this.targetForm.secretKey;
                    _this.targetObj.bucket =  _this.targetForm.bucket;
                    _this.targetObj.endpoint =  _this.targetForm.endpoint;
                    _this.targetObj.prefix =  _this.targetForm.prefix;
                     _this.targetObj.domain = _this.targetForm.domain;
                    _this.targetObj.typeId = _this.targetType;
                    _this.afterSubmit(_this.targetObj);
                } else {
                    _this.editLoading = false;
                    return false;
                }
            });
        },
        handleCancel() {
            let _this = this; 
            _this.editLoading = false;
            _this.$refs.categoryForm.resetFields();
            _this.afterCancel();
        }
    }
})
</script>

<style lang="scss" scoped>

</style>