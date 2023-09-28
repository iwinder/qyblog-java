<template>
    <a-form-model ref="targetForm" :model="targetForm" :rules="targetRules" v-bind="layout">
        <a-form-model-item has-feedback label="名称" prop="name" required >
            <a-input v-model="targetForm.name" :disabled="targetObj.type=='SYSTEM'"/> 
        </a-form-model-item>
        <a-form-model-item has-feedback label="标识" prop="identifier" required >
            <a-input-number v-model="targetForm.identifier" :disabled="targetObj.type=='SYSTEM'"/> 
        </a-form-model-item>
        <a-form-model-item has-feedback label="是否启用" prop="status" > 
            <a-switch  v-model="targetForm.status" checked-children="是" un-checked-children="否" default-checked :disabled="targetObj.identifier==1"/>
        </a-form-model-item>

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
        typeForm: {},
        tloading: {
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
                name:'',
                identifier:'',
                status:false

            },
            targetRules:  {
                name: [
                    { required: true, message: '请输入媒体库类型名称', trigger: 'blur', whitespace:true },
                    { min: 3, max: 200, message: '名称长度应在3到200', trigger: 'blur' },
                ],
                identifier: [
                    { required: true, message: '请输入媒体库类型标识', trigger: 'blur'  },
                   
                ],
            },
            targetObj: {},
            layout: {
                labelCol: { span: 4 },
                wrapperCol: { span: 14 },
            },
        }
    },
    watch: {
        typeForm(val) {
            let _this = this;
            if(val) { 
                _this.targetForm = val;
                _this.targetObj.id = val.id;
                _this.targetObj.type = val.type;
                _this.targetObj.identifier = val.identifier;
                if(_this.targetForm.identifier==1 && !_this.targetForm.status) {
                    _this.targetForm.status = true;
                }
            } 
        },
        tloading(val) {
            let _this = this; 
            _this.editLoading = val;
        },
        typeType(val) {
            let _this = this;
            _this.targetType = val;
        }
    },
    mounted() {
        let _this = this;
        if(_this.typeForm) {
            _this.targetForm = _this.typeForm;
            _this.targetObj.id =  _this.typeForm.id;
            _this.targetObj.type = _this.typeForm.type;
            _this.targetObj.identifier = _this.typeForm.identifier;
            if(_this.targetForm.identifier==1 && !_this.targetForm.status) {
                _this.targetForm.status = true;
            }
        }
    },
    methods: {
        editTarget(){
            let _this = this;
            _this.editLoading = true;
            _this.$refs.targetForm.validate(valid => {
                if (valid) {
                    _this.targetObj.name = _this.targetForm.name;
                    _this.targetObj.identifier =  _this.targetForm.identifier;
                    _this.targetObj.status =  _this.targetForm.status; 
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