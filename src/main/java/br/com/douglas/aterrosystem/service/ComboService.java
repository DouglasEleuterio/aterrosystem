package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.Combo;
import br.com.douglas.aterrosystem.entity.TipoDescarte;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.BaseRepository;
import br.com.douglas.aterrosystem.repository.ComboRepository;
import br.com.douglas.aterrosystem.repository.TipoDescarteRepository;
import br.com.douglas.aterrosystem.repository.TransportadorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

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

    public Integer retornaQuantidadeDeComboPorCategoria(Long idTransportadora, Long idTipoDescarte){
        ComboRepository repository = getRepository();
        return repository.retornaQuantidadeDeComboPorCategoria(idTransportadora, idTipoDescarte);
    }

    public List<Combo> retornaComboParaConsumirSaldoNoDescarte(int quantidadeDesejada, long idTransportadora, long idTipoDescarte){
        List<Combo> combosParaBaixa = new ArrayList<>();
        List<Combo> combos = ((ComboRepository) getRepository()).findAllByTransportadoraIdAndTipoDescarteId(idTransportadora, idTipoDescarte);
        int acumulador = 0;
        for (Combo combo : combos) {
            if(acumulador < quantidadeDesejada) {
                combosParaBaixa.add(combo);
                acumulador += combo.getSaldo();
            }
        }
        return combosParaBaixa;
    }

    public Page<Combo> findAll(Pageable pageable, String transportadorId, String tipoDescarteId) {
        //Predicate para transportador
        CriteriaBuilder criteriaBuilder =  getEm().getCriteriaBuilder();
        CriteriaQuery<Combo> query = criteriaBuilder.createQuery(Combo.class);
        Root<Combo> root = query.from(Combo.class);
        query.select(root);
        getEm().createQuery(query);

        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(transportadorId) && !transportadorId.equals("null")){
            Predicate predicateForTransportador = criteriaBuilder.equal(root.get("transportador").get("id"), Long.parseLong(transportadorId));
            predicates.add(predicateForTransportador);
        }
        if(Objects.nonNull(tipoDescarteId) && !tipoDescarteId.equals("null")){
            Predicate predicateForTipoDescarte = criteriaBuilder.equal(root.get("tipoDescarte").get("id"), Long.parseLong(tipoDescarteId));
            predicates.add(predicateForTipoDescarte);
        }
        if(!predicates.isEmpty())
            query.where(predicates.toArray(new Predicate[predicates.size()]));
        query.orderBy(criteriaBuilder.desc(root.get("saldo")));

        List<Combo> resultList = getEm().createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(resultList, pageable, count(predicates));
    }

    private Long count(List<Predicate> predicates) {
        CriteriaQuery<Long> countQuery = getCriteriaBuilder().createQuery(Long.class);
        Root<Combo> root = countQuery.from(Combo.class);
        countQuery
                .select(getCriteriaBuilder().count(root))
                .where(getCriteriaBuilder().and(predicates.toArray(new Predicate[predicates.size()])));

        return getEm().createQuery(countQuery).getSingleResult();
    }

    private CriteriaBuilder getCriteriaBuilder(){
        return getEm().getCriteriaBuilder();
    }

    private Root<Combo> getRoot(){
        return getCriteriaQuery().from(Combo.class);
    }

    private CriteriaQuery<Combo> getCriteriaQuery (){
         return getCriteriaBuilder().createQuery(Combo.class);
    }
}
