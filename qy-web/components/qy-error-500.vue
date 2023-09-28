<template>
    <a-row class="error-500" type="flex" justify="center"  align="middle">

        <a-col  flex="auto" class="box">
    <div class="box_ghost">
        <div class="symbol"></div>
        <div class="symbol"></div>
        <div class="symbol"></div>
        <div class="symbol"></div>
        <div class="symbol"></div>
        <div class="symbol"></div>

        <div class="box_ghost-container">
            <div class="box_ghost-eyes"   :style="{'transform': 'translate('+ xAxis +'%,-'+ yAxis +'%)'}">
                <div class="box__eye-left"></div>
                <div class="box__eye-right"></div>
            </div>
            <div class="box_ghost-bottom">
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
            </div>
        </div>
        <div class="box_ghost-shadow"></div>
    </div>

    <div class="box_description">
        <div class="box_description-container">
            <div class="box_description-title">服务器内部错误</div>
            <div class="box_description-text">服务器遇到错误，无法完成请求</div>
            <div class="box_description-text">{{errMsg}}</div>
        </div>  
        <a href="javascript:void(0);" class="box_button" @click="toIndex()" >返回</a>

    </div>

    </a-col>
    </a-row>
</template>

<script  >
import Vue from 'vue'
export default Vue.extend({
    props: {  
        errMsg: ""
    },
    data(){
        return {
            pageX:0, 
            pageY:0 ,
            mouseY:0,
            mouseX:0,
            yAxis:0,
            xAxis:0
        }
    },
    mounted() {
        let _this = this;
      _this.initPageMousemove();
    },
    methods: { 
         toIndex() {
            let _this  = this;
           _this.$router.push("/");
       },
       initPageMousemove() {
           let _this = this;
           _this.pageX = document.body.clientWidth;
            _this.pageY = document.body.clientHeight;
            window.onresize = () => {
                return (() => {
                _this.pageX = document.body.clientWidth;
                _this.pageY = document.body.clientHeight;
                })();
            };
            document.onmousemove = function(event) { 
                        _this.mouseY = event.pageY; 
                    _this.yAxis = (_this.pageY/2-_this.mouseY)/_this.pageY*300;
                        //horizontalAxis
                        _this.mouseX = event.pageX / -_this.pageX;
                        _this.xAxis = - _this.mouseX * 100 - 100; 
            }
        },
    }
})
</script>

