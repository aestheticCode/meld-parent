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


--changeset patrick:5
    alter table public.so_school
       add column place_address varchar(255);

    alter table public.so_school
       add column place_id varchar(255);

    alter table public.so_school
       add column place_lat float8;

    alter table public.so_school
       add column place_lng float8;

    alter table public.so_school
       add column place_name varchar(255);


--changeset patrick:6
    alter table public.so_school
       add column place_country varchar(255);

    alter table public.so_school
       add column place_state varchar(255);

    alter table public.so_school
       add column place_street varchar(255);

    alter table public.so_school
       add column place_street_number varchar(255);

    alter table public.so_school
       add column place_zipCode varchar(255);

    ALTER TABLE so_school
      drop COLUMN place_address;



--changeset patrick:7
      alter table public.so_address
       add column place_country varchar(255);

    alter table public.so_address
       add column place_id varchar(255);

    alter table public.so_address
       add column place_lat float8;

    alter table public.so_address
       add column place_lng float8;

    alter table public.so_address
       add column place_name varchar(255);

    alter table public.so_address
       add column place_state varchar(255);

    alter table public.so_address
       add column place_street varchar(255);

    alter table public.so_address
       add column place_street_number varchar(255);

    alter table public.so_address
       add column place_zipCode varchar(255);

    ALTER  TABLE so_address
      DROP COLUMN city;

    ALTER  TABLE so_address
      DROP COLUMN country;

    ALTER  TABLE so_address
      DROP COLUMN state;

    ALTER  TABLE so_address
      DROP COLUMN street;


--changeset patrick:8

    alter table public.so_company
       add column place_country varchar(255);

    alter table public.so_company
       add column place_id varchar(255);

    alter table public.so_company
       add column place_lat float8;

    alter table public.so_company
       add column place_lng float8;

    alter table public.so_company
       add column place_name varchar(255);

    alter table public.so_company
       add column place_state varchar(255);

    alter table public.so_company
       add column place_street varchar(255);

    alter table public.so_company
       add column place_street_number varchar(255);

    alter table public.so_company
       add column place_zipCode varchar(255);

    ALTER TABLE so_company
      drop COLUMN name;

--changeset patrick:9
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('fba277b2-624b-4d60-af89-eda6074dc717', 'GET', 'Photos Photo Read', 'media/photo/{id}/{fileName}');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('348141ce-83ea-4a58-b790-2e23aea389bb', 'GET', 'Photos Thumbnail Read', 'media/thumbnail/{id}/{fileName}');


--changeset patrick:10

insert into uc_role_uc_permission (Role_id, permissions_id) values ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', 'fba277b2-624b-4d60-af89-eda6074dc717');
insert into uc_role_uc_permission (Role_id, permissions_id) values ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '348141ce-83ea-4a58-b790-2e23aea389bb');
insert into uc_role_uc_permission (Role_id, permissions_id) values ('b6498b48-ce14-418e-947a-d06dfe87e73d', 'fba277b2-624b-4d60-af89-eda6074dc717');
insert into uc_role_uc_permission (Role_id, permissions_id) values ('b6498b48-ce14-418e-947a-d06dfe87e73d', '348141ce-83ea-4a58-b790-2e23aea389bb');

--changeset patrick:11

update uc_permission set path = 'social/user/{id}/profile/background' where path = 'social/user/{id}/profile' and method = 'GET';
update uc_permission set path = 'social/user/current/profile/background' where path = 'social/user/current/profile' and method = 'GET';

