package br.com.douglas.mapper;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdAndNameResponse {

    private String id;
    private String nome;
}
