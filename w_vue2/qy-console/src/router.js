import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login/index.vue'
import Admin from './views/admin/index.vue'
import User from './views/admin/user/index.vue'
import UserAdd from './views/admin/user/add.vue'
import UserEdit from './views/admin/user/_id.vue'
import Role from './views/admin/role/index.vue'
import Permission from './views/admin/permission/index.vue'
import Article from './views/admin/article/index.vue'
import ArticleAdd from './views/admin/article/add.vue'
import ArticleEdit from './views/admin/article/_id.vue'
import Page from './views/admin/page/index.vue'
import PageAdd from './views/admin/page/add.vue'
import PageEdit from './views/admin/page/_id.vue'
import Tag from './views/admin/tag/index.vue'
// import TagAdd from './views/admin/tag/add.vue'
// import TagEdit from './views/admin/tag/_id.vue'
import Category from './views/admin/category/index.vue'
import CategoryAdd from './views/admin/category/add.vue'
import CategoryEdit from './views/admin/category/_id.vue'
import Comment from './views/admin/comment/index.vue'
import Site from './views/admin/site/index.vue'
import Menus from './views/admin/menus/index.vue'
import MenusEdit from './views/admin/menus/_id.vue'
import Link from './views/admin/link/index.vue'
import ShortLink from './views/admin/short-link/index.vue'
import Dashboard from './views/admin/dashboard/index.vue'
import NotFound  from './views/error/404.vue'
import ErrorPage  from './views/error/500.vue'
import IpBlack from './views/admin/safe/ip-black.vue'
import LoginLog from './views/admin/safe/login-log.vue'
import LIpWhilte from './views/admin/safe/ip-whilte.vue'
import FileLib from './views/admin/file-lib/index.vue' 
import FileLibConfig from './views/admin/file-lib/config/index.vue' 
import FileLibConfigEdit from './views/admin/file-lib/config/_id.vue' 
import JobConfig from './views/admin/job/index.vue'
// import Teacher from './views/admin/teacher.vue'
// import File from './views/admin/file.vue'
// import Content from './views/admin/content.vue'

// import Resource from './views/admin/resource.vue'

// import Member from "./views/admin/member.vue"

// const _09edad84 = () => interopDefault(import('../pages/article/index.vue' /* webpackChunkName: "pages/article/index" */))
// const _6d1d638c = () => interopDefault(import('../pages/category/index.vue' /* webpackChunkName: "pages/category/index" */))
// const _a0234932 = () => interopDefault(import('../pages/comment/index.vue' /* webpackChunkName: "pages/comment/index" */))
// const _736e5f31 = () => interopDefault(import('../pages/login/index.vue' /* webpackChunkName: "pages/login/index" */))
// const _3486ea5d = () => interopDefault(import('../pages/permission/index.vue' /* webpackChunkName: "pages/permission/index" */))
// const _41b2a284 = () => interopDefault(import('../pages/role/index.vue' /* webpackChunkName: "pages/role/index" */))
// const _6be91fd6 = () => interopDefault(import('../pages/site/index.vue' /* webpackChunkName: "pages/site/index" */))
// const _4359053c = () => interopDefault(import('../pages/tag/index.vue' /* webpackChunkName: "pages/tag/index" */))
// const _09ccb0ce = () => interopDefault(import('../pages/user/index.vue' /* webpackChunkName: "pages/user/index" */))
// const _b41ec2e6 = () => interopDefault(import('../pages/article/add.vue' /* webpackChunkName: "pages/article/add" */))
// const _64cb145b = () => interopDefault(import('../pages/category/add.vue' /* webpackChunkName: "pages/category/add" */))
// const _6d32f7a8 = () => interopDefault(import('../pages/user/add.vue' /* webpackChunkName: "pages/user/add" */))
// const _44ac6326 = () => interopDefault(import('../pages/article/_id.vue' /* webpackChunkName: "pages/article/_id" */))
// const _0386d8f4 = () => interopDefault(import('../pages/category/_id.vue' /* webpackChunkName: "pages/category/_id" */))
// const _0beebc41 = () => interopDefault(import('../pages/user/_id.vue' /* webpackChunkName: "pages/user/_id" */))
// const _07bdd017 = () => interopDefault(import('../pages/index.vue' /* webpackChunkName: "pages/index" */))

 
Vue.use(Router);

