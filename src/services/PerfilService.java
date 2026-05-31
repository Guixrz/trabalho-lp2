package services;

import entidades.Usuarios;
import entidades.enums.PapelSistema;
import excecoes.InvalidDataException;
import repositorios.UsuarioRepositorio;

public class PerfilService {

    private UsuarioRepositorio usuarioRepo;

    public PerfilService(UsuarioRepositorio usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public void listarUsuarios() {
        for (Usuarios u : usuarioRepo.listarTodos()) {
            System.out.println("- " + u.getNome()
                    + " | " + u.getPapel()
                    + " | Ativo: " + u.isAtivo());
        }
    }

    public void ativar(String email) {
        Usuarios u = usuarioRepo.buscar(email);
        if (u == null) {
            throw new InvalidDataException("usuario nao encontrado");
        }
        u.setAtivo(true);
        System.out.println("usuario ativado: " + u.getNome());
    }

    public void desativar(String email) {
        Usuarios u = usuarioRepo.buscar(email);
        if (u == null) {
            throw new InvalidDataException("usuario nao encontrado");
        }
        u.setAtivo(false);
        System.out.println("usuario desativado: " + u.getNome());
    }

    public void alterarPapel(String email, PapelSistema novoPapelSistema) {
        Usuarios u = usuarioRepo.buscar(email);
        if (u == null) {
            throw new InvalidDataException("usuario nao encontrado");
        }
        u.setPapel(novoPapelSistema);
        System.out.println("papel alterado para " + novoPapelSistema
                + " — usuario: " + u.getNome());
    }

}
