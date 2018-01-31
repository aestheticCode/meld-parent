--liquibase formatted sql

--changeset patrick:1

    create table AccountTypeEntity (
       email varchar(255),
        firstName varchar(255),
        lastName varchar(255),
        loginName varchar(255),
        id varchar(255) not null,
        primary key (id)
    );

    create table AttributedTypeEntity (
       id varchar(255) not null,
        primary key (id)
    );

    create table AttributeTypeEntity (
       id int8 not null,
        name varchar(255),
        typeName varchar(255),
        value varchar(1024),
        owner_id varchar(255),
        primary key (id)
    );

    create table cn_comment (
       id uuid not null,
        created timestamp,
        text text,
        user_id uuid,
        primary key (id)
    );

    create table cn_comment_cn_comment (
       MeldComment_id uuid not null,
        comments_id uuid not null,
        comments_ORDER int4 not null,
        primary key (MeldComment_id, comments_ORDER)
    );

    create table cn_comment_uc_identity (
       MeldComment_id uuid not null,
        likes_id uuid not null,
        likes_ORDER int4 not null,
        primary key (MeldComment_id, likes_ORDER)
    );

    create table cn_image (
       id uuid not null,
        fileName varchar(255),
        lastModified timestamp,
        primary key (id)
    );

    create table cn_post (
       DTYPE varchar(31) not null,
        id uuid not null,
        created timestamp,
        modified timestamp,
        version int4 not null,
        header varchar(255),
        text text,
        videoId varchar(255),
        link varchar(255),
        category_id uuid,
        user_id uuid,
        image_id uuid,
        photo_id uuid,
        primary key (id)
    );

    create table cn_post_cn_comment (
       MeldPost_id uuid not null,
        comments_id uuid not null,
        comments_ORDER int4 not null,
        primary key (MeldPost_id, comments_ORDER)
    );

    create table cn_post_uc_identity (
       MeldPost_id uuid not null,
        likes_id uuid not null,
        likes_ORDER int4 not null,
        primary key (MeldPost_id, likes_ORDER)
    );

    create table GroupTypeEntity (
       name varchar(255),
        path varchar(255),
        id varchar(255) not null,
        parent_id varchar(255),
        primary key (id)
    );

    create table IdentityTypeEntity (
       createdDate timestamp,
        enabled boolean not null,
        expirationDate timestamp,
        typeName varchar(255),
        id varchar(255) not null,
        partition_id varchar(255),
        primary key (id)
    );

    create table me_photo (
       id uuid not null,
        created timestamp,
        modified timestamp,
        version int4 not null,
        fileName varchar(255),
        lastModified timestamp,
        user_id uuid,
        primary key (id)
    );

    create table PartitionTypeEntity (
       configurationName varchar(255),
        name varchar(255),
        typeName varchar(255),
        id varchar(255) not null,
        primary key (id)
    );

    create table PasswordCredentialTypeEntity (
       id int8 not null,
        effectiveDate timestamp,
        expiryDate timestamp,
        typeName varchar(255),
        passwordEncodedHash varchar(255),
        passwordSalt varchar(255),
        owner_id varchar(255),
        primary key (id)
    );

    create table PersonalContact_emails (
       PersonalContact_id uuid not null,
        emails varchar(255),
        emails_ORDER int4 not null,
        primary key (PersonalContact_id, emails_ORDER)
    );

    create table RelationshipIdentityTypeEntity (
       identifier int8 not null,
        descriptor varchar(255),
        identityType_id varchar(255),
        owner_id varchar(255),
        primary key (identifier)
    );

    create table RelationshipTypeEntity (
       typeName varchar(255),
        id varchar(255) not null,
        primary key (id)
    );

    create table RoleTypeEntity (
       name varchar(255),
        id varchar(255) not null,
        primary key (id)
    );

    create table so_address (
       id uuid not null,
        endDate date,
        place_country varchar(255),
        place_id varchar(255),
        place_lat float8,
        place_lng float8,
        place_name varchar(255),
        place_state varchar(255),
        place_street varchar(255),
        place_street_number varchar(255),
        place_zipCode varchar(255),
        startDate date,
        tillNow boolean not null,
        primary key (id)
    );

    create table so_category (
       id uuid not null,
        name varchar(255),
        user_id uuid,
        primary key (id)
    );

    create table so_chat (
       id uuid not null,
        name varchar(255),
        type int4,
        primary key (id)
    );

    create table so_company (
       id uuid not null,
        description varchar(255),
        endDate date,
        name varchar(255),
        place_country varchar(255),
        place_id varchar(255),
        place_lat float8,
        place_lng float8,
        place_name varchar(255),
        place_state varchar(255),
        place_street varchar(255),
        place_street_number varchar(255),
        place_zipCode varchar(255),
        startDate date,
        tillNow boolean not null,
        title varchar(255),
        primary key (id)
    );

    create table so_education (
       id uuid not null,
        created timestamp,
        modified timestamp,
        version int4 not null,
        user_id uuid,
        primary key (id)
    );

    create table so_personalContact (
       id uuid not null,
        created timestamp,
        modified timestamp,
        version int4 not null,
        user_id uuid,
        primary key (id)
    );

    create table so_personalContact_so_address (
       PersonalContact_id uuid not null,
        addresses_id uuid not null,
        addresses_ORDER int4 not null,
        primary key (PersonalContact_id, addresses_ORDER)
    );

    create table so_personalContact_so_chat (
       PersonalContact_id uuid not null,
        chats_id uuid not null,
        chats_ORDER int4 not null,
        primary key (PersonalContact_id, chats_ORDER)
    );

    create table so_personalContact_so_phone (
       PersonalContact_id uuid not null,
        phones_id uuid not null,
        phones_ORDER int4 not null,
        primary key (PersonalContact_id, phones_ORDER)
    );

    create table so_phone (
       id uuid not null,
        number varchar(255),
        type int4,
        primary key (id)
    );

    create table so_places (
       id uuid not null,
        user_id uuid,
        primary key (id)
    );

    create table so_places_so_address (
       Places_id uuid not null,
        addresses_id uuid not null,
        addresses_ORDER int4 not null,
        primary key (Places_id, addresses_ORDER)
    );

    create table so_profile (
       id uuid not null,
        created timestamp,
        modified timestamp,
        version int4 not null,
        backgroundPhoto_id uuid,
        user_id uuid,
        userPhoto_id uuid,
        primary key (id)
    );

    create table so_relationship (
       id uuid not null,
        category_id uuid,
        from_id uuid,
        to_id uuid,
        primary key (id)
    );

    create table so_school (
       id uuid not null,
        course varchar(255),
        description varchar(255),
        yearEndSemester varchar(255),
        yearEndYear int4,
        name varchar(255),
        place_country varchar(255),
        place_id varchar(255),
        place_lat float8,
        place_lng float8,
        place_name varchar(255),
        place_state varchar(255),
        place_street varchar(255),
        place_street_number varchar(255),
        place_zipCode varchar(255),
        yearStartSemester varchar(255),
        yearStartYear int4,
        tillNow boolean not null,
        visitEndSemester varchar(255),
        visitEndYear int4,
        visitStartSemester varchar(255),
        visitStartYear int4,
        education_id uuid,
        primary key (id)
    );

    create table so_workHistory (
       id uuid not null,
        created timestamp,
        modified timestamp,
        version int4 not null,
        user_id uuid,
        primary key (id)
    );

    create table so_workHistory_so_company (
       WorkHistory_id uuid not null,
        companies_id uuid not null,
        companies_ORDER int4 not null,
        primary key (WorkHistory_id, companies_ORDER)
    );

    create table TokenCredentialTypeEntity (
       id int8 not null,
        effectiveDate timestamp,
        expiryDate timestamp,
        typeName varchar(255),
        token TEXT,
        type varchar(255),
        owner_id varchar(255),
        primary key (id)
    );

    create table uc_identity (
       DTYPE varchar(31) not null,
        id uuid not null,
        created timestamp,
        modified timestamp,
        version int4 not null,
        name varchar(255),
        birthdate date,
        externalId varchar(255),
        firstName varchar(255),
        gender int4,
        lastName varchar(255),
        primary key (id)
    );

    create table uc_identity_uc_identity (
       Group_id uuid not null,
        members_id uuid not null,
        primary key (Group_id, members_id)
    );

    create table uc_image (
       id uuid not null,
        fileName varchar(255),
        lastModified timestamp,
        user_id uuid,
        primary key (id)
    );

    create table uc_permission (
       id uuid not null,
        created timestamp,
        modified timestamp,
        version int4 not null,
        method varchar(255),
        name varchar(255),
        path varchar(255),
        primary key (id)
    );

    create table uc_role (
       id uuid not null,
        created timestamp,
        modified timestamp,
        version int4 not null,
        name varchar(255),
        primary key (id)
    );

    create table uc_role_uc_identity (
       Role_id uuid not null,
        scopes_id uuid not null,
        primary key (Role_id, scopes_id)
    );

    create table uc_role_uc_permission (
       Role_id uuid not null,
        permissions_id uuid not null,
        primary key (Role_id, permissions_id)
    );

    alter table so_personalContact_so_address
       drop constraint if exists UK_l1wcpa8c7xswjsd2ia310ngtb;

    alter table so_personalContact_so_address
       add constraint UK_l1wcpa8c7xswjsd2ia310ngtb unique (addresses_id);

    alter table so_personalContact_so_chat
       drop constraint if exists UK_r0me384qord9bbrlyv9259mx1;

    alter table so_personalContact_so_chat
       add constraint UK_r0me384qord9bbrlyv9259mx1 unique (chats_id);

    alter table so_personalContact_so_phone
       drop constraint if exists UK_9b6b0jv9lm6p9htodotrv8ssi;

    alter table so_personalContact_so_phone
       add constraint UK_9b6b0jv9lm6p9htodotrv8ssi unique (phones_id);

    alter table so_places_so_address
       drop constraint if exists UK_hsyh9tgmxwbkoo0xmg301nsio;

    alter table so_places_so_address
       add constraint UK_hsyh9tgmxwbkoo0xmg301nsio unique (addresses_id);

    alter table so_workHistory_so_company
       drop constraint if exists UK_kw3wf29m1avx1kr3wru61an9g;

    alter table so_workHistory_so_company
       add constraint UK_kw3wf29m1avx1kr3wru61an9g unique (companies_id);

    alter table uc_identity
       drop constraint if exists UK_q2komuiagft1ry9qjn53ji0pt;

    alter table uc_identity
       add constraint UK_q2komuiagft1ry9qjn53ji0pt unique (name);
