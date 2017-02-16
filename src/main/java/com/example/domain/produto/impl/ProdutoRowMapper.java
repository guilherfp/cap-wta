package com.example.domain.produto.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.domain.produto.Produto;

final class ProdutoRowMapper implements RowMapper<Produto> {

  @Override
  public Produto mapRow(ResultSet rs, int rowNum) throws SQLException {
    Produto produto = new Produto();
    produto.setCodigo(rs.getInt("codigo"));
    produto.setValor(rs.getInt("valor"));
    produto.setAtivo(rs.getBoolean("ativo"));
    return produto;
  }

}
