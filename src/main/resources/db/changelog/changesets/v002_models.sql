--liquibase formatted sql

--changeset thiago:models-1 dbms:mysql
create table if not exists despesa
(
    id              varchar(255) not null primary key,
    nome_categoria   varchar(129) not null,
    created_date    timestamp    null,
    modified_time   timestamp    null
);

--changeset thiago:models-2 dbms:mysql
create table if not exists fornecedor
(
    cnpj                varchar(255) not null primary key,
    nome                varchar(129) not null,
    created_date        timestamp    null,
    modified_time       timestamp    null
);

--changeset thiago:models-3 dbms:mysql
create table if not exists item_reembolso
(
    id                  varchar(255) not null primary key,
    id_vereador         varchar(255) not null,
    nome_vereador       varchar(255) not null,
    id_centro_custo     double not null,
    departamento        varchar(255) not null,
    tipo_departamento   int not null,
    ano                 int not null,
    mes                 int not null,
    nome_despesa        varchar(128) not null,
    id_despesa          varchar(255) not null,
    cnpj                varchar(30) not null,
    fornecedor          varchar(255) not null,
    valor               double not null,
    created_date        timestamp    null,
    modified_time       timestamp    null,
    constraint FK_ItemReembolso_Vereador foreign key (id_vereador) references vereador (id),
    constraint FK_ItemReembolso_Despesa foreign key (id_despesa) references despesa (id),
    constraint FK_ItemReembolso_Fornecedor foreign key (cnpj) references fornecedor (cnpj)
);

--changeset thiago:models-4 dbms:mysql
create table if not exists autenticacao
(
    token                   varchar(255) not null primary key,
    data_expiracao          timestamp not null,
    role                    varchar(64) not null,
    email                   varchar(255) null,
    created_date            timestamp    null,
    modified_time           timestamp    null
);

