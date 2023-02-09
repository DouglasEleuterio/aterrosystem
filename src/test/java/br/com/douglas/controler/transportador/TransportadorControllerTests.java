package br.com.douglas.controler.transportador;

import br.com.douglas.controler.util.BaseTest;
import br.com.douglas.mapper.transportador.TransportadorRequest;
import br.com.douglas.mapper.transportador.TransportadorResponse;
import br.com.douglas.mapper.veiculo.VeiculoRequest;
import br.com.douglas.mapper.veiculo.VeiculoResponse;
import br.com.douglas.exception.model.ErrorResponse;
import br.com.douglas.model.entity.BaseEntityRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class TransportadorControllerTests extends BaseTest {

    private final static String TRANSPORTADOR_URL = "transportador";
    private final static String VEICULO_URL = "veiculo";
    private String codigoTransportadoraPersistido = "";

    @After
    public void deletarTransportadora() throws Exception {
        if(!codigoTransportadoraPersistido.isEmpty() && !codigoTransportadoraPersistido.isBlank()) {
            getMockMvc()
                    .perform(getDelete(API_URL + TRANSPORTADOR_URL + "/" + codigoTransportadoraPersistido)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(getLog())
                    .andExpect(getStatus().isNoContent());
            codigoTransportadoraPersistido = "";
        }
    }

    @Test
    @Rollback
    public void deveSalvarTransportadorComSucesso() throws Exception {
        var transportadora = TransportadorRequest.builder()
                .nome("Transportadora A")
                .cnpj("00636994000137")
                .razaoSocial("Razão Social Válido")
                .telefone("6232000000")
                .ativo(true)
                .email("email@mail.com")
                .endereco(getEnderecoValido())
                .build();

        final ResultActions result = getMockMvc().perform(getPost(API_URL + TRANSPORTADOR_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsBytes(transportadora))).andExpect(getStatus().isOk())
                .andDo(getLog());

        final TransportadorResponse saved = getMapper().readValue(
                result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
                });
        codigoTransportadoraPersistido = saved.getId();
        Assertions.assertNotNull(saved);
        Assertions.assertEquals("email@mail.com", saved.getEmail());
    }

    @Test
    @Rollback
    public void deveFalharAoSalvarTransportadorComErroNome() throws Exception {
        var transportadora = TransportadorRequest.builder()
                .cnpj("00636994000137")
                .razaoSocial("Razão Social Válido")
                .telefone("6232000000")
                .ativo(true)
                .email("email@mail.com")
                .endereco(getEnderecoValido())
                .build();

        ResultActions result = getMockMvc()
                .perform(getPost(API_URL + TRANSPORTADOR_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsBytes(transportadora)))
                .andDo(getLog())
                .andExpect(getStatus().isBadRequest());

        final ErrorResponse response = getMapper().readValue(result.andReturn()
                .getResponse().getContentAsString(StandardCharsets.UTF_8), ErrorResponse.class);

        Assertions.assertEquals("Nome inválido", response.getMessage());
    }

    @Test
    @Rollback
    public void deveFalharAoSalvarTransportadorSemEndereco() throws Exception {
        var transportadora = TransportadorRequest.builder()
                .nome("Nome Válido")
                .cnpj("00636994000137")
                .razaoSocial("Razão Social Válido")
                .telefone("6232000000")
                .ativo(true)
                .email("email@mail.com")
                .build();

        ResultActions result = getMockMvc()
                .perform(getPost(API_URL + TRANSPORTADOR_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsBytes(transportadora)))
                .andDo(getLog())
                .andExpect(getStatus().isBadRequest());

        final ErrorResponse response = getMapper().readValue(result.andReturn()
                .getResponse().getContentAsString(StandardCharsets.UTF_8), ErrorResponse.class);

        Assertions.assertEquals("Endereço inválido", response.getMessage());
    }

    @Test
    @Rollback
    public void deveSucessoAoEditarTransportadora() throws Exception {

        var transportadora = TransportadorRequest.builder()
                .nome("Transportadora A")
                .cnpj("00636994000137")
                .razaoSocial("Razão Social Válido")
                .telefone("6232000000")
                .ativo(true)
                .email("email@mail.com")
                .endereco(getEnderecoValido())
                .build();

        final ResultActions savedResult = getMockMvc().perform(getPost(API_URL + TRANSPORTADOR_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsBytes(transportadora))).andExpect(getStatus().isOk())
                .andDo(getLog());

        final TransportadorResponse savedEntity = getMapper().readValue(
                savedResult.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
                });
        codigoTransportadoraPersistido = savedEntity.getId();

        var edited = TransportadorRequest.builder()
                .nome("Nome Alterado")
                .cnpj("00636994000137")
                .razaoSocial("Razão Social Válido")
                .telefone("6232000000")
                .ativo(true)
                .email("email@mail.com")
                .endereco(getEnderecoValido())
                .build();

        ResultActions result = getMockMvc()
                .perform(getPut(API_URL + TRANSPORTADOR_URL + "/" + savedEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsBytes(edited)))
                .andDo(getLog())
                .andExpect(getStatus().isOk());

        final TransportadorResponse response = getMapper().readValue(
                result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
                });

        Assertions.assertEquals("Nome Alterado", response.getNome());
    }

    @Test
    @Rollback
    public void deveSucessoAoExcluirTransportadoraSemVeiculosAssociados() throws Exception {
        var transportadora = TransportadorRequest.builder()
                .nome("Transportadora A")
                .cnpj("00636994000137")
                .razaoSocial("Razão Social Válido")
                .telefone("6232000000")
                .ativo(true)
                .email("email@mail.com")
                .endereco(getEnderecoValido())
                .build();

        final ResultActions savedResult = getMockMvc().perform(getPost(API_URL + TRANSPORTADOR_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsBytes(transportadora))).andExpect(getStatus().isOk())
                .andDo(getLog());

        final TransportadorResponse savedEntity = getMapper().readValue(
                savedResult.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
                });

        getMockMvc()
                .perform(getDelete(API_URL + TRANSPORTADOR_URL + "/" + savedEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(getLog())
                .andExpect(getStatus().isNoContent());
    }

    @Test
    public void deveSucessoAoExcluirTransportadoraComVeiculosAssociados() throws Exception {
        var transportadora = TransportadorRequest.builder()
                .nome("Transportadora A")
                .cnpj("00636994000137")
                .razaoSocial("Razão Social Válido")
                .telefone("6232000000")
                .ativo(true)
                .email("email@mail.com")
                .endereco(getEnderecoValido())
                .build();

        final ResultActions savedResult = getMockMvc().perform(getPost(API_URL + TRANSPORTADOR_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsBytes(transportadora))).andExpect(getStatus().isOk())
                .andDo(getLog());

        final TransportadorResponse savedEntity = getMapper().readValue(
                savedResult.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
                });

        var veiculo = VeiculoRequest.builder()
                .marca("Fiat")
                .modelo("Linea")
                .placa("AAA-0A00")
                .transportador(BaseEntityRequest.of(savedEntity.getId()))
                .ativo(true)
                .build();

        getMockMvc().perform(getPost(API_URL + VEICULO_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsBytes(veiculo))).andExpect(getStatus().isOk())
                .andDo(getLog());

        getMockMvc()
                .perform(getDelete(API_URL + TRANSPORTADOR_URL + "/" + savedEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(getLog())
                .andExpect(getStatus().isNoContent());

        var result = getMockMvc().perform(getGet(API_URL + VEICULO_URL + "/find-list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(getStatus().isOk())
                .andDo(getLog());

        final List<VeiculoResponse> veiculos = getMapper().readValue(
                result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
                });

        Assertions.assertEquals(0, veiculos.size());
    }

    @Test
    @Rollback
    public void deveSucessoAoEditarTransportadoraComVeiculosAssociados() throws Exception {
        var transportadora = TransportadorRequest.builder()
                .nome("Transportadora A")
                .cnpj("00636994000137")
                .razaoSocial("Razão Social Válido")
                .telefone("6232000000")
                .ativo(true)
                .email("email@mail.com")
                .endereco(getEnderecoValido())
                .build();

        final ResultActions savedResult = getMockMvc().perform(getPost(API_URL + TRANSPORTADOR_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsBytes(transportadora))).andExpect(getStatus().isOk())
                .andDo(getLog());

        final TransportadorResponse savedEntity = getMapper().readValue(
                savedResult.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
                });
        codigoTransportadoraPersistido = savedEntity.getId();

        var veiculo = VeiculoRequest.builder()
                .marca("Fiat")
                .modelo("Linea")
                .placa("AAA-0A00")
                .transportador(BaseEntityRequest.of(savedEntity.getId()))
                .ativo(true)
                .build();

        getMockMvc().perform(getPost(API_URL + VEICULO_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsBytes(veiculo))).andExpect(getStatus().isOk())
                .andDo(getLog());

        transportadora.setNome("Nome Alterado");
        var editedResult = getMockMvc()
                .perform(getPut(API_URL + TRANSPORTADOR_URL + "/" + savedEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getMapper().writeValueAsBytes(transportadora)))
                .andDo(getLog())
                .andExpect(getStatus().isOk());

        var result = getMockMvc().perform(getGet(API_URL + VEICULO_URL + "/find-list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(getStatus().isOk())
                .andDo(getLog());

        final TransportadorResponse editedEntity = getMapper().readValue(
                editedResult.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
                });

        Assertions.assertEquals("Nome Alterado", editedEntity.getNome());

        final List<VeiculoResponse> veiculos = getMapper().readValue(
                result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
                });

        Assertions.assertEquals(1, veiculos.size());
    }
}