create sequence hibernate_sequence start 1 increment 1;

    alter table AccountTypeEntity
       add constraint FK8oixjku214nivbx3mu3rup9q2
       foreign key (id)
       references IdentityTypeEntity;

    alter table AttributeTypeEntity
       add constraint FK3fc0sfh4p5fwqujn3bcqyfi1q
       foreign key (owner_id)
       references AttributedTypeEntity;

    alter table cn_comment
       add constraint FKd505qhqhbi64yffvk4pxcbpdc
       foreign key (user_id)
       references uc_identity;

    alter table cn_comment_cn_comment
       add constraint FK4khiocdp8foqud8w1h8yb1uk8
       foreign key (comments_id)
       references cn_comment;

    alter table cn_comment_cn_comment
       add constraint FK48cfd0uwvy1iaaxp355a1ob81
       foreign key (MeldComment_id)
       references cn_comment;

    alter table cn_comment_uc_identity
       add constraint FKdpj784i1it6pletdx8bkm8grj
       foreign key (likes_id)
       references uc_identity;

    alter table cn_comment_uc_identity
       add constraint FK9303ajueevv0pi2jqe0oswp3l
       foreign key (MeldComment_id)
       references cn_comment;

    alter table cn_post
       add constraint FKbhydicgnrb7bam7vqcjtwjmx0
       foreign key (category_id)
       references so_category;

    alter table cn_post
       add constraint FKi9wbf17qftmf62ncobcjn2crv
       foreign key (user_id)
       references uc_identity;

    alter table cn_post
       add constraint FKowu29qk4cifb66sp3ffpox8o1
       foreign key (image_id)
       references cn_image;

    alter table cn_post
       add constraint FK1skc7nlqmc1dx12gw3f6m8801
       foreign key (photo_id)
       references me_photo;

    alter table cn_post_cn_comment
       add constraint FKd3g50hx3gh4jk8kuxxjk2djlq
       foreign key (comments_id)
       references cn_comment;

    alter table cn_post_cn_comment
       add constraint FKq8datrrhpwo2m4llmqrtjst7o
       foreign key (MeldPost_id)
       references cn_post;

    alter table cn_post_uc_identity
       add constraint FKhv0j72t388xxf3oamvrqwpj1q
       foreign key (likes_id)
       references uc_identity;

    alter table cn_post_uc_identity
       add constraint FKj0fewbmb0cb6y9hlwa7qx2gwe
       foreign key (MeldPost_id)
       references cn_post;

    alter table GroupTypeEntity
       add constraint FKcfwrrwheglinq7fpqp08nvfl8
       foreign key (parent_id)
       references GroupTypeEntity;

    alter table GroupTypeEntity
       add constraint FKknkix69udwy3a3spdaefml776
       foreign key (id)
       references IdentityTypeEntity;

    alter table IdentityTypeEntity
       add constraint FKbuy38ltx6ij4ykptxgdi07x05
       foreign key (partition_id)
       references PartitionTypeEntity;

    alter table IdentityTypeEntity
       add constraint FK1qxcf48j9k2h32is731dn50qo
       foreign key (id)
       references AttributedTypeEntity;

    alter table me_photo
       add constraint FK9w8porh1c99j9egi9saruq7x5
       foreign key (user_id)
       references uc_identity;

    alter table PartitionTypeEntity
       add constraint FKgyobt200r1w4xcx19mbx6viik
       foreign key (id)
       references AttributedTypeEntity;

    alter table PasswordCredentialTypeEntity
       add constraint FKkuakw2lg2vko45b0hwm3vbf01
       foreign key (owner_id)
       references AttributedTypeEntity;

    alter table PersonalContact_emails
       add constraint FK2aqujhjh7wcafovh51le0h0pc
       foreign key (PersonalContact_id)
       references so_personalContact;

    alter table RelationshipIdentityTypeEntity
       add constraint FK8b6t4v6mf47qq8941o4i2n3kh
       foreign key (identityType_id)
       references IdentityTypeEntity;

    alter table RelationshipIdentityTypeEntity
       add constraint FK974q2hxrr2bi72ciduuockjt
       foreign key (owner_id)
       references RelationshipTypeEntity;

    alter table RelationshipTypeEntity
       add constraint FKj04ddo357wkwmkb3rptbxsin3
       foreign key (id)
       references AttributedTypeEntity;

    alter table RoleTypeEntity
       add constraint FKnyw0xnnlou75od1kp01n5jhfj
       foreign key (id)
       references IdentityTypeEntity;

    alter table so_category
       add constraint FKfs6te1odm697u6c7f6w7e88j
       foreign key (user_id)
       references uc_identity;

    alter table so_education
       add constraint FKqf0do8pl19u87uf8j0iiqb5g6
       foreign key (user_id)
       references uc_identity;

    alter table so_personalContact
       add constraint FK9hf8qvjs3dcxr4m93cb00iogl
       foreign key (user_id)
       references uc_identity;

    alter table so_personalContact_so_address
       add constraint FKa07ublsylfqn00briotwcnuce
       foreign key (addresses_id)
       references so_address;

    alter table so_personalContact_so_address
       add constraint FKck2mhw6h1w9cm3liy5y0m8u5o
       foreign key (PersonalContact_id)
       references so_personalContact;

    alter table so_personalContact_so_chat
       add constraint FKpm7lkt413tt2uxifg0ytpv0la
       foreign key (chats_id)
       references so_chat;

    alter table so_personalContact_so_chat
       add constraint FKl3x2yin0w4bramxb3f25xir1c
       foreign key (PersonalContact_id)
       references so_personalContact;

    alter table so_personalContact_so_phone
       add constraint FKh4bcn6uf8sgfysni7cplc44bf
       foreign key (phones_id)
       references so_phone;

    alter table so_personalContact_so_phone
       add constraint FKkrllrpx7lqpldbqon692waep0
       foreign key (PersonalContact_id)
       references so_personalContact;

    alter table so_places
       add constraint FKf5qtl2xtm1jnd5lm0rwh0ko01
       foreign key (user_id)
       references uc_identity;

    alter table so_places_so_address
       add constraint FK53rijdywj9pckuxyk7cy27sf9
       foreign key (addresses_id)
       references so_address;

    alter table so_places_so_address
       add constraint FKjj5lfcnprugrl0vule6jy0rce
       foreign key (Places_id)
       references so_places;

    alter table so_profile
       add constraint FKq9kplhqlvi1fyecek5x34241o
       foreign key (backgroundPhoto_id)
       references me_photo;

    alter table so_profile
       add constraint FKac6gxw2uyi8bw50477cpsl62p
       foreign key (user_id)
       references uc_identity;

    alter table so_profile
       add constraint FKjajy2g88qrwydu0b8w1gqia88
       foreign key (userPhoto_id)
       references me_photo;

    alter table so_relationship
       add constraint FKsacgki45tn5ph4jingn6vmwxj
       foreign key (category_id)
       references so_category;

    alter table so_relationship
       add constraint FK3etdc57csvew8bmod1ku86ceq
       foreign key (from_id)
       references uc_identity;

    alter table so_relationship
       add constraint FKsc5scuplikquhguusnmvlxb1y
       foreign key (to_id)
       references uc_identity;

    alter table so_school
       add constraint FKt59rn73yotb85se1fl5q0bpvr
       foreign key (education_id)
       references so_education;

    alter table so_workHistory
       add constraint FKmw4n59mqn1wyd45evw5h4ty7i
       foreign key (user_id)
       references uc_identity;

    alter table so_workHistory_so_company
       add constraint FK3a7dtpfmkio305wdodaa13fdp
       foreign key (companies_id)
       references so_company;

    alter table so_workHistory_so_company
       add constraint FKr8ltx3o7etn53dl474flnpntx
       foreign key (WorkHistory_id)
       references so_workHistory;

    alter table TokenCredentialTypeEntity
       add constraint FKax6xg86mlowbydnyg645ekvik
       foreign key (owner_id)
       references AttributedTypeEntity;

    alter table uc_identity_uc_identity
       add constraint FKq4o8bxngdiv3mjg9059fysi85
       foreign key (members_id)
       references uc_identity;

    alter table uc_identity_uc_identity
       add constraint FKtsuckon1qj4hl05j6bldek4m
       foreign key (Group_id)
       references uc_identity;

    alter table uc_image
       add constraint FKg6m5s17jmy3h4fugeihrumpm9
       foreign key (user_id)
       references uc_identity;

    alter table uc_role_uc_identity
       add constraint FKehjkm656m00yvfbecappo3e7l
       foreign key (scopes_id)
       references uc_identity;

    alter table uc_role_uc_identity
       add constraint FKaynr02r6rkp106alls4de8tkf
       foreign key (Role_id)
       references uc_role;

    alter table uc_role_uc_permission
       add constraint FK10omdqa4tudgtjod388c4yfgo
       foreign key (permissions_id)
       references uc_permission;

    alter table uc_role_uc_permission
       add constraint FK4n297y4y76f2tqi8ywf4o86yw
       foreign key (Role_id)
       references uc_role;

