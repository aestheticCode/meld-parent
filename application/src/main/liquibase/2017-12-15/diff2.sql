
    alter table public.so_school
       add column endSemester varchar(255);

    alter table public.so_school
       add column endYear int4;

    alter table public.so_school
       add column startSemester varchar(255);

    alter table public.so_school
       add column startYear int4;

    alter TABLE public.so_school
       drop COLUMN startdate;

    alter TABLE public.so_school
      drop COLUMN enddate;
