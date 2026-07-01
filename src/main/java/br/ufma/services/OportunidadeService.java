package br.ufma.services;

import br.ufma.entidades.Docente;
import br.ufma.entidades.Oportunidade;
import br.ufma.entidades.TipoOportunidade;
import br.ufma.entidades.Usuarios;
import br.ufma.entidades.enums.Modalidade;
import br.ufma.entidades.enums.Status;
import br.ufma.excecoes.NotFoundException;
import br.ufma.excecoes.RegraNegocioException;
import br.ufma.interfaces.Aprovavel;
import br.ufma.repo.DocenteRepo;
import br.ufma.repo.OportunidadeRepo;
import br.ufma.repo.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OportunidadeService implements Aprovavel<Oportunidade> {

    private final OportunidadeRepo oportunidadeRepo;
    private final UsuarioRepo usuarioRepo;
    private final DocenteRepo docenteRepo;

    @Transactional
    public Oportunidade criarOportunidade(Long idDocenteResponsavel, String titulo, String descricao,
                                          TipoOportunidade tipo, Modalidade modalidade, int cargaHoraria,
                                          int vagas, LocalDateTime inicio, Long idAutor) {

        Docente responsavel = docenteRepo.findById(idDocenteResponsavel)
                .orElseThrow(() -> new NotFoundException("Docente responsável com ID " + idDocenteResponsavel + " não encontrado."));

        Usuarios autor = usuarioRepo.findById(idAutor)
                .orElseThrow(() -> new NotFoundException("Autor com ID " + idAutor + " não encontrado."));

        Oportunidade oportunidade = Oportunidade.builder()
                .responsavel(responsavel)
                .titulo(titulo)
                .descricao(descricao)
                .tipo(tipo)
                .modalidade(modalidade)
                .carga_horaria(cargaHoraria)
                .vagas(vagas)
                .status(Status.pendente) // Novas oportunidades nascem como pendentes
                .inicio(inicio != null ? inicio : LocalDateTime.now())
                .autor(autor)
                .build();

        return oportunidadeRepo.save(oportunidade);
    }

    @Transactional
    public Oportunidade aprovar(Long idOportunidade, Long idAvaliador) {
        Oportunidade oportunidade = findById(idOportunidade);
        Usuarios avaliador = findUsuarioById(idAvaliador);

        if (oportunidade.getStatus() != Status.pendente) {
            throw new RegraNegocioException("Apenas oportunidades com status 'pendente' podem ser aprovadas.");
        }

        oportunidade.setAvaliador(avaliador);
        oportunidade.setStatus(Status.publicada);
        return oportunidadeRepo.save(oportunidade);
    }

    @Transactional
    public Oportunidade rejeitar(Long idOportunidade, Long idAvaliador, String motivo) {
        Oportunidade oportunidade = findById(idOportunidade);
        Usuarios avaliador = findUsuarioById(idAvaliador);

        if (motivo == null || motivo.trim().isEmpty()) {
            throw new RegraNegocioException("O motivo da rejeição é obrigatório.");
        }

        oportunidade.setAvaliador(avaliador);
        oportunidade.setMotivoRejeicao(motivo);
        oportunidade.setStatus(Status.rejeitada);
        return oportunidadeRepo.save(oportunidade);
    }

    @Transactional
    public void registrarPlanoAtividade(Long idOportunidade, LocalDate dataInicio) {
        Oportunidade oportunidade = findById(idOportunidade);

        if (dataInicio.isBefore(LocalDate.now())) {
            throw new RegraNegocioException("A data de início não pode ser anterior a hoje.");
        }

        oportunidade.setInicio(dataInicio.atStartOfDay());
        oportunidadeRepo.save(oportunidade);
    }

    public List<Oportunidade> listarTodas() {
        return oportunidadeRepo.findAll();
    }

    public Oportunidade findById(Long id) {
        return oportunidadeRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Oportunidade com ID " + id + " não encontrada."));
    }

    private Usuarios findUsuarioById(Long id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + id + " não encontrado."));
    }
}