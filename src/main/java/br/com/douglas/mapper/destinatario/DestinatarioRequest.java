package br.com.douglas.mapper.destinatario;

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
public class DestinatarioRequest {

    private String nome;
    private String razaoSocial;
    private String cnpj;
    private EnderecoRequest enderecoRecebimento;
    private String telefone;
    private String email;
    private Boolean ativo;
}
