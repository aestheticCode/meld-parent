--liquibase formatted sql

--changeset patrick:1

UPDATE uc_permission SET method = 'GET' WHERE path = 'social/people/find' and method = 'POST'


--changeset patrick:2

    alter table public.so_address
       add column places_id uuid;

    alter table public.so_company
       add column history_id uuid;

    alter table so_address
       add constraint FKqhxjywexgn69nnpsya43f62rn
       foreign key (places_id)
       references so_places;

    alter table so_company
       add constraint FKuuji6fx2fcsixjfvucw947od
       foreign key (history_id)
       references so_workHistory;

--changeset patrick:3

drop TABLE so_workhistory_so_company;
drop table so_education_so_school;

--changeset patrick:4

drop TABLE so_places_so_address

--changeset patrick:5

    alter table public.so_chat
       add column contact_id uuid;

    alter table public.so_chat
       add column chats_ORDER int4;

    alter table public.so_phone
       add column contact_id uuid;

    alter table public.so_phone
       add column phones_ORDER int4;

    alter table so_chat
       add constraint FKnld7x3s1ucixf6tl0pox2wi5n
       foreign key (contact_id)
       references so_personalContact;

    alter table so_phone
       add constraint FKc6pyji8gt4p9e8ibc1023p087
       foreign key (contact_id)
       references so_personalContact;


drop TABLE so_personalcontact_so_address;
drop TABLE so_personalcontact_so_chat;
drop table so_personalcontact_so_phone;

--changeset patrick:6

INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('d42fdfcb-5c31-49e8-a1e8-b1f27e519a13', 'GET', 'Social Addresses Read', 'social/user/current/places/addresses', now(), now(), 0);

delete from uc_role_uc_permission WHERE permissions_id = 'b4991f35-0ab7-400c-8f5d-f398e0c82c83';
delete from uc_permission WHERE id = 'b4991f35-0ab7-400c-8f5d-f398e0c82c83';

--changeset patrick:7

INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('60842301-c1b1-4792-8aef-443920e89b5c', 'GET', 'Social Companies List', 'social/user/current/work/history/companies', now(), now(), 0);

--changeset patrick:8

update uc_permission set method = 'GET' where path = 'social/people/categories' and method = 'POST';
update uc_permission set method = 'GET' where path = 'usercontrol/group/multiselect' and method = 'POST';
update uc_permission set method = 'GET' where path = 'usercontrol/group/table' and method = 'POST';
update uc_permission set method = 'GET' where path = 'channel/meld/posts' and method = 'POST';
update uc_permission set method = 'GET' where path = 'usercontrol/permission/table' and method = 'POST';
update uc_permission set method = 'GET' where path = 'media/photos/grid' and method = 'POST';
update uc_permission set method = 'GET' where path = 'usercontrol/role/multiselect' and method = 'POST';
update uc_permission set method = 'GET' where path = 'usercontrol/role/table' and method = 'POST';
update uc_permission set method = 'GET' where path = 'social/education/find' and method = 'POST';
update uc_permission set method = 'GET' where path = 'usercontrol/user/table' and method = 'POST';


--changeset patrick:9

    create table so_education_so_category (
       Education_id uuid not null,
        categories_id uuid not null,
        primary key (Education_id, categories_id)
    );

    create table so_personalContact_so_category (
       PersonalContact_id uuid not null,
        categories_id uuid not null,
        primary key (PersonalContact_id, categories_id)
    );

    alter table public.so_places
       add column created timestamp;

    alter table public.so_places
       add column modified timestamp;

    alter table public.so_places
       add column version int4 not null DEFAULT 0;

    create table so_places_so_category (
       Places_id uuid not null,
        categories_id uuid not null,
        primary key (Places_id, categories_id)
    );

    create table so_workHistory_so_category (
       WorkHistory_id uuid not null,
        categories_id uuid not null,
        primary key (WorkHistory_id, categories_id)
    );

    alter table so_education_so_category
       drop constraint if exists UK_hf4qew4r5yjwr0epl2qil84yh;

    alter table so_education_so_category
       add constraint UK_hf4qew4r5yjwr0epl2qil84yh unique (categories_id);

    alter table so_personalContact_so_category
       drop constraint if exists UK_hini5mcdhmkgdhdc2d9jpc2bl;

    alter table so_personalContact_so_category
       add constraint UK_hini5mcdhmkgdhdc2d9jpc2bl unique (categories_id);

    alter table so_places_so_category
       drop constraint if exists UK_nfo0q8lpc330fcameh7sokghh;

    alter table so_places_so_category
       add constraint UK_nfo0q8lpc330fcameh7sokghh unique (categories_id);

    alter table so_workHistory_so_category
       drop constraint if exists UK_67odgjfr8oq3shkpy247fo2xf;

    alter table so_workHistory_so_category
       add constraint UK_67odgjfr8oq3shkpy247fo2xf unique (categories_id);

    alter table so_education_so_category
       add constraint FKc663ghvitx8v6x7h55s4un3t1
       foreign key (categories_id)
       references so_category;

    alter table so_education_so_category
       add constraint FKn96npow2nty8u2fh4u0g94e06
       foreign key (Education_id)
       references so_education;

    alter table so_personalContact_so_category
       add constraint FKm090aouavfpqcrt02okka8pwh
       foreign key (categories_id)
       references so_category;

    alter table so_personalContact_so_category
       add constraint FK56ys2mheam0bc2nk8moncfftk
       foreign key (PersonalContact_id)
       references so_personalContact;

    alter table so_places_so_category
       add constraint FK5mpud8by51d3qxmfhhi42a8kn
       foreign key (categories_id)
       references so_category;

    alter table so_places_so_category
       add constraint FK1tx0eaac18b6llou2gt85y32j
       foreign key (Places_id)
       references so_places;

    alter table so_workHistory_so_category
       add constraint FKkxpgu568yfydpd7a038hywid7
       foreign key (categories_id)
       references so_category;

    alter table so_workHistory_so_category
       add constraint FKooyccy508ax2nos038dijnclc
       foreign key (WorkHistory_id)
       references so_workHistory;



--changeset patrick:10


    create table so_user_profile (
       id uuid not null,
        created timestamp,
        modified timestamp,
        version int4 not null,
        user_id uuid,
        primary key (id)
    );

    create table so_user_profile_so_category (
       UserProfile_id uuid not null,
        categories_id uuid not null,
        primary key (UserProfile_id, categories_id)
    );

    alter table so_user_profile_so_category
       drop constraint if exists UK_5q8hwxmtgv9tjeplo7bgtp9w2;

    alter table so_user_profile_so_category
       add constraint UK_5q8hwxmtgv9tjeplo7bgtp9w2 unique (categories_id);

    alter table so_user_profile
       add constraint FKdlgxa862ghisc16wva68kka41
       foreign key (user_id)
       references uc_identity;

    alter table so_user_profile_so_category
       add constraint FKsx83ln1ibo406qeua7xf2hkss
       foreign key (categories_id)
       references so_category;

    alter table so_user_profile_so_category
       add constraint FKphr2c8bmmp9261x42751qqaa8
       foreign key (UserProfile_id)
       references so_user_profile;

--changeset patrick:11

INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('1702d377-f509-483f-bff6-d7df90a5e713', 'GET', 'Social', 'social/communities', now(), now(), 0);


