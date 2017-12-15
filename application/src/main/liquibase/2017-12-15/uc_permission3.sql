update uc_permission set path = 'social/user/current/profile/user' WHERE path = 'social/profile/user';
update uc_permission set path = 'social/user/current/profile/background' WHERE path = 'social/profile/background';

INSERT INTO public.uc_permission (id, method, name, path) VALUES ('ee1009ad-017c-4f9a-aa76-48519524f8a2', 'GET', 'SocialSocial Profile User Read', 'social/user/{id}/profile/user');
INSERT INTO public.uc_permission (id, method, name, path) VALUES ('951e0289-7994-4f7b-a953-9dadba15703f', 'GET', 'Social Profile Background Read', 'social/user/{id}/profile/background');

