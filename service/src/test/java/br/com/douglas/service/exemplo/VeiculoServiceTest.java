package br.com.douglas.service.exemplo;

import br.com.douglas.entity.entities.Marca;
import br.com.douglas.entity.entities.Modelo;
import br.com.douglas.entity.entities.Veiculo;
import br.com.douglas.entity.enums.Categoria;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.repository.RepositoryApplication;
import br.com.douglas.rsql.jpa.util.SpecificationUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = RepositoryApplication.class)
@Rollback
public class VeiculoServiceTest {

    @Autowired
    VeiculoService veiculoService;

    @Autowired
    MarcaService marcaService;

    @Autowired
    ModeloService modeloService;

    @Before
    public void persiste() throws DomainException {
        Marca fiat = marcaService.create(Marca.builder()
                .nome("Fiat")
                .build());

        Modelo linea = modeloService.create(Modelo.builder()
                .marca(fiat)
                .nome("Linea")
                .categoria(Categoria.SEDAN)
                .build());

        Modelo uno = modeloService.create(Modelo.builder()
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

        veiculoService.create(instanciaLinea);
    }

    @Test
    @Rollback
    public void dadoVeiculoPersistidoNaBase_quandoObterListaDeVeiculos_entaoCarregarVeiculoPersistido() {
        Iterable<Veiculo> all = veiculoService.findAll();
        Assert.assertTrue(all.iterator().hasNext());
    }

    @Test
    @Rollback
    public void dadoVeiculoSemModelo_quandoSalvar_entaoRetornaErro() {
        try {
            Modelo linea = null;
            Iterable<Modelo> all = modeloService.findAll(SpecificationUtils.rsqlToSpecification("marca.nome==Fiat"), Sort.unsorted());
            while (all.iterator().hasNext()){
                if(all.iterator().next().getNome().equals("Linea")) {
                    linea = all.iterator().next();
                    break;
                }
            }
            Veiculo instanciaLinea = Veiculo.builder()
                    .dataFabricacao(LocalDateTime.of(2015, 9, 15, 15, 36))
                    .dataLancamento(LocalDate.of(2015, 1, 1))
                    .build();
            veiculoService.create(instanciaLinea);
                } catch ( Exception e) {
            Assert.assertEquals("Modelo do veículo não informado!", e.getMessage());
        }

    }

    @Test
    @Rollback
    public void dadoVeiculoSemDataFabricacao_quandoSalvar_entaoRetornaErro() {
        try {
            Modelo linea = null;
            Iterable<Modelo> all = modeloService.findAll(SpecificationUtils.rsqlToSpecification("marca.nome==Fiat"), Sort.unsorted());
            while (all.iterator().hasNext()){
                if(all.iterator().next().getNome().equals("Linea")) {
                    linea = all.iterator().next();
                    break;
                }
            }
            Veiculo instanciaLinea = Veiculo.builder()
//                    .dataFabricacao(LocalDateTime.of(2015, 9, 15, 15, 36))
                    .dataLancamento(LocalDate.of(2015, 1, 1))
                    .modelo(linea)
                    .build();
            veiculoService.create(instanciaLinea);
        } catch ( Exception e) {
            Assert.assertEquals("Data de fabricação do veículo não informada!", e.getMessage());
        }
    }

    @Test
    @Rollback
    public void dadoVeiculoSemCategoria_quandoSalvar_entaoRetornaErro() {
        try {
            Modelo linea = null;
            Iterable<Modelo> all = modeloService.findAll(SpecificationUtils.rsqlToSpecification("marca.nome==Fiat"), Sort.unsorted());
            while (all.iterator().hasNext()){
                if(all.iterator().next().getNome().equals("Linea")) {
                    linea = all.iterator().next();
                    break;
                }
            }
            Veiculo instanciaLinea = Veiculo.builder()
                    .dataFabricacao(LocalDateTime.of(2015, 9, 15, 15, 36))
//                    .categoriaOrdinal(Categoria.SEDAN)
                    .dataLancamento(LocalDate.of(2015, 1, 1))
                    .modelo(linea)
                    .build();
            veiculoService.create(instanciaLinea);
        } catch ( Exception e) {
            Assert.assertEquals("Categoria do veículo não informada!", e.getMessage());
        }
    }

    @Test
    @Rollback
    public void dadoVeiculoSemDataDeLancamento_quandoSalvar_entaoRetornaErro() {
        try {
            Modelo linea = null;
            Iterable<Modelo> all = modeloService.findAll(SpecificationUtils.rsqlToSpecification("marca.nome==Fiat"), Sort.unsorted());
            while (all.iterator().hasNext()){
                if(all.iterator().next().getNome().equals("Linea")) {
                    linea = all.iterator().next();
                    break;
                }
            }
            Veiculo instanciaLinea = Veiculo.builder()
                    .dataFabricacao(LocalDateTime.of(2015, 9, 15, 15, 36))
//                    .dataLancamento(LocalDate.of(2015, 1, 1))
                    .modelo(linea)
                    .build();
            veiculoService.create(instanciaLinea);
        } catch ( Exception e) {
            Assert.assertEquals("Data de lançamento do veículo não informada!", e.getMessage());
        }
    }

    @Test
    @Rollback
    public void dadoModeloSemNome_quandoSalvar_entaoRetornaErro() {
        Modelo modelo = Modelo.builder()
                .nome("")
                .marca(marcaService.findAll().iterator().next())
                .build();
        try {
            modeloService.create(modelo);
            Assert.fail();
        } catch (DomainException e) {
            Assert.assertEquals("Nome do modelo não informado!",e.getMessage() );
        }
    }

    @Test
    @Rollback
    public void dadoModeloSemMarca_quandoSalvar_entaoRetornaErro() {
        Modelo modelo = Modelo.builder()
                .nome("Linea")
                .build();
        try {
            modeloService.create(modelo);
            Assert.fail();
        } catch (DomainException e) {
            Assert.assertEquals("Marca do modelo não informada!", e.getMessage());
        }
    }

    @Test
    @Rollback
    public void dadoMarcaSemNome_quandoSalvar_entaoRetornaErro() {
        try {
            Marca fiat = marcaService.create(Marca.builder()
                    .build());
            Assert.fail();
        } catch (DomainException e) {
            Assert.assertEquals("Nome da marca não informado!", e.getMessage());
        }
    }
}
