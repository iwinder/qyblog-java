// import SessionStorage from './session-storage'
// import LocalStorage from './local-storage'
import * as Cookies from "js-cookie";
import   md5 from 'js-md5'
const QyServeTool = {
  /**
 * 空校验 null或""都返回true
 */
isEmpty: function (obj) {
  if ((typeof obj == 'string')) {
    return !obj || obj.replace(/\s+/g, "") == ""
  } else {
    return (!obj ||  obj.length === 0);
  }
},

/**
 * 非空校验
 */
isNotEmpty: function (obj) {
  return !this.isEmpty(obj);
},

/**
 * 长度校验
 */
isLength: function (str, min, max) {
  return $.trim(str).length >= min && $.trim(str).length <= max;
},
 
 /**
 * 时间格式化，date为空时取当前时间
 */
dateFormat: function (format, date) {
  let result;
  if (!date) {
    date = new Date();
  }
  const option = {
    "y+": date.getFullYear().toString(),        // 年
    "M+": (date.getMonth() + 1).toString(),     // 月
    "d+": date.getDate().toString(),            // 日
    "h+": date.getHours().toString(),           // 时
    "m+": date.getMinutes().toString(),         // 分
    "s+": date.getSeconds().toString()          // 秒
  };
  for (let i in option) {
    result = new RegExp("(" + i + ")").exec(format);
    if (result) {
      format = format.replace(result[1], (result[1].length == 1) ? (option[i]) : (option[i].padStart(result[1].length, "0")))
    }
  }
  return format;
},
/**
* 移除对象数组中的对象
* @param array
* @param obj
* @returns {number}
*/
removeObj: function (array, obj) {
 let index = -1;
 for (let i = 0; i < array.length; i++) {
   if (array[i] === obj) {
     array.splice(i, 1);
     index = i;
     break;
   }
 }
 return index;
 },
 /**
 * 10进制转62进制
 * @param number
 * @returns {string}
 * @private
 */
_10to62: function (number) {
  let chars = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ';
  let radix = chars.length;
  let arr = [];
  do {
    let mod = number % radix;
    number = (number - mod) / radix;
    arr.unshift(chars[mod]);
  } while (number);
  return arr.join('');
},
  
/**
 * 随机生成[len]长度的[radix]进制数
 * @param {*} len 
 * @param {*} radix 
 */
uuid: function(len, radix) {
  let chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
  let uuid = [];
  radix = radix || chars.length;
  for (let i = 0; i < len; i++) {
    uuid[i] = chars[0 | Math.random() * radix];
  }
  return uuid.join("");
},

 /**
 * 查找是否有权限
 * @param id 资源id
 */
hasResource: function (id) {
  let _this = this;
  let resources = _this.getLoginUser().resources;
  if (_this.isEmpty(resources)) {
    return false;
  }
  for (let i = 0; i < resources.length; i++) {
    if (id === resources[i].id) {
      return true;
    }
  }
  return false;
}, 

defaultImageFuc: function(event) { 
  var img=event.srcElement;
  img.src = "../../static/images/course/avatar"+this.randomNum(1,8)+".jpg";
  img.onerror = null; //防止一直跳动
},
randomNum: function(minNum,maxNum) {
  // https://www.cnblogs.com/mq0036/p/9139231.html
  // https://www.cnblogs.com/starof/p/4988516.html
  switch(arguments.length){ 
      case 1: 
          return parseInt(Math.random()*minNum+1,10);  
      case 2: 
          return parseInt(Math.random()*(maxNum-minNum+1)+minNum,10);  
      default: 
          return 0;
  } 
},
initCommentByCookie(commentForm) {
  let _this = this; 
  let commentUsername = Cookies.get("comment_remember_author"); 
  if(_this.isNotEmpty(commentUsername)) {
      let commentMail = Cookies.get("comment_remember_mail");
      let  commentUrl = Cookies.get("comment_remember_url");
     commentForm.authorName = commentUsername;
      commentForm.authorEmail = commentMail;
      commentForm.authorUrl = commentUrl;
      commentForm.authotImg  = md5(commentMail);
  }         

}
}
export default ({ app }, inject) => {
  // Set the function directly on the context.app object
  inject("QyServeTool",    ()=> {
      return QyServeTool;
  }  )

}

// Vue.use(LruCache)