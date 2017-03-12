create table "order"(
  order_id                   varchar(36),
  subtotal                   decimal(10,2),
  tax                        decimal(10,2),
  total                      decimal(10,2),
  user_id                    varchar(25),
  shipping_address_to        varchar(100),
  shipping_address_line_one  varchar(100),
  shipping_address_line_two  varchar(100),
  shipping_address_city      varchar(100),
  shipping_address_state     varchar(100),
  shipping_address_zip       varchar(100)
);

create table order_item(
  order_id  varchar(36),
  sku       varchar(20),
  name      varchar(100),
  price     decimal(10,2),
  quantity  int
);

alter table order_item add foreign key (order_id) references "order"(order_id);

commit;