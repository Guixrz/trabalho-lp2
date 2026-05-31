package entidades;

import entidades.enums.PapelSistema;


public class Discente extends Usuarios {

    private String matricula;
    private int semestreAtual;
    private Curso curso;


    public Discente(String nome, String email, String senha, PapelSistema papelSistema, String matricula, int semestreAtual, Curso curso) {
        super(nome, email, senha, papelSistema);
        this.matricula = matricula;
        this.semestreAtual = semestreAtual;
        this.curso = curso;
    }

    public String getMatricula() {
        return matricula;
    }

    public int getSemestreAtual() {
        return semestreAtual;
    }

    public void setSemestreAtual(int semestreAtual) {
        this.semestreAtual = semestreAtual;
    }

    public Curso getCurso() {
        return curso;
    }

    public void mudarCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Discente)) return false;
        Discente d = (Discente) o;
        return this.getMatricula().equals(d.getMatricula());
    }

    @Override
    public int hashCode() {
        return getMatricula().hashCode();
    }

}
