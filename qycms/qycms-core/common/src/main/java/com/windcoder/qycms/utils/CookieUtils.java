package com.windcoder.qycms.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtils {
    public static final Integer DEFAULTMAXAGE = 7 * 24 * 60 * 60;

    public static Cookie setCookie(HttpServletResponse response, String cookieKey, String cookieValue, Integer maxAge){
        Cookie loginCookie = new Cookie(cookieKey,cookieValue);
        // loginCookie.setDomain("192.168.0.108");
         loginCookie.setPath("/");
        if( maxAge != null){
            loginCookie.setMaxAge(maxAge);
        }
        response.addCookie(loginCookie);
        return loginCookie;
    }

    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    public static  Cookie getCookieByName(HttpServletRequest request, String name){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie;
        }else{
            return null;
        }
    }



    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
