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
