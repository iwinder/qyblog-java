package com.windcoder.qycms.utils;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.*;

public class HtmlToText extends HTMLEditorKit.ParserCallback  {
    private static HtmlToText html2Text = new HtmlToText();

    StringBuffer stringBuffer;

    private HtmlToText() {
    }

    public void parse(String str) {
        try( InputStream iin = new ByteArrayInputStream(str.getBytes());
             Reader in = new InputStreamReader(iin);) {
            stringBuffer = new StringBuffer();
            ParserDelegator delegator = new ParserDelegator();
            // the third parameter is TRUE to ignore charset directive
            delegator.parse(in, this, Boolean.TRUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleText(char[] text, int pos) {
        stringBuffer.append(text);
    }

    public String getText() {
        return stringBuffer.toString();
    }

    /**
     *
     * @param str
     * @return
     */
    public static String getContent(String str) {
        html2Text.parse(str);
        return html2Text.getText();
    }
}
