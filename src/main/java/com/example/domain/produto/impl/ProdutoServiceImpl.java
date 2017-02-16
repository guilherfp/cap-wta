package com.example.domain.produto.impl;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.produto.Produto;
import com.example.domain.produto.ProdutoRepository;
import com.example.domain.produto.ProdutoService;
import com.example.domain.usuario.Usuario;

@Service
public class ProdutoServiceImpl implements ProdutoService {

  private ProdutoRepository produtoRepository;

  @Autowired
  public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
    this.produtoRepository = produtoRepository;
  }

  @Override
  public void inativarProdutosCaros(Usuario usuario) {
    produtoRepository.todos().forEach(inativarCaros(usuario));
  }

  private Consumer<? super Produto> inativarCaros(Usuario usuario) {
    return produto -> {
      if (produto.inativarCasoSejaCaro(usuario)) {
        produtoRepository.salvar(produto);
      }
    };
  }

  @Override
  public void alterar(int codigo, Produto produto) {
    Produto produtoExistente = produtoRepository.comCodigo(codigo);
    if (produtoExistente != null) {
      produtoExistente.setValor(produto.getValor());
      produtoExistente.setAtivo(produto.isAtivo());
      produtoRepository.salvar(produtoExistente);
    }
  }

}
