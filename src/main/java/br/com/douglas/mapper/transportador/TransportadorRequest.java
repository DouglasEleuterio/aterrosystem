package br.com.douglas.mapper.transportador;

import br.com.douglas.mapper.endereco.EnderecoRequest;
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
public class TransportadorRequest {

    private String nome;
    private String razaoSocial;
    private String cnpj;
    private EnderecoRequest endereco;
    private Boolean ativo;
    private String telefone;
    private String email;
}
