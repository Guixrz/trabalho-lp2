package repositorios;

import entidades.Usuarios;
import entidades.Discente;
import interfaces.Repositorio;
import excecoes.InvalidDataException;
import excecoes.IntegrityException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioRepositorio implements Repositorio<Usuarios, String> {

    private Map<String, Usuarios> usuariosPorEmail = new HashMap<>();
    private Map<String, Discente> discentesPorMatricula = new HashMap<>();
    private List<Usuarios> usuariosTodos = new ArrayList<>();

    @Override
    public void salvar(Usuarios usuario) {
        if (usuario == null || usuario.getEmail() == null) {
            throw new InvalidDataException("Usuário ou email nulo");
        }
        if (usuariosPorEmail.containsKey(usuario.getEmail())) {
            throw new IntegrityException("Email já existe: " + usuario.getEmail());
        }
        usuariosPorEmail.put(usuario.getEmail(), usuario);
        usuariosTodos.add(usuario);
        if (usuario instanceof Discente) {
            Discente d = (Discente) usuario;
            if (d.getMatricula() != null) {
                discentesPorMatricula.put(d.getMatricula(), d);
            }
        }
    }

    @Override
    public Usuarios buscar(String chave) {
        if (chave == null) throw new InvalidDataException("Chave nula para busca");
        return usuariosPorEmail.get(chave);
    }

    public Discente buscarMatricula(String matricula) {
        if (matricula == null) throw new InvalidDataException("Matrícula nula");
        return discentesPorMatricula.get(matricula);
    }

    @Override
    public List<Usuarios> listarTodos() {
        return new ArrayList<>(usuariosTodos);
    }

    @Override
    public void remover(String chave) {
        if (chave == null) throw new InvalidDataException("Email nulo ao remover usuário");
        Usuarios u = usuariosPorEmail.remove(chave);
        if (u != null) {
            usuariosTodos.remove(u);
            if (u instanceof Discente) {
                Discente d = (Discente) u;
                discentesPorMatricula.remove(d.getMatricula());
            }
        } else {
            throw new IntegrityException("Usuário com email '" + chave + "' não encontrado para remoção");
        }
    }
}