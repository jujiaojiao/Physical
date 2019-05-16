package com.physical.app.common.utils;


public class WebViewUtil {

    public static String fomatContentForMobile(String content) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<!DOCTYPE html>");
        buffer.append("<html>");
        buffer.append("<head>");
        buffer.append("<meta charset=\"utf-8\">");
        buffer.append("<meta id=\"viewport\" name=\"viewport\" content=\"width=device-width,height=device-height,user-scalable=no\" />");
        buffer.append("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />");
        buffer.append("<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\" />");
        buffer.append("<meta name=\"black\" name=\"apple-mobile-web-app-status-bar-style\" />");
        buffer.append("<style>img{max-width:100% !important;height:auto;}</style>");
        buffer.append("<style>p{max-width:100% !important;height:auto;}</style>");
        buffer.append("<style>table{max-width:100% !important;width: auto !important;}</style>");
        buffer.append("<title>webview</title>");
//		buffer.append("<base href=\"" + URLs.IMAGE_URL + "\" />");
        buffer.append("</head>");
        buffer.append("<body>");
        buffer.append("<div style=\"font-size:1em;color:#333333; line-height:1.5em; padding:0 0 10px 0\">");
        buffer.append(content);
        buffer.append("</div>");
        buffer.append("</body>");
        buffer.append("</html>");
        LogUtil.i("Content", buffer.toString());
        return buffer.toString();
    }


}
