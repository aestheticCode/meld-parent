--liquibase formatted sql

--changeset patrick:1

INSERT INTO public.uc_permission (id, method, name, path) VALUES ('9b021db1-3822-4513-b878-0184839d69cf', 'DELETE', 'Social Education Delete', 'social/user/current/education');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('97fe5371-786c-49fd-b907-86dd0dadf00c', 'GET', 'Social Education Create', 'social/user/current/education/create');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('bf192238-28aa-4d20-91ca-02d23f966389', 'GET', 'Channel Meld Post Create', 'channel/meld/create/{type}');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('50c69150-e3d5-49ad-9b76-de0d0474579f', 'DELETE', 'Social User Form Delete', 'social/user/current/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('799826c7-f2dd-493d-a94e-dd79f029d9c1', 'GET', 'User Control Group Form Create', 'usercontrol/group/create/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('e41d77ce-89d0-47f2-800b-6b971ce09847', 'GET', 'User Control Role Form Create', 'usercontrol/role/create/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('2c873691-6f01-42cf-93b9-d856761a4e21', 'GET', 'Photos Photo Read', 'media/photo/create');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('dc77ab3a-5514-472e-8224-7817a933c3a2', 'DELETE', 'Social Personal Contact Delete', 'social/user/current/contact');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('53405e3d-4f0b-4ba1-957f-eebb3be2887a', 'DELETE', 'Channel Meld Post add Comment', 'channel/meld/post/comment/{id}');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('8bfb4c5d-e3a9-4126-a990-458c505aa6b0', 'POST', 'Channel Meld Post create Comment', 'channel/meld/post/{id}/comment/create');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('700729e1-7f67-4fec-8336-1f0dbbab3b68', 'GET', 'Social Categories Read', 'social/people/category/create');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('33987671-e431-4fea-a0a7-fc09cd946759', 'GET', 'Social Work History Read Current', 'social/user/current/work/history/create');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('5ad30e51-b94a-492c-920c-15b262f13ad9', 'DELETE', 'Social Work History Delete', 'social/user/current/work/history');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('1c6d710e-6c75-4e21-a396-43c0f70152d1', 'GET', 'Social Places Create', 'social/user/current/places/create');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('3e243e64-8747-42d3-a0d2-22f3b8210897', 'DELETE', 'Social Places Update', 'social/user/current/places');
