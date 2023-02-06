package br.com.douglas.repositories.descarteporcombo;

import br.com.douglas.entity.entities.temp.DescartePorCombo;
import br.com.douglas.repositories.BaseRepository;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescartePorComboRepository extends BaseRepository<DescartePorCombo> {

    @ReadOnlyProperty
    @Query(value = "from DescartePorCombo d where d.combo.id =:idCombo")
    List<DescartePorCombo> findAllByComboId(long idCombo);
}
