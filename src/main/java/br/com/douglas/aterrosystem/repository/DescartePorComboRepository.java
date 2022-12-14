package br.com.douglas.aterrosystem.repository;

import br.com.douglas.aterrosystem.entity.DescartePorCombo;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescartePorComboRepository extends BaseRepository<DescartePorCombo> {

    @ReadOnlyProperty
    @Query(value = "from descartes_por_combo d where d.combo.id =:idCombo")
    List<DescartePorCombo> findAllByComboId(long idCombo);
}
