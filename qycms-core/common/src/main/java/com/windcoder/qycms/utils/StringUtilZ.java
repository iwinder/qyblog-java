package com.windcoder.qycms.utils;

public class StringUtilZ {

    private static String removeHtml(String html) {
        String content = "";
        content = html.replaceAll( ".*?<body.*?>(.*?)<\\/body>", "$1");
        content=content.replaceAll("</?[a-zA-Z]+[^><]*>","");
        content.replaceAll("\n","");
        return content;
    }

    public static String removeHtmlAndSubstring(String html) {
        String content = removeHtml(html);
        return content.substring(0,Math.min(100, content.length()));

    }

}