--changeset patrick:2

INSERT INTO public.attributedtypeentity (id) VALUES ('5ed71de4-9645-4ee4-aa13-2a301a83effc');
INSERT INTO public.attributedtypeentity (id) VALUES ('a54f522f-fd1c-4330-af25-303212eefa87');
INSERT INTO public.attributedtypeentity (id) VALUES ('11992e18-8ede-49f8-aa9b-50a780084f63');

INSERT INTO public.partitiontypeentity (configurationname, name, typename, id) VALUES ('default', 'default', 'org.picketlink.idm.model.basic.Realm', '5ed71de4-9645-4ee4-aa13-2a301a83effc');

INSERT INTO public.identitytypeentity (createddate, enabled, expirationdate, typename, id, partition_id) VALUES ('2017-12-09 15:37:05.016000', true, null, 'org.picketlink.idm.model.basic.User', 'a54f522f-fd1c-4330-af25-303212eefa87', '5ed71de4-9645-4ee4-aa13-2a301a83effc');
INSERT INTO public.identitytypeentity (createddate, enabled, expirationdate, typename, id, partition_id) VALUES ('2017-12-09 15:37:06.482000', true, null, 'org.picketlink.idm.model.basic.User', '11992e18-8ede-49f8-aa9b-50a780084f63', '5ed71de4-9645-4ee4-aa13-2a301a83effc');

