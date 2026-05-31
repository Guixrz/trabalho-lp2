package entidades;

import entidades.enums.Status;
import excecoes.OperationNotAllowedException;
import interfaces.Aprovavel;

import java.time.LocalDate;

public class Inscricao implements Aprovavel<Usuarios> {

    private Oportunidade oportunidade;
    private Discente discente;
    private Status status;
    private String motivacao;
    private LocalDate dataAprovacao;
    private LocalDate dataLimiteCoord;
    private Usuarios avaliador;

    public Inscricao(Oportunidade o,Discente d){
        this.oportunidade = o;
        this.discente = d;
        this.status = Status.pendente;
    }

    public Inscricao(Oportunidade o,Discente d,LocalDate dataLimiteCoord){
        this.oportunidade = o;
        this.discente = d;
        this.status = Status.pendente;
        if(dataLimiteCoord != null) {
            this.dataLimiteCoord = dataLimiteCoord;
        } else {
            this.dataLimiteCoord = LocalDate.now().plusDays(7);
        }
    }

    public Inscricao(Oportunidade oportunidade, Discente discente, Status status, String motivacao) {
        this.oportunidade = oportunidade;
        this.discente = discente;
        this.status = status;
        this.motivacao = motivacao;
    }

    public Oportunidade getOportunidade() {
        return oportunidade;
    }

    public Discente getDiscente() {
        return discente;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }

    public LocalDate getDataAprovacao() {
        return dataAprovacao;
    }

    public LocalDate getDataLimiteCoord() {
        return dataLimiteCoord;
    }

    public void setDataLimiteCoord(LocalDate dataLimiteCoord) {
        this.dataLimiteCoord = dataLimiteCoord;
    }

    @Override
    public void aprovar(Usuarios avaliador) {
        if (this.status != Status.pendente) {
            throw new OperationNotAllowedException("Oportunidade não está pendente");
        }
        this.avaliador = avaliador;
        this.status = Status.aprovada;
        this.dataAprovacao = LocalDate.now(); // registra agora
    }

    @Override
    public void rejeitar(Usuarios avaliador, String motivo) {
        if (this.status != Status.pendente) {
            throw new OperationNotAllowedException("Oportunidade não está pendente");
        }
        this.avaliador = avaliador;
        this.status = Status.rejeitada;
        this.motivacao = motivo; // reutiliza campo existente
    }

    public void abandonar() {
        this.status = Status.abandonada;
    }

    public void reenviar(String novaMotivacao) {
        this.motivacao = novaMotivacao;
        this.status = Status.pendente;
        this.dataAprovacao = null;
    }
}