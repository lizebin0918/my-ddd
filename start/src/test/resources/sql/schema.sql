create table "order"
(
    order_id         bigint                              not null
        constraint "PRIMARY_order"
            primary key,
    order_status     text                                not null,
    currency         text,
    exchange_rate    numeric,
    total_should_pay numeric,
    total_actual_pay numeric,
    email            text,
    phone_number     text,
    first_name       text,
    last_name        text,
    address_line1    text,
    address_line2    text,
    country          text,
    version          int                                 not null,
    add_time         timestamp default CURRENT_TIMESTAMP not null,
    update_time      timestamp default CURRENT_TIMESTAMP not null
);

create sequence order_order_id_seq;

alter table "order"
    alter column order_id set default nextval('order_order_id_seq'::regclass);


create table order_detail
(
    id           bigint
        constraint "PRIMARY_order_detail"
            primary key,
    order_id     bigint                              not null,
    sku_id       integer,
    order_status text                                not null,
    price        numeric,
    add_time     timestamp default CURRENT_TIMESTAMP not null,
    update_time  timestamp default CURRENT_TIMESTAMP not null
);

create sequence order_detail_id_seq;

alter table order_detail
    alter column id set default nextval('order_detail_id_seq'::regclass);

create table domain_event
(
    id           bigint                               not null
        primary key,
    topic        varchar(255)                         not null,
    tag          varchar(255)                         not null,
    biz_id       varchar(255)                         not null,
    sharding_key varchar(255)                         not null,
    content      json                                 not null,
    sent         boolean default false                 not null,
    msg_id       varchar(255)                         null,
    add_time     timestamp   default CURRENT_TIMESTAMP not null,
    update_time  timestamp   default CURRENT_TIMESTAMP not null,
    constraint uk_topic_tag_biz_id
        unique (topic, tag, biz_id)
);

create index domain_event_sharding_key_index on domain_event (sharding_key);

create sequence domain_event_id_seq;

alter table domain_event alter column id set default nextval('domain_event_id_seq'::regclass);