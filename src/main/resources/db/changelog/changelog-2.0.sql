--liquibase formatted sql

--changeset farshonok:1
ALTER TABLE users
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP;

ALTER TABLE users
    ADD COLUMN IF NOT EXISTS modified_at TIMESTAMP;

ALTER TABLE users
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(32);

ALTER TABLE users
    ADD COLUMN IF NOT EXISTS modified_by VARCHAR(32)

