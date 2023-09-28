SESSION_KEY_SITEINFO_BASE = "SESSION_KEY_SITEINFO_BASE"; // 课程管理页面点击章管理时，保存课程信息
SESSION_KEY_CHAPTER = "SESSION_KEY_CHAPTER"; // 章管理页面点击节管理时，保存章信息
SESSION_KEY_LOGIN_USER = "SESSION_KEY_LOGIN_USER";  // 登录信息
SESSION_KEY_LOGIN_TOKEN = "SESSION_KEY_LOGIN_TOKEN";  // 登录Toen
// import Vue from 'vue'
SessionStorage = {
    get: function (key) {
        let v = sessionStorage.getItem(key);
        if (v && typeof(v) !== "undefined" && v !== "undefined") {
            return JSON.parse(v);
        }
    },
    set: function (key, data) {
        sessionStorage.setItem(key, JSON.stringify(data));
    },
    remove: function (key) {
        sessionStorage.removeItem(key);
    },
    clearAll: function () {
        sessionStorage.clear();
    },

};