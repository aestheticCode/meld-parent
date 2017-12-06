--liquibase formatted sql

INSERT INTO public.uc_identity (dtype, id, version, name, birthdate, externalid, firstname, gender, lastname) VALUES ('Group', 'a7910541-bdd2-4502-803d-3ac364257a97', 1, 'Administrators', null, null, null, null, null);
INSERT INTO public.uc_identity (dtype, id, version, name, birthdate, externalid, firstname, gender, lastname) VALUES ('Group', '3decdd61-3f47-4f27-b6ac-3e0945466574', 0, 'Users', null, null, null, null, null);