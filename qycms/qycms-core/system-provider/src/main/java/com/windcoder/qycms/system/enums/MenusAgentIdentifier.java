package com.windcoder.qycms.system.enums;

public enum MenusAgentIdentifier {
    LOCAL(1, "本地"),
    QINIU(2, "七牛云"),
    ALIOSS(3,"阿里OSS");
    private Integer code;

    private String desc;

    MenusAgentIdentifier(){}
    MenusAgentIdentifier(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }



    public String getDesc() {
        return desc;
    }
}
