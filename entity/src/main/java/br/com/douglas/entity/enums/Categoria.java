package br.com.douglas.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Categoria {

    SEDAN(1), HACTHBACK(2), SUV(3), PICKUP(4), VAN(5), COUPE(6), ESPORTIVO(7), FURGON(8), OUTROS(9);
    private Integer codigo;
}
