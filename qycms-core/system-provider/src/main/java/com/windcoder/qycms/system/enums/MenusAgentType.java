package com.windcoder.qycms.system.enums;

public enum  MenusAgentType {
    SYSTEM("S", "系统"),
    USER("U","用户自定义");
    private String code;

    private String desc;

    MenusAgentType(){}
    MenusAgentType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
