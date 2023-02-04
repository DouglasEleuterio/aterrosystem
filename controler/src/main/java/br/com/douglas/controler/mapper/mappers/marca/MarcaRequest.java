package br.com.douglas.controler.mapper.mappers.marca;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarcaRequest {
    private String id;
    private String nome;
}
