--liquibase formatted sql

INSERT INTO public.uc_permission (id, method, name, path) VALUES ('d416747d-1ad6-4f02-83c8-9c93cf5b5c61', 'POST', 'Photos Photo Save', 'media/photo');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('da92e199-5ae9-4a76-a26c-35c7f2de26f3', 'PUT', 'Photos Photo Update', 'media/photo/{id}');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('42509d33-cc57-4de6-9110-7e636bdc3f4e', 'GET', 'Photos Photo Read', 'media/photo/{id}');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('480be602-bf89-4ece-8a93-fe8feb2f16dd', 'POST', 'Photos Photos Read', 'media/photos/grid');