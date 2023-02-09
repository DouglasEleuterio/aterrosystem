package br.com.douglas.controller.mapper.motorista;

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
public class MotoristaRequest {

    private String nome;
    private String telefone;
    private String cnh;
}
