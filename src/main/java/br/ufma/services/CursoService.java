package br.ufma.services;

import br.ufma.entidades.Curso;
import br.ufma.entidades.VersaoPPC;
import br.ufma.excecoes.InvalidDataException;
import br.ufma.excecoes.NotFoundException;
import br.ufma.repo.CursoRepo;
import br.ufma.repo.VersaoPpcRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepo cursoRepo;
    private final VersaoPpcRepo versaoPpcRepo;

    @Transactional
    public Curso salvar(Curso curso) {
        return cursoRepo.save(curso);
    }

    public List<Curso> listarTodos() {
        return cursoRepo.findAll();
    }

    public Curso buscarPorId(Long id) {
        return cursoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso com ID " + id + " não encontrado."));
    }

    @Transactional
    public Curso registrarVersao(Curso curso, VersaoPPC versao) {
        if (curso == null || curso.getId() == null) {
            throw new InvalidDataException("Dados do curso para registrar versão são inválidos.");
        }
        if (versao == null) {
            throw new InvalidDataException("A versão do PPC não pode ser nula.");
        }

        Curso cursoPersistido = buscarPorId(curso.getId());
        versao.setCurso(cursoPersistido);
        cursoPersistido.getVersoes().add(0, versao);
        cursoPersistido.setCargaHoraria(versao.getCargaHoraria());

        return cursoRepo.save(cursoPersistido);
    }

    public List<VersaoPPC> listarHistorico(Long cursoId) {
        return versaoPpcRepo.findByCurso_IdOrderByDataCriacaoDesc(cursoId);
    }

    public VersaoPPC obterVersaoAtual(Long cursoId) {
        List<VersaoPPC> versoes = listarHistorico(cursoId);
        return versoes.isEmpty() ? null : versoes.get(0);
    }

    public VersaoPPC obterVersaoAnterior(Long cursoId) {
        List<VersaoPPC> versoes = listarHistorico(cursoId);
        return versoes.size() < 2 ? null : versoes.get(1);
    }

    public int getTamanhoHistorico(Long cursoId) {
        return listarHistorico(cursoId).size();
    }
}