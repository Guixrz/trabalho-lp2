package br.ufma.services;

import br.ufma.entidades.Coordenador;
import br.ufma.entidades.Curso;
import br.ufma.entidades.VersaoPPC;
import br.ufma.excecoes.InvalidDataException;
import br.ufma.excecoes.NotFoundException;
import br.ufma.excecoes.OperationNotAllowedException;
import br.ufma.repo.CoordenadorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PpcService {

    private final CursoService cursoService;
    private final CoordenadorRepo coordenadorRepo;

    @Transactional
    public VersaoPPC cadastrarPPC(Long coordenadorId, Long cursoId, String versao, int cargaHoraria) {
        Coordenador coordenador = findCoordenadorById(coordenadorId);
        Curso curso = cursoService.buscarPorId(cursoId);
        validarEntrada(coordenador, curso);

        VersaoPPC novoPPC = VersaoPPC.builder()
                .versao(versao)
                .cargaHoraria(cargaHoraria)
                .dataCriacao(LocalDate.now())
                .coordenador(coordenador)
                .build();

        cursoService.registrarVersao(curso, novoPPC);
        return novoPPC;
    }

    @Transactional
    public VersaoPPC atualizar(Long cursoId, int novaCargaHoraria, String novaVersao, Long coordenadorId) {
        Coordenador coordenador = findCoordenadorById(coordenadorId);
        Curso curso = cursoService.buscarPorId(cursoId);
        validarEntrada(coordenador, curso);

        VersaoPPC novaVersaoPPC = VersaoPPC.builder()
                .versao(novaVersao)
                .cargaHoraria(novaCargaHoraria)
                .dataCriacao(LocalDate.now())
                .coordenador(coordenador)
                .build();

        cursoService.registrarVersao(curso, novaVersaoPPC);
        return novaVersaoPPC;
    }

    public List<VersaoPPC> listarHistorico(Long cursoId) {
        Curso curso = cursoService.buscarPorId(cursoId);
        validarCurso(curso);
        return cursoService.listarHistorico(curso.getId());
    }

    public VersaoPPC obterVersaoAtual(Long cursoId) {
        Curso curso = cursoService.buscarPorId(cursoId);
        validarCurso(curso);
        return cursoService.obterVersaoAtual(curso.getId());
    }

    public VersaoPPC obterVersaoAnterior(Long cursoId) {
        Curso curso = cursoService.buscarPorId(cursoId);
        validarCurso(curso);
        return cursoService.obterVersaoAnterior(curso.getId());
    }

    private Coordenador findCoordenadorById(Long id) {
        return coordenadorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Coordenador com ID " + id + " não encontrado."));
    }

    private void validarEntrada(Coordenador coordenador, Curso curso) {
        if (coordenador == null) {
            throw new InvalidDataException("Coordenador não pode ser nulo.");
        }
        if (!coordenador.isAtivo()) {
            throw new OperationNotAllowedException("Operação não permitida: Coordenador está inativo.");
        }
        validarCurso(curso);
    }

    private void validarCurso(Curso curso) {
        if (curso == null || curso.getId() == null) {
            throw new InvalidDataException("Curso inválido ou não salvo.");
        }
    }
}