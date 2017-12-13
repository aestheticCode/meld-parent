
    alter table public.cn_post 
       add column photo_id uuid;

    alter table cn_post 
       add constraint FK1skc7nlqmc1dx12gw3f6m8801 
       foreign key (photo_id) 
       references me_photo;
