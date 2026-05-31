package entidades;

import entidades.enums.Modalidade;
import entidades.enums.Status;
import entidades.enums.Tipo;
import excecoes.OperationNotAllowedException;
import interfaces.Aprovavel;

import java.time.LocalDateTime;

public class Oportunidade implements Aprovavel<Usuarios> {

    private String titulo;
    private String descricao;
    private Tipo tipo;
    private Modalidade modalidade;
    private int carga_horaria;
    private int vagas;
    private Status status;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Usuarios autor;
    private Docente responsavel;
    private Usuarios avaliador;
    private String motivoRejeicao;

    public Oportunidade(String titulo, String descricao, Tipo tipo, Modalidade modalidade,
                        int carga_horaria, int vagas, Status status, LocalDateTime inicio,
                        Usuarios autor, Docente responsavel) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.modalidade = modalidade;
        this.carga_horaria = carga_horaria;
        this.vagas = vagas;
        this.status = status;
        this.inicio = inicio;
        this.autor = autor;
        this.responsavel = responsavel;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public int getCarga_horaria() {
        return carga_horaria;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }

    public Usuarios getAutor() {
        return autor;
    }

    public Usuarios getResponsavel() {
        return responsavel;
    }

    public void publicar() {
        this.status = Status.publicada;
    }

    public void fecharInscricao() {
        this.status = Status.encerrada;
    }

    public void rejeitarInscricao() {
        this.status = Status.rejeitada;
    }

    @Override
    public void aprovar(Usuarios avaliador) {
        if (this.status != Status.pendente) {
            throw new OperationNotAllowedException("Oportunidade não está pendente");
        }
        this.avaliador = avaliador;
        this.publicar();
    }

    @Override
    public void rejeitar(Usuarios avaliador, String motivo) {
        this.avaliador = avaliador;
        this.motivoRejeicao = motivo;
        this.rejeitarInscricao();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Oportunidade)) return false;
        Oportunidade op = (Oportunidade) o;
        return this.titulo.equals(op.titulo);
    }

    @Override public int hashCode() { return titulo.hashCode(); }

}
