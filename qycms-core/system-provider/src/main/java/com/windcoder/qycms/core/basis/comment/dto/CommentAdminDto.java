package com.windcoder.qycms.core.basis.comment.dto;



public class CommentAdminDto extends CommentDto {
    private String email;
    private String ip;
    private String status;

    private CommentAgentDto target;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CommentAgentDto getTarget() {
        return target;
    }

    public void setTarget(CommentAgentDto target) {
        this.target = target;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
