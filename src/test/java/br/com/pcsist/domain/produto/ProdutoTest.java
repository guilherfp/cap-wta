package br.com.pcsist.domain.produto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Before;
import org.junit.Test;

import br.com.pcsist.domain.produto.Produto;

public class ProdutoTest {

  private Produto produto;

  @Before
  public void setUp() throws Exception {
    produto = new Produto();
    System.out.println("NOVO TESTE");
  }

  @Test
  public void testSetValor() throws Exception {
    produto.setValor(-1);

    assertThat(produto.getValor()).isEqualTo(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCodigo_ValorNegativo() throws Exception {
    produto.setCodigo(-1);
  }

  @Test
  public void testSetCodigo_ValorNegativoValidandoMensagem() throws Exception {
    assertThatThrownBy(() -> produto.setCodigo(-1))
      .hasMessage("Código do produto inválido")
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void testToString() throws Exception {
    produto = new Produto(10, 10);

    assertThat(produto).hasToString("Produto [codigo=10, valor=10]");
  }

}