INSERT INTO public.accounttypeentity (email, firstname, lastname, loginname, id) VALUES (null, null, null, 'PatrickBittner1Apr1980', 'a54f522f-fd1c-4330-af25-303212eefa87');
INSERT INTO public.accounttypeentity (email, firstname, lastname, loginname, id) VALUES (null, null, null, 'MartinDutkiewicz21Dec1979', '11992e18-8ede-49f8-aa9b-50a780084f63');

INSERT INTO public.passwordcredentialtypeentity (id, effectivedate, expirydate, typename, passwordencodedhash, owner_id, passwordsalt) VALUES (1, '2017-12-09 16:10:06.992000', null, 'org.picketlink.idm.credential.storage.EncodedPasswordStorage', '$2a$12$m8X5J3.cpHOe7uu4z9bdtOx9ztcovGMHV.mz4htjPNqwro2FNyV3y', 'a54f522f-fd1c-4330-af25-303212eefa87', '5824143678160461421');
INSERT INTO public.passwordcredentialtypeentity (id, effectivedate, expirydate, typename, passwordencodedhash, owner_id, passwordsalt) VALUES (2, '2017-12-09 16:10:07.414000', null, 'org.picketlink.idm.credential.storage.EncodedPasswordStorage', '$2a$12$vHQRtP0Ypyijykiqt0t6DeClzqu5GWWEDh5xPSQ52xf9yODSJKpUC', '11992e18-8ede-49f8-aa9b-50a780084f63', '176013283202288211');

