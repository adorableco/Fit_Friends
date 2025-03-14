package com.example.postservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;


class PostTest {
    @Test
    @DisplayName("post 제목을 성공적으로 업데이트한다.")
    void update() {
        Post post = getPost();
        String newTitle = "newTitle";

        post.update(
            null,
            newTitle,
            null,
                null
        );

        Assertions.assertThat(post.getTitle()).isEqualTo(newTitle);
        Assertions.assertThat(post.getContent()).isNotNull();
    }


    private Post getPost() {
        return new Post(
                614L,
                UUID.randomUUID(),
                new Tag('F', "beginner", "teens"),
                844L,
                "Tellus neque molestie lorem",
                "Vitae ridiculus justo quis dapibus.",
                "Maryia Bi"
        );
    }
}
