package entidades;

import entidades.enums.PapelSistema;
import entidades.enums.PapelCargo;
import entidades.enums.Status;
import entidades.enums.Tipo;
import entidades.enums.Modalidade;
import interfaces.CriadorOportunidade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Docente extends Usuarios implements CriadorOportunidade {

    private String siape;
    private String departamento;
    private ArrayList<HistoricoCargo> historicoCargo = new ArrayList<>();

    public Docente(String nome, String email, String senha, PapelSistema papelSistema, String siape, String departamento) {
        super(nome, email, senha, papelSistema);
        this.siape = siape;
        this.departamento = departamento;
    }

    public String getSiepe() {
        return siape;
    }

    public void setSiepe(String siape) {
        this.siape = siape;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public Oportunidade criarOportunidade(String titulo, String descricao, Tipo tipo,
                                          Modalidade modalidade, int cargaHoraria,
                                          int vagas, Status status, LocalDateTime inicio,
                                          Usuarios autor, Docente responsavel) {
        // se o autor/responsavel for null, podemos usar 'this' como autor/responsavel
        Usuarios realAutor;
        if (autor != null) {
            realAutor = autor;
        } else {
            realAutor = this;
        }
        Docente realResponsavel;
        if (responsavel != null) {
            realResponsavel = responsavel;
        } else {
            realResponsavel = this;
        }
        LocalDateTime inicioDateTime;
        if (inicio != null) {
            inicioDateTime = inicio;
        } else {
            inicioDateTime = LocalDate.now().atStartOfDay();
        }

        Oportunidade oportunidade = new Oportunidade(titulo, descricao, tipo, modalidade,
                cargaHoraria, vagas, status, inicioDateTime, realAutor, realResponsavel);

        return oportunidade;
    }

    public void registrarPlanoAtividade(Oportunidade oportunidade, LocalDate dataInicio) {
        if (oportunidade == null) {
            System.out.println("oportunidade nao existe");
            return;
        }

        if (!oportunidade.getResponsavel().equals(this) && !oportunidade.getAutor().equals(this)) {
            System.out.println("sem permissao");
            return;
        }
        if (dataInicio.isBefore(LocalDate.now())) {
            System.out.println("a data de início não pode ser anterior a hoje");
            return;
        }

        LocalDateTime dataInicioDateTime = dataInicio.atStartOfDay();

        oportunidade.setInicio(dataInicioDateTime);

        System.out.println("plano de atividade registrado com sucesso para: " + oportunidade.getTitulo());
        System.out.println("data de início: " + dataInicio);
    }

    public void atribuirCargo(Usuarios usuario, Grupo grupo, PapelCargo papel, String motivo) {
        if (usuario == null || grupo == null || papel == null) {
            System.out.println("Dados inválidos para atribuir cargo.");
            return;
        }

        if (!usuario.isAtivo()) {
            System.out.println("Usuário inativo não pode receber cargo.");
            return;
        }

        HistoricoCargo historico = new HistoricoCargo(usuario, grupo, papel,
                LocalDate.now(), null, motivo);
        this.historicoCargo.add(historico);
        grupo.adicionarHistoricoCargo(usuario, historico);

        System.out.println("Cargo '" + papel + "' atribuído a " + usuario.getNome()
                + " no grupo " + grupo.getNome());
    }

    public void removerCargo(Usuarios usuario, Grupo grupo, String motivo) {
        if (usuario == null || grupo == null) {
            System.out.println("Dados inválidos para remover cargo.");
            return;
        }

        for (HistoricoCargo h : historicoCargo) {
            if (h.getUsuario().equals(usuario) && h.getGrupo().equals(grupo) && h.getDataRemocao() == null) {
                h.setDataRemocao(LocalDate.now());
                h.setMotivo(motivo);
                System.out.println("Cargo removido de " + usuario.getNome()
                        + " no grupo " + grupo.getNome());
                return;
            }
        }
        System.out.println("Cargo não encontrado para este usuário.");
    }

    public ArrayList<HistoricoCargo> getHistoricoCargo() {
        return historicoCargo;
    }

}