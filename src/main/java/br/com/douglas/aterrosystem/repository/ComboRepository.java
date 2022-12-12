package br.com.douglas.aterrosystem.repository;

import br.com.douglas.aterrosystem.entity.Combo;
import br.com.douglas.aterrosystem.entity.TipoDescarte;
import br.com.douglas.aterrosystem.entity.Transportador;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComboRepository extends BaseRepository<Combo> {

    List<Combo> findAllByTransportadorId(Long id, Sort sort);

    @Query("select c from Combo c where c.ativo = true")
    List<Combo> findAllAtivo(Sort sort);
}
