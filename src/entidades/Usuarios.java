package entidades;

import entidades.enums.PapelSistema;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuarios {

    private String nome;
    private String email;
    private String senha;
    private PapelSistema papelSistema;
    private boolean ativo=false;

    public Usuarios(String nome, String email, String senha, PapelSistema papelSistema) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.papelSistema = papelSistema;
        this.ativo=true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public PapelSistema getPapel() {
        return papelSistema;
    }

    public void setPapel(PapelSistema papelSistema) {
        this.papelSistema = papelSistema;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void mudarSenha(String novaSenha) {
        this.senha = novaSenha;
    }

}
