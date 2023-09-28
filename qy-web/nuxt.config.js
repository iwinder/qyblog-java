const webpack = require('webpack')
const LRUCache = require('lru-cache')
export default {
  debug:false,
  dev:false, 
  telemetry:false,
  server: {
    port: 4001, // default: 3000
   host: '0.0.0.0' // default: localhost
  },
  /*
  ** Nuxt rendering mode
  ** See https://nuxtjs.org/api/configuration-mode
  */
  mode: 'universal',
  /*
  ** Headers of the page
  ** See https://nuxtjs.org/api/configuration-head
  */
  head: {
    title: process.env.npm_package_name || '',
    meta: [
      { charset: 'utf-8' },  
      { "http-equiv": "X-UA-Compatible", content: "IE=edge,chrome=1" },
      { 'http-equiv': 'pragma', content: 'no-cache' },
      { 'http-equiv': 'cache-control', content: 'no-cache' },
      { 'http-equiv': 'expires', content: '0' }, 
      { name: "format-detection", content: "telephone=no" },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },  
      { hid: 'description', name: 'description', content: process.env.npm_package_description || '' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
    ],
    script: [
      { src:'/js/local-storage.js'},
      { src:'/js/session-storage.js'},
      { src:'/js/qy-tool.js'},
    ]
  },
  /*
  ** Global CSS
  */
  css: [
    // 'ant-design-vue/dist/antd.css',
    '@/assets/css/main.scss'
  ],
  /*
  ** Plugins to load before mounting the App
  ** https://nuxtjs.org/guide/plugins
  */
  plugins: [
    {src:'@/plugins/antd-ui', ssr: true},
    '@/plugins/qy-serve-tool',
    "@/plugins/qy-real-img",
    "@/plugins/qy-axios-cache",
    {src: '@/plugins/vueLazyLoad', ssr: true }, 
    { src: '@/plugins/vue-highlight', ssr: false }, 
  ],
  router: {
    extendRoutes(routes, resolve) {
      routes.push({
        name: 'custom',
        path: '*',
        component: resolve(__dirname, 'pages/404.vue')
      })
    }
  },
  /*
  ** Nuxt.js dev-modules
  */
  buildModules: [
    '@nuxt/typescript-build',
  ],
  /*
  ** Nuxt.js modules
  */
  modules: [
    '@nuxtjs/axios'
  ],
  /*
  ** Build configuration
  ** See https://nuxtjs.org/api/configuration-build/
  */
  build: {
    analyze: true,
    assetFilter: function(assetFilename) {	    		
      return assetFilename.endsWith('.js');	    	
    },
    telemetry:false,
    transpile: ['ant-design-vue'],
    babel: {
      plugins: [
        ['import',{
            libraryName: 'ant-design-vue',
            libraryDirectory: 'lib',
            style: true,
          },
          "ant-design-vue"]
      ]
    },
    module:{
    },
    loaders: {
      less: {
        lessOptions: {
          javascriptEnabled: true
        }
      }
    },
    plugins: [
      new webpack.IgnorePlugin(/^\.\/locale$/, /moment$/),
    ],
    extend(config, ctx) {
      const Timestamp = new Date().getTime();
      config.output.filename = `js/[name].${Timestamp}.js` // 每次构建打包时给文件名加上时间戳，保证版本更新时与上版本文件名不一样
      config.output.chunkFilename = `js/[name].${Timestamp}.js`
      const path = require('path');
      config.resolve.alias['@ant-design/icons/lib/dist$'] = path.resolve(__dirname, './assets/icons/antd-icon.js') // 引入需要的
    }
  }, 
  axios: {
    baseURL: 'http://localhost:8000/api/admin',
    // or other axios configs.
    // baseURL:"https://windcoder.com/api/",
    // proxy: false,
    proxy: true,
    prefix: '/api/',
    withCredentials: true,
    
  },
  render: {
    bundleRenderer: {
      cache: new LRUCache({
        max: 1000,
        maxAge: 1000 * 60 * 30
      })
    }
  },
  // proxy: {
  //   '/api': {
  //       target: 'http://localhost:8000/api', // 代理地址
  //       changeOrigin: true,
  //       pathRewrite: {
  //         '^/api': '', //将 /api 替换掉
  //       },
  //   },
  // '/qy-content':{
  //     target: 'http://localhost:8000/qy-content', // 代理地址
  //     changeOrigin: true,
  //     pathRewrite: {
  //       '^/qy-content': '', //将 /api 替换掉
  //     },
  //   },

  // },


}
