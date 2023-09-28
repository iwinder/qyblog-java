<template>
     <a-form-model ref="userForm" :model="userForm" :rules="rules" v-bind="layout">

           <a-row>
            <a-col :xs="24"   :lg="{ span: 15, offset: 3 }"  >
            <a-form-model-item has-feedback  label="用户名"  prop="username"> 
                <a-input v-model="userForm.username"  :disabled="usernameDisable"  type="text" autocomplete="off" placeholder="用户名" />
            </a-form-model-item>

            <a-form-model-item has-feedback  label="昵称"  prop="nickname"> 
                <a-input v-model="userForm.nickname" type="text" autocomplete="off" placeholder="昵称" />
            </a-form-model-item>

            <a-form-model-item has-feedback  label="邮箱"  prop="email"> 
                <a-input v-model="userForm.email" type="text" autocomplete="off" placeholder="邮箱" />
            </a-form-model-item>

            <template  v-if="!userForm || !userForm.id">
                    <a-form-model-item has-feedback  label="密码"  prop="password"> 
                    <a-input v-model="userForm.password" type="password" autocomplete="off" placeholder="密码" />
                </a-form-model-item>
            </template>

            <a-form-model-item label="角色">
                <a-select v-model="userForm.roleId" placeholder="用户角色">
                            <a-select-option v-for="d in roleData" :key="d.id">
                            {{ d.name }}
                            </a-select-option>
                </a-select>
        </a-form-model-item>

            <a-form-model-item has-feedback  label="头像"  prop="avatar"> 
                <a-input v-model="userForm.avatar" type="text" autocomplete="off" />
                <a-upload
                    name="file"
                    list-type="picture-card"
                    class="avatar-uploader"
                    :action = "'/api/admin/fileMeta/upload'"
                    :accept="acceptType"
                    :show-upload-list="false" 
                    :before-upload="beforeUpload"
                    @change="handleChange"
                >
                <img v-if="userForm.avatar" :src="userForm.avatar" alt="avatar" style="width: 100%;" />
                <div v-else>
                <a-icon :type="loading ? 'loading' : 'plus'" />
                <div class="ant-upload-text">
                    Upload
                </div>
                </div>
            </a-upload>
            </a-form-model-item>

            <a-form-model-item has-feedback label="禁用" prop="disable">
            <a-switch v-model="userForm.disable" >
                <a-icon slot="checkedChildren" type="check" />
                    <a-icon slot="unCheckedChildren" type="close" />
            </a-switch>
        </a-form-model-item>

                <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
            <a-button  ref="saveButton"  type="primary"  :loading ="subLoading"  @click="submitForm('userForm')">
                保存
            </a-button>
            <a-button v-if="!userForm || !userForm.id" style="margin-left: 10px" @click="resetForm">
                重置
            </a-button>

            <a-button  v-else style="margin-left: 10px" @click="backForm">
                取消
            </a-button>
        </a-form-model-item>

        </a-col>
        
        </a-row>
     </a-form-model>
</template>

<script >
import Vue from 'vue'
export default Vue.extend({
    props: { 
        userObj: {},
        afterSubmit: {
            type: Function,
            default: null
        }, 
        afterBack: {
            type: Function,
            default: null
        }
    },
    watch: {
        userObj(val) {
             let _this = this;
             _this.userForm = val;
             if(val) {
                 _this.usernameDisable = true;
             }
           
        }
    },
    data() {
        return {
            usernameDisable: false,
            userForm: {
                nickname: null,
                avatar: '',
                disabled: false,
                roleId: null,
            },
             rules: {

            },
             loading: false,
             subLoading: false,
            layout:   {
                labelCol: { span: 5 },
                wrapperCol: { span: 8 }
            },
            acceptType: "image/*",
            roleData:[]
        }
    },
    mounted() {
        let _this = this;
        _this.initRoleData();
    },
    methods: {
        submitForm(formName) {
            let  _this = this;
            _this.subLoading = true;
            _this.$refs[formName].validate(valid => {
                if (valid) {
                    _this.afterSubmit(_this.userForm); 
                } else {
                     _this.subLoading = false;
                    return false;
                }
            });
        },
        resetForm() {
             this.$refs.userForm.resetFields(); 
        },
        backForm() {
            this.afterBack();
        },
        beforeUpload(file) {
            const isLt2M = file.size / 1024 / 1024 < 2;
            if (!isLt2M) {
                this.$message.error('Image must smaller than 2MB!');
            }
            return  isLt2M;

        },
        handleChange(info) { 
            if (info.file.status === 'uploading') {
                    this.loading = true;
                    return;
                }
                if (info.file.status === 'done') { 
                     let resp = info.file.response;
                     if(resp.success) {
                         this.userForm.avatar = resp.content.defUrl;
                         
                     }
                      this.loading = false;
                }
        },
        initRoleData() {
                let _this = this;
                _this.$axios.get('/admin/role/list').then(res => {
                                // _this.editLoading = false;
                                if(res.data.success) {
                                    _this.roleData = res.data.content;
                                } else {
                                    this.$message.error(res.data.message,5);
                                }
                }).catch((response) => {
                    _this.editLoading = false;
                     this.$message.error(response,5);
                });
        }
    }
    
})
</script>