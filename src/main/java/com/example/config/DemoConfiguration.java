package com.example.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.example.domain.produto.ProdutoRepository;
import com.example.domain.produto.impl.ProdutoRepositoryImpl;

@Configuration
public class DemoConfiguration {

  @Bean
  public DataSource dataSource() {
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    builder.setType(EmbeddedDatabaseType.H2);
    return builder.build();
  }

  @Bean
  public ProdutoRepository produtoRepository(DataSource dataSource) {
    return new ProdutoRepositoryImpl(dataSource);
  }

}
