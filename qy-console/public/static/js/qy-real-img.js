import Vue from 'vue'
Vue.directive('real-img', async function (el, binding) {//指令名称为：real-img
    let imgURL = binding.value;//获取图片地址 
    if (imgURL) {
        try {
            let exist = await imageIsExist(imgURL); 
            if (exist) {
                el.setAttribute('src', imgURL);
            }  else {
                el.onerror=null;
            } 
        } catch(error) {
            console.log('资源2233error', error);
        }
    }
})

Vue.directive('real-background-img', async function (el, binding) {//指令名称为：real-img
    let imgURL = binding.value;//获取图片地址
    if (imgURL) {
        try {
            let exist = await imageIsExist(imgURL);
            if (exist) {
                el.setAttribute('style', "background-image:url('"+imgURL+"');");
            } else {
                el.onerror=null;
            } 
        } catch(error) {
            console.log('资源22error', error);
        }

    }
})

/**
 * 检测图片是否存在
 * @param url
 */
let imageIsExist = function(url) { 
    return new Promise((resolve) => {
        var img = new Image();
        img.onload = function () { 
            if (img.complete == true){
                resolve(true);
                img = null;
            }
        }
        img.onerror = function () { 
            //console.log('资源error', img);
            resolve(false);
            img = null; 
        }
        img.src = url;
    })
}