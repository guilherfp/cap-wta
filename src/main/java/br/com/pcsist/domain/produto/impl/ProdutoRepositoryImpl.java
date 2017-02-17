package br.com.pcsist.domain.produto.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import br.com.pcsist.domain.produto.Produto;
import br.com.pcsist.domain.produto.ProdutoRepository;

public class ProdutoRepositoryImpl implements ProdutoRepository {

  private JdbcOperations template;
  private ProdutoRowMapper mapper;
  // private SimpleJdbcInsert insert;

  public ProdutoRepositoryImpl(DataSource dataSource) {
    // insert = new SimpleJdbcInsert(dataSource).withTableName("produto");
    // insert.setColumnNames(Arrays.asList("codigo", "valor", "ativo"));
    template = new JdbcTemplate(dataSource);
    mapper = new ProdutoRowMapper();
  }

  @Override
  public List<Produto> todos() {
    String sql = "select * from produto order by codigo desc";
    return template.query(sql, mapper);
  }

  @Override
  public void salvar(Produto produto) {
    // Map<String, Object> map = new HashMap<>();
    // map.put("codigo", produto.getCodigo());
    // map.put("valor", produto.getValor());
    // map.put("ativo", produto.isAtivo());
    // insert.execute(map);
    String sql = "insert into produto (codigo, valor, ativo) values (?, ?, ?)";
    template.update(sql, produto.getCodigo(), produto.getValor(), produto.isAtivo());
  }

  @Override
  public void salvar(List<Produto> produtos) {
    String sql = "insert into produto (codigo, valor, ativo) values (?, ?, ?)";
    template.batchUpdate(sql, produtos, 100, (ps, p) -> {
      ps.setInt(1, p.getCodigo());
      ps.setInt(2, p.getValor());
      ps.setBoolean(3, p.isAtivo());
    });
  }

  @Override
  public Produto comCodigo(int codigo) {
    try {
      String sql = "select * from produto where codigo = ?";
      return template.queryForObject(sql, mapper, codigo);
    } catch (DataAccessException ex) {
      String msg = String.format("Produto de código: %s inválido", codigo);
      throw new IllegalArgumentException(msg);
    }
  }

  @Override
  public long totalDeProdutos() {
    String sql = "select count(codigo) from produto";
    return template.queryForObject(sql, Long.class);
  }

  @Override
  public List<Long> codigos() {
    String sql = "select codigo from produto";
    return template.queryForList(sql, Long.class);
  }

  @Override
  public List<Map<String, Object>> codigosValores() {
    String sql = "select codigo, valor from produto";
    return template.queryForList(sql);
  }

}
