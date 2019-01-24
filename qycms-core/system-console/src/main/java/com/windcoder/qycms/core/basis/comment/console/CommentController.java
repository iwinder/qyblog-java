package com.windcoder.qycms.core.basis.comment.console;

import com.windcoder.qycms.core.basis.comment.dto.CommentAdminDto;
import com.windcoder.qycms.core.basis.comment.dto.CommentDto;
import com.windcoder.qycms.core.basis.comment.entity.Comment;
import com.windcoder.qycms.core.basis.comment.service.CommentService;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping(value = "")
    public Page<CommentAdminDto> list(Comment comment,
                                 @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Comment> comments = commentService.findAlllComments(comment, pageable);
        Type type = new TypeToken<List<CommentAdminDto>>() {}.getType();
        List<CommentAdminDto> commentsDto = ModelMapperUtils.map(comments.getContent(),type);
        return  new PageImpl<>(commentsDto,pageable,comments.getTotalElements());
    }

    @PostMapping(value = "{commentId}/updateStatus")
    public  void updateStatus(  @PathVariable("commentId") Long commentId, String status){
        commentService.updateStatus(commentId, status);
    }
}
