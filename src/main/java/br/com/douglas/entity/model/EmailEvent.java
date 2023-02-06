package br.com.douglas.entity.model;

import br.com.douglas.entity.enums.Templates;

import javax.validation.constraints.NotNull;
import java.util.Map;

public record EmailEvent(@NotNull String subject,
                         @NotNull String to,
                         @NotNull String from,
                         @NotNull Templates template,
                         Map<String, Object> params) {
}
