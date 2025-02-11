package com.slasify.repository;

import com.slasify.entity.Comment;
import com.slasify.entity.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Transactional
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MessageRepository messageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testFindByMessageId() {
        Message message = new Message();
        message.setContent("Test Message");
        messageRepository.save(message);

        Comment comment1 = new Comment();
        comment1.setContent("First comment");
        comment1.setUsername("user1");
        comment1.setPostingTime(LocalDateTime.now());
        comment1.setMessage(message);
        commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setContent("Second comment");
        comment2.setUsername("user2");
        comment2.setPostingTime(LocalDateTime.now());
        comment2.setMessage(message);
        commentRepository.save(comment2);

        entityManager.flush();
        entityManager.clear();

        List<Comment> comments = commentRepository.findByMessageId(message.getId());

        assertThat(comments).hasSize(2);
        assertThat(comments).extracting(Comment::getContent)
                .containsExactlyInAnyOrder("First comment", "Second comment");
    }
}
