package br.ufma.services;

import br.ufma.entidades.Docente;
import br.ufma.entidades.Grupo;
import br.ufma.entidades.Usuarios;
import br.ufma.entidades.enums.Status;
import br.ufma.excecoes.InvalidDataException;
import br.ufma.excecoes.NotFoundException;
import br.ufma.excecoes.RegraNegocioException;
import br.ufma.repo.GrupoRepo;
import br.ufma.repo.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrupoService {

    private final GrupoRepo grupoRepo;
    private final UsuarioRepo usuarioRepo;

    @Transactional
    public Grupo cadastrar(String nome, String tipo, String email, String descricao, Docente responsavel) {
        if (nome == null || nome.isBlank()) {
            throw new InvalidDataException("O nome do grupo é obrigatório.");
        }
        if (responsavel == null) {
            throw new InvalidDataException("O docente responsável é obrigatório.");
        }

        Grupo grupo = Grupo.builder()
                .nome(nome)
                .tipo(tipo)
                .email(email)
                .descricao(descricao)
                .responsavel(responsavel)
                .status(Status.rascunho)
                .build();

        return grupoRepo.save(grupo);
    }

    @Transactional
    public Grupo aprovarSolicitacao(Long idGrupo) {
        Grupo grupo = findGrupoById(idGrupo);
        grupo.setStatus(Status.publicada);
        return grupoRepo.save(grupo);
    }

    @Transactional
    public Grupo encerrar(Long idGrupo) {
        Grupo grupo = findGrupoById(idGrupo);
        grupo.setStatus(Status.cancelada);
        return grupoRepo.save(grupo);
    }

    public List<Grupo> listarAtivos() {
        return grupoRepo.findByStatus(Status.publicada);
    }

    public List<Grupo> listarTodos() {
        return grupoRepo.findAll();
    }

    @Transactional
    public Grupo adicionarMembro(Long idGrupo, Long idUsuario) {
        Grupo grupo = findGrupoById(idGrupo);
        Usuarios usuario = findUsuarioById(idUsuario);

        if (!grupo.getMembros().add(usuario)) {
            throw new RegraNegocioException("Usuário já pertence ao grupo.");
        }
        return grupoRepo.save(grupo);
    }

    @Transactional
    public Grupo removerMembro(Long idGrupo, Long idUsuario) {
        Grupo grupo = findGrupoById(idGrupo);
        Usuarios usuario = findUsuarioById(idUsuario);

        if (!grupo.getMembros().remove(usuario)) {
            throw new RegraNegocioException("Usuário não pertence ao grupo.");
        }
        return grupoRepo.save(grupo);
    }

    public boolean isMembro(Long idGrupo, Long idUsuario) {
        Grupo grupo = findGrupoById(idGrupo);
        Usuarios usuario = findUsuarioById(idUsuario);
        return grupo.getMembros().contains(usuario);
    }

    private Grupo findGrupoById(Long idGrupo) {
        return grupoRepo.findById(idGrupo)
                .orElseThrow(() -> new NotFoundException("Grupo com ID " + idGrupo + " não encontrado."));
    }

    private Usuarios findUsuarioById(Long idUsuario) {
        return usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + idUsuario + " não encontrado."));
    }
}
