package com.windcoder.qycms.core.basis.comment.service;

import com.windcoder.qycms.core.basis.comment.entity.Comment;
import com.windcoder.qycms.core.basis.comment.repository.jpa.CommentRepository;
import com.windcoder.qycms.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class CommentService extends BaseService<Comment,Long,CommentRepository> {



    public Page<Comment> findTopLevelComments(Long targetId, Pageable pageable) {
        return super.findAll(getCommentsSpecification(targetId,null), pageable);
    }

    public Page<Comment> findComments(Long targetId, Long parentId, Pageable pageable) {
        return super.findAll(getCommentsSpecification(targetId,parentId), pageable);
    }

    private  Specification<Comment> getCommentsSpecification(Long targetId, Long parentId){
        Specification<Comment> specification = new Specification<Comment>(){

            @Override
            public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.equal(root.get("target").get("id"), targetId);
                if( null == parentId ){
                    predicate = cb.and(predicate,cb.isNull(root.get("parent").get("id")));
                    predicate = cb.and(predicate,cb.equal(root.get("depth"),1));
                }else {
                    predicate = cb.and(predicate, cb.equal(root.get("topParent").get("id"), parentId));
                }
                return predicate;
            }
        };
        return specification;
    }
}
