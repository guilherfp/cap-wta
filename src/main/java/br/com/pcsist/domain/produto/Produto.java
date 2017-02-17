package br.com.pcsist.domain.produto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import br.com.pcsist.domain.usuario.Perfil;
import br.com.pcsist.domain.usuario.Usuario;

public class Produto {

  private int codigo;
  private int valor = 0;
  private boolean ativo;
  private Map<String, Object> map;

  {
    map = new HashMap<>();
    map.put("algo", "TESTE");
  }

  public Produto() {
    super();
  }

  public Produto(int codigo, int valor) {
    this.codigo = codigo;
    this.valor = valor;
  }

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    Validate.isTrue(codigo > 0, "Código do produto inválido");
    this.codigo = codigo;
  }

  public int getValor() {
    return valor;
  }

  public void setValor(int valor) {
    if (valor >= 0) {
      this.valor = valor;
    }
  }

  public void setAtivo(boolean ativo) {
    this.ativo = ativo;
  }

  public boolean isAtivo() {
    return ativo;
  }

  public boolean inativar(Usuario usuario) {
    if (Perfil.ADMIN.equals(usuario.getPerfil())) {
      ativo = false;
      return true;
    }
    return false;
  }

  @JsonAnyGetter
  public Map<String, Object> getMap() {
    return map;
  }

  @JsonAnySetter
  public void setMap(Map<String, Object> map) {
    this.map = map;
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigo);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Produto other = (Produto) obj;
    return codigo == other.codigo;
  }

  @Override
  public String toString() {
    return String.format("Produto [codigo=%s, valor=%s]", codigo, valor);
  }

  public boolean inativarCasoSejaCaro(Usuario usuario) {
    if (valor > 10) {
      inativar(usuario);
      return true;
    }
    return false;
  }

  public void incrementar(int valor) {
    this.valor += valor;
  }

}
