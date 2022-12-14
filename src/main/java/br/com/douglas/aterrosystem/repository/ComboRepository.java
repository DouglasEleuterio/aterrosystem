package br.com.douglas.aterrosystem.repository;

import br.com.douglas.aterrosystem.entity.Combo;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComboRepository extends BaseRepository<Combo> {

    List<Combo> findAllByTransportadorId(Long id, Sort sort);

    @Query("select c from Combo c where c.ativo = true")
    List<Combo> findAllAtivo(Sort sort);

    @Query("select sum (c.saldo) from Combo c where c.ativo = true and c.transportador.id = :idTransportadora and c.tipoDescarte.id = :idTipoDescarte")
    Integer retornaQuantidadeDeComboPorCategoria(Long idTransportadora, Long idTipoDescarte);

    @ReadOnlyProperty
    @Query(value = "from Combo c where c.transportador.id =:idTransportadora and c.tipoDescarte.id =:idTipoDescarte and c.saldo > 0")
    List<Combo> findAllByTransportadoraIdAndTipoDescarteId(long idTransportadora, long idTipoDescarte);
}