delete from uc_role_uc_permission WHERE permissions_id = '47a3a285-7245-40ed-a881-f4b2eef0d2ee';
delete from uc_permission WHERE id = '47a3a285-7245-40ed-a881-f4b2eef0d2ee';
delete from uc_role_uc_permission WHERE permissions_id = '7740c489-a5ee-4633-b997-d94097c74d63';
delete from uc_permission WHERE id = '7740c489-a5ee-4633-b997-d94097c74d63';
delete from uc_role_uc_permission WHERE permissions_id = 'c2da1ff2-d1fa-4802-b4ac-0cfbd8966f54';
delete from uc_permission WHERE id = 'c2da1ff2-d1fa-4802-b4ac-0cfbd8966f54';
delete from uc_role_uc_permission WHERE permissions_id = '876858da-4a1a-415a-b6f2-cc1cd4d1353a';
delete from uc_permission WHERE id = '876858da-4a1a-415a-b6f2-cc1cd4d1353a';
delete from uc_role_uc_permission WHERE permissions_id = '0cdb4a63-3208-4652-8b58-56a0ef0f5aaf';
delete from uc_permission WHERE id = '0cdb4a63-3208-4652-8b58-56a0ef0f5aaf';
delete from uc_role_uc_permission WHERE permissions_id = '54905c66-799a-4e92-b9c6-52bf42866247';
delete from uc_permission WHERE id = '54905c66-799a-4e92-b9c6-52bf42866247';
delete from uc_role_uc_permission WHERE permissions_id = 'c7353232-5b55-460e-9f27-69fc8672b57d';
delete from uc_permission WHERE id = 'c7353232-5b55-460e-9f27-69fc8672b57d';
delete from uc_role_uc_permission WHERE permissions_id = 'c3050901-263b-472a-9a7d-6bc9dd91a260';
delete from uc_permission WHERE id = 'c3050901-263b-472a-9a7d-6bc9dd91a260';
delete from uc_role_uc_permission WHERE permissions_id = '1ac08f9d-d243-4a69-8c9d-a8fb6150ee0d';
delete from uc_permission WHERE id = '1ac08f9d-d243-4a69-8c9d-a8fb6150ee0d';
delete from uc_role_uc_permission WHERE permissions_id = 'ee1009ad-017c-4f9a-aa76-48519524f8a2';
delete from uc_permission WHERE id = 'ee1009ad-017c-4f9a-aa76-48519524f8a2';


--changeset patrick:12
update uc_permission set path = 'social/user/{id}/profile' where path = 'social/user/{id}/profile/background' and method = 'GET';
update uc_permission set path = 'social/user/current/profile' where path = 'social/user/current/profile/background' and method = 'GET';

delete from uc_role_uc_permission WHERE permissions_id = '62b967d7-2291-4961-9b24-abf546d3c62c';
delete from uc_permission WHERE id = '62b967d7-2291-4961-9b24-abf546d3c62c';
delete from uc_role_uc_permission WHERE permissions_id = '951e0289-7994-4f7b-a953-9dadba15703f';
delete from uc_permission WHERE id = '951e0289-7994-4f7b-a953-9dadba15703f';

--changeset patrick:13
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('9fe2549e-18b5-4e75-aac0-346569d02787', 'GET', 'Social Profile Background Read', 'social/user/current/profile');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('df5bc5a4-6c7c-413b-9a42-69be4d19b835', 'GET', 'Social Profile Background Read', 'social/user/{id}/profile');

--changeset patrick:14
insert into uc_role_uc_permission (Role_id, permissions_id) values ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '9fe2549e-18b5-4e75-aac0-346569d02787');
insert into uc_role_uc_permission (Role_id, permissions_id) values ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', 'df5bc5a4-6c7c-413b-9a42-69be4d19b835');
insert into uc_role_uc_permission (Role_id, permissions_id) values ('b6498b48-ce14-418e-947a-d06dfe87e73d', '9fe2549e-18b5-4e75-aac0-346569d02787');
insert into uc_role_uc_permission (Role_id, permissions_id) values ('b6498b48-ce14-418e-947a-d06dfe87e73d', 'df5bc5a4-6c7c-413b-9a42-69be4d19b835');

--changeset patrick:15
update uc_permission set path = 'social/user/current/contact/create' where path = 'social/user/{id}/contact/create';

--changeset patrick:16
    alter table public.cn_post
       add column link varchar(255);

--changeset patrick:17


    alter table public.so_school
       add column yearEndSemester varchar(255);

    alter table public.so_school
       add column yearEndYear int4;

    alter table public.so_school
       add column yearStartSemester varchar(255);

    alter table public.so_school
       add column yearStartYear int4;

    alter table public.so_school
       add column visitEndSemester varchar(255);

    alter table public.so_school
       add column visitEndYear int4;

    alter table public.so_school
       add column visitStartSemester varchar(255);

    alter table public.so_school
       add column visitStartYear int4;

    alter table so_school
      drop COLUMN endsemester;

    alter table so_school
      drop COLUMN endyear;

    alter table so_school
      drop COLUMN startsemester;

    alter table so_school
      drop COLUMN startyear;

INSERT INTO public.uc_permission (id, method, name, path) VALUES ('1bca4d8c-ef07-4099-95ea-69da41c58421', 'GET', 'Social Education Name', 'social/education/name');

--changeset patrick:18

insert into uc_role_uc_permission (Role_id, permissions_id) values ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '1bca4d8c-ef07-4099-95ea-69da41c58421');
insert into uc_role_uc_permission (Role_id, permissions_id) values ('b6498b48-ce14-418e-947a-d06dfe87e73d', '1bca4d8c-ef07-4099-95ea-69da41c58421');


