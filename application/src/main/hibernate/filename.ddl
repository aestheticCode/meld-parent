
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
