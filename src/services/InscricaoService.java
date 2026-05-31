package services;

import entidades.Discente;
import entidades.Docente;
import entidades.Inscricao;
import entidades.Oportunidade;
import entidades.enums.Status;
import excecoes.IntegrityException;
import excecoes.InvalidDataException;
import excecoes.NotFoundException;
import excecoes.OperationNotAllowedException;
import repositorios.InscricaoRepositorio;
import repositorios.OportunidadeRepositorio;

import java.time.LocalDate;
import java.util.List;

public class InscricaoService {

    private InscricaoRepositorio inscricaoRepo;
    private OportunidadeRepositorio oportunidadeRepo;

    public InscricaoService(InscricaoRepositorio inscricaoRepo,
                            OportunidadeRepositorio oportunidadeRepo) {
        this.inscricaoRepo = inscricaoRepo;
        this.oportunidadeRepo = oportunidadeRepo;
    }

    public int inscrever(Discente d, Oportunidade o) {
        if (o.getStatus() != Status.em_progresso) {
            throw new OperationNotAllowedException("Não é possível se inscrever em uma oportunidade que não esteja em progresso");
        }
        if (inscricaoRepo.buscarDiscenteOportunidade(d, o) != null) {
            throw new IntegrityException("Discente já inscrito nesta oportunidade");
        }
        Inscricao inscricao = new Inscricao(o, d);
        inscricaoRepo.salvar(inscricao);
        System.out.println("Inscrição realizada com sucesso.");
        return 1;
    }

    public int abandonar(Discente d, Oportunidade o) {
        Inscricao i = inscricaoRepo.buscarDiscenteOportunidade(d, o);
        if (i == null) {
            throw new NotFoundException("inscricao nao encontrada");
        }
        i.abandonar();
        System.out.println("Inscrição abandonada com sucesso.");
        return 1;
    }

    public void listarInscricoes(Discente d) {
        List<Inscricao> lista = inscricaoRepo.buscarPor(d);
        if (lista.isEmpty()) {
            throw new NotFoundException("nenhuma inscricao encontrada");
        }
        for (Inscricao i : lista) {
            System.out.println("- " + i.getOportunidade().getTitulo() + " | Status: " + i.getStatus());
        }
    }

    public int substituirParticipante(Inscricao inscricaoAntiga, Discente novoDiscente) {
        if (inscricaoAntiga == null || novoDiscente == null) {
            throw new InvalidDataException("Inscrição antiga ou novo discente não podem ser nulos");
        }

        Oportunidade oportunidade = inscricaoAntiga.getOportunidade();

        if (oportunidade.getStatus() != Status.em_progresso) {
            throw new OperationNotAllowedException("oportunidade nao esta em progresso!");
        }

        if (inscricaoRepo.buscarDiscenteOportunidade(novoDiscente, oportunidade) != null) {
            throw new OperationNotAllowedException("O novo discente já está inscrito nesta oportunidade");
        }

        inscricaoAntiga.setStatus(Status.cancelada);
        System.out.println("Inscrição de " + inscricaoAntiga.getDiscente().getNome() + " foi cancelada.");

        Inscricao novaInscricao = new Inscricao(oportunidade, novoDiscente);
        novaInscricao.setStatus(Status.aprovada);
        inscricaoRepo.salvar(novaInscricao);

        System.out.println("Substituição realizada: " + novoDiscente.getNome() + " agora ocupa a vaga.");
        return 1;
    }

    public int aprovarInscricao(Inscricao inscricao, Docente responsavel) {
        if (inscricao == null || responsavel == null) {
            throw new  InvalidDataException("Inscricao ou responsavel nulo");
        }
        if (inscricao.getStatus() != Status.pendente) {
            throw new OperationNotAllowedException("Apenas inscrições pendentes podem ser aprovadas");
        }
        inscricao.aprovar(responsavel);
        System.out.println("Inscrição de " + inscricao.getDiscente().getNome() + " aprovada por " + responsavel.getNome());
        return 1;
    }

    public int rejeitarInscricao(Inscricao inscricao, Docente responsavel, String motivo) {
        if (inscricao == null || responsavel == null) {
            throw new  InvalidDataException("Inscricao ou responsavel nulo");
        }
        if (inscricao.getStatus() != Status.pendente) {
            throw new OperationNotAllowedException("Apenas inscrições pendentes podem ser rejeitadas");
        }
        inscricao.rejeitar(responsavel, motivo);
        System.out.println("Inscrição de " + inscricao.getDiscente().getNome() + " rejeitada por " + responsavel.getNome() + ". Motivo: " + motivo);
        return 1;
    }

    public List<Inscricao> getTodasAsInscricoes() {
        return inscricaoRepo.listarTodos();
    }

    public List<Inscricao> getInscricoes(Discente d) {
        return inscricaoRepo.buscarPor(d);
    }
}
