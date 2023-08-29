create table "order"
(
    order_id         bigint not null
        constraint "PRIMARY_order"
            primary key,
    order_status     text not null,
    currency         text,
    exchange_rate    numeric,
    total_should_pay numeric,
    total_actual_pay numeric,
    add_time         timestamp default CURRENT_TIMESTAMP,
    update_time      timestamp
);

create table order_detail
(
    id           bigint
        constraint "PRIMARY_order_detail"
            primary key,
    order_id     integer,
    sku_id       integer,
    order_status text not null,
    price        numeric,
    add_time     timestamp default CURRENT_TIMESTAMP,
    update_time  timestamp
);