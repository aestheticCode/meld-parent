--liquibase formatted sql

--changeset patrick:1

UPDATE uc_permission SET method = 'GET' WHERE path = 'social/people/find' and method = 'POST'


--changeset patrick:2

    alter table public.so_address
       add column places_id uuid;

    alter table public.so_company
       add column history_id uuid;

    alter table so_address
       add constraint FKqhxjywexgn69nnpsya43f62rn
       foreign key (places_id)
       references so_places;

    alter table so_company
       add constraint FKuuji6fx2fcsixjfvucw947od
       foreign key (history_id)
       references so_workHistory;

--changeset patrick:3

drop TABLE so_workhistory_so_company;
drop table so_education_so_school;

--changeset patrick:4

drop TABLE so_places_so_address

--changeset patrick:5

    alter table public.so_chat
       add column contact_id uuid;

    alter table public.so_chat
       add column chats_ORDER int4;

    alter table public.so_phone
       add column contact_id uuid;

    alter table public.so_phone
       add column phones_ORDER int4;

    alter table so_chat
       add constraint FKnld7x3s1ucixf6tl0pox2wi5n
       foreign key (contact_id)
       references so_personalContact;

    alter table so_phone
       add constraint FKc6pyji8gt4p9e8ibc1023p087
       foreign key (contact_id)
       references so_personalContact;


drop TABLE so_personalcontact_so_address;
drop TABLE so_personalcontact_so_chat;
drop table so_personalcontact_so_phone;

