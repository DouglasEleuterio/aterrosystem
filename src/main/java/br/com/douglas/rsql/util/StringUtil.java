package br.com.douglas.rsql.util;

import lombok.NonNull;

import java.text.Normalizer;

public class StringUtil {

    public static String ignoreAccents(@NonNull String value) {
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}+", "");
    }
}
