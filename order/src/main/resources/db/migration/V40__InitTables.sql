CREATE TABLE deliver
(
    id   serial primary key,
    uuid varchar not null,
    name varchar not null,
    price decimal not null
);

CREATE TABLE orders
(
    id        serial primary key,
    uuid      varchar not null,
    orders   varchar not null,
    status    varchar not null,
    firstName varchar not null,
    lastName  varchar not null,
    phone     varchar not null,
    email     varchar not null,
    city      varchar not null,
    street    varchar not null,
    number    varchar not null,
    postCode  varchar not null,
    client    varchar,
    deliver   integer REFERENCES "deliver" (id)
);

CREATE TABLE order_items
(
    id   serial primary key,
    uuid varchar not null,
    name varchar not null,
    product varchar not null,
    priceunit decimal not null,
    pricesummary decimal not null,
    quantity bigint DEFAULT 1,
    orders bigint REFERENCES  orders(id)
);

insert into deliver values (1,'XYZ-123','Kurier XYZ',12.50);
insert into deliver values (2,'XYZ-125','Kurier ZYX',60.50);