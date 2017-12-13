--liquibase formatted sql

    create table me_photo (
       id uuid not null,
        fileName varchar(255),
        lastModified timestamp,
        user_id uuid,
        primary key (id)
    );

    alter table me_photo
       add constraint FK9w8porh1c99j9egi9saruq7x5
       foreign key (user_id)
       references uc_identity;


    alter table public.cn_post
       add column modified timestamp default CURRENT_TIMESTAMP;

    alter table public.me_photo
       add column created timestamp default CURRENT_TIMESTAMP;

    alter table public.me_photo
       add column modified timestamp default CURRENT_TIMESTAMP;

    alter table public.me_photo
       add column version int4 not null;

    alter table public.so_education
       add column created timestamp default CURRENT_TIMESTAMP;

    alter table public.so_education
       add column modified timestamp default CURRENT_TIMESTAMP;

    alter table public.so_personalcontact
       add column created timestamp default CURRENT_TIMESTAMP;

    alter table public.so_personalcontact
       add column modified timestamp default CURRENT_TIMESTAMP;

    alter table public.so_workhistory
       add column created timestamp default CURRENT_TIMESTAMP;

    alter table public.so_workhistory
       add column modified timestamp default CURRENT_TIMESTAMP;

    alter table public.uc_identity
       add column created timestamp default CURRENT_TIMESTAMP;

    alter table public.uc_identity
       add column modified timestamp default CURRENT_TIMESTAMP;

    alter table public.uc_role
       add column created timestamp default CURRENT_TIMESTAMP;

    alter table public.uc_role
       add column modified timestamp default CURRENT_TIMESTAMP;

    alter table public.uc_permission
       add column created timestamp DEFAULT CURRENT_TIMESTAMP;

    alter table public.uc_permission
       add column modified timestamp DEFAULT CURRENT_TIMESTAMP;

    alter table public.uc_permission
       add column version int4 not null DEFAULT 0;

