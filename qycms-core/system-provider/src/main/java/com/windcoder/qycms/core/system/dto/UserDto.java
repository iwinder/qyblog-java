package com.windcoder.qycms.core.system.dto;

public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private String avatar;
    private String nickname;
    private boolean isDisable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public boolean getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(boolean isDisable) {
        this.isDisable = isDisable;
    }
}
