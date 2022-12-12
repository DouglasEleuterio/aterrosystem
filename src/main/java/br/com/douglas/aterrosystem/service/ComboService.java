package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.Combo;
import br.com.douglas.aterrosystem.entity.TipoDescarte;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.BaseRepository;
import br.com.douglas.aterrosystem.repository.ComboRepository;
import br.com.douglas.aterrosystem.repository.TipoDescarteRepository;
import br.com.douglas.aterrosystem.repository.TransportadorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ComboService extends BaseService<Combo> {

    private final TransportadorService transportadorService;
    private final TipoDescarteService tipoDescarteService;

    public ComboService(ComboRepository repository, TransportadorService transportadorService, TipoDescarteService tipoDescarteService) {
        super(repository);
        this.transportadorService = transportadorService;
        this.tipoDescarteService = tipoDescarteService;
    }

    @Override
    public void validate(Combo combo) throws DomainException {
        if(Objects.isNull(combo.getTipoDescarte())){
            throw new DomainException("Tipo de descarte não informado!");
        }
        if(Objects.isNull(combo.getTransportador())){
            throw new DomainException("Transportador não informado!");
        }
    }

    public List<Combo> findAllByTransportadoraId(String transportadoraId, Sort sort) {
        ComboRepository repository = getRepository();
        return repository.findAllByTransportadorId(Long.parseLong(transportadoraId), sort);
    }

    public void preparaCombo(Combo combo) throws DomainException {
        combo.setAtivo(true);
        combo.setTransportador(transportadorService.findById(combo.getTransportador().getId()));
        combo.setTipoDescarte(tipoDescarteService.findById(combo.getTipoDescarte().getId()));
    }
}
