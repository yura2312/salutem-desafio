alter table pedido_hamburguer
    add column preco_venda numeric(10, 2) not null default 0.00;

alter table pedido_bebida
    add column preco_venda numeric(10, 2) not null default 0.00;
