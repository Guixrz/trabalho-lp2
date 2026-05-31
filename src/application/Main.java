package application;

import java.util.Locale;
import java.util.Scanner;

import frontTestes.Front;
import repositorios.*;
import services.*;
import entidades.*;
import entidades.enums.*;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        // repositorios
        UsuarioRepositorio usuarioRepo = new UsuarioRepositorio();
        OportunidadeRepositorio oportunidadeRepo = new OportunidadeRepositorio();
        InscricaoRepositorio inscricaoRepo = new InscricaoRepositorio();
        AproveitamentoRepositorio aproveitamentoRepo = new AproveitamentoRepositorio();
        GrupoRepositorio grupoRepo = new GrupoRepositorio();

        // serviços
        UsuarioService usuarioService = new UsuarioService(usuarioRepo, aproveitamentoRepo, inscricaoRepo);
        OportunidadeService oportunidadeService = new OportunidadeService(oportunidadeRepo);
        InscricaoService inscricaoService = new InscricaoService(inscricaoRepo, oportunidadeRepo);
        AproveitamentoService aproveitamentoService = new AproveitamentoService(aproveitamentoRepo);
        PerfilService perfilService = new PerfilService(usuarioRepo);
        AdminService adminService = new AdminService(usuarioRepo);
        GrupoService grupoService = new GrupoService(grupoRepo);

        // dados de exemplo
        Curso curso = new Curso("Engenharia de Software", 123, 3000);
        Discente discente = new Discente("João Silva", "joao@email.com", "senha",
                PapelSistema.discente, "2021001", 1, curso);
        usuarioRepo.salvar(discente);

        Docente docente = new Docente("Prof Maria", "maria@email.com", "senha",
                PapelSistema.docente, "siape123", "Computação");
        usuarioRepo.salvar(docente);

        // oportunidade de exemplo
        Oportunidade opp1 = new Oportunidade("Estágio em TI", "Descrição do estágio",
                Tipo.estagio, Modalidade.presencial, 20, 5, Status.em_progresso,
                LocalDateTime.now(), docente, docente);
        oportunidadeRepo.salvar(opp1);

        Oportunidade opp2 = new Oportunidade("Projeto de Pesquisa", "Pesquisa acadêmica",
                Tipo.pesquisa, Modalidade.remoto, 40, 2, Status.pendente,
                LocalDateTime.now(), docente, docente);
        oportunidadeRepo.salvar(opp2);

        // front
        Front front = new Front(sc,
                usuarioService,
                oportunidadeService,
                inscricaoService,
                perfilService,
                aproveitamentoService,
                adminService,
                grupoService);

        front.start();

        sc.close();
    }
}