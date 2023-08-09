package com.wanted.preonboarding.post.domain;

import static lombok.AccessLevel.PROTECTED;

import com.wanted.preonboarding.post.exception.InvalidPostException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Post {

    public static final int MAX_TITLE_LENGTH = 255;
    public static final int MAX_CONTENT_LENGTH = 65535;
    public static final String TOO_LONG_TITLE_MESSAGE = "게시글 제목은 " + MAX_TITLE_LENGTH + "자 이하여야 합니다.";
    public static final String TOO_LONG_CONTENT_MESSAGE = "게시글 본문은 " + MAX_CONTENT_LENGTH + "자 이하여야 합니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;
    private Long writerId;

    private Post(final Long id, final String title, final String content, final Long writerId) {
        validateTitle(title);
        validateContent(content);
        this.id = id;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
    }

    public static Post ofNew(final String title, final String content, final Long writerId) {
        return new Post(null, title, content, writerId);
    }

    private void validateTitle(final String title) {
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new InvalidPostException(TOO_LONG_TITLE_MESSAGE);
        }
    }

    private void validateContent(final String content) {
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new InvalidPostException(TOO_LONG_CONTENT_MESSAGE);
        }
    }
}
