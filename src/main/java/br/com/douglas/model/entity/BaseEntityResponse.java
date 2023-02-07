package br.com.douglas.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="BaseEntityReesponse", description="Modelo padrão para representar o id de um determinado registro.")
public class BaseEntityResponse {

	private String id;
}
