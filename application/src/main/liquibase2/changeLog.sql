--liquibase formatted sql

--changeset patrick:1
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('d4267e61-cae0-4033-85c1-37974a68e409', 'DELETE', 'Channel Meld Post Delete', 'channel/meld/{id}');

--changeset patrick:2
insert into uc_role_uc_permission (Role_id, permissions_id) values ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', 'd4267e61-cae0-4033-85c1-37974a68e409');
insert into uc_role_uc_permission (Role_id, permissions_id) values ('b6498b48-ce14-418e-947a-d06dfe87e73d', 'd4267e61-cae0-4033-85c1-37974a68e409');
