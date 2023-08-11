CREATE TABLE post (
    post_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    writer_id BIGINT(20) NOT NULL,
    PRIMARY KEY (post_id),
    FOREIGN KEY (writer_id) REFERENCES member (member_id)
);
