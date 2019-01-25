package com.windcoder.qycms.core.basis.comment.repository.jpa;

import com.windcoder.qycms.core.basis.comment.entity.Comment;
import com.windcoder.qycms.repository.SupportRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

public interface CommentRepository extends SupportRepository<Comment, Long> {

    @Transactional
    @Modifying
    @Query(value="update Comment c set c.status = :status, c.lastModifiedDate = :lastModifiedDate where c.id = :commentId")
    void updateStatus(@Param("commentId") Long commentId, @Param("status")String status,  @Param("lastModifiedDate")Date lastModifiedDate);

    @Query(value="select  count(c) from Comment c  where c.email =:email and (c.status = 'APPLIED' or c.status = 'REFUSED') ")
    Integer countByEmailAndStatusNotEnrolled(@Param("email") String email);

    Integer countByEmail(String email);
}

