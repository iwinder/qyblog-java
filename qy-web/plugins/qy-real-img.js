import Vue from 'vue'
Vue.directive('real-img', async function (el, binding) {//指令名称为：real-img
    let imgURL = binding.value;//获取图片地址
    console.log("imageIsExist binding", binding);
    if (imgURL) {
        let exist = await imageIsExist(imgURL);
        console.log("imgURL", imgURL, exist);
        if (exist) {
            el.setAttribute('src', imgURL);
        } 
    }
})

Vue.directive('real-background-img', async function (el, binding) {//指令名称为：real-img
    let imgURL = binding.value;//获取图片地址
    if (imgURL) {
        let exist = await imageIsExist(imgURL);
        if (exist) {
            el.setAttribute('style', "background-image:url('"+imgURL+"');");
        } 
    }
})

/**
 * 检测图片是否存在
 * @param url
 */
let imageIsExist = function(url) {
    console.log("imageIsExist url", url, img.complete);
    return new Promise((resolve) => {
        var img = new Image();
        img.onload = function () {
            console.log("url", url, img.complete);
            if (img.complete == true){
                resolve(true);
                img = null;
            }
        }
        img.onerror = function () {
            console.log("url onerror", url, this);
            resolve(false);
            img = null;
        }
        img.src = url;
    })
}