<style scoped>
   body,html{background:#f0f2f5;font-family:Ubuntu}
        *{box-sizing:border-box}
    .error-500{height: 100vh; min-height: 500px;    background: #f0f2f5;
}
        .box{padding:30px 50px;width:350px;height:100%;max-height:500px;min-height:400px;border-radius:20px;background:#332f63;}
        .box .box_ghost{position:absolute;top:30%;left:50%;padding:15px 25px 25px;transform:translate(-50%,-30%)}
        .box .box_ghost .symbol:nth-child(1){opacity:.2;animation:shine 4s ease-in-out 3s infinite}
        .box .box_ghost .symbol:nth-child(1):after,.box .box_ghost .symbol:nth-child(1):before{position:absolute;bottom:65px;left:0;width:12px;height:4px;border-radius:5px;background:#fff;content:''}
        .box .box_ghost .symbol:nth-child(1):before{transform:rotate(45deg)}
        .box .box_ghost .symbol:nth-child(1):after{transform:rotate(-45deg)}
        .box .box_ghost .symbol:nth-child(2){position:absolute;top:30px;left:-5px;width:18px;height:18px;border:4px solid;border-color:#fff;border-radius:50%;opacity:.2;animation:shine 4s ease-in-out 1.3s infinite}
        .box .box_ghost .symbol:nth-child(3){opacity:.2;animation:shine 3s ease-in-out .5s infinite}
        .box .box_ghost .symbol:nth-child(3):after,.box .box_ghost .symbol:nth-child(3):before{position:absolute;top:5px;left:40px;width:12px;height:4px;border-radius:5px;background:#fff;content:''}
        .box .box_ghost .symbol:nth-child(3):before{transform:rotate(90deg)}
        .box .box_ghost .symbol:nth-child(3):after{transform:rotate(180deg)}
        .box .box_ghost .symbol:nth-child(4){opacity:.2;animation:shine 6s ease-in-out 1.6s infinite}
        .box .box_ghost .symbol:nth-child(4):after,.box .box_ghost .symbol:nth-child(4):before{position:absolute;top:10px;right:30px;width:15px;height:4px;border-radius:5px;background:#fff;content:''}
        .box .box_ghost .symbol:nth-child(4):before{transform:rotate(45deg)}
        .box .box_ghost .symbol:nth-child(4):after{transform:rotate(-45deg)}
        .box .box_ghost .symbol:nth-child(5){position:absolute;top:40px;right:5px;width:12px;height:12px;border:3px solid;border-color:#fff;border-radius:50%;opacity:.2;animation:shine 1.7s ease-in-out 7s infinite}
        .box .box_ghost .symbol:nth-child(6){opacity:.2;animation:shine 2s ease-in-out 6s infinite}
        .box .box_ghost .symbol:nth-child(6):after,.box .box_ghost .symbol:nth-child(6):before{position:absolute;right:-5px;bottom:65px;width:15px;height:4px;border-radius:5px;background:#fff;content:''}
        .box .box_ghost .symbol:nth-child(6):before{transform:rotate(90deg)}
        .box .box_ghost .symbol:nth-child(6):after{transform:rotate(180deg)}
        .box .box_ghost .box_ghost-container{position:relative;margin:0 auto;width:100px;height:100px;border-radius:100px 100px 0 0;background:#fff;animation:upndown 3s ease-in-out infinite}
        .box .box_ghost .box_ghost-container .box_ghost-eyes{position:absolute;top:45%;left:50%;width:70px;height:12px}
        .box .box_ghost .box_ghost-container .box_ghost-eyes .box__eye-left{position:absolute;left:0;margin:0 10px;width:12px;height:12px;border-radius:50%;background:#332f63}
        .box .box_ghost .box_ghost-container .box_ghost-eyes .box__eye-right{position:absolute;right:0;margin:0 10px;width:12px;height:12px;border-radius:50%;background:#332f63}
        .box .box_ghost .box_ghost-container .box_ghost-bottom{position:absolute;top:100%;right:0;left:0;display:flex}
        .box .box_ghost .box_ghost-container .box_ghost-bottom div{position:relative;top:-10px;height:20px;border-radius:100%;background-color:#fff;flex-grow:1}
        .box .box_ghost .box_ghost-container .box_ghost-bottom div:nth-child(2n){top:-12px;margin:0 0;border-top:15px solid #332f63;background:0 0}
        .box .box_ghost .box_ghost-shadow{margin:0 auto;height:20px;border-radius:50%;box-shadow:0 50px 15px 5px #3b3769;animation:smallnbig 3s ease-in-out infinite}
        .box .box_description{position:absolute;bottom:30px;left:50%;transform:translateX(-50%)}
        .box .box_description .box_description-container{margin:0 auto;width:200px;color:#fff;text-align:center;font-size:16px}
        .box .box_description .box_description-container .box_description-title{letter-spacing:.5px;font-size:24px}
        .box .box_description .box_description-container .box_description-text{margin-top:20px;color:#8c8aa7;line-height:20px}
        .box .box_description .box_button{position:relative;display:block;overflow:hidden;margin-top:25px;padding:0 70px;height:50px;border:1px solid transparent;border-radius:50px;background:#ff5e65;color:#fff;text-align:center;text-decoration:none;white-space:nowrap;font-size:18px;line-height:50px;transition:background .5s ease}
        @keyframes upndown{0%{transform:translateY(5px)}
            50%{transform:translateY(15px)}
            100%{transform:translateY(5px)}
        }
        @keyframes smallnbig{0%{width:90px}
            50%{width:100px}
            100%{width:90px}
        }
        @keyframes shine{0%{opacity:.2}
            25%{opacity:.1}
            50%{opacity:.2}
            100%{opacity:.2}
        }
</style>