package com.lanchonete.salutem.pedido;

import com.lanchonete.salutem.pedido.model.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    public PedidoEntity findByClienteNome (String clienteNome);
}
