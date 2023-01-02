package br.com.douglas.aterrosystem.repository;

import br.com.douglas.aterrosystem.entity.TipoDescarte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoDescarteRepository extends BaseRepository<TipoDescarte> {

    @Query("select t from TipoDescarte t where t.ativo = true")
    List<TipoDescarte> findAllAtivo(Sort sort);

    @Query(value = "from TipoDescarte td where td.nome like %:nome% and td.ativo =:ativo")
    <T> Page<T> findAllWithParams(Pageable pageable, String nome, Boolean ativo);

    @Query(value = "from TipoDescarte td where td.ativo =:ativo ")
    Page<TipoDescarte> findAllByAtivo(Pageable pageable, Boolean ativo);
}
