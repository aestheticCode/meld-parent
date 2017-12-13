
    alter table public.uc_permission 
       add column created timestamp DEFAULT CURRENT_TIMESTAMP ;

    alter table public.uc_permission 
       add column modified timestamp DEFAULT CURRENT_TIMESTAMP ;

    alter table public.uc_permission 
       add column version int4 not null;

    alter table public.uc_permission 
       add column created timestamp;

    alter table public.uc_permission 
       add column modified timestamp;

    alter table public.uc_permission 
       add column version int4 not null;
