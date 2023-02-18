package br.com.douglas.service.descartepocombo;

import br.com.douglas.entity.entities.temp.DescartePorCombo;
import br.com.douglas.repositories.descarteporcombo.DescartePorComboRepository;
import br.com.douglas.service.impls.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class DescartePorComboService extends BaseService<DescartePorCombo> {

    private final DescartePorComboRepository repository;
    public DescartePorComboService(DescartePorComboRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<DescartePorCombo> findAllByCombo(String comboId, Sort sort) {
        return repository.findAllByComboId(comboId, sort);
    }
}
