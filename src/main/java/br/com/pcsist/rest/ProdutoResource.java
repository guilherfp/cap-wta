package br.com.pcsist.rest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.pcsist.domain.produto.Produto;
import br.com.pcsist.domain.produto.ProdutoRepository;
import br.com.pcsist.domain.produto.ProdutoService;

@Controller
@RequestMapping("/produtos")
public class ProdutoResource {

  private static final Logger LOG = LoggerFactory.getLogger(ProdutoResource.class);

  @Autowired
  private ProdutoRepository produtoRepository;
  @Autowired
  private ProdutoService produtoService;

  @RequestMapping(method = RequestMethod.GET)
  public @ResponseBody List<Produto> listar() {
    return produtoRepository.todos();
  }

  @RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
  public @ResponseBody Produto comCodigo(@PathVariable int codigo) {
    LOG.info("Obtendo produto com codigo: {}", codigo);
    return produtoRepository.comCodigo(codigo);
  }

  @RequestMapping(value = "/codigos", method = RequestMethod.GET)
  public @ResponseBody List<Long> total() {
    return produtoRepository.codigos();
  }

  @RequestMapping(value = "/total", method = RequestMethod.GET)
  public @ResponseBody Long codigos() {
    return produtoRepository.totalDeProdutos();
  }

  @RequestMapping(value = "/codigos-valores", method = RequestMethod.GET)
  public @ResponseBody List<Map<String, Object>> codigosValores() {
    return produtoRepository.codigosValores();
  }

  @RequestMapping(method = RequestMethod.POST)
  public @ResponseBody void salvar(@RequestBody Produto produto) {
    LOG.info("Salvando produto: {}", produto);
    produtoRepository.salvar(produto);
  }

  @RequestMapping(value = "/carga", method = RequestMethod.POST)
  public @ResponseBody void carga(@RequestBody List<Produto> produto) {
    LOG.info("Salvando produto: {}", produto);
    produtoRepository.salvar(produto);
  }

  @RequestMapping(value = "/{codigo}", method = RequestMethod.PUT)
  public @ResponseBody void alterar(@RequestBody Produto produto, @PathVariable int codigo) {
    LOG.info("Salvando produto: {}", produto);
    produtoService.alterar(codigo, produto);
  }

}
