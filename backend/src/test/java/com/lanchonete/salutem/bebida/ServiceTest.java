package com.lanchonete.salutem.bebida;


import com.lanchonete.salutem.bebida.model.BebidaEntity;
import com.lanchonete.salutem.bebida.model.dto.BebidaResponse;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void encontrarBebidaPorId(){
        var bebida = BebidaEntity.builder()
                .id(1L)
                .descricao("Coca-Cola 600ML")
                .precoUnitario(BigDecimal.TWO)
                .contemAcucar(true)
                .build();

        var dtoResponse = new BebidaResponse(bebida.getDescricao(), bebida.getPrecoUnitario(), bebida.getContemAcucar());

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
    void excecaoEncontrarBebidaPorId(){

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(1L))
                .isInstanceOf(BebidaNotFoundException.class)
                .hasMessageContaining("Bebida");
    } 

}
