package br.com.pcsist.domain.produto;

import br.com.pcsist.domain.usuario.Usuario;

public interface ProdutoService {

  void inativarProdutosCaros(Usuario usuario);

  void alterar(int codigo, Produto produto);

}
