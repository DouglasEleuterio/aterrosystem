package br.com.douglas.mapper.gerador;

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
public class GeradorResponse {

    private String id;
    private String nome;
    private String razaoSocial;
    private String cnpj;
    private String cpf;
    private EnderecoResponse retirada;
    private String telefone;
    private String email;
    private Boolean ativo;
}
