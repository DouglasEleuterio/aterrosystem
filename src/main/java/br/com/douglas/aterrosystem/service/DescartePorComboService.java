package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.DescartePorCombo;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.DescartePorComboRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescartePorComboService extends BaseService<DescartePorCombo> {

    public DescartePorComboService(DescartePorComboRepository repository) {
        super(repository);
    }

    @Override
    void validate(DescartePorCombo descartePorCombo) throws DomainException {
    }

    public List<DescartePorCombo> findAllByCombo(String comboId) {
        long idCombo = Long.parseLong(comboId);
        return ((DescartePorComboRepository) getRepository()).findAllByComboId(idCombo);
    }
}
