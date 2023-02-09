package br.com.douglas.controller.mapper.gerador;

import br.com.douglas.controller.mapper.endereco.EnderecoRequest;
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
public class GeradorRequest {

    private String nome;
    private String razaoSocial;
    private String cnpj;
    private String cpf;
    private EnderecoRequest retirada;
    private String telefone;
    private String email;
    private Boolean ativo;
}
