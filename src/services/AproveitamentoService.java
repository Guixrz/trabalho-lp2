package services;

import entidades.Aproveitamento;
import entidades.Discente;
import entidades.Usuarios;
import entidades.enums.Status;
import repositorios.AproveitamentoRepositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AproveitamentoService {

    private AproveitamentoRepositorio aproveitamentoRepositorio;

    public AproveitamentoService(AproveitamentoRepositorio aproveitamentoRepositorio) {
        this.aproveitamentoRepositorio = aproveitamentoRepositorio;
    }

    public void submeter(Discente discente, String descricao, String instituicao, int horas) {
        Aproveitamento ap = new Aproveitamento(discente, descricao, instituicao, horas);
        aproveitamentoRepositorio.salvar(ap);
        System.out.println("Solicitação de aproveitamento submetida com sucesso!");
    }

    public List<Aproveitamento> listarPendentes() {
        return aproveitamentoRepositorio.listarTodos().stream()
                .filter(ap -> ap.getStatus() == Status.pendente)
                .collect(Collectors.toList());
    }

    public void mostrarPendentes() {
        List<Aproveitamento> pendentes = listarPendentes();
        if (pendentes.isEmpty()) {
            System.out.println("Nenhuma solicitação de aproveitamento pendente.");
            return;
        }
        for (Aproveitamento ap : pendentes) {
            System.out.println("- " + ap.getDiscente().getNome()
                    + " | " + ap.getDescricao()
                    + " | " + ap.getHoras() + "h"
                    + " | Prazo: " + ap.getDataLimite()); // RF022
        }
    }

    public void aprovar(Aproveitamento ap, Usuarios avaliador) {
        ap.aprovar(avaliador);
        System.out.println("Aproveitamento aprovado para: " + ap.getDiscente().getNome());
    }

    public void rejeitar(Aproveitamento ap, Usuarios avaliador, String motivo) {
        ap.rejeitar(avaliador, motivo);
        System.out.println("Aproveitamento rejeitado. Motivo: " + motivo);
    }


    public void verificarPrazosVencidos() {
        LocalDate hoje = LocalDate.now();
        System.out.println("--- Verificando Prazos Vencidos ---");
        for (Aproveitamento ap : aproveitamentoRepositorio.listarTodos()) {
            if (ap.getStatus() == Status.pendente && ap.getDataLimite() != null && ap.getDataLimite().isBefore(hoje)) {
                System.out.println("ALERTA: Prazo vencido para a solicitação de " +
                        ap.getDiscente().getNome() + " (" + ap.getDescricao() + ").");
            }
        }
        System.out.println("------------------------------------");
    }

    public void reenviarSolicitacao(Aproveitamento aproveitamento) {
        if (aproveitamento != null && aproveitamento.getStatus() == Status.rejeitada) {
            aproveitamento.reenviar();
            System.out.println("Solicitação de '" + aproveitamento.getDescricao() + "' foi reenviada para análise.");
        } else {
            System.out.println("Esta solicitação não pode ser reenviada.");
        }
    }
}