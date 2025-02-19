CREATE TABLE users
(
    user_id         BINARY(16)       NOT NULL,
    name            VARCHAR(255)     NULL,
    email           VARCHAR(255)     NULL,
    picture         VARCHAR(255)     NOT NULL,
    gender          CHAR             NOT NULL,
    age             VARCHAR(255)     NULL,
    level           VARCHAR(255)     NULL,
    gender_visible  BIT(1) DEFAULT 1 NOT NULL,
    age_visible     BIT(1) DEFAULT 1 NOT NULL,
    winning_rate    FLOAT  DEFAULT 0 NOT NULL,
    attendance_rate FLOAT  DEFAULT 0 NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

CREATE TABLE posts
(
    post_id       BIGINT AUTO_INCREMENT NOT NULL,
    created_date  datetime              NOT NULL,
    modified_date datetime              NOT NULL,
    user_id       uuid                NULL,
    tag_id        BIGINT DEFAULT null   NULL,
    match_id      BIGINT                NULL,
    title         VARCHAR(255)          NOT NULL,
    content       VARCHAR(255)          NOT NULL,
    category      VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_posts PRIMARY KEY (post_id)
);

ALTER TABLE posts
    ADD CONSTRAINT uc_posts_match UNIQUE (match_id);

ALTER TABLE posts
    ADD CONSTRAINT uc_posts_tag UNIQUE (tag_id);

ALTER TABLE posts
    ADD CONSTRAINT FK_POSTS_ON_MATCH FOREIGN KEY (match_id) REFERENCES matches (match_id);

ALTER TABLE posts
    ADD CONSTRAINT FK_POSTS_ON_TAG FOREIGN KEY (tag_id) REFERENCES tags (tag_id);

ALTER TABLE posts
    ADD CONSTRAINT FK_POSTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (user_id);


CREATE TABLE tags
(
    tag_id BIGINT AUTO_INCREMENT NOT NULL,
    sex    CHAR                  NULL,
    level  VARCHAR(255)          NULL,
    age    VARCHAR(255)          NULL,
    CONSTRAINT pk_tags PRIMARY KEY (tag_id)
);
CREATE TABLE participations
(
    participation_id BIGINT AUTO_INCREMENT NOT NULL,
    user_id          uuid                NULL,
    match_id         BIGINT                NULL,
    status           VARCHAR(255)          NULL,
    attendance       BIT(1) DEFAULT 0      NOT NULL,
    iswin            BIT(1) DEFAULT 0      NOT NULL,
    CONSTRAINT pk_participations PRIMARY KEY (participation_id)
);

ALTER TABLE participations
    ADD CONSTRAINT FK_PARTICIPATIONS_ON_MATCH FOREIGN KEY (match_id) REFERENCES matches (match_id);

ALTER TABLE participations
    ADD CONSTRAINT FK_PARTICIPATIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (user_id);
CREATE TABLE matches
(
    match_id         BIGINT AUTO_INCREMENT NOT NULL,
    user_id          uuid                NULL,
    category         VARCHAR(255)          NOT NULL,
    current_head_cnt INT    DEFAULT 0      NOT NULL,
    head_cnt         INT                   NOT NULL,
    place            VARCHAR(255)          NOT NULL,
    tag_id           BIGINT DEFAULT null   NULL,
    start_time       datetime              NOT NULL,
    end_time         datetime              NOT NULL,
    attendance_count INT    DEFAULT 0      NOT NULL,
    CONSTRAINT pk_matches PRIMARY KEY (match_id)
);

ALTER TABLE matches
    ADD CONSTRAINT uc_matches_tag UNIQUE (tag_id);

ALTER TABLE matches
    ADD CONSTRAINT FK_MATCHES_ON_TAG FOREIGN KEY (tag_id) REFERENCES tags (tag_id);

ALTER TABLE matches
    ADD CONSTRAINT FK_MATCHES_ON_USER FOREIGN KEY (user_id) REFERENCES users (user_id);
