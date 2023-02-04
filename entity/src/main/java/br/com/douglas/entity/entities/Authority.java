package br.com.douglas.entity.entities;

import br.com.douglas.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "AUT_AUTHORITY")
public class Authority extends BaseEntity {

   @Id
   @Column(name = "AUT_ID", length = 36)
   private String id;

   @Column(name = "AUT_DS_NAME", length = 50, unique = true)
   @NotNull
   private String name;
}
