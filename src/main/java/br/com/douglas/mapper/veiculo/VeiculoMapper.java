package br.com.douglas.mapper.veiculo;

import br.com.douglas.mapper.BaseMapper;
import br.com.douglas.entity.entities.temp.Veiculo;
import br.com.douglas.mapper.transportador.TransportadorToVeiculoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VeiculoMapper extends BaseMapper<Veiculo, VeiculoRequest, VeiculoResponse> {

    VeiculoToVeiculoController toVeiculoController(Veiculo veiculo);
    default VeiculoToVeiculoController toVeiculoController2 (Veiculo veiculo){
        var transp = TransportadorToVeiculoResponse.builder()
                .nome(veiculo.getTransportador().getNome())
                .build();
        var veiculoResponse = VeiculoToVeiculoController.builder()
                .ativo(veiculo.getAtivo())
                .placa(veiculo.getPlaca())
                .transportador(transp)
                .build();
        return veiculoResponse;
    }

}
