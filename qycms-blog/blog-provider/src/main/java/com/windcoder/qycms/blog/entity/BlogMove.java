package com.windcoder.qycms.blog.entity;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class BlogMove implements Serializable {
    private static final long serialVersionUID = 1L;
//    @NotBlank(message = "Mysql用户名不能为空")
    private String username;

//    @NotBlank(message = "Mysql密码不能为空")
    private String password;

//    @NotNull(message = "端口不能为空")
    private Integer port;

//    @NotBlank(message = "数据库不能为空")
    private String database;

//    @NotBlank
    private String ip;

//    @NotBlank
    private String blogType;
}
