package com.example.domain.produto;

import com.example.domain.usuario.Usuario;

public interface ProdutoService {

  void inativarProdutosCaros(Usuario usuario);

  void alterar(int codigo, Produto produto);

}
