package entidades;

import entidades.enums.PapelCargo;

import java.time.LocalDate;

public class HistoricoCargo {

    private Usuarios usuario;
    private Grupo grupo;
    private PapelCargo papelCargo;
    private LocalDate dataAtribuicao;
    private LocalDate dataRemocao;
    private String motivo;

    public HistoricoCargo(Usuarios usuario,Grupo grupo,PapelCargo papelCargo,
                          LocalDate dataAtribuicao, LocalDate dataRemocao, String motivo) {
        this.usuario = usuario;
        this.grupo = grupo;
        this.papelCargo = papelCargo;
        this.dataAtribuicao = dataAtribuicao;
        this.dataRemocao = dataRemocao;
        this.motivo = motivo;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public PapelCargo getPapelCargo() {
        return papelCargo;
    }

    public void setPapelCargo(PapelCargo papelCargo) {
        this.papelCargo = papelCargo;
    }

    public LocalDate getDataAtribuicao() {
        return dataAtribuicao;
    }

    public void setDataAtribuicao(LocalDate dataAtribuicao) {
        this.dataAtribuicao = dataAtribuicao;
    }

    public LocalDate getDataRemocao() {
        return dataRemocao;
    }

    public void setDataRemocao(LocalDate dataRemocao) {
        this.dataRemocao = dataRemocao;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
