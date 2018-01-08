
    alter table public.so_school 
       add column yearEndSemester varchar(255);

    alter table public.so_school 
       add column yearEndYear int4;

    alter table public.so_school 
       add column yearStartSemester varchar(255);

    alter table public.so_school 
       add column yearStartYear int4;

    alter table public.so_school 
       add column visitEndSemester varchar(255);

    alter table public.so_school 
       add column visitEndYear int4;

    alter table public.so_school 
       add column visitStartSemester varchar(255);

    alter table public.so_school 
       add column visitStartYear int4;


    alter table so_school
      drop COLUMN endsemester;

    alter table so_school
      drop COLUMN endyear;

    alter table so_school
      drop COLUMN startsemester;

    alter table so_school
      drop COLUMN startyear;
