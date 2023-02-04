package br.com.douglas.entity.entities;

import br.com.douglas.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "TKN_TOKEN")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token extends BaseEntity {

    @Id
    @Column(length = 36)
    private String id;

    @OneToOne
    @JoinColumn(name = "USR_ID")
    private User user;

    @Column(name = "EXPIRATION")
    private LocalDateTime expiration;

    @Column(name = "TOKEN")
    private String token;
}
