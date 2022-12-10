package br.com.douglas.aterrosystem.repository;

import br.com.douglas.aterrosystem.entity.TipoDescarte;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoDescarteRepository extends JpaRepository<TipoDescarte, Long> {

    @Query("select t from TipoDescarte t where t.ativo = true")
    List<TipoDescarte> findAllAtivo(Sort sort);
}
