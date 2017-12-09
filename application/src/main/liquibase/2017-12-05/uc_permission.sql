--liquibase formatted sql

INSERT INTO public.uc_permission (id, method, name, path) VALUES ('3199b212-91c5-46ce-ab7a-55ed92c3b75e', 'POST', 'Social Find User Read', 'social/people/find');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('65f74026-897c-493e-9b49-c1ecb24a5e7f', 'POST', 'User Control Group MultiSelect', 'usercontrol/group/multiselect');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('40b960ae-754e-41a4-a64a-81cf70ef7355', 'POST', 'User Control Permission Table', 'usercontrol/permission/table');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('62b967d7-2291-4961-9b24-abf546d3c62c', 'GET', 'Social Profile Read', 'social/profile');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('20d17b9a-9a3d-40fa-97d8-44ea3207e65c', 'POST', 'User Control Group Table', 'usercontrol/group/table');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('2a2cae87-cfad-4ae0-8f64-67cfab296ee1', 'POST', 'User Control Role Table', 'usercontrol/role/table');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('22e667e7-aedf-4474-94de-ae332722c180', 'POST', 'Channel Meld Post Save', 'channel/meld');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('f027af3a-7bb7-42d9-89d9-a467534ceca6', 'PUT', 'Channel Meld Post Update', 'channel/meld/{id}');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('05fc7f1d-6b0e-4a46-8616-55b19fd4923b', 'GET', 'Channel Meld Post Read', 'channel/meld/{id}');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('6d2bc89b-a051-4ed4-bd9e-455a8b79dd3f', 'PUT', 'Social Education Update', 'social/user/current/education');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('12986aa7-118a-434b-882b-09c437c584c8', 'GET', 'Social Education Read', 'social/user/current/education');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('1e8fcee6-e20e-4161-8b14-5ce03d00a9f0', 'GET', 'Social Education Read', 'social/user/{id: [0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}}/education');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('bcd429a9-2dcf-414f-9075-a982e7ca94d0', 'POST', 'Social Education Save', 'social/user/current/education');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('47a3a285-7245-40ed-a881-f4b2eef0d2ee', 'GET', 'User Control User Image', 'usercontrol/user/{id}/image');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('7740c489-a5ee-4633-b997-d94097c74d63', 'GET', 'User Control User Thumbnail', 'usercontrol/user/{id}/thumbnail');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('2a3d382c-a76a-4cc7-bde7-b99fc41fa2a0', 'GET', 'Social Places Read', 'social/user/current/places');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('a6a33573-a411-43a2-bda9-f773de705085', 'PUT', 'Social Places Update', 'social/user/current/places');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('b0f6501c-507f-4b04-a1f5-64c1fa9adbe9', 'POST', 'Social Places Save', 'social/user/current/places');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('081c5003-41ab-443c-9ddd-1eb8b6624a47', 'POST', 'Social Places Geo coding', 'social/place');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('d58caa97-41da-4143-8db5-647fb039507b', 'GET', 'Social Places Read', 'social/user/{id: [0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}}/places');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('50ee4722-c0bf-4318-9be5-bb1b9c51a619', 'DELETE', 'Social Categories Delete', 'social/people/category/{id}');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('707fc806-77f4-4ebf-8145-b3079b595ffc', 'POST', 'Social Categories Save', 'social/people/category');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('55b16d19-e6ab-491f-adc9-c4e61ac17975', 'PUT', 'Social Categories Update', 'social/people/category/{id}');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('62abdb15-427d-40ce-895d-9fe4d536c551', 'GET', 'Channel Meld Post Plus One', 'channel/meld/{id}/plus/one');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('bf5a87ab-90cb-41f4-af10-3f4621efdc55', 'GET', 'Channel Meld Post Plus One', 'channel/meld/comment/{id}/plus/one');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('f82b7b25-9598-41b4-b429-3ab6e34b97b4', 'PUT', 'Social Find User Read', 'social/people/find');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('87035535-3b58-43ce-8f63-1c8eeada8d9d', 'POST', 'Channel Meld Post add Comment', 'channel/meld/post/{id}/comment');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('a3cf4012-9279-4005-8f22-34ca5c16983d', 'PUT', 'Channel Meld Post add Comment', 'channel/meld/post/comment/{id}');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('9a5b77fa-1796-40f7-a057-6ad198e78431', 'GET', 'User Control User Table META', 'usercontrol/user/table/meta');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('3af4acf3-752a-4682-886b-d5200e2bcf40', 'POST', 'User Control User Table', 'usercontrol/user/table');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('dd1b07ed-aeb1-4102-b2d8-143ce65f7171', 'POST', 'Social Categories Read', 'social/people/categories');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('57dac83b-de57-499f-8f40-ff3118798ad5', 'PUT', 'Social Personal Contact Save', 'social/user/current/contact');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('3f7fb6d8-bf90-4599-aba9-6b4a38ace843', 'POST', 'Social Personal Contact Save', 'social/user/current/contact');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('24d780ed-8666-48cf-8d6e-c5be228feaa4', 'GET', 'Social Personal Contact Save', 'social/user/current/contact');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('7dd2bb5f-a533-45e0-b024-ee38117cc87d', 'GET', 'Social Personal Contact Save', 'social/user/{id: [0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}}/contact');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('4ed3151a-76a9-499e-961e-2a1f6afda8e3', 'POST', 'User Control Role Form Name Validate', 'usercontrol/role/form/validate');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('57a40e96-d28f-476c-9a08-36115a3f9643', 'DELETE', 'User Control Role Form Delete', 'usercontrol/role/{id}/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('1c283fee-82ce-4d3e-8999-c334b3d547d2', 'PUT', 'User Control Role Form Update', 'usercontrol/role/{id}/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('0959a3ab-3577-4179-89cf-06277100f521', 'POST', 'User Control Role Form Save', 'usercontrol/role/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('6ec2e0d5-29c3-4e59-a819-25c5e36aa461', 'GET', 'User Control Role Form Read', 'usercontrol/role/{id}/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('a7045aa2-e597-4a6e-8a5b-62ae097a967b', 'PUT', 'Social Work History Update', 'social/user/current/work/history');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('c3229b62-fe5c-4a7b-94a5-3679b9c77e48', 'GET', 'Social Work History Read Current', 'social/user/current/work/history');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('6db22d80-50b7-47c4-a437-01a050f61319', 'GET', 'Social Work History Read', 'social/user/{id}/work/history');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('0d69b5cd-e9fe-4003-9264-162c411963c2', 'POST', 'Social Work History Save', 'social/user/current/work/history');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('c946268f-33ac-499b-87dc-3e079df33a9d', 'POST', 'Channel Meld Posts', 'channel/meld/posts');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('0d1ad934-c891-4035-9cec-c716709c03ad', 'PUT', 'User Control User Form Update', 'usercontrol/user/{id}/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('deb65040-27ec-4ec5-8381-7d23105306f4', 'DELETE', 'User Control User Form Delete', 'usercontrol/user/{id}/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('6597d6eb-4c8b-4a45-a3b1-9c3452d49c78', 'GET', 'User Control User Form Read', 'usercontrol/user/create/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('252c0ea2-b67d-4489-8fbc-2274e2a2c4c6', 'POST', 'User Control User Form Name Validate', 'usercontrol/user/form/validate');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('de7369a7-35bc-4cee-8eca-13e2b1ab3251', 'GET', 'User Control User Form Read', 'usercontrol/user/{id}/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('06c84209-561d-4104-b0b2-26ec28f0b9a4', 'POST', 'User Control User Form Save', 'usercontrol/user/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('d1f9a315-8e0a-45e4-848a-8afa35192efc', 'GET', 'User Control Group Form Read', 'usercontrol/group/{id}/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('2bc38748-b069-4209-b50e-ddd5b9d35181', 'DELETE', 'User Control Group Form Delete', 'usercontrol/group/{id}/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('36929831-8c8f-4506-b13d-044c796ef07d', 'POST', 'User Control Group Form Name Validate', 'usercontrol/group/form/validate');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('b0cd0f77-64b2-458b-b95f-8381ccee2516', 'POST', 'User Control Group Form Save', 'usercontrol/group/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('9d2d3da4-d55f-4106-a2a6-1a217023e652', 'PUT', 'User Control Group Form Update', 'usercontrol/group/{id}/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('c2da1ff2-d1fa-4802-b4ac-0cfbd8966f54', 'DELETE', 'Channel', 'channel/meld/upload/image');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('876858da-4a1a-415a-b6f2-cc1cd4d1353a', 'POST', 'Channel Meld Image Upload', 'channel/meld/upload/image');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('0cdb4a63-3208-4652-8b58-56a0ef0f5aaf', 'GET', 'Channel Meld Thumbnail', 'channel/meld/upload/thumbnail');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('54905c66-799a-4e92-b9c6-52bf42866247', 'GET', 'Channel Meld Thumbnail', 'channel/meld/{id}/thumbnail');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('c7353232-5b55-460e-9f27-69fc8672b57d', 'GET', 'Channel Meld Image', 'channel/meld/{id}/image');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('c3050901-263b-472a-9a7d-6bc9dd91a260', 'GET', 'Channel Meld Image', 'channel/meld/upload/image');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('09429651-e571-4a7c-ab52-8932824724b5', 'POST', 'User Control Role MultiSelect', 'usercontrol/role/multiselect');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('3011f56e-bf10-431b-8e9f-93902cfc0b9f', 'GET', 'Social User Form Read', 'social/user/{id: [0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}}/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('07ad4442-0e0e-4c30-9004-0688a83f26b4', 'GET', 'Social User Form Read', 'social/user/current/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('6179d5b4-4e26-4892-9b55-3e82e6042aac', 'POST', 'Social User Form Save', 'social/user/current/form');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('d59116f3-8c4e-4b44-b7ad-7075fecab0ba', 'POST', 'Social User Form Name Validate', 'social/user/current/form/validate');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('4c85a0d0-ce9a-4dc8-b7af-7c0d9d437253', 'PUT', 'Social User Form Update', 'social/user/current/form');