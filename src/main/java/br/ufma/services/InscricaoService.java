package br.ufma.services;

import br.ufma.entidades.Discente;
import br.ufma.entidades.Inscricao;
import br.ufma.entidades.Oportunidade;
import br.ufma.entidades.Usuarios;
import br.ufma.entidades.enums.Status;
import br.ufma.interfaces.Aprovavel;
import br.ufma.excecoes.InvalidDataException;
import br.ufma.excecoes.NotFoundException;
import br.ufma.excecoes.RegraNegocioException;
import br.ufma.repo.DiscenteRepo;
import br.ufma.repo.InscricaoRepo;
import br.ufma.repo.OportunidadeRepo;
import br.ufma.repo.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscricaoService implements Aprovavel<Inscricao> {

    private final InscricaoRepo inscricaoRepo;
    private final UsuarioRepo usuarioRepo;
    private final OportunidadeRepo oportunidadeRepo;
    private final DiscenteRepo discenteRepo;

    @Transactional
    public Inscricao criar(Long idOportunidade, Long idDiscente, String motivacao) {
        Oportunidade oportunidade = oportunidadeRepo.findById(idOportunidade)
                .orElseThrow(() -> new NotFoundException("Oportunidade com ID " + idOportunidade + " não encontrada."));
        Discente discente = discenteRepo.findById(idDiscente)
                .orElseThrow(() -> new NotFoundException("Discente com ID " + idDiscente + " não encontrado."));

        if (inscricaoRepo.findByDiscenteAndOportunidade(discente, oportunidade).isPresent()) {
            throw new RegraNegocioException("Discente já inscrito nesta oportunidade.");
        }

        Inscricao inscricao = Inscricao.builder()
                .oportunidade(oportunidade)
                .discente(discente)
                .motivacao(motivacao)
                .status(Status.pendente)
                .dataInscricao(LocalDate.now())
                .build();

        return inscricaoRepo.save(inscricao);
    }

    @Transactional
    public Inscricao abandonar(Long idInscricao) {
        Inscricao inscricao = buscarPorId(idInscricao);
        inscricao.setStatus(Status.abandonada);
        return inscricaoRepo.save(inscricao);
    }

    @Transactional
    public Inscricao reenviar(Long idInscricao, String novaMotivacao) {
        Inscricao inscricao = buscarPorId(idInscricao);

        if (inscricao.getStatus() != Status.rejeitada) {
            throw new RegraNegocioException("Apenas inscrições com status 'rejeitada' podem ser reenviadas.");
        }

        inscricao.setMotivacao(novaMotivacao);
        inscricao.setStatus(Status.pendente);
        inscricao.setDataAprovacao(null);
        inscricao.setAvaliador(null);

        return inscricaoRepo.save(inscricao);
    }

    @Override
    @Transactional
    public Inscricao aprovar(Long idEntidade, Long idAvaliador) {
        Inscricao inscricao = buscarPorId(idEntidade);
        Usuarios avaliador = findUsuarioById(idAvaliador);

        if (inscricao.getStatus() != Status.pendente) {
            throw new RegraNegocioException("Apenas inscrições com status 'pendente' podem ser aprovadas.");
        }

        inscricao.setAvaliador(avaliador);
        inscricao.setStatus(Status.aprovada);
        inscricao.setDataAprovacao(LocalDate.now());

        return inscricaoRepo.save(inscricao);
    }

    @Override
    @Transactional
    public Inscricao rejeitar(Long idEntidade, Long idAvaliador, String motivo) {
        Inscricao inscricao = buscarPorId(idEntidade);
        Usuarios avaliador = findUsuarioById(idAvaliador);

        if (inscricao.getStatus() != Status.pendente) {
            throw new RegraNegocioException("Apenas inscrições com status 'pendente' podem ser rejeitadas.");
        }
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new InvalidDataException("O motivo da rejeição é obrigatório.");
        }

        inscricao.setAvaliador(avaliador);
        inscricao.setStatus(Status.rejeitada);
        inscricao.setMotivacao(motivo);

        return inscricaoRepo.save(inscricao);
    }

    public List<Inscricao> listarTodas() {
        return inscricaoRepo.findAll();
    }

    public Inscricao buscarPorId(Long id) {
        return inscricaoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Inscrição com ID " + id + " não encontrada."));
    }

    private Usuarios findUsuarioById(Long id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + id + " não encontrado."));
    }
}