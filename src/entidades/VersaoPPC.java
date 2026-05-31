package entidades;

import java.time.LocalDate;

public class VersaoPPC {
    private String versao;
    private int cargaHoraria;
    private LocalDate dataCriacao;
    private Coordenador coordenador;

    public VersaoPPC(String versao,int cargaHoraria,LocalDate dataCriacao,Coordenador coordenador) {
        this.versao = versao;
        this.cargaHoraria = cargaHoraria;
        this.dataCriacao = dataCriacao;
        this.coordenador = coordenador;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Coordenador getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Coordenador coordenador) {
        this.coordenador = coordenador;
    }
}
