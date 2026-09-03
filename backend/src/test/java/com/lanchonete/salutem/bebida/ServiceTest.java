package com.lanchonete.salutem.bebida;


import com.lanchonete.salutem.bebida.model.BebidaEntity;
import com.lanchonete.salutem.bebida.model.dto.BebidaRequest;
import com.lanchonete.salutem.bebida.model.dto.BebidaResponse;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    BebidaRepository repository;

    @Mock
    BebidaMapper mapper;

    @InjectMocks
    BebidaService service;

    @Test
    @DisplayName("Deve encontrar uma bebida com id valido")
    void encontrarBebidaPorId() {
        var bebida = BebidaEntity.builder()
                .id(1L)
                .descricao("Coca-Cola 600ML")
                .precoUnitario(BigDecimal.TWO)
                .contemAcucar(true)
                .build();

        var dtoResponse = new BebidaResponse(bebida.getId(),bebida.getDescricao(), bebida.getPrecoUnitario(), bebida.getContemAcucar());

        when(repository.findById(1L)).thenReturn(Optional.of(bebida));
        when(mapper.toBebidaResponse(bebida)).thenReturn(dtoResponse);

        var serviceResponse = service.findById(1L);

        assertThat(serviceResponse)
                .isNotNull()
                .isEqualTo(dtoResponse);

        verify(repository).findById(1L);
    }


    @Test
    @DisplayName("Deve lançar exceção BebidaNotFoundException quando não encontrar bebida")
    void excecaoEncontrarBebidaPorId() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(1L))
                .isInstanceOf(BebidaNotFoundException.class)
                .hasMessageContaining("Bebida");
    }

    @Test
    @DisplayName("Deve encontrar todas as bebidas")
    void encontrarTodasBebidas() {
        var bebida1 = BebidaEntity.builder()
                .id(1L)
                .descricao("Coca-Cola 600ML")
                .precoUnitario(BigDecimal.TWO)
                .contemAcucar(true)
                .build();

        var bebida2 = BebidaEntity.builder()
                .id(2L)
                .descricao("Pepsi Black 300ML")
                .precoUnitario(BigDecimal.ONE)
                .contemAcucar(false)
                .build();

        var dtoResponse1 = new BebidaResponse(bebida1.getId(),bebida1.getDescricao(), bebida1.getPrecoUnitario(), bebida1.getContemAcucar());
        var dtoResponse2 = new BebidaResponse(bebida2.getId(),bebida2.getDescricao(), bebida2.getPrecoUnitario(), bebida2.getContemAcucar());

        when(repository.findAll()).thenReturn(java.util.List.of(bebida1, bebida2));
        when(mapper.toBebidaResponse(bebida1)).thenReturn(dtoResponse1);
        when(mapper.toBebidaResponse(bebida2)).thenReturn(dtoResponse2);

        var serviceResponse = service.findAll();

        assertThat(serviceResponse)
                .isNotNull()
                .hasSize(2)
                .containsExactly(dtoResponse1, dtoResponse2);

        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve salvar uma bebida valida")
    void salvarBebida() {
        var dtoRequest = new BebidaRequest("Coca-Cola 600ML", BigDecimal.TWO, true);

        var bebidaSalva = BebidaEntity.builder()
                .id(1L)
                .descricao("Coca-Cola 600ML")
                .precoUnitario(BigDecimal.TWO)
                .contemAcucar(true)
                .build();

        var dtoResponse = new BebidaResponse(1L, "Coca-Cola 600ML", BigDecimal.TWO, true);

        when(mapper.toBebidaEntity(dtoRequest)).thenReturn(bebidaSalva);
        when(repository.save(bebidaSalva)).thenReturn(bebidaSalva);
        when(mapper.toBebidaResponse(bebidaSalva)).thenReturn(dtoResponse);

        var serviceResponse = service.save(dtoRequest);

        assertThat(serviceResponse)
                .isNotNull()
                .isEqualTo(dtoResponse);

        verify(mapper).toBebidaEntity(dtoRequest);
        verify(repository).save(bebidaSalva);
        verify(mapper).toBebidaResponse(bebidaSalva);
    }

}
