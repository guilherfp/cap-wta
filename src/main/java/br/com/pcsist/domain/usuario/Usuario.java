package br.com.pcsist.domain.usuario;

public class Usuario {

  private Perfil perfil;

  public Usuario(Perfil perfil) {
    this.perfil = perfil;
  }

  public Perfil getPerfil() {
    return perfil;
  }

}
