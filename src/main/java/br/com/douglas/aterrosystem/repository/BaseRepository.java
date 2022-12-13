package br.com.douglas.aterrosystem.repository;

import br.com.douglas.aterrosystem.entity.Combo;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<R> extends JpaRepository<R, Long> {

    @ReadOnlyProperty
    @Query(value = "from Combo c where c.transportador.id =:idTransportadora and c.tipoDescarte.id =:idTipoDescarte")
    List<Combo> findAllByTransportadoraIdAndTipoDescarteId(long idTransportadora, long idTipoDescarte);
}
