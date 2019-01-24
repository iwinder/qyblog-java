package com.windcoder.qycms.core.basis.comment.dto;



public class CommentAdminDto extends CommentDto {
    private String email;
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
}
