package br.com.douglas.repository.repositories.combo;

import br.com.douglas.entity.entities.temp.Combo;
import br.com.douglas.repository.BaseRepository;
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
    Integer retornaQuantidadeDeComboPorCategoria(String idTransportadora, String idTipoDescarte);

    @ReadOnlyProperty
    @Query(value = "from Combo c where c.transportador.id =:idTransportadora and c.tipoDescarte.id =:idTipoDescarte and c.saldo > 0")
    List<Combo> findAllByTransportadoraIdAndTipoDescarteId(String idTransportadora, String idTipoDescarte);
}
