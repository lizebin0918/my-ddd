create table "order"
(
    order_id         bigint not null
        constraint "PRIMARY_order"
            primary key,
    order_status     text   not null,
    currency         text,
    exchange_rate    numeric,
    total_should_pay numeric,
    total_actual_pay numeric,
    add_time         timestamp default CURRENT_TIMESTAMP not null,
    update_time      timestamp not null
);

create sequence order_order_id_seq;

alter table "order"
    alter column order_id set default nextval('order_order_id_seq'::regclass);


create table order_detail
(
    id           bigint
        constraint "PRIMARY_order_detail"
            primary key,
    order_id     bigint not null,
    sku_id       integer,
    order_status text not null,
    price        numeric,
    add_time     timestamp default CURRENT_TIMESTAMP not null,
    update_time  timestamp not null
);

create sequence order_detail_id_seq;

alter table order_detail alter column id set default nextval('order_detail_id_seq'::regclass);

