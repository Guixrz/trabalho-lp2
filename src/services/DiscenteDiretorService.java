package services;

import entidades.DiscenteDiretor;
import entidades.Docente;
import entidades.Oportunidade;
import entidades.Usuarios;
import entidades.enums.Modalidade;
import entidades.enums.Status;
import entidades.enums.Tipo;
import repositorios.OportunidadeRepositorio;

import java.time.LocalDateTime;

public class DiscenteDiretorService {

    private OportunidadeRepositorio oportunidadeRepo;
    private InscricaoService inscricaoService;

    public DiscenteDiretorService(OportunidadeRepositorio oportunidadeRepo, InscricaoService inscricaoService) {
        this.oportunidadeRepo = oportunidadeRepo;
        this.inscricaoService = inscricaoService;
    }

    public void submeterOportunidade(DiscenteDiretor diretor, String titulo, String descricao,
                                     Tipo tipo, Modalidade modalidade, int carga_horaria, int vagas,
                                     Status status, LocalDateTime inicio, LocalDateTime fim, Usuarios autor,
                                     Docente responsavel) {
        Oportunidade op = diretor.criarOportunidade(titulo,descricao,tipo,modalidade,
                carga_horaria,vagas,status,
                inicio,autor,responsavel);
        oportunidadeRepo.salvar(op);
        System.out.println("oportunidade salvo com sucesso!");
    }

    public void listarOportunidadesGrupo(DiscenteDiretor diretor) {
        System.out.println("--- oportunidades do grupo: "
                + diretor.getGrupo().getNome() + " ---");
        for (Oportunidade o : oportunidadeRepo.listarTodos()) {
            if (o.getAutor().equals(diretor)) {
                System.out.println("- " + o.getTitulo() + " | " + o.getStatus());
            }
        }
    }

    public void solicitarAprovacaoGrupo(DiscenteDiretor diretor) {
        // TODO: envolve comunicado ao coordenador
        System.out.println("solicitação registrada para o grupo: "
                + diretor.getGrupo().getNome());
    }

    public void emitirComunicado(DiscenteDiretor diretor, String mensagem) {
        // TODO: envolve envio de email
        System.out.println("[COMUNICADO - " + diretor.getGrupo().getNome()
                + "]: " + mensagem);
    }

}
