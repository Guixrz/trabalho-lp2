package services;

import entidades.Docente;
import entidades.Grupo;
import entidades.enums.Status;
import repositorios.GrupoRepositorio;

import java.util.List;

public class GrupoService {

    private GrupoRepositorio grupoRepositorio;

    public GrupoService(GrupoRepositorio grupoRepositorio) {
        this.grupoRepositorio = grupoRepositorio;
    }

    public void cadastrar(String nome, String tipo, String email, String descricao,
                          Docente responsavel) {
        Grupo grupo = new Grupo(nome, tipo, email, descricao, Status.rascunho, responsavel);
        grupoRepositorio.salvar(grupo);
        System.out.println("grupo cadastro");
    }

    public void encerrar(Grupo grupo) {
        grupo.setStatus(Status.cancelada);
        System.out.println("grupo encerrado");
    }

    public void listarAtivos() {
        for (Grupo g : grupoRepositorio.listarTodos()) {
            if (g.getStatus() == Status.publicada) {
                System.out.println("- " + g.getNome()
                        + " | Resp.: " + g.getResponsavel().getNome()
                        + " | " + g.getTipo());
            }
        }
    }

    public void aprovarSolicitacao(Grupo grupo) {
        grupo.setStatus(Status.publicada);
        System.out.println("grupo aprovado");
    }

    public List<Grupo> listarTodos() {
        return grupoRepositorio.listarTodos();
    }
}