package com.lanchonete.salutem.bebida;

import com.lanchonete.salutem.bebida.model.BebidaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import java.util.List;

public interface BebidaRepository extends JpaRepository<BebidaEntity, Long> {

    @NativeQuery("""
    select *
    from bebida b
    where b.descricao ilike '%' || :descricao || '%'
""")
    List<BebidaEntity> findByDescricao(String descricao);
    //Mesmo que List<BebidaEntity>findByDescricaoContainingIgnoreCase, mas prefiro o nome do metodo curto
    //JPQL seria "SELECT b FROM BebidaEntity b WHERE LOWER(b.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))""
}
