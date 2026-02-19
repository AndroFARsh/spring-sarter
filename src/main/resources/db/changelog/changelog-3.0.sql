--liquibase formatted sql

--changeset farshonok:1
ALTER TABLE users
    ADD COLUMN image VARCHAR(64);

--changeset farshonok:2
ALTER TABLE users_aud
    ADD COLUMN image VARCHAR(64);
