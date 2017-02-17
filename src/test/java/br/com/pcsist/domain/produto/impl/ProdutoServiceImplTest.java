package br.com.pcsist.domain.produto.impl;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.pcsist.domain.produto.Produto;
import br.com.pcsist.domain.produto.ProdutoRepository;
import br.com.pcsist.domain.produto.impl.ProdutoServiceImpl;
import br.com.pcsist.domain.usuario.Perfil;
import br.com.pcsist.domain.usuario.Usuario;

@RunWith(MockitoJUnitRunner.class)
public class ProdutoServiceImplTest {

  @Mock
  private ProdutoRepository produtoRepository;

  @InjectMocks
  private ProdutoServiceImpl produtoService;

  private Usuario usuario;

  @Before
  public void setUp() throws Exception {
    usuario = new Usuario(Perfil.ADMIN);
  }

  @Test
  public void testInativarProdutosCaros() throws Exception {
    Produto produto = new Produto(1, 15);
    produto.setAtivo(true);
    when(produtoRepository.todos()).thenReturn(asList(produto));

    produtoService.inativarProdutosCaros(usuario);

    assertThat(produto.isAtivo()).isFalse();
    verify(produtoRepository, times(1)).alterar(any(Produto.class));
  }

  @Test
  public void testIncrementarValor() throws Exception {
    Produto produto1 = new Produto(1, 100);
    Produto produto2 = new Produto(1, 200);
    when(produtoRepository.todos()).thenReturn(asList(produto1, produto2));

    produtoService.incrementarValor(50);

    assertThat(produto1.getValor()).isEqualTo(150);
    assertThat(produto2.getValor()).isEqualTo(250);

    verify(produtoRepository, times(2)).alterar(any(Produto.class));
  }

}
