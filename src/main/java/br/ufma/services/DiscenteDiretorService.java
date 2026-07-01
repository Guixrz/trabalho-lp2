package br.ufma.services;

import br.ufma.entidades.DiscenteDiretor;
import br.ufma.entidades.Docente;
import br.ufma.entidades.Oportunidade;
import br.ufma.entidades.Usuarios;
import br.ufma.entidades.TipoOportunidade;
import br.ufma.entidades.enums.Modalidade;
import br.ufma.entidades.enums.Status;
import br.ufma.excecoes.NotFoundException;
import br.ufma.interfaces.CriadorOportunidade;
import br.ufma.repo.DiscenteDiretorRepo;
import br.ufma.repo.DocenteRepo;
import br.ufma.repo.OportunidadeRepo;
import br.ufma.repo.TipoOportunidadeRepo;
import br.ufma.repo.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscenteDiretorService implements CriadorOportunidade {

    private final DiscenteDiretorRepo discenteDiretorRepo;
    private final OportunidadeRepo oportunidadeRepo;
    private final TipoOportunidadeRepo tipoOportunidadeRepo;
    private final UsuarioRepo usuarioRepo;
    private final DocenteRepo docenteRepo;

    // controller especifico

    @Transactional
    public DiscenteDiretor salvar(DiscenteDiretor discenteDiretor) {
        return discenteDiretorRepo.save(discenteDiretor);
    }

    public List<DiscenteDiretor> listarTodos() {
        return discenteDiretorRepo.findAll();
    }

    public DiscenteDiretor buscarPorId(Long id) {
        return discenteDiretorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Discente Diretor com ID " + id + " não encontrado."));
    }

    @Transactional
    public Oportunidade criarOportunidadePorIds(String titulo, String descricao, Long idTipoOportunidade, Modalidade modalidade,
                                                int cargaHoraria, int vagas, Status status, LocalDateTime inicio,
                                                Long autorId, Long responsavelId) {
        Usuarios autor = usuarioRepo.findById(autorId)
                .orElseThrow(() -> new NotFoundException("Autor com ID " + autorId + " não encontrado."));
        Docente responsavel = docenteRepo.findById(responsavelId)
                .orElseThrow(() -> new NotFoundException("Docente responsável com ID " + responsavelId + " não encontrado."));

        return criarOportunidade(titulo, descricao, idTipoOportunidade, modalidade, cargaHoraria, vagas, status, inicio, autor, responsavel);
    }

    @Override
    @Transactional
    public Oportunidade criarOportunidade(String titulo, String descricao, Long idTipoOportunidade, Modalidade modalidade,
                                          int cargaHoraria, int vagas, Status status, LocalDateTime inicio,
                                          Usuarios autor, Docente responsavel) {
        if (idTipoOportunidade == null) {
            throw new NotFoundException("Tipo de oportunidade nao informado.");
        }

        TipoOportunidade tipo = tipoOportunidadeRepo.findById(idTipoOportunidade)
                .orElseThrow(() -> new NotFoundException(
                        "Tipo de oportunidade com ID " + idTipoOportunidade + " nao encontrado."));

        Oportunidade oportunidade = Oportunidade.builder()
                .titulo(titulo)
                .descricao(descricao)
                .tipo(tipo)
                .modalidade(modalidade)
                .carga_horaria(cargaHoraria)
                .vagas(vagas)
                .status(status)
                .inicio(inicio)
                .autor(autor)
                .responsavel(responsavel)
                .build();

        return oportunidadeRepo.save(oportunidade);
    }

    public List<Oportunidade> listarOportunidadesPorAutor(Long idAutor) {
        return oportunidadeRepo.findByAutorId(idAutor);
    }
}