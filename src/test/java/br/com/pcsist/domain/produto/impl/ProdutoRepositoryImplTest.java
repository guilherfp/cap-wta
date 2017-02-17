package br.com.pcsist.domain.produto.impl;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.pcsist.domain.produto.Produto;
import br.com.pcsist.domain.produto.ProdutoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataSourceTestConfig.class)
public class ProdutoRepositoryImplTest {

  @Autowired
  private DataSource dataSource;

  private JdbcTemplate template;

  private ProdutoRepository produtoRepository;

  @Before
  public void setUp() throws Exception {
    template = new JdbcTemplate(dataSource);
    produtoRepository = new ProdutoRepositoryImpl(dataSource);
    template.execute("delete from produto");
  }

  @Test
  public void testTodos() throws Exception {
    template.execute("insert into produto (codigo, valor, ativo) values (1, 100, true)");
    template.execute("insert into produto (codigo, valor, ativo) values (2, 200, false)");

    List<Produto> todos = produtoRepository.todos();

    assertThat(todos).hasSize(2);
    assertThat(todos.get(0).getCodigo()).isEqualTo(2);
    assertThat(todos.get(1).getCodigo()).isEqualTo(1);
  }

  @Test
  public void testSalvarProduto() throws Exception {
    Produto produto = new Produto(9999, 9999);

    produtoRepository.salvar(produto);

    Produto comCodigo = produtoRepository.comCodigo(9999);
    assertThat(comCodigo).isNotNull();
    assertThat(comCodigo.getCodigo()).isEqualTo(9999);
    assertThat(comCodigo.getValor()).isEqualTo(9999);
  }

}
