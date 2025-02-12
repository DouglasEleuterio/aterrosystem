package br.com.douglas.aterrosystem.repository;

import br.com.douglas.aterrosystem.entity.Aquisicao;
import br.com.douglas.aterrosystem.entity.Combo;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AquisicaoRepository  extends BaseRepository<Aquisicao> {

    @Query("select a from Aquisicao a where a.ativo = true")
    List<Aquisicao> findAllAtivo(Sort sort);

    @ReadOnlyProperty
    @Query(value = "from Aquisicao aq where aq.combo.id =:idCombo")
    Optional<Aquisicao> findByComboId(Long idCombo);
}
