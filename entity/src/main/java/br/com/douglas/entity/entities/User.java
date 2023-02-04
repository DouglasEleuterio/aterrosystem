package br.com.douglas.entity.entities;

import br.com.douglas.entity.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USR_USER")
public class User extends BaseEntity {

    @Id
    @Column(name = "USR_ID", unique = true, nullable = false)
    private String id;

    @Column(name = "USR_DS_USERNAME", length = 50, unique = true, nullable = false)
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "USR_DS_PASSWORD", length = 255, nullable = false)
    @Size(min = 4, max = 255)
    private String password;

    @Column(name = "USR_DS_FIRSTNAME", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    private String firstname;

    @Column(name = "USR_DS_LASTNAME", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    private String lastname;

    @Column(name = "USR_DS_EMAIL", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    private String email;

    @Column(name = "USR_ST_ACTIVATED", nullable = false)
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "USA_USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USR_ID", referencedColumnName = "USR_ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUT_ID", referencedColumnName = "AUT_ID")})
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();
}