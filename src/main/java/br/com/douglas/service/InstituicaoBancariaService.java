package br.com.douglas.service;

import br.com.douglas.entity.entities.temp.InstituicaoBancaria;
import br.com.douglas.repositories.BaseRepository;
import br.com.douglas.service.impls.BaseService;
import org.springframework.stereotype.Service;

@Service
public class InstituicaoBancariaService extends BaseService<InstituicaoBancaria> {

    protected InstituicaoBancariaService(BaseRepository<InstituicaoBancaria> repository) {
        super(repository);
    }
}
