
    alter table public.so_profile
       add column backgroundPhoto_id uuid;

    alter table public.so_profile
       add column userPhoto_id uuid;

    alter table so_profile
       add constraint FKq9kplhqlvi1fyecek5x34241o
       foreign key (backgroundPhoto_id)
       references me_photo;

    alter table so_profile
       add constraint FKjajy2g88qrwydu0b8w1gqia88
       foreign key (userPhoto_id)
       references me_photo;
