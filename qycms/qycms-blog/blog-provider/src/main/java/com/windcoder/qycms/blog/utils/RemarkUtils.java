package com.windcoder.qycms.blog.utils;

import com.overzealous.remark.Options;
import com.overzealous.remark.Remark;

public class RemarkUtils {
    private static final Remark remark = new Remark(Options.github());
    public static String htmlToMarkdown(String htmlStr) {
        return remark.convertFragment(htmlStr);
    }

}
