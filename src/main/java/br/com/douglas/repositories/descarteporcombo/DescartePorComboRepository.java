package br.com.douglas.repositories.descarteporcombo;

import br.com.douglas.entity.entities.temp.DescartePorCombo;
import br.com.douglas.repositories.BaseRepository;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DescartePorComboRepository extends BaseRepository<DescartePorCombo> {

    @ReadOnlyProperty
    @Query(value = "from DescartePorCombo d where d.combo.id =:idCombo")
    List<DescartePorCombo> findAllByComboId(String idCombo, Sort sort);

    Optional<DescartePorCombo> findByCtrId(String id);
}
