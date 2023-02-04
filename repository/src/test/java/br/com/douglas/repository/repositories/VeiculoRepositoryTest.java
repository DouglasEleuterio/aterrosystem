package br.com.douglas.repository.repositories;

import br.com.douglas.entity.entities.Marca;
import br.com.douglas.entity.entities.Modelo;
import br.com.douglas.entity.entities.Veiculo;
import br.com.douglas.entity.enums.Categoria;
import br.com.douglas.repository.RepositoryApplication;
import br.com.douglas.rsql.jpa.rsql.CustomRsqlVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = RepositoryApplication.class)
@Rollback
public class VeiculoRepositoryTest {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private ModeloRepository modeloRepository;

    @Before
    public void persiste() {
        Marca fiat = marcaRepository.save(Marca.builder()
                .nome("Fiat")
                .build());

        Modelo linea = modeloRepository.save(Modelo.builder()
                .marca(fiat)
                .nome("Linea")
                .categoria(Categoria.SEDAN)
                .build());

        Modelo uno = modeloRepository.save(Modelo.builder()
                .marca(fiat)
                .nome("Uno")
                .categoria(Categoria.HACTHBACK)
                .build());

        Veiculo instanciaLinea = Veiculo.builder()
                .dataFabricacao(LocalDateTime.of(2015, 9, 15, 15, 36))
                .dataLancamento(LocalDate.of(2015, 1, 1))
                .modelo(linea)
                .build();

        Veiculo instanciaUno = Veiculo.builder()
                .dataFabricacao(LocalDateTime.of(2010, 3, 17, 15, 36))
                .dataLancamento(LocalDate.of(2009, 1, 1))
                .modelo(uno)
                .build();

        veiculoRepository.saveAll(List.of(instanciaLinea, instanciaUno));
    }

    public void delete() {
        veiculoRepository.deleteAll();
        modeloRepository.deleteAll();
        marcaRepository.deleteAll();
    }

    @Test
    @Rollback
    public void testaConsulta() {
        List<Veiculo> veiculos = veiculoRepository.findAll();
        Assert.assertEquals(2, veiculos.size());
        delete();
    }

    @Test
    @Rollback
    public void dadoVeiculoSalvo_quandoBuscaPorMarcaDoVeiculo_entaoRetornarVeiculosDaMarca() {
        Specification<Veiculo> spec = new RSQLParser().parse("modelo.marca.nome==Fiat").accept(new CustomRsqlVisitor<>());
        var veiculos = veiculoRepository.findAll(spec);

        Assert.assertFalse(veiculos.isEmpty());
        Assert.assertEquals(2, veiculos.size());
        delete();
    }
}
