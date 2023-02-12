package br.com.douglas.mapper.combo;


import br.com.douglas.model.entity.BaseEntityRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComboAquisicaoRequest {

    private String id;
    private BaseEntityRequest transportador;
    private BaseEntityRequest tipoDescarte;
    private Boolean ativo;
    private Integer saldo;
}
