
    alter table public.so_school 
       add column education_id uuid;

    alter table so_school 
       add constraint FKt59rn73yotb85se1fl5q0bpvr 
       foreign key (education_id) 
       references so_education;
