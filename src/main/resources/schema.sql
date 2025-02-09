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
