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

--changeset patrick:6

INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('d42fdfcb-5c31-49e8-a1e8-b1f27e519a13', 'GET', 'Social Addresses Read', 'social/user/current/places/addresses', now(), now(), 0);

delete from uc_role_uc_permission WHERE permissions_id = 'b4991f35-0ab7-400c-8f5d-f398e0c82c83';
delete from uc_permission WHERE id = 'b4991f35-0ab7-400c-8f5d-f398e0c82c83';

--changeset patrick:7

INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('60842301-c1b1-4792-8aef-443920e89b5c', 'GET', 'Social Companies List', 'social/user/current/work/history/companies', now(), now(), 0);

--changeset patrick:8

update uc_permission set method = 'GET' where path = 'social/people/categories' and method = 'POST';
update uc_permission set method = 'GET' where path = 'usercontrol/group/multiselect' and method = 'POST';
update uc_permission set method = 'GET' where path = 'usercontrol/group/table' and method = 'POST';
update uc_permission set method = 'GET' where path = 'channel/meld/posts' and method = 'POST';
update uc_permission set method = 'GET' where path = 'usercontrol/permission/table' and method = 'POST';
update uc_permission set method = 'GET' where path = 'media/photos/grid' and method = 'POST';
update uc_permission set method = 'GET' where path = 'usercontrol/role/multiselect' and method = 'POST';
update uc_permission set method = 'GET' where path = 'usercontrol/role/table' and method = 'POST';
update uc_permission set method = 'GET' where path = 'social/education/find' and method = 'POST';
update uc_permission set method = 'GET' where path = 'usercontrol/user/table' and method = 'POST';

