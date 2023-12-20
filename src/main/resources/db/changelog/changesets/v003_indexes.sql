--liquibase formatted sql

--changeset thiago:indexes-1 dbms:mysql
create index idx_despesa_nome_categoria on despesa (nome_categoria);

--changeset thiago:indexes-2 dbms:mysql
create index idx_fornecedor_nome on fornecedor (nome);

--changeset thiago:indexes-3 dbms:mysql
create index idx_item_reembolso_id_vereador on item_reembolso (id_vereador);
create index idx_item_reembolso_cnpj on item_reembolso (cnpj);
create index idx_item_reembolso_ano_mes on item_reembolso (ano, mes);
create index idx_item_reembolso_id_despesa on item_reembolso (id_despesa);
