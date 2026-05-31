package services;

import entidades.Usuarios;
import entidades.enums.PapelSistema;
import excecoes.InvalidDataException;
import excecoes.OperationNotAllowedException;
import repositorios.UsuarioRepositorio;

public class AdminService {

    private UsuarioRepositorio usuarioRepositorio;

    public AdminService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void cadastrarGestor(Usuarios admin, String emailUsuario, PapelSistema novoPapel) {
        if (admin == null || admin.getPapel() != PapelSistema.admin) {
            throw new OperationNotAllowedException("Apenas administradores podem cadastrar gestores");
        }

        if (novoPapel != PapelSistema.coord_curso && novoPapel != PapelSistema.coord_uce && novoPapel != PapelSistema.admin) {
            throw new OperationNotAllowedException("O papel '\" + novoPapel + \"' não é um papel de gestor válido");
        }

        Usuarios usuario = usuarioRepositorio.buscar(emailUsuario);
        if (usuario == null) {
            throw new InvalidDataException("Usuário com o e-mail '\" + emailUsuario + \"' não encontrado");
        }

        usuario.setPapel(novoPapel);
        System.out.println("Sucesso: O usuário " + usuario.getNome() + " foi promovido a " + novoPapel + ".");
    }
}
