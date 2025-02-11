drop table if exists USERS cascade;
drop table if exists MESSAGE cascade;
drop table if exists COMMENT cascade;

CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(20) UNIQUE CHECK (LENGTH(username) BETWEEN 5 AND 20),
                       email VARCHAR(255) UNIQUE CHECK (email LIKE '%@%'),
                       password_hash VARCHAR(255) NOT NULL,
                       CONSTRAINT chk_username_or_email CHECK (username IS NOT NULL OR email IS NOT NULL)
);

CREATE TABLE message (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         content TEXT NOT NULL,
                         username VARCHAR(255) NOT NULL,
                         posting_time DATETIME NOT NULL
);

CREATE TABLE comment (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         content TEXT NOT NULL,
                         username VARCHAR(255) NOT NULL,
                         posting_time DATETIME NOT NULL,
                         message_id BIGINT,
                         parent_comment_id BIGINT,
                         FOREIGN KEY (message_id) REFERENCES message(id) ON DELETE CASCADE,
                         FOREIGN KEY (parent_comment_id) REFERENCES comment(id) ON DELETE CASCADE
);
create index idx_UserName on users (username);
create index idx_email on users (email);
create index idx_UserName_msg on message (username);

/**
WITH RECURSIVE CommentHierarchy(id, content, username, posting_time, parent_comment_id, level) AS (
    SELECT
        id,
        content,
        username,
        posting_time,
        parent_comment_id,
        1 AS level
    FROM
        comment
    WHERE
        message_id = 1 AND parent_comment_id IS NULL

    UNION ALL
    SELECT
        c.id,
        c.content,
        c.username,
        c.posting_time,
        c.parent_comment_id,
        ch.level + 1 AS level
    FROM
        comment c
            JOIN
        CommentHierarchy ch ON c.parent_comment_id = ch.id
    WHERE
        c.message_id = 1
)
SELECT
    id,
    content,
    username,
    posting_time,
    parent_comment_id,
    level
FROM
    CommentHierarchy
ORDER BY
    level, posting_time;
**/