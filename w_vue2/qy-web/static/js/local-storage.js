LOCAL_KEY_REMEMBER_USER = "LOCAL_KEY_REMEMBER_USER"; // 课程管理页面点击章管理时，保存课程信息
LOCAL_KEY_LOGIN_TOKEN = "LOCAL_KEY_LOGIN_TOKEN";  // 登录Toen
LOCAL_KEY_SITE_GO_LINK = "LOCAL_KEY_SITE_GO_LINK";  // 登录Toen
  LocalStorage = {
    get: function (key) {
        let v = localStorage.getItem(key);
        if (v && typeof(v) !== "undefined" && v !== "undefined") {
            return JSON.parse(v);
        }
    },
    set: function (key, data) {
        localStorage.setItem(key, JSON.stringify(data));
    },
    remove: function (key) {
        localStorage.removeItem(key);
    },
    clearAll: function () {
        localStorage.clear();
    }
};