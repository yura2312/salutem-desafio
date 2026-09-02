package com.lanchonete.salutem.ingredientes;

import com.lanchonete.salutem.ingredientes.model.IngredienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredienteRepository extends JpaRepository<IngredienteEntity, Long> {


    @NativeQuery("""
    select *
    from ingrediente i
    where i.descricao ilike '%' || :descricao || '%'
""")
    List<IngredienteEntity> findByDescricao(String descricao);
}
