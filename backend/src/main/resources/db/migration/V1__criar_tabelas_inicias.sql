create table bebida
(
    id         bigserial primary key,
    descricao      text           not null,
    preco_unitario numeric(10, 2) not null,
    contem_acucar  boolean        not null
);

create table ingrediente
(
    id         bigserial primary key,
    descricao      text           not null,
    preco_unitario numeric(10, 2) not null,
    adicional      boolean        not null
);

create table hamburguer
(
    id    bigserial primary key,
    descricao text           not null,
    valor     numeric(10, 2) not null
);

create table ingrediente_hamburguer
(
    id_hamburguer  bigint not null,
    id_ingrediente bigint not null,
    primary key (id_hamburguer, id_ingrediente)
);

create table pedido
(
    id           bigserial primary key,
    data             date not null,
    descricao        text not null,
    cliente_nome     text not null,
    cliente_endereco text not null,
    cliente_telefone text not null
);

create table pedido_bebida
(
    id_pedido bigint not null,
    id_bebida bigint not null,
    primary key (id_pedido, id_bebida)
);

create table pedido_hamburguer
(
    id_pedido    bigint not null,
    id_hamburguer bigint not null,
    primary key (id_pedido, id_hamburguer)
);