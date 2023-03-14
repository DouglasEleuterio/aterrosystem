package br.com.douglas.repositories.pagamento;

import br.com.douglas.entity.entities.temp.Pagamento;
import br.com.douglas.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PagamentoRepository extends BaseRepository<Pagamento> {

    @Query(value = "select sum(p.valor) from Pagamento p where p.dataPagamento between :inicio and :fim")
    Optional<Integer> somaMensal(LocalDate inicio, LocalDate fim);

    @Query(value = "select sum(p.valor) from Pagamento p where p.ctr != null and p.dataPagamento =:data ")
    Optional<Integer> somaSemanal(LocalDate data);
}
