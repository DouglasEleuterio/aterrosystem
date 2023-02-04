package br.com.douglas.repository.repositories.veiculo;

import br.com.douglas.entity.entities.temp.Veiculo;
import br.com.douglas.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends BaseRepository<Veiculo> {

    @Query(value = "from Veiculo v where v.placa like %:placa% or v.modelo like %:modelo% and v.ativo =:ativo")
    Page<Veiculo> findAllByOneParam(Pageable pageable, String placa, String modelo, Boolean ativo);

    @Query(value = "from Veiculo v where v.placa like %:placa% and v.ativo =:ativo")
    Page<Veiculo> findAllByPlaca(Pageable pageable, String placa, Boolean ativo);

    @Query(value = "from Veiculo v where v.modelo like %:modelo% and v.ativo =:ativo")
    Page<Veiculo> findAllByModelo(Pageable pageable, String modelo, Boolean ativo);

    @Query(value = "from Veiculo v where v.placa like %:placa% and v.modelo like %:modelo% and v.ativo =:ativo")
    Page<Veiculo> findAllByPlacaAndModelo(Pageable pageable,  String placa, String modelo, Boolean ativo);

    Page<Veiculo> findAllByAtivo(Pageable pageable, Boolean ativo);
}
