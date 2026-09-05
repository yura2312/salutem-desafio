package com.lanchonete.salutem.pedido;

import com.lanchonete.salutem.pedido.model.entity.PedidoEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {


    @EntityGraph(attributePaths = {"hamburgueres.hamburguer", "bebidas.bebida"})
    List<PedidoEntity> findAll();
}
