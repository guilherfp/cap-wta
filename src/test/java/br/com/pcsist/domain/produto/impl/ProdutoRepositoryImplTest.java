package br.com.pcsist.domain.produto.impl;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.pcsist.domain.produto.Produto;
import br.com.pcsist.domain.produto.ProdutoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataSourceTestConfig.class)
public class ProdutoRepositoryImplTest {

  @Autowired
  private DataSource dataSource;

  private ProdutoRepository produtoRepository;

  @Before
  public void setUp() throws Exception {
    produtoRepository = new ProdutoRepositoryImpl(dataSource);
  }

  @Test
  public void testTodos() throws Exception {
    List<Produto> todos = produtoRepository.todos();

    assertThat(todos).hasSize(2);
    assertThat(todos.get(0).getCodigo()).isEqualTo(2);
    assertThat(todos.get(1).getCodigo()).isEqualTo(1);
  }

}
