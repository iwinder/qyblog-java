<template>
        <div>
         <a-page-header
                title="修改用户"
                sub-title=""
                @back="backF()"
            />
            <qy-user-form    
                ref="userForm"
                :userObj = "userObj"
                 :afterSubmit="submitForm"
                 :afterBack="afterBack"> </qy-user-form>
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
        return {
            userObj: {},
            userId:null
        }

    },
    created() {
        let _this = this; 
       _this.userId = _this.$route.params.id;
    },
    mounted() {
         let _this = this;
        _this.loadInfo();
    },
    methods: {
        loadInfo() {
            let  _this = this;
            _this.$axios.get("/admin/user/" + _this.userId).then(res => {
                    if(res.data.success) {
                            _this.userObj = res.data.content; 
                    }
            });
        },
        submitForm(user) {
            let  _this = this;
            _this.$axios.post('/admin/user/save',user).then(res => {
                    _this.$refs.userForm.subLoading = false;
                    if(res.data.success) {
                          this.$message.success('保存成功',15);
                          let logUser = QyTool.getLoginUser();
                          if(logUser.id == user.id) {
                              QyTool.removeLoginUser();

                          }

                    }
            }).catch((response) => {
                    _this.$refs.userForm.subLoading = false;
                     _this.$message.error('保存失败: ' + response,5);
            });
        },
        backF() {
              let _this = this;
                _this.$router.push("/user");
        },
        afterBack() {
            this.backF();
        }
    }
    
})
</script>