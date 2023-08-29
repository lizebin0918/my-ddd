create table "order"
(
    order_id         bigint not null
        constraint "PRIMARY_order"
            primary key,
    order_status     smallint  default '0':: smallint,
    currency         text,
    exchange_rate    numeric,
    total_should_pay numeric,
    total_actual_pay numeric,
    add_time         timestamp default CURRENT_TIMESTAMP,
    update_time      timestamp with time zone
);

create table order_detail
(
    id           bigint
        constraint "PRIMARY_order_detail"
            primary key,
    order_id     integer,
    sku_id       integer,
    buy_num      integer   default 1,
    order_status smallint not null,
    price        numeric,
    add_time     timestamp default CURRENT_TIMESTAMP,
    update_time  timestamp with time zone
);