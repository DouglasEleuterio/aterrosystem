package br.com.douglas.repositories.veiculo;

import br.com.douglas.entity.entities.temp.Veiculo;
import br.com.douglas.mapper.veiculo.VeiculoFromList;
import br.com.douglas.mapper.veiculo.VeiculoFromSelect;
import br.com.douglas.repositories.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Query(value = "select new br.com.douglas.mapper.veiculo.VeiculoFromSelect(v.id, v.placa) from Veiculo v ")
    List<VeiculoFromSelect> findAllFromSelect(Sort sort);

    /**
     * Nova versão
     */
    @Query(value = "select new br.com.douglas.mapper.veiculo.VeiculoFromList(v.id, v.placa, v.transportador.nome, v.ativo) from Veiculo v")
    Page<VeiculoFromList> findAllFromList(Specification<Veiculo> spec, Pageable pageable);

    @Query(value = "select new br.com.douglas.mapper.veiculo.VeiculoFromList(v.id, v.placa, v.transportador.nome, v.ativo) from Veiculo v")
    Page<VeiculoFromList> findAllFromList(Pageable pageable);

}
