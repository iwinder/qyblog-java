package com.windcoder.qycms.core.basis.comment.service;

import com.windcoder.qycms.core.basis.comment.entity.CommentAgent;
import com.windcoder.qycms.core.basis.comment.repository.jpa.CommentAgentRepository;
import com.windcoder.qycms.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class CommentAgentService extends BaseService<CommentAgent, Long, CommentAgentRepository> {
}
