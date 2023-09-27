package com.windcoder.qycms.system.shiro;

import com.fasterxml.jackson.core.JsonParser;
import com.windcoder.qycms.dto.ResponseDto;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


public class ShiroLoginFilter extends FormAuthenticationFilter {
    /**
     * 在访问controller前判断是否登录，返回401，不进行重定向。
     *
     * @param request
     * @param response
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //加了一次过滤
//        if (isAjax(request)) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            HttpServletRequest httpServletRequest = (HttpServletRequest)request;
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setStatus(401);
            // 指定本次预检请求的有效期,单位为秒
            httpServletResponse.setHeader("Access-Control-Max-Age", "1800");
            // 防止乱码，适用于传输JSON数据
            httpServletResponse.setHeader("Content-Type","application/json;charset=UTF-8");
            // 跨域的header设置
            String method = httpServletRequest.getMethod();
            httpServletResponse.setHeader("Access-Control-Allow-Methods", method);
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
            httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
            ResponseDto responseDto = new ResponseDto();
            responseDto.setSuccess(false);
            responseDto.setCode("401");
            responseDto.setMessage("未授权");
            PrintWriter writer = httpServletResponse.getWriter();
                JSONObject   json = new JSONObject(responseDto);

            writer.write(json.toString());
//            return false;
//        }
//        return super.onAccessDenied(request, response);'
        return false;
    }

//    private boolean isAjax(ServletRequest request) {
//        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
//        if ("XMLHttpRequest".equalsIgnoreCase(header)) {
//            return Boolean.TRUE;
//        }
//        return Boolean.FALSE;
//    }
}
