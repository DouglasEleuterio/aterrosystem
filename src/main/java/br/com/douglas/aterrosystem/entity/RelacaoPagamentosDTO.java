package br.com.douglas.aterrosystem.entity;

import lombok.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelacaoPagamentosDTO {
    private String nome;
    private Double valor;
}
