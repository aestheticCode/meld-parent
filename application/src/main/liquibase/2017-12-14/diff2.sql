
    alter table public.so_address
       add column tillNow boolean not null DEFAULT false;

    alter table public.so_company
       add column tillNow boolean not null DEFAULT false;

    alter table public.so_school
       add column tillNow boolean not null DEFAULT false;
