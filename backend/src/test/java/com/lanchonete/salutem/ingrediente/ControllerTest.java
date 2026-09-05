package com.lanchonete.salutem.ingrediente;

import com.lanchonete.salutem.ingredientes.controller.IngredienteController;
import com.lanchonete.salutem.ingredientes.IngredienteNotFoundException;
import com.lanchonete.salutem.ingredientes.IngredienteService;
import com.lanchonete.salutem.ingredientes.model.dto.IngredienteRequest;
import com.lanchonete.salutem.ingredientes.model.dto.IngredienteResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(IngredienteController.class)
public class ControllerTest {

    @Autowired
    MockMvcTester mockMvcTester;

    @MockitoBean
    IngredienteService service;

    @Test
    @DisplayName("Deve retornar 200 e todos os ingredientes")
    void retornaOkTodosOsIngredientes() {

        var response = List.of(
                new IngredienteResponse(1L, "Alface", BigDecimal.TWO, false),
                new IngredienteResponse(2L, "Hamburguer 200g", BigDecimal.TEN, true)
        );

        when(service.findAll()).thenReturn(response);

        assertThat(mockMvcTester.get().uri("/api/ingrediente/all"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.length()")
                .isEqualTo(2);
    }

    @Test
    @DisplayName("Deve retornar 200 e o ingrediente quando o id existir")
    void retornaOkIngredientePorId() {

        var response = new IngredienteResponse(1L, "Alface", BigDecimal.TWO, false);

        when(service.findById(1L)).thenReturn(response);

        assertThat(mockMvcTester.get().uri("/api/ingrediente/1"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.descricao")
                .isEqualTo("Alface");

    }

    @Test
    @DisplayName("Deve retornar 404 e titulo de ingrediente não encontrado quando o id não existir")
    void retornaNotFoundIngredienteExceptionPorId() {

        when(service.findById(1L)).thenThrow(new IngredienteNotFoundException("Ingrediente de id: 1 não encontrado"));

        assertThat(mockMvcTester.get().uri("/api/ingrediente/1"))
                .hasStatus(HttpStatus.NOT_FOUND)
                .bodyJson()
                .extractingPath("$.title")
                .isEqualTo("Ingrediente não encontrado");
    }

    @Test
    @DisplayName("Deve retornar 200 com os ingredientes com a descrição informada")
    void retornaOkIngredientePorDescricao() {

        var response = List.of(
                new IngredienteResponse(1L, "Hamburguer 100g", BigDecimal.TWO, false),
                new IngredienteResponse(2L, "Hamburguer 200g", BigDecimal.TEN, true)
        );

        when(service.findByDescricao("hamb")).thenReturn(response);

        var httpResponse = mockMvcTester.get()
                .uri("/api/ingrediente")
                .param("descricao", "hamb")
                .exchange();

        assertThat(httpResponse)
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.length()")
                .isEqualTo(2);

        assertThat(httpResponse)
                .bodyJson()
                .extractingPath("$[0].descricao")
                .isEqualTo("Hamburguer 100g");
    }

    @Test
    @DisplayName("Deve retornar 200 e lista vazia quando nao encontrar ingredientes com a descrição informada")
    void retornaOkIngredienteListaVazia() {

        when(service.findByDescricao("abc")).thenReturn(List.of());

        assertThat(mockMvcTester.get().uri("/api/ingrediente")
                .param("descricao", "abc"))
                .hasStatusOk()
                .bodyJson()
                .isEqualTo("[]");
    }

    @Test
    @DisplayName("Deve retornar 200 ingrediente criado")
    void retornaOkIngredienteCriado() {

        var request = new IngredienteRequest("Alface", BigDecimal.valueOf(1.99), false);
        var response = new IngredienteResponse(1L, "Alface", BigDecimal.valueOf(1.99), false);

        String json = """
                {
                    "descricao": "Alface",
                    "precoUnitario": 1.99,
                    "adicional": false
                }
                """;

        when(service.save(request)).thenReturn(response);

        assertThat(mockMvcTester.post()
                .uri("/api/ingrediente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .exchange())
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .extractingPath("$.id")
                .isEqualTo(1);
    }

    @Test
    @DisplayName("Deve retornar 204 quando deletar ingrediente")
    void retornaOkQuandoDeletarIngrediente() {

        doNothing().when(service).delete(1L);

        assertThat(mockMvcTester.delete()
                .uri("/api/ingrediente/1")
                .exchange())
                .hasStatus(HttpStatus.NO_CONTENT);
    }

}
