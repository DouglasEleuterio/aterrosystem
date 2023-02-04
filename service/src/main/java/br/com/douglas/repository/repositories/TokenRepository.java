package br.com.douglas.repository.repositories;

import br.com.douglas.entity.entities.Token;
import br.com.douglas.repository.BaseRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends BaseRepository<Token> {
    @NonNull
    Optional<Token> findByTokenEquals(@NonNull String token);
}
