
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
        image oid,
        lastModified timestamp,
        thumbnail oid,
        primary key (id)
    );

    create table cn_post (
       id uuid not null,
        version int4 not null,
        created timestamp,
        fileName varchar(255),
        header varchar(255),
        text text,
        image_id uuid,
        user_id uuid,
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
        city varchar(255),
        country varchar(255),
        endDate date,
        startDate date,
        state varchar(255),
        street varchar(255),
        zipCode varchar(255),
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
        startDate date,
        title varchar(255),
        primary key (id)
    );

    create table so_education (
       id uuid not null,
        version int4 not null,
        user_id uuid,
        primary key (id)
    );

    create table so_education_so_school (
       Education_id uuid not null,
        schools_id uuid not null,
        schools_ORDER int4 not null,
        primary key (Education_id, schools_ORDER)
    );

    create table so_personalContact (
       id uuid not null,
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
        endDate date,
        name varchar(255),
        startDate date,
        primary key (id)
    );

    create table so_workHistory (
       id uuid not null,
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

    create table uc_identity (
       DTYPE varchar(31) not null,
        id uuid not null,
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
        image oid,
        lastModified timestamp,
        thumbnail oid,
        user_id uuid,
        primary key (id)
    );

    create table uc_permission (
       id uuid not null,
        method varchar(255),
        name varchar(255),
        path varchar(255),
        primary key (id)
    );

    create table uc_role (
       id uuid not null,
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

    alter table so_education_so_school 
       drop constraint if exists UK_2w85yhb3ug9o7t97ev0tp5dfh;

    alter table so_education_so_school 
       add constraint UK_2w85yhb3ug9o7t97ev0tp5dfh unique (schools_id);

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
       add constraint FKowu29qk4cifb66sp3ffpox8o1 
       foreign key (image_id) 
       references cn_image;

    alter table cn_post 
       add constraint FKi9wbf17qftmf62ncobcjn2crv 
       foreign key (user_id) 
       references uc_identity;

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

    alter table so_education_so_school 
       add constraint FKmg9elhfp87mk41j3t8nwvjeq6 
       foreign key (schools_id) 
       references so_school;

    alter table so_education_so_school 
       add constraint FKtnonblc6bmcpfrfmgk6xmlbyv 
       foreign key (Education_id) 
       references so_education;

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
