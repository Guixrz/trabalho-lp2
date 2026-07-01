package br.ufma.services;

import br.ufma.entidades.Aproveitamento;
import br.ufma.entidades.Discente;
import br.ufma.entidades.Usuarios;
import br.ufma.entidades.enums.Status;
import br.ufma.excecoes.NotFoundException;
import br.ufma.excecoes.RegraNegocioException;
import br.ufma.repo.AproveitamentoRepo;
import br.ufma.repo.DiscenteRepo;
import br.ufma.repo.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AproveitamentoService {

    private final AproveitamentoRepo aproveitamentoRepo;
    private final DiscenteRepo discenteRepo;
    private final UsuarioRepo usuarioRepo;

    @Transactional
    public Aproveitamento submeter(Long idDiscente, String descricao, String instituicao, int horas, LocalDate dataLimite) {
        Discente discente = discenteRepo.findById(idDiscente)
                .orElseThrow(() -> new NotFoundException("Discente com ID " + idDiscente + " não encontrado."));

        Aproveitamento novoAproveitamento = Aproveitamento.builder()
                .discente(discente)
                .descricao(descricao)
                .instituicao(instituicao)
                .horas(horas)
                .status(Status.pendente)
                .dataLimite(dataLimite != null ? dataLimite : LocalDate.now().plusDays(15))
                .build();

        return aproveitamentoRepo.save(novoAproveitamento);
    }

    @Transactional
    public void aprovar(Long aproveitamentoId, Long idAvaliador) {
        Aproveitamento ap = buscarPorId(aproveitamentoId);
        Usuarios avaliador = findUsuarioById(idAvaliador);
        ap.setAvaliador(avaliador);
        ap.setStatus(Status.aprovada);
        aproveitamentoRepo.save(ap);
    }

    @Transactional
    public void rejeitar(Long aproveitamentoId, Long idAvaliador, String motivo) {
        Aproveitamento ap = buscarPorId(aproveitamentoId);
        Usuarios avaliador = findUsuarioById(idAvaliador);
        ap.setAvaliador(avaliador);
        ap.setMotivo_rejeicao(motivo);
        ap.setStatus(Status.rejeitada);
        aproveitamentoRepo.save(ap);
    }

    @Transactional
    public void reenviar(Long aproveitamentoId) {
        Aproveitamento ap = buscarPorId(aproveitamentoId);
        if (ap.getStatus() != Status.rejeitada) {
            throw new RegraNegocioException("Apenas solicitações com status 'rejeitada' podem ser reenviadas.");
        }
        ap.setStatus(Status.pendente);
        ap.setAvaliador(null);
        ap.setMotivo_rejeicao(null);
        ap.setDataLimite(LocalDate.now().plusDays(15));
        aproveitamentoRepo.save(ap);
    }

    public List<Aproveitamento> listarTodos() {
        return aproveitamentoRepo.findAll();
    }

    public List<Aproveitamento> listarPendentes() {
        return aproveitamentoRepo.findByStatus(Status.pendente);
    }

    public List<Aproveitamento> listarVencidos() {
        return aproveitamentoRepo.findByStatusAndDataLimiteBefore(Status.pendente, LocalDate.now());
    }

    public Aproveitamento buscarPorId(Long id) {
        return aproveitamentoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Aproveitamento com ID " + id + " não encontrado."));
    }

    private Usuarios findUsuarioById(Long id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + id + " não encontrado."));
    }
}