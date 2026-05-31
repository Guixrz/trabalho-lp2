package frontTestes;

import entidades.*;
import entidades.enums.PapelSistema;
import excecoes.AppException;
import services.*;

import java.util.*;

public class Front {

    private final Scanner sc;
    private final UsuarioService usuarioService;
    private final OportunidadeService oportunidadeService;
    private final InscricaoService inscricaoService;
    private final PerfilService perfilService;
    private final AproveitamentoService aproveitamentoService;
    private final AdminService adminService;
    private final GrupoService grupoService;
    private final Deque<VersaoPPC> versoesIniciais = new LinkedList<>();

    private Usuarios usuarioLogado;

    public Front(Scanner sc,
                 UsuarioService usuarioService,
                 OportunidadeService oportunidadeService,
                 InscricaoService inscricaoService,
                 PerfilService perfilService,
                 AproveitamentoService aproveitamentoService,
                 AdminService adminService,
                 GrupoService grupoService) {
        this.sc = sc;
        this.usuarioService = usuarioService;
        this.oportunidadeService = oportunidadeService;
        this.inscricaoService = inscricaoService;
        this.perfilService = perfilService;
        this.aproveitamentoService = aproveitamentoService;
        this.adminService = adminService;
        this.grupoService = grupoService;
        this.usuarioLogado = null;
    }

