INSERT INTO "order" (order_id, order_status, currency, exchange_rate, total_should_pay, total_actual_pay, email,
                     phone_number, first_name, last_name, address_line1, address_line2, country, version,
                     add_time, update_time)
VALUES (1, 'WAIT_PAY', 'CNY', 1, 88, 88, '123456@qq.com', '13800000000', 'first', 'last', 'line1', 'line2', 'CHINA',
        1, DEFAULT, DEFAULT);

INSERT INTO order_detail (id, order_id, sku_id, order_status, price, add_time, update_time)
VALUES (1, 1, 1, 'WAIT_PAY', 1, DEFAULT, DEFAULT);
INSERT INTO order_detail (id, order_id, sku_id, order_status, price, add_time, update_time)
VALUES (2, 1, 2, 'WAIT_PAY', 10, DEFAULT, DEFAULT);