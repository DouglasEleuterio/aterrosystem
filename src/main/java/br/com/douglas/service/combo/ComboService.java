package br.com.douglas.service.combo;

import br.com.douglas.entity.entities.temp.Combo;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.repositories.combo.ComboRepository;
import br.com.douglas.service.impls.BaseService;
import br.com.douglas.service.tipodescarte.TipoDescarteService;
import br.com.douglas.service.transportador.TransportadorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ComboService extends BaseService<Combo> {

    private final TransportadorService transportadorService;
    private final TipoDescarteService tipoDescarteService;
    private final ComboRepository comboRepository;

    public ComboService(ComboRepository repository, TransportadorService transportadorService, TipoDescarteService tipoDescarteService, ComboRepository comboRepository) {
        super(repository);
        this.transportadorService = transportadorService;
        this.tipoDescarteService = tipoDescarteService;
        this.comboRepository = comboRepository;
    }

    @Override
    public void validate(Combo combo) throws DomainException {
        if(Objects.isNull(combo.getTipoDescarte())){
            throw new DomainException("Tipo de descarte não informado!");
        }
        if(Objects.isNull(combo.getTransportador())){
            throw new DomainException("Transportador não informado!");
        }
        if(Objects.isNull(combo.getPagamentos()) || CollectionUtils.isEmpty(combo.getPagamentos())){
            throw new DomainException("Pagamento não informado!");
        }
    }

    public List<Combo> findAllByTransportadoraId(String transportadoraId, Sort sort) {
        return comboRepository.findAllByTransportadorId(Long.parseLong(transportadoraId), sort);
    }

    public void preparaCombo(Combo combo) throws DomainException {
        combo.setAtivo(true);
        combo.setTransportador(transportadorService.findById(combo.getTransportador().getId()));
        combo.setTipoDescarte(tipoDescarteService.findById(combo.getTipoDescarte().getId()));
    }

    public Integer retornaQuantidadeDeComboPorCategoria(String idTransportadora, String idTipoDescarte){
        return comboRepository.retornaQuantidadeDeComboPorCategoria(idTransportadora, idTipoDescarte);
    }

    public List<Combo> retornaComboParaConsumirSaldoNoDescarte(int quantidadeDesejada, String idTransportadora, String idTipoDescarte){
        List<Combo> combosParaBaixa = new ArrayList<>();
        var combos = comboRepository.findAllByTransportadoraIdAndTipoDescarteId(idTransportadora, idTipoDescarte);
        int acumulador = 0;
        for (Combo combo : combos) {
            if(acumulador < quantidadeDesejada) {
                combosParaBaixa.add(combo);
                acumulador += combo.getSaldo();
            }
        }
        return combosParaBaixa;
    }

    @Override
    @Transactional
    public Page<Combo> findAll(Specification<Combo> specification, Pageable pageable) {
        return findPageAll(specification, pageable);
    }

    @Override
    @Transactional
    public Page<Combo> findAll(Pageable pageable) {
        return findPageAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Combo> findPageAll(Specification<Combo> specification, Pageable pageable) {
        Page<Combo> all = comboRepository.findAll(specification, pageable);
        return new PageImpl<>(all.getContent());
    }

    @Transactional(readOnly = true)
    public Page<Combo> findPageAll( Pageable pageable) {
        Page<Combo> all = comboRepository.findAll(pageable);
        return new PageImpl<>(all.getContent(), pageable, all.getTotalElements());
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
}
