package br.com.douglas.repositories.user;

import br.com.douglas.entity.entities.User;
import br.com.douglas.repositories.BaseRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {
    @Query("select u from User u where u.username =:username")
    Optional<User> findByUsername(@NonNull String username);
    Optional<User> findFirstByEmailLikeIgnoreCase(@NonNull String email);
    Optional<User> findFirstByUsernameLikeIgnoreCase(@NonNull String username);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

}
