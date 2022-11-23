package br.com.douglas.aterrosystem.repository;

import br.com.douglas.aterrosystem.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query(value = "select sum(p.valor) from Pagamento p where p.dataPagamento between :inicio and :fim")
    Optional<Integer> somaMensal(LocalDate inicio, LocalDate fim);

    @Query(value = "select sum(p.valor) from Pagamento p where p.dataPagamento =:data ")
    Optional<Integer> somaSemanal(LocalDate data);
}
