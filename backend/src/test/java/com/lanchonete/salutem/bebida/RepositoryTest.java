package com.lanchonete.salutem.bebida;

import com.lanchonete.salutem.TestContainerConfiguration;
import com.lanchonete.salutem.bebida.model.BebidaEntity;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfiguration.class)
//@ImportTestcontainers(TestContainerConfiguration.class)
public class RepositoryTest {


    @Autowired
    BebidaRepository repository;

    @Test
    @DisplayName("Deve rodar com sucesso ao salvar uma bebida valida")
    void adicionarBebidaValida() {

        var bebida = BebidaEntity.builder()
                .descricao("Coca-Cola 600ML")
                .precoUnitario(BigDecimal.TWO)
                .contemAcucar(true)
                .build();

        repository.save(bebida);

        assertThat(repository.findAll()).containsExactly(bebida);
    }

    @Test
    @DisplayName("deve lancar excecao ConstraintViolationException ao salvar uma bebida com campo nulo")
    void adicionarBebidaInvalida() {
        var bebida = BebidaEntity.builder()
                .descricao(null)
                .precoUnitario(BigDecimal.TWO)
                .contemAcucar(true)
                .build();

        assertThatThrownBy(() -> repository.save(bebida))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Deve rodar com sucesso ao buscar bebidas por descricao")
    void buscarBebidasPorDescricao() {
        var bebida1 = BebidaEntity.builder()
                .descricao("Coca-Cola 600ml")
                .precoUnitario(BigDecimal.TWO)
                .contemAcucar(true)
                .build();

        var bebida2 = BebidaEntity.builder()
                .descricao("Coca-Cola 2L")
                .precoUnitario(BigDecimal.valueOf(5))
                .contemAcucar(true)
                .build();

        var bebida3 = BebidaEntity.builder()
                .descricao("Pepsi 2L")
                .precoUnitario(BigDecimal.valueOf(5))
                .contemAcucar(true)
                .build();
        var bebidas = List.of(bebida1, bebida2, bebida3);
        repository.saveAll(bebidas);

        assertThat(repository.findByDescricao("Coca"))
                .contains(bebida1, bebida2)
                .doesNotContain(bebida3);
    }

    @Test
    @DisplayName("Deve rodar com sucesso ao deletar uma bebida")
    void deletaBebida() {
        var bebida = BebidaEntity.builder()
                .descricao("Coca-Cola 600")
                .precoUnitario(BigDecimal.TWO)
                .contemAcucar(true)
                .build();

        repository.save(bebida);
        repository.delete(bebida);
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Deve rodar com sucesso ao atualizar uma bebida")
    void atualizaBebida() {
        var bebida = BebidaEntity.builder()
                .descricao("Coca-Cola 600")
                .precoUnitario(BigDecimal.TWO)
                .contemAcucar(true)
                .build();

        repository.save(bebida);
        bebida.setPrecoUnitario(BigDecimal.TEN);
        repository.save(bebida);
        assertThat(repository.findAll()).containsExactly(bebida)
                .extracting(BebidaEntity::getPrecoUnitario)
                .isEqualTo(List.of(BigDecimal.TEN));
    }
}
