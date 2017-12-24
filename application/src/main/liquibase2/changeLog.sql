--liquibase formatted sql

--changeset patrick:2
insert into uc_role_uc_permission (Role_id, permissions_id) values ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', 'd4267e61-cae0-4033-85c1-37974a68e409');
insert into uc_role_uc_permission (Role_id, permissions_id) values ('b6498b48-ce14-418e-947a-d06dfe87e73d', 'd4267e61-cae0-4033-85c1-37974a68e409');

--changeset patrick:3
update uc_permission set path = 'generic/google/place/autocomplete' where path = 'generic/google/place';
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('fbff4aa3-9368-4a5f-89c7-6af9d8cd6229', 'GET', 'Generic Google Places Details', 'generic/google/place/{id}/details');

--changeset patrick:4
insert into uc_role_uc_permission (Role_id, permissions_id) values ('b6498b48-ce14-418e-947a-d06dfe87e73d', 'fbff4aa3-9368-4a5f-89c7-6af9d8cd6229');
insert into uc_role_uc_permission (Role_id, permissions_id) values ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', 'fbff4aa3-9368-4a5f-89c7-6af9d8cd6229');
