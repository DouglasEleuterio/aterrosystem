package br.com.douglas.entity.entities;

import br.com.douglas.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TKN_TOKEN")
public class Token extends BaseEntity {

    @Id
    @Column(name = "TKN_ID", length = 36)
    private String id;

    @OneToOne
    @JoinColumn(name = "USR_ID", nullable = false)
    private User user;

    @Column(name = "TKN_DH_EXPIRATION", nullable = false)
    private LocalDateTime expiration;

    @Column(name = "TKN_DS_KEY", nullable = false, unique = true)
    private String key;
}
