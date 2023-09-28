<template>
 



 <a-layout   style="min-height: 100vh" >
 
      <a-layout-content class="container">
        <div class="top">
        <div class="header">
          <a href="/">
            <img  :src="site.site_logo" class="logo" alt="logo">
            <span class="title">{{site.site_name}}</span>
          </a>
        </div>
        <div class="desc">
           {{site.site_description}}
        </div>
      </div>
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

            <a-layout-footer class="footer">
        <div class="links">
          <a href="_self">帮助</a>
          <a href="_self">隐私</a>
          <a href="_self">条款</a>
        </div>
        <div class="copyright">
          Copyright &copy; 2018 vueComponent
        </div>
      </a-layout-footer>
      </a-layout-content>

     
      
      </a-layout>
</template>

<script >
import Vue from 'vue'
 import { mapState } from 'vuex'

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
            }, 
            // site: {}
        }
    },
    computed: {
        ...mapState({
        site: state => state.siteInfo.siteInfo
        })
    },
    created() {
      let _this = this; 
      _this.initSiteInfo(); 
  },
    mounted(){
      let _this  =this;
 
    },
    methods: {
        handleSubmit(e) {
                    let _this = this;
                _this.state.loginBtn= true;
              _this.$axios.post('/admin/login',_this.loginForm).then(res => {
                    _this.state.loginBtn= false;
                    	let resp = res.data;
                    if(resp.success) {
                      			
                          let loginToken = resp.content.token;
                          QyTool.setLoginToken(loginToken);
                          _this.$message.success('登录成功',5);
                          	_this.$router.push("/");
                    } else {
                        _this.state.loginBtn= false;
                        _this.$message.error('登录失败2: ' + resp.message,5);
                    }
            }).catch((response) => {
                   _this.state.loginBtn= false;
                     _this.$message.error('登录失败:1 ' + response,5);
            });
                 
        },
        initSiteInfo() {
              let _this = this; 
              if(QyTool.isEmpty( _this.site)) {
                  _this.$store.dispatch("initSiteBase");
              } 
          },
          initIco(url) {

          } 
    }
})
</script>






<style lang="scss" scoped>

    .container  {
      width: 100%;
      min-height: 100%;
          background: #f0f2f5;
      // background: #f0f2f5 url(~@/assets/background.svg) no-repeat 50%;
      background-size: 100%;
      padding: 110px 0 144px;
      position: relative;
      a {
        text-decoration: none;
      }
      .top {
        text-align: center;
        .header {
          height: 44px;
          line-height: 44px;
          .badge {
            position: absolute;
            display: inline-block;
            line-height: 1;
            vertical-align: middle;
            margin-left: -12px;
            margin-top: -10px;
            opacity: 0.8;
          }
          .logo {
            height: 44px;
            vertical-align: top;
            margin-right: 16px;
            border-style: none;
          }
          .title {
            font-size: 33px;
            color: rgba(0, 0, 0, .85);
            font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
            font-weight: 600;
            position: relative;
            top: 2px;
          }
        }
        .desc {
          font-size: 14px;
          color: rgba(0, 0, 0, 0.45);
          margin-top: 12px;
          margin-bottom: 40px;
        }
      }
      .main {
        min-width: 260px;
        width: 368px;
        margin: 0 auto;
      }
      .footer {
        position: absolute;
        width: 100%;
        bottom: 0;
        padding: 0 16px;
        margin: 48px 0 24px;
        text-align: center;
        .links {
          margin-bottom: 8px;
          font-size: 14px;
          a {
            color: rgba(0, 0, 0, 0.45);
            transition: all 0.3s;
            &:not(:last-child) {
              margin-right: 40px;
            }
          }
        }
        .copyright {
          color: rgba(0, 0, 0, 0.45);
          font-size: 14px;
        }
      }
    }

</style>



<style   lang="scss" scoped>
  






// .user-layout-login {
//   label {
//     font-size: 14px;
//   }
//   .getCaptcha {
//     display: block;
//     width: 100%;
//     height: 40px;
//   }
//   .forge-password {
//     font-size: 14px;
//   }
//   button.login-button {
//     padding: 0 15px;
//     font-size: 16px;
//     height: 40px;
//     width: 100%;
//   }
//   .user-login-other {
//     text-align: left;
//     margin-top: 24px;
//     line-height: 22px;
//     .item-icon {
//       font-size: 24px;
//       color: rgba(0, 0, 0, 0.2);
//       margin-left: 16px;
//       vertical-align: middle;
//       cursor: pointer;
//       transition: color 0.3s;
//       &:hover {
//         color: #1890ff;
//       }
//     }
//     .register {
//       float: right;
//     }
//   }
// }
</style>


