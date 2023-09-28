export default function(ctx) {
    const { $axios, req, redirect, route } = ctx
  
    /**
     * 发送前拦截
     */
    $axios.onRequest(config => {
      if (process.client) {
        // 浏览器执行的客户端代码
      }
      if (process.server) {
        // node执行的服务端代码
        // node服务端做host穿透-重点在这里哟
        config.headers.host = req.headers.host
      }
    })
    /**
     * 返回结果拦截
     */
    $axios.onResponse(response => {
      // 可以添加处理代码
  
      // 或者不处理直接返回response
      return response
    })
  
    /**
     * 错误拦截
     */
    $axios.onError(error => {
      const code = parseInt(error.response && error.response.status)
      if (code === 404) {
        // 404错误处理
        createError({ statusCode: 404, message: err.message});
        } else  {
        createError({ statusCode: 500, message: err.message});
        }
    })
  }