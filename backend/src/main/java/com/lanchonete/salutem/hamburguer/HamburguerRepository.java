package com.lanchonete.salutem.hamburguer;

import com.lanchonete.salutem.hamburguer.model.HamburguerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HamburguerRepository extends JpaRepository<HamburguerEntity, Long> {

    @NativeQuery("""
    select *
    from hamburguer h
    where h.descricao ilike '%' || :descricao || '%'
""")
    List<HamburguerEntity> findByDescricao(String descricao);
}
