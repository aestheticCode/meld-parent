--liquibase formatted sql

--changeset patrick:1

UPDATE uc_permission SET method = 'GET' WHERE path = 'social/people/find' and method = 'POST'