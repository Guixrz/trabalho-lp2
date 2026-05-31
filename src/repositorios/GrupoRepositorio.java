package repositorios;

import entidades.Grupo;
import excecoes.InvalidDataException;
import interfaces.Repositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrupoRepositorio implements Repositorio<Grupo, String> {

    private Map<String, Grupo> grupos = new HashMap<>();

    @Override
    public void salvar(Grupo grupo) {
        if (grupo == null) throw new InvalidDataException("Grupo Nulo");
        grupos.put(grupo.getId(), grupo);
    }

    @Override
    public Grupo buscar(String chave) {
        if (chave == null) throw new InvalidDataException("Chave Nula");
        // chave é id aqui
        return grupos.get(chave);
    }

    @Override
    public List<Grupo> listarTodos() {
        return new ArrayList<>(grupos.values());
    }

    @Override
    public void remover(String chave) {
        if (chave == null) throw new InvalidDataException("Chave Nula");
        grupos.remove(chave);
    }
}