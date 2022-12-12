package br.com.douglas.aterrosystem.repository;

import br.com.douglas.aterrosystem.entity.Aquisicao;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AquisicaoRepository  extends BaseRepository<Aquisicao> {

    @Query("select a from Aquisicao a where a.ativo = true")
    List<Aquisicao> findAllAtivo(Sort sort);
}
