package br.com.douglas.repository.repositories.authority;

import br.com.douglas.entity.entities.Authority;
import br.com.douglas.repository.BaseRepository;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends BaseRepository<Authority> {

    @ReadOnlyProperty
    @Query(value = "from Authority a where a.name =:name")
    Optional<Authority> findFromName(String name);
}
