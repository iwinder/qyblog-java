<template>
    <a-layout-header     :style="{background: '#fff', padding: 0,  width: '100%' ,position: 'fixed',zIndex: 2}" >
          <a-row class="table-operations">
                <a-col :xs="{span:6}"  :lg="{ span: 12}">
        <a-icon
          class="trigger"
          :type="collapsed ? 'menu-unfold' : 'menu-fold'"
          @click="callChange"
        /> </a-col>
  <a-col  :xs="{span:8, offset: 10 }"  :lg="{ span: 3, offset: 5 }" style="    text-align: center;  " >
        <a-dropdown class="right-dropdown"  placement="bottomCenter">
            <a class="ant-dropdown-link" @click="e => e.preventDefault()">
                         <a-avatar  icon="user"  :src="user.avatar"/>
            </a>
            <a-menu slot="overlay"  @click="onClick">
             <a-menu-divider />
             <a-menu-item key="userIndo" disabled>
                    <a-row class="user-ul">
                        <a-row> {{user.nickname}} </a-row>
                        <a-row> {{user.roleName}} </a-row>
                     </a-row>
                </a-menu-item>
            <a-menu-item key="logout">
               退出
            </a-menu-item>
            </a-menu>
        </a-dropdown> </a-col>
         </a-row>
      </a-layout-header>
      
</template>

<script>
export default {
    props: { 
        isCollapsed: {
            default:  false
        }
    },
     data() {
         return {
             collapsed: this.isCollapsed,
             user: {}
         }
     },
    watch: {
        isCollapsed(val) {
            this.collapsed = val;
        }
    },
    created() {
        let _this = this; 
        _this.initUser();
    },
     methods:{ 
         callChange:function() {
             this.collapsed = !this.collapsed;
              this.$emit('on-collapsed',this.collapsed);
         },
         initUser() {
             let _this  = this;
             _this.user =  QyTool.getLoginUser();
             if (QyTool.isEmpty( _this.user)) {
                    _this.$axios.get('/admin/currentUser' ).then(res => {
                        let resp  = res.data				
                        if(resp && resp.success) {
                            _this.user =   resp.content;  
                            QyTool.setLoginUser(_this.user);
                        }
                });
             }


         },
         onClick({ key }) {
            let _this  = this;
            if(key ==  'logout')  {
                _this.logoutFun();
            }
            
         },
         logoutFun() {
             let _this  = this;
            _this.$axios.get('/admin/logout' ).then(res => {
                 QyTool.removeLoginToken();
                 	_this.$router.push("/login");
          });
         }
     }
}
</script>

<style lang="scss" scoped>
// .ant-dropdown  /deep/  {
//   .menuitem  .user-ul {
//       background: #000;
//       li {
//               list-style-type: none;
//       }

//     }

// }

</style>