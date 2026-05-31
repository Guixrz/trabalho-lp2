package services;

import entidades.*;
import entidades.enums.Status;
import repositorios.OportunidadeRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OportunidadeService {

    private OportunidadeRepositorio repositorio;

    public OportunidadeService(OportunidadeRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Oportunidade> listarAbertas() {
        return repositorio.listarTodos().stream()
                .filter(o -> o.getStatus() == Status.em_progresso)
                .collect(Collectors.toList());
    }

    public void mostrarOportunidades() {
        List<Oportunidade> lista = listarAbertas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma oportunidade disponível.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            Oportunidade o = lista.get(i);
            System.out.println("[" + i + "] " + o.getTitulo()
                    + " | " + o.getTipo()
                    + " | Vagas: " + o.getVagas());
        }
    }

    public void aprovar(Oportunidade oportunidade, Usuarios avaliador) {
        if (oportunidade.getStatus() != Status.pendente) {
            System.out.println("Oportunidade não está pendente.");
            return;
        }
        oportunidade.aprovar(avaliador);
        System.out.println("Oportunidade aprovada por " + avaliador.getNome());
    }

    public void rejeitar(Oportunidade oportunidade, Docente docente) {
        oportunidade.rejeitarInscricao();
        System.out.println("Oportunidade rejeitada por " + docente.getNome());
    }

    public List<Oportunidade> listarPendentes() {
        return repositorio.listarTodos().stream()
                .filter(o -> o.getStatus() == Status.pendente)
                .collect(Collectors.toList());
    }

    public void mostrarPendentes() {
        List<Oportunidade> lista = listarPendentes();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma oportunidade pendente.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            Oportunidade o = lista.get(i);
            System.out.println(i + " - " + o.getTitulo() + " | Submetida por: " + o.getAutor().getNome());
        }
    }

    public void fecharInscricaoEGerarLista(Oportunidade oportunidade, InscricaoService inscricaoService) {
        if (oportunidade == null) {
            System.out.println("Oportunidade não pode ser nula.");
            return;
        }

        if (oportunidade.getStatus() != Status.em_progresso) {
            System.out.println("Apenas oportunidades em progresso podem ser encerradas.");
            return;
        }

        oportunidade.setStatus(Status.encerrada);
        System.out.println("Oportunidade '" + oportunidade.getTitulo() + "' foi encerrada.");

        List<Inscricao> aprovadas = inscricaoService.getTodasAsInscricoes().stream()
                .filter(i -> i.getOportunidade().equals(oportunidade) && i.getStatus() == Status.aprovada)
                .collect(Collectors.toList());

        if (aprovadas.isEmpty()) {
            System.out.println("Não há participantes aprovados para certificação.");
        } else {
            System.out.println("--- Lista de Participantes para Certificação ---");
            for (Inscricao inscricao : aprovadas) {
                Discente discente = inscricao.getDiscente();
                System.out.println("  - Nome: " + discente.getNome() + " | Matrícula: " + discente.getMatricula());
            }
            System.out.println("-------------------------------------------------");
        }
    }
}
