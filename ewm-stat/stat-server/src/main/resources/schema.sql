CREATE TABLE IF NOT EXISTS HITS (
    ID          BIGINT generated by default as identity primary key,
    APP         VARCHAR(64) not null,
    URI         VARCHAR(200) not null,
    IP          VARCHAR(20) not null,
    TIMESTAMP   TIMESTAMP WITHOUT TIME ZONE not null
);