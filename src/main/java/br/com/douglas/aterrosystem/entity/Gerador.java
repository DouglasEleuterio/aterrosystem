package br.com.douglas.aterrosystem.entity;

import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gerador {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String nome;
    private String razaoSocial;
    @CNPJ
    @Column(unique = true)
    private String cnpj;
    @CPF
    @Column(unique = true)
    private String cpf;
    @OneToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Endereco retirada;
    @Column
    private String telefone;
    @Column
    @Email
    private String email;
}
