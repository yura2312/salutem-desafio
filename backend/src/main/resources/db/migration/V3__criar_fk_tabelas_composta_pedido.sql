alter table pedido_hamburguer
     add constraint fk_hamburguer
    foreign key (id_hamburguer) references hamburguer(id);

alter table pedido_hamburguer
    add constraint fk_pedido
    foreign key (id_pedido) references pedido(id);

alter table pedido_bebida
    add constraint fk_bebida
    foreign key (id_bebida) references bebida(id);

alter table pedido_bebida
    add constraint fk_pedido
    foreign key (id_pedido) references pedido(id);