const router = new Router({
    mode: 'history',// hash 会变成 xxx.com/#/xxx
    base: process.env.BASE_URL,
    routes: [{
        path: '*',
        redirect: '/404',
    },{
        path: '',
        redirect: '/dashboard',
    }, {
        path: '/login',
        component: Login
    }, {
        path: '/404',
        component: NotFound
    },   {
        path: '/500', 
        component: ErrorPage
    }, {
        path: '/',
        name: 'admin', // 为每个路由增加name属性，后续做通用sidebar激活样式方法时需要用到，
        component: Admin,
        meta:{
            loginRequire: true // 属性名称可以自定义
        },
        children: [
            {
                path: 'dashboard',
                name: 'dashboard',
                component: Dashboard,
            }, 
            {
                    path: 'user',
                    name: 'user',
                    component: User,
                    
            },
            {
                path: 'user/add',
                name: 'user-add',
                component: UserAdd,
            },
            {
                path: 'user/:id',
                name: 'user-id',
                component: UserEdit,
            },
            {
                path: 'role', 
                name: 'role',
                component: Role,
            },
            {
                path: 'permission', 
                name: 'permission',
                component: Permission,
            },
             {
                path: "site",
                name: 'site',
                component: Site,
            },
            {
                path: 'article',
                name: 'article',
                component: Article,
            }, 
            {
                path: 'article/add',
                name: 'article-add',
                component: ArticleAdd,
            },
            {
                path: 'article/:id',
                name: 'article-id',
                component: ArticleEdit,
            },
            {
                path: 'page',
                name: 'page',
                component: Page,
            }, 
            {
                path: 'page/add',
                name: 'page-add',
                component: PageAdd,
            },
            {
                path: 'page/:id',
                name: 'page-id',
                component: PageEdit,
            },
            {
                path: 'tag',
                name: 'tag',
                component: Tag,
            },
            {
                path: 'category',
                name: 'category',
                component: Category,
            },
        {
            path: 'category/add',
            name: 'category-add',
            component: CategoryAdd,
        },
        {
            path: 'category/:id',
            name: 'category-id',
            component: CategoryEdit,
        },
        {
            path: 'comment',
            name: 'comment',
            component: Comment,
        },
        {
            path: 'menus',
            name: 'menus',
            component: Menus,
             
        },
        {
            path: 'menus/:id',
            name: 'menus-id',
            component: MenusEdit,
        }
        ,{
            path: 'link',
            name: 'link',
            component: Link,
        },
        {
            path: 'short-link',
            name: 'short-link',
            component: ShortLink,
        },
        {
            path: 'safe/ip-black',
            name: 'ip-black',
            component: IpBlack,
        },
        {
            path: 'safe/login-log',
            name: 'login-log',
            component: LoginLog,
        },
        {
            path: 'safe/ip-whilte',
            name: 'ip-whilte',
            component: LIpWhilte,
        },
        {
            path: 'media-lib',
            name: 'media-lib',
            component: FileLib
        },
        {
            path: 'media-lib-config',
            name: 'media-lib-config',
            component: FileLibConfig
        },
        {
            path: 'media-lib-config/:id',
            name: 'media-lib-config-id',
            component: FileLibConfigEdit,
        },
        {
            path:'job',
            name: 'job',
            component: JobConfig
        }
    ] // admin child end
    }]
})
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
   return originalPush.call(this, location).catch(err => err)
}

// 路由登录拦截
router.beforeEach((to, from, next) => {
    // 要不要对meta.loginRequire属性做监控拦截 
    if(to.matched.some(function(item){ 
      return item.meta.loginRequire
    })) {
      let loginToken =  QyTool.getLoginToken();
      if(QyTool.isEmpty(loginToken)) {
        next("/login");
      }   else {
        next();
      }
    } else {
        let loginToken =  QyTool.getLoginToken();
        if (QyTool.isNotEmpty(loginToken) && (to.path === "/login") ) {
            next(from);
        } else {
            next();
        }
 
    }
  });


export default router;