package com.lanchonete.salutem.bebida;

import com.lanchonete.salutem.bebida.model.BebidaEntity;
import com.lanchonete.salutem.bebida.model.dto.BebidaResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BebidaMapper{
    BebidaResponse toBebidaResponse(BebidaEntity bebida);
}
