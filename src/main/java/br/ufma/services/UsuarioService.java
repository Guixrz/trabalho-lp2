package br.ufma.services;

import br.ufma.entidades.Papel;
import br.ufma.entidades.Usuarios;
import br.ufma.excecoes.NotFoundException;
import br.ufma.excecoes.RegraNegocioException;
import br.ufma.repo.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepo usuarioRepo;

    @Transactional
    public Usuarios cadastrar(Usuarios usuario) {
        if (usuario == null) {
            throw new RegraNegocioException("Usuário não pode ser nulo.");
        }
        if (usuarioRepo.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RegraNegocioException("E-mail já cadastrado.");
        }
        usuario.setAtivo(true); // usuário já nasce ativo
        return usuarioRepo.save(usuario);
    }

    // ideia que encontrei -> passwordEncoder.matches(senha, usuario.getSenha())
    public Usuarios autenticar(String email, String senha) {
        Optional<Usuarios> usuarioOpt = usuarioRepo.findByEmail(email);

        if (usuarioOpt.isEmpty() || !usuarioOpt.get().isAtivo()) {
            throw new RegraNegocioException("Usuário não encontrado ou inativo.");
        }

        if (!Objects.equals(usuarioOpt.get().getSenha(), senha)) {
            throw new RegraNegocioException("Senha inválida.");
        }

        return usuarioOpt.get();
    }

    @Transactional
    public Usuarios desativar(String email) {
        Usuarios usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário com e-mail " + email + " não encontrado."));

        usuario.setAtivo(false);
        return usuarioRepo.save(usuario);

    }

    @Transactional
    public void atribuirPapel(Long usuarioId, Papel papel) {
        Usuarios usuario = findById(usuarioId);
        if (papel == null) {
            throw new RegraNegocioException("Papel não pode ser nulo.");
        }
        if (usuario.getPapelSistema() == null) {
            usuario.setPapelSistema(new ArrayList<>());
        }
        //evita duplicatas
        if (!usuario.getPapelSistema().contains(papel)) {
            usuario.getPapelSistema().add(papel);
            usuarioRepo.save(usuario);
        }
    }

    public List<Usuarios> listarTodos() {
        return usuarioRepo.findAll();
    }

    public Usuarios findById(Long id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + id + " não encontrado."));
    }
}
