--liquibase formatted sql

--changeset thiago:initial-1 dbms:mysql
create table if not exists vereador
(
    id            varchar(255) not null primary key,
    nome          varchar(255) null,
    created_date  timestamp    null,
    modified_time timestamp    null
);