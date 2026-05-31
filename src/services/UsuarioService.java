package services;

import entidades.Aproveitamento;
import entidades.Discente;
import entidades.Inscricao;
import entidades.Usuarios;
import entidades.enums.Status;
import excecoes.IntegrityException;
import excecoes.InvalidDataException;
import excecoes.NotFoundException;
import excecoes.OperationNotAllowedException;
import repositorios.AproveitamentoRepositorio;
import repositorios.InscricaoRepositorio;
import repositorios.UsuarioRepositorio;

import java.util.List;

public class UsuarioService {

    private final UsuarioRepositorio usuariosRepo;
    private final AproveitamentoRepositorio aproveitamentoRepo;
    private final InscricaoRepositorio inscricaoRepo;

    public UsuarioService(UsuarioRepositorio usuariosRepo, AproveitamentoRepositorio aproveitamentoRepo, InscricaoRepositorio inscricaoRepo) {
        this.usuariosRepo = usuariosRepo;
        this.aproveitamentoRepo = aproveitamentoRepo;
        this.inscricaoRepo = inscricaoRepo;
    }

    public Usuarios login(String email, String senha) {
        if (email==null || senha==null) throw new InvalidDataException("Email ou senha nula");
        Usuarios usuario = usuariosRepo.buscar(email);

        if (usuario == null) {
            throw new NotFoundException("usuario nao encontrado");
        }

        if (!usuario.getSenha().equals(senha)) {
            throw new IntegrityException("senha incorreta");
        }

        if (!usuario.isAtivo()) {
            throw new IntegrityException("usuario inativo");
        }

        System.out.println("Login feito com sucesso! Bem vindo " + usuario.getNome());
        return usuario;
    }

    public void cadastrarDiscente(Discente discente) {
        if (discente == null) throw new InvalidDataException("Discente não pode ser nulo.");
        if (discente.getMatricula() == null || discente.getMatricula().trim().isEmpty()) {
            throw new InvalidDataException("Matrícula é obrigatória para o discente.");
        }
        try {
            usuariosRepo.salvar(discente);
            System.out.println("Discente cadastrado com sucesso: " + discente.getNome());
        } catch (IntegrityException e) {
            System.out.println("Erro ao cadastrar discente: " + e.getMessage());
        }
    }

    public void cadastrarUsuario(Usuarios usuario) {
        if (usuario == null) {
            throw new InvalidDataException("Usuario nulo");
        }

        if (usuario instanceof Discente) {
            cadastrarDiscente((Discente) usuario);
            return;
        }

        if (usuariosRepo.buscar(usuario.getEmail()) != null) {
            throw new OperationNotAllowedException("email ja cadastrado");
        }

        usuariosRepo.salvar(usuario);
    }

    public double calcularProgressoDiscente(Discente discente) {
        if (discente == null || discente.getCurso() == null) {
            throw new InvalidDataException("Discente ou curso inválido");
        }

        int horas = 0;

        for (Aproveitamento ap : aproveitamentoRepo.listarTodos()) {
            if (ap.getDiscente().equals(discente) && ap.getStatus() == Status.aprovada) {
                horas += ap.getHoras();
            }
        }

        for (Inscricao insc : inscricaoRepo.buscarPor(discente)) {
            if (insc.getStatus() == Status.aprovada && insc.getOportunidade().getStatus() == Status.encerrada) {
                horas += insc.getOportunidade().getCarga_horaria();
            }
        }

        int cargaHorariaTotal = discente.getCurso().getCargaHoraria();

        if (cargaHorariaTotal <= 0) {
            return 0.0;
        }

        double percentual = (100.0 * horas) / cargaHorariaTotal;

        System.out.printf("Progresso de %s: %d/%d horas (%.1f%%)%n",
                discente.getNome(), horas, cargaHorariaTotal, percentual);

        return Math.min(percentual, 100.0);
    }
}