package entidades;

import entidades.enums.Modalidade;
import entidades.enums.PapelSistema;
import entidades.enums.Status;
import entidades.enums.Tipo;
import interfaces.CriadorOportunidade;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DiscenteDiretor extends Discente implements CriadorOportunidade {

    private Grupo grupo;
    private String cargo;
    private LocalDate dataInicio;
    private LocalDate dataFim;


    public DiscenteDiretor(String nome, String email, String senha,
                           PapelSistema papelSistema, String matricula, int semestreAtual,
                           Curso curso, Grupo grupo, String cargo,
                           LocalDate dataInicio, LocalDate dataFim) {
        super(nome, email, senha, PapelSistema.discenteDiretor, matricula, semestreAtual, curso);
        this.grupo = grupo;
        this.cargo = cargo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    @Override
    public Oportunidade criarOportunidade(String titulo, String descricao, Tipo tipo,
                                          Modalidade modalidade, int carga_horaria,
                                          int vagas, Status status, LocalDateTime inicio
                                          ,Usuarios autor,Docente responsavel) {
        return new Oportunidade(titulo,descricao,tipo,modalidade,carga_horaria,vagas
        ,status,inicio,autor,responsavel);
    }

}
