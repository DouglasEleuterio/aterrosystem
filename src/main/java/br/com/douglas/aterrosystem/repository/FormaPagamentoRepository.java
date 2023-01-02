package br.com.douglas.aterrosystem.repository;

import br.com.douglas.aterrosystem.entity.FormaPagamento;
import br.com.douglas.aterrosystem.entity.RelacaoPagamentosDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

    @Query(value = "select new br.com.douglas.aterrosystem.entity.RelacaoPagamentosDTO(fp.nome, sum(p.valor)) from Pagamento p join FormaPagamento fp on p.formaPagamento.id = fp.id group by fp.nome order by sum(p.valor) desc")
    List<RelacaoPagamentosDTO> agruparPagamentosPorFormaDePagamento();

    @Query(value = "select new br.com.douglas.aterrosystem.entity.RelacaoPagamentosDTO(t.nome, sum(p.valor)) from CTR c join Pagamento p join Transportador t group by t.nome ORDER BY sum(p.valor) DESC")
    List<RelacaoPagamentosDTO> agruparPagamentosPorTransportadora();

    @Query(value = "select new br.com.douglas.aterrosystem.entity.RelacaoPagamentosDTO(t.nome, sum(p.valor)) from CTR c join fetch Transportador t on t.id = c.transportador.id join fetch Pagamento p on c.id = p.ctr.id group by t.nome ORDER BY sum(p.valor) DESC")
    List<RelacaoPagamentosDTO> agruparPagamentosPorTransportadora2();

    @Query(value = "from FormaPagamento fp where fp.nome like %:nome% and fp.ativo =:ativo")
    <T> Page<T> findAllWithParams(Pageable pageable, String nome, Boolean ativo);

    @Query(value = "from FormaPagamento fp where fp.ativo =:ativo")
    <T> Page<T> findAllByAtivo(Pageable pageable, Boolean ativo);
}
