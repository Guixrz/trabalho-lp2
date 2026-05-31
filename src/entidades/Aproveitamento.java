package entidades;

import entidades.enums.Status;
import interfaces.Aprovavel;

import java.time.LocalDate;

public class Aproveitamento implements Aprovavel<Usuarios> {

    private Discente discente;
    private String descricao;
    private String instituicao;
    private int horas;
    private Status status;
    private Usuarios avaliador;
    private String motivo_rejeicao;
    private LocalDate dataLimite;
    private String id;

    public Aproveitamento(Discente discente, String descricao, String instituicao, int horas) {
        this.discente = discente;
        this.descricao = descricao;
        this.instituicao = instituicao;
        this.horas = horas;
        this.status = Status.pendente;
        this.dataLimite = LocalDate.now().plusDays(15);
        this.id = discente.getMatricula() + "_" + System.currentTimeMillis();
    }

    public Aproveitamento(Discente discente, String descricao, String instituicao, int horas, LocalDate dataLimite) {
        this.discente = discente;
        this.descricao = descricao;
        this.instituicao = instituicao;
        this.horas = horas;
        this.status = Status.pendente;
        this.dataLimite = (dataLimite != null) ? dataLimite : LocalDate.now().plusDays(15);
    }

    public String getId() {
        return id;
    }
    public Discente getDiscente() { return discente; }
    public void setDiscente(Discente discente) { this.discente = discente; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getInstituicao() { return instituicao; }
    public void setInstituicao(String instituicao) { this.instituicao = instituicao; }
    public int getHoras() { return horas; }
    public void setHoras(int horas) { this.horas = horas; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Usuarios getAvaliador() { return avaliador; }
    public String getMotivo_rejeicao() { return motivo_rejeicao; }
    public LocalDate getDataLimite() { return dataLimite; }
    public void setDataLimite(LocalDate dataLimite) { this.dataLimite = dataLimite; }

    @Override
    public void rejeitar(Usuarios avaliador, String motivo_rejeicao) {
        this.avaliador = avaliador;
        this.motivo_rejeicao = motivo_rejeicao;
        this.status = Status.rejeitada;
    }

    @Override
    public void aprovar(Usuarios avaliador) {
        this.avaliador = avaliador;
        this.status = Status.aprovada;
    }

    public void reenviar() {
        if (this.status == Status.rejeitada) {
            this.status = Status.pendente;
            this.avaliador = null;
            this.motivo_rejeicao = null;
            this.dataLimite = LocalDate.now().plusDays(15);
        } else {
            System.out.println("Apenas solicitações rejeitadas podem ser reenviadas.");
        }
    }
}