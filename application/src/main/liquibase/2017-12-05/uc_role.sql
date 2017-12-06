--liquibase formatted sql

INSERT INTO public.uc_role (id, version, name) VALUES ('8c4329d7-6953-4b71-8be4-2f2a5b960fa4', 1, 'Administrator');
INSERT INTO public.uc_role (id, version, name) VALUES ('d91f5810-c1cf-4457-8cf1-f892e40d543a', 0, 'User');