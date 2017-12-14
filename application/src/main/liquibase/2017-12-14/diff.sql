
    create table so_profile (
       id uuid not null,
        created timestamp,
        modified timestamp,
        version int4 not null,
        photo_id uuid,
        user_id uuid,
        primary key (id)
    );

    alter table so_profile
       add constraint FKndle21ej6nl5iv2mhe1q3ntkt
       foreign key (photo_id)
       references me_photo;

    alter table so_profile
           add constraint FKac6gxw2uyi8bw50477cpsl62p
           foreign key (user_id)
           references uc_identity;
