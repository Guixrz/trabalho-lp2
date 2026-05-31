package entidades;

import entidades.enums.PapelSistema;

public class Coordenador extends Usuarios{

    private Curso curso;

    public Coordenador(String nome, String email, String senha, PapelSistema papelSistema, Curso curso) {
        super(nome, email, senha, papelSistema);
        this.curso = curso;
    }

}