package br.com.pcsist.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pcsist.DataSourceTestConfig;
import br.com.pcsist.domain.produto.Produto;
import br.com.pcsist.domain.produto.ProdutoRepository;

@ComponentScan("br.com.pcsist")
@RunWith(SpringRunner.class)
@WebMvcTest(ProdutoResource.class)
@ContextConfiguration(classes = { DataSourceTestConfig.class })
public class ProdutoResourceTest {

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private DataSource dataSource;
  @Autowired
  private ProdutoRepository produtoRepository;

  @Before
  public void setUp() throws Exception {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.execute("delete from produto");
  }

  @Test
  public void testTotal() throws Exception {
    mockMvc.perform(get("/produtos/total"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().json("0"));
  }

  @Test
  public void testValorTotal_SemProdutos() throws Exception {
    mockMvc.perform(get("/produtos/valor-total"))
      .andExpect(status().isOk())
      .andExpect(content().json("0"));
  }

  @Test
  public void testValorTotal() throws Exception {
    produtoRepository.salvar(new Produto(1, 100));
    produtoRepository.salvar(new Produto(2, 50));

    mockMvc.perform(get("/produtos/valor-total"))
      .andExpect(status().isOk())
      .andExpect(content().json("150"));
  }

  @Test
  public void testSalvar() throws Exception {
    Produto produto = new Produto(1, 50);

    long total = produtoRepository.totalDeProdutos();

    mockMvc.perform(post("/produtos")
      .contentType(MediaType.APPLICATION_JSON)
      .content(json(produto)))
      .andExpect(status().isOk());

    assertThat(produtoRepository.totalDeProdutos()).isEqualTo(total + 1);
    assertThat(produtoRepository.todos()).contains(produto);
  }

  private String json(Produto value) throws JsonProcessingException {
    return objectMapper.writeValueAsString(value);
  }

  @Test
  public void testAlterar() throws Exception {
    produtoRepository.salvar(new Produto(2, 50));

    mockMvc.perform(put("/produtos/{codigo}", 2)
      .contentType(MediaType.APPLICATION_JSON)
      .content(json(new Produto(2, 5_000))))
      .andExpect(status().isOk());

    Produto produto = produtoRepository.comCodigo(2);
    assertThat(produto).isNotNull();
    assertThat(produto.getCodigo()).isEqualTo(2);
    assertThat(produto.getValor()).isEqualTo(5_000);
  }

  @Test
  public void testComCodigo() throws Exception {
    produtoRepository.salvar(new Produto(99, 50));

    mockMvc.perform(get("/produtos/{codigo}", "99"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(notNullValue())))
      .andExpect(jsonPath("$.codigo", is(99)))
      .andExpect(jsonPath("$.valor", is(50)))
      .andExpect(jsonPath("$.ativo", is(false)));
  }

}