INSERT INTO public.uc_identity (dtype, id, version, name, birthdate, externalid, firstname, gender, lastname) VALUES ('User', 'e8194dcb-58d7-46f4-ba7c-1d00a588036f', 1, 'PatrickBittner1Apr1980', '1982-12-09', 'a54f522f-fd1c-4330-af25-303212eefa87', 'Patrick', 0, 'Bittner');
INSERT INTO public.uc_identity (dtype, id, version, name, birthdate, externalid, firstname, gender, lastname) VALUES ('User', '54909778-8ae2-4fc2-97ec-a5548c0cce1b', 1, 'MartinDutkiewicz21Dec1979', '1981-12-09', '11992e18-8ede-49f8-aa9b-50a780084f63', 'Martin', 0, 'Dutkiewicz');
INSERT INTO public.uc_identity (dtype, id, version, name, birthdate, externalid, firstname, gender, lastname) VALUES ('Group', 'ff7eb24d-5799-4843-be6f-23b4163121fb', 0, 'Administrators', null, null, null, null, null);
INSERT INTO public.uc_identity (dtype, id, version, name, birthdate, externalid, firstname, gender, lastname) VALUES ('Group', '981c597f-a8b0-4658-9667-60b7acb72574', 0, 'Users', null, null, null, null, null);

INSERT INTO public.uc_identity_uc_identity (group_id, members_id) VALUES ('ff7eb24d-5799-4843-be6f-23b4163121fb', 'e8194dcb-58d7-46f4-ba7c-1d00a588036f');
INSERT INTO public.uc_identity_uc_identity (group_id, members_id) VALUES ('ff7eb24d-5799-4843-be6f-23b4163121fb', '54909778-8ae2-4fc2-97ec-a5548c0cce1b');
INSERT INTO public.uc_identity_uc_identity (group_id, members_id) VALUES ('981c597f-a8b0-4658-9667-60b7acb72574', 'e8194dcb-58d7-46f4-ba7c-1d00a588036f');
INSERT INTO public.uc_identity_uc_identity (group_id, members_id) VALUES ('981c597f-a8b0-4658-9667-60b7acb72574', '54909778-8ae2-4fc2-97ec-a5548c0cce1b');

INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('65f74026-897c-493e-9b49-c1ecb24a5e7f', 'POST', 'User Control Group MultiSelect', 'usercontrol/group/multiselect', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('40b960ae-754e-41a4-a64a-81cf70ef7355', 'POST', 'User Control Permission Table', 'usercontrol/permission/table', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('20d17b9a-9a3d-40fa-97d8-44ea3207e65c', 'POST', 'User Control Group Table', 'usercontrol/group/table', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('2a2cae87-cfad-4ae0-8f64-67cfab296ee1', 'POST', 'User Control Role Table', 'usercontrol/role/table', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('9a5b77fa-1796-40f7-a057-6ad198e78431', 'GET', 'User Control User Table META', 'usercontrol/user/table/meta', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('3af4acf3-752a-4682-886b-d5200e2bcf40', 'POST', 'User Control User Table', 'usercontrol/user/table', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('4ed3151a-76a9-499e-961e-2a1f6afda8e3', 'POST', 'User Control Role Form Name Validate', 'usercontrol/role/form/validate', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('57a40e96-d28f-476c-9a08-36115a3f9643', 'DELETE', 'User Control Role Form Delete', 'usercontrol/role/{id}/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('1c283fee-82ce-4d3e-8999-c334b3d547d2', 'PUT', 'User Control Role Form Update', 'usercontrol/role/{id}/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('0959a3ab-3577-4179-89cf-06277100f521', 'POST', 'User Control Role Form Save', 'usercontrol/role/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('6ec2e0d5-29c3-4e59-a819-25c5e36aa461', 'GET', 'User Control Role Form Read', 'usercontrol/role/{id}/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('0d1ad934-c891-4035-9cec-c716709c03ad', 'PUT', 'User Control User Form Update', 'usercontrol/user/{id}/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('deb65040-27ec-4ec5-8381-7d23105306f4', 'DELETE', 'User Control User Form Delete', 'usercontrol/user/{id}/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('6597d6eb-4c8b-4a45-a3b1-9c3452d49c78', 'GET', 'User Control User Form Read', 'usercontrol/user/create/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('252c0ea2-b67d-4489-8fbc-2274e2a2c4c6', 'POST', 'User Control User Form Name Validate', 'usercontrol/user/form/validate', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('de7369a7-35bc-4cee-8eca-13e2b1ab3251', 'GET', 'User Control User Form Read', 'usercontrol/user/{id}/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('06c84209-561d-4104-b0b2-26ec28f0b9a4', 'POST', 'User Control User Form Save', 'usercontrol/user/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('d1f9a315-8e0a-45e4-848a-8afa35192efc', 'GET', 'User Control Group Form Read', 'usercontrol/group/{id}/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('2bc38748-b069-4209-b50e-ddd5b9d35181', 'DELETE', 'User Control Group Form Delete', 'usercontrol/group/{id}/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('36929831-8c8f-4506-b13d-044c796ef07d', 'POST', 'User Control Group Form Name Validate', 'usercontrol/group/form/validate', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('b0cd0f77-64b2-458b-b95f-8381ccee2516', 'POST', 'User Control Group Form Save', 'usercontrol/group/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('9d2d3da4-d55f-4106-a2a6-1a217023e652', 'PUT', 'User Control Group Form Update', 'usercontrol/group/{id}/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('09429651-e571-4a7c-ab52-8932824724b5', 'POST', 'User Control Role MultiSelect', 'usercontrol/role/multiselect', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('ebbcadd9-35fc-48e5-ba9e-5944ec6f806c', 'GET', 'User Control Role Form Create', 'usercontrol/role/create/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('99b30772-a429-47cc-87d6-a54fd19dc292', 'GET', 'User Control Group Form Create', 'usercontrol/group/create/form', now(), now(), 0);


INSERT INTO public.uc_role (id, version, name) VALUES ('b6498b48-ce14-418e-947a-d06dfe87e73d', 0, 'User');
INSERT INTO public.uc_role (id, version, name) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', 1, 'Administrator');

INSERT INTO public.uc_role_uc_identity (role_id, scopes_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', 'ff7eb24d-5799-4843-be6f-23b4163121fb');
INSERT INTO public.uc_role_uc_identity (role_id, scopes_id) VALUES ('b6498b48-ce14-418e-947a-d06dfe87e73d', '981c597f-a8b0-4658-9667-60b7acb72574');

INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '65f74026-897c-493e-9b49-c1ecb24a5e7f');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '40b960ae-754e-41a4-a64a-81cf70ef7355');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '20d17b9a-9a3d-40fa-97d8-44ea3207e65c');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '2a2cae87-cfad-4ae0-8f64-67cfab296ee1');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '9a5b77fa-1796-40f7-a057-6ad198e78431');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '3af4acf3-752a-4682-886b-d5200e2bcf40');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '4ed3151a-76a9-499e-961e-2a1f6afda8e3');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '57a40e96-d28f-476c-9a08-36115a3f9643');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '1c283fee-82ce-4d3e-8999-c334b3d547d2');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '0959a3ab-3577-4179-89cf-06277100f521');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '6ec2e0d5-29c3-4e59-a819-25c5e36aa461');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '0d1ad934-c891-4035-9cec-c716709c03ad');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', 'deb65040-27ec-4ec5-8381-7d23105306f4');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '6597d6eb-4c8b-4a45-a3b1-9c3452d49c78');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '252c0ea2-b67d-4489-8fbc-2274e2a2c4c6');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', 'de7369a7-35bc-4cee-8eca-13e2b1ab3251');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '06c84209-561d-4104-b0b2-26ec28f0b9a4');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', 'd1f9a315-8e0a-45e4-848a-8afa35192efc');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '2bc38748-b069-4209-b50e-ddd5b9d35181');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '36929831-8c8f-4506-b13d-044c796ef07d');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', 'b0cd0f77-64b2-458b-b95f-8381ccee2516');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '9d2d3da4-d55f-4106-a2a6-1a217023e652');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '09429651-e571-4a7c-ab52-8932824724b5');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', 'ebbcadd9-35fc-48e5-ba9e-5944ec6f806c');
INSERT INTO public.uc_role_uc_permission (role_id, permissions_id) VALUES ('a0b1574d-2a08-4d54-a34a-d9ca8f4a1022', '99b30772-a429-47cc-87d6-a54fd19dc292');


