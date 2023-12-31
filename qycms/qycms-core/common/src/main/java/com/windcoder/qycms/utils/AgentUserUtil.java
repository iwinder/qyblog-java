package com.windcoder.qycms.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class AgentUserUtil {

    public static String getUserAgent() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return getUserAgent(request);
    }
    /**
     * 根据http获取userAgent信息
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request) {
        String userAgent=request.getHeader("User-Agent");
        return userAgent;
    }

    /**
     * 根据request获取userAgent，然后解析出osVersion
     * @param request
     * @return
     */
    public static String getOsVersion(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getOsVersion(userAgent);
    }

    /**
     * 根据userAgent解析出osVersion
     * @param userAgent
     * @return
     */
    public static String getOsVersion(String userAgent) {
        String osVersion = "";
        if(StringUtils.isBlank(userAgent))
            return osVersion;
        String[] strArr = null;
        if(userAgent.indexOf("(")>=0 &&  userAgent.indexOf(")")>=0) {
            strArr = userAgent.substring(userAgent.indexOf("(")+1,
                    userAgent.indexOf(")")).split(";");
        }
        if(null == strArr || strArr.length == 0)
            return osVersion;

        osVersion = strArr[1];
        log.info("osVersion is:{}", osVersion);
        return osVersion;
    }

    /**
     * 获取操作系统对象
     * @param
     * @return
     */
    private static OperatingSystem getOperatingSystem(String userAgent) {
        UserAgent agent = UserAgent.parseUserAgentString(userAgent);
        OperatingSystem operatingSystem = agent.getOperatingSystem();
        return operatingSystem;
    }



    /**
     * 获取os：Windows/ios/Android
     * @param request
     * @return
     */
    public static String getOs(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getOs(userAgent);
    }

    /**
     * 获取os：Windows/ios/Android
     * @param
     * @return
     */
    public static String getOs(String userAgent) {
        OperatingSystem operatingSystem =  getOperatingSystem(userAgent);
        String os = operatingSystem.getGroup().getName();
        log.info("os is:{}", os);
        return os;
    }


    /**
     * 获取deviceType
     * @param request
     * @return
     */
    public static String getDevicetype(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getDevicetype(userAgent);
    }

    /**
     * 获取deviceType
     * @param
     * @return
     */
    public static String getDevicetype(String userAgent) {
        OperatingSystem operatingSystem =  getOperatingSystem(userAgent);
        String deviceType = operatingSystem.getDeviceType().toString();
        log.info("deviceType is:{}", deviceType);
        return deviceType;
    }

    /**
     * 获取操作系统的名字
     * @param request
     * @return
     */
    public static String getOsName(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getOsName(userAgent);
    }

    /**
     * 获取操作系统的名字
     * @param
     * @return
     */
    public static String getOsName(String userAgent) {
        OperatingSystem operatingSystem =  getOperatingSystem(userAgent);
        String osName = operatingSystem.getName();
        log.info("osName is:{}", osName);
        return osName;
    }


    /**
     * 获取device的生产厂家
     * @param request
     */
    public static String getDeviceManufacturer(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getDeviceManufacturer(userAgent);
    }

    /**
     * 获取device的生产厂家
     * @param
     */
    public static String getDeviceManufacturer(String userAgent) {
        OperatingSystem operatingSystem =  getOperatingSystem(userAgent);
        String deviceManufacturer = operatingSystem.getManufacturer().toString();
        log.info("deviceManufacturer is:{}", deviceManufacturer);
        return deviceManufacturer;
    }

    /**
     * 获取浏览器对象
     * @param
     * @return
     */
    public static Browser getBrowser(String agent) {
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);
        Browser browser = userAgent.getBrowser();
        return browser;
    }


    /**
     * 获取browser name
     * @param request
     * @return
     */
    public static String getBorderName(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getBorderName(userAgent);
    }

    /**
     * 获取browser name
     * @param
     * @return
     */
    public static String getBorderName(String userAgent) {
        Browser browser =  getBrowser(userAgent);
        String borderName = browser.getName();
        log.info("borderName is:{}", borderName);
        return borderName;
    }


    /**
     * 获取浏览器的类型
     * @param request
     * @return
     */
    public static String getBorderType(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getBorderType(userAgent);
    }

    /**
     * 获取浏览器的类型
     * @param
     * @return
     */
    public static String getBorderType(String userAgent) {
        Browser browser =  getBrowser(userAgent);
        String borderType = browser.getBrowserType().getName();
        log.info("borderType is:{}", borderType);
        return borderType;
    }

    /**
     * 获取浏览器组： CHROME、IE
     * @param request
     * @return
     */
    public static String getBorderGroup(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getBorderGroup(userAgent);
    }

    /**
     * 获取浏览器组： CHROME、IE
     * @param
     * @return
     */
    public static String getBorderGroup(String userAgent) {
        Browser browser =  getBrowser(userAgent);
        String browerGroup = browser.getGroup().getName();
        log.info("browerGroup is:{}", browerGroup);
        return browerGroup;
    }

    /**
     * 获取浏览器的生产厂商
     * @param request
     * @return
     */
    public static String getBrowserManufacturer(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getBrowserManufacturer(userAgent);
    }


    /**
     * 获取浏览器的生产厂商
     * @param
     * @return
     */
    public static String getBrowserManufacturer(String userAgent) {
        Browser browser =  getBrowser(userAgent);
        String browserManufacturer = browser.getManufacturer().getName();
        log.info("browserManufacturer is:{}", browserManufacturer);
        return browserManufacturer;
    }


    /**
     * 获取浏览器使用的渲染引擎
     * @param request
     * @return
     */
    public static String getBorderRenderingEngine(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getBorderRenderingEngine(userAgent);
    }

    /**
     * 获取浏览器使用的渲染引擎
     * @param
     * @return
     */
    public static String getBorderRenderingEngine(String userAgent) {
        Browser browser =  getBrowser(userAgent);
        String renderingEngine = browser.getRenderingEngine().name();
        log.info("renderingEngine is:{}", renderingEngine);
        return renderingEngine;
    }


    /**
     * 获取浏览器版本
     * @param request
     * @return
     */
    public static String getBrowserVersion(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getBrowserVersion(userAgent);
    }

    /**
     * 获取浏览器版本
     * @param
     * @return
     */
    public static String getBrowserVersion(String userAgent) {
        Browser browser =  getBrowser(userAgent);
        String borderVersion = browser.getVersion( userAgent)!=null?  browser.getVersion( userAgent).toString():null;
        return borderVersion;
    }


    public static void main(String[] args) {
//		String androidUserAgent = "Mozilla/5.0 (Linux; Android 8.0; LON-AL00 Build/HUAWEILON-AL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 MQQBrowser/6.2 TBS/044204 Mobile Safari/537.36 V1_AND_SQ_7.7.8_908_YYB_D QQ/7.7.8.3705 NetType/WIFI WebP/0.3.0 Pixel/1440";
//		String iosUserAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 12_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16A366 QQ/7.7.8.421 V1_IPH_SQ_7.7.8_1_APP_A Pixel/750 Core/UIWebView Device/Apple(iPhone 6s) NetType/WIFI QBWebViewType/1";
//        String winUserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36";
//        String winUserAgent = "Sogou web spider/4.0(+http://www.sogou.com/docs/help/webmasters.htm#07)";
        String winUserAgent = "Mozilla/5.0 zgrab/0.x";
//        String winUserAgent = "python-requests/2.20.1";
        System.out.println("测试是否包含python："+  winUserAgent.toLowerCase().contains("python"));
        System.out.println("测试是否包含zgrab："+  winUserAgent.toLowerCase().contains("zgrab"));
        System.out.println("浏览器组："+getBorderGroup(winUserAgent));
        System.out.println("浏览器名字："+getBorderName(winUserAgent));
        System.out.println("浏览器类型"+getBorderType(winUserAgent));
        System.out.println("浏览器生产商："+getBrowserManufacturer(winUserAgent));
        System.out.println("浏览器版本："+getBrowserVersion(winUserAgent));
        System.out.println("设备生产厂商:"+getDeviceManufacturer(winUserAgent));
        System.out.println("设备类型:"+getDevicetype(winUserAgent));
        System.out.println("设备操作系统："+getOs(winUserAgent));
        System.out.println("操作系统的名字："+getOsName(winUserAgent));
        System.out.println("操作系统的版本号："+getOsVersion(winUserAgent));
        System.out.println("操作系统浏览器的渲染引擎:"+getBorderRenderingEngine(winUserAgent));
    }

}
