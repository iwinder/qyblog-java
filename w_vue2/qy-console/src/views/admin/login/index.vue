<template>
 <div class="main">

          <a-form-model   id="formLogin" layout="horizontal" :model="loginForm" @submit="handleSubmit" @submit.native.prevent>
          <a-form-model-item>
            <a-input v-model="loginForm.username" placeholder="用户名">
              <a-icon slot="prefix" type="user" style="color:rgba(0,0,0,.25)" />
            </a-input>
          </a-form-model-item>

          <a-form-model-item>
            <a-input v-model="loginForm.password" type="password" placeholder="密码">
              <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
            </a-input>
          </a-form-model-item>

          <a-form-model-item>
            <!-- <a-button
              type="primary"
              html-type="submit"
              :disabled="loginForm.user === '' || loginForm.password === ''"
            >
              Log in
            </a-button> -->

               <a-button block
          size="large"
          type="primary"
          htmlType="submit"
          class="login-button"
          :loading="state.loginBtn"
          :disabled="state.loginBtn"
        >确定</a-button>
          </a-form-model-item>
        </a-form-model>

</div>
</template>

<script >
import Vue from 'vue'
export default Vue.extend({
    layout:'page',
    data() {
        return {
            loginForm: {
                username: "",
                password: ""
            },
            state: {
              time: 60,
              loginBtn: false,
              // login type: 0 email, 1 username, 2 telephone
              loginType: 0,
              smsSendBtn: false
            }
        }
    },
    methods: {
        handleSubmit(e) { 
                    let _this = this;
                _this.state.loginBtn= true;
              _this.$axios.post('/admin/login',_this.loginForm).then(res => {
                    _this.state.loginBtn= false;
                    if(res.data.success) {
                          this.$message.success('登录成功',15);
                           _this.$router.push("/article");
                    } else {
                        _this.$message.error('登录失败: ' + res.data.message,5);
                    }
            }).catch((response) => {
                   _this.state.loginBtn= true;
                     _this.$message.error('登录失败: ' + response,5);
            });
                 
        }
    }
})
</script>


<style   lang="scss" scoped>
  
.user-layout-login {
  label {
    font-size: 14px;
  }
  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }
  .forge-password {
    font-size: 14px;
  }
  button.login-button {
    padding: 0 15px;
    font-size: 16px;
    height: 40px;
    width: 100%;
  }
  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;
    .item-icon {
      font-size: 24px;
      color: rgba(0, 0, 0, 0.2);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;
      &:hover {
        color: #1890ff;
      }
    }
    .register {
      float: right;
    }
  }
}
</style>