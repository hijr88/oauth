drop table if exists account cascade;

create table account
(
    id             varchar(100) not null,
    password       varchar(100) not null,
    nickname       varchar(50)  not null,
    email          varchar(300) not null,
    zone_code      varchar(30),
    address        varchar(300),
    extra_address  varchar(300),
    detail_address varchar(500),
    create_date    timestamp    not null default now(),
    profile_image  varchar(500),
    enable         boolean      not null default '1',
    role           varchar(50)  not null default 'ROLE_USER',
    platform_type  varchar(100) not null
);

alter table account add
primary key (id, platform_type);

alter table account add constraint UK_account_email
unique (email, platform_type);
