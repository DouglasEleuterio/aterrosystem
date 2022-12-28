package br.com.douglas.aterrosystem.repository;

import br.com.douglas.aterrosystem.entity.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    @Query(value = "from Veiculo v where v.placa like %:placa% or v.modelo like %:modelo% and v.ativo =:ativo")
    Page<Veiculo> findAllByOneParam(Pageable pageable, String placa, String modelo, Boolean ativo);

    @Query(value = "from Veiculo v where v.placa like %:placa% and v.modelo like %:modelo% and v.ativo =:ativo")
    Page<Veiculo> findAllByPlacaAndModelo(Pageable pageable,  String placa, String modelo, Boolean ativo);

    Page<Veiculo> findAllByAtivo(Pageable pageable, Boolean ativo);
}
