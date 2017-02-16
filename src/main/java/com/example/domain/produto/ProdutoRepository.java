package com.example.domain.produto;

import java.util.List;
import java.util.Map;

public interface ProdutoRepository {

  List<Produto> todos();

  void salvar(Produto produto);

  Produto comCodigo(int codigo);

  long totalDeProdutos();

  List<Long> codigos();

  List<Map<String, Object>> codigosValores();

  void salvar(List<Produto> produtos);

}
