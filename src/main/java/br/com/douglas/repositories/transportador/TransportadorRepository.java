package br.com.douglas.repositories.transportador;

import br.com.douglas.entity.entities.temp.Transportador;
import br.com.douglas.repositories.BaseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportadorRepository extends BaseRepository<Transportador> {

    @Query("select t from Transportador t where t.ativo = true")
    List<Transportador> findAllAtivo(Sort sort);
}
