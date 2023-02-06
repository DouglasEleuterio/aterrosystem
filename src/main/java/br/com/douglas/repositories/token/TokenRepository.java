package br.com.douglas.repositories.token;

import br.com.douglas.entity.entities.Token;
import br.com.douglas.repositories.BaseRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends BaseRepository<Token> {
    @NonNull
    Optional<Token> findByKeyEquals(@NonNull String key);
}
