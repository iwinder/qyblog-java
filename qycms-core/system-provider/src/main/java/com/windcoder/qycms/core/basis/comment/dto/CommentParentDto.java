package com.windcoder.qycms.core.basis.comment.dto;

public class CommentParentDto extends CommentDto {
    private CommentParentDto parent;

    public CommentParentDto getParent() {
        if(parent!=null){
            parent.setParent(null);
        }
        return parent;
    }

    public void setParent(CommentParentDto parent) {
        this.parent = parent;
    }
}
