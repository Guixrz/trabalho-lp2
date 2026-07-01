package br.ufma;

import br.ufma.entidades.Curso;
import br.ufma.entidades.Discente;
import br.ufma.entidades.Docente;
import br.ufma.entidades.Oportunidade;
import br.ufma.entidades.TipoOportunidade;
import br.ufma.entidades.enums.Modalidade;
import br.ufma.entidades.enums.Status;
import br.ufma.repo.CursoRepo;
import br.ufma.repo.DiscenteRepo;
import br.ufma.repo.DocenteRepo;
import br.ufma.repo.OportunidadeRepo;
import br.ufma.repo.TipoOportunidadeRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CursoRepo cursoRepo;
    private final DiscenteRepo discenteRepo;
    private final DocenteRepo docenteRepo;
    private final OportunidadeRepo oportunidadeRepo;
    private final TipoOportunidadeRepo tipoOportunidadeRepo;

    public DataInitializer(CursoRepo cursoRepo,
                           DiscenteRepo discenteRepo,
                           DocenteRepo docenteRepo,
                           OportunidadeRepo oportunidadeRepo,
                           TipoOportunidadeRepo tipoOportunidadeRepo) {
        this.cursoRepo = cursoRepo;
        this.discenteRepo = discenteRepo;
        this.docenteRepo = docenteRepo;
        this.oportunidadeRepo = oportunidadeRepo;
        this.tipoOportunidadeRepo = tipoOportunidadeRepo;
    }

    // obs -> a ia deu essa sugestão de suprir dados iniciais para testar com o insommia
    @Override
    public void run(String... args) {
        // Evita recriar os dados a cada reinicialização se já existirem
        if (cursoRepo.count() > 0) {
            return;
        }

        // 1. Cria um Curso
        Curso curso = cursoRepo.save(Curso.builder()
                .nome("Ciencia da Computacao")
                .codigo(2026001)
                .cargaHoraria(3200)
                .build());

        // 2. Cria um Docente
        Docente docente = new Docente();
        docente.setNome("Docente Exemplo");
        docente.setEmail("docente.exemplo@ufma.br");
        docente.setSenha("senha123");
        docente.setAtivo(true);
        docente.setSiape("1234567");
        docente.setDepartamento("Computacao");
        docenteRepo.save(docente);

        // 3. Cria um Discente
        Discente discente = new Discente();
        discente.setNome("Discente Exemplo");
        discente.setEmail("discente.exemplo@ufma.br");
        discente.setSenha("senha123");
        discente.setAtivo(true);
        discente.setMatricula("2024001001");
        discente.setSemestreAtual(4);
        discente.setCurso(curso); // Associa ao curso criado
        discenteRepo.save(discente);

        // 4. Cria um Tipo de Oportunidade
        TipoOportunidade tipoOportunidade = tipoOportunidadeRepo.save(TipoOportunidade.builder()
                .nome("Projeto de Extensao")
                .build());

        // 5. Cria uma Oportunidade
        oportunidadeRepo.save(Oportunidade.builder()
                .titulo("Monitoria de LP2")
                .descricao("Vaga para monitor de Laboratorio de Programacao 2")
                .tipo(tipoOportunidade) // Associa ao tipo criado
                .modalidade(Modalidade.presencial)
                .carga_horaria(80)
                .vagas(2)
                .status(Status.pendente)
                .inicio(LocalDateTime.now().plusDays(1))
                .fim(LocalDateTime.now().plusMonths(6))
                .autor(docente) // Associa ao docente criado
                .responsavel(docente) // Associa ao docente criado
                .build());
    }
}
