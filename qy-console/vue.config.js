module.exports = {
    runtimeCompiler: true,
    publicPath:process.env.NODE_ENV === 'production'? '/qyAdminZ/':'/', // 设置打包文件相对路径
    devServer: {
      // open: process.platform === 'darwin',
      // host: 'localhost',
      port: 4002,
      // open: true, //配置自动启动浏览器 
 
      proxy: {
        '/api': {
            target: 'http://localhost:8000/api', // 代理地址
            changeOrigin: true,
            pathRewrite: {
              '^/api': '', //将 /api 替换掉
            },
        },
        '/content':{
          target: 'http://localhost:8000/content', // 代理地址
          changeOrigin: true,
          pathRewrite: {
            '^/content': '', //将 /api 替换掉
          },
        },
      }
     }, 
  }
  