--changeset patrick:3


INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('1e1ba6e6-fe44-489d-baa8-665408ade850', 'PUT', 'Channel Meld Post add Comment', 'channel/meld/post/comment/{id}', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('df7abe57-f262-45f5-82a3-454e1720094e', 'POST', 'Channel Meld Post create Comment', 'channel/meld/post/{id}/comment/create', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('e19dbbe8-5568-457a-a38d-54ddf312e3e3', 'DELETE', 'Channel Meld Post add Comment', 'channel/meld/post/comment/{id}', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('570c3615-499b-47d1-a32c-4a80e5b61661', 'POST', 'Channel Meld Post add Comment', 'channel/meld/post/{id}/comment', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('66f164c0-2853-4824-be15-e2496d09ead2', 'GET', 'Channel Meld Post Plus One', 'channel/meld/{id}/plus/one', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('00294032-685e-4f2d-a76f-bdc88f835c80', 'GET', 'Channel Meld Post Plus One', 'channel/meld/comment/{id}/plus/one', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('b06f8bb1-fb62-490b-a46d-5e07e5ece1aa', 'GET', 'Generic Google Places Details', 'generic/google/place/{id}/details', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('b071c273-d197-4918-8a20-08852da2d270', 'POST', 'Social Education Save', 'social/user/current/education', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('d0bf9af0-1db2-4b12-aa3a-96434beb906e', 'GET', 'Social Education Read', 'social/user/{id: [0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}}/education', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('f809ece5-5960-4cff-bc8d-86f05da561f9', 'GET', 'Social Education Read', 'social/user/current/education', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('6a236db7-04e2-43c6-9313-51878f2e8411', 'DELETE', 'Social Education Delete', 'social/user/current/education', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('564c6863-20a9-4461-8d70-abc2df270e7b', 'GET', 'Social Education Create', 'social/user/current/education/create', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('6ad38714-82d2-441a-98e2-d8870a89e12b', 'PUT', 'Social Education Update', 'social/user/current/education', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('ea7c118c-a8b5-427c-870b-6a0459c4e110', 'GET', 'Social Education Name', 'social/education/name', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('d491852e-e5fc-4281-962d-3ae5e4ef9458', 'GET', 'Social Work History Read', 'social/user/{id}/work/history', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('a8a61bed-9691-4c39-a4bb-5e7f6d34836e', 'POST', 'Social Work History Save', 'social/user/current/work/history', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('56d2b127-cf91-4db5-8dc1-6b4eb94c7554', 'GET', 'Social Work History Name', 'social/work/history/name', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('d2885c9a-a956-48b1-b6bf-63d993ea9372', 'PUT', 'Social Work History Update', 'social/user/current/work/history', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('b2a878fa-516e-460b-abc2-d52b111b3521', 'GET', 'Social Work History Read Current', 'social/user/current/work/history', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('8b8ae514-cf6f-486f-80f1-361bdf818c12', 'DELETE', 'Social Work History Delete', 'social/user/current/work/history', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('1c0dacf4-fb68-4f41-9378-fdb85b902b84', 'GET', 'Social Work History Read Current', 'social/user/current/work/history/create', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('a0bafe26-2fc3-4ccb-8d2b-cb102189902a', 'POST', 'Social Categories Save', 'social/people/category', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('1daf4d7e-cb5b-4869-a9a8-b4ebab20ca2e', 'DELETE', 'Social Categories Delete', 'social/people/category/{id}', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('413c22bc-ff79-487e-af61-d5045b12f7df', 'GET', 'Social Categories Read', 'social/people/category/create', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('b7ead1c6-f652-4826-9be7-c29805dd4b0d', 'GET', 'Social Categories Read', 'social/people/category/{id}', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('86c176e9-7fd5-48a7-a73f-4c3349dd361a', 'PUT', 'Social Categories Update', 'social/people/category/{id}', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('373b8e8a-34bb-49cd-ae3e-0b8350d574ad', 'POST', 'Photos Photos Read', 'media/photos/grid', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('f35cf673-3b4b-44cc-9f6d-d479c1ebf2c9', 'PUT', 'Social Personal Contact Save', 'social/user/current/contact', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('ebabeb8b-6194-482a-90fe-7fd86ada812b', 'GET', 'Social Personal Contact Read', 'social/user/{id: [0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}}/contact', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('cc04415e-1ee6-43bb-a4ba-4b875f0e24cb', 'GET', 'Social Personal Contact Create', 'social/user/current/contact/create', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('165e78f1-412f-4368-a4ab-99ef661e7dd5', 'POST', 'Social Personal Contact Save', 'social/user/current/contact', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('aacb51f0-1bc7-4147-ae73-142544c885f3', 'DELETE', 'Social Personal Contact Delete', 'social/user/current/contact', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('fff8917b-cc9a-4f5d-a4c0-48f66b992f26', 'GET', 'Social Personal Contact Read', 'social/user/current/contact', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('6f47b461-f026-4779-b1e5-e81cc8f93a4e', 'PUT', 'Channel Meld Post Update', 'channel/meld/{id}', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('8792d003-1552-404c-a3b4-d4f9705cfb62', 'POST', 'Channel Meld Post Save', 'channel/meld', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('a737122e-27c1-431f-bc91-b7dd8da27012', 'DELETE', 'Channel Meld Post Delete', 'channel/meld/{id}', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('68bfc356-23fa-4e83-b1b5-be861de20957', 'GET', 'Channel Meld Post Create', 'channel/meld/create/{type}', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('5bb09e68-d4cb-4df8-909b-6980c11980bb', 'GET', 'Channel Meld Post Read', 'channel/meld/{id}', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('7b62eab1-7c12-40c6-83fb-5bf1a95d3f0e', 'POST', 'Social Profile Background Read', 'social/user/current/profile/background', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('5e76f93f-64c0-40fb-95f2-0ed189210834', 'POST', 'Social Profile User Update', 'social/user/current/profile/user', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('d26ee33a-d63c-4bf2-9466-23805765d60d', 'GET', 'Social Profile Background Read', 'social/user/{id}/profile', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('68969553-448c-4e46-a95e-b35ee641f92c', 'GET', 'Social Profile Background Read', 'social/user/current/profile', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('50672951-badb-4f5b-b09f-c41b9865ae1e', 'POST', 'Channel Meld Posts', 'channel/meld/posts', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('b89d81ef-f917-49da-8477-5e2a645db65d', 'DELETE', 'Social User Form Delete', 'social/user/current/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('eecbf551-346c-4cea-a68d-0024850094ad', 'PUT', 'Social User Form Update', 'social/user/current/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('458fb910-fa67-4f83-a8bb-d1de06182855', 'GET', 'Social User Form Read', 'social/user/current/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('6ec7a963-1b4d-44f0-beb1-aea09f45a4f7', 'POST', 'Social User Form Name Validate', 'social/user/current/form/validate', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('8c416ce5-8287-4d3b-a69d-59c67b770b6a', 'POST', 'Social User Form Save', 'social/user/current/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('e391f429-7eee-4a76-b55a-1fc4c8e57608', 'GET', 'Social User Form Read', 'social/user/{id: [0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}}/form', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('62b20c6d-2f4d-48cf-a20b-4554329608c3', 'GET', 'Social Places Read', 'social/user/{id: [0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}}/places', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('ab629ed6-cfca-4823-b38f-4774343ec709', 'GET', 'Social Places Read', 'social/user/current/places', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('5aefc978-a72b-41e3-991f-71b11663400c', 'GET', 'Social Places Create', 'social/user/current/places/create', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('f8e96f87-b0c8-48a5-a560-be0a86438e5e', 'DELETE', 'Social Places Update', 'social/user/current/places', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('57ed9c0e-8794-4828-9bd7-c3892f74f1ca', 'PUT', 'Social Places Update', 'social/user/current/places', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('ad34d378-4d18-4d2c-acc4-fecfe515cde3', 'POST', 'Social Places Save', 'social/user/current/places', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('dd780505-e8bc-49de-9704-c9c9d19f0545', 'PUT', 'Social Find User Read', 'social/people/find', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('60e1c52e-36e1-4c4a-b11e-c94f21debd41', 'GET', 'Social Find User Read Meta', 'social/people/find/meta', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('1a56ff93-474f-4cc1-9c50-6b8038a49f55', 'POST', 'Social Find User Read', 'social/people/find', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('28dcae89-6e26-4585-8975-a08255f90359', 'POST', 'Generic Google Places Geo coding', 'generic/google/place/autocomplete', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('14bebb46-0f85-4a4f-b0bb-b96fc65cd096', 'GET', 'Social', 'social/education/find/meta', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('2d0d59c1-06b2-431e-9174-7b0a7b35eb15', 'POST', 'Social', 'social/education/find', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('75f830b4-5eaa-44c1-ad8a-fdd1d68af22a', 'POST', 'Social Categories Read', 'social/people/categories', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('7ff880fa-a018-4a3c-9e82-1552c1151a60', 'GET', 'Photos Photo Read', 'media/photo/{id}/{fileName}', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('88a93271-5542-4448-a27d-31cca773e074', 'PUT', 'Photos Photo Update', 'media/photo/{id}', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('e2fadebd-8b5b-4b40-a8da-4c82f0b1239c', 'GET', 'Photos Thumbnail Read', 'media/thumbnail/{id}/{fileName}', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('deecbf6a-62fb-4760-963d-5d8644cbbe8b', 'POST', 'Photos Photo Save', 'media/photo', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('30e4f787-e125-4b0f-ae7e-49f736cc0244', 'GET', 'Photos Photo Read', 'media/photo/{id}', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('284ff231-6703-4e6f-85da-0c9dff981372', 'GET', 'Photos Photo Read', 'media/photo/create', now(), now(), 0);
INSERT INTO public.uc_permission (id, method, name, path, created, modified, version) VALUES ('121b1422-86bb-4415-a06b-f130a1455179', 'DELETE', 'Photos Photo Delete', 'media/photo', now(), now(), 0);

--changeset patrick:4


CREATE EXTENSION fuzzystrmatch;