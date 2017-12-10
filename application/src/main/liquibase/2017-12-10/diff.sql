--liquibase formatted sql


ALTER TABLE public.uc_image DROP COLUMN image;
ALTER TABLE public.uc_image DROP COLUMN thumbnail;

ALTER TABLE public.cn_image DROP COLUMN image;
ALTER TABLE public.cn_image DROP COLUMN thumbnail;
