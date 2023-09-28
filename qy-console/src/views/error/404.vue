<template>
    <a-layout style="min-height: 100vh"  >
         <a-layout-content class="container"> 
             <!-- <div class="main">
                 
             </div> -->
             <a-row     class="img-404"> </a-row>
             <p class="notfound-p">哎呀迷路了...</p>   
             <a-row class="notfound-reason">
                 <p>可能的原因：</p>
                 <ul>
                    <li>原来的页面不存在了</li>
                    <li>你查找的方式有误</li>
                    <li>服务器被外星人劫持了</li>
             </ul>
             </a-row>
             <a-row class="notfound-btn-container">
                    <a-button  type="primary"  @click="toBack()">
                        返回
                    </a-button>
                   <a-button  type="primary"  @click="toIndex()">
                        首页
                    </a-button>
             </a-row>

        </a-layout-content>


    </a-layout  >
          
</template>


<script  >
import Vue from 'vue'
import { log } from 'util';
export default Vue.extend({
    data(){
        return {

        }
    },
   methods:{
       toBack() {
           let _this  = this;
           this.$router.go(-1);
       },
       toIndex() {
            let _this  = this;
           _this.$router.push("/");
       }
   } 

    
})
</script>
 
<style lang="scss" scoped>
 
    .container {
          height: 100%;
    background: #f0f2f5; 
        .img-404 {
            height: 155px;
            background: url(../../assets/images/404.png) center center no-repeat;
            background-size: 150px auto;
            margin-top: 40px;
            margin-bottom: 20px;
        }
        .notfound-p{
                line-height: 22px;
                font-size: 17px;
                padding-bottom: 15px;
                border-bottom: 1px solid #f6f6f6;
                text-align: center;
                color: #262b31;
        }
        .notfound-reason {
            color: #9ca4ac;
            font-size: 13px;
            line-height: 13px;
            text-align: left;
            width: 210px;
            margin: 0 auto;
        }
        .notfound-btn-container {
            margin: 40px auto 0;
            text-align: center;
            button {
                    margin: 0 20px;
            }
        }
    }
 


 
</style>