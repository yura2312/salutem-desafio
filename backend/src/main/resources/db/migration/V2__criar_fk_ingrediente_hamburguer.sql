alter table ingrediente_hamburguer
    add constraint fk_hamburguer
        foreign key (id_hamburguer) references hamburguer (id);

alter table ingrediente_hamburguer
    add constraint fk_ingrediente
        foreign key (id_ingrediente) references ingrediente (id)