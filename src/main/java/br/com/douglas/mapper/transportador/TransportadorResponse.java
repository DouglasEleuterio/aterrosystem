package br.com.douglas.mapper.transportador;

import br.com.douglas.mapper.endereco.EnderecoResponse;
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
public class TransportadorResponse {

    private String id;
    private String nome;
    private String razaoSocial;
    private String cnpj;
    private EnderecoResponse endereco;
    private Boolean ativo;
    private String telefone;
    private String email;
}
