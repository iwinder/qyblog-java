<template>
        <div>
         <a-page-header
                title="添加用户"
                sub-title=""
                @back="backF()"
            />
            <qy-user-form      
                ref="userForm"
                 :afterSubmit="submitForm"> </qy-user-form  >
    </div>
</template>

<script  >
import Vue from 'vue'
import  QyUserForm  from '../../../components/qy-user-form.vue';

export default Vue.extend({
    components: {
        QyUserForm
    },
    data() {
        return {}
    },
    methods: {
        backF() {
              let _this = this;
                _this.$router.push("/user");
        },
        submitForm(val) {
            let  _this = this;
            _this.$axios.post('/admin/user/save',val).then(res => {
                    _this.$refs.userForm.subLoading = false;
                    if(res.data.success) {
                          this.$message.success('保存成功',15);
                           _this.backF() ;
                    }
            }).catch((response) => {
                    _this.$refs.userForm.subLoading = false;
                     _this.$message.error('保存失败: ' + response,5);
            });
        }
    }
})
</script>