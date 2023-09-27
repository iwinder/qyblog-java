export const MAIN_MENU = [
    { name: '控制面板', icon: 'dashboard', route: '/' },
    {
        name: '系统管理', icon: 'appstore', children: [
            {name: '用户管理', icon: 'user', route: '/system/user'},
            // {name: '角色管理', icon: 'paper-clip', route: '/system/role'},
            // {name: '菜单管理', icon: 'bars', route: '/system/user'},
            // {name: '授权管理', icon: 'setting', route: '/system/role'},
            // {name: '分类管理', icon: 'setting', route: '/system/category'},
        ]
    },
    {
        name: '博客管理', icon: 'file-text', children: [
            {name: '文章管理', icon: 'form', route: '/blog/article'},
            {name: '分类管理', icon: 'tag', route: '/blog/category'},
        ]
    },
    {
        name: '评论管理', icon: 'file-text', children: [
            {name: '基础设置', icon: 'form', route: '/comment/sysComment'},
            {name: '评论列表', icon: 'tag', route: '/comment/list'},
        ]
    }

];