    public void start() {
        System.out.println("Gerenciamento de Extensão (versão CLI)");
        boolean running = true;
        while (running) {
            try {
                if (usuarioLogado == null) {
                    running = loginMenu();
                } else {
                    switch (usuarioLogado.getPapel()) {
                        case discente:
                            menuDiscente();
                            break;
                        case docente:
                            menuDocente();
                            break;
                        case admin:
                            menuAdmin();
                            break;
                        default:
                            System.out.println("Papel não suportado ainda.");
                            usuarioLogado = null;
                            break;
                    }
                }
            } catch (AppException ae) {
                System.out.println("Erro: " + ae.getMessage());
            } catch (InputMismatchException ime) {
                System.out.println("Entrada inválida. Tente novamente.");
                sc.nextLine(); // limpa token inválido
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Programa encerrado.");
    }

    private boolean loginMenu() {
        System.out.println("\n--- Menu Inicial ---");
        System.out.println("1. Login");
        System.out.println("2. Cadastrar usuário");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
        int opcao = Integer.parseInt(sc.nextLine().trim());
        switch (opcao) {
            case 1:
                login();
                return true;
            case 2:
                cadastrarUsuario();
                return true;
            case 0:
                return false;
            default:
                System.out.println("Opção inválida.");
                return true;
        }
    }

    private void login() {
        System.out.println("\n--- Login ---");
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Senha: ");
        String senha = sc.nextLine().trim();
        Usuarios u = usuarioService.login(email, senha);
        if (u == null) {
            System.out.println("Falha no login. Tente novamente.");
        } else {
            usuarioLogado = u;
        }
    }

    private void cadastrarUsuario() {
        System.out.println("\n--- Cadastro de Usuário ---");
        System.out.println("Tipos disponíveis: discente, docente, admin");
        System.out.print("Escolha o tipo: ");
        String tipo = sc.nextLine().trim().toLowerCase();

        System.out.print("Nome: ");
        String nome = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Senha: ");
        String senha = sc.nextLine().trim();

        try {
            switch (tipo) {
                case "discente":
                    System.out.print("Matrícula: ");
                    String matricula = sc.nextLine().trim();
                    System.out.print("Semestre atual (numero): ");
                    int semestre = Integer.parseInt(sc.nextLine().trim());

                    System.out.print("Deseja vincular um curso agora? (s/n): ");
                    String resp = sc.nextLine().trim().toLowerCase();
                    Curso curso = null;
                    if (resp.equals("s") || resp.equals("sim")) {
                        System.out.print("Nome do curso: ");
                        String nomeCurso = sc.nextLine().trim();
                        System.out.print("Código do curso (numero): ");
                        int codigo = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Carga horária total: ");
                        int carga = Integer.parseInt(sc.nextLine().trim());
                        curso = new Curso(nomeCurso, codigo, carga, versoesIniciais);
                    }

                    Discente d = new Discente(nome, email, senha, entidades.enums.PapelSistema.discente, matricula, semestre, curso);
                    usuarioService.cadastrarUsuario(d);
                    System.out.println("Discente cadastrado com sucesso.");
                    break;

                case "docente":
                    System.out.print("SIAPE: ");
                    String siape = sc.nextLine().trim();
                    System.out.print("Departamento: ");
                    String departamento = sc.nextLine().trim();
                    Docente doc = new Docente(nome, email, senha, entidades.enums.PapelSistema.docente, siape, departamento);
                    usuarioService.cadastrarUsuario(doc);
                    System.out.println("Docente cadastrado com sucesso.");
                    break;

                case "admin":
                    Admin admin = new Admin(nome, email, senha, entidades.enums.PapelSistema.admin);
                    usuarioService.cadastrarUsuario(admin);
                    System.out.println("Admin cadastrado com sucesso.");
                    break;

                default:
                    System.out.println("Tipo inválido. Use 'discente', 'docente' ou 'admin'.");
            }
        } catch (AppException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        } catch (NumberFormatException nfe) {
            System.out.println("Entrada numérica inválida. Cadastro cancelado.");
        }
    }

    private void menuDiscente() {
        boolean stay = true;
        while (stay && usuarioLogado != null) {
            System.out.println("\n--- Menu Discente ---");
            System.out.println("1. Listar oportunidades abertas");
            System.out.println("2. Inscrever-se em oportunidade");
            System.out.println("3. Listar minhas inscrições");
            System.out.println("4. Abandonar inscrição");
            System.out.println("0. Sair (logout)");
            System.out.print("Escolha: ");
            int opcao = Integer.parseInt(sc.nextLine().trim());
            try {
                switch (opcao) {
                    case 1:
                        oportunidadeService.mostrarOportunidades();
                        break;
                    case 2:
                        List<Oportunidade> abertas = oportunidadeService.listarAbertas();
                        if (abertas.isEmpty()) {
                            System.out.println("Nenhuma oportunidade aberta.");
                            break;
                        }
                        oportunidadeService.mostrarOportunidades();
                        System.out.print("Escolha o índice da oportunidade: ");
                        int idx = Integer.parseInt(sc.nextLine().trim());
                        if (idx < 0 || idx >= abertas.size()) {
                            System.out.println("Índice inválido.");
                            break;
                        }
                        Oportunidade opp = abertas.get(idx);
                        int result = inscricaoService.inscrever((Discente) usuarioLogado, opp);
                        if (result == 1) System.out.println("Inscrição realizada com sucesso.");
                        else System.out.println("Falha na inscrição.");
                        break;
                    case 3:
                        inscricaoService.listarInscricoes((Discente) usuarioLogado);
                        break;
                    case 4:
                        List<Inscricao> inscricoes = inscricaoService.getInscricoes((Discente) usuarioLogado);
                        if (inscricoes.isEmpty()) {
                            System.out.println("Nenhuma inscrição encontrada.");
                            break;
                        }
                        for (int i = 0; i < inscricoes.size(); i++) {
                            Inscricao it = inscricoes.get(i);
                            System.out.println("[" + i + "] " + it.getOportunidade().getTitulo() + " | Status: " + it.getStatus());
                        }
                        System.out.print("Escolha o índice da inscrição para abandonar: ");
                        int idxAbandonar = Integer.parseInt(sc.nextLine().trim());
                        if (idxAbandonar < 0 || idxAbandonar >= inscricoes.size()) {
                            System.out.println("Índice inválido.");
                            break;
                        }
                        Inscricao insc = inscricoes.get(idxAbandonar);
                        int resAbandonar = inscricaoService.abandonar((Discente) usuarioLogado, insc.getOportunidade());
                        if (resAbandonar == 1) System.out.println("Inscrição abandonada.");
                        else System.out.println("Falha ao abandonar.");
                        break;
                    case 0:
                        usuarioLogado = null;
                        stay = false;
                        System.out.println("Logout realizado.");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (AppException ae) {
                System.out.println("Erro: " + ae.getMessage());
            }
        }
    }

    private void menuDocente() {
        boolean stay = true;
        while (stay && usuarioLogado != null) {
            System.out.println("\n--- Menu Docente ---");
            System.out.println("1. Listar oportunidades pendentes");
            System.out.println("2. Aprovar oportunidade");
            System.out.println("3. Rejeitar oportunidade");
            System.out.println("0. Sair (logout)");
            System.out.print("Escolha: ");
            int opcao = Integer.parseInt(sc.nextLine().trim());
            try {
                switch (opcao) {
                    case 1:
                        oportunidadeService.mostrarPendentes();
                        break;
                    case 2:
                        List<Oportunidade> pendentes = oportunidadeService.listarPendentes();
                        if (pendentes.isEmpty()) {
                            System.out.println("Nenhuma oportunidade pendente.");
                            break;
                        }
                        oportunidadeService.mostrarPendentes();
                        System.out.print("Escolha índice para aprovar: ");
                        int idxAprovar = Integer.parseInt(sc.nextLine().trim());
                        if (idxAprovar < 0 || idxAprovar >= pendentes.size()) {
                            System.out.println("Índice inválido.");
                            break;
                        }
                        Oportunidade oppA = pendentes.get(idxAprovar);
                        oportunidadeService.aprovar(oppA, (Docente) usuarioLogado);
                        break;
                    case 3:
                        List<Oportunidade> pendentesR = oportunidadeService.listarPendentes();
                        if (pendentesR.isEmpty()) {
                            System.out.println("Nenhuma oportunidade pendente.");
                            break;
                        }
                        oportunidadeService.mostrarPendentes();
                        System.out.print("Escolha índice para rejeitar: ");
                        int idxR = Integer.parseInt(sc.nextLine().trim());
                        if (idxR < 0 || idxR >= pendentesR.size()) {
                            System.out.println("Índice inválido.");
                            break;
                        }
                        Oportunidade oppR = pendentesR.get(idxR);
                        oportunidadeService.rejeitar(oppR, (Docente) usuarioLogado);
                        break;
                    case 0:
                        usuarioLogado = null;
                        stay = false;
                        System.out.println("Logout realizado.");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (AppException ae) {
                System.out.println("Erro: " + ae.getMessage());
            }
        }
    }

    private void menuAdmin() {
        boolean stay = true;
        while (stay && usuarioLogado != null) {
            System.out.println("\n--- Menu Admin ---");
            System.out.println("1. Cadastrar gestor");
            System.out.println("2. Listar usuários");
            System.out.println("3. Ativar usuário");
            System.out.println("4. Desativar usuário");
            System.out.println("5. Analisar aproveitamentos pendentes");
            System.out.println("6. Gerenciar oportunidades (encerrar)");
            System.out.println("0. Sair (logout)");
            System.out.print("Escolha: ");
            int opcao = Integer.parseInt(sc.nextLine().trim());
            try {
                switch (opcao) {
                    case 1:
                        System.out.print("Email do usuário a promover: ");
                        String emailUser = sc.nextLine().trim();
                        System.out.println("Papeis disponíveis: admin, coord_curso, coord_uce");
                        System.out.print("Escolha novo papel: ");
                        String papel = sc.nextLine().trim();
                        PapelSistema novoPapel;
                        try {
                            novoPapel = PapelSistema.valueOf(papel);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Papel inválido.");
                            break;
                        }
                        adminService.cadastrarGestor(usuarioLogado, emailUser, novoPapel);
                        break;
                    case 2:
                        perfilService.listarUsuarios();
                        break;
                    case 3:
                        System.out.print("Email do usuário a ativar: ");
                        String emailAtivar = sc.nextLine().trim();
                        perfilService.ativar(emailAtivar);
                        break;
                    case 4:
                        System.out.print("Email do usuário a desativar: ");
                        String emailDes = sc.nextLine().trim();
                        perfilService.desativar(emailDes);
                        break;
                    case 5:
                        aproveitamentoService.mostrarPendentes();
                        break;
                    case 6:
                        List<Oportunidade> emProgresso = oportunidadeService.listarAbertas();
                        if (emProgresso.isEmpty()) {
                            System.out.println("Nenhuma oportunidade em progresso.");
                            break;
                        }
                        oportunidadeService.mostrarOportunidades();
                        System.out.print("Escolha índice para encerrar: ");
                        int idx = Integer.parseInt(sc.nextLine().trim());
                        if (idx < 0 || idx >= emProgresso.size()) {
                            System.out.println("Índice inválido.");
                            break;
                        }
                        Oportunidade escolha = emProgresso.get(idx);
                        oportunidadeService.fecharInscricaoEGerarLista(escolha, inscricaoService);
                        break;
                    case 0:
                        usuarioLogado = null;
                        stay = false;
                        System.out.println("Logout realizado.");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (AppException ae) {
                System.out.println("Erro: " + ae.getMessage());
            }
        }
    }
}