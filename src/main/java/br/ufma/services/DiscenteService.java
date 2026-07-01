package br.ufma.services;

import br.ufma.entidades.Curso;
import br.ufma.entidades.Discente;
import br.ufma.entidades.enums.Status;
import br.ufma.excecoes.NotFoundException;
import br.ufma.repo.AproveitamentoRepo;
import br.ufma.repo.CursoRepo;
import br.ufma.repo.DiscenteRepo;
import br.ufma.repo.InscricaoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscenteService {

    private final DiscenteRepo discenteRepo;
    private final AproveitamentoRepo aproveitamentoRepo;
    private final InscricaoRepo inscricaoRepo;
    private final CursoRepo cursoRepo;

    @Transactional
    public Discente salvar(Discente discente, Long cursoId) {
        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new NotFoundException("Curso com ID " + cursoId + " não encontrado."));
        discente.setCurso(curso);
        return discenteRepo.save(discente);
    }

    public List<Discente> listarTodos() {
        return discenteRepo.findAll();
    }

    public Discente buscarPorId(Long id) {
        return discenteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Discente com ID " + id + " não encontrado."));
    }

    public Map<String, Object> gerarRelatorioDeHoras(Long discenteId) {
        Discente discente = buscarPorId(discenteId);

        // Soma as horas de aproveitamentos aprovados
        Integer horasAproveitamento = Optional.ofNullable(
                aproveitamentoRepo.sumHorasByDiscenteIdAndStatus(discenteId, Status.aprovada))
                .orElse(0);

        // Soma as horas de inscrições em oportunidades concluídas
        Integer horasInscricao = Optional.ofNullable(
                inscricaoRepo.sumHorasByDiscenteIdAndStatus(discenteId, Status.aprovada, Status.encerrada))
                .orElse(0);

        int horasTotais = horasAproveitamento + horasInscricao;
        int cargaHorariaCurso = discente.getCurso().getCargaHoraria();
        double percentualConcluido = (cargaHorariaCurso > 0) ? (100.0 * horasTotais) / cargaHorariaCurso : 0.0;

        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("discente", discente.getNome());
        relatorio.put("curso", discente.getCurso().getNome());
        relatorio.put("horasAproveitamento", horasAproveitamento);
        relatorio.put("horasInscricao", horasInscricao);
        relatorio.put("horasTotais", horasTotais);
        relatorio.put("cargaHorariaCurso", cargaHorariaCurso);
        relatorio.put("percentualConcluido", Math.min(percentualConcluido, 100.0));

        return relatorio;
    }
}