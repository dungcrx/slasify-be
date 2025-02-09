package com.slasify.repository;

import com.slasify.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMessageId(Long messageId);

    List<Comment> findByMessageIdOrderByPostingTimeAsc(Long messageId);